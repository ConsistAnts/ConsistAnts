package de.hu_berlin.cchecker.ui.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;

import de.hu_berlin.cchecker.core.checking.report.ConsistencyReport;
import de.hu_berlin.cchecker.ui.CCheckerUiPlugin;
import de.hu_berlin.cchecker.ui.util.exceptions.InvalidConsistencyReportFileException;

/**
 * Utility class for reading and writing {@link ConsistencyReport} models in
 * an Eclipse workspace.
 */
public class ConsistencyReportIOUtils {

	/**
	 * Attempts to parse a {@link ConsistencyReport} from a file with the given
	 * IPath and return the report. If parsing fails, this will throw an
	 * {@link InvalidConsistencyReportFileException}.
	 * 
	 * @param reportPath
	 *            The path of the report file to load
	 * @param forceReload
	 *            Specifies whether a re-load from the file should be forced.
	 *            Otherwise a cached version of the report may be returned.
	 * 
	 * @throws InvalidConsistencyReportFileException
	 */
	public static ConsistencyReport loadConsistencyReport(IPath reportPath, boolean forceReload)
			throws InvalidConsistencyReportFileException {
		IPath resourcePath = reportPath;
		ResourceSet rs = CCheckerUiPlugin.getInstance().getWorkspaceResourceSet();
		try {
			Resource resource = rs.getResource(URI.createPlatformResourceURI(resourcePath.toString(), true), true);

			if (forceReload && resource.isLoaded()) {
				resource.unload();
				resource.load(null);
			}

			return (ConsistencyReport) resource.getContents().get(0);
		} catch (Exception e) {
			throw new InvalidConsistencyReportFileException(
					"Cannot parse a Consistency Report from " + resourcePath.toFile().getName() + ".",
					resourcePath.toFile().getName());
		}

	}

	/**
	 * Creates a new resource for the given {@link ConsistencyReport} at the
	 * given {@link IPath}, saves the new file and refreshes the containing
	 * {@link IContainer} in Eclipse.
	 * 
	 * This method can only be run from the main thread. If you're not on the main thread
	 * use {@link #saveReportToFile(ConsistencyReport, IPath)} instead.
	 * 
	 * @param report
	 *            The report to write to disk
	 * @param reportFilePath
	 *            The path to write the report to
	 * @param openInEditor
	 *            Specifies whether the file should be opened in an editor after
	 *            saving.
	 */
	public static void saveConsistencyReport(ConsistencyReport report, IPath reportFilePath,
			boolean openInEditor) {
		// Create a new resource, add the report to its contents, then save
		// the file.
		try {
			saveReportToFile(report, reportFilePath);
		} catch (WrappedException | IOException e) {
			ErrorMessages.showConsistencyReportFileCannotBeOpenedForWriting(reportFilePath.toString());
		}

		IContainer parentFolder = IOUtils.getContainerInWorkspace(ResourcesPlugin.getWorkspace().getRoot(),
				reportFilePath.removeLastSegments(1));

		ProgressMonitorDialog progressDialog = new ProgressMonitorDialog(Display.getCurrent().getActiveShell());

		// Refresh the workspace so Eclipse automatically shows the new file.
		try {
			progressDialog.run(false, false, new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					monitor.beginTask("Refreshing workspace", 1);
					try {
						parentFolder.refreshLocal(1, monitor);
					} catch (CoreException e) {
						throw new InvocationTargetException(e);
					}
				}
			});
		} catch (InvocationTargetException | InterruptedException e1) {
			e1.printStackTrace();
		}

		if (openInEditor) {
			IFile reportFile = parentFolder.getFile(new Path(reportFilePath.lastSegment()));
			// Opens up the new report file in a new tab in the editor.
			if (null != reportFile) {
				IOUtils.openFileInTextEditor(reportFile);
			}
		}
	}

	/**
	 * Saves the given Consistency Report to the given path.
	 * 
	 * This method can be run from a background thread.
	 * 
	 * @param report
	 *            The report to save.
	 * @param reportFilePath
	 *            The path to save it to.
	 * 
	 * @throws IOException
	 *             if saving fails
	 */
	public static void saveReportToFile(ConsistencyReport report, IPath reportFilePath)
			throws IOException {
		ResourceSet workspaceRS = CCheckerUiPlugin.getInstance().getWorkspaceResourceSet();
		Resource reportResource = workspaceRS.createResource(URI.createURI(reportFilePath.toString(), true));

		// Add model to resource and save
		reportResource.getContents().clear();
		reportResource.getContents().add(report);
		reportResource.save(null);
	}

	private ConsistencyReportIOUtils() {
		// Make this class non-instantiable
	}

}
