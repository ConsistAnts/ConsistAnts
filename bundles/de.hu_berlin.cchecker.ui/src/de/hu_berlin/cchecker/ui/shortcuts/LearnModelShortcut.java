package de.hu_berlin.cchecker.ui.shortcuts;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;

import de.hu_berlin.cchecker.core.learning.LearningAlgorithm;
import de.hu_berlin.cchecker.core.learning.alergia.Alergia;
import de.hu_berlin.cchecker.core.models.ModelSetup;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.traces.TraceDataset;
import de.hu_berlin.cchecker.ui.util.ErrorMessages;
import de.hu_berlin.cchecker.ui.util.IOUtils;
import de.hu_berlin.cchecker.ui.util.ProbabilisticAutomatonIOUtils;
import de.hu_berlin.cchecker.ui.util.exceptions.InvalidTraceDatasetFileException;

/**
 * This is the launch shortcut for learning a model from a trace dataset.
 * (Run As > Learn Model From Traces).
 * @author Linus
 *
 */
public class LearnModelShortcut implements ILaunchShortcut {

	@Override
	/**
	 * Launches learning a model from a trace data set by selecting files and then selecting Run As. 
	 * This will validate that the given selection contains exactly one parseable .trc file, 
	 * then initiate the Alergia algorithm on that {@link TraceDataset} and then save the resulting automaton in a new file.
	 * If any of the validation requirements aren't met, an error dialog will be shown.
	 */
	public void launch(ISelection selection, String mode) {
		if (selection instanceof StructuredSelection) {
			Object firstElement = ((StructuredSelection) selection).getFirstElement();
			
			if (firstElement instanceof IResource) {
				IResource selectedResource = (IResource) firstElement;
				TraceDataset traceDataset = null;
				try {
					traceDataset = IOUtils.getTraceDatasetFromURI(selectedResource.getLocationURI());
				} catch (InvalidTraceDatasetFileException e) {
					ErrorMessages.showInvalidTraceDatasetFileErrorMessage(e.getFileName());
					return;
				}
				
				// Learn an automata using Alergia
				LearningAlgorithm algorithm = new Alergia();
				ProbabilisticAutomata learnedModel = algorithm.learnModel(traceDataset);
				
				// Create a path for the new automata file
				IContainer parentFolder = selectedResource.getParent();
				IPath parentFolderPath = parentFolder.getFullPath();
				IPath resourceFilename = new Path(selectedResource.getName()).removeFileExtension();
				
				IPath automataFilePath = parentFolderPath.append(resourceFilename)
						.addFileExtension(ModelSetup.PROBABILISTIC_AUTOMATA_FILE_EXTENSION);
				
				// Finally save the learned automaton
				ProbabilisticAutomatonIOUtils.saveAutomatonToFileUI(learnedModel, automataFilePath, true);
			}
		}
	}
	

	@Override
	/**
	 * Launches Model Learning from a trace data set by having the trace file open in an editor
	 * and then selecting Run.
	 */
	public void launch(IEditorPart editor, String mode) {
		// TODO implement this
	}
}