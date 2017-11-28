package de.hu_berlin.cchecker.ui.util;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.traces.TraceDataset;

/**
 * Utility class to show error message dialogs.
 */
public class ErrorMessages {

	/**
	 * Opens up a {@link MessageDialog} showing the user an error message that the current selection is invalid
	 * for running a consistency check (for example, two .pdfa files).
	 */
	public static void showInvalidFileSelectionConsistencyCheckErrorMessage() {
		MessageDialog.openError(getCurrentShell(), "Invalid Selection for Consistency Checking", 
				"Consistency checking cannot be performed on the current selection. "
						+ "Please select exactly one .trc and exactly one .pdfa file and then try again.");
	}

	/**
	 * Opens up a {@link MessageDialog} informing the user that the consistency check could not be performed.
	 */
	public static void showFailedToPerformConsistencyCheck() {
		MessageDialog.openError(getCurrentShell(), "Failed to perform consistency check", 
				"The consistency check could not be performed.");
	}

	/**
	 * Opens up a {@link MessageDialog} showing the user an error message that a {@link TraceDataset}
	 * cannot be parsed from the given fileName, even though it should be possible.
	 */
	public static void showInvalidTraceDatasetFileErrorMessage(String fileName) {
		MessageDialog.openError(getCurrentShell(), "Cannot Read TraceDataset",
				"Unable to parse a TraceDataset from " + fileName + ".");
	}

	/**
	 * Opens up a {@link MessageDialog} showing the user an error message that a {@link ProbabilisticAutomata}
	 * cannot be parsed from the given fileName, even though it should be possible.
	 */
	public static void showInvalidProbabilisticAutomatonFileErrorMessage(String fileName) {
		MessageDialog.openError(getCurrentShell(), "Cannot Read ProbabilisticAutomaton",
				"Unable to parse a ProbabilisticAutomaton from " + fileName + ".");
	}

	/**
	 * Opens up a {@link MessageDialog} showing the user an error message that the processing
	 * of an input file failed. Also the given details is shown.
	 */
	public static void showProcessingFailedMessage(String details) {
		MessageDialog.openError(getCurrentShell(), "Failed to process input files",
				"An error occured while processing the input files:" + details);
	}
	
	/**
	 * Opens up a generic {@link MessageDialog} showing the user the given error message.
	 */
	public static void showCustomErrorMessage(String message) {
		MessageDialog.openError(getCurrentShell(), "Error",
				message);
	}

	/**
	 * Opens up a {@link MessageDialog} showing the user an error message that a {@link ProbabilisticAutomata}
	 * file cannot be opened for writing.
	 */
	public static void showProbabilisticAutomataFileCannotBeOpenedForWriting(String path) {
		MessageDialog.openError(getCurrentShell(), "Cannot write ProbabilisticAutomaton",
				"Unable to open the the resource at " + path + ".");
	}
	
	public static void showConsistencyReportFileCannotBeOpenedForWriting(String path) {
		MessageDialog.openError(getCurrentShell(), "Cannot write Consistency Report",
				"Unable to open the the resource at " + path + ".");
	}

	public static void showFailedToRefreshWorkspace() {
		MessageDialog.openError(getCurrentShell(), "Failed to refresh",
				"Failed to refresh the workspace.");
	}

	public static void showFailedToReadSREFile(String workspacePath) {
		MessageDialog.openError(getCurrentShell(), "Failed to read SRE file",
				"Failed to read Stochastic Regular Expression file: " + workspacePath);
	}

	public static void showFailedToParseSREFile(String workspacePath) {
		MessageDialog.openError(getCurrentShell(), "Failed to parse SRE",
				"Failed to parse the Stochastic Regular Expression in file " + workspacePath);
	}
	
	public static void showFailedToWriteSREFile(String workspacePath) {
		MessageDialog.openError(getCurrentShell(), "Failed to write SRE file",
				"Failed to open for writing: " + workspacePath);
	}

	public static void showInvalidSelectionSRETransformation() {
		MessageDialog.openError(getCurrentShell(), "Invalid selection",
				"To perform a SRE-to-PDFA-Transformation select exactly one .sre file.");
	}
	
	public static void showUnsupportedOperationError() {
		MessageDialog.openError(getCurrentShell(), "Unsupported Operation",
				"This operation is not yet supported.");
	}

	/**
	 * Gets the currently active {@link Shell} from the Eclipse workbench. May return null.
	 */
	private static Shell getCurrentShell() {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window == null) {
			IWorkbenchWindow[] windows = PlatformUI.getWorkbench().getWorkbenchWindows();
			if (windows.length > 0) {
				return windows[0].getShell();
			}
		} else {
			return window.getShell();
		}
		return null;
	}

}
