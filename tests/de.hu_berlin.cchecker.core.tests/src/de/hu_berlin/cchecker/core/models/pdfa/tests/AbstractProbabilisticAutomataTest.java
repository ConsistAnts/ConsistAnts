package de.hu_berlin.cchecker.core.models.pdfa.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.After;
import org.junit.Before;

import de.hu_berlin.cchecker.core.models.ModelSetup;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;

public abstract class AbstractProbabilisticAutomataTest {

	protected static final String PROBABILISTIC_AUTOMATA_FILE_EXTENSION = "pdfa";
	private ResourceSetImpl resourceSet;
	private String testResourceLocation;

	public AbstractProbabilisticAutomataTest(String testResourceLocation) {
		this.testResourceLocation = testResourceLocation;
	}

	@Before
	public void setUp() {
		// Register the necessary EMF Packages
		EPackage.Registry.INSTANCE.put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);

		// Register the PdfaPackage
		ModelSetup.register();

		// Create a test resource set
		resourceSet = new ResourceSetImpl();

		// Set res/ as the root folder for all our testing resources
		ProbabilisticAutomataTestUtils.configureTestResourceLocation(resourceSet, "res");

		// Set res/ as the root folder for all our testing resources
		ProbabilisticAutomataTestUtils.configureTestResourceLocation(getResourceSet(), testResourceLocation);
	}

	@After
	public void tearDown() {
		ModelSetup.unregister();
	}

	/**
	 * Returns a resource set to access the test directory resources.
	 * 
	 * Use the URI-scheme "cctest:/" to access your test resource. (e.g.
	 * cctest:/test.pdfa)
	 */
	protected ResourceSetImpl getResourceSet() {
		return resourceSet;
	}

	protected ProbabilisticAutomata loadAutomataFromTestResources(String filename) {
		Resource resource = getResourceSet()
			.getResource(URI.createURI(ProbabilisticAutomataTestUtils.CCTEST_SCHEME + ":/" + filename), true);
		EList<EObject> contents = resource.getContents();

		assertTrue("The ProbabilisticAutomata resource " + filename + " contains exactly one element.",
				contents.size() == 1);
		EObject firstContent = contents.get(0);

		assertTrue("The ProbabilisticAutomata resource " + filename + " contains a model of correct type ",
				firstContent instanceof ProbabilisticAutomata);

		return (ProbabilisticAutomata) firstContent;
	}

	/**
	 * Asserts that the given {@link ThrowingRunnable} throws an exception of the
	 * expected type.
	 */
	protected static void assertException(String message, Class<? extends Exception> expected, ThrowingRunnable r) {
		try {
			r.run();
		} catch (Exception e) {
			if (expected.isInstance(e)) {
				return;
			} else {
				fail(String.format("%s: expected exception type %s but got %s", message, expected.getSimpleName(),
						e.getClass().getSimpleName()));
			}
		}
		fail(String.format("%s: code didn't throw an exception", message));
	}

	/**
	 * A runnable that can throw an exception.
	 */
	@FunctionalInterface
	public static interface ThrowingRunnable {
		public abstract void run() throws Exception;
	}

}