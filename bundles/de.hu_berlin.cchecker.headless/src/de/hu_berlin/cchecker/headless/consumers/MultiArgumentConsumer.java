package de.hu_berlin.cchecker.headless.consumers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import de.hu_berlin.cchecker.headless.Argument;

/**
 * An {@link ArgumentConsumer} that delegates consumption to multiple
 * child consumers.
 */
public class MultiArgumentConsumer implements ArgumentConsumer {
	private List<ArgumentConsumer> consumers;

	public MultiArgumentConsumer(List<? extends ArgumentConsumer> consumers) {
		this.consumers = new ArrayList<>(consumers);
	}

	@Override
	public Map<String, String> consume(Map<String, String> arguments) {
		Map<String, String> args = arguments;
		for (ArgumentConsumer c : consumers) {
			args = c.consume(args);
		}
		return args;
	}

	@Override
	public List<Argument> getMissingArguments() {
		return consumers.stream()
			.map(ArgumentConsumer::getMissingArguments)
			.flatMap(List<Argument>::stream)
			.collect(Collectors.toList());
	}
}
