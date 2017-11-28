/**
 */
package de.hu_berlin.cchecker.core.checking.report.impl;

import de.hu_berlin.cchecker.core.checking.report.ConsistencyLengthResult;
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

import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Matrix Row</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.hu_berlin.cchecker.core.checking.report.impl.MatrixRowImpl#getElements <em>Elements</em>}</li>
 *   <li>{@link de.hu_berlin.cchecker.core.checking.report.impl.MatrixRowImpl#getResult <em>Result</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MatrixRowImpl extends MinimalEObjectImpl.Container implements MatrixRow {
	/**
	 * The cached value of the '{@link #getElements() <em>Elements</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElements()
	 * @generated
	 * @ordered
	 */
	protected EList<Double> elements;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MatrixRowImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ReportPackage.Literals.MATRIX_ROW;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Double> getElements() {
		if (elements == null) {
			elements = new EDataTypeEList<Double>(Double.class, this, ReportPackage.MATRIX_ROW__ELEMENTS);
		}
		return elements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConsistencyLengthResult getResult() {
		if (eContainerFeatureID() != ReportPackage.MATRIX_ROW__RESULT) return null;
		return (ConsistencyLengthResult)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConsistencyLengthResult basicGetResult() {
		if (eContainerFeatureID() != ReportPackage.MATRIX_ROW__RESULT) return null;
		return (ConsistencyLengthResult)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetResult(ConsistencyLengthResult newResult, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newResult, ReportPackage.MATRIX_ROW__RESULT, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setResult(ConsistencyLengthResult newResult) {
		if (newResult != eInternalContainer() || (eContainerFeatureID() != ReportPackage.MATRIX_ROW__RESULT && newResult != null)) {
			if (EcoreUtil.isAncestor(this, newResult))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newResult != null)
				msgs = ((InternalEObject)newResult).eInverseAdd(this, ReportPackage.CONSISTENCY_LENGTH_RESULT__RESULT_MATRIX, ConsistencyLengthResult.class, msgs);
			msgs = basicSetResult(newResult, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ReportPackage.MATRIX_ROW__RESULT, newResult, newResult));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ReportPackage.MATRIX_ROW__RESULT:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetResult((ConsistencyLengthResult)otherEnd, msgs);
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
			case ReportPackage.MATRIX_ROW__RESULT:
				return basicSetResult(null, msgs);
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
			case ReportPackage.MATRIX_ROW__RESULT:
				return eInternalContainer().eInverseRemove(this, ReportPackage.CONSISTENCY_LENGTH_RESULT__RESULT_MATRIX, ConsistencyLengthResult.class, msgs);
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
			case ReportPackage.MATRIX_ROW__ELEMENTS:
				return getElements();
			case ReportPackage.MATRIX_ROW__RESULT:
				if (resolve) return getResult();
				return basicGetResult();
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
			case ReportPackage.MATRIX_ROW__ELEMENTS:
				getElements().clear();
				getElements().addAll((Collection<? extends Double>)newValue);
				return;
			case ReportPackage.MATRIX_ROW__RESULT:
				setResult((ConsistencyLengthResult)newValue);
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
			case ReportPackage.MATRIX_ROW__ELEMENTS:
				getElements().clear();
				return;
			case ReportPackage.MATRIX_ROW__RESULT:
				setResult((ConsistencyLengthResult)null);
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
			case ReportPackage.MATRIX_ROW__ELEMENTS:
				return elements != null && !elements.isEmpty();
			case ReportPackage.MATRIX_ROW__RESULT:
				return basicGetResult() != null;
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
		result.append(" (elements: ");
		result.append(elements);
		result.append(')');
		return result.toString();
	}

} //MatrixRowImpl
