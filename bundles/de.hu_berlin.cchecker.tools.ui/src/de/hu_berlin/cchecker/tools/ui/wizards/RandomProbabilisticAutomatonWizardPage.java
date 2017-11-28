package de.hu_berlin.cchecker.tools.ui.wizards;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.tools.pdfa.generation.RandomProbabilisticAutomatonGenerator;
import de.hu_berlin.cchecker.tools.pdfa.generation.RandomProbabilisticAutomatonGenerator.Range;
import de.hu_berlin.cchecker.ui.util.IOUtils;

/**
 * Single wizard page for {@link RandomProbabilisticAutomatonWizard} that allows
 * the user to specify the parameters for the random generation of a new
 * {@link ProbabilisticAutomata}.
 * 
 * It also shows a textual preview of the generated
 * {@link ProbabilisticAutomata}.
 */

public class RandomProbabilisticAutomatonWizardPage extends WizardPage {

	/**
	 * A group of two spinners to specify a {@link Range}.
	 */
	private static class RangeSpinner implements ModifyListener {
		private Spinner minSpinner;
		private Spinner maxSpinner;

		/**
		 * Creates a new {@link RangeSpinner}. This constructor will add 3 widgets
		 * (spinner, label, spinner) to the given parent composite.
		 * 
		 * The layout of the parent composite can be set by the client.
		 */
		public RangeSpinner(Composite parent, int min, int max) {
			minSpinner = new Spinner(parent, SWT.BORDER);
			minSpinner.setMinimum(min);

			Label toLabel = new Label(parent, SWT.NONE);
			toLabel.setText("to");

			maxSpinner = new Spinner(parent, SWT.BORDER);
			maxSpinner.setMaximum(max);

			// add listeners
			minSpinner.addModifyListener(this);
			maxSpinner.addModifyListener(this);
		}

		/**
		 * Returns the currently selected {@link Range}.
		 */
		public Range getSelection() {
			return Range.fromTo(minSpinner.getSelection(), maxSpinner.getSelection());
		}

		/**
		 * Sets the selection of this {@link RangeSpinner}.
		 */
		public void setSelection(Range range) {
			minSpinner.setSelection(range.lowerBound);
			maxSpinner.setSelection(range.upperBound);
		}

		@Override
		public void modifyText(ModifyEvent e) {
			int minSpinnerValue = minSpinner.getSelection();
			int maxSpinnerValue = maxSpinner.getSelection();

			maxSpinner.setMinimum(minSpinnerValue);
			minSpinner.setMaximum(maxSpinnerValue);
		}
	}

	// widgets of this wizard page
	private Text containerText;
	private Text fileText;
	private Text previewText;
	private RangeSpinner rangeOfStatesSpinner;
	private RangeSpinner rangeOfOutgoingTransitionsSpinner;
	private RangeSpinner rangeOfDistinctLabels;

	/** the selection the wizard was launched with */
	private ISelection selection;

	/** the generated probabilistic automaton */
	private ProbabilisticAutomata automaton;

	/** the generator instance that is being configured by the user */
	private RandomProbabilisticAutomatonGenerator generator = new RandomProbabilisticAutomatonGenerator();
	private Spinner selfCycleProbabilitySpinner;

	/**
	 * Instantiates a new {@link RandomProbabilisticAutomatonWizardPage} with the
	 * given initial selection.
	 */
	public RandomProbabilisticAutomatonWizardPage(ISelection selection) {
		super("wizardPage");
		setTitle("Create A Random Probabilistic Automaton");
		setDescription("This wizard allows the creation of random Probabilistic Automata.");
		this.selection = selection;
	}

