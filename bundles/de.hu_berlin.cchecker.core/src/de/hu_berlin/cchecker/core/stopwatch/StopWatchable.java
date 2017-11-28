package de.hu_berlin.cchecker.core.stopwatch;

/**
 * Types that allow performance measuring of long-running
 * operations using {@link Stopwatch}.
 */
public interface StopWatchable {
	/**
	 * Returns the finished {@link Stopwatch} that was used in most
	 * recent invocation of the long-running operation.
	 */
	public Stopwatch getStopwatch();
}
