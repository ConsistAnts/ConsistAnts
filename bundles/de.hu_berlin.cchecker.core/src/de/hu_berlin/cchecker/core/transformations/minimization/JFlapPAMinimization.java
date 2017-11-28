package de.hu_berlin.cchecker.core.transformations.minimization;

import java.util.Collection;
import java.util.Optional;

import org.eclipse.emf.common.util.EMap;

import com.google.common.collect.HashBiMap;

import de.hu_berlin.cchecker.core.checking.Pair;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomataBuilder;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomataVisitor;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticState;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticTransition;
import de.hu_berlin.cchecker.core.stopwatch.StopWatchable;
import de.hu_berlin.cchecker.core.stopwatch.Stopwatch;
import de.hu_berlin.cchecker.core.transformations.singlefinalstate.FromSingleFinalStateTransformation;
import de.hu_berlin.cchecker.core.transformations.singlefinalstate.FromSingleFinalStateTransformationImpl;
import de.hu_berlin.cchecker.core.transformations.singlefinalstate.ToSingleFinalStateTransformation;
import de.hu_berlin.cchecker.core.transformations.singlefinalstate.ToSingleFinalStateTransformationImpl;
import edu.duke.cs.jflap.automata.Automaton;
import edu.duke.cs.jflap.automata.State;
import edu.duke.cs.jflap.automata.Transition;
import edu.duke.cs.jflap.automata.fsa.FSATransition;
import edu.duke.cs.jflap.automata.fsa.FiniteStateAutomaton;

/**
 * Uses Jflap's minimization algorithm to minimize the
 * <code>ProbabilisticAutomata</code>.
 * 
 * @author Daniel - rychlewd
 */
public class JFlapPAMinimization implements PAMinimization, StopWatchable {

	private final boolean useCharacterMapping;
	private Stopwatch stopwatch;

	/**
	 * Instantiates a new {@link JFlapPAMinimization}.
	 */
	public JFlapPAMinimization() {
		this(false);
	}

	/**
	 * Instantiates a new {@link JFlapPAMinimization}.
	 * 
	 * @param useCharacterMapping
	 *            Specifies whether character mapping should be used.
	 */
	public JFlapPAMinimization(boolean useCharacterMapping) {
		super();
		this.useCharacterMapping = useCharacterMapping;
	}

	/**
	 * Method which applies Jflap's minimization algorithm to a
	 * <code>ProbabilisticAutomata</code> and returns the minimized
	 * <code>ProbabilisticAutomata</code>. 
	 */
	public ProbabilisticAutomata minimize(ProbabilisticAutomata unminimizedInput) {
		stopwatch = new Stopwatch();
		stopwatch.start("Automaton minimization");
		// basic input validation
		if (unminimizedInput == null) {
			return null;
		}
		if (unminimizedInput.getStates().isEmpty()) {
			return ProbabilisticAutomataBuilder.newAutomaton().create();
		}

		//// Transform the input automaton so that it only contains a single final state
		//// with termination probability 1.0
		
		// find an unused label mapping (terminal in the alphabet)
		ToSingleFinalStateTransformation toSingleFinalStateTransformation = new ToSingleFinalStateTransformationImpl();
		Pair<Integer, String> unusedMapping = ToSingleFinalStateTransformation
			.getUnusedTerminalMapping(unminimizedInput);

		// transform the automaton
		ProbabilisticAutomata singleFinalStateAutomaton = toSingleFinalStateTransformation.transform(unminimizedInput, unusedMapping);
		
		Optional<ProbabilisticState> finalState = ProbabilisticAutomataVisitor.stateStream(singleFinalStateAutomaton)
			.filter(s -> s.getTerminatingProbability() > 0)
			.findFirst();
		
		if (!finalState.isPresent()) {
			return createSingleStateAlphabetAutomaton(singleFinalStateAutomaton.getTransitionLabels().map().values());
		}
		
		int singleFinalStateId =  finalState.get().getStateId();
		
		stopwatch.checkpoint("To be minimized automaton converted to JFlap format");

		// do actual minimization on transformed input
		ProbabilisticAutomata minimizedSingleFinalStateAutomaton = doMinimize(singleFinalStateAutomaton, singleFinalStateId,
				unusedMapping);
				
		stopwatch.checkpoint("Minimization JFlap result converted back into use format");

		// transform the automaton back so that it gets back all the original final states the original input had
		FromSingleFinalStateTransformation fromSingleFinalStateTransformation = new FromSingleFinalStateTransformationImpl();
		ProbabilisticAutomata transformedOutput = fromSingleFinalStateTransformation
			.transform(minimizedSingleFinalStateAutomaton, unusedMapping);
		
		stopwatch.checkpoint("Minimization result back-transformed from single-state automaton");
		stopwatch.finish();

		return transformedOutput;

	}
	
