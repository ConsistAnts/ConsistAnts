/**
 */
package de.hu_berlin.cchecker.core.checking.report;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see de.hu_berlin.cchecker.core.checking.report.ReportPackage
 * @generated
 */
public interface ReportFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ReportFactory eINSTANCE = de.hu_berlin.cchecker.core.checking.report.impl.ReportFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Counter Example</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Counter Example</em>'.
	 * @generated
	 */
	CounterExample createCounterExample();

	/**
	 * Returns a new object of class '<em>Matrix Row</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Matrix Row</em>'.
	 * @generated
	 */
	MatrixRow createMatrixRow();

	/**
	 * Returns a new object of class '<em>Consistency Length Result</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Consistency Length Result</em>'.
	 * @generated
	 */
	ConsistencyLengthResult createConsistencyLengthResult();

	/**
	 * Returns a new object of class '<em>Consistency Report</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Consistency Report</em>'.
	 * @generated
	 */
	ConsistencyReport createConsistencyReport();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ReportPackage getReportPackage();

} //ReportFactory
