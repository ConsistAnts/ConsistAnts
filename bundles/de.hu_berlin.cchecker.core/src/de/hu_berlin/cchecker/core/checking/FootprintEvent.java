package de.hu_berlin.cchecker.core.checking;

/**
 * A Footprint Event is the concatenation of two transitions.
 * 
 * @author HorazVitae
 */
public class FootprintEvent {

	/**
	 * name of event as string by concatenating both saved transitions
	 */
	private String name;

	/**
	 * id of first transition in event
	 */
	private int firstId;

	/**
	 * id of second transition in event
	 */
	private int secondId;

	/**
	 * Instantiates new event with concatenated event name, first transition and
	 * second transition IDs.
	 */
	public FootprintEvent(String n, int firstId, int secondId) {
		this.name = n;
		this.firstId = firstId;
		this.secondId = secondId;
	}

	/**
	 * Returns concatenated event name.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Returns the ID of the first transition.
	 */
	public int getFirstId() {
		return this.firstId;
	}

	/**
	 * Returns the ID of the second transition
	 */
	public int getSecondId() {
		return this.secondId;
	}

}
