package de.hu_berlin.cchecker.ui.editors.trace;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.xml.bind.DatatypeConverter;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.wb.swt.SWTResourceManager;

import de.hu_berlin.cchecker.core.models.traces.OrderedTransition;
import de.hu_berlin.cchecker.core.models.traces.Trace;
import de.hu_berlin.cchecker.core.models.traces.TraceDataset;
import de.hu_berlin.cchecker.core.models.traces.TraceDatasetParser;

/**
 * The TracesAggregateView is a read-only view that mainly consists of a table that contains
 * every distinct trace in the TraceDataset from the given file and their number of occurrences.
 * For convenience, the full path to the file as well as its md5 checksum is provided as well.
 * @author Linus
 *
 */
public class TracesAggregateView extends EditorPart {
	
	/**
	 * The underlying file that this view is showing.
	 */
	private final File traceDatasetFile;
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	
	/**
	 * The rows for the {@link #table}. The String arrays all have length 2, with
	 * a String representation of the trace being at index 0 and their number of
	 * occurrences at index 1.
	 */
	private List<String[]> rows;
	
	/**
	 * The table that shows the traces and their number of occurrences.
	 * Populating it with data is automatically done when the editor opens,
	 * the file contents change or the user initiates sorting on a column.
	 */
	private Table table;
	
	/**
	 * The ui text element that displays the md5 checksum of the underlying {@link #traceDatasetFile}.
	 */
	private StyledText txtChecksum;
	
	/**
	 * The ui text element that displays the smallest trace length of the underlying {@link #traceDatasetFile}.
	 */
	private StyledText txtMinTrcLength;
	
	/**
	 * The ui text element that displays the longest trace length of the underlying {@link #traceDatasetFile}.
	 */
	private StyledText txtMaxTrcLength;
	
	public TracesAggregateView(File traceDatasetFile) {
		this.setPartName("Aggregated");
		this.traceDatasetFile = traceDatasetFile;
		this.rows = new ArrayList<>();
	}

