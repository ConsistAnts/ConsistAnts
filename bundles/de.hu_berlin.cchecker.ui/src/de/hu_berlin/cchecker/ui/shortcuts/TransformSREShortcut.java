package de.hu_berlin.cchecker.ui.shortcuts;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.transformations.sre.SRE2DTMCTransformation.SREParseException;
import de.hu_berlin.cchecker.core.transformations.sre.SRE2ProbabilisticAutomatonTransformation;
import de.hu_berlin.cchecker.core.transformations.sre.SRE2ProbabilisticAutomatonTransformationImpl;
import de.hu_berlin.cchecker.ui.util.ErrorMessages;
import de.hu_berlin.cchecker.ui.util.IOUtils;
import de.hu_berlin.cchecker.ui.util.ProbabilisticAutomatonIOUtils;

/**
 * Launch shortcut for transforming SRE files to PDFA files. (Run As > Transform
 * to Probabilistic Automaton).
 *
 */
public class TransformSREShortcut implements ILaunchShortcut {

	@Override
	/**
	 * Performs the transformation from SRE to PA.
	 */
	public void launch(ISelection selection, String mode) {
		if (selection instanceof StructuredSelection) {
			Object firstElement = ((StructuredSelection) selection).getFirstElement();

			if (firstElement instanceof IFile) {
				IFile selectedFile = (IFile) firstElement;
				performSreTransformation(selectedFile);
			} else {
				ErrorMessages.showInvalidSelectionSRETransformation();
			}
		} else {
			ErrorMessages.showInvalidSelectionSRETransformation();
		}
	}

	/**
	 * Performs the SRE-to-PDFA transformation by reading the SRE from the given
	 * file.
	 */
	private void performSreTransformation(IFile sreFile) {
		// try to read the SRE from the given file
		String sreString;
		try {
			sreString = IOUtils.getStringFromInputStream(sreFile.getContents());
		} catch (IOException | CoreException e1) {
			ErrorMessages.showFailedToReadSREFile(sreFile.getFullPath()
				.toString());
			return;
		}

		IPath sreFilePath = sreFile.getFullPath();
		IPath sreAutomatonPath = sreFilePath.removeLastSegments(1)
			.append(sreFilePath.removeFileExtension()
				.lastSegment())
			.addFileExtension("pdfa");

		// perform the transformation (includes parsing of the SRE from sreString)
		try {
			SRE2ProbabilisticAutomatonTransformation t = new SRE2ProbabilisticAutomatonTransformationImpl();
			ProbabilisticAutomata automaton = t.transform(sreString);
			
			// finally save the automaton
			ProbabilisticAutomatonIOUtils.saveAutomatonToFileUI(automaton, sreAutomatonPath, true);

		} catch (SREParseException e) {
			ErrorMessages.showFailedToParseSREFile(sreFile.getFullPath()
				.toString());
		}
	}

	@Override
	/**
	 * Does nothing for now.
	 */
	public void launch(IEditorPart editor, String mode) {
		// TODO implement this
		ErrorMessages.showUnsupportedOperationError();
	}
}