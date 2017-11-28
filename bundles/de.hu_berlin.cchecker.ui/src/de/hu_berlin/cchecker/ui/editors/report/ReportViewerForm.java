package de.hu_berlin.cchecker.ui.editors.report;

import java.text.SimpleDateFormat;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.ViewPart;

import de.hu_berlin.cchecker.core.checking.report.ConsistencyReport;
import de.hu_berlin.cchecker.ui.util.IOUtils;

/**
 * User Interface of the {@link ReportViewer} editor. 
 */
public class ReportViewerForm extends ViewPart {

	/* Date formatter to use to display dates on the UI */
	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss yyyy-MM-dd");

	//// Editor widgets
	private FormToolkit toolkit;
	private ConsistencyReport model;
	private FormText modelChecksumText;
	private Hyperlink modelFileText;
	private Hyperlink traceDataSetFileText;
	private FormText traceDataSetFileChecksumText;
	private FormText createdOnText;

	private ConsistencyResultSection resultsSection;
	private CounterExamplesSection counterExamplesSection;
	private FootprintMatrixSection footprintMatrixSection;

	/** A hyperlink listener that opens editors for workspace paths */
	private IHyperlinkListener workspaceLinkListener = new HyperlinkAdapter() {
		@Override
		public void linkActivated(HyperlinkEvent e) {
			Object objectHref = e.getHref();
			if (objectHref instanceof IPath) {
				IPath href = (IPath) objectHref;
				IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(href);
				if (file.exists()) {
					IOUtils.openFileInTextEditor(file);
				}
			}
		}
	};

	/* Main entry point for UI creation */
	@Override
	public void createPartControl(Composite parent) {
		// initialise the form toolkit
		toolkit = new FormToolkit(parent.getShell().getDisplay());

		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(GridLayoutFactory.fillDefaults().numColumns(1).create());

		ScrolledForm mainForm = toolkit.createScrolledForm(container);
		mainForm.setLayoutData(GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).create());
		mainForm.setText("Consistency Report");
		GridLayoutFactory.fillDefaults().numColumns(1).margins(5, 5).applyTo(mainForm.getBody());

		createGeneralInformationSection(mainForm);
		
		// create sash form for the table viewers
		SashForm tableSashForm = new SashForm(mainForm.getBody(), SWT.VERTICAL);
		toolkit.adapt(tableSashForm, true, true);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(tableSashForm);
		
		// create consistency result sections
		createConsistencyResultsSection(tableSashForm);
		
		// create counter example section
		counterExamplesSection = new CounterExamplesSection(toolkit);
		counterExamplesSection.createPartControl(tableSashForm);

		updateUI();
	}

	/**
	 * Creates the upper General Information section of the UI.
	 */
	private void createGeneralInformationSection(ScrolledForm mainForm) {
		Section section = toolkit.createSection(mainForm.getBody(), SWT.NONE);
		section.setText("General Information");
		GridDataFactory.fillDefaults().grab(true, false).applyTo(section);

		Composite client = new Composite(section, SWT.NONE);
		client.setLayout(new GridLayout());
		client.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_LIST_BACKGROUND));

		GridLayoutFactory.swtDefaults().numColumns(3).applyTo(client);

		toolkit.createLabel(client, "Model file:");
		modelFileText = createWorkpaceLinkText(client, new Path("/Model.pdfa"), "Model.pdfa");
		GridDataFactory.fillDefaults().grab(true, false).applyTo(modelFileText);
		modelChecksumText = createValueText(client, "-");

		toolkit.createLabel(client, "Trace Data Set file:");
		traceDataSetFileText = createWorkpaceLinkText(client, new Path("/RandomProject/tracedataset_task1.trc"),
				"/RandomProject/tracedataset_task1.trc");
		traceDataSetFileChecksumText = createValueText(client, "-");

		toolkit.createLabel(client, "Created On:");
		createdOnText = createValueText(client, "-");

		section.setClient(client);
	}

	/**
	 * Creates the Consistency Results section of the UI.
	 */
	private void createConsistencyResultsSection(Composite parent) {
		// initialise a sash form for the consistency table viewers
		SashForm parentSash = new SashForm(parent, SWT.HORIZONTAL);
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL)
			.applyTo(parentSash);
		toolkit.adapt(parentSash, true, true);
		
		resultsSection = new ConsistencyResultSection(toolkit);
		resultsSection.createPartControl(parentSash);
		resultsSection.addSelectionChangedListener(result -> footprintMatrixSection.setInput(result));
		
		footprintMatrixSection = new FootprintMatrixSection(toolkit);
		footprintMatrixSection.createPartControl(parentSash);
	}

	@Override
	public void dispose() {
		footprintMatrixSection.dispose();
		counterExamplesSection.dispose();
		resultsSection.dispose();
		
		super.dispose();
	}

	@Override
	public void setFocus() {
		resultsSection.setFocus();
	}

	/**
	 * Sets the input model to view in this {@link ReportViewerForm}.
	 */
	public void setInput(ConsistencyReport reportModel) {
		this.model = reportModel;
		updateUI();
	}

	/**
	 * Creates a {@link Hyperlink} that links to resources in the workspace.
	 * 
	 * Also @see #workspaceLinkListener.  
	 */
	private Hyperlink createWorkpaceLinkText(Composite parent, IPath path, String text) {
		Hyperlink link = toolkit.createHyperlink(parent, text, SWT.WRAP);
		link.setHref(path);
		link.addHyperlinkListener(workspaceLinkListener);
		return link;
	}

	/**
	 * Creates a read-only {@link FormText} with a default margin.
	 */
	private FormText createValueText(Composite parent, String text) {
		boolean parseTags = !text.isEmpty() && text.charAt(0) == '<';
		FormText formText = toolkit.createFormText(parent, false);
		formText.setText(text, parseTags, false);
	
		formText.marginHeight = 5;
		formText.marginWidth = 5;
	
		return formText;
	}

	/**
	 * Populates the UI from the {@link #model} field.
	 */
	private void updateUI() {
		// if model is null, do nothing
		if (null == this.model) {
			return;
		}
	
		// if the UI wasn't initialised yet, do nothing
		if (null == toolkit) {
			return;
		}
	
		// set model file general info
		Path modelPath = new Path(model.getModelPath());
		setModelFilePath(modelPath);
		modelChecksumText.setText(model.getModelChecksum(), false, false);
	
		// set trace data set file general info
		Path traceDataSetFilePath = new Path(model.getTraceDataSetPath());
		setTraceDataSetFilePath(traceDataSetFilePath);
		traceDataSetFileChecksumText.setText(model.getTraceDataSetChecksum(), false, false);
	
		// set and format created on date
		createdOnText.setText(dateFormatter.format(model.getCreatedOn()), false, false);
	
		// set input for sub-sections
		counterExamplesSection.setInput(model);
		resultsSection.setInput(model);
		footprintMatrixSection.updateViewer(model);
		
		// set initial consistency selection
		if (!model.getFootprintMatrices().isEmpty()) {
			resultsSection.setSelection(model.getFootprintMatrices().get(0));
		}
		
	}

	/**
	 * Sets the value of the controls that depend on the model file path.
	 */
	private void setModelFilePath(Path modelPath) {
		modelFileText.setText(modelPath.toString());
		modelFileText.setHref(modelPath);
	}

	/**
	 * Sets the value of the controls that depend on the trace data set file path.
	 */
	private void setTraceDataSetFilePath(Path modelPath) {
		traceDataSetFileText.setText(modelPath.toString());
		traceDataSetFileText.setHref(modelPath);
	}
}
