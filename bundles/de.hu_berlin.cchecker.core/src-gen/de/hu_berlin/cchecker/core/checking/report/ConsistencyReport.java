/**
 */
package de.hu_berlin.cchecker.core.checking.report;

import java.util.Date;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Consistency Report</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * A consistency report contains all the information a consistency checking
 * algorithm provides to measure consistency.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.hu_berlin.cchecker.core.checking.report.ConsistencyReport#getCreatedOn <em>Created On</em>}</li>
 *   <li>{@link de.hu_berlin.cchecker.core.checking.report.ConsistencyReport#getModelPath <em>Model Path</em>}</li>
 *   <li>{@link de.hu_berlin.cchecker.core.checking.report.ConsistencyReport#getModelChecksum <em>Model Checksum</em>}</li>
 *   <li>{@link de.hu_berlin.cchecker.core.checking.report.ConsistencyReport#getTraceDataSetPath <em>Trace Data Set Path</em>}</li>
 *   <li>{@link de.hu_berlin.cchecker.core.checking.report.ConsistencyReport#getTraceDataSetChecksum <em>Trace Data Set Checksum</em>}</li>
 *   <li>{@link de.hu_berlin.cchecker.core.checking.report.ConsistencyReport#getLabelMapping <em>Label Mapping</em>}</li>
 *   <li>{@link de.hu_berlin.cchecker.core.checking.report.ConsistencyReport#getFootprintMatrices <em>Footprint Matrices</em>}</li>
 *   <li>{@link de.hu_berlin.cchecker.core.checking.report.ConsistencyReport#getCounterExamples <em>Counter Examples</em>}</li>
 * </ul>
 *
 * @see de.hu_berlin.cchecker.core.checking.report.ReportPackage#getConsistencyReport()
 * @model
 * @generated
 */
public interface ConsistencyReport extends EObject {
	/**
	 * Returns the value of the '<em><b>Created On</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Created On</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Created On</em>' attribute.
	 * @see #setCreatedOn(Date)
	 * @see de.hu_berlin.cchecker.core.checking.report.ReportPackage#getConsistencyReport_CreatedOn()
	 * @model unique="false"
	 * @generated
	 */
	Date getCreatedOn();

	/**
	 * Sets the value of the '{@link de.hu_berlin.cchecker.core.checking.report.ConsistencyReport#getCreatedOn <em>Created On</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Created On</em>' attribute.
	 * @see #getCreatedOn()
	 * @generated
	 */
	void setCreatedOn(Date value);

	/**
	 * Returns the value of the '<em><b>Model Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Model Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Model Path</em>' attribute.
	 * @see #setModelPath(String)
	 * @see de.hu_berlin.cchecker.core.checking.report.ReportPackage#getConsistencyReport_ModelPath()
	 * @model unique="false"
	 * @generated
	 */
	String getModelPath();

	/**
	 * Sets the value of the '{@link de.hu_berlin.cchecker.core.checking.report.ConsistencyReport#getModelPath <em>Model Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Model Path</em>' attribute.
	 * @see #getModelPath()
	 * @generated
	 */
	void setModelPath(String value);

	/**
	 * Returns the value of the '<em><b>Model Checksum</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Model Checksum</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Model Checksum</em>' attribute.
	 * @see #setModelChecksum(String)
	 * @see de.hu_berlin.cchecker.core.checking.report.ReportPackage#getConsistencyReport_ModelChecksum()
	 * @model unique="false"
	 * @generated
	 */
	String getModelChecksum();

	/**
	 * Sets the value of the '{@link de.hu_berlin.cchecker.core.checking.report.ConsistencyReport#getModelChecksum <em>Model Checksum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Model Checksum</em>' attribute.
	 * @see #getModelChecksum()
	 * @generated
	 */
	void setModelChecksum(String value);

	/**
	 * Returns the value of the '<em><b>Trace Data Set Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Trace Data Set Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Trace Data Set Path</em>' attribute.
	 * @see #setTraceDataSetPath(String)
	 * @see de.hu_berlin.cchecker.core.checking.report.ReportPackage#getConsistencyReport_TraceDataSetPath()
	 * @model unique="false"
	 * @generated
	 */
	String getTraceDataSetPath();

	/**
	 * Sets the value of the '{@link de.hu_berlin.cchecker.core.checking.report.ConsistencyReport#getTraceDataSetPath <em>Trace Data Set Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Trace Data Set Path</em>' attribute.
	 * @see #getTraceDataSetPath()
	 * @generated
	 */
	void setTraceDataSetPath(String value);

	/**
	 * Returns the value of the '<em><b>Trace Data Set Checksum</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Trace Data Set Checksum</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Trace Data Set Checksum</em>' attribute.
	 * @see #setTraceDataSetChecksum(String)
	 * @see de.hu_berlin.cchecker.core.checking.report.ReportPackage#getConsistencyReport_TraceDataSetChecksum()
	 * @model unique="false"
	 * @generated
	 */
	String getTraceDataSetChecksum();

	/**
	 * Sets the value of the '{@link de.hu_berlin.cchecker.core.checking.report.ConsistencyReport#getTraceDataSetChecksum <em>Trace Data Set Checksum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Trace Data Set Checksum</em>' attribute.
	 * @see #getTraceDataSetChecksum()
	 * @generated
	 */
	void setTraceDataSetChecksum(String value);

	/**
	 * Returns the value of the '<em><b>Label Mapping</b></em>' map.
	 * The key is of type {@link java.lang.Integer},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Label Mapping</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Label Mapping</em>' map.
	 * @see de.hu_berlin.cchecker.core.checking.report.ReportPackage#getConsistencyReport_LabelMapping()
	 * @model mapType="de.hu_berlin.cchecker.core.models.pdfa.TransitionLabelPair&lt;org.eclipse.emf.ecore.EIntegerObject, org.eclipse.emf.ecore.EString&gt;"
	 * @generated
	 */
	EMap<Integer, String> getLabelMapping();

	/**
	 * Returns the value of the '<em><b>Footprint Matrices</b></em>' containment reference list.
	 * The list contents are of type {@link de.hu_berlin.cchecker.core.checking.report.ConsistencyLengthResult}.
	 * It is bidirectional and its opposite is '{@link de.hu_berlin.cchecker.core.checking.report.ConsistencyLengthResult#getReport <em>Report</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Footprint Matrices</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Footprint Matrices</em>' containment reference list.
	 * @see de.hu_berlin.cchecker.core.checking.report.ReportPackage#getConsistencyReport_FootprintMatrices()
	 * @see de.hu_berlin.cchecker.core.checking.report.ConsistencyLengthResult#getReport
	 * @model opposite="report" containment="true"
	 * @generated
	 */
	EList<ConsistencyLengthResult> getFootprintMatrices();

	/**
	 * Returns the value of the '<em><b>Counter Examples</b></em>' containment reference list.
	 * The list contents are of type {@link de.hu_berlin.cchecker.core.checking.report.CounterExample}.
	 * It is bidirectional and its opposite is '{@link de.hu_berlin.cchecker.core.checking.report.CounterExample#getReport <em>Report</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Counter Examples</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Counter Examples</em>' containment reference list.
	 * @see de.hu_berlin.cchecker.core.checking.report.ReportPackage#getConsistencyReport_CounterExamples()
	 * @see de.hu_berlin.cchecker.core.checking.report.CounterExample#getReport
	 * @model opposite="report" containment="true"
	 * @generated
	 */
	EList<CounterExample> getCounterExamples();

} // ConsistencyReport
