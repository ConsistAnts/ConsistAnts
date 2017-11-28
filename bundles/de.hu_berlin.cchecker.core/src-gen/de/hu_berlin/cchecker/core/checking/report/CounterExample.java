/**
 */
package de.hu_berlin.cchecker.core.checking.report;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Counter Example</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * A CounterExample represents one or muliple traces in
 * the trace data set, that cannot be re-produced in the model
 * the consistency check was performed with.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.hu_berlin.cchecker.core.checking.report.CounterExample#getTraceIds <em>Trace Ids</em>}</li>
 *   <li>{@link de.hu_berlin.cchecker.core.checking.report.CounterExample#getTrace <em>Trace</em>}</li>
 *   <li>{@link de.hu_berlin.cchecker.core.checking.report.CounterExample#getIndex <em>Index</em>}</li>
 *   <li>{@link de.hu_berlin.cchecker.core.checking.report.CounterExample#getReport <em>Report</em>}</li>
 * </ul>
 *
 * @see de.hu_berlin.cchecker.core.checking.report.ReportPackage#getCounterExample()
 * @model
 * @generated
 */
public interface CounterExample extends EObject {
	/**
	 * Returns the value of the '<em><b>Trace Ids</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Trace Ids</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Trace Ids</em>' attribute list.
	 * @see de.hu_berlin.cchecker.core.checking.report.ReportPackage#getCounterExample_TraceIds()
	 * @model unique="false"
	 * @generated
	 */
	EList<String> getTraceIds();

	/**
	 * Returns the value of the '<em><b>Trace</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.Integer}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Trace</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Trace</em>' attribute list.
	 * @see de.hu_berlin.cchecker.core.checking.report.ReportPackage#getCounterExample_Trace()
	 * @model unique="false"
	 * @generated
	 */
	EList<Integer> getTrace();

	/**
	 * Returns the value of the '<em><b>Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Index</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Index</em>' attribute.
	 * @see #setIndex(int)
	 * @see de.hu_berlin.cchecker.core.checking.report.ReportPackage#getCounterExample_Index()
	 * @model unique="false"
	 * @generated
	 */
	int getIndex();

	/**
	 * Sets the value of the '{@link de.hu_berlin.cchecker.core.checking.report.CounterExample#getIndex <em>Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Index</em>' attribute.
	 * @see #getIndex()
	 * @generated
	 */
	void setIndex(int value);

	/**
	 * Returns the value of the '<em><b>Report</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link de.hu_berlin.cchecker.core.checking.report.ConsistencyReport#getCounterExamples <em>Counter Examples</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Report</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Report</em>' container reference.
	 * @see #setReport(ConsistencyReport)
	 * @see de.hu_berlin.cchecker.core.checking.report.ReportPackage#getCounterExample_Report()
	 * @see de.hu_berlin.cchecker.core.checking.report.ConsistencyReport#getCounterExamples
	 * @model opposite="counterExamples" transient="false"
	 * @generated
	 */
	ConsistencyReport getReport();

	/**
	 * Sets the value of the '{@link de.hu_berlin.cchecker.core.checking.report.CounterExample#getReport <em>Report</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Report</em>' container reference.
	 * @see #getReport()
	 * @generated
	 */
	void setReport(ConsistencyReport value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%java.lang.String%&gt; _string = this.getTraceIds().toString();\n&lt;%java.lang.String%&gt; _plus = (\"CounterExample with IDs\" + _string);\n&lt;%java.lang.String%&gt; _plus_1 = (_plus + \" for trace \");\n&lt;%java.lang.String%&gt; _string_1 = this.getTrace().toString();\n&lt;%java.lang.String%&gt; _plus_2 = (_plus_1 + _string_1);\n&lt;%java.lang.String%&gt; _plus_3 = (_plus_2 + \" at index \");\nint _index = this.getIndex();\nreturn (_plus_3 + &lt;%java.lang.Integer%&gt;.valueOf(_index));'"
	 * @generated
	 */
	String toString();

} // CounterExample
