package de.hu_berlin.cchecker.core.models.pdfa;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.common.util.EMap;

import com.google.common.base.Function;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;

/**
 * Builder class for {@link ProbabilisticAutomata} to simplify the 
 * tedious manual creation of {@link ProbabilisticAutomata}s.
 */
public class ProbabilisticAutomataBuilder {
	
	/**
	 * Creates a new empty automaton builder instance.
	 */
	public static ProbabilisticAutomataBuilder newAutomaton() {
		return new ProbabilisticAutomataBuilder();
	}
	
	/**
	 * Returns a new builder instance which is already configured to create a copy of
	 * the given {@link ProbabilisticAutomata}.
	 * 
	 * Just invoke {@link #create()} on the returned builder instance to retrieve a
	 * copy of the given instance.
	 */
	public static ProbabilisticAutomataBuilder copyOf(ProbabilisticAutomata automaton) {
		ProbabilisticAutomataBuilder builder = ProbabilisticAutomataBuilder.newAutomaton();
		
		// copy start state
		builder.startIn(automaton.getStartState().getStateId());
		
		ProbabilisticAutomataVisitor.visit(automaton, new ProbabilisticAutomataVisitor() {
			@Override
			public void visit(ProbabilisticState state) {
				// copy terminating states and termination probabilities
				if (state.getTerminatingProbability() > 0) {
					builder.terminatesInWithProbability(state.getStateId(), state.getTerminatingProbability());
				} else if (state.isTerminating()) {
					builder.terminatesIn(state.getStateId());
				}
			}
			
			@Override
			public void visit(ProbabilisticTransition transition) {
				// copy all transitions and their probabilities
				builder.fromTo(transition.getSource().getStateId(), transition.getTarget().getStateId(), transition.getProbability(), transition.getTransitionId());
			}
		});
		
		// copy label mappings
		builder.labelMapping(new HashMap<>(automaton.getTransitionLabels().map()));
		
		return builder;
	}
	
	private BiMap<Integer, String> idToLabelMapping = HashBiMap.create();
	private BiMap<String, Integer> labelToIdMapping = idToLabelMapping.inverse();
	private	Map<Integer, ProbabilisticState> states = new HashMap<>();
	// keeps track of all used transition IDs 
	private Set<Integer> labelIds = new HashSet<>();
	
	private PdfaFactory factory = PdfaFactory.eINSTANCE;
	
	private ProbabilisticState startState = null;
	
	private ProbabilisticAutomataBuilder() {
		// Prevent direct instantiation.
	}
	
	/**
	 * Construct a new transition between fromState to toState with the given probability and label.
	 */
	public ProbabilisticAutomataBuilder fromTo(int fromState, int toState, double probability, String label) {
		int maxLabelId = idToLabelMapping.isEmpty() ? 0 : Collections.max(idToLabelMapping.keySet());
		labelToIdMapping.putIfAbsent(label, maxLabelId + 1);
		
		int labelId = labelToIdMapping.get(label);
		
		// keep track of used transition IDs
		this.labelIds.add(labelId);
		
		return this.fromTo(fromState, toState, probability, labelId);
	}
	
	/**
	 * Construct a new transition between fromState to toState with the given probability and label id.
	 * 
	 * <p>Note that when using this method, that labelId must have a valid mapping in the label map of
	 * this builder. Otherwise an invalid model may be produced. </p>
	 */
	public ProbabilisticAutomataBuilder fromTo(int fromState, int toState, double probability, int labelId) {
		ProbabilisticState s1 = getOrCreateState(fromState);
		ProbabilisticState s2 = getOrCreateState(toState);
		
		ProbabilisticTransition t = factory.createProbabilisticTransition();
		t.setSource(s1);
		t.setTarget(s2);
		t.setProbability(probability);
		t.setTransitionId(labelId);
		
		s1.getOutgoingTransitions().add(t);
		
		labelIds.add(labelId);
		
		return this;
	}
	
