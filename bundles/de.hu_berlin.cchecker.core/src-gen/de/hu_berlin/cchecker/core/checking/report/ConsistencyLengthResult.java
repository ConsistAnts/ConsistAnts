/**
 */
package de.hu_berlin.cchecker.core.checking.report;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Consistency Length Result</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * A ConsistencyLengthResult represents the consistency of a
 * check regarding a specific trace length.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.hu_berlin.cchecker.core.checking.report.ConsistencyLengthResult#getTraceLength <em>Trace Length</em>}</li>
 *   <li>{@link de.hu_berlin.cchecker.core.checking.report.ConsistencyLengthResult#getResultMatrix <em>Result Matrix</em>}</li>
 *   <li>{@link de.hu_berlin.cchecker.core.checking.report.ConsistencyLengthResult#getResult <em>Result</em>}</li>
 *   <li>{@link de.hu_berlin.cchecker.core.checking.report.ConsistencyLengthResult#getReport <em>Report</em>}</li>
 * </ul>
 *
 * @see de.hu_berlin.cchecker.core.checking.report.ReportPackage#getConsistencyLengthResult()
 * @model
 * @generated
 */
public interface ConsistencyLengthResult extends EObject {
	/**
	 * Returns the value of the '<em><b>Trace Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Trace Length</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Trace Length</em>' attribute.
	 * @see #setTraceLength(int)
	 * @see de.hu_berlin.cchecker.core.checking.report.ReportPackage#getConsistencyLengthResult_TraceLength()
	 * @model unique="false"
	 * @generated
	 */
	int getTraceLength();

	/**
	 * Sets the value of the '{@link de.hu_berlin.cchecker.core.checking.report.ConsistencyLengthResult#getTraceLength <em>Trace Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Trace Length</em>' attribute.
	 * @see #getTraceLength()
	 * @generated
	 */
	void setTraceLength(int value);

	/**
	 * Returns the value of the '<em><b>Result Matrix</b></em>' containment reference list.
	 * The list contents are of type {@link de.hu_berlin.cchecker.core.checking.report.MatrixRow}.
	 * It is bidirectional and its opposite is '{@link de.hu_berlin.cchecker.core.checking.report.MatrixRow#getResult <em>Result</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Result Matrix</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Result Matrix</em>' containment reference list.
	 * @see de.hu_berlin.cchecker.core.checking.report.ReportPackage#getConsistencyLengthResult_ResultMatrix()
	 * @see de.hu_berlin.cchecker.core.checking.report.MatrixRow#getResult
	 * @model opposite="result" containment="true"
	 * @generated
	 */
	EList<MatrixRow> getResultMatrix();

	/**
	 * Returns the value of the '<em><b>Result</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Result</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Result</em>' attribute.
	 * @see #setResult(double)
	 * @see de.hu_berlin.cchecker.core.checking.report.ReportPackage#getConsistencyLengthResult_Result()
	 * @model unique="false"
	 * @generated
	 */
	double getResult();

	/**
	 * Sets the value of the '{@link de.hu_berlin.cchecker.core.checking.report.ConsistencyLengthResult#getResult <em>Result</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Result</em>' attribute.
	 * @see #getResult()
	 * @generated
	 */
	void setResult(double value);

	/**
	 * Returns the value of the '<em><b>Report</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link de.hu_berlin.cchecker.core.checking.report.ConsistencyReport#getFootprintMatrices <em>Footprint Matrices</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Report</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Report</em>' container reference.
	 * @see #setReport(ConsistencyReport)
	 * @see de.hu_berlin.cchecker.core.checking.report.ReportPackage#getConsistencyLengthResult_Report()
	 * @see de.hu_berlin.cchecker.core.checking.report.ConsistencyReport#getFootprintMatrices
	 * @model opposite="footprintMatrices" transient="false"
	 * @generated
	 */
	ConsistencyReport getReport();

	/**
	 * Sets the value of the '{@link de.hu_berlin.cchecker.core.checking.report.ConsistencyLengthResult#getReport <em>Report</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Report</em>' container reference.
	 * @see #getReport()
	 * @generated
	 */
	void setReport(ConsistencyReport value);

} // ConsistencyLengthResult
