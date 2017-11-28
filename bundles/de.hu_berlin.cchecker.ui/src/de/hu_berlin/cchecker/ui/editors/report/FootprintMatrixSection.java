package de.hu_berlin.cchecker.ui.editors.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.ViewPart;

import de.hu_berlin.cchecker.core.checking.report.ConsistencyLengthResult;
import de.hu_berlin.cchecker.core.checking.report.ConsistencyReport;
import de.hu_berlin.cchecker.core.checking.report.ConsistencyReportBuilder;
import de.hu_berlin.cchecker.core.checking.report.ConsistencyReportUtils;
import de.hu_berlin.cchecker.core.checking.report.MatrixRow;

/**
 * A section to display a result footprint matrix of a given {@link ConsistencyReportBuilder} input.
 */
public class FootprintMatrixSection extends ViewPart {

	// Part widgets
	private FormToolkit toolkit;
	private Section matrixSubSection;
	private TableViewer resultMatrixViewer;
	
	// Part models
	private ArrayList<Integer> columnIds;
	private Map<Integer, String> alphabet;

	////Part Resources (make sure to dispose them in #dispose())
	private Font smallDefaultFont;
	
	/**
	 * Initialises a new {@link FootprintMatrixSection} with the given {@link FormToolkit}.
	 */
	public FootprintMatrixSection(FormToolkit toolkit) {
		this.toolkit = toolkit;
	}
	
	@Override
	public void createPartControl(Composite parent) {
		initializeFonts();
		
		matrixSubSection = toolkit.createSection(parent, Section.DESCRIPTION);
		matrixSubSection.setText("Footprint Matrix");
		GridDataFactory.fillDefaults().grab(true, true).applyTo(matrixSubSection);
		
		Composite matrixView = new Composite(matrixSubSection, SWT.NONE);
		matrixView.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
	
		GridLayoutFactory.fillDefaults().margins(5, 5).numColumns(1).applyTo(matrixView);
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(matrixView);
		
		resultMatrixViewer = new TableViewer(matrixView);
		resultMatrixViewer.getTable().setHeaderVisible(true);
		resultMatrixViewer.setContentProvider(ArrayContentProvider.getInstance());
		GridDataFactory.fillDefaults().grab(true, true).applyTo(resultMatrixViewer.getTable());
		
		matrixSubSection.setClient(matrixView);
	}

	/**
	 * Constructs and sets the input for the result matrix table {@link #resultMatrixViewer}.
	 */
	public void setInput(ConsistencyLengthResult result) {
		EList<MatrixRow> rawMatrix = result.getResultMatrix();
		
		List<List<String>> input = new ArrayList<>();
		
		for (int i=0; i<rawMatrix.size(); i++) {
			EList<Double> rowElements = rawMatrix.get(i).getElements();
			final Double[] rawRow = rowElements.toArray(new Double[rowElements.size()]);
			
			// determine the transition label of the current row
			final Integer rowTransitionId = columnIds.get(i);
			final String rowTransitionLabel = alphabet.get(rowTransitionId);
			
			List<String> rowInput = new ArrayList<>();
			rowInput.add(rowTransitionLabel); // add transition label in first column
			
			// add all cell values for this row
			for (double cell : rawRow) {
				rowInput.add(Double.toString(cell));
			}
			
			input.add(rowInput);
		}
		
		resultMatrixViewer.setInput(input);
		matrixSubSection.setDescription(String.format("Trace length %d", result.getTraceLength()));
	}

	/**
	 * Updates the columns of the viewer.
	 * 
	 * The number and titles of the result matrix viewer depend on the model alphabet.
	 */
	public void updateViewer(ConsistencyReport model) {
		TableColumn[] columns = resultMatrixViewer.getTable().getColumns();
		for (TableColumn column : columns) {
			column.dispose();
		}
		
		columnIds = ConsistencyReportUtils.createSortedAlphabet(model);
		alphabet = model.getLabelMapping().map();
		
		// add column to show row labels
		createTableColumn(resultMatrixViewer, "", new ColumnLabelProvider() {
			@SuppressWarnings("unchecked")
			@Override
			public String getText(Object element) {
				if (element instanceof List<?>) {
					return ((List<String>) element).get(0);
				}
				return null;
			}
			@Override
			public Font getFont(Object element) {
				return smallDefaultFont;
			}
		}, 80);
		
		for (int i=0; i<columnIds.size(); i++) {
			final int transitionId = columnIds.get(i);
			final String transition = model.getLabelMapping().get((Integer) transitionId);
			
			final int index = i;
			
			TableViewerColumn valueColumn = createTableColumn(resultMatrixViewer, transition, new ColumnLabelProvider() {
				@SuppressWarnings("unchecked")
				@Override
				public String getText(Object element) {
					if (element instanceof List<?>) {
						return ((List<String>) element).get(index + 1);
					}
					return null;
				}
			}, 80);
			valueColumn.getColumn().pack();
			// set minimum column width to 40
			valueColumn.getColumn().setWidth(Math.max(valueColumn.getColumn().getWidth(), 70));
			
			valueColumn.getColumn().setAlignment(SWT.CENTER);
		}
	}

	@Override
	public void dispose() {
		smallDefaultFont.dispose();
		smallDefaultFont = null;
		
		super.dispose();
	}

	/**
	 * Initialises the font resources of this part.
	 */
	private void initializeFonts() {
		Display display = Display.getCurrent();
	
		FontDescriptor descriptor = FontDescriptor.createFrom(display.getSystemFont());
		if (null == smallDefaultFont) {
			smallDefaultFont = descriptor.setHeight(11).createFont(display);
		}
	}
	
	private static TableViewerColumn createTableColumn(TableViewer v, String title, CellLabelProvider labelProvider, int width) {
		TableViewerColumn viewerColumn = new TableViewerColumn(v, SWT.LEFT);
		viewerColumn.setLabelProvider(labelProvider);
	
		TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setResizable(true);
		column.setMoveable(false);
		column.setWidth(width);
		
		return viewerColumn;
	}

	@Override
	public void setFocus() {
		this.resultMatrixViewer.getControl().setFocus();
	}

}
