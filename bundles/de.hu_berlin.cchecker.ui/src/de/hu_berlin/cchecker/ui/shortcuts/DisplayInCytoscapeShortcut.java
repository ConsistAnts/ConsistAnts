package de.hu_berlin.cchecker.ui.shortcuts;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.resources.IResource;
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;
import org.eclipse.ui.internal.browser.WorkbenchBrowserSupport;

import de.hu_berlin.cchecker.core.cytoscape.CytoscapeUtils;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.ui.util.ProbabilisticAutomatonIOUtils;
import de.hu_berlin.cchecker.ui.util.exceptions.InvalidProbabilisticAutomatonFileException;

/**
 * This is a launch shortcut to view a visual representation of PA inside an Eclipse browser window. 
 *
 */
@SuppressWarnings("restriction")
public class DisplayInCytoscapeShortcut implements ILaunchShortcut {

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
				ProbabilisticAutomata a = null;
				try {
					a = ProbabilisticAutomatonIOUtils.getProbabilisticAutomatonFromPath(selectedResource.getFullPath(), true);
				} catch (InvalidProbabilisticAutomatonFileException e) {
					System.out.println("Failed to open automaton in cytoscape");
					e.printStackTrace();
					return;
				}
				
				String filename = selectedResource.getName();
				String indexLocation = CytoscapeUtils.saveCytoscapeScriptAndGetIndexLocation(a);
				
				try {
					int style = IWorkbenchBrowserSupport.AS_EDITOR | IWorkbenchBrowserSupport.STATUS;
					IWebBrowser browser = WorkbenchBrowserSupport.getInstance().createBrowser(style, "viewer-" + filename, filename, "");
					browser.openURL(new URL("file://" + indexLocation));
				} catch (PartInitException | MalformedURLException e) {
					e.printStackTrace();
				}
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
