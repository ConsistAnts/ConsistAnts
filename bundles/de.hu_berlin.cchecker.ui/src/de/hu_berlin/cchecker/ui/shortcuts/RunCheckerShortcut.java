package de.hu_berlin.cchecker.ui.shortcuts;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import de.hu_berlin.cchecker.core.checking.CheckingAlgorithm;
import de.hu_berlin.cchecker.core.checking.FootprintMatrixCheckingAlgorithm;
import de.hu_berlin.cchecker.core.checking.report.ConsistencyReport;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.traces.TraceDataset;
import de.hu_berlin.cchecker.ui.collectors.ConditionalPairCollector;
import de.hu_berlin.cchecker.ui.collectors.Pair;
import de.hu_berlin.cchecker.ui.util.ConsistencyCheckInput;
import de.hu_berlin.cchecker.ui.util.ConsistencyReportIOUtils;
import de.hu_berlin.cchecker.ui.util.ErrorMessages;
import de.hu_berlin.cchecker.ui.util.IOUtils;
import de.hu_berlin.cchecker.ui.util.ProbabilisticAutomatonIOUtils;
import de.hu_berlin.cchecker.ui.util.exceptions.InvalidFileSelectionException;
import de.hu_berlin.cchecker.ui.util.exceptions.InvalidProbabilisticAutomatonFileException;
import de.hu_berlin.cchecker.ui.util.exceptions.InvalidTraceDatasetFileException;
import de.hu_berlin.cchecker.ui.util.exceptions.ProcessingException;

/**
 * This is the launch shortcut for running a consistency check on a selected
 * model and trace dataset file. (Run As > Run Consistency Checker).
 * 
 * @author Linus
 *
 */
public class RunCheckerShortcut implements ILaunchShortcut {

	@Override
	/**
	 * Launches running a consistency check on a model and a trace dataset file by
	 * selecting both and then selecting Run / Run As.
	 * 
	 * This will validate that the given selection contains exactly two files, one
	 * of them being a .trc file and the other being a .pdfa file that can
	 * be parsed to the java models. Then this will initiate our consistency
	 * checking algorithm on that model and those traces and save the resulting
	 * report in a new text file, then print that file's contents to the console. If
	 * any of the validation requirements aren't met, an error dialog will be shown.
	 */
	public void launch(ISelection selection, String mode) {
		if (selection instanceof StructuredSelection) {
			StructuredSelection structuredSelection = (StructuredSelection) selection;

			ConsistencyCheckInput input = null;
			try {
				input = this.getInputFromSelection(structuredSelection);
			} catch (InvalidFileSelectionException e) {
				ErrorMessages.showInvalidFileSelectionConsistencyCheckErrorMessage();
				return;
			} catch (InvalidTraceDatasetFileException e) {
				ErrorMessages.showInvalidTraceDatasetFileErrorMessage(e.getFileName());
				return;
			} catch (InvalidProbabilisticAutomatonFileException e) {
				ErrorMessages.showInvalidProbabilisticAutomatonFileErrorMessage(e.getFileName());
				return;
			} catch (ProcessingException e) {
				ErrorMessages.showProcessingFailedMessage(e.getMessage());
				return;
			}
			// Try to run a consistency check with the computed input
			try {
				runConsistencyCheck(input);
			} catch (InvocationTargetException | InterruptedException e) {
				ErrorMessages.showFailedToPerformConsistencyCheck();
			}

		} else {
			ErrorMessages.showInvalidFileSelectionConsistencyCheckErrorMessage();
			return;
		}
	}

	@Override
	/**
	 * Launches running a consistency check on a model and a trace dataset file by
	 * having them open in the editor and then selecting Run / Run As. This use case
	 * does not apply to running consistency checks, so this will do nothing.
	 */
	public void launch(IEditorPart editor, String mode) {
		// Not supported, since an editor can only be converted to a one-element-selection.
		// Consistency Checking however, always needs two input files.
		ErrorMessages.showUnsupportedOperationError();
	}

