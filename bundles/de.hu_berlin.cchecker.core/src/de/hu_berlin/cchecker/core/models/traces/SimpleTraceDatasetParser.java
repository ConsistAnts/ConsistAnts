package de.hu_berlin.cchecker.core.models.traces;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class SimpleTraceDatasetParser {
	
	private int currentTraceId = 1;
	private int currentTransitionId = 1;

	public TraceDataset parseTraceDatasetFromFile(File trcFile) {
		Map<String, Integer> labelToTransitionId = new HashMap<>();
		List<Trace> traces = new ArrayList<>();
		Map<String, String> alphabetMapping = null;
		
		String thisLine;
		FileInputStream trcFileStream = null;
		BufferedReader reader = null;
		Trace trace;
		try {
			trcFileStream = new FileInputStream(trcFile);
			reader = new BufferedReader(
					new InputStreamReader(trcFileStream)
			);
			thisLine = reader.readLine();
			if (thisLine.contains("=")) {
				alphabetMapping = this.parseAlphabetMappingLine(thisLine);
				thisLine = reader.readLine();
			}
			while(thisLine != null) {
				trace = this.parseTraceLine(thisLine, labelToTransitionId, alphabetMapping);
				traces.add(trace);
				thisLine = reader.readLine();
			}
		} catch (IOException e) {
			return null;
		} finally {
			try {
				if (trcFileStream != null) {
					trcFileStream.close();
				}
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				return null;
			}
		}
		
		//get the inverse of the map
		Map<Integer, String> transitionIdToLabel = labelToTransitionId.entrySet().stream().collect(
				Collectors.toMap(Entry::getValue, c -> c.getKey())
		);
		return new TraceDataset(transitionIdToLabel, traces);
	}

	private Map<String, String> parseAlphabetMappingLine(String thisLine) {
		Map<String, String> alphabetMapping = new HashMap<>();
		
		String[] states = thisLine.split("\\s+");
		for (String state : states) {
			String[] assignment = state.split("=");
			if (assignment.length != 2) {
				throw new RuntimeException("Invalid format for alphabet mapping.");
			}
			alphabetMapping.put(assignment[1], assignment[0]); //e.g. start=0 (key will be 0)
		}
		
		return alphabetMapping;
	}

	private Trace parseTraceLine(String thisLine, 
								 Map<String, Integer> labelToTransitionId,
								 Map<String, String> alphabetMapping) {
		Trace resultTrace = new Trace();
		resultTrace.setTransitions(new ArrayList<>());
		
		if (thisLine.startsWith("[")) {
			int indexOfClosingBracket = thisLine.indexOf("]");
			resultTrace.setId(thisLine.substring(1, indexOfClosingBracket));
			thisLine = thisLine.substring(indexOfClosingBracket+1).trim();
		} else {
			resultTrace.setId(""+currentTraceId++);
		}
		
		String[] transitions = thisLine.split("\\s+");
		int labelId;
		int traceOrdinal = 1;
		for (int i = 0; i < transitions.length; i++) {
			labelId = getLabelId(labelToTransitionId, transitions[i], alphabetMapping);
			resultTrace.getTransitions().add(
				new OrderedTransition(labelId, traceOrdinal++)
			);
		}
		
		return resultTrace;
	}

	private int getLabelId(Map<String, Integer> labelToTransitionId, 
						   String label, 
						   Map<String, String> alphabetMapping) {
		int labelId;
		if (alphabetMapping != null) {
			if (alphabetMapping.get(label) != null) {
				label = alphabetMapping.get(label);
			}
		}
		if (labelToTransitionId.get(label) == null) {
			labelToTransitionId.put(label, currentTransitionId++);
		}
		labelId = labelToTransitionId.get(label);
		return labelId;
	}
	
}
