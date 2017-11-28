package de.hu_berlin.cchecker.ui.editors.report;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.ViewPart;

import de.hu_berlin.cchecker.core.checking.report.ConsistencyReport;
import de.hu_berlin.cchecker.core.checking.report.ConsistencyReportBuilder;
import de.hu_berlin.cchecker.core.checking.report.CounterExample;

/**
 * A form section that displays a list of counter examples 
 * as they are contained in a {@link ConsistencyReport}.
 */
public class CounterExamplesSection extends ViewPart {

	// System icon for problems
	private static final Image PROBLEM_ICON = PlatformUI.getWorkbench().getSharedImages()
			.getImage(ISharedImages.IMG_OBJS_ERROR_TSK);
	
	// Widget of this section
	private TableViewer counterExampleTableViewer;
	private FormToolkit toolkit;
	private ConsistencyReport input;

	// Resources of this section
	private Font boldDefaultFont;

	/** Styler to create bold red StyledStrings */
	private StyledString.Styler boldRedStyler = new StyledString.Styler() {
		@Override
		public void applyStyles(TextStyle textStyle) {
			textStyle.font = boldDefaultFont;
			textStyle.foreground = textStyle.font.getDevice().getSystemColor(SWT.COLOR_RED);
		}
	};

	/**
	 * Initialises a new {@link CounterExamplesSection} with the given {@link FormToolkit}.
	 */
	public CounterExamplesSection(FormToolkit toolkit) {
		this.toolkit = toolkit;
	}
	
	@Override
	public void createPartControl(Composite parent) {
		initializeFonts();
		
		Section section = toolkit.createSection(parent, SWT.NONE);
		section.setText("Counter Examples");
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1).applyTo(section);
	
		Composite client = new Composite(section, SWT.NONE);
		client.setLayout(new GridLayout());
		client.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
	
		GridLayoutFactory.swtDefaults().numColumns(1).applyTo(client);
	
		counterExampleTableViewer = new TableViewer(client);
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL)
				.applyTo(counterExampleTableViewer.getControl());
	
		counterExampleTableViewer.setContentProvider(ArrayContentProvider.getInstance());
	
		counterExampleTableViewer.getTable().setHeaderVisible(true);
		counterExampleTableViewer.getTable().setLinesVisible(true);
	
		createTableColumn(counterExampleTableViewer, "", counterExampleImageColumnProvider(e -> PROBLEM_ICON), 25);
		
		TableViewerColumn lengthColumn = createTableColumn(counterExampleTableViewer, "Trace Length",
				counterExampleStyledColumnProvider(e -> styled(Integer.toString(e.getTrace().size()))), 80);
		lengthColumn.getColumn().setAlignment(SWT.CENTER);
		
		createTableColumn(counterExampleTableViewer, "Trace IDs", counterExampleStyledColumnProvider(
				e -> styled(e.getTraceIds().stream().collect(Collectors.joining(", ")))), 170);
		createTableColumn(counterExampleTableViewer, "Trace",
				counterExampleStyledColumnProvider(e -> getStringTrace(e.getTrace(), e.getIndex())), 300);
		createTableColumn(counterExampleTableViewer, "Description", counterExampleStyledColumnProvider(
				e -> styled("The model does not allow the highlighted transition sequence")), 400);
	
		section.setClient(client);
	}

	@Override
	public void dispose() {
		super.dispose();
		
		boldDefaultFont.dispose();
	}

	/**
	 * Sets the input model for this section.
	 * 
	 * The section will display {@link ConsistencyReportBuilder#getCounterExamples()}.
	 */
	public void setInput(ConsistencyReport report) {
		this.input = report;
		this.counterExampleTableViewer.setInput(report.getCounterExamples());
	}

	@Override
	public void setFocus() {
		this.counterExampleTableViewer.getControl().setFocus();
	}

	/**
	 * Initialises the font resources of this section.
	 */
	private void initializeFonts() {
		Display display = Display.getCurrent();
	
		FontDescriptor descriptor = FontDescriptor.createFrom(display.getSystemFont());
		if (null == boldDefaultFont) {
			boldDefaultFont = descriptor.setStyle(SWT.BOLD).createFont(display);
		}
	}
	
	/**
	 * Returns a styled string representation of the given number-encoded trace.
	 * 
	 * @param numberTrace
	 *            The number-encoded trace
	 * @param highlightIndex
	 *            The index of the transition pair to highlight (the
	 *            counter-example)
	 */
	private StyledString getStringTrace(List<Integer> numberTrace, int highlightIndex) {
		if (null == this.input) {
			return null;
		}
		Map<Integer, String> alphabet = this.input.getLabelMapping().map();
	
		List<String> transitions = numberTrace.stream().map(alphabet::get).collect(Collectors.toList());
	
		if (highlightIndex >= transitions.size() - 1 || highlightIndex < 0) {
			// don't highlight any transitions
			return new StyledString(transitions.stream().collect(Collectors.joining(" ")));
		} else {
			StyledString head = new StyledString(
					transitions.subList(0, highlightIndex).stream().collect(Collectors.joining(" ")));
			StyledString bold = new StyledString(
					transitions.subList(highlightIndex, highlightIndex + 2).stream().collect(Collectors.joining(" ")),
					boldRedStyler);
			StyledString tail = new StyledString(transitions.subList(highlightIndex + 2, transitions.size()).stream()
					.collect(Collectors.joining(" ")));
	
			if (head.length() > 0) {
				return head.append(' ').append(bold).append(' ').append(tail);
			} else {
				return bold.append(' ').append(tail);
			}
		}
	
	}

	/**
	 * Returns a {@link CounterExample} cell label provider that uses the given 
	 * function to get a styled string for a {@link CounterExample} element.
	 */
	private static CellLabelProvider counterExampleStyledColumnProvider(
			Function<CounterExample, StyledString> labelProvider) {
		return new DelegatingStyledCellLabelProvider(new BaseStyledLabelProvider() {
			@Override
			public StyledString getStyledText(Object element) {
				return labelProvider.apply((CounterExample) element);
			}
		});
	}

	/**
	 * Returns a {@link CounterExample} cell label provider that uses the given 
	 * function to get an {@link Image} for a {@link CounterExample} element.
	 */
	private static CellLabelProvider counterExampleImageColumnProvider(Function<CounterExample, Image> labelProvider) {
		return new DelegatingStyledCellLabelProvider(new BaseStyledLabelProvider() {
			@Override
			public Image getImage(Object element) {
				return labelProvider.apply((CounterExample) element);
			}
		});
	}

	/**
	 * Returns an un-styled {@link StyledString} with the given content.
	 */
	private static StyledString styled(String content) {
		return new StyledString(content);
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

	/**
	 * A base implementation of an {@link IStyledLabelProvider}.
	 *
	 */
	private static class BaseStyledLabelProvider extends BaseLabelProvider implements IStyledLabelProvider {
		@Override
		public StyledString getStyledText(Object element) {
			return new StyledString("");
		}

		@Override
		public Image getImage(Object element) {
			return null;
		}
	}

}