	/**
	 * Returns an automaton that is just a single state that has a self-transition
	 * for each terminal in the given alphabet.
	 * 
	 * The single state has termination probability 0.0.
	 * 
	 * Each transition is equally likely.
	 * 
	 * The automaton starts in its single state.
	 */
	private ProbabilisticAutomata createSingleStateAlphabetAutomaton(Collection<String> alphabet) {
		ProbabilisticAutomataBuilder builder = ProbabilisticAutomataBuilder.newAutomaton();
		final double probabilityPerTransition = 1.0 / (double)alphabet.size();
		for (String terminal : alphabet) {
			builder.fromTo(1, 1, probabilityPerTransition, terminal);
		}
		builder.startIn(1);
		return builder.create();
	}

	/**
	 * Performs the actual minimization.
	 * 
	 * The input of this method can be assumed to be a {@link ProbabilisticAutomata}
	 * that has a single final state with termination probability 1.0.
	 * 
	 * @param unminimized
	 *            The input automaton for the minimization
	 * @param finalStateId
	 *            The id of the single final state in the automaton
	 * @param unusedMapping
	 *            The mapping that is used for transitions to the single final state
	 * @return
	 */
	protected ProbabilisticAutomata doMinimize(ProbabilisticAutomata unminimized, final int finalStateId,
			Pair<Integer, String> unusedMapping) {
		// Convert ProbabilisticAutomata unminimized to the Automaton format which is
		// used by JFlap
		Automaton inputForJflap = traverseStates(unminimized);
		createAllTransitionsInJFlapAutomaton(unminimized, inputForJflap);

		// Use JFlap to minimize the PA
		FiniteStateAutomaton outputOfJflap = minimizePAWithJflap(inputForJflap);

		// Convert JFlap's result FiniteStateAutomaton back to a ProbabilisticAutomata
		return convertFSAtoPAWithoutMapping(unminimized, outputOfJflap, finalStateId, unusedMapping);
	}

	/**
	 * Traverses the states of the given unminimized
	 * <code>ProbabilisticAutomata</code> as part of its conversion process to an
	 * <code>Automaton</code>, the format used by Jflap.
	 * 
	 * @param unminimized
	 *            Input <code>ProbabilisticAutomata</code>, which needs to be
	 *            converted into Jflap's format
	 * @return inputForJflap <code>Automaton</code> for Jflap's minimization
	 *         algorithm
	 */
	protected Automaton traverseStates(ProbabilisticAutomata unminimized) {
		Automaton inputForJflap = new Automaton();
		ProbabilisticState startState = unminimized.getStartState();
		for (ProbabilisticState s : unminimized.getStates()) {
			State stateForJflap = inputForJflap.createStateWithId(null, s.getStateId());
			// Are we dealing with the initial state? If so, mark it as initial in the Jflap
			// automaton
			if (s.getStateId() == startState.getStateId()) {
				inputForJflap.setInitialState(stateForJflap);
			}
			// If the current state is terminating, it is a final state in Jflap
			if (s.isTerminating()) {
				inputForJflap.addFinalState(stateForJflap);
			}
		}
		return inputForJflap;
	}

	/**
	 * Traversed all transitions in the given unminized
	 * {@link ProbabilisticAutomata} and creates equivalent
	 * {@link ProbabilisticFSATransition}s in the given JFlap {@link Automaton}.
	 * 
	 * @param unminimized
	 *            The {@link ProbabilisticAutomata} to transfer the transitions from
	 * @param inputForJflap
	 *            The JFlap {@link Automaton} to transfer the transitions to.
	 */
	private void createAllTransitionsInJFlapAutomaton(ProbabilisticAutomata unminimized, Automaton inputForJflap) {
		EMap<Integer, String> idToLabelsMapping = unminimized.getTransitionLabels();
		for (ProbabilisticState s : unminimized.getStates()) {
			for (ProbabilisticTransition t : s.getOutgoingTransitions()) {
				ProbabilisticFSATransition transitionForJflap = new ProbabilisticFSATransition(
						inputForJflap.getStateWithID(t.getSource().getStateId()),
						inputForJflap.getStateWithID(t.getTarget().getStateId()),
						idToLabelsMapping.get((Object) t.getTransitionId()), t.getProbability());
				inputForJflap.addTransition(transitionForJflap);
			}
		}
	}

	/**
	 * Uses Jflap's minimization algorithm to minimize the automaton in the
	 * parameter. However, the <code>Minimizer</code> has been changed for our needs
	 * to not split multiple character transition labels.
	 * 
	 * @param inputForJflap
	 *            <code>Automaton</code> to be minimized
	 * @return Minimized <code>FiniteStateAutomaton</code>
	 */
	protected FiniteStateAutomaton minimizePAWithJflap(Automaton inputForJflap) {
		Minimizer m = new Minimizer();
		m.initializeMinimizer();
		inputForJflap = m.getMinimizeableAutomaton(inputForJflap);
		return m.getMinimumDfa(inputForJflap, m.getDistinguishableGroupsTree(inputForJflap));
	}

