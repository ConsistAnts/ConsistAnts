package de.hu_berlin.cchecker.headless.consumers;

import java.util.HashSet;
import java.util.Set;

import de.hu_berlin.cchecker.headless.Argument;

/**
 * A {@link ToggleArgumentConsumer} can be used to detect the presence
 * of a switch-like argument without a value.
 * 
 * {@link #getValue()} returns a {@link Boolean} according to the presence of the argument.
 * 
 * Toggle arguments are always optional.
 */
public class ToggleArgumentConsumer extends KeyValueArgumentConsumer<Boolean> {
	
	private Set<ToggleConsumedListener> listeners = new HashSet<>();
	
	public ToggleArgumentConsumer(Argument argument) {
		super(new Argument(/*hard-code optionality*/ true, argument.getKeyword(), argument.getDescription()));
	}

	@Override
	protected Boolean extractValue(String argument) {
		// notify listeners
		listeners.forEach(l -> l.toggleConsumed(this));
		// always extract true as a value
		return true;
	}
	
	@Override
	protected Boolean getDefault() {
		// return false as a default value
		return false;
	}
	
	/**
	 * Returns {@code true} iff the toggle argument was consumed.
	 */
	public boolean isPresent() {
		return this.getValue();
	}
	
	/**
	 * Adds a toggle consumed listener.
	 */
	public void addToggleListener(ToggleConsumedListener listener) {
		this.listeners.add(listener);
	}
	
	/**
	 * Remove the given toggle consumed listener if it is registered.
	 */
	public void removeToggleListener(ToggleConsumedListener listener) {
		this.listeners.remove(listener);
	}
	
	/**
	 * A listener to listen for toggle arguments to be consumed.
	 */
	@FunctionalInterface
	public interface ToggleConsumedListener {
		void toggleConsumed(ToggleArgumentConsumer consumer);
	}
}
