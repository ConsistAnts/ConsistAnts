package de.hu_berlin.cchecker.ui.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;

import javax.xml.bind.DatatypeConverter;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import de.hu_berlin.cchecker.core.models.traces.TraceDataset;
import de.hu_berlin.cchecker.core.models.traces.TraceDatasetParser;
import de.hu_berlin.cchecker.ui.util.exceptions.InvalidTraceDatasetFileException;

/**
 * A utility class for common use cases when interacting with files etc.
 *
 */
public class IOUtils {

	/**
	 * Attempts to parse a {@link TraceDataset} from a file with the given URI and
	 * return the trace dataset. If parsing fails, this will throw an
	 * {@link InvalidTraceDatasetFileException}.
	 * 
	 * @throws InvalidTraceDatasetFileException
	 */
	public static TraceDataset getTraceDatasetFromURI(java.net.URI tracesURI) throws InvalidTraceDatasetFileException {
		File tracesFile = new File(tracesURI);
		TraceDataset traceDataset = new TraceDatasetParser().parseTraceDatasetFromFile(tracesFile);
		if (traceDataset == null) {
			throw new InvalidTraceDatasetFileException("Cannot parse a TraceDataset from " + tracesFile.getName() + ".",
					tracesFile.getName());
		}
		return traceDataset;
	}

	public static String getHash(IFile file) throws IOException {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("Hash algorithm is unavailable");
		}
		md.update(Files.readAllBytes(Paths.get(file.getLocationURI())));
		return DatatypeConverter.printHexBinary(md.digest());
	}
	
	/**
	 * Opens the given {@link IFile} in the default text editor in the current
	 * workbench.
	 */
	public static void openFileInTextEditor(IFile file) {
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		try {
			IDE.openEditor(page, file, true);
		} catch (PartInitException e) {
			System.err.println("Failed to open editor for " + file.getFullPath());
		}
	}

	/**
	 * Returns the container of the given path.
	 * 
	 * In case of the path being top-level in a project, the {@link IProject} is
	 * returned.
	 */
	public static IContainer getContainerInWorkspace(IWorkspaceRoot root, IPath p) {
		if (p.segmentCount() == 1) {
			return root.getProject(p.toString());
		} else {
			return root.getFolder(p);
		}
	}

	/**
	 * Reads and returns the given {@link InputStream} as String.
	 */
	public static String getStringFromInputStream(InputStream is) throws IOException {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
			return br.lines().collect(Collectors.joining(System.lineSeparator()));
		}
	}

	/**
	 * This private constructor hides the default public empty constructor and
	 * should never be called. Doing so will throw a
	 * {@link UnsupportedOperationException}.
	 */
	private IOUtils() {
		throw new UnsupportedOperationException("Cannot instantiate IOUtils!");
	}

	/**
	 * Returns a string that represents the current time including year, month, day,
	 * hours, minutes and seconds.
	 */
	public static String getTimestring() {
		return new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
	}
	
	/**
	 * Waits for a workspace refresh of the given folder.
	 * 
	 * Shows a {@link ProgressMonitorDialog} while waiting.
	 * 
	 * @param parentFolder
	 *            The folder to refresh
	 * @throws InvocationTargetException
	 *             when the refresh fails
	 * @throws InterruptedException
	 *             when the refresh is cancelled
	 */
	public static void waitForRefresh(IContainer parentFolder) {
		// Create a progress monitor dialog for the workspace refresh
		ProgressMonitorDialog progressDialogRefresh = new ProgressMonitorDialog(Display.getCurrent().getActiveShell());
		progressDialogRefresh.open();

		try {
			progressDialogRefresh.run(false, false, new IRunnableWithProgress() {

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
		} catch (InvocationTargetException | InterruptedException e) {
			System.out.println("Failed to refresh " + parentFolder.getFullPath().toString());
		}
	}

}
