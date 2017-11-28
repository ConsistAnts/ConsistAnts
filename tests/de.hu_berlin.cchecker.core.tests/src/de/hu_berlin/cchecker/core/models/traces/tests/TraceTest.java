package de.hu_berlin.cchecker.core.models.traces.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.hu_berlin.cchecker.core.models.traces.OrderedTransition;
import de.hu_berlin.cchecker.core.models.traces.Trace;

/**
 * 
 * @author Linus
 *
 */
public class TraceTest {

	@Test
	public void shouldTestThatTransitionAreReturnedInOrder() {
		List<OrderedTransition> trace1transitions = new ArrayList<>();
		OrderedTransition firstTransition = new OrderedTransition(1, 1);
		OrderedTransition secondTransition = new OrderedTransition(2, 2);
		OrderedTransition thirdTransition = new OrderedTransition(3, 3);
		OrderedTransition fourthTransition = new OrderedTransition(2, 4);
		trace1transitions.add(fourthTransition);
		trace1transitions.add(secondTransition);
		trace1transitions.add(firstTransition);
		trace1transitions.add(thirdTransition);
		Trace trace = new Trace("1", trace1transitions);
		
		List<OrderedTransition> sortedTransitions = trace.getTransitions();
		assertEquals("The first transition should be at index 0!", firstTransition, sortedTransitions.get(0));
		assertEquals("The second transition should be at index 1!", secondTransition, sortedTransitions.get(1));
		assertEquals("The third transition should be at index 2!", thirdTransition, sortedTransitions.get(2));
		assertEquals("The fourth transition should be at index 3!", fourthTransition, sortedTransitions.get(3));
	}
}