	/**
	 * Extends (overrides) label mappings of the automata builder.
	 */
	public ProbabilisticAutomataBuilder labelMapping(Map<Integer, String> labelMap) {
		this.idToLabelMapping.putAll(labelMap);
		return this;
	}
	
	/**
	 * Extends (overrides) label mappings of the automata builder.
	 */
	public ProbabilisticAutomataBuilder labelMapping(Integer id, String label) {
		this.idToLabelMapping.put(id, label);
		return this;
	}
	
	/**
	 * Adds or replaces the label mapping given by id -> label
	 */
	public ProbabilisticAutomataBuilder labelMapping(int id, String label) {
		this.idToLabelMapping.put(id, label);
		return this;
	}
	
	/**
	 * Replaces the current label-mapping with an automatic one.
	 * 
	 * The automatic mappings maps the transition IDs to the sequence 
	 * of ASCII characters starting with 'A'. 
	 * 
	 * Note that this automatic mapping is limited by the number characters available.
	 */
	public ProbabilisticAutomataBuilder automaticLabelMapping() {
		this.idToLabelMapping.clear();

		// generate automatic mapping
		Map<Integer, String> automaticMapping = Maps.asMap(this.labelIds, new Function<Integer, String>() {
			private int currentChar = 'A';
			@Override
			public String apply(Integer labelId) {
				return String.valueOf((char)(currentChar++));
			}
		});
		
		this.idToLabelMapping.putAll(automaticMapping);
		return this;
	}
	
	/**
	 * Sets the state with the given id to be a terminating state.
	 * 
	 * Also sets the terminating probability of the state to 1.0.
	 */
	public ProbabilisticAutomataBuilder terminatesIn(int stateId) {
		ProbabilisticState state = getOrCreateState(stateId);
		state.setTerminating(true);
		state.setTerminatingProbability(1.0);
		return this;
	}
	
	/**
	 * Sets the termination probability for the state with the given id.
	 */
	public ProbabilisticAutomataBuilder terminatesInWithProbability(int stateId, double probability) {
		ProbabilisticState s = getOrCreateState(stateId);
		s.setTerminating(probability > 0);
		s.setTerminatingProbability(probability);
		return this;
	}
	
	/**
	 * Sets the state with the given id as the start state. 
	 * 
	 * <p> Note that there can only be one start state. Multiple invokations of 
	 * this method on the same builder will override previous start states.</p>
	 */
	public ProbabilisticAutomataBuilder startIn(int stateId) {
		startState = getOrCreateState(stateId);
		return this;
	}
	
	/**
	 * Create the {@link ProbabilisticAutomata} specified with this builder.
	 */
	public ProbabilisticAutomata create() {
		ProbabilisticAutomata a = factory.createProbabilisticAutomata();
		
		// set start state
		a.setStartState(this.startState);
		
		// keep track of unmapped label IDs
		Set<Integer> unmappedLabelIds = new HashSet<>(labelIds);
		
		// set transition label mappings
		EMap<Integer, String> labelMap = a.getTransitionLabels();
		for (Entry<Integer, String> idToStringEntry: idToLabelMapping.entrySet()) {
			Integer labelId = idToStringEntry.getKey();
			labelMap.put(labelId, idToStringEntry.getValue());
			
			// remove label ID from unmapped set
			unmappedLabelIds.remove(labelId);
		}
		
		// if label IDs remain unmapped...
		if (!unmappedLabelIds.isEmpty()) {
			// abort since this will yield in an invalid {@link ProbabilisticAutomata} model
			throw new IllegalStateException("The following label IDs were used for transitions but weren't mapped to labels: " + unmappedLabelIds.toString());
		}
		
		// add all states
		for (Entry<Integer, ProbabilisticState> stateEntry : states.entrySet()) {
			a.getStates().add(stateEntry.getValue());
		}
		
		return a;
	}

	private ProbabilisticState getOrCreateState(int id) {
		if (!states.containsKey(id)) {
			ProbabilisticState s = factory.createProbabilisticState();
			s.setStateId(id);
			s.setTerminatingProbability(0.0);
			states.put(id, s);
			return s;
		} else {
			return states.get(id);
		}
	}
}
