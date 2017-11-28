package de.hu_berlin.cchecker.ui.editors.report;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;

import de.hu_berlin.cchecker.core.checking.report.ConsistencyReport;
import de.hu_berlin.cchecker.ui.util.ConsistencyReportIOUtils;
import de.hu_berlin.cchecker.ui.util.exceptions.InvalidConsistencyReportFileException;

/**
 * A viewer (Eclipse Editor) for consistency report files.
 */
public class ReportViewer extends EditorPart {

	private ReportViewerForm form;

	@Override
	public void doSave(IProgressMonitor monitor) {
		// do nothing, this is just a viewer

	}

	@Override
	public void doSaveAs() {
		// do nothing, this is just a viewer

	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);

	}

	@Override
	public boolean isDirty() {
		// viewers can't be dirty
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {
		ConsistencyReport report = getReportFromInput();
		
		this.form = new ReportViewerForm();
		this.form.createPartControl(parent);

		this.form.setInput(report);
	}

	/**
	 * Extracts the consistency report from the {@link IEditorInput} of this viewer
	 * and returns it.
	 * 
	 * @throws IllegalArgumentException if the {@link #getEditorInput()} isn't a valid report file input.
	 */
	private ConsistencyReport getReportFromInput() throws IllegalArgumentException {
		IEditorInput input = this.getEditorInput();
		if (!(input instanceof FileEditorInput)) {
			throw new IllegalArgumentException("The editor input is not suitable for the Report Viewer.");
		}
		FileEditorInput fileInput = (FileEditorInput) input;

		this.setPartName(fileInput.getFile().getName());

		try {
			return ConsistencyReportIOUtils.loadConsistencyReport(fileInput.getFile().getFullPath(), true);
		} catch (InvalidConsistencyReportFileException e) {
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	public void setFocus() {
		form.setFocus();
	}

}
