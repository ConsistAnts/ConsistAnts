package de.hu_berlin.cchecker.headless;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A supercharged {@link StringBuilder} to create beautifully
 * formatted CLI help texts.
 */
public class HelpBuilder {
	private List<Argument> arguments = new ArrayList<>();
	private String modeName = "";
	private String description = "";
	
	private HelpBuilder() {}
	
	/**
	 * Returns a new {@link HelpBuilder} instance.
	 */
	public static HelpBuilder newHelp(String mode, String description) {
		HelpBuilder builder = new HelpBuilder();
		builder.modeName = mode;
		builder.description = description;
		
		return builder;
	}
	
	/**
	 * Adds a (possibly optional) new argument to the help text.
	 */
	public HelpBuilder argument(String keyword, String description, boolean optional) {
		this.arguments.add(new Argument(optional, keyword, description));
		return this;
	}
	
	/**
	 * Adds a new argument to the help text.
	 */
	public HelpBuilder argument(String keyword, String description) {
		return this.argument(keyword, description, false);
	}
	
	/**
	 * Adds the given {@link Iterable} of {@link Argument}s to the help text.
	 * @param args
	 * @return
	 */
	public HelpBuilder arguments(Iterable<Argument> args) {
		for (Argument a : args) {
			this.arguments.add(a);
		}
		return this;
	}
	
	/**
	 * Returns the formatted help text of this builder.
	 */
	@Override
	public String toString() {
		String header = modeName + " - " + description + "\n";
		String argumentHelp = this.arguments.stream().map(Argument::toString).collect(Collectors.joining("\n"));
		return header + argumentHelp + "\n";
	}
}
