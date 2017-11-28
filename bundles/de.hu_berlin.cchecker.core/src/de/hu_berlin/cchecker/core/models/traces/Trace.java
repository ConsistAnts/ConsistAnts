package de.hu_berlin.cchecker.core.models.traces;

import java.util.Comparator;
import java.util.List;

/**
 * This class models a single trace of software behavior with an id as well as a list of transitions in order.
 * @author Linus
 *
 */
public class Trace {

	private static final Comparator<OrderedTransition> COMPARE_TRANSITIONS_BY_ORDINAL =  
			(OrderedTransition t1, OrderedTransition t2) -> Integer.compare(t1.getOrdinal(), t2.getOrdinal());
	
	/**
	 * Unique id of this trace, can be used to identify it.
	 */
	private String id;
	
	/**
	 * List of transitions happening in chronological order.
	 */
	private List<OrderedTransition> transitions;
	
	public Trace() {
		// Default constructor for json serialization.
	}
	
	/**
	 * Returns a new instance of Trace with the given id and list of transitions.
	 */
	public Trace(String id, List<OrderedTransition> transitions) {
		super();
		this.id = id;
		this.transitions = transitions;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		return id.equals(((Trace) obj).id);
	}

	/**
	 * Returns the id of this trace.
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Returns the list of transitions sorted by their ordinal, lowest to highest.
	 */
	public List<OrderedTransition> getTransitions() {
		transitions.sort(COMPARE_TRANSITIONS_BY_ORDINAL);
		return transitions;
	}

	public void setTransitions(List<OrderedTransition> transitions) {
		this.transitions = transitions;
	}
}
