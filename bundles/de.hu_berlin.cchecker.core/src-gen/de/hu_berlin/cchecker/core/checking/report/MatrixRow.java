/**
 */
package de.hu_berlin.cchecker.core.checking.report;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Matrix Row</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.hu_berlin.cchecker.core.checking.report.MatrixRow#getElements <em>Elements</em>}</li>
 *   <li>{@link de.hu_berlin.cchecker.core.checking.report.MatrixRow#getResult <em>Result</em>}</li>
 * </ul>
 *
 * @see de.hu_berlin.cchecker.core.checking.report.ReportPackage#getMatrixRow()
 * @model
 * @generated
 */
public interface MatrixRow extends EObject {
	/**
	 * Returns the value of the '<em><b>Elements</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.Double}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Elements</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Elements</em>' attribute list.
	 * @see de.hu_berlin.cchecker.core.checking.report.ReportPackage#getMatrixRow_Elements()
	 * @model unique="false"
	 * @generated
	 */
	EList<Double> getElements();

	/**
	 * Returns the value of the '<em><b>Result</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link de.hu_berlin.cchecker.core.checking.report.ConsistencyLengthResult#getResultMatrix <em>Result Matrix</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Result</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Result</em>' container reference.
	 * @see #setResult(ConsistencyLengthResult)
	 * @see de.hu_berlin.cchecker.core.checking.report.ReportPackage#getMatrixRow_Result()
	 * @see de.hu_berlin.cchecker.core.checking.report.ConsistencyLengthResult#getResultMatrix
	 * @model opposite="resultMatrix" transient="false"
	 * @generated
	 */
	ConsistencyLengthResult getResult();

	/**
	 * Sets the value of the '{@link de.hu_berlin.cchecker.core.checking.report.MatrixRow#getResult <em>Result</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Result</em>' container reference.
	 * @see #getResult()
	 * @generated
	 */
	void setResult(ConsistencyLengthResult value);

} // MatrixRow
