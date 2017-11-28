package de.hu_berlin.cchecker.core.models.pdfa;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * A visitor for {@link ProbabilisticAutomata}s.
 */
public interface ProbabilisticAutomataVisitor {
	public default void visit(ProbabilisticState state) {}
	public default void visit(ProbabilisticTransition transition) {}
	
	/**
	 * Returns a breadth-first-ordered stream of states of the given automaton.
	 */
	public static Stream<ProbabilisticState> stateStream(ProbabilisticAutomata automaton) {
		List<ProbabilisticState> states = new ArrayList<>();
		
		visit(automaton, new ProbabilisticAutomataVisitor() {
			@Override
			public void visit(ProbabilisticState state) {
				states.add(state);
			}
		});
		
		return states.stream();
	}
	
	/**
	 * Returns a breadth-first-ordered stream of transitions of the given automaton.
	 */
	public static Stream<ProbabilisticTransition> transitionStream(ProbabilisticAutomata automaton) {
		List<ProbabilisticTransition> transitions = new ArrayList<>();
		
		visit(automaton, new ProbabilisticAutomataVisitor() {
			@Override
			public void visit(ProbabilisticTransition transition) {
				transitions.add(transition);
			}
		});
		
		return transitions.stream();
	}
	
	/**
	 * Does a breadth-first-visit of the automata with the given visitor.
	 * 
	 * The method will visit every state directly followed by all its transitions.
	 * 
	 * Every element is visited exactly once.
	 */
	public static void visit(ProbabilisticAutomata automata, ProbabilisticAutomataVisitor visitor) {
		Deque<ProbabilisticState> stateStack = new ArrayDeque<>();
		stateStack.push(automata.getStartState());
		Set<ProbabilisticState> visitedStates = new HashSet<>();
	
		while (!stateStack.isEmpty()) {
		    ProbabilisticState s = stateStack.removeLast();
		    visitedStates.add(s);
		    
		    visitor.visit(s);
		    
		    // Create a copy of the transitions list
		    List<ProbabilisticTransition> transitions = 
		    		new ArrayList<ProbabilisticTransition>(s.getOutgoingTransitions());
		    
		    // Sort transitions by label
		    transitions.sort((o1, o2) -> Comparator
					.comparing(ProbabilisticTransition::getTransitionId)
					.thenComparing(t -> t.getTarget().getStateId())
					.compare(o1, o2));
		    
		    for (ProbabilisticTransition transition : transitions) {
		        ProbabilisticState target = transition.getTarget();
		        
		        visitor.visit(transition);
		        
		        // Only add to stack if unvisited and not planning to visit anyway
		        if (!visitedStates.contains(target) && !stateStack.contains(target)) {
		            stateStack.push(target);
		        }
		    }
		}
	}
}