/**
 */
package de.hu_berlin.cchecker.core.checking.report;

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
 * @see de.hu_berlin.cchecker.core.checking.report.ReportFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/emf/2002/GenModel basePackage='de.hu_berlin.cchecker.core.checking'"
 * @generated
 */
public interface ReportPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "report";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "de.hu_berlin.cchecker.core.checking.report";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "report";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ReportPackage eINSTANCE = de.hu_berlin.cchecker.core.checking.report.impl.ReportPackageImpl.init();

	/**
	 * The meta object id for the '{@link de.hu_berlin.cchecker.core.checking.report.impl.CounterExampleImpl <em>Counter Example</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.cchecker.core.checking.report.impl.CounterExampleImpl
	 * @see de.hu_berlin.cchecker.core.checking.report.impl.ReportPackageImpl#getCounterExample()
	 * @generated
	 */
	int COUNTER_EXAMPLE = 0;

	/**
	 * The feature id for the '<em><b>Trace Ids</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COUNTER_EXAMPLE__TRACE_IDS = 0;

	/**
	 * The feature id for the '<em><b>Trace</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COUNTER_EXAMPLE__TRACE = 1;

	/**
	 * The feature id for the '<em><b>Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COUNTER_EXAMPLE__INDEX = 2;

	/**
	 * The feature id for the '<em><b>Report</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COUNTER_EXAMPLE__REPORT = 3;

	/**
	 * The number of structural features of the '<em>Counter Example</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COUNTER_EXAMPLE_FEATURE_COUNT = 4;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COUNTER_EXAMPLE___TO_STRING = 0;

	/**
	 * The number of operations of the '<em>Counter Example</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COUNTER_EXAMPLE_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link de.hu_berlin.cchecker.core.checking.report.impl.MatrixRowImpl <em>Matrix Row</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.cchecker.core.checking.report.impl.MatrixRowImpl
	 * @see de.hu_berlin.cchecker.core.checking.report.impl.ReportPackageImpl#getMatrixRow()
	 * @generated
	 */
	int MATRIX_ROW = 1;

	/**
	 * The feature id for the '<em><b>Elements</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATRIX_ROW__ELEMENTS = 0;

	/**
	 * The feature id for the '<em><b>Result</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATRIX_ROW__RESULT = 1;

	/**
	 * The number of structural features of the '<em>Matrix Row</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATRIX_ROW_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Matrix Row</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATRIX_ROW_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.hu_berlin.cchecker.core.checking.report.impl.ConsistencyLengthResultImpl <em>Consistency Length Result</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.cchecker.core.checking.report.impl.ConsistencyLengthResultImpl
	 * @see de.hu_berlin.cchecker.core.checking.report.impl.ReportPackageImpl#getConsistencyLengthResult()
	 * @generated
	 */
	int CONSISTENCY_LENGTH_RESULT = 2;

	/**
	 * The feature id for the '<em><b>Trace Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSISTENCY_LENGTH_RESULT__TRACE_LENGTH = 0;

	/**
	 * The feature id for the '<em><b>Result Matrix</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSISTENCY_LENGTH_RESULT__RESULT_MATRIX = 1;

	/**
	 * The feature id for the '<em><b>Result</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSISTENCY_LENGTH_RESULT__RESULT = 2;

	/**
	 * The feature id for the '<em><b>Report</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSISTENCY_LENGTH_RESULT__REPORT = 3;

	/**
	 * The number of structural features of the '<em>Consistency Length Result</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSISTENCY_LENGTH_RESULT_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Consistency Length Result</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSISTENCY_LENGTH_RESULT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.hu_berlin.cchecker.core.checking.report.impl.ConsistencyReportImpl <em>Consistency Report</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.cchecker.core.checking.report.impl.ConsistencyReportImpl
	 * @see de.hu_berlin.cchecker.core.checking.report.impl.ReportPackageImpl#getConsistencyReport()
	 * @generated
	 */
	int CONSISTENCY_REPORT = 3;

	/**
	 * The feature id for the '<em><b>Created On</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSISTENCY_REPORT__CREATED_ON = 0;

	/**
	 * The feature id for the '<em><b>Model Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSISTENCY_REPORT__MODEL_PATH = 1;

	/**
	 * The feature id for the '<em><b>Model Checksum</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSISTENCY_REPORT__MODEL_CHECKSUM = 2;

	/**
	 * The feature id for the '<em><b>Trace Data Set Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSISTENCY_REPORT__TRACE_DATA_SET_PATH = 3;

	/**
	 * The feature id for the '<em><b>Trace Data Set Checksum</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSISTENCY_REPORT__TRACE_DATA_SET_CHECKSUM = 4;

	/**
	 * The feature id for the '<em><b>Label Mapping</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSISTENCY_REPORT__LABEL_MAPPING = 5;

	/**
	 * The feature id for the '<em><b>Footprint Matrices</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSISTENCY_REPORT__FOOTPRINT_MATRICES = 6;

	/**
	 * The feature id for the '<em><b>Counter Examples</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSISTENCY_REPORT__COUNTER_EXAMPLES = 7;

	/**
	 * The number of structural features of the '<em>Consistency Report</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSISTENCY_REPORT_FEATURE_COUNT = 8;

	/**
	 * The number of operations of the '<em>Consistency Report</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSISTENCY_REPORT_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link de.hu_berlin.cchecker.core.checking.report.CounterExample <em>Counter Example</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Counter Example</em>'.
	 * @see de.hu_berlin.cchecker.core.checking.report.CounterExample
	 * @generated
	 */
	EClass getCounterExample();

	/**
	 * Returns the meta object for the attribute list '{@link de.hu_berlin.cchecker.core.checking.report.CounterExample#getTraceIds <em>Trace Ids</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Trace Ids</em>'.
	 * @see de.hu_berlin.cchecker.core.checking.report.CounterExample#getTraceIds()
	 * @see #getCounterExample()
	 * @generated
	 */
	EAttribute getCounterExample_TraceIds();

	/**
	 * Returns the meta object for the attribute list '{@link de.hu_berlin.cchecker.core.checking.report.CounterExample#getTrace <em>Trace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Trace</em>'.
	 * @see de.hu_berlin.cchecker.core.checking.report.CounterExample#getTrace()
	 * @see #getCounterExample()
	 * @generated
	 */
	EAttribute getCounterExample_Trace();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.cchecker.core.checking.report.CounterExample#getIndex <em>Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Index</em>'.
	 * @see de.hu_berlin.cchecker.core.checking.report.CounterExample#getIndex()
	 * @see #getCounterExample()
	 * @generated
	 */
	EAttribute getCounterExample_Index();

	/**
	 * Returns the meta object for the container reference '{@link de.hu_berlin.cchecker.core.checking.report.CounterExample#getReport <em>Report</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Report</em>'.
	 * @see de.hu_berlin.cchecker.core.checking.report.CounterExample#getReport()
	 * @see #getCounterExample()
	 * @generated
	 */
	EReference getCounterExample_Report();

	/**
	 * Returns the meta object for the '{@link de.hu_berlin.cchecker.core.checking.report.CounterExample#toString() <em>To String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>To String</em>' operation.
	 * @see de.hu_berlin.cchecker.core.checking.report.CounterExample#toString()
	 * @generated
	 */
	EOperation getCounterExample__ToString();

	/**
	 * Returns the meta object for class '{@link de.hu_berlin.cchecker.core.checking.report.MatrixRow <em>Matrix Row</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Matrix Row</em>'.
	 * @see de.hu_berlin.cchecker.core.checking.report.MatrixRow
	 * @generated
	 */
	EClass getMatrixRow();

	/**
	 * Returns the meta object for the attribute list '{@link de.hu_berlin.cchecker.core.checking.report.MatrixRow#getElements <em>Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Elements</em>'.
	 * @see de.hu_berlin.cchecker.core.checking.report.MatrixRow#getElements()
	 * @see #getMatrixRow()
	 * @generated
	 */
	EAttribute getMatrixRow_Elements();

	/**
	 * Returns the meta object for the container reference '{@link de.hu_berlin.cchecker.core.checking.report.MatrixRow#getResult <em>Result</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Result</em>'.
	 * @see de.hu_berlin.cchecker.core.checking.report.MatrixRow#getResult()
	 * @see #getMatrixRow()
	 * @generated
	 */
	EReference getMatrixRow_Result();

	/**
	 * Returns the meta object for class '{@link de.hu_berlin.cchecker.core.checking.report.ConsistencyLengthResult <em>Consistency Length Result</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Consistency Length Result</em>'.
	 * @see de.hu_berlin.cchecker.core.checking.report.ConsistencyLengthResult
	 * @generated
	 */
	EClass getConsistencyLengthResult();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.cchecker.core.checking.report.ConsistencyLengthResult#getTraceLength <em>Trace Length</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Trace Length</em>'.
	 * @see de.hu_berlin.cchecker.core.checking.report.ConsistencyLengthResult#getTraceLength()
	 * @see #getConsistencyLengthResult()
	 * @generated
	 */
	EAttribute getConsistencyLengthResult_TraceLength();

	/**
	 * Returns the meta object for the containment reference list '{@link de.hu_berlin.cchecker.core.checking.report.ConsistencyLengthResult#getResultMatrix <em>Result Matrix</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Result Matrix</em>'.
	 * @see de.hu_berlin.cchecker.core.checking.report.ConsistencyLengthResult#getResultMatrix()
	 * @see #getConsistencyLengthResult()
	 * @generated
	 */
	EReference getConsistencyLengthResult_ResultMatrix();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.cchecker.core.checking.report.ConsistencyLengthResult#getResult <em>Result</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Result</em>'.
	 * @see de.hu_berlin.cchecker.core.checking.report.ConsistencyLengthResult#getResult()
	 * @see #getConsistencyLengthResult()
	 * @generated
	 */
	EAttribute getConsistencyLengthResult_Result();

	/**
	 * Returns the meta object for the container reference '{@link de.hu_berlin.cchecker.core.checking.report.ConsistencyLengthResult#getReport <em>Report</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Report</em>'.
	 * @see de.hu_berlin.cchecker.core.checking.report.ConsistencyLengthResult#getReport()
	 * @see #getConsistencyLengthResult()
	 * @generated
	 */
	EReference getConsistencyLengthResult_Report();

	/**
	 * Returns the meta object for class '{@link de.hu_berlin.cchecker.core.checking.report.ConsistencyReport <em>Consistency Report</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Consistency Report</em>'.
	 * @see de.hu_berlin.cchecker.core.checking.report.ConsistencyReport
	 * @generated
	 */
	EClass getConsistencyReport();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.cchecker.core.checking.report.ConsistencyReport#getCreatedOn <em>Created On</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Created On</em>'.
	 * @see de.hu_berlin.cchecker.core.checking.report.ConsistencyReport#getCreatedOn()
	 * @see #getConsistencyReport()
	 * @generated
	 */
	EAttribute getConsistencyReport_CreatedOn();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.cchecker.core.checking.report.ConsistencyReport#getModelPath <em>Model Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Model Path</em>'.
	 * @see de.hu_berlin.cchecker.core.checking.report.ConsistencyReport#getModelPath()
	 * @see #getConsistencyReport()
	 * @generated
	 */
	EAttribute getConsistencyReport_ModelPath();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.cchecker.core.checking.report.ConsistencyReport#getModelChecksum <em>Model Checksum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Model Checksum</em>'.
	 * @see de.hu_berlin.cchecker.core.checking.report.ConsistencyReport#getModelChecksum()
	 * @see #getConsistencyReport()
	 * @generated
	 */
	EAttribute getConsistencyReport_ModelChecksum();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.cchecker.core.checking.report.ConsistencyReport#getTraceDataSetPath <em>Trace Data Set Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Trace Data Set Path</em>'.
	 * @see de.hu_berlin.cchecker.core.checking.report.ConsistencyReport#getTraceDataSetPath()
	 * @see #getConsistencyReport()
	 * @generated
	 */
	EAttribute getConsistencyReport_TraceDataSetPath();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.cchecker.core.checking.report.ConsistencyReport#getTraceDataSetChecksum <em>Trace Data Set Checksum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Trace Data Set Checksum</em>'.
	 * @see de.hu_berlin.cchecker.core.checking.report.ConsistencyReport#getTraceDataSetChecksum()
	 * @see #getConsistencyReport()
	 * @generated
	 */
	EAttribute getConsistencyReport_TraceDataSetChecksum();

	/**
	 * Returns the meta object for the map '{@link de.hu_berlin.cchecker.core.checking.report.ConsistencyReport#getLabelMapping <em>Label Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Label Mapping</em>'.
	 * @see de.hu_berlin.cchecker.core.checking.report.ConsistencyReport#getLabelMapping()
	 * @see #getConsistencyReport()
	 * @generated
	 */
	EReference getConsistencyReport_LabelMapping();

	/**
	 * Returns the meta object for the containment reference list '{@link de.hu_berlin.cchecker.core.checking.report.ConsistencyReport#getFootprintMatrices <em>Footprint Matrices</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Footprint Matrices</em>'.
	 * @see de.hu_berlin.cchecker.core.checking.report.ConsistencyReport#getFootprintMatrices()
	 * @see #getConsistencyReport()
	 * @generated
	 */
	EReference getConsistencyReport_FootprintMatrices();

	/**
	 * Returns the meta object for the containment reference list '{@link de.hu_berlin.cchecker.core.checking.report.ConsistencyReport#getCounterExamples <em>Counter Examples</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Counter Examples</em>'.
	 * @see de.hu_berlin.cchecker.core.checking.report.ConsistencyReport#getCounterExamples()
	 * @see #getConsistencyReport()
	 * @generated
	 */
	EReference getConsistencyReport_CounterExamples();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ReportFactory getReportFactory();

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
		 * The meta object literal for the '{@link de.hu_berlin.cchecker.core.checking.report.impl.CounterExampleImpl <em>Counter Example</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.cchecker.core.checking.report.impl.CounterExampleImpl
		 * @see de.hu_berlin.cchecker.core.checking.report.impl.ReportPackageImpl#getCounterExample()
		 * @generated
		 */
		EClass COUNTER_EXAMPLE = eINSTANCE.getCounterExample();

		/**
		 * The meta object literal for the '<em><b>Trace Ids</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COUNTER_EXAMPLE__TRACE_IDS = eINSTANCE.getCounterExample_TraceIds();

		/**
		 * The meta object literal for the '<em><b>Trace</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COUNTER_EXAMPLE__TRACE = eINSTANCE.getCounterExample_Trace();

		/**
		 * The meta object literal for the '<em><b>Index</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COUNTER_EXAMPLE__INDEX = eINSTANCE.getCounterExample_Index();

		/**
		 * The meta object literal for the '<em><b>Report</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COUNTER_EXAMPLE__REPORT = eINSTANCE.getCounterExample_Report();

		/**
		 * The meta object literal for the '<em><b>To String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation COUNTER_EXAMPLE___TO_STRING = eINSTANCE.getCounterExample__ToString();

		/**
		 * The meta object literal for the '{@link de.hu_berlin.cchecker.core.checking.report.impl.MatrixRowImpl <em>Matrix Row</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.cchecker.core.checking.report.impl.MatrixRowImpl
		 * @see de.hu_berlin.cchecker.core.checking.report.impl.ReportPackageImpl#getMatrixRow()
		 * @generated
		 */
		EClass MATRIX_ROW = eINSTANCE.getMatrixRow();

		/**
		 * The meta object literal for the '<em><b>Elements</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MATRIX_ROW__ELEMENTS = eINSTANCE.getMatrixRow_Elements();

		/**
		 * The meta object literal for the '<em><b>Result</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MATRIX_ROW__RESULT = eINSTANCE.getMatrixRow_Result();

		/**
		 * The meta object literal for the '{@link de.hu_berlin.cchecker.core.checking.report.impl.ConsistencyLengthResultImpl <em>Consistency Length Result</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.cchecker.core.checking.report.impl.ConsistencyLengthResultImpl
		 * @see de.hu_berlin.cchecker.core.checking.report.impl.ReportPackageImpl#getConsistencyLengthResult()
		 * @generated
		 */
		EClass CONSISTENCY_LENGTH_RESULT = eINSTANCE.getConsistencyLengthResult();

		/**
		 * The meta object literal for the '<em><b>Trace Length</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONSISTENCY_LENGTH_RESULT__TRACE_LENGTH = eINSTANCE.getConsistencyLengthResult_TraceLength();

		/**
		 * The meta object literal for the '<em><b>Result Matrix</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONSISTENCY_LENGTH_RESULT__RESULT_MATRIX = eINSTANCE.getConsistencyLengthResult_ResultMatrix();

		/**
		 * The meta object literal for the '<em><b>Result</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONSISTENCY_LENGTH_RESULT__RESULT = eINSTANCE.getConsistencyLengthResult_Result();

		/**
		 * The meta object literal for the '<em><b>Report</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONSISTENCY_LENGTH_RESULT__REPORT = eINSTANCE.getConsistencyLengthResult_Report();

		/**
		 * The meta object literal for the '{@link de.hu_berlin.cchecker.core.checking.report.impl.ConsistencyReportImpl <em>Consistency Report</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.cchecker.core.checking.report.impl.ConsistencyReportImpl
		 * @see de.hu_berlin.cchecker.core.checking.report.impl.ReportPackageImpl#getConsistencyReport()
		 * @generated
		 */
		EClass CONSISTENCY_REPORT = eINSTANCE.getConsistencyReport();

		/**
		 * The meta object literal for the '<em><b>Created On</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONSISTENCY_REPORT__CREATED_ON = eINSTANCE.getConsistencyReport_CreatedOn();

		/**
		 * The meta object literal for the '<em><b>Model Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONSISTENCY_REPORT__MODEL_PATH = eINSTANCE.getConsistencyReport_ModelPath();

		/**
		 * The meta object literal for the '<em><b>Model Checksum</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONSISTENCY_REPORT__MODEL_CHECKSUM = eINSTANCE.getConsistencyReport_ModelChecksum();

		/**
		 * The meta object literal for the '<em><b>Trace Data Set Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONSISTENCY_REPORT__TRACE_DATA_SET_PATH = eINSTANCE.getConsistencyReport_TraceDataSetPath();

		/**
		 * The meta object literal for the '<em><b>Trace Data Set Checksum</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONSISTENCY_REPORT__TRACE_DATA_SET_CHECKSUM = eINSTANCE.getConsistencyReport_TraceDataSetChecksum();

		/**
		 * The meta object literal for the '<em><b>Label Mapping</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONSISTENCY_REPORT__LABEL_MAPPING = eINSTANCE.getConsistencyReport_LabelMapping();

		/**
		 * The meta object literal for the '<em><b>Footprint Matrices</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONSISTENCY_REPORT__FOOTPRINT_MATRICES = eINSTANCE.getConsistencyReport_FootprintMatrices();

		/**
		 * The meta object literal for the '<em><b>Counter Examples</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONSISTENCY_REPORT__COUNTER_EXAMPLES = eINSTANCE.getConsistencyReport_CounterExamples();

	}

} //ReportPackage
