package de.hu_berlin.cchecker.core.models.traces;

/**
 * This class models a transition by its id as well as an ordinal.
 * 
 * @author Linus
 *
 */
public class OrderedTransition {
	
	/**
	 * Id of the transition happening.
	 */
	private int id;
	
	/**
	 * Ordinal of transition, lowest ordinals happen first.
	 */
	private int ordinal;
	
	public OrderedTransition() {
		// Default constructor for json serialization.
	}
	
	/**
	 * Returns a new instance of OrderedTransition with the given id and ordinal.
	 */
	public OrderedTransition(int id, int ordinal) {
		super();
		this.id = id;
		this.ordinal = ordinal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ordinal;
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
		OrderedTransition other = (OrderedTransition) obj;
		return ordinal == other.ordinal && id == other.id;
	}

	@Override
	public String toString() {
		return Integer.toString(id);
	}
	
	/**
	 * Returns the id of the transition happening.
	 */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Returns the ordinal of this transition.
	 */
	public int getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}
}
