package de.hu_berlin.cchecker.headless.consumers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.hu_berlin.cchecker.headless.Argument;

/**
 * An {@link ArgumentConsumer} that similar to {@link MultiArgumentConsumer} delegates to multiple
 * ArgumentConsumers but also invokes a parent delegate consumer. (@see #delegate)
 *
 */
public class DelegatingArgumentConsumer extends MultiArgumentConsumer {
	private ArgumentConsumer delegate;

	/**
	 * Initialises a new {@link DelegatingArgumentConsumer} with the 
	 * given consumers and parent delegate.
	 */
	public DelegatingArgumentConsumer(List<? extends ArgumentConsumer> consumers, ArgumentConsumer parent) {
		super(consumers);
		this.delegate = parent;
	}
	
	@Override
	public Map<String, String> consume(Map<String, String> arguments) {
		Map<String, String> consumed = super.consume(arguments);
		return delegate.consume(consumed);
	}
	
	@Override
	public List<Argument> getMissingArguments() {
		ArrayList<Argument> list = new ArrayList<>(super.getMissingArguments());
		list.addAll(delegate.getMissingArguments());
		return list;
	}
}
