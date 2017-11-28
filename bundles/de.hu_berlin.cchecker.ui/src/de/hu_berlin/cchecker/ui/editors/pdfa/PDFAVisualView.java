package de.hu_berlin.cchecker.ui.editors.pdfa;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.CompositeLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.GridLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticState;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticTransition;

public class PDFAVisualView extends EditorPart {
	
	private Composite composite;
	private final File pdfaModelFile;
	private ProbabilisticAutomata automata;
	private Graph graph;
	
	public PDFAVisualView(File pdfaModelFile) {
		this.setPartName("Visual");
		this.pdfaModelFile = pdfaModelFile;
		getAutomataFromFile();
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// Saving is not possible from the view.
	}

	@Override
	public void doSaveAs() {
		// Saving is not possible from the view.
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		this.setSite(site);
		this.setInput(input);
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

	@PostConstruct
	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout(SWT.VERTICAL));
		composite = parent;
		drawGraph();
	}

	private void drawGraph() {
		graph = new Graph(composite, SWT.NONE);	
		
		Map<Integer, GraphNode> nodes = new HashMap<>();
		
		addStatesToGraph(graph, nodes);
		
		Map<Integer, Map<Integer, Integer>> amountOfTransitionsBetweenTwoNodes = 
				calculateOverlappingTransitions();
		
		addTransitionsToGraph(graph, nodes, amountOfTransitionsBetweenTwoNodes);
		
		nodes.get(automata.getStartState().getStateId()).setBackgroundColor(
				graph.getDisplay().getSystemColor(SWT.COLOR_RED)
		);
		
		graph.setLayoutAlgorithm(new CompositeLayoutAlgorithm(
				LayoutStyles.NO_LAYOUT_NODE_RESIZING,
				new LayoutAlgorithm[] {
						new TreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING),
						new SpringLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING),
						new GridLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING)
				}
		), true);
	}

	private void addStatesToGraph(Graph graph, Map<Integer, GraphNode> nodes) {
		int stateId;
		String label;
		for (ProbabilisticState state  : automata.getStates()) {
			stateId = state.getStateId();
			label = stateId + " [" + String.format("%.4f", state.getTerminatingProbability()) + "]";
			nodes.put(stateId, new GraphNode(graph, SWT.NONE, label));
			nodes.get(stateId).setBackgroundColor(graph.getDisplay().getSystemColor(SWT.COLOR_GRAY));
			nodes.get(stateId).setFont(graph.getDisplay().getSystemFont());
		}
	}

	private void addTransitionsToGraph(Graph graph, Map<Integer, GraphNode> nodes, Map<Integer, Map<Integer, Integer>> amountOfTransitionsBetweenTwoNodes) {
		String label;
		Integer sourceId;
		Integer targetId;
		GraphNode sourceNode;
		GraphNode targetNode;
		GraphConnection connection;
		Integer amount;
		int depth;
		for (ProbabilisticState state  : automata.getStates()) {
			for (ProbabilisticTransition transition : state.getOutgoingTransitions()) {
				sourceId = state.getStateId();
				targetId = transition.getTarget().getStateId();
				sourceNode = nodes.get(sourceId);
				targetNode = nodes.get(targetId);
				label = automata.getTransitionLabels().get((Integer)transition.getTransitionId());
				connection = new GraphConnection(graph, ZestStyles.CONNECTIONS_DIRECTED, sourceNode, targetNode);
				connection.setText(label + " [" + String.format("%.4f", transition.getProbability()) + "]");
				
				if (targetId < sourceId) {
					amount = amountOfTransitionsBetweenTwoNodes.get(targetId).get(sourceId);
					amountOfTransitionsBetweenTwoNodes.get(targetId).put(sourceId, amount - 1);
				} else {
					amount = amountOfTransitionsBetweenTwoNodes.get(sourceId).get(targetId);
					amountOfTransitionsBetweenTwoNodes.get(sourceId).put(targetId, amount - 1);
				}
				
				if (sourceId == targetId) {
					connection.setCurveDepth(30 * amount);
				} else if (amount > 1) {
					depth = 20 * (amount / 2);
					if (amount % 2 > 0) depth *= -1;
					connection.setCurveDepth(depth);
				}
			}
		}
	}
	
	public void refreshGraph() {
		getAutomataFromFile();
		graph.dispose();
		drawGraph();
		composite.pack();
		composite.layout(true);
	}

	@Override
	public void setFocus() {
		this.graph.setFocus();
	}
	
	private void getAutomataFromFile() {
		URI uri = URI.createFileURI(pdfaModelFile.getAbsolutePath());
		Resource resource = new ResourceSetImpl().getResource(uri, true);
		EList<EObject> contents = resource.getContents();
		EObject firstObject = contents.get(0);
		automata = (ProbabilisticAutomata) firstObject;
	}
	
	private Map<Integer, Map<Integer, Integer>> calculateOverlappingTransitions() {
		Map<Integer, Map<Integer, Integer>> amountOfTransitionsBetweenTwoNodes = new HashMap<>();
		Integer firstId;
		Integer secondId;	
		Map<Integer, Integer> subMap;
		for (ProbabilisticState state  : automata.getStates()) {
			for (ProbabilisticTransition transition : state.getOutgoingTransitions()) {
				firstId = transition.getSource().getStateId();
				secondId = transition.getTarget().getStateId();
				if (secondId < firstId) {
					firstId = secondId;
					secondId = transition.getSource().getStateId();
				}
				
				subMap = amountOfTransitionsBetweenTwoNodes.get(firstId);
				if (subMap == null) {
					amountOfTransitionsBetweenTwoNodes.put(firstId, new HashMap<>());
					amountOfTransitionsBetweenTwoNodes.get(firstId).put(secondId, 1);
				} else if (subMap.get(secondId) == null) {
					amountOfTransitionsBetweenTwoNodes.get(firstId).put(secondId, 1);
				} else {
					amountOfTransitionsBetweenTwoNodes.get(firstId).put(secondId, subMap.get(secondId) + 1);
				}
			}
		}
		return amountOfTransitionsBetweenTwoNodes;
	}

}