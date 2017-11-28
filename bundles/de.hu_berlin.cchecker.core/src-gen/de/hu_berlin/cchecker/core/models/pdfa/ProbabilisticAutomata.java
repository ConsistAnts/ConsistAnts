/**
 */
package de.hu_berlin.cchecker.core.models.pdfa;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Probabilistic Automata</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata#getStates <em>States</em>}</li>
 *   <li>{@link de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata#getStartState <em>Start State</em>}</li>
 *   <li>{@link de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata#getTransitionLabels <em>Transition Labels</em>}</li>
 * </ul>
 *
 * @see de.hu_berlin.cchecker.core.models.pdfa.PdfaPackage#getProbabilisticAutomata()
 * @model
 * @generated
 */
public interface ProbabilisticAutomata extends EObject {
	/**
	 * Returns the value of the '<em><b>States</b></em>' containment reference list.
	 * The list contents are of type {@link de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticState}.
	 * It is bidirectional and its opposite is '{@link de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticState#getAutomata <em>Automata</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * All the states of this automata.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>States</em>' containment reference list.
	 * @see de.hu_berlin.cchecker.core.models.pdfa.PdfaPackage#getProbabilisticAutomata_States()
	 * @see de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticState#getAutomata
	 * @model opposite="automata" containment="true"
	 * @generated
	 */
	EList<ProbabilisticState> getStates();

	/**
	 * Returns the value of the '<em><b>Start State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * The start state of this automata.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Start State</em>' reference.
	 * @see #setStartState(ProbabilisticState)
	 * @see de.hu_berlin.cchecker.core.models.pdfa.PdfaPackage#getProbabilisticAutomata_StartState()
	 * @model
	 * @generated
	 */
	ProbabilisticState getStartState();

	/**
	 * Sets the value of the '{@link de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata#getStartState <em>Start State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Start State</em>' reference.
	 * @see #getStartState()
	 * @generated
	 */
	void setStartState(ProbabilisticState value);

	/**
	 * Returns the value of the '<em><b>Transition Labels</b></em>' map.
	 * The key is of type {@link java.lang.Integer},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * The id-to-labels mapping for this automata.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Transition Labels</em>' map.
	 * @see de.hu_berlin.cchecker.core.models.pdfa.PdfaPackage#getProbabilisticAutomata_TransitionLabels()
	 * @model mapType="de.hu_berlin.cchecker.core.models.pdfa.TransitionLabelPair&lt;org.eclipse.emf.ecore.EIntegerObject, org.eclipse.emf.ecore.EString&gt;"
	 * @generated
	 */
	EMap<Integer, String> getTransitionLabels();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return &lt;%de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomataUtils%&gt;.toTextualRepresentation(this);'"
	 * @generated
	 */
	String toString();

} // ProbabilisticAutomata
