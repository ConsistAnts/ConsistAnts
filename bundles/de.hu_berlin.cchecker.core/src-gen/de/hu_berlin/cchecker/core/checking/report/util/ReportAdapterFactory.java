/**
 */
package de.hu_berlin.cchecker.core.checking.report.util;

import de.hu_berlin.cchecker.core.checking.report.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see de.hu_berlin.cchecker.core.checking.report.ReportPackage
 * @generated
 */
public class ReportAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ReportPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReportAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = ReportPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ReportSwitch<Adapter> modelSwitch =
		new ReportSwitch<Adapter>() {
			@Override
			public Adapter caseCounterExample(CounterExample object) {
				return createCounterExampleAdapter();
			}
			@Override
			public Adapter caseMatrixRow(MatrixRow object) {
				return createMatrixRowAdapter();
			}
			@Override
			public Adapter caseConsistencyLengthResult(ConsistencyLengthResult object) {
				return createConsistencyLengthResultAdapter();
			}
			@Override
			public Adapter caseConsistencyReport(ConsistencyReport object) {
				return createConsistencyReportAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link de.hu_berlin.cchecker.core.checking.report.CounterExample <em>Counter Example</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hu_berlin.cchecker.core.checking.report.CounterExample
	 * @generated
	 */
	public Adapter createCounterExampleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.hu_berlin.cchecker.core.checking.report.MatrixRow <em>Matrix Row</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hu_berlin.cchecker.core.checking.report.MatrixRow
	 * @generated
	 */
	public Adapter createMatrixRowAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.hu_berlin.cchecker.core.checking.report.ConsistencyLengthResult <em>Consistency Length Result</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hu_berlin.cchecker.core.checking.report.ConsistencyLengthResult
	 * @generated
	 */
	public Adapter createConsistencyLengthResultAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.hu_berlin.cchecker.core.checking.report.ConsistencyReport <em>Consistency Report</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hu_berlin.cchecker.core.checking.report.ConsistencyReport
	 * @generated
	 */
	public Adapter createConsistencyReportAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //ReportAdapterFactory
