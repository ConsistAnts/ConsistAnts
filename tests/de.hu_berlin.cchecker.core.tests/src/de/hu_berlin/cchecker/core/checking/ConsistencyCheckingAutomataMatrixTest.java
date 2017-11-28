package de.hu_berlin.cchecker.core.checking;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomataBuilder;
import de.hu_berlin.cchecker.core.models.pdfa.tests.ProbabilisticAutomataTestUtils;
import de.hu_berlin.cchecker.core.models.traces.TraceDataset;
import de.hu_berlin.cchecker.core.models.traces.tests.TraceTestUtils;

/**
 * Basic tests regarding the automata matrix output of {@link FootprintMatrixCheckingAlgorithm}.
 *
 */
@RunWith(JUnit4.class)
public class ConsistencyCheckingAutomataMatrixTest extends AbstractFootprintMatrixTest {
	
	/**
	 * Tests the footprint matrix generation with an automaton as input (as opposed to a trace set).
	 */
	@Test
	public void testTask1WithAutomaton() {
		// Get task 1 alergia automata
		ProbabilisticAutomata alergiaResult = ProbabilisticAutomataTestUtils.createTask1AlergiaResult();
		// Get task 1 trace data
		TraceDataset task1Traces = TraceTestUtils.getTask1TraceDataset();
		
		// Extract algorithm parameters from trace data
		Map<Integer, String> labelMapping = task1Traces.getTransitionIdToLabel();
		Map<Integer, Integer> matrixIndices = FootprintMatrixCheckingAlgorithm.buildMatrixIndicesMap(labelMapping);
		
		// Generate trace matrices
		TraceFootprintMatrixGenerator traceMatrixGenerator = new TraceFootprintMatrixGenerator(matrixIndices);
		List<FPMatrix> tracematrices = traceMatrixGenerator.generate(task1Traces);
		
		// collect required matrix lengths from trace matrix output
		List<Integer> lengths = new LinkedList<Integer>();
		for (int i = 0; i < tracematrices.size(); i++) {
			lengths.add(tracematrices.get(i).getLength());
		}
		
		// generate automata matrices
		AutomatonMatrixGenerator automatonMatrixGenerator = new AutomatonMatrixGenerator(matrixIndices, lengths);
		List<FPMatrix> automatrices = automatonMatrixGenerator.generate(alergiaResult);
		
		/* Matrix format is:
		 *        start toll won lost
		 * start
		 * toll 
		 * won 
		 * lost
		 * */
		
		final String expectation = "[length=2\n" + 
				"numberOfTraces=0\n" + 
				"matrix=\n" + 
				"[0.0, 0.8095, 0.1905, 0.0]\n" + 
				"[0.0, 0.0, 0.0, 0.0]\n" + 
				"[0.0, 0.0, 0.0, 0.0]\n" + 
				"[0.0, 0.0, 0.0, 0.0]\n" + 
				", length=3\n" + 
				"numberOfTraces=0\n" + 
				"matrix=\n" + 
				"[0.0, 0.8095, 0.1905, 0.0]\n" + 
				"[0.0, 0.09252584999999999, 0.56665, 0.08095000000000001]\n" + 
				"[0.0, 0.02177415, 0.13335, 0.01905]\n" + 
				"[0.0, 0.0, 0.0, 0.0]\n" + 
				", length=4\n" + 
				"numberOfTraces=0\n" + 
				"matrix=\n" + 
				"[0.0, 0.8095, 0.1905, 0.0]\n" + 
				"[0.0, 0.17090135999999997, 0.56665, 0.09728347000000001]\n" + 
				"[0.0, 0.10178414999999999, 0.62335, 0.08905]\n" + 
				"[0.0, 0.0, 0.0, 0.08205000000000001]\n" + 
				", length=5\n" + 
				"numberOfTraces=0\n" + 
				"matrix=\n" + 
				"[0.0, 0.8095, 0.1905, 0.0]\n" + 
				"[0.0, 0.27950630420699996, 0.56665, 0.11991675937900001]\n" + 
				"[0.0, 0.15779115, 0.9663499999999998, 0.13805]\n" + 
				"[0.0, 0.0, 0.0, 0.219511197966]\n" + 
				", length=6\n" + 
				"numberOfTraces=0\n" + 
				"matrix=\n" + 
				"[0.0, 0.8095, 0.1905, 0.0]\n" + 
				"[0.0, 0.39238071434973987, 0.56665, 0.1434398062061803]\n" + 
				"[0.0, 0.19699605, 1.2064499999999998, 0.17235]\n" + 
				"[0.0, 0.0, 0.0, 0.3895643151904409]\n" + 
				", length=7\n" + 
				"numberOfTraces=0\n" + 
				"matrix=\n" + 
				"[0.0, 0.8095, 0.1905, 0.0]\n" + 
				"[0.0, 0.49666149731461673, 0.56665, 0.16517193962557783]\n" + 
				"[0.0, 0.22443948, 1.3745199999999997, 0.19635999999999998]\n" + 
				"[0.0, 0.0, 0.0, 0.5743586437756257]\n" + 
				", length=8\n" + 
				"numberOfTraces=0\n" + 
				"matrix=\n" + 
				"[0.0, 0.8095, 0.1905, 0.0]\n" + 
				"[0.0, 0.5869847901446328, 0.56665, 0.1839953296582587]\n" + 
				"[0.0, 0.24364988099999998, 1.4921689999999996, 0.21316699999999997]\n" + 
				"[0.0, 0.0, 0.0, 0.7608917505592667]\n" + 
				", length=9\n" + 
				"numberOfTraces=0\n" + 
				"matrix=\n" + 
				"[0.0, 0.8095, 0.1905, 0.0]\n" + 
				"[0.0, 0.662092044003875, 0.56665, 0.199647694506568]\n" + 
				"[0.0, 0.25709716169999997, 1.5745232999999996, 0.22493189999999996]\n" + 
				"[0.0, 0.0, 0.0, 0.9403337024482309]\n" + 
				", length=10\n" + 
				"numberOfTraces=0\n" + 
				"matrix=\n" + 
				"[0.0, 0.8095, 0.1905, 0.0]\n" + 
				"[0.0, 0.7228138883511472, 0.56665, 0.2123021374950837]\n" + 
				"[0.0, 0.26651025818999996, 1.6321713099999997, 0.23316732999999995]\n" + 
				"[0.0, 0.0, 0.0, 1.107181894982002]\n" + 
				", length=11\n" + 
				"numberOfTraces=0\n" + 
				"matrix=\n" + 
				"[0.0, 0.8095, 0.1905, 0.0]\n" + 
				"[0.0, 0.7709054172832647, 0.56665, 0.22232442054072984]\n" + 
				"[0.0, 0.27309942573299995, 1.6725249169999998, 0.23893213099999996]\n" + 
				"[0.0, 0.0, 0.0, 1.2584407286326411]\n" + 
				", length=12\n" + 
				"numberOfTraces=0\n" + 
				"matrix=\n" + 
				"[0.0, 0.8095, 0.1905, 0.0]\n" + 
				"[0.0, 0.8083999708562529, 0.56665, 0.2301382920670241]\n" + 
				"[0.0, 0.2777118430130999, 1.7007724418999999, 0.24296749169999995]\n" + 
				"[0.0, 0.0, 0.0, 1.3929114748583278]\n" + 
				", length=13\n" + 
				"numberOfTraces=0\n" + 
				"matrix=\n" + 
				"[0.0, 0.8095, 0.1905, 0.0]\n" + 
				"[0.0, 0.8372727207702152, 0.56665, 0.23615537820193036]\n" + 
				"[0.0, 0.2809405351091699, 1.7205457093299998, 0.24579224418999995]\n" + 
				"[0.0, 0.0, 0.0, 1.5106185289979308]\n" + 
				", length=14\n" + 
				"numberOfTraces=0\n" + 
				"matrix=\n" + 
				"[0.0, 0.8095, 0.1905, 0.0]\n" + 
				"[0.0, 0.8592846795564942, 0.56665, 0.240742674265164]\n" + 
				"[0.0, 0.2832006195764189, 1.7343869965309997, 0.24776957093299995]\n" + 
				"[0.0, 0.0, 0.0, 1.6123683118266563]\n" + 
				", length=15\n" + 
				"numberOfTraces=0\n" + 
				"matrix=\n" + 
				"[0.0, 0.8095, 0.1905, 0.0]\n" + 
				"[0.0, 0.8759280196154389, 0.56665, 0.24421114924609316]\n" + 
				"[0.0, 0.28478267870349316, 1.7440758975716997, 0.24915369965309997]\n" + 
				"[0.0, 0.0, 0.0, 1.6994237927183922]\n" + 
				", length=16\n" + 
				"numberOfTraces=0\n" + 
				"matrix=\n" + 
				"[0.0, 0.8095, 0.1905, 0.0]\n" + 
				"[0.0, 0.8884251758372917, 0.56665, 0.24681555878977524]\n" + 
				"[0.0, 0.2858901200924452, 1.7508581283001898, 0.25012258975716994]\n" + 
				"[0.0, 0.0, 0.0, 1.7732734071539547]\n" + 
				", length=17\n" + 
				"numberOfTraces=0\n" + 
				"matrix=\n" + 
				"[0.0, 0.8095, 0.1905, 0.0]\n" + 
				"[0.0, 0.8977538484190208, 0.56665, 0.2487596557883593]\n" + 
				"[0.0, 0.2866653290647116, 1.7556056898101329, 0.250800812830019]\n" + 
				"[0.0, 0.0, 0.0, 1.8354735782047575]\n" + 
				", length=18\n" + 
				"numberOfTraces=0\n" + 
				"matrix=\n" + 
				"[0.0, 0.8095, 0.1905, 0.0]\n" + 
				"[0.0, 0.9046820800005956, 0.56665, 0.25020350046242523]\n" + 
				"[0.0, 0.28720797534529807, 1.7589289828670929, 0.2512755689810133]\n" + 
				"[0.0, 0.0, 0.0, 1.8875465775900577]\n" + 
				", length=19\n" + 
				"numberOfTraces=0\n" + 
				"matrix=\n" + 
				"[0.0, 0.8095, 0.1905, 0.0]\n" + 
				"[0.0, 0.9098048609506794, 0.56665, 0.2512710889089282]\n" + 
				"[0.0, 0.2875878277417086, 1.761255288006965, 0.25160789828670926]\n" + 
				"[0.0, 0.0, 0.0, 1.930918677302366]\n" + 
				", length=20\n" + 
				"numberOfTraces=0\n" + 
				"matrix=\n" + 
				"[0.0, 0.8095, 0.1905, 0.0]\n" + 
				"[0.0, 0.9135780166363707, 0.56665, 0.2520574152141422]\n" + 
				"[0.0, 0.287853724419196, 1.7628837016048755, 0.25184052880069646]\n" + 
				"[0.0, 0.0, 0.0, 1.966886726780474]\n" + 
				", length=21\n" + 
				"numberOfTraces=0\n" + 
				"matrix=\n" + 
				"[0.0, 0.8095, 0.1905, 0.0]\n" + 
				"[0.0, 0.9163475948418022, 0.56665, 0.25263459579684044]\n" + 
				"[0.0, 0.28803985209343713, 1.7640235911234128, 0.2520033701604875]\n" + 
				"[0.0, 0.0, 0.0, 1.9966041127098673]\n" + 
				", length=22\n" + 
				"numberOfTraces=0\n" + 
				"matrix=\n" + 
				"[0.0, 0.8095, 0.1905, 0.0]\n" + 
				"[0.0, 0.9183743223634934, 0.56665, 0.25305696616704576]\n" + 
				"[0.0, 0.2881701414654059, 1.7648215137863887, 0.2521173591123413]\n" + 
				"[0.0, 0.0, 0.0, 2.021079404118605]\n" + 
				", length=23\n" + 
				"numberOfTraces=0\n" + 
				"matrix=\n" + 
				"[0.0, 0.8095, 0.1905, 0.0]\n" + 
				"[0.0, 0.919853388847476, 0.56665, 0.25336520388114975]\n" + 
				"[0.0, 0.28826134402578407, 1.765380059650472, 0.2521971513786389]\n" + 
				"[0.0, 0.0, 0.0, 2.0411828521891513]\n" + 
				", length=24\n" + 
				"numberOfTraces=0\n" + 
				"matrix=\n" + 
				"[0.0, 0.8095, 0.1905, 0.0]\n" + 
				"[0.0, 0.9209301223311946, 0.56665, 0.25358959532758885]\n" + 
				"[0.0, 0.2883251858180488, 1.7657710417553303, 0.25225300596504724]\n" + 
				"[0.0, 0.0, 0.0, 2.057657352268367]\n" + 
				", length=25\n" + 
				"numberOfTraces=0\n" + 
				"matrix=\n" + 
				"[0.0, 0.8095, 0.1905, 0.0]\n" + 
				"[0.0, 0.9217122147979362, 0.56665, 0.25375258353452695]\n" + 
				"[0.0, 0.28836987507263406, 1.7660447292287311, 0.25229210417553305]\n" + 
				"[0.0, 0.0, 0.0, 2.071131554229369]\n" + 
				", length=26\n" + 
				"numberOfTraces=0\n" + 
				"matrix=\n" + 
				"[0.0, 0.8095, 0.1905, 0.0]\n" + 
				"[0.0, 0.9222791390242502, 0.56665, 0.25387073064250454]\n" + 
				"[0.0, 0.2884011575508438, 1.7662363104601118, 0.25231947292287316]\n" + 
				"[0.0, 0.0, 0.0, 2.0821335975606514]\n" + 
				", length=27\n" + 
				"numberOfTraces=0\n" + 
				"matrix=\n" + 
				"[0.0, 0.8095, 0.1905, 0.0]\n" + 
				"[0.0, 0.9226893293615418, 0.56665, 0.25395621438058097]\n" + 
				"[0.0, 0.2884230552855906, 1.7663704173220782, 0.25233863104601123]\n" + 
				"[0.0, 0.0, 0.0, 2.09110451356365]\n" + 
				", length=28\n" + 
				"numberOfTraces=0\n" + 
				"matrix=\n" + 
				"[0.0, 0.8095, 0.1905, 0.0]\n" + 
				"[0.0, 0.922985612152539, 0.56665, 0.25401795976607516]\n" + 
				"[0.0, 0.28843838369991337, 1.7664642921254547, 0.2523520417322079]\n" + 
				"[0.0, 0.0, 0.0, 2.098410733158014]\n" + 
				", length=29\n" + 
				"numberOfTraces=0\n" + 
				"matrix=\n" + 
				"[0.0, 0.8095, 0.1905, 0.0]\n" + 
				"[0.0, 0.9231992839560267, 0.56665, 0.25406248900731526]\n" + 
				"[0.0, 0.2884491135899393, 1.7665300044878183, 0.2523614292125455]\n" + 
				"[0.0, 0.0, 0.0, 2.104355408976535]\n" + 
				", length=30\n" + 
				"numberOfTraces=0\n" + 
				"matrix=\n" + 
				"[0.0, 0.8095, 0.1905, 0.0]\n" + 
				"[0.0, 0.9233531561972689, 0.56665, 0.25409455600931813]\n" + 
				"[0.0, 0.28845662451295745, 1.7665760031414728, 0.2523680004487819]\n" + 
				"[0.0, 0.0, 0.0, 2.10918843911742]\n" + 
				"]";
		
		assertMatrices("Automata matrices are generated correctly", expectation, automatrices);
	}
	
