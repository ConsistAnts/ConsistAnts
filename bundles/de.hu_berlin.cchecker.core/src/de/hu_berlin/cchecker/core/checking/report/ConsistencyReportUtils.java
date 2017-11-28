package de.hu_berlin.cchecker.core.checking.report;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;

import de.hu_berlin.cchecker.core.checking.Pair;

/**
 * Utilitiy class for {@link ConsistencyReport} instances.
 */
public class ConsistencyReportUtils {

	/**
	 * Creates a textual report from the given consistency report.
	 */
	public static String createTextualReport(ConsistencyReport report) {
		StringBuilder result = new StringBuilder();
		result.append("===> Conformance Report <===\n\n");
		DecimalFormat df = new DecimalFormat("#.####");
		df.setRoundingMode(RoundingMode.CEILING);

		EList<ConsistencyLengthResult> resultMatrices = report.getFootprintMatrices();
		Map<Integer, Set<Pair<Integer, Integer>>> counterExampleMap = counterExampleMap(report);

		final int matrixDimension = report.getLabelMapping().values().size();
		List<Integer> alphabet = createSortedAlphabet(report);

		for (int h = 0; h < resultMatrices.size(); h++) {
			ConsistencyLengthResult lengthResult = resultMatrices.get(h);

			// this set contains all the events for which there are counter examples of the
			// current length
			Set<Pair<Integer, Integer>> counterExampleEvents = counterExampleMap.get(lengthResult.getTraceLength());
			if (null == counterExampleEvents) {
				counterExampleEvents = Collections.emptySet();
			}

			result.append("\n");
			result.append("Trace Length: " + lengthResult.getTraceLength() + "\n\n");

			for (int i = 0; i < matrixDimension + 1; i++) {
				StringBuilder temp = new StringBuilder();
				for (int k = 0; k < matrixDimension + 1; k++) {
					StringBuilder intertemp = new StringBuilder();
					if (i == 0 && k == 0) {
						intertemp.append("");
					} else if (i == 0) {
						Integer transitionId = alphabet.get(k - 1);
						intertemp.append(report.getLabelMapping().get(transitionId));
					} else if (k == 0) {
						Integer transitionId = alphabet.get(i - 1);
						intertemp.append(report.getLabelMapping().get(transitionId));
					} else {
						final int firstTransitionId = alphabet.get(i - 1);
						final int secondTransitionId = alphabet.get(k - 1);
						final boolean hasCounterExample = counterExampleEvents
								.contains(Pair.create(firstTransitionId, secondTransitionId));
						if (hasCounterExample) {
							intertemp.append("f");
						} else {
							double yoy = lengthResult.getResultMatrix().get(i - 1).getElements().get(k - 1);
							if (yoy >= 0) { // add space in front to even out negative
								intertemp.append(" ");
								intertemp.append(df.format(yoy));
							} else { // handle - separately so + and - have same decimal lengths
								intertemp.append("-");
								intertemp.append(df.format(Math.abs(yoy)));
							}
						}
					}
					int melm = 15 - intertemp.length();
					for (int p = 0; p < melm; p++) { // add spaces at end so everything is streamlined
						intertemp.append(" ");
					}

					temp.append(intertemp);

				}
				result.append(temp + "\n");
			}
			result.append("\n");
			result.append("Conformance: " + df.format(lengthResult.getResult() * 100d) + "%\n\n");
			result.append("------------------------------------------------------------------\n\n");
		}
		return result.toString();
	}

	/**
	 * Creates a map which maps trace length to the set of events that have
	 * counter-examples in the given report.
	 * 
	 * An event is represented by a pair of integers that represent the transition
	 * IDs.
	 */
	public static Map<Integer, Set<Pair<Integer, Integer>>> counterExampleMap(ConsistencyReport report) {
		Map<Integer, Set<Pair<Integer, Integer>>> map = new HashMap<>();

		for (ConsistencyLengthResult result : report.getFootprintMatrices()) {
			final int length = result.getTraceLength();
			final Set<Pair<Integer, Integer>> counterExampleEvents = new HashSet<>();
			map.put(length, counterExampleEvents);
		}

		for (CounterExample counterExample : report.getCounterExamples()) {
			final int index = counterExample.getIndex();
			try {
				final EList<Integer> trace = counterExample.getTrace();
				final int length = trace.size();
				List<Integer> event = trace.subList(index, index + 2);
				Set<Pair<Integer, Integer>> set = map.get(length);

				if (null != set) {
					set.add(Pair.create(event.get(0), event.get(1)));
				}
			} catch (IndexOutOfBoundsException e) {
				// ignore invalid counter-examples
				continue;
			}
		}

		return map;
	}

	/**
	 * Returns a sorted list of the number-encoded trace alphabet.
	 * 
	 * Use {@link ConsistencyReportBuilder#getLabelMapping()} to retrieve the
	 * human-readable transition labels.
	 */
	public static ArrayList<Integer> createSortedAlphabet(ConsistencyReport model) {
		ArrayList<Integer> alphabetList = new ArrayList<>(model.getLabelMapping().map().keySet());
		Collections.sort(alphabetList);
		return alphabetList;
	}

	private ConsistencyReportUtils() {
		// non-instantiable utility class
	}
}