	/**
	 * Returns a populated {@link ConsistencyCheckInput} for the given
	 * {@link StructuredSelection}.
	 * 
	 * Validates the selection and extracts the needed {@link ProbabilisticAutomata}
	 * and {@link TraceDataset} model. 
	 * 
	 * If all validations succeed, the results from parsing the selected files will
	 * be returned as an instance of {@link ConsistencyCheckInput}.
	 * 
	 * @throws InvalidFileSelectionException
	 * @throws InvalidTraceDatasetFileException
	 * @throws InvalidProbabilisticAutomatonFileException
	 */
	protected ConsistencyCheckInput getInputFromSelection(StructuredSelection selection)
			throws InvalidFileSelectionException, InvalidTraceDatasetFileException,
			InvalidProbabilisticAutomatonFileException, ProcessingException {

		if (selection.toArray().length != 2) {
			throw new InvalidFileSelectionException(
					"Invalid selection for consistency checking: Make sure exactly one trace data file and one PDFA file is selected.");
		}

		// Filter ".pdfa" and ".trc" resources from selection
		Pair<IResource, IResource> resources = Arrays.asList(selection.toArray())
			.stream()
			.filter(e -> e instanceof IResource)
			.map(e -> ((IResource) e))
			.collect(new ConditionalPairCollector<>(IResource.class, IResource.class,
					r -> "pdfa".equals(r.getFileExtension()), 
					r -> ("trc".equals(r.getFileExtension()) || "strc".equals(r.getFileExtension()) 
							|| "jtrc".equals(r.getFileExtension()))));

		IResource pdfaResource = resources.getFirst();
		IResource traceResource = resources.getSecond();

		// Check that the selection was valid
		if (pdfaResource == null || traceResource == null) {
			throw new InvalidFileSelectionException(
					"Invalid selection for consistency checking: Make sure exactly one trace data file and one PDFA file is selected.");
		}

		// Build input object
		ConsistencyCheckInput input = new ConsistencyCheckInput();

		input.setAutomataPath(pdfaResource.getFullPath());
		input.setAutomaton(ProbabilisticAutomatonIOUtils.getProbabilisticAutomatonFromPath(pdfaResource.getFullPath(), true));

		input.setTraceDatasetPath(traceResource.getFullPath());
		input.setTraceDataset(IOUtils.getTraceDatasetFromURI(traceResource.getLocationURI()));

		return input;
	}

	/**
	 * Runs a consistency check on the given input.
	 * 
	 * Automatically displays progress monitor dialogs as needed.
	 * 
	 * This method will open the newly created report file on completion.
	 */
	public void runConsistencyCheck(ConsistencyCheckInput input)
			throws InvocationTargetException, InterruptedException {
		// Create a path for the new report file
		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace()
			.getRoot();
		IPath parentFolderPath = input.getAutomataPath()
			.removeLastSegments(1);
		IContainer parentFolder = IOUtils.getContainerInWorkspace(workspaceRoot, parentFolderPath);

		String reportFilename = new Path(input.getAutomataPath()
			.lastSegment()).removeFileExtension()
				.addFileExtension(IOUtils.getTimestring())
				.addFileExtension("report").toString();
		
		IPath reportPath = input.getAutomataPath().removeLastSegments(1).append(reportFilename);

		// Create a progress monitor dialog for the algorithm
		ProgressMonitorDialog progressDialogAlgorithm = new ProgressMonitorDialog(PlatformUI.getWorkbench()
			.getDisplay()
			.getActiveShell());

		IRunnableWithProgress algorithmRunnable = new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				SubMonitor subMonitor = SubMonitor.convert(monitor, 11);
				// this is just to show some progress before the algorithm starts.
				subMonitor.split(1);
				
				// run our consistency checking algorithm
				CheckingAlgorithm algorithm = new FootprintMatrixCheckingAlgorithm();
				ConsistencyReport report = algorithm.performConsistencyCheck(input.getAutomaton(),
						input.getTraceDataset(), subMonitor.split(8));

				if (null == report) {
					throw new IllegalArgumentException("Failed to perform consistency checking.");
				}
				
				subMonitor.split(1);
				
				// add file specific information to report
				report.setModelPath(input.getAutomataPath().toString());
				report.setTraceDataSetPath(input.getTraceDatasetPath().toString());

				subMonitor.setTaskName("Computing input file hashes");
				
				// compute and set input file hashes
				IFile automataFile = ResourcesPlugin.getWorkspace().getRoot().getFile(input.getAutomataPath());
				IFile traceDataFile = ResourcesPlugin.getWorkspace().getRoot().getFile(input.getTraceDatasetPath());
				
				try {
					report.setModelChecksum(IOUtils.getHash(automataFile));
					report.setTraceDataSetChecksum(IOUtils.getHash(traceDataFile));
				} catch (IOException ioException) {
					throw new InvocationTargetException(ioException);
				}
				
				subMonitor.split(1);
				
				// save report file to disk
				try {
					ConsistencyReportIOUtils.saveReportToFile(report, reportPath);
				} catch (IOException e) {
					throw new InvocationTargetException(e);
				}
				
				subMonitor.done();
			}
		};

		progressDialogAlgorithm.run(true, true, algorithmRunnable);

		IOUtils.waitForRefresh(parentFolder);

		// If everything went well, open the created report file in a new editor
		IFile reportFile = ResourcesPlugin.getWorkspace().getRoot().getFile(reportPath);
		if (null != reportFile) {
			IOUtils.openFileInTextEditor(reportFile);
		}
	}
}
