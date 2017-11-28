package de.hu_berlin.cchecker.ui.shortcuts;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;

import de.hu_berlin.cchecker.core.learning.blockwise.BlockwiseLeftAlignedLearningAlgorithm;
import de.hu_berlin.cchecker.core.models.traces.TraceDataset;
import de.hu_berlin.cchecker.ui.util.ErrorMessages;
import de.hu_berlin.cchecker.ui.util.IOUtils;
import de.hu_berlin.cchecker.ui.util.exceptions.InvalidTraceDatasetFileException;

/**
 * This is the launch shortcut for learning a model from a trace dataset.
 * (Run As > Learn SRE From Traces).
 *
 */
public class LearnSREShortcut implements ILaunchShortcut {

	private static final String SRE_FILE_EXTENSION = "sre";


	@Override
	/**
	 * Launches SRE-learning for a given workspace selection.
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
				BlockwiseLeftAlignedLearningAlgorithm algorithm = new BlockwiseLeftAlignedLearningAlgorithm();
				String learnedSRE = algorithm.learnModel(traceDataset);
				
				// Create a path for the new automata file
				IContainer parentFolder = selectedResource.getParent();
				IPath parentFolderPath = parentFolder.getFullPath();
				IPath resourceFilename = new Path(selectedResource.getName()).removeFileExtension();
				
				IPath sreFilePath = parentFolderPath.append(resourceFilename)
						.addFileExtension(SRE_FILE_EXTENSION);
				
				// build the sre file path
				IFile workspaceSreFile = ResourcesPlugin.getWorkspace().getRoot().getFile(sreFilePath);
				File sreFile = new File(workspaceSreFile.getLocationURI());

				// make sure the file exists
				try {
					sreFile.createNewFile();
				} catch (IOException e) {
					ErrorMessages.showFailedToWriteSREFile(workspaceSreFile.getFullPath().toString());
					return;
				}
				
				// write sre string to disk
				try (FileWriter writer = new FileWriter(sreFile)) {
					writer.write(learnedSRE);
					writer.flush();
				} catch (IOException e) {
					ErrorMessages.showFailedToWriteSREFile(workspaceSreFile.getFullPath().toString());
					return;
				}
				
				// wait for a refresh of the parent folder
				IOUtils.waitForRefresh(parentFolder);
				
				// open new file in editor
				IOUtils.openFileInTextEditor(workspaceSreFile);
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