/**
 */
package de.hu_berlin.cchecker.core.models.pdfa;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Probabilistic Transition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * A probabilistic automata transition.
 * 
 * Comprises of a transition id as well as probability.
 * 
 * Also references the target and source state.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticTransition#getTransitionId <em>Transition Id</em>}</li>
 *   <li>{@link de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticTransition#getProbability <em>Probability</em>}</li>
 *   <li>{@link de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticTransition#getTarget <em>Target</em>}</li>
 *   <li>{@link de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticTransition#getSource <em>Source</em>}</li>
 * </ul>
 *
 * @see de.hu_berlin.cchecker.core.models.pdfa.PdfaPackage#getProbabilisticTransition()
 * @model
 * @generated
 */
public interface ProbabilisticTransition extends EObject {
	/**
	 * Returns the value of the '<em><b>Transition Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * The id of this transition.
	 * 
	 * To get a human-readable label for this transition,
	 * use the transitionLabels mapping of the corresponding {@link ProbabilisticAutomata}
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Transition Id</em>' attribute.
	 * @see #setTransitionId(int)
	 * @see de.hu_berlin.cchecker.core.models.pdfa.PdfaPackage#getProbabilisticTransition_TransitionId()
	 * @model unique="false"
	 * @generated
	 */
	int getTransitionId();

	/**
	 * Sets the value of the '{@link de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticTransition#getTransitionId <em>Transition Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Transition Id</em>' attribute.
	 * @see #getTransitionId()
	 * @generated
	 */
	void setTransitionId(int value);

	/**
	 * Returns the value of the '<em><b>Probability</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * The probability of this transition.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Probability</em>' attribute.
	 * @see #setProbability(double)
	 * @see de.hu_berlin.cchecker.core.models.pdfa.PdfaPackage#getProbabilisticTransition_Probability()
	 * @model unique="false"
	 * @generated
	 */
	double getProbability();

	/**
	 * Sets the value of the '{@link de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticTransition#getProbability <em>Probability</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Probability</em>' attribute.
	 * @see #getProbability()
	 * @generated
	 */
	void setProbability(double value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * The target-state of this transition.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(ProbabilisticState)
	 * @see de.hu_berlin.cchecker.core.models.pdfa.PdfaPackage#getProbabilisticTransition_Target()
	 * @model
	 * @generated
	 */
	ProbabilisticState getTarget();

	/**
	 * Sets the value of the '{@link de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticTransition#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(ProbabilisticState value);

	/**
	 * Returns the value of the '<em><b>Source</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticState#getOutgoingTransitions <em>Outgoing Transitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * The source state of this transition.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Source</em>' container reference.
	 * @see #setSource(ProbabilisticState)
	 * @see de.hu_berlin.cchecker.core.models.pdfa.PdfaPackage#getProbabilisticTransition_Source()
	 * @see de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticState#getOutgoingTransitions
	 * @model opposite="outgoingTransitions" transient="false"
	 * @generated
	 */
	ProbabilisticState getSource();

	/**
	 * Sets the value of the '{@link de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticTransition#getSource <em>Source</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' container reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(ProbabilisticState value);

} // ProbabilisticTransition
