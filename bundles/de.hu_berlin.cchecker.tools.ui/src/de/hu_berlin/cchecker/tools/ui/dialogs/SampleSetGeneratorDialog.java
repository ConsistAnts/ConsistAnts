package de.hu_berlin.cchecker.tools.ui.dialogs;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.tools.traces.generation.SampleSetEstimator;
import de.hu_berlin.cchecker.tools.traces.generation.SampleSetGenerator;
import de.hu_berlin.cchecker.tools.traces.generation.SampleSetGenerator.GeneratorResult;
import de.hu_berlin.cchecker.tools.traces.generation.SampleSetGenerator.RoundingStrategy;
import de.hu_berlin.cchecker.tools.traces.generation.TraceDatasetWriter;
import de.hu_berlin.cchecker.tools.ui.dialogs.SampleSetGeneratorDialog.SampleSetViewModel.MODE;
import de.hu_berlin.cchecker.ui.util.ErrorMessages;
import de.hu_berlin.cchecker.ui.util.IOUtils;

/**
 * A dialog to configure the generation of a sample set based on a given
 * {@link ProbabilisticAutomata}.
 */
public class SampleSetGeneratorDialog extends Dialog {
	// Dialog widgets
	private Button checkRepresentationRate;
	private Spinner representationRateSpinner;
	private Spinner numberOfTracesSpinner;
	private Text dataSetPathText;
	private Text previewText;
	private Label numberOfTracesLabel;
	private Label representationRateLabel;
	private Button previewButton;

	// Dialog models
	
	/** Form model of the dialog, kept in sync with all user inputs */
	private SampleSetViewModel model;
	/** GeneratorResult model, updated whenever the user presses the Preview button */
	private GeneratorResult generatorResult;

	/**
	 * The view model of this dialog.
	 */
	static class SampleSetViewModel {
		String traceDataSetPath;
		int numberOfTraces;
		int representationRate;
		final ProbabilisticAutomata automaton;

		MODE mode = MODE.REPRESENTATION;

		/**
		 * Indicates a dirty state of the dialog (preview and #generatorResult out-of-sync with #model)
		 */
		boolean dirty;

		/** Specifies the generator mode. */
		enum MODE {
			/** Generate enough traces to reach the given representation rate */
			REPRESENTATION,
			/** Generate the given number of traces */
			NUMBER
		}

		/** Instantiates a new sample set view model with the given initial values */
		public SampleSetViewModel(ProbabilisticAutomata automaton, String traceDataSetPath, int numberOfTraces,
				int representationRate) {
			super();
			this.automaton = automaton;
			this.traceDataSetPath = traceDataSetPath;
			this.numberOfTraces = numberOfTraces;
			this.representationRate = representationRate;
		}
	}

	/**
	 * Instantiates a new sample set generator dialog for the given automaton.
	 * 
	 * @param parentShell
	 *            The shell provider for the dialog.
	 * @param automaton
	 *            The automaton of the dialog
	 * @param traceDataSetPath
	 *            The initial sample set output path to use
	 */
	public SampleSetGeneratorDialog(IShellProvider parentShell, ProbabilisticAutomata automaton,
			IPath traceDataSetPath) {
		super(parentShell);
		this.model = new SampleSetViewModel(automaton, traceDataSetPath.toString(), 20, 80);
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);

