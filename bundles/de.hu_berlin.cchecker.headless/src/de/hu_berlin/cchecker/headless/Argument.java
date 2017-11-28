package de.hu_berlin.cchecker.headless;

/**
 * An {@link Argument} describes a command line argument a user may specify.
 */
public class Argument {
	private final boolean optional;
	private final String keyword;
	private final String description;
	private final String valueFormat;
	
	/**
	 * Initialises a new argument with the given values and without a {@link #getValueFormat()}.
	 */
	public Argument(boolean optional, String keyword, String description) {
		this(optional, keyword, description, "");
	}
	
	/**
	 * Initialises a new argument with the given values.
	 */
	public Argument(boolean optional, String keyword, String description, String valueFormat) {
		this.optional = optional;
		this.keyword = keyword;
		this.description = description;
		this.valueFormat = valueFormat;
	}
	
	/**
	 * Returns <code>true</code> if this argument is optional.
	 * <code>false</code> otherwise.
	 */
	public boolean isOptional() {
		return optional;
	}

	/**
	 * Returns the keyword of this argument.
	 * 
	 * Example: -keyword value
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * Returns a user-faced description of this argument.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Returns a user-faced value format description for this argument.
	 * 
	 * Example: Value format <integer> results in the description '-keyword <integer>' 
	 */
	public String getValueFormat() {
		return valueFormat;
	}

	@Override
	public String toString() {
		String isOptional = this.isOptional() ? "[OPTIONAL] " : "";
		String valueFormatPart = !this.valueFormat.isEmpty() ? " <" + this.valueFormat + ">" : "";
		
		return String.format("\t%s%n\t\t%s", "-" + this.keyword + valueFormatPart, isOptional + this.getDescription());
	}
}