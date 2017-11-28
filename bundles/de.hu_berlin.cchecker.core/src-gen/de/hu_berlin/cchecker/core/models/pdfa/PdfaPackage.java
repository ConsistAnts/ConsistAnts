/**
 */
package de.hu_berlin.cchecker.core.models.pdfa;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see de.hu_berlin.cchecker.core.models.pdfa.PdfaFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/emf/2002/GenModel basePackage='de.hu_berlin.cchecker.core.models'"
 * @generated
 */
public interface PdfaPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "pdfa";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "de.hu_berlin.cchecker.core.models.pdfa";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "pdfa";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	PdfaPackage eINSTANCE = de.hu_berlin.cchecker.core.models.pdfa.impl.PdfaPackageImpl.init();

	/**
	 * The meta object id for the '{@link de.hu_berlin.cchecker.core.models.pdfa.impl.ProbabilisticTransitionImpl <em>Probabilistic Transition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.cchecker.core.models.pdfa.impl.ProbabilisticTransitionImpl
	 * @see de.hu_berlin.cchecker.core.models.pdfa.impl.PdfaPackageImpl#getProbabilisticTransition()
	 * @generated
	 */
	int PROBABILISTIC_TRANSITION = 0;

	/**
	 * The feature id for the '<em><b>Transition Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBABILISTIC_TRANSITION__TRANSITION_ID = 0;

	/**
	 * The feature id for the '<em><b>Probability</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBABILISTIC_TRANSITION__PROBABILITY = 1;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBABILISTIC_TRANSITION__TARGET = 2;

	/**
	 * The feature id for the '<em><b>Source</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBABILISTIC_TRANSITION__SOURCE = 3;

	/**
	 * The number of structural features of the '<em>Probabilistic Transition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBABILISTIC_TRANSITION_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Probabilistic Transition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBABILISTIC_TRANSITION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.hu_berlin.cchecker.core.models.pdfa.impl.ProbabilisticStateImpl <em>Probabilistic State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.cchecker.core.models.pdfa.impl.ProbabilisticStateImpl
	 * @see de.hu_berlin.cchecker.core.models.pdfa.impl.PdfaPackageImpl#getProbabilisticState()
	 * @generated
	 */
	int PROBABILISTIC_STATE = 1;

	/**
	 * The feature id for the '<em><b>State Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBABILISTIC_STATE__STATE_ID = 0;

	/**
	 * The feature id for the '<em><b>Outgoing Transitions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBABILISTIC_STATE__OUTGOING_TRANSITIONS = 1;

	/**
	 * The feature id for the '<em><b>Terminating</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBABILISTIC_STATE__TERMINATING = 2;

	/**
	 * The feature id for the '<em><b>Terminating Probability</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBABILISTIC_STATE__TERMINATING_PROBABILITY = 3;

	/**
	 * The feature id for the '<em><b>Automata</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBABILISTIC_STATE__AUTOMATA = 4;

	/**
	 * The number of structural features of the '<em>Probabilistic State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBABILISTIC_STATE_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Probabilistic State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBABILISTIC_STATE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.hu_berlin.cchecker.core.models.pdfa.impl.TransitionLabelPairImpl <em>Transition Label Pair</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.cchecker.core.models.pdfa.impl.TransitionLabelPairImpl
	 * @see de.hu_berlin.cchecker.core.models.pdfa.impl.PdfaPackageImpl#getTransitionLabelPair()
	 * @generated
	 */
	int TRANSITION_LABEL_PAIR = 2;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION_LABEL_PAIR__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION_LABEL_PAIR__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Transition Label Pair</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION_LABEL_PAIR_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Transition Label Pair</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION_LABEL_PAIR_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.hu_berlin.cchecker.core.models.pdfa.impl.ProbabilisticAutomataImpl <em>Probabilistic Automata</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.cchecker.core.models.pdfa.impl.ProbabilisticAutomataImpl
	 * @see de.hu_berlin.cchecker.core.models.pdfa.impl.PdfaPackageImpl#getProbabilisticAutomata()
	 * @generated
	 */
	int PROBABILISTIC_AUTOMATA = 3;

	/**
	 * The feature id for the '<em><b>States</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBABILISTIC_AUTOMATA__STATES = 0;

	/**
	 * The feature id for the '<em><b>Start State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBABILISTIC_AUTOMATA__START_STATE = 1;

	/**
	 * The feature id for the '<em><b>Transition Labels</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBABILISTIC_AUTOMATA__TRANSITION_LABELS = 2;

	/**
	 * The number of structural features of the '<em>Probabilistic Automata</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBABILISTIC_AUTOMATA_FEATURE_COUNT = 3;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBABILISTIC_AUTOMATA___TO_STRING = 0;

	/**
	 * The number of operations of the '<em>Probabilistic Automata</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBABILISTIC_AUTOMATA_OPERATION_COUNT = 1;


	/**
	 * Returns the meta object for class '{@link de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticTransition <em>Probabilistic Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Probabilistic Transition</em>'.
	 * @see de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticTransition
	 * @generated
	 */
	EClass getProbabilisticTransition();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticTransition#getTransitionId <em>Transition Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Transition Id</em>'.
	 * @see de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticTransition#getTransitionId()
	 * @see #getProbabilisticTransition()
	 * @generated
	 */
	EAttribute getProbabilisticTransition_TransitionId();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticTransition#getProbability <em>Probability</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Probability</em>'.
	 * @see de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticTransition#getProbability()
	 * @see #getProbabilisticTransition()
	 * @generated
	 */
	EAttribute getProbabilisticTransition_Probability();

	/**
	 * Returns the meta object for the reference '{@link de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticTransition#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticTransition#getTarget()
	 * @see #getProbabilisticTransition()
	 * @generated
	 */
	EReference getProbabilisticTransition_Target();

	/**
	 * Returns the meta object for the container reference '{@link de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticTransition#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Source</em>'.
	 * @see de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticTransition#getSource()
	 * @see #getProbabilisticTransition()
	 * @generated
	 */
	EReference getProbabilisticTransition_Source();

	/**
	 * Returns the meta object for class '{@link de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticState <em>Probabilistic State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Probabilistic State</em>'.
	 * @see de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticState
	 * @generated
	 */
	EClass getProbabilisticState();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticState#getStateId <em>State Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>State Id</em>'.
	 * @see de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticState#getStateId()
	 * @see #getProbabilisticState()
	 * @generated
	 */
	EAttribute getProbabilisticState_StateId();

	/**
	 * Returns the meta object for the containment reference list '{@link de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticState#getOutgoingTransitions <em>Outgoing Transitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Outgoing Transitions</em>'.
	 * @see de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticState#getOutgoingTransitions()
	 * @see #getProbabilisticState()
	 * @generated
	 */
	EReference getProbabilisticState_OutgoingTransitions();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticState#isTerminating <em>Terminating</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Terminating</em>'.
	 * @see de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticState#isTerminating()
	 * @see #getProbabilisticState()
	 * @generated
	 */
	EAttribute getProbabilisticState_Terminating();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticState#getTerminatingProbability <em>Terminating Probability</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Terminating Probability</em>'.
	 * @see de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticState#getTerminatingProbability()
	 * @see #getProbabilisticState()
	 * @generated
	 */
	EAttribute getProbabilisticState_TerminatingProbability();

	/**
	 * Returns the meta object for the container reference '{@link de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticState#getAutomata <em>Automata</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Automata</em>'.
	 * @see de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticState#getAutomata()
	 * @see #getProbabilisticState()
	 * @generated
	 */
	EReference getProbabilisticState_Automata();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>Transition Label Pair</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Transition Label Pair</em>'.
	 * @see java.util.Map.Entry
	 * @model keyUnique="false" keyDataType="org.eclipse.emf.ecore.EIntegerObject"
	 *        valueUnique="false" valueDataType="org.eclipse.emf.ecore.EString"
	 * @generated
	 */
	EClass getTransitionLabelPair();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getTransitionLabelPair()
	 * @generated
	 */
	EAttribute getTransitionLabelPair_Key();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getTransitionLabelPair()
	 * @generated
	 */
	EAttribute getTransitionLabelPair_Value();

	/**
	 * Returns the meta object for class '{@link de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata <em>Probabilistic Automata</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Probabilistic Automata</em>'.
	 * @see de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata
	 * @generated
	 */
	EClass getProbabilisticAutomata();

	/**
	 * Returns the meta object for the containment reference list '{@link de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata#getStates <em>States</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>States</em>'.
	 * @see de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata#getStates()
	 * @see #getProbabilisticAutomata()
	 * @generated
	 */
	EReference getProbabilisticAutomata_States();

	/**
	 * Returns the meta object for the reference '{@link de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata#getStartState <em>Start State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Start State</em>'.
	 * @see de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata#getStartState()
	 * @see #getProbabilisticAutomata()
	 * @generated
	 */
	EReference getProbabilisticAutomata_StartState();

	/**
	 * Returns the meta object for the map '{@link de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata#getTransitionLabels <em>Transition Labels</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Transition Labels</em>'.
	 * @see de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata#getTransitionLabels()
	 * @see #getProbabilisticAutomata()
	 * @generated
	 */
	EReference getProbabilisticAutomata_TransitionLabels();

	/**
	 * Returns the meta object for the '{@link de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata#toString() <em>To String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>To String</em>' operation.
	 * @see de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata#toString()
	 * @generated
	 */
	EOperation getProbabilisticAutomata__ToString();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	PdfaFactory getPdfaFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link de.hu_berlin.cchecker.core.models.pdfa.impl.ProbabilisticTransitionImpl <em>Probabilistic Transition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.cchecker.core.models.pdfa.impl.ProbabilisticTransitionImpl
		 * @see de.hu_berlin.cchecker.core.models.pdfa.impl.PdfaPackageImpl#getProbabilisticTransition()
		 * @generated
		 */
		EClass PROBABILISTIC_TRANSITION = eINSTANCE.getProbabilisticTransition();

		/**
		 * The meta object literal for the '<em><b>Transition Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROBABILISTIC_TRANSITION__TRANSITION_ID = eINSTANCE.getProbabilisticTransition_TransitionId();

		/**
		 * The meta object literal for the '<em><b>Probability</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROBABILISTIC_TRANSITION__PROBABILITY = eINSTANCE.getProbabilisticTransition_Probability();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROBABILISTIC_TRANSITION__TARGET = eINSTANCE.getProbabilisticTransition_Target();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROBABILISTIC_TRANSITION__SOURCE = eINSTANCE.getProbabilisticTransition_Source();

		/**
		 * The meta object literal for the '{@link de.hu_berlin.cchecker.core.models.pdfa.impl.ProbabilisticStateImpl <em>Probabilistic State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.cchecker.core.models.pdfa.impl.ProbabilisticStateImpl
		 * @see de.hu_berlin.cchecker.core.models.pdfa.impl.PdfaPackageImpl#getProbabilisticState()
		 * @generated
		 */
		EClass PROBABILISTIC_STATE = eINSTANCE.getProbabilisticState();

		/**
		 * The meta object literal for the '<em><b>State Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROBABILISTIC_STATE__STATE_ID = eINSTANCE.getProbabilisticState_StateId();

		/**
		 * The meta object literal for the '<em><b>Outgoing Transitions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROBABILISTIC_STATE__OUTGOING_TRANSITIONS = eINSTANCE.getProbabilisticState_OutgoingTransitions();

		/**
		 * The meta object literal for the '<em><b>Terminating</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROBABILISTIC_STATE__TERMINATING = eINSTANCE.getProbabilisticState_Terminating();

		/**
		 * The meta object literal for the '<em><b>Terminating Probability</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROBABILISTIC_STATE__TERMINATING_PROBABILITY = eINSTANCE.getProbabilisticState_TerminatingProbability();

		/**
		 * The meta object literal for the '<em><b>Automata</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROBABILISTIC_STATE__AUTOMATA = eINSTANCE.getProbabilisticState_Automata();

		/**
		 * The meta object literal for the '{@link de.hu_berlin.cchecker.core.models.pdfa.impl.TransitionLabelPairImpl <em>Transition Label Pair</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.cchecker.core.models.pdfa.impl.TransitionLabelPairImpl
		 * @see de.hu_berlin.cchecker.core.models.pdfa.impl.PdfaPackageImpl#getTransitionLabelPair()
		 * @generated
		 */
		EClass TRANSITION_LABEL_PAIR = eINSTANCE.getTransitionLabelPair();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSITION_LABEL_PAIR__KEY = eINSTANCE.getTransitionLabelPair_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSITION_LABEL_PAIR__VALUE = eINSTANCE.getTransitionLabelPair_Value();

		/**
		 * The meta object literal for the '{@link de.hu_berlin.cchecker.core.models.pdfa.impl.ProbabilisticAutomataImpl <em>Probabilistic Automata</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.cchecker.core.models.pdfa.impl.ProbabilisticAutomataImpl
		 * @see de.hu_berlin.cchecker.core.models.pdfa.impl.PdfaPackageImpl#getProbabilisticAutomata()
		 * @generated
		 */
		EClass PROBABILISTIC_AUTOMATA = eINSTANCE.getProbabilisticAutomata();

		/**
		 * The meta object literal for the '<em><b>States</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROBABILISTIC_AUTOMATA__STATES = eINSTANCE.getProbabilisticAutomata_States();

		/**
		 * The meta object literal for the '<em><b>Start State</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROBABILISTIC_AUTOMATA__START_STATE = eINSTANCE.getProbabilisticAutomata_StartState();

		/**
		 * The meta object literal for the '<em><b>Transition Labels</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROBABILISTIC_AUTOMATA__TRANSITION_LABELS = eINSTANCE.getProbabilisticAutomata_TransitionLabels();

		/**
		 * The meta object literal for the '<em><b>To String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PROBABILISTIC_AUTOMATA___TO_STRING = eINSTANCE.getProbabilisticAutomata__ToString();

	}

} //PdfaPackage