	/**
	 * Create contents of the view part, including the general information composite
	 * and the actual table.
	 */
	@PostConstruct
	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout(SWT.VERTICAL));
		
		// This is the composite for the whole aggregated view.
		Composite composite = new Composite(parent, SWT.NONE);
		toolkit.adapt(composite);
		toolkit.paintBordersFor(composite);
		composite.setLayout(new GridLayout(1, false));
		
		this.createGeneralInformation(composite);
		this.createAggregatedTable(composite);
		this.refreshDataFromFile();
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// Saving is not possible from the aggregated view.
	}

	@Override
	public void doSaveAs() {
		// Saving is not possible from the aggregated view.
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);
	}

	@Override
	public boolean isDirty() {
		// Since saving is not possible, this view is never dirty.
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}
	
	@Override
	public void setFocus() {
		this.table.setFocus();
	}
	
	/**
	 * Creates all UI elements for the general information header above the aggregated table.
	 */
	private void createGeneralInformation(Composite parent) {
		Form form = toolkit.createForm(parent);
		GridData gridData = new GridData(SWT.LEFT, SWT.TOP, true, false, 1, 1);
		form.setLayoutData(gridData);
		toolkit.paintBordersFor(form);
		form.setText("General Information");
		form.getBody().setLayout(new GridLayout(2, false));
		
		// This is just a label saying "File Path".
		Label lblModelFile = new Label(form.getBody(), SWT.NONE);
		lblModelFile.setText("File Path");
		toolkit.adapt(lblModelFile, true, true);
		
		// This is the full path to the file.
		StyledText txtFilePath = new StyledText(form.getBody(), SWT.NONE);
		txtFilePath.setText(traceDatasetFile.getPath());
		txtFilePath.setEditable(false);
		txtFilePath.setCaret(null);
		toolkit.adapt(txtFilePath, true, true);
		
		// This is just a label saying "MD5-checksum".
		Label labelMd5 = new Label(form.getBody(), SWT.NONE);
		labelMd5.setText("MD5-checksum");
		labelMd5.setBounds(0, 0, 58, 14);
		toolkit.adapt(labelMd5, true, true);
		
		// This is the file's checksum.
		txtChecksum = new StyledText(form.getBody(), SWT.NONE);
		txtChecksum.setForeground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_FOREGROUND));
		toolkit.adapt(txtChecksum, true, true);
		txtChecksum.setEditable(false);
		txtChecksum.setCaret(null);
		
		// This is just a label saying "Minimal Trace Length".
		Label labelMinTrcLength = new Label(form.getBody(), SWT.NONE);
		labelMinTrcLength.setText("Minimal Trace Length");
		labelMinTrcLength.setBounds(0, 0, 58, 28);
		toolkit.adapt(labelMinTrcLength, true, true);
		
		// This is the file's minimal trace length
		txtMinTrcLength = new StyledText(form.getBody(), SWT.NONE);
		txtMinTrcLength.setForeground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_FOREGROUND));
		toolkit.adapt(txtMinTrcLength, true, true);
		txtMinTrcLength.setEditable(false);
		txtMinTrcLength.setCaret(null);
		
		// This is just a label saying "Maximal trace length".
		Label labelMaxTrcLength = new Label(form.getBody(), SWT.NONE);
		labelMaxTrcLength.setText("Maximal trace length");
		labelMaxTrcLength.setBounds(0, 0, 58, 42);
		toolkit.adapt(labelMaxTrcLength, true, true);
		
		txtMaxTrcLength = new StyledText(form.getBody(), SWT.NONE);
		txtMaxTrcLength.setForeground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_FOREGROUND));
		toolkit.adapt(txtMaxTrcLength, true, true);
		txtMaxTrcLength.setEditable(false);
		txtMaxTrcLength.setCaret(null);
		
		// This is an empty label, giving us a bit of space between this section and the table.
		new Label(form.getBody(), SWT.NONE);
	}
	
	/**
	 * Adds string representation of all traces of {@link #dataset} and their number of occurences to the list of rows.
	 * 
	 * @see TraceDataset#getTracesAggregated()
	 */
	private void createAggregatedTable(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		toolkit.adapt(composite);
		toolkit.paintBordersFor(composite);
		
		Form frmNewForm = toolkit.createForm(composite);
		toolkit.paintBordersFor(frmNewForm);
		frmNewForm.setText("Traces (Aggregated)");
		frmNewForm.getBody().setLayout(new FillLayout(SWT.HORIZONTAL));
		
		final TableViewer tableViewer = new TableViewer(frmNewForm.getBody(), SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn columnTrace = new TableColumn(table, SWT.NONE);
		columnTrace.setWidth(500);
		columnTrace.setText("Trace");
		
		TableColumn columnOccurences = new TableColumn(table, SWT.NONE);
		columnOccurences.setWidth(250);
		columnOccurences.setText("Number of Occurrences");

		// Sorts our traces simply alphabetically and the occurrences from highest to lowest.
		Listener sortListener = e -> {
			TableColumn column = (TableColumn) e.widget;
			int index = 0;
			if (column.equals(columnTrace)) {
				index = 0;
			} else if (column.equals(columnOccurences)) {
				index = 1;
			}
			Collections.sort(rows, getComparatorForColumn(index));
			table.setSortColumn(column);
			updateTable();
		};
		columnTrace.addListener(SWT.Selection, sortListener);
		columnOccurences.addListener(SWT.Selection, sortListener);
	}
	
	/**
	 * Refreshes the data of this view from the underlying {@link #traceDatasetFile}.
	 */
	public void refreshDataFromFile() {
		TraceDataset dataset = new TraceDatasetParser().parseTraceDatasetFromFile(traceDatasetFile);
		
		this.calculateMd5Checksum();
		this.calculateMinMaxTraceLengths(dataset);
		this.addAggregatedTracesFromFileToRows(dataset);
	}
	
	/**
	 * Calculates the md5 checksum of {@link #traceDatasetFile} and refreshes the text in
	 * general information.
	 */
	private void calculateMd5Checksum() {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(Files.readAllBytes(Paths.get(traceDatasetFile.toURI())));
			txtChecksum.setText(DatatypeConverter.printHexBinary(md.digest()));
		} catch (NoSuchAlgorithmException | IOException e) {
			txtChecksum.setText("An error occured while calculating this file's checksum.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Calculates the minimal and maximal trace lengths of the given {@link TraceDataset} 
	 * and refreshes the respective texts in general information.
	 */
	private void calculateMinMaxTraceLengths(TraceDataset dataset) {
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		for (Trace trace : dataset.getTraces()) {
			int length = trace.getTransitions().size();
			max = length > max ? length : max;
			min = length < min? length: min;
		}
		txtMinTrcLength.setText(Integer.toString(min));
		txtMaxTrcLength.setText(Integer.toString(max));
	}
	
	/**
	 * Parses {@link #traceDatasetFile} to a TraceDataset and adds all the aggregated traces to {@link #rows}.
	 */
	private void addAggregatedTracesFromFileToRows(TraceDataset dataset) {
		rows.clear();
		// For every distinct trace in the aggregated OccurenceCounter
		for (Entry<List<OrderedTransition>, Integer> entry : dataset.getTracesAggregated().entrySet()) {
			// First, translate all the transition ids to their readable Strings.
			StringBuilder traceStringBuilder = new StringBuilder();
			for (OrderedTransition transition : entry.getKey()) {
				traceStringBuilder.append(dataset.getTransitionIdToLabel().get(transition.getId()));
				traceStringBuilder.append(" ");
			}
			// Then add the resulting array to the row list.
			rows.add(new String[] {traceStringBuilder.toString().trim(), Integer.toString(entry.getValue())});
		}
		updateTable();
	}
	
	/**
	 * This populates the actual table with data from {@link #rows}. The order of {@link #rows}
	 * will determine the order of rows in the table.
	 */
	private void updateTable() {
		table.removeAll();
		for (String[] row : rows) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(row);
		}
	}
	
	/**
	 * For a given column index, returns the appropriate comparator to sort that column.
	 */
	private Comparator<String[]> getComparatorForColumn(int index) {
		if (index == 0) {
			return (String[] o1, String[] o2) -> o1[index].compareTo(o2[index]);
		} else {
			// When we compare occurrences, we want the highest number at the top, that's why we reverse the order here.
			return (String[] o1, String[] o2) -> {
				Integer i1 = Integer.parseInt(o1[index]);
				Integer i2 = Integer.parseInt(o2[index]);
				return Integer.compare(i2, i1);
			};
		}
	}
}