	/**
	 * Converts Jflap's minimized <code>FiniteStateAutomaton</code> to the format of
	 * a <code>ProbabilisticAutomata</code>.
	 * 
	 * @param unminimized
	 *            The original (unminimized) <code>ProbabilisticAutomata</code>,
	 *            from which the start state's ID is extracted
	 * @param characterMapping
	 *            Contains the character mapping from the encoded character to both
	 *            the human-readable label (without probability) and the probability
	 *            of the transition type which has been coded with this character.
	 *            These two properties are stored in a
	 *            <code>ProbabilisticTransitionMapping</code>
	 * @param outputOfJflap
	 *            The <code>FiniteStateAutomaton</code> which has been minimized by
	 *            Jflap and needs to be converted into our format of a of a
	 *            <code>ProbabilisticAutomata</code>
	 * @return Minimized <code>ProbabilisticAutomata</code>
	 */
	protected ProbabilisticAutomata convertFSAtoPA(ProbabilisticAutomata unminimized,
			HashBiMap<Character, ProbabilisticTransitionMapping> characterMapping, FiniteStateAutomaton outputOfJflap) {
		ProbabilisticAutomataBuilder builder = ProbabilisticAutomataBuilder.newAutomaton();
		Transition[] jflapTransitions = outputOfJflap.getTransitions();
		for (Transition t : jflapTransitions) {
			FSATransition tfsa = ((FSATransition) t);
			builder = builder.fromTo(t.getFromState().getID(), t.getToState().getID(),
					characterMapping.get(tfsa.getLabel().charAt(0)).probability,
					characterMapping.get(tfsa.getLabel().charAt(0)).label);
		}
		// Determine initial state
		builder = builder.startIn(unminimized.getStartState().getStateId()); // The Jflap minimization doesn't consider
																				// the initial state, but it shouldn't
																				// change anyway
		return builder.create();
	}

	/**
	 * Converts Jflap's minimized <code>FiniteStateAutomaton</code> to the format of
	 * a <code>ProbabilisticAutomata</code>. This method does not use character
	 * mapping.
	 * 
	 * @param unminimized
	 *            The original (unminimized) <code>ProbabilisticAutomata</code>,
	 *            from which the start state's ID is extracted
	 * @param outputOfJflap
	 *            The <code>FiniteStateAutomaton</code> which has been minimized by
	 *            Jflap and needs to be converted into our format of a of a
	 *            <code>ProbabilisticAutomata</code>
	 * @param unusedMapping
	 * @return Minimized <code>ProbabilisticAutomata</code>
	 */
	protected ProbabilisticAutomata convertFSAtoPAWithoutMapping(ProbabilisticAutomata unminimized,
			FiniteStateAutomaton outputOfJflap, int finalStateId, Pair<Integer, String> unusedMapping) {
		// create a new builder
		ProbabilisticAutomataBuilder builder = ProbabilisticAutomataBuilder.newAutomaton();
		// traverse all JFlap transitions
		for (Transition t : outputOfJflap.getTransitions()) {
			ProbabilisticFSATransition ptfsa = ((ProbabilisticFSATransition) t);

			double probabilityOfTransition = ptfsa.getProbability();
			String label = ptfsa.getActualLabel();

			// check if we are dealing with single final state transition
			if (label.equals(unusedMapping.second)) {
				// if so use given unusedMapping
				builder.fromTo(t.getFromState().getID(), t.getToState().getID(), probabilityOfTransition,
						unusedMapping.first);
			} else {
				// otherwise use auto-generated mapping of builder
				builder.fromTo(t.getFromState().getID(), t.getToState().getID(), probabilityOfTransition, label);
			}
		}

		// make sure builder is aware of unusedMapping
		builder.labelMapping(unusedMapping.first, unusedMapping.second);

		// set initial state
		builder = builder.startIn(outputOfJflap.getInitialState().getID()); // The Jflap minimization doesn't consider
																			// the initial state, but it shouldn't
																			// change anyway
		// set finalStateId to be final state
		builder.terminatesInWithProbability(finalStateId, 1.0);

		return builder.create();
	}

	/**
	 * Returns <code>true</code> if this minimizer is configured to use character
	 * mapping for the transformation of the automaton.
	 */
	public boolean getUseCharacterMapping() {
		return useCharacterMapping;
	}
	
	@Override
	public Stopwatch getStopwatch() {
				return stopwatch;
	}

	static class ProbabilisticTransitionMapping {
		public final String label;
		public final double probability;

		/**
		 * Creates a new {@link ProbabilisticTransitionMapping} with the given label and
		 * probability.
		 */
		static ProbabilisticTransitionMapping create(String label, double probability) {
			return new ProbabilisticTransitionMapping(label, probability);
		}

		private ProbabilisticTransitionMapping(String label, double probability) {
			this.label = label;
			this.probability = probability;
		}

		@Override
		public int hashCode() {
			return label.hashCode() ^ Double.hashCode(probability);
		}

		@Override
		public boolean equals(Object obj) {
			if (null == obj) {
				return false;
			}
			return this.hashCode() == obj.hashCode();
		}
	}
}