	@Override
	public void createControl(Composite parent) {
		// create a three panes split layout
		Composite split = new Composite(parent, SWT.NULL);
		split.setLayout(GridLayoutFactory.swtDefaults().numColumns(3).equalWidth(true).create());

		// first pane contains the parameter controls
		Composite container = new Composite(split, SWT.NULL);
		container.setLayoutData(GridDataFactory.swtDefaults().grab(false, true).align(SWT.FILL, SWT.FILL).create());
		// grid layout for main form
		container.setLayout(GridLayoutFactory.swtDefaults().numColumns(3).spacing(3, 9).create());

		// controls to specify workspace container
		Label label = new Label(container, SWT.NULL);
		label.setText("&Container:");

		containerText = new Text(container, SWT.BORDER | SWT.SINGLE);
		containerText.setLayoutData(GridDataFactory.swtDefaults().align(SWT.FILL, SWT.BEGINNING).create());
		containerText.addModifyListener(e -> dialogChanged());

		// create a browse button for the workspace container
		Button button = new Button(container, SWT.PUSH);
		button.setText("Browse...");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleBrowse();
			}
		});

		// create file name controls
		label = new Label(container, SWT.NULL);
		label.setText("&File name:");

		fileText = new Text(container, SWT.BORDER | SWT.SINGLE);
		fileText.setLayoutData(GridDataFactory.swtDefaults().align(SWT.FILL, SWT.BEGINNING).create());
		fileText.addModifyListener(e -> dialogChanged());

		// No extra button for the File Name row
		label = new Label(container, SWT.NULL);

		// No extra label for the parameters
		label = new Label(container, SWT.NULL);

		// create extra-composite for the min-max-parameter controls to reside in
		Composite parameters = new Composite(container, SWT.FILL);
		parameters.setLayoutData(GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).create());

		// create the parameter controls
		createParameterControls(parameters);

		// add shuffle button to parameter controls
		Button shuffleButton = new Button(container, SWT.PUSH);
		shuffleButton.setText("Shuffle");
		shuffleButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleShuffle();
			}
		});

		// right pane contains the text preview
		previewText = new Text(split, SWT.BORDER | SWT.V_SCROLL);
		previewText.setLayoutData(GridDataFactory.swtDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).create());

		initialize();
		dialogChanged();
		setControl(container);
	}

	private void createParameterControls(Composite parent) {
		parent.setLayout(GridLayoutFactory.swtDefaults().numColumns(4).create());

		Label label = new Label(parent, SWT.NONE);
		label.setText("Number of States:");

		rangeOfStatesSpinner = new RangeSpinner(parent, 2, 2000);

		createDescriptionLabel(parent, "The range of number of states to randomly choose from.");

		label = new Label(parent, SWT.NONE);
		label.setText("Number of Outgoing States:");

		rangeOfOutgoingTransitionsSpinner = new RangeSpinner(parent, 0, 200);

		createDescriptionLabel(parent, "The range of outgoing transitions to randomly\n choose from per state.");
		
		label = new Label(parent, SWT.NONE);
		label.setText("Number of Distinct Labels:");

		rangeOfDistinctLabels = new RangeSpinner(parent, 1, 2000);

		createDescriptionLabel(parent, "The range of distinct labels (alphabet size) to randomly\n choose from");
		

		label = new Label(parent, SWT.NONE);
		label.setText("Self cycle probability (percent):");

		selfCycleProbabilitySpinner = new Spinner(parent, SWT.BORDER);
		selfCycleProbabilitySpinner.setMinimum(0);
		selfCycleProbabilitySpinner.setMaximum(100);

		createDescriptionLabel(parent, "The probability that a state can have a self-cycle.\n Note that a value of 1.0 doesn't imply a self-cycle\n for each state, it just increases\n the likelyhood.");
	}

	private void createDescriptionLabel(Composite parent, String text) {
		final Color backgroundColor = Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
		
		Text description = new Text(parent, SWT.MULTI | SWT.READ_ONLY | SWT.WRAP | SWT.LEFT);
		description.setEnabled(false);
		description.setLayoutData(GridDataFactory.swtDefaults().grab(true, false).span(4, 2).create());
		description.setText(text);
		description.setSize(130, 50);
		description.setBackground(backgroundColor);
	}

	/**
	 * Creates a new random automaton and stores it in {@link #automaton}.
	 * 
	 * Also updates the preview text.
	 */
	private void handleShuffle() {
		final Range numberOfStatesRange = rangeOfStatesSpinner.getSelection();
		final Range numberOfOutgoingTransitions = rangeOfOutgoingTransitionsSpinner.getSelection();
		final Range numberOfDistinctLabels = rangeOfDistinctLabels.getSelection();
		final double selfCycleProbability = ((double) selfCycleProbabilitySpinner.getSelection()) / 100.0;

		automaton = generator.createRandom(numberOfStatesRange, numberOfOutgoingTransitions, numberOfDistinctLabels,
				selfCycleProbability);
		previewText.setText(automaton.toString());
	}

	/**
	 * Tests if the current workbench selection is a suitable container to use.
	 */

	private void initialize() {
		initializeShellBounds();

		// infer container from initial selection if possible
		if (selection != null && !selection.isEmpty() && selection instanceof IStructuredSelection) {
			IStructuredSelection ssel = (IStructuredSelection) selection;
			if (ssel.size() > 1)
				return;
			Object obj = ssel.getFirstElement();
			if (obj instanceof IResource) {
				IContainer container;
				if (obj instanceof IContainer)
					container = (IContainer) obj;
				else
					container = ((IResource) obj).getParent();
				containerText.setText(container.getFullPath().toString());
			}
		}

		fileText.setText("random-automaton-" + IOUtils.getTimestring() + ".pdfa");

		rangeOfStatesSpinner.setSelection(RandomProbabilisticAutomatonGenerator.DEFAULT_RANGE_OF_STATES);
		rangeOfOutgoingTransitionsSpinner
				.setSelection(RandomProbabilisticAutomatonGenerator.DEFAULT_RANGE_OF_OUTGOING_TRANSITIONS_PER_STATE);
		rangeOfDistinctLabels.setSelection(RandomProbabilisticAutomatonGenerator.DEFAULT_RANGE_OF_DISTINCT_LABELS);
		selfCycleProbabilitySpinner.setSelection(35);

		// invoke initial shuffle based on default parameters
		handleShuffle();
	}

	/**
	 * Initialises the bounds of the shell.
	 * 
	 * This includes basic dimensions as well as centering on screen.
	 */
	private void initializeShellBounds() {
		Rectangle displayBounds = Display.getCurrent().getBounds();
		this.getShell().setMinimumSize(1000, 600);

		Rectangle windowBounds = this.getShell().getBounds();
		this.getShell().setBounds((int) (((double) displayBounds.width - windowBounds.width) / 2.0),
				(int) (((double) displayBounds.height - windowBounds.height) / 2.0), windowBounds.width,
				windowBounds.height);
	}

	/**
	 * Uses the standard container selection dialog to choose the new value for the
	 * container field.
	 */
	private void handleBrowse() {
		ContainerSelectionDialog dialog = new ContainerSelectionDialog(getShell(),
				ResourcesPlugin.getWorkspace().getRoot(), false, "Select new file container");
		if (dialog.open() == ContainerSelectionDialog.OK) {
			Object[] result = dialog.getResult();
			if (result.length == 1) {
				containerText.setText(((Path) result[0]).toString());
			}
		}
	}

	/**
	 * Ensures that container and filename text fields are set to valid values.
	 */
	private void dialogChanged() {
		IResource container = ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(getContainerName()));
		String fileName = getFileName();

		if (getContainerName().length() == 0) {
			updateStatus("File container must be specified");
			return;
		}
		if (container == null || (container.getType() & (IResource.PROJECT | IResource.FOLDER)) == 0) {
			updateStatus("File container must exist");
			return;
		}
		if (!container.isAccessible()) {
			updateStatus("Project must be writable");
			return;
		}
		if (fileName.length() == 0) {
			updateStatus("File name must be specified");
			return;
		}
		if (fileName.replace('\\', '/').indexOf('/', 1) > 0) {
			updateStatus("File name must be valid");
			return;
		}
		int dotLoc = fileName.lastIndexOf('.');
		if (dotLoc != -1) {
			String ext = fileName.substring(dotLoc + 1);
			if (!ext.equalsIgnoreCase("pdfa")) {
				updateStatus("File extension must be \"pdfa\"");
				return;
			}
		}
		updateStatus(null);
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	public String getContainerName() {
		return containerText.getText();
	}

	public String getFileName() {
		return fileText.getText();
	}

	public ProbabilisticAutomata getAutomaton() {
		return this.automaton;
	}
}