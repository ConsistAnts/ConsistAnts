package de.hu_berlin.cchecker.ui.editors.trace;

import java.io.File;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;

/**
 * This is the editor for our trace files. A TracesEditor has multiple pages: One page
 * for a read-only aggregate view of which traces occur how many times, and one simple
 * plaintext editor.
 *
 */
public class TracesEditor extends MultiPageEditorPart {
	
	// The individual pages of this editor.
	private TracesAggregateView aggregateView;
	private TextEditor plaintextEditor;
	
	@Override
	public void doSave(IProgressMonitor monitor) {
		// We save the contents of plaintextEditor, which is page 1.
		this.getEditor(1).doSave(monitor);
		aggregateView.refreshDataFromFile();
	}

	@Override
	public void doSaveAs() {
		// We save the contents of plaintextEditor, which is page 1.
		IEditorPart editor = this.getEditor(1);
		editor.doSaveAs();
		setPageText(1, editor.getTitle());
		setInput(editor.getEditorInput());
		aggregateView.refreshDataFromFile();
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);
	}

	@Override
	public boolean isSaveAsAllowed() {
		return plaintextEditor == null ? false : plaintextEditor.isSaveAsAllowed();
	}

	/**
	 * If we open the aggregated view, we refresh the actual file contents first.
	 */
	@Override
	protected void pageChange(int newPageIndex) {
		super.pageChange(newPageIndex);
		if (newPageIndex == 0) {
			aggregateView.refreshDataFromFile();
		}
	}
	
	@Override
	protected void createPages() {
		// Get the actual file from this editor's input.
		File traceDatasetFile = ((FileEditorInput) this.getEditorInput()).getPath().toFile();
		this.setPartName(traceDatasetFile.getName());
		// Parse it to a TraceDataset, then create the plaintext and aggregate pages.
		aggregateView = new TracesAggregateView(traceDatasetFile);
		plaintextEditor = new TextEditor();
		try {
			this.addPage(0, aggregateView, this.getEditorInput());
			setPageText(0, "Aggregated");
			this.addPage(1, (IEditorPart) plaintextEditor, this.getEditorInput());
			setPageText(1, "Plaintext");
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setFocus() {
		this.aggregateView.setFocus();
	}
	
}
