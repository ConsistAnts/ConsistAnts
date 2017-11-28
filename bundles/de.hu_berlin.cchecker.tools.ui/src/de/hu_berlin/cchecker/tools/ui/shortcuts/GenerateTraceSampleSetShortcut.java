package de.hu_berlin.cchecker.tools.ui.shortcuts;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.tools.ui.dialogs.SampleSetGeneratorDialog;
import de.hu_berlin.cchecker.ui.util.ErrorMessages;
import de.hu_berlin.cchecker.ui.util.ProbabilisticAutomatonIOUtils;
import de.hu_berlin.cchecker.ui.util.exceptions.InvalidProbabilisticAutomatonFileException;

/**
 * A shortcut to open the dialog to generate a sample set for a given
 * {@link ProbabilisticAutomata} file.
 */
public class GenerateTraceSampleSetShortcut implements ILaunchShortcut {

	@Override
	/**
	 * Launches sample set generation from a navigator selection.
	 */
	public void launch(ISelection selection, String mode) {
		if (selection instanceof StructuredSelection) {
			Object firstElement = ((StructuredSelection) selection).getFirstElement();

			if (firstElement instanceof IResource) {
				IResource selectedResource = (IResource) firstElement;

				try {
					ProbabilisticAutomata a = ProbabilisticAutomatonIOUtils
						.getProbabilisticAutomatonFromPath(selectedResource.getFullPath(), true);

					IPath dataSetPath = selectedResource.getFullPath()
						.removeFileExtension()
						.addFileExtension("gen")
						.addFileExtension("trc");
					
					Display.getCurrent().asyncExec(() -> openDialog(a, dataSetPath));
				} catch (InvalidProbabilisticAutomatonFileException e) {
					ErrorMessages.showCustomErrorMessage("Failed to read automaton from file.");
					return;
				}
			}
		}
	}

	public void openDialog(ProbabilisticAutomata automaton, IPath traceDataSetPath) {
		IShellProvider shellProvider = PlatformUI.getWorkbench().getModalDialogShellProvider();
		SampleSetGeneratorDialog dialog = new SampleSetGeneratorDialog(shellProvider, automaton, traceDataSetPath);

		dialog.open();
	}

	@Override
	/**
	 * Launches the Generate Sample Set from an editor. 
	 */
	public void launch(IEditorPart editor, String mode) {
		// TODO implement this
	}
}
