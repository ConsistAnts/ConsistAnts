package de.hu_berlin.cchecker.headless.consumers;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import de.hu_berlin.cchecker.headless.Argument;
import de.hu_berlin.cchecker.headless.CommandLineArgumentException;

/**
 * A simple key value argument that uses the abstract method
 * {@link #extractValue(String)} to extract the actual value of type T from the
 * string argument.
 *
 * @param <T>
 */
public abstract class KeyValueArgumentConsumer<T> implements ArgumentConsumer {

	/**
	 * Creates a new {@link KeyValueArgumentConsumer} for the given argument.
	 * 
	 * @param argument
	 *            The argument to consume
	 * @param extractor
	 *            The extractor method to use
	 * @return
	 */
	public static <T> KeyValueArgumentConsumer<T> extract(Argument argument, Function<String, T> extractor) {
		return new KeyValueArgumentConsumer<T>(argument) {
			@Override
			protected T extractValue(String argument) {
				return extractor.apply(argument);
			}
		};
	}

	/**
	 * Creates a new {@link KeyValueArgumentConsumer} for the given argument.
	 * 
	 * @param argument
	 *            The argument to consume
	 * @param defaultValue
	 *            The default value if not supplied and optional
	 * @param extractor
	 *            The extractor method to use
	 * @return
	 */
	public static <T> KeyValueArgumentConsumer<T> extract(Argument argument, T defaultValue,
			Function<String, T> extractor) {
		return new KeyValueArgumentConsumer<T>(argument) {
			@Override
			protected T extractValue(String argument) {
				return extractor.apply(argument);
			}

			@Override
			protected T getDefault() {
				return defaultValue;
			}
		};
	}

	private final Argument argument;
	private T value = null;

	public KeyValueArgumentConsumer(Argument argument) {
		this.argument = argument;
	}

	@Override
	public Map<String, String> consume(Map<String, String> arguments) {
		final String key = getArgument().getKeyword();
		final String argumentValue = arguments.get(key);
		
		if (null == argumentValue) {
			return arguments;
		}
		
		try {
			this.setValue(extractValue(argumentValue));
		} catch (RuntimeException e) {
			throw new CommandLineArgumentException("Failed to parse argument " + this.getArgument().getKeyword());
		}

		arguments.remove(key);

		return arguments;
	}

	/**
	 * Extracts the actual value of this consumer from the given string argument and
	 * returns it.
	 */
	protected abstract T extractValue(String argument);

	/**
	 * Returns a default value for this argument consumer.
	 * 
	 * If the default value is null, it is assumed to not exist.
	 * 
	 * By default this method returns null.
	 */
	protected T getDefault() {
		return null;
	}

	@Override
	public List<Argument> getMissingArguments() {
		if (hasValue() || getArgument().isOptional()) {
			return Collections.emptyList();
		} else {
			return Collections.singletonList(getArgument());
		}
	}

	/**
	 * Returns true if this consumer holds a value.
	 * 
	 * @return
	 */
	public boolean hasValue() {
		return this.getValue() != null || getDefault() != null;
	}

	/**
	 * Returns the value of this argument consumer.
	 */
	public T getValue() {
		if (null != this.value) {
			return this.value;
		} else {
			return this.getDefault();
		}
	}
	
	/**
	 * Returns the value of this consumer or the given alternative.
	 * 
	 * This method ignores any default values (@see {@link #getDefault()})
	 */
	public T getValueOr(T alternative) {
		if (null == this.value) {
			return alternative;
		} else {
			return this.value;
		}
	}

	/**
	 * Sets the value of this consumer
	 */
	private void setValue(T value) {
		this.value = value;
	}

	/**
	 * Returns the argument description of this consumer.
	 */
	public Argument getArgument() {
		return argument;
	}

}
