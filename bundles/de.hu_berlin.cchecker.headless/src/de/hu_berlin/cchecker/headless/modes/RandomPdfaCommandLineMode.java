package de.hu_berlin.cchecker.headless.modes;

import java.util.List;
import java.util.Random;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.headless.AbstractCommandLineMode;
import de.hu_berlin.cchecker.headless.Argument;
import de.hu_berlin.cchecker.headless.CommandLineArgumentException;
import de.hu_berlin.cchecker.headless.HeadlessIOUtils;
import de.hu_berlin.cchecker.headless.consumers.ArgumentConsumer;
import de.hu_berlin.cchecker.headless.consumers.KeyValueArgumentConsumer;
import de.hu_berlin.cchecker.headless.consumers.ToggleArgumentConsumer;
import de.hu_berlin.cchecker.tools.pdfa.generation.RandomProbabilisticAutomatonGenerator;
import de.hu_berlin.cchecker.tools.pdfa.generation.RandomProbabilisticAutomatonGenerator.Range;

/**
 * CLI for the generation of random PDFA.
 * 
 * @author lucabeurer-kellner
 *
 */
public class RandomPdfaCommandLineMode extends AbstractCommandLineMode {

	private RangeArgumentConsumer numberOfStatesConsumer = new RangeArgumentConsumer("number-of-states",
			"Range of the possible number of states. (e.g. 4...6)", false,
			RandomProbabilisticAutomatonGenerator.DEFAULT_RANGE_OF_STATES);

	private RangeArgumentConsumer numberOfTransitionsConsumer = new RangeArgumentConsumer("number-of-transitions",
			"Range of the possible number of outgoing transitions per state. (e.g. 4...6)", false,
			RandomProbabilisticAutomatonGenerator.DEFAULT_RANGE_OF_OUTGOING_TRANSITIONS_PER_STATE);

	private RangeArgumentConsumer sizeOfAlphabetLabels = new RangeArgumentConsumer("size-of-alphabet",
			"Range of the possible size of the alphabet (distinct labels). (e.g. 4...6)", false,
			RandomProbabilisticAutomatonGenerator.DEFAULT_RANGE_OF_DISTINCT_LABELS);

	private KeyValueArgumentConsumer<Double> selfCycleConsumer = KeyValueArgumentConsumer.extract(
			new Argument(true, "self-cycle-probability", "The probability that a state has a transition to itself", "double"),
			RandomProbabilisticAutomatonGenerator.DEFAULT_SELF_CYCLE_PROBABILITY, Double::parseDouble);

	private KeyValueArgumentConsumer<Long> seedConsumer = KeyValueArgumentConsumer.extract(
			new Argument(true, "seed", "An integer seed to use for initialization of the random number generator.", "integer"), -1,
			Long::parseLong);
	
	private ToggleArgumentConsumer humanReadableConsumer = new ToggleArgumentConsumer(
			new Argument(true, "h", "Use a human-readable PDFA output format.  (not usable for further processing)"));

	public RandomPdfaCommandLineMode() {
		super("random-pdfa", "Generate a random PDFA");

		registerConsumers(numberOfStatesConsumer, numberOfTransitionsConsumer, sizeOfAlphabetLabels, selfCycleConsumer,
				seedConsumer, humanReadableConsumer);
	}

	@Override
	public void run(List<String> mode) {		
		long seed = seedConsumer.getValueOr(new Random().nextLong());
		RandomProbabilisticAutomatonGenerator generator = new RandomProbabilisticAutomatonGenerator(seed);
		
		verboseLog("seed=" + seed);

		ProbabilisticAutomata result = generator.createRandom(numberOfStatesConsumer.getValue(),
				numberOfTransitionsConsumer.getValue(), sizeOfAlphabetLabels.getValue(), selfCycleConsumer.getValue());

		out(HeadlessIOUtils.automatonToString(result, humanReadableConsumer.isPresent()));
		performanceOut(generator);
	}
	
	/**
	 * An {@link ArgumentConsumer} for {@link Range}.
	 */
	private static class RangeArgumentConsumer extends KeyValueArgumentConsumer<Range> {
		private Range defaultValue;

		public RangeArgumentConsumer(String name, String description, boolean optional, Range defaultValue) {
			super(new Argument(optional, name, description, "range"));
			this.defaultValue = defaultValue;
		}

		@Override
		protected Range getDefault() {
			return this.defaultValue;
		}

		@Override
		protected Range extractValue(String argument) {
			String[] values = argument.split("\\.\\.\\.");

			if (values.length != 2) {
				throwParseError(argument);
			}
			try {
				int from = Integer.parseInt(values[0]);
				int to = Integer.parseInt(values[0]);

				return Range.fromTo(from, to);
			} catch (NumberFormatException e) {
				throwParseError(argument);
			}

			throwParseError(argument);
			return null;
		}

		private void throwParseError(String argument) {
			throw new CommandLineArgumentException("Failed to parse range from '" + argument
					+ "'. Format is FROM...TO where FROM and TO are integers.");
		}

	}
}
