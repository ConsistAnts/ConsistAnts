package de.hu_berlin.cchecker.ui.shortcuts;

import java.io.IOException;
import java.util.Arrays;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.transformations.sre.SRE2ProbabilisticAutomatonTransformation;
import de.hu_berlin.cchecker.core.transformations.sre.SRE2ProbabilisticAutomatonTransformationImpl;
import de.hu_berlin.cchecker.core.transformations.sre.SRE2DTMCTransformation.SREParseException;
import de.hu_berlin.cchecker.ui.collectors.ConditionalPairCollector;
import de.hu_berlin.cchecker.ui.collectors.Pair;
import de.hu_berlin.cchecker.ui.util.ConsistencyCheckInput;
import de.hu_berlin.cchecker.ui.util.IOUtils;
import de.hu_berlin.cchecker.ui.util.ProbabilisticAutomatonIOUtils;
import de.hu_berlin.cchecker.ui.util.exceptions.InvalidFileSelectionException;
import de.hu_berlin.cchecker.ui.util.exceptions.InvalidProbabilisticAutomatonFileException;
import de.hu_berlin.cchecker.ui.util.exceptions.InvalidTraceDatasetFileException;
import de.hu_berlin.cchecker.ui.util.exceptions.ProcessingException;

/**
 * Launch shortcut for running a consistency check on a selected SRE file and
 * trace dataset file. (Run As > Run Consistency Checker with SRE).
 */
public class RunSRECheckerShortcut extends RunCheckerShortcut {

	private boolean keepIntermediateAutomatonCopy = false;

	@Override
	public void launch(ISelection selection, String mode) {
		keepIntermediateAutomatonCopy = MessageDialog.openQuestion(Display.getCurrent()
			.getActiveShell(), "Save intermediate automaton",
				"Would you like to keep a copy of the intermediate Probabilistic Automaton that "
						+ "is created to perform a consistency check with the given Stochastic Regular "
						+ "Expression (.sre file)?");
		super.launch(selection, mode);
	}

	/**
	 * Returns a populated {@link ConsistencyCheckInput} for the given
	 * {@link StructuredSelection}.
	 *
	 * Transform the given SRE file to an equivalent {@link ProbabilisticAutomata}
	 * model.
	 * 
	 * If all validations succeed, the results from parsing the selected files will
	 * be returned as an instance of {@link ConsistencyCheckInput}.
	 * 
	 * @throws InvalidFileSelectionException
	 * @throws InvalidTraceDatasetFileException
	 * @throws InvalidProbabilisticAutomatonFileException
	 * @throws ProcessingException
	 */
	@Override
	protected ConsistencyCheckInput getInputFromSelection(StructuredSelection selection)
			throws InvalidFileSelectionException, InvalidTraceDatasetFileException,
			InvalidProbabilisticAutomatonFileException, ProcessingException {

		if (selection.toArray().length != 2) {
			throw new InvalidFileSelectionException(
					"Invalid selection for consistency checking: Make sure exactly one trace data file and one SRE file is selected.");
		}

		// Filter ".sre" and ".trc" resources from selection
		Pair<IFile, IFile> resources = Arrays.asList(selection.toArray())
			.stream()
			.filter(e -> e instanceof IResource)
			.map(e -> ((IResource) e))
			.collect(new ConditionalPairCollector<>(IFile.class, IFile.class, r -> "sre".equals(r.getFileExtension()),
					r -> "trc".equals(r.getFileExtension())));

		IFile sreFile = resources.getFirst();
		IFile traceFile = resources.getSecond();

		// Check that the selection was valid
		if (sreFile == null || traceFile == null) {
			throw new InvalidFileSelectionException(
					"Invalid selection for consistency checking: Make sure exactly one trace data file and one PDFA file is selected.");
		}

		// Transform SRE from file to equivalent {@link ProbabilisticAutomaton}
		ProbabilisticAutomata automaton;
		try {
			automaton = getAutomatonOfSreFile(sreFile);
		} catch (IOException | CoreException ioException) {
			throw new ProcessingException("Failed to open the SRE file " + sreFile.getFullPath()
				.toString() + ".");
		} catch (SREParseException e1) {
			throw new ProcessingException(e1.getInternalException()
				.getMessage());
		}

		// check if the user wants to keep the intermediate automaton
		if (keepIntermediateAutomatonCopy) {
			IPath intermediateAutomatonPath = sreFile.getFullPath()
				.removeFileExtension()
				.addFileExtension(IOUtils.getTimestring())
				.addFileExtension("pdfa");
			ProbabilisticAutomatonIOUtils.saveAutomatonToFileUI(automaton, intermediateAutomatonPath, false);
		}

		// Build input object
		ConsistencyCheckInput input = new ConsistencyCheckInput();

		input.setAutomataPath(sreFile.getFullPath());
		input.setAutomaton(automaton);

		input.setTraceDatasetPath(traceFile.getFullPath());
		input.setTraceDataset(IOUtils.getTraceDatasetFromURI(traceFile.getLocationURI()));

		return input;
	}

	/**
	 * Helper function to trigger all required transformations to retrieve a 
	 * {@link ProbabilisticAutomata} from the given SRE file.
	 */
	private ProbabilisticAutomata getAutomatonOfSreFile(IFile sreFile)
			throws IOException, CoreException, SREParseException {
		String sreString = IOUtils.getStringFromInputStream(sreFile.getContents());
		SRE2ProbabilisticAutomatonTransformation t = new SRE2ProbabilisticAutomatonTransformationImpl();
		ProbabilisticAutomata automaton = t.transform(sreString);

		return automaton;
	}
}
