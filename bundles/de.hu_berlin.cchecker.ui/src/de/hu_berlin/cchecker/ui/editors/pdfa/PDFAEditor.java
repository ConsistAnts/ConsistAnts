package de.hu_berlin.cchecker.ui.editors.pdfa;

import java.io.File;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;

/**
 * This is the editor for our pdfa models. A PDFAEditor has multiple pages: One page 
 * for a read-only graphical view of the pdfa model, and one simple plaintext editor.
 */
public class PDFAEditor extends MultiPageEditorPart {
	
	private PDFAVisualView visualView;
	private TextEditor plaintextEditor;
	private File pdfaModelFile;

	@Override
	protected void createPages() {
		pdfaModelFile = ((FileEditorInput) this.getEditorInput()).getPath().toFile();
		this.setPartName(pdfaModelFile.getName());
		visualView = new PDFAVisualView(pdfaModelFile);
		plaintextEditor = new TextEditor();
		try {
			this.addPage(0, visualView, this.getEditorInput());
			setPageText(0, "Visual");
			this.addPage(1, (IEditorPart) plaintextEditor, this.getEditorInput());
			setPageText(1, "Plaintext");
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// We save the contents of plaintextEditor, which is page 1.
		this.getEditor(1).doSave(monitor);
		this.visualView.refreshGraph();
	}

	@Override
	public void doSaveAs() {
		// We save the contents of plaintextEditor, which is page 1.
		IEditorPart editor = this.getEditor(1);
		editor.doSaveAs();
		setPageText(1, editor.getTitle());
		setInput(editor.getEditorInput());
		this.visualView.refreshGraph();
	}

	@Override
	public boolean isSaveAsAllowed() {
		return plaintextEditor == null ? false : plaintextEditor.isSaveAsAllowed();
	}
	
	@Override
	public void setFocus() {
		this.visualView.setFocus();
	}

}