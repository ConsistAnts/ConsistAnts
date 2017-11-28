/**
 */
package de.hu_berlin.cchecker.core.models.pdfa.impl;

import de.hu_berlin.cchecker.core.models.pdfa.PdfaFactory;
import de.hu_berlin.cchecker.core.models.pdfa.PdfaPackage;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticState;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticTransition;

import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PdfaPackageImpl extends EPackageImpl implements PdfaPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass probabilisticTransitionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass probabilisticStateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass transitionLabelPairEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass probabilisticAutomataEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see de.hu_berlin.cchecker.core.models.pdfa.PdfaPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private PdfaPackageImpl() {
		super(eNS_URI, PdfaFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link PdfaPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static PdfaPackage init() {
		if (isInited) return (PdfaPackage)EPackage.Registry.INSTANCE.getEPackage(PdfaPackage.eNS_URI);

		// Obtain or create and register package
		PdfaPackageImpl thePdfaPackage = (PdfaPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof PdfaPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new PdfaPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		thePdfaPackage.createPackageContents();

		// Initialize created meta-data
		thePdfaPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		thePdfaPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(PdfaPackage.eNS_URI, thePdfaPackage);
		return thePdfaPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProbabilisticTransition() {
		return probabilisticTransitionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProbabilisticTransition_TransitionId() {
		return (EAttribute)probabilisticTransitionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProbabilisticTransition_Probability() {
		return (EAttribute)probabilisticTransitionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProbabilisticTransition_Target() {
		return (EReference)probabilisticTransitionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProbabilisticTransition_Source() {
		return (EReference)probabilisticTransitionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProbabilisticState() {
		return probabilisticStateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProbabilisticState_StateId() {
		return (EAttribute)probabilisticStateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProbabilisticState_OutgoingTransitions() {
		return (EReference)probabilisticStateEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProbabilisticState_Terminating() {
		return (EAttribute)probabilisticStateEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProbabilisticState_TerminatingProbability() {
		return (EAttribute)probabilisticStateEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProbabilisticState_Automata() {
		return (EReference)probabilisticStateEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTransitionLabelPair() {
		return transitionLabelPairEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTransitionLabelPair_Key() {
		return (EAttribute)transitionLabelPairEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTransitionLabelPair_Value() {
		return (EAttribute)transitionLabelPairEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProbabilisticAutomata() {
		return probabilisticAutomataEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProbabilisticAutomata_States() {
		return (EReference)probabilisticAutomataEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProbabilisticAutomata_StartState() {
		return (EReference)probabilisticAutomataEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProbabilisticAutomata_TransitionLabels() {
		return (EReference)probabilisticAutomataEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getProbabilisticAutomata__ToString() {
		return probabilisticAutomataEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PdfaFactory getPdfaFactory() {
		return (PdfaFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		probabilisticTransitionEClass = createEClass(PROBABILISTIC_TRANSITION);
		createEAttribute(probabilisticTransitionEClass, PROBABILISTIC_TRANSITION__TRANSITION_ID);
		createEAttribute(probabilisticTransitionEClass, PROBABILISTIC_TRANSITION__PROBABILITY);
		createEReference(probabilisticTransitionEClass, PROBABILISTIC_TRANSITION__TARGET);
		createEReference(probabilisticTransitionEClass, PROBABILISTIC_TRANSITION__SOURCE);

		probabilisticStateEClass = createEClass(PROBABILISTIC_STATE);
		createEAttribute(probabilisticStateEClass, PROBABILISTIC_STATE__STATE_ID);
		createEReference(probabilisticStateEClass, PROBABILISTIC_STATE__OUTGOING_TRANSITIONS);
		createEAttribute(probabilisticStateEClass, PROBABILISTIC_STATE__TERMINATING);
		createEAttribute(probabilisticStateEClass, PROBABILISTIC_STATE__TERMINATING_PROBABILITY);
		createEReference(probabilisticStateEClass, PROBABILISTIC_STATE__AUTOMATA);

		transitionLabelPairEClass = createEClass(TRANSITION_LABEL_PAIR);
		createEAttribute(transitionLabelPairEClass, TRANSITION_LABEL_PAIR__KEY);
		createEAttribute(transitionLabelPairEClass, TRANSITION_LABEL_PAIR__VALUE);

		probabilisticAutomataEClass = createEClass(PROBABILISTIC_AUTOMATA);
		createEReference(probabilisticAutomataEClass, PROBABILISTIC_AUTOMATA__STATES);
		createEReference(probabilisticAutomataEClass, PROBABILISTIC_AUTOMATA__START_STATE);
		createEReference(probabilisticAutomataEClass, PROBABILISTIC_AUTOMATA__TRANSITION_LABELS);
		createEOperation(probabilisticAutomataEClass, PROBABILISTIC_AUTOMATA___TO_STRING);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(probabilisticTransitionEClass, ProbabilisticTransition.class, "ProbabilisticTransition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getProbabilisticTransition_TransitionId(), theEcorePackage.getEInt(), "transitionId", null, 0, 1, ProbabilisticTransition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProbabilisticTransition_Probability(), theEcorePackage.getEDouble(), "probability", null, 0, 1, ProbabilisticTransition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProbabilisticTransition_Target(), this.getProbabilisticState(), null, "target", null, 0, 1, ProbabilisticTransition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProbabilisticTransition_Source(), this.getProbabilisticState(), this.getProbabilisticState_OutgoingTransitions(), "source", null, 0, 1, ProbabilisticTransition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(probabilisticStateEClass, ProbabilisticState.class, "ProbabilisticState", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getProbabilisticState_StateId(), theEcorePackage.getEInt(), "stateId", null, 0, 1, ProbabilisticState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProbabilisticState_OutgoingTransitions(), this.getProbabilisticTransition(), this.getProbabilisticTransition_Source(), "outgoingTransitions", null, 0, -1, ProbabilisticState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProbabilisticState_Terminating(), theEcorePackage.getEBoolean(), "terminating", null, 0, 1, ProbabilisticState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProbabilisticState_TerminatingProbability(), theEcorePackage.getEDouble(), "terminatingProbability", null, 0, 1, ProbabilisticState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProbabilisticState_Automata(), this.getProbabilisticAutomata(), this.getProbabilisticAutomata_States(), "automata", null, 0, 1, ProbabilisticState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(transitionLabelPairEClass, Map.Entry.class, "TransitionLabelPair", !IS_ABSTRACT, !IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTransitionLabelPair_Key(), theEcorePackage.getEIntegerObject(), "key", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTransitionLabelPair_Value(), theEcorePackage.getEString(), "value", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(probabilisticAutomataEClass, ProbabilisticAutomata.class, "ProbabilisticAutomata", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getProbabilisticAutomata_States(), this.getProbabilisticState(), this.getProbabilisticState_Automata(), "states", null, 0, -1, ProbabilisticAutomata.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProbabilisticAutomata_StartState(), this.getProbabilisticState(), null, "startState", null, 0, 1, ProbabilisticAutomata.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProbabilisticAutomata_TransitionLabels(), this.getTransitionLabelPair(), null, "transitionLabels", null, 0, -1, ProbabilisticAutomata.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getProbabilisticAutomata__ToString(), theEcorePackage.getEString(), "toString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //PdfaPackageImpl
