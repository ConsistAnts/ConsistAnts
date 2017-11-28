package de.hu_berlin.cchecker.ui.editors.report;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.OwnerDrawLabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.TextLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.ViewPart;

import de.hu_berlin.cchecker.core.checking.report.ConsistencyLengthResult;
import de.hu_berlin.cchecker.core.checking.report.ConsistencyReport;
import de.hu_berlin.cchecker.core.checking.report.ConsistencyReportBuilder;

/**
 * A form section that displays the consistency result per trace length of a {@link ConsistencyReportBuilder} 
 * in a table. 
 *
 */
public class ConsistencyResultSection extends ViewPart {
	public static interface ConsistencyResultSelectionChangedListener {
		void consistencyResultSelectionChanged(ConsistencyLengthResult result);
	}
	
	private FormToolkit toolkit;
	private TableViewer consistencyTableViewer;
	private Set<ConsistencyResultSelectionChangedListener> changedListeners = new HashSet<>();

	public ConsistencyResultSection(FormToolkit toolkit) {
		this.toolkit = toolkit;
	}
	
	@Override
	public void createPartControl(Composite parent) {
		// create section
		Section section = toolkit.createSection(parent, Section.DESCRIPTION);
		section.setText("Consistency");
		section.setDescription("Degree of consistency per trace length");
		GridDataFactory.fillDefaults().grab(true, true).applyTo(section);

		// create container composite for consistency table viewer
		Composite client = new Composite(section, SWT.NONE);
		client.setLayout(new GridLayout());
		client.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
		GridLayoutFactory.swtDefaults().numColumns(1).applyTo(client);

		consistencyTableViewer = new TableViewer(client, SWT.SINGLE | SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL)
				.applyTo(consistencyTableViewer.getTable());

		consistencyTableViewer.setContentProvider(ArrayContentProvider.getInstance());
		consistencyTableViewer.getTable().setHeaderVisible(true);

		// update result matrix viewer whenever the selection in the consistency tree
		// changes
		consistencyTableViewer.addSelectionChangedListener(new ConsistencyTableSelectionListener());

		// create the Trace Length column
		TableViewerColumn viewerColumn = new TableViewerColumn(consistencyTableViewer, SWT.NONE);
		viewerColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof ConsistencyLengthResult) {
					return "Length " + Integer.toString(((ConsistencyLengthResult) element).getTraceLength());
				} else {
					return "";
				}
			}
		});
		TableColumn traceLengthColumn = viewerColumn.getColumn();
		traceLengthColumn.setWidth(80);
		traceLengthColumn.setResizable(false);
		traceLengthColumn.setText("Trace Length");

		// create the consistency degree column that shows a bar to indicate degree of
		// consistency
		TableViewerColumn progressColumn = new TableViewerColumn(consistencyTableViewer, SWT.NONE);
		progressColumn.setLabelProvider(new ConsistencyProgressBarProvider(progressColumn));
		progressColumn.getColumn().setText("Consistency");
		progressColumn.getColumn().setWidth(300);
		section.setClient(client);
	}

	@Override
	public void setFocus() {
		this.consistencyTableViewer.getControl().setFocus();
	}
	
	/**
	 * Sets the model input to display in this results section.
	 */
	public void setInput(ConsistencyReport report) {
		this.getConsistencyTableViewer().setInput(report.getFootprintMatrices());
	}

	/**
	 * Sets the currently selected {@link ConsistencyLengthResult}.
	 */
	public void setSelection(ConsistencyLengthResult result) {
		this.consistencyTableViewer.setSelection(new StructuredSelection(result));
	}
	
	/**
	 * Returns the table viewer control of this part.
	 * @return
	 */
	public TableViewer getConsistencyTableViewer() {
		return consistencyTableViewer;
	}

	public void addSelectionChangedListener(ConsistencyResultSelectionChangedListener listener) {
		if (null == listener) {
			throw new NullPointerException();
		}
		this.changedListeners.add(listener);
	}

	public void removeSelectionChangedListener(ConsistencyResultSelectionChangedListener listener) {
		if (null == listener) {
			throw new NullPointerException();
		}
		this.changedListeners.remove(listener);
	}

	public void fireSelectionChanged(ConsistencyLengthResult result) {
		this.changedListeners.forEach(l -> l.consistencyResultSelectionChanged(result));
	}

	/**
	 * Label provider that draws a bar indicating the relative degree of 
	 * consistency as well as a right-aligned text that numerically displays the degree of consistency. 
	 */
	private final class ConsistencyProgressBarProvider extends OwnerDrawLabelProvider {
		private final TableViewerColumn progressColumn;
		private static final int BAR_HEIGHT = 15;
		private static final int MARGIN_RIGHT = 70;

		private ConsistencyProgressBarProvider(TableViewerColumn progressColumn) {
			this.progressColumn = progressColumn;
		}

		@Override
		protected void paint(Event event, Object element) {
			if (element instanceof ConsistencyLengthResult) {
				double result = ((ConsistencyLengthResult) element).getResult();
				int barWidth = (int) (result * (progressColumn.getColumn().getWidth() - MARGIN_RIGHT));
				GC context = event.gc;
				Device device = context.getDevice();

				// draw progress bar on the left
				final Color c = result > 0.5 ? device.getSystemColor(SWT.COLOR_DARK_GREEN) : device.getSystemColor(SWT.COLOR_DARK_RED);
				final int minimumAlpha = 50;
				
				context.setForeground(c);
				context.setBackground(c);
				context.setAlpha((int) (minimumAlpha + (result * (255 - minimumAlpha))));
				
				context.drawRectangle(event.x + 2, event.y + 1, barWidth - 2, BAR_HEIGHT - 1);
				context.fillRectangle(event.x + 2, event.y + 1, barWidth - 2, BAR_HEIGHT - 1);
				
				// draw progress label on the right
				context.setForeground(device.getSystemColor(SWT.COLOR_BLACK));
				context.setAlpha(255);
				TextLayout textLayout = new TextLayout(device);
				textLayout.setText(String.format("[%.2f%%]", result * 100));
				textLayout.setAlignment(SWT.RIGHT);
				textLayout.setWidth(progressColumn.getColumn().getWidth());
				textLayout.draw(context, event.x, event.y);
				
			}
		}

		@Override
		protected void measure(Event event, Object element) {
			event.setBounds(new Rectangle(event.x, event.y, progressColumn.getColumn().getWidth(), BAR_HEIGHT));
		}
	}

	/**
	 * A selection listener that delegates a change in selection to {@link #changedListeners}
	 *
	 */
	private final class ConsistencyTableSelectionListener implements ISelectionChangedListener {
		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			ISelection selection = event.getSelection();
			if (selection instanceof IStructuredSelection) {
				Object firstElement = ((IStructuredSelection) selection).getFirstElement();
				if (firstElement instanceof ConsistencyLengthResult) {
					fireSelectionChanged((ConsistencyLengthResult) firstElement);
				}
			}
	
		}
	}
}
