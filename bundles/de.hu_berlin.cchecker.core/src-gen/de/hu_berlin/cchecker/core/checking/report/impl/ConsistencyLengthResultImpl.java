/**
 */
package de.hu_berlin.cchecker.core.checking.report.impl;

import de.hu_berlin.cchecker.core.checking.report.ConsistencyLengthResult;
import de.hu_berlin.cchecker.core.checking.report.ConsistencyReport;
import de.hu_berlin.cchecker.core.checking.report.MatrixRow;
import de.hu_berlin.cchecker.core.checking.report.ReportPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Consistency Length Result</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.hu_berlin.cchecker.core.checking.report.impl.ConsistencyLengthResultImpl#getTraceLength <em>Trace Length</em>}</li>
 *   <li>{@link de.hu_berlin.cchecker.core.checking.report.impl.ConsistencyLengthResultImpl#getResultMatrix <em>Result Matrix</em>}</li>
 *   <li>{@link de.hu_berlin.cchecker.core.checking.report.impl.ConsistencyLengthResultImpl#getResult <em>Result</em>}</li>
 *   <li>{@link de.hu_berlin.cchecker.core.checking.report.impl.ConsistencyLengthResultImpl#getReport <em>Report</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConsistencyLengthResultImpl extends MinimalEObjectImpl.Container implements ConsistencyLengthResult {
	/**
	 * The default value of the '{@link #getTraceLength() <em>Trace Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTraceLength()
	 * @generated
	 * @ordered
	 */
	protected static final int TRACE_LENGTH_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getTraceLength() <em>Trace Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTraceLength()
	 * @generated
	 * @ordered
	 */
	protected int traceLength = TRACE_LENGTH_EDEFAULT;

	/**
	 * The cached value of the '{@link #getResultMatrix() <em>Result Matrix</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResultMatrix()
	 * @generated
	 * @ordered
	 */
	protected EList<MatrixRow> resultMatrix;

	/**
	 * The default value of the '{@link #getResult() <em>Result</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResult()
	 * @generated
	 * @ordered
	 */
	protected static final double RESULT_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getResult() <em>Result</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResult()
	 * @generated
	 * @ordered
	 */
	protected double result = RESULT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConsistencyLengthResultImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ReportPackage.Literals.CONSISTENCY_LENGTH_RESULT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getTraceLength() {
		return traceLength;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTraceLength(int newTraceLength) {
		int oldTraceLength = traceLength;
		traceLength = newTraceLength;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ReportPackage.CONSISTENCY_LENGTH_RESULT__TRACE_LENGTH, oldTraceLength, traceLength));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MatrixRow> getResultMatrix() {
		if (resultMatrix == null) {
			resultMatrix = new EObjectContainmentWithInverseEList<MatrixRow>(MatrixRow.class, this, ReportPackage.CONSISTENCY_LENGTH_RESULT__RESULT_MATRIX, ReportPackage.MATRIX_ROW__RESULT);
		}
		return resultMatrix;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getResult() {
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setResult(double newResult) {
		double oldResult = result;
		result = newResult;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ReportPackage.CONSISTENCY_LENGTH_RESULT__RESULT, oldResult, result));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConsistencyReport getReport() {
		if (eContainerFeatureID() != ReportPackage.CONSISTENCY_LENGTH_RESULT__REPORT) return null;
		return (ConsistencyReport)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConsistencyReport basicGetReport() {
		if (eContainerFeatureID() != ReportPackage.CONSISTENCY_LENGTH_RESULT__REPORT) return null;
		return (ConsistencyReport)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetReport(ConsistencyReport newReport, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newReport, ReportPackage.CONSISTENCY_LENGTH_RESULT__REPORT, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReport(ConsistencyReport newReport) {
		if (newReport != eInternalContainer() || (eContainerFeatureID() != ReportPackage.CONSISTENCY_LENGTH_RESULT__REPORT && newReport != null)) {
			if (EcoreUtil.isAncestor(this, newReport))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newReport != null)
				msgs = ((InternalEObject)newReport).eInverseAdd(this, ReportPackage.CONSISTENCY_REPORT__FOOTPRINT_MATRICES, ConsistencyReport.class, msgs);
			msgs = basicSetReport(newReport, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ReportPackage.CONSISTENCY_LENGTH_RESULT__REPORT, newReport, newReport));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ReportPackage.CONSISTENCY_LENGTH_RESULT__RESULT_MATRIX:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getResultMatrix()).basicAdd(otherEnd, msgs);
			case ReportPackage.CONSISTENCY_LENGTH_RESULT__REPORT:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetReport((ConsistencyReport)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ReportPackage.CONSISTENCY_LENGTH_RESULT__RESULT_MATRIX:
				return ((InternalEList<?>)getResultMatrix()).basicRemove(otherEnd, msgs);
			case ReportPackage.CONSISTENCY_LENGTH_RESULT__REPORT:
				return basicSetReport(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case ReportPackage.CONSISTENCY_LENGTH_RESULT__REPORT:
				return eInternalContainer().eInverseRemove(this, ReportPackage.CONSISTENCY_REPORT__FOOTPRINT_MATRICES, ConsistencyReport.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ReportPackage.CONSISTENCY_LENGTH_RESULT__TRACE_LENGTH:
				return getTraceLength();
			case ReportPackage.CONSISTENCY_LENGTH_RESULT__RESULT_MATRIX:
				return getResultMatrix();
			case ReportPackage.CONSISTENCY_LENGTH_RESULT__RESULT:
				return getResult();
			case ReportPackage.CONSISTENCY_LENGTH_RESULT__REPORT:
				if (resolve) return getReport();
				return basicGetReport();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ReportPackage.CONSISTENCY_LENGTH_RESULT__TRACE_LENGTH:
				setTraceLength((Integer)newValue);
				return;
			case ReportPackage.CONSISTENCY_LENGTH_RESULT__RESULT_MATRIX:
				getResultMatrix().clear();
				getResultMatrix().addAll((Collection<? extends MatrixRow>)newValue);
				return;
			case ReportPackage.CONSISTENCY_LENGTH_RESULT__RESULT:
				setResult((Double)newValue);
				return;
			case ReportPackage.CONSISTENCY_LENGTH_RESULT__REPORT:
				setReport((ConsistencyReport)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ReportPackage.CONSISTENCY_LENGTH_RESULT__TRACE_LENGTH:
				setTraceLength(TRACE_LENGTH_EDEFAULT);
				return;
			case ReportPackage.CONSISTENCY_LENGTH_RESULT__RESULT_MATRIX:
				getResultMatrix().clear();
				return;
			case ReportPackage.CONSISTENCY_LENGTH_RESULT__RESULT:
				setResult(RESULT_EDEFAULT);
				return;
			case ReportPackage.CONSISTENCY_LENGTH_RESULT__REPORT:
				setReport((ConsistencyReport)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ReportPackage.CONSISTENCY_LENGTH_RESULT__TRACE_LENGTH:
				return traceLength != TRACE_LENGTH_EDEFAULT;
			case ReportPackage.CONSISTENCY_LENGTH_RESULT__RESULT_MATRIX:
				return resultMatrix != null && !resultMatrix.isEmpty();
			case ReportPackage.CONSISTENCY_LENGTH_RESULT__RESULT:
				return result != RESULT_EDEFAULT;
			case ReportPackage.CONSISTENCY_LENGTH_RESULT__REPORT:
				return basicGetReport() != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (traceLength: ");
		result.append(traceLength);
		result.append(", result: ");
		result.append(result);
		result.append(')');
		return result.toString();
	}

} //ConsistencyLengthResultImpl
