package de.hu_berlin.cchecker.core.stopwatch;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Stopwatch API to measure elapsed time for performance tests
 * 
 * @author HorazVitae
 */

public class Stopwatch {
	private List<String> cpnames;
	private List<Long> cptimes;

	public Stopwatch() {
		cpnames = new LinkedList<String>();
		cptimes = new LinkedList<Long>();
	}

	/**
	 * starts a measurement set; name is the set's name (saved at index 0 btw
	 * 
	 * @param name
	 */
	public void start(String name) {
		cpnames.add(name);
		cptimes.add(System.nanoTime());
	}

	/**
	 * sets checkpoint for set; name is name of checkpoint; returns id of checkpoint
	 * in set
	 * 
	 * @param name
	 * @return id of checkpoint in set
	 */
	public int checkpoint(String name) {
		cpnames.add(name);
		cptimes.add(System.nanoTime());
		return cpnames.size() - 1;
	}

	/**
	 * ends set; sets last id to end; returns last id
	 * 
	 * @return last id
	 */
	public int finish() {
		cpnames.add("end");
		cptimes.add(System.nanoTime());
		return cpnames.size() - 1;
	}

	/**
	 * prints measurement at given id
	 * 
	 * @param checkId
	 */
	public void print(int checkId) {
		double time = (cptimes.get(checkId) - cptimes.get(0));
		double swf = time / 1000000000;
		System.out.println("Measurepoint: " + cpnames.get(checkId));
		System.out.println("Elapsed time since start of '" + cpnames.get(0) + "': " + swf + " seconds\n");

	}

	/**
	 * prints given id and calculates time difference to id before that (checkId is
	 * later in time; lastCheckId is earlier
	 */
	public void printDiff(int checkId, int lastCheckId) {
		if (lastCheckId == 0) {
			double time = (cptimes.get(checkId) - cptimes.get(lastCheckId));
			double swf = time / 1000000000;
			System.out.println("Measurepoint: " + cpnames.get(checkId));
			System.out.println("Elapsed time since start of '" + cpnames.get(lastCheckId) + "': " + swf + " seconds\n");
		} else {
			double time = (cptimes.get(checkId) - cptimes.get(lastCheckId));
			double swf = time / 1000000000;
			System.out.println("Measurepoint: " + cpnames.get(checkId));
			System.out.println(
					"Elapsed time since measurepoint '" + cpnames.get(lastCheckId) + "': " + swf + " seconds\n");
		}
	}

	/**
	 * prints all points from start to end with differences to the measurement point
	 * respectively before the one thats currently printed; (dont use if set is not
	 * finished yet (i think?)
	 */
	public String getEvalutation() {
		int count = cpnames.size();
		StringBuilder builder = new StringBuilder();
		builder.append(
				"##################################################################################################\n\n");
		for (int i = 0; i < count; i++) {
			if (i == 0) {
				builder.append(cpnames.get(0) + ":\n\n");
			} else if (i < count - 1) {
				double time = (cptimes.get(i) - cptimes.get(i - 1));
				double swf = time / 1000000000;
				builder.append("Measurepoint: " + cpnames.get(i) + "\n");
				builder.append("Elapsed time since last measurepoint: " + swf + " seconds\n\n");
			} else {
				double time = (cptimes.get(i) - cptimes.get(i - 1));
				double swf = time / 1000000000;
				builder.append("End of '" + cpnames.get(0) + "'\n");
				// builder.append("Elapsed time since last measurepoint: " + swf + "
				// seconds\n");
				swf = getTotalElapsedTime();
				builder.append("Total elapsed time: " + swf + " seconds\n\n");
			}
		}
		builder.append(
				"##################################################################################################\n");

		return builder.toString();
	}
	
	/**
	 * Returns a map-based table that represents a table containg
	 * all check-points and their points in time.
	 */
	public Map<String, Double> getEvaluationTable() {
		Map<String, Double> result = new HashMap<>();
		int count = cpnames.size();
		
		for (int i = 0; i < count; i++) {
			if (i == 0) {
				result.put(cpnames.get(0), 0.0);
			} else if (i < count - 1) {
				double time = (cptimes.get(i) - cptimes.get(0));
				double swf = time / 1000000000;
				result.put(cpnames.get(i), swf);
			} else {
				double swf = getTotalElapsedTime();
				result.put("Total", swf);
			}
		}

		return result;
	}

	public void printAll() {
		System.out.println(getEvalutation());
	}

	/**
	 * returns elapsed time since start
	 * 
	 * @return elapsed time since start
	 */
	public double getTotalElapsedTime() {
		double time = (cptimes.get(cptimes.size() - 1) - cptimes.get(0));
		double swf = time / 1000000000;
		return swf;
	}
}