		newShell.setText("Generate Sample Set from Automaton");
		initializeShellBounds(newShell);
	}

	/**
	 * Initialises the shell bounds.
	 * 
	 * This includes the general dimensions and centering on the screen.
	 */
	private void initializeShellBounds(Shell shell) {
		Rectangle displayBounds = Display.getCurrent().getBounds();
		final int width = 1000;
		final int height = 600;

		shell.setMinimumSize(width, height);
		shell.setBounds((int) (((double) displayBounds.width - width) / 2.0),
				(int) (((double) displayBounds.height - height) / 2.0), width, height);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);

		// create two pane split
		Composite split = new Composite(container, SWT.NONE);
		split.setLayout(GridLayoutFactory.fillDefaults().numColumns(2).create());
		split.setLayoutData(GridDataFactory.fillDefaults().grab(true, true).create());

		// left pane for parameter form
		Composite grid = new Composite(split, SWT.NONE);
		grid.setLayout(GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).create());
		grid.setLayoutData(GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).create());

		Button checkNumberOfTraces = new Button(grid, SWT.RADIO);
		checkNumberOfTraces.setText("Specify number of traces");
		checkNumberOfTraces.setSelection(true);

		numberOfTracesSpinner = new Spinner(grid, SWT.NONE);
		numberOfTracesSpinner.setMinimum(1);
		numberOfTracesSpinner.setMaximum(Integer.MAX_VALUE);
		numberOfTracesSpinner.setSelection(this.model.numberOfTraces);

		checkRepresentationRate = new Button(grid, SWT.RADIO);
		checkRepresentationRate.setText("Specify minimum representation rate (percent):");
		checkRepresentationRate.setSelection(false);

		representationRateSpinner = new Spinner(grid, SWT.NONE);
		representationRateSpinner.setMinimum(0);
		representationRateSpinner.setMaximum(100);
		representationRateSpinner.setSelection(this.model.representationRate);

		Label label = new Label(grid, SWT.NONE);
		label.setText("Output file:");

		dataSetPathText = new Text(grid, SWT.BORDER);
		dataSetPathText.setText(this.model.traceDataSetPath);
		dataSetPathText.setLayoutData(
				GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).hint(new Point(140, -1)).create());

		// register input listeners
		SelectionAdapter checkboxChangedListener = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				dialogChanged();
			}
		};

		checkNumberOfTraces.addSelectionListener(checkboxChangedListener);
		checkRepresentationRate.addSelectionListener(checkboxChangedListener);

		ModifyListener modifyListener = e -> dialogChanged();

		representationRateSpinner.addModifyListener(modifyListener);
		numberOfTracesSpinner.addModifyListener(modifyListener);
		dataSetPathText.addModifyListener(modifyListener);

		// right preview pane
		Composite previewPane = new Composite(split, SWT.NONE);
		previewPane.setLayout(GridLayoutFactory.swtDefaults().numColumns(3).equalWidth(false).create());
		previewPane.setLayoutData(GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).create());

		// preview text
		previewText = new Text(previewPane, SWT.BORDER | SWT.V_SCROLL);
		previewText.setLayoutData(
				GridDataFactory.swtDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).span(3, 1).create());

		previewButton = new Button(previewPane, SWT.PUSH);
		previewButton.setText("Update Preview");
		previewButton.setLayoutData(GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.CENTER).create());

		previewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// refresh preview on button click
				refreshPreview();
			}
		});

		numberOfTracesLabel = new Label(previewPane, SWT.FILL);
		numberOfTracesLabel.setText("Number of Traces: -");
		numberOfTracesLabel
				.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.CENTER).create());

		representationRateLabel = new Label(previewPane, SWT.FILL);
		representationRateLabel.setText("Representation Rate: -");
		representationRateLabel
				.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.CENTER).create());

		dialogChanged();

		return container;
	}

	private void refreshPreview() {

		ProgressMonitorDialog progressDialogAlgorithm = new ProgressMonitorDialog(this.getParentShell());
		IRunnableWithProgress generateTracesRunnable = this::generateTraces;

		previewButton.setEnabled(false);

		try {
			progressDialogAlgorithm.run(true, true, generateTracesRunnable);
		} catch (InvocationTargetException e) {
			ErrorMessages.showCustomErrorMessage("Failed to create preview for the given parameters");
			generatorResult = null;
		} catch (InterruptedException e) {
			// If cancelled, set the result to null. A preview refresh (see finally) will set the UI to the empty state.
			generatorResult = null;
		} finally {
			previewButton.setEnabled(true);
			updatePreview();
		}
	}

	/**
	 * Updates the sample set preview with whatever the 
	 * current generatorResult is set to.
	 * 
	 * If <code>null</code> the preview is cleared and shows an 
	 * empty state. Otherwise basic information and a preview of 
	 * the sample set are displayed.
	 */
	private void updatePreview() {
		if (null == generatorResult) {
			previewText.setText("");
			representationRateLabel.setText("-");
			numberOfTracesLabel.setText("-");
		} else {
			previewText.setText(generatorResult.getDataSet().toString());
			representationRateLabel.setText(
					String.format("Representation Rate: %d%%", (int) (generatorResult.getRepresentationRate() * 100)));
			String numberOfTracesString = String.format("Number Of Traces: %d", generatorResult.getNumberOfTraces());
			numberOfTracesLabel.setText(numberOfTracesString);
		}
		this.model.dirty = false;
	}

	/**
	 * This method has to be invoced whenever the user
	 * makes changes to any of the inputs.
	 */
	private void dialogChanged() {
		if (checkRepresentationRate.getSelection()) {
			representationRateSpinner.setEnabled(true);
			numberOfTracesSpinner.setEnabled(false);
			this.model.mode = MODE.REPRESENTATION;
		} else {
			representationRateSpinner.setEnabled(false);
			numberOfTracesSpinner.setEnabled(true);
			this.model.mode = MODE.NUMBER;
		}
	
		this.model.numberOfTraces = numberOfTracesSpinner.getSelection();
		this.model.representationRate = representationRateSpinner.getSelection();
		this.model.traceDataSetPath = dataSetPathText.getText();
	
		this.model.dirty = true;
	}

	/** Returns true if user chose Number Of Traces based generation */
	private boolean isNumberMode() {
		return this.model.mode == MODE.NUMBER;
	}

	/** Returns true if user chose Representation Rate based generation */ 
	private boolean isRepresentationMode() {
		return this.model.mode == MODE.REPRESENTATION;
	}

	@Override
	protected Button createButton(Composite parent, int id, String label, boolean defaultButton) {
		if (id == IDialogConstants.OK_ID) {
			// Change OK label to 'Generate'
			return super.createButton(parent, id, "Generate", defaultButton);
		} else {
			return super.createButton(parent, id, label, defaultButton);
		}
	}

	@Override
	protected void okPressed() {
		ProgressMonitorDialog progressDialogAlgorithm = new ProgressMonitorDialog(this.getParentShell());
	
		// get workspace file of output location
		IFile workspaceFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(model.traceDataSetPath));
	
		IRunnableWithProgress algorithmRunnable = new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				// if model has changes that aren't reflected in the current generator result
				if (model.dirty) {
					// re-generate sample set
					SampleSetGeneratorDialog.this.generateTraces(monitor);
				}
				// save generator results
				saveDataSetResult(SampleSetGeneratorDialog.this.generatorResult, workspaceFile, monitor);
			}
		};
	
		try {
			// run the save operation
			progressDialogAlgorithm.run(true, true, algorithmRunnable);
			// close the dialog
			this.close();
			// open the new trace file in an editor
			IOUtils.openFileInTextEditor(workspaceFile);
		} catch (InvocationTargetException e) {
			ErrorMessages.showCustomErrorMessage("Failed to generate sample set: " + e.toString());
		} catch (InterruptedException e) {
			// Operation cancelled, don't do anything.
			// The dialog will be left open, for the user to make further changes or press Cancel
		}
	}

	/**
	 * Generates the traces for the current dialog view model.
	 */
	private void generateTraces(IProgressMonitor monitor) {
		int numberOfTraces = model.numberOfTraces; // default to specified number of traces
		if (isRepresentationMode()) { // if representation mode then compute required number of traces
			IProgressMonitor subMonitor = SubMonitor.convert(monitor, 2);
			subMonitor.setTaskName("Estimating number of traces...");
	
			SampleSetEstimator estimator = new SampleSetEstimator(model.automaton);
			estimator.setMonitor(subMonitor);
	
			double relativeRepresentationRate = ((double) model.representationRate) / 100.0;
			numberOfTraces = estimator.estimateSampleSizeForRepresentationRate(relativeRepresentationRate);
	
			subMonitor.worked(1);
		}
	
		SampleSetGenerator generator = new SampleSetGenerator(model.automaton, numberOfTraces, RoundingStrategy.UP);
	
		if (isNumberMode()) {
			generator.setRepresentationThreshold(0.0);
		}
	
		generator.setMonitor(monitor);
		generatorResult = generator.generatePossibleSampleSet();
	}

	/**
	 * Saves the given {@link GeneratorResult} to the given workspace location.
	 */
	private void saveDataSetResult(GeneratorResult result, IFile workspaceFile, IProgressMonitor monitor)
			throws InvocationTargetException {
		// save trace data set to disk
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			TraceDatasetWriter.writeTraceDatasetToFile(result.getDataSet(), outputStream);

			try (ByteArrayInputStream byteStream = new ByteArrayInputStream(outputStream.toByteArray())) {
				workspaceFile.create(byteStream, true, monitor);
			}
		} catch (IOException | CoreException e) {
			throw new InvocationTargetException(e, e.getMessage());
		}
	}

}
