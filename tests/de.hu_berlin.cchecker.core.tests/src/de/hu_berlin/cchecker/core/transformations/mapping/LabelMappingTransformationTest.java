package de.hu_berlin.cchecker.core.transformations.mapping;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomataBuilder;
import de.hu_berlin.cchecker.core.models.pdfa.tests.AbstractProbabilisticAutomataTest;
import de.hu_berlin.cchecker.core.models.pdfa.tests.ProbabilisticAutomataTestUtils;
import de.hu_berlin.cchecker.core.transformations.Transformation.ConditionNotFulfilledException;
import de.hu_berlin.cchecker.core.transformations.Transformation.PreconditionNotFulfilledException;

/**
 * Tests for the {@link LabelMappingTransformation}.
 *
 */
public class LabelMappingTransformationTest extends AbstractProbabilisticAutomataTest {
	public LabelMappingTransformationTest() {
		super("res/");
	}

	protected LabelMappingTransformation createTransformation(Map<String, Integer> targetMapping) {
		return new LabelMappingTransformation(targetMapping);
	}

	/**
	 * Tests the label re-mapping transformation with the alergia task 1 result automaton.
	 */
	@Test
	public void testTask1Automaton() throws ConditionNotFulfilledException {
		ProbabilisticAutomata a = ProbabilisticAutomataTestUtils.createTask1AlergiaResult();

		HashMap<String, Integer> testMapping = new HashMap<>();
		testMapping.put("s", 42);
		testMapping.put("w", 44);
		testMapping.put("t", 43); // w > t must be true, otherwise the visiting order changes
		testMapping.put("l", 45);

		LabelMappingTransformation transformation = createTransformation(testMapping);

		ProbabilisticAutomata transformed = transformation.safeTransform(a);
		assertEquals("Re-mapped automaton has same string representation after transformation", a.toString(),
				transformed.toString());
	}

	/**
	 * Tests that the pre-condition for the transformation fails, if the target
	 * mapping isn't complete.
	 */
	@Test
	public void testIncompleteTargetMapping() {
		ProbabilisticAutomata a = ProbabilisticAutomataTestUtils.createTask1AlergiaResult();

		HashMap<String, Integer> testMapping = new HashMap<>();
		testMapping.put("s", 42);
		testMapping.put("w", 44);

		LabelMappingTransformation transformation = createTransformation(testMapping);

		assertException("Incomplete target mapping fails to transform", PreconditionNotFulfilledException.class,
				() -> transformation.safeTransform(a));
	}

	/**
	 * Tests the label re-mapping transformation with a simple two transition example.
	 */
	@Test
	public void testSimpleReMapping() throws ConditionNotFulfilledException {
		ProbabilisticAutomata simpleAutomaton = ProbabilisticAutomataBuilder.newAutomaton()
			.startIn(1)
			.fromTo(1, 2, 1.0, "a")
			.fromTo(2, 3, 1.0, "b")
			.terminatesIn(3)
			.create();

		HashMap<String, Integer> testMapping = new HashMap<>();
		testMapping.put("a", 42);
		testMapping.put("b", 43);

		LabelMappingTransformation transformation = createTransformation(testMapping);

		ProbabilisticAutomata transformed = transformation.safeTransform(simpleAutomaton);
		assertEquals("Re-mapped automaton has same string representation after transformation",
				simpleAutomaton.toString(), transformed.toString());

	}
}
