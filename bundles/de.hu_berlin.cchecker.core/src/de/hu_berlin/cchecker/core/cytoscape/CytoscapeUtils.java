package de.hu_berlin.cchecker.core.cytoscape;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.io.Files;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomataVisitor;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticState;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticTransition;

public class CytoscapeUtils {
	
	private static final String SCRIPT_HEAD = "var cy = window.cy = cytoscape({\n" + 
			"  container: document.getElementById('cy'),\n" + 
			"\n" + 
			"  boxSelectionEnabled: false,\n" + 
			"  autounselectify: true,\n" + 
			"\n" + 
			"  layout: {\n" + 
			"    name: 'breadthfirst'\n" + 
			"  },\n" + 
			"\n" + 
			"  style: [\n" + 
			"    {\n" + 
			"      selector: 'node',\n" + 
			"      style: {\n" + 
			"        'content': 'data(id)',\n" + 
			"        'text-opacity': 0.5,\n" + 
			"        'text-valign': 'center',\n" + 
			"        'text-halign': 'right',\n" + 
			"        'background-color': 'grey'\n" + 
			"      }\n" + 
			"    },\n" + 
			"\n" + 
			"    {\n" + 
			"      selector: 'edge',\n" + 
			"      style: {\n" + 
			"        'width': 3,\n" + 
			"        'target-arrow-shape': 'triangle',\n" + 
			"        'target-arrow-color': '#bebebe',\n" + 
			"        'curve-style': 'bezier',\n" + 
			"        'line-color': '#bebebe',\n" + 
			"        'label': 'data(label)',\n" + 
			"        \"text-background-opacity\": 1,\n" + 
			"        \"text-background-color\": \"#ccc\",\n" + 
			"        \"text-background-shape\": \"roundrectangle\",\n" + 
			"        \"text-background-padding\": \"5pt\"\n" + 
			"      }\n" + 
			"    },\n" + 
			"\n" + 
			"    {\n" + 
			"      selector: 'node[terminating=1]',\n" + 
			"      style: {\n" + 
			"        \"background-color\": \"red\"\n" + 
			"      }\n" + 
			"    },\n" + 
			"{\n" + 
				"      selector: 'node[start=1]',\n" + 
				"      style: {\n" + 
				"        \"background-color\": \"green\"\n" + 
				"      }\n" + 
				"    }\n" + 
			"  ],\n";
	
	private static final String SCRIPT_REMAINDER = "});";
	
	public static String toCytoscapeElements(ProbabilisticAutomata automata) {
	
		StringBuilder textualRepresentation = new StringBuilder();
	
		textualRepresentation.append("elements: {\n nodes: [\n");
	
		List<String> nodeDescription = new ArrayList<>();
		List<String> edgeDescription = new ArrayList<>();
	
		ProbabilisticAutomataVisitor.visit(automata, new ProbabilisticAutomataVisitor() {
			@Override
			public void visit(ProbabilisticState state) {
				nodeDescription
					.add(String.format("{ data: { id: 's" + state.getStateId() + "' %s %s} }",
							state.getTerminatingProbability() > 0 ? ", terminating: 1" : "",
							automata.getStartState() == state ? ", start: 1" : ""));
			}
	
			@Override
			public void visit(ProbabilisticTransition transition) {
				ProbabilisticState target = transition.getTarget();
				String label = automata.getTransitionLabels()
					.get(new Integer(transition.getTransitionId()));
				edgeDescription.add(String.format("{ data: { source: 's%d', target: 's%d', label: '%s[%f]'} }",
						transition.getSource()
							.getStateId(),
						target.getStateId(), label, transition.getProbability()));
			}
		});
	
		textualRepresentation.append(nodeDescription.stream()
			.collect(Collectors.joining(", \n\t")));
	
		textualRepresentation.append("\n],\n edges: [");
		textualRepresentation.append(edgeDescription.stream()
			.collect(Collectors.joining(", \n\t")));
		textualRepresentation.append("]\n}\n");
	
		return SCRIPT_HEAD + textualRepresentation.toString() + SCRIPT_REMAINDER;
	}
	
	/**
	 * Generates and saves a cytoscape script displaying the given automaton and returns
	 * the temporary location of the generated index.html.
	 */
	public static String saveCytoscapeScriptAndGetIndexLocation(ProbabilisticAutomata a) {
		File tempDir = Files.createTempDir();
		
		File indexFile = new File(tempDir.toPath().toString() + "/index.html");
		File styleFile = new File(tempDir.toPath().toString() + "/style.css");
		
		try {
			String indexFilePath = org.eclipse.core.runtime.FileLocator.toFileURL(CytoscapeUtils.class.getResource("/res/index.html").toURI().toURL()).getFile();
			String styleFilePath = org.eclipse.core.runtime.FileLocator.toFileURL(CytoscapeUtils.class.getResource("/res/style.css").toURI().toURL()).getFile();
			Files.copy(new File(indexFilePath), indexFile);
			Files.copy(new File(styleFilePath), styleFile);
		} catch (IOException | URISyntaxException e1) {
			System.out.println("Failed to copy cytosape resources.");
			return null;
		}
		
		File file = new File(tempDir.toPath().toString() + "/main.js");
		String s = toCytoscapeElements(a);
		try {
			Files.write(s.getBytes(), file);
			String indexPath = tempDir.toPath().toString() + "/index.html";
			System.out.println("View cytoscape automaton at file://" + indexPath);
			return indexPath;
			//Runtime.getRuntime().exec("open " + indexPath);
		} catch (IOException e) {
			System.out.println("Failed to save cytoscape script:" + e.getMessage());
			return null;
		}
	}

}
