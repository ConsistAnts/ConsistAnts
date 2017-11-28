/**
 */
package de.hu_berlin.cchecker.core.models.pdfa;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Probabilistic State</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * A probabilistic state.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticState#getStateId <em>State Id</em>}</li>
 *   <li>{@link de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticState#getOutgoingTransitions <em>Outgoing Transitions</em>}</li>
 *   <li>{@link de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticState#isTerminating <em>Terminating</em>}</li>
 *   <li>{@link de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticState#getTerminatingProbability <em>Terminating Probability</em>}</li>
 *   <li>{@link de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticState#getAutomata <em>Automata</em>}</li>
 * </ul>
 *
 * @see de.hu_berlin.cchecker.core.models.pdfa.PdfaPackage#getProbabilisticState()
 * @model
 * @generated
 */
public interface ProbabilisticState extends EObject {
	/**
	 * Returns the value of the '<em><b>State Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * * The id of this state.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>State Id</em>' attribute.
	 * @see #setStateId(int)
	 * @see de.hu_berlin.cchecker.core.models.pdfa.PdfaPackage#getProbabilisticState_StateId()
	 * @model unique="false"
	 * @generated
	 */
	int getStateId();

	/**
	 * Sets the value of the '{@link de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticState#getStateId <em>State Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>State Id</em>' attribute.
	 * @see #getStateId()
	 * @generated
	 */
	void setStateId(int value);

	/**
	 * Returns the value of the '<em><b>Outgoing Transitions</b></em>' containment reference list.
	 * The list contents are of type {@link de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticTransition}.
	 * It is bidirectional and its opposite is '{@link de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticTransition#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * All outgoing transitions of this state.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Outgoing Transitions</em>' containment reference list.
	 * @see de.hu_berlin.cchecker.core.models.pdfa.PdfaPackage#getProbabilisticState_OutgoingTransitions()
	 * @see de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticTransition#getSource
	 * @model opposite="source" containment="true"
	 * @generated
	 */
	EList<ProbabilisticTransition> getOutgoingTransitions();

	/**
	 * Returns the value of the '<em><b>Terminating</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Specifies whether this is an accepting state.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Terminating</em>' attribute.
	 * @see #setTerminating(boolean)
	 * @see de.hu_berlin.cchecker.core.models.pdfa.PdfaPackage#getProbabilisticState_Terminating()
	 * @model unique="false"
	 * @generated
	 */
	boolean isTerminating();

	/**
	 * Sets the value of the '{@link de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticState#isTerminating <em>Terminating</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Terminating</em>' attribute.
	 * @see #isTerminating()
	 * @generated
	 */
	void setTerminating(boolean value);

	/**
	 * Returns the value of the '<em><b>Terminating Probability</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Specifies the probability of terminating in this state.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Terminating Probability</em>' attribute.
	 * @see #setTerminatingProbability(double)
	 * @see de.hu_berlin.cchecker.core.models.pdfa.PdfaPackage#getProbabilisticState_TerminatingProbability()
	 * @model unique="false"
	 * @generated
	 */
	double getTerminatingProbability();

	/**
	 * Sets the value of the '{@link de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticState#getTerminatingProbability <em>Terminating Probability</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Terminating Probability</em>' attribute.
	 * @see #getTerminatingProbability()
	 * @generated
	 */
	void setTerminatingProbability(double value);

	/**
	 * Returns the value of the '<em><b>Automata</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata#getStates <em>States</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * The automata this state is contained in.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Automata</em>' container reference.
	 * @see #setAutomata(ProbabilisticAutomata)
	 * @see de.hu_berlin.cchecker.core.models.pdfa.PdfaPackage#getProbabilisticState_Automata()
	 * @see de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata#getStates
	 * @model opposite="states" transient="false"
	 * @generated
	 */
	ProbabilisticAutomata getAutomata();

	/**
	 * Sets the value of the '{@link de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticState#getAutomata <em>Automata</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Automata</em>' container reference.
	 * @see #getAutomata()
	 * @generated
	 */
	void setAutomata(ProbabilisticAutomata value);

} // ProbabilisticState