	/**
	 * Tests the automata matrices for a simple custom PA as an input.
	 */
	@Test
	public void testCustomAutomaton() {
		List<String> alphabet = Arrays.asList("a", "b", "c");
		Map<Integer, String> mapping = IntStream.range(0, alphabet.size()).boxed().collect(Collectors.toMap(i -> i, i -> alphabet.get(i)));
	
		List<Integer> lengths = Arrays.asList(1,2,3,4);
		
		ProbabilisticAutomata customAutomata = ProbabilisticAutomataBuilder.newAutomaton()
			.fromTo(0, 1, 1.0, 0)
			.fromTo(1, 2, 1.0, 1)
			.fromTo(2, 3, 1.0, 2)
			.fromTo(3, 3, 1.0, 2)
			.labelMapping(mapping)
			.startIn(0).create();
		
		Map<Integer, Integer> matrixIndices = FootprintMatrixCheckingAlgorithm.buildMatrixIndicesMap(mapping);
		AutomatonMatrixGenerator generator = new AutomatonMatrixGenerator(matrixIndices, lengths);
		
		List<FPMatrix> automataMatrices = generator.generate(customAutomata);
		
		// Check the number of matrices
		assertEquals("The number of automata matrices is the number of lengths that was asked for",
				4, automataMatrices.size());
		
		final String expectations = "[length=1\n" + 
				"numberOfTraces=0\n" + 
				"matrix=\n" + 
				"[0.0, 0.0, 0.0]\n" + 
				"[0.0, 0.0, 0.0]\n" + 
				"[0.0, 0.0, 0.0]\n" + 
				", length=2\n" + 
				"numberOfTraces=0\n" + 
				"matrix=\n" + 
				"[0.0, 1.0, 0.0]\n" + 
				"[0.0, 0.0, 0.0]\n" + 
				"[0.0, 0.0, 0.0]\n" + 
				", length=3\n" + 
				"numberOfTraces=0\n" + 
				"matrix=\n" + 
				"[0.0, 1.0, 0.0]\n" + 
				"[0.0, 0.0, 1.0]\n" + 
				"[0.0, 0.0, 0.0]\n" + 
				", length=4\n" + 
				"numberOfTraces=0\n" + 
				"matrix=\n" + 
				"[0.0, 1.0, 0.0]\n" + 
				"[0.0, 0.0, 1.0]\n" + 
				"[0.0, 0.0, 1.0]\n" + 
				"]";
		
		assertMatrices("The automaton matrices are as expected.", expectations, automataMatrices);
		
		
	}
}
