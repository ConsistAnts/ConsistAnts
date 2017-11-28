/**
 */
package de.hu_berlin.cchecker.core.checking.report.impl;

import de.hu_berlin.cchecker.core.checking.report.ConsistencyReport;
import de.hu_berlin.cchecker.core.checking.report.CounterExample;
import de.hu_berlin.cchecker.core.checking.report.ReportPackage;

import java.lang.reflect.InvocationTargetException;

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
 * An implementation of the model object '<em><b>Counter Example</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.hu_berlin.cchecker.core.checking.report.impl.CounterExampleImpl#getTraceIds <em>Trace Ids</em>}</li>
 *   <li>{@link de.hu_berlin.cchecker.core.checking.report.impl.CounterExampleImpl#getTrace <em>Trace</em>}</li>
 *   <li>{@link de.hu_berlin.cchecker.core.checking.report.impl.CounterExampleImpl#getIndex <em>Index</em>}</li>
 *   <li>{@link de.hu_berlin.cchecker.core.checking.report.impl.CounterExampleImpl#getReport <em>Report</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CounterExampleImpl extends MinimalEObjectImpl.Container implements CounterExample {
	/**
	 * The cached value of the '{@link #getTraceIds() <em>Trace Ids</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTraceIds()
	 * @generated
	 * @ordered
	 */
	protected EList<String> traceIds;

	/**
	 * The cached value of the '{@link #getTrace() <em>Trace</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTrace()
	 * @generated
	 * @ordered
	 */
	protected EList<Integer> trace;

	/**
	 * The default value of the '{@link #getIndex() <em>Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIndex()
	 * @generated
	 * @ordered
	 */
	protected static final int INDEX_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getIndex() <em>Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIndex()
	 * @generated
	 * @ordered
	 */
	protected int index = INDEX_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CounterExampleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ReportPackage.Literals.COUNTER_EXAMPLE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getTraceIds() {
		if (traceIds == null) {
			traceIds = new EDataTypeEList<String>(String.class, this, ReportPackage.COUNTER_EXAMPLE__TRACE_IDS);
		}
		return traceIds;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Integer> getTrace() {
		if (trace == null) {
			trace = new EDataTypeEList<Integer>(Integer.class, this, ReportPackage.COUNTER_EXAMPLE__TRACE);
		}
		return trace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIndex(int newIndex) {
		int oldIndex = index;
		index = newIndex;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ReportPackage.COUNTER_EXAMPLE__INDEX, oldIndex, index));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConsistencyReport getReport() {
		if (eContainerFeatureID() != ReportPackage.COUNTER_EXAMPLE__REPORT) return null;
		return (ConsistencyReport)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConsistencyReport basicGetReport() {
		if (eContainerFeatureID() != ReportPackage.COUNTER_EXAMPLE__REPORT) return null;
		return (ConsistencyReport)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetReport(ConsistencyReport newReport, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newReport, ReportPackage.COUNTER_EXAMPLE__REPORT, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReport(ConsistencyReport newReport) {
		if (newReport != eInternalContainer() || (eContainerFeatureID() != ReportPackage.COUNTER_EXAMPLE__REPORT && newReport != null)) {
			if (EcoreUtil.isAncestor(this, newReport))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newReport != null)
				msgs = ((InternalEObject)newReport).eInverseAdd(this, ReportPackage.CONSISTENCY_REPORT__COUNTER_EXAMPLES, ConsistencyReport.class, msgs);
			msgs = basicSetReport(newReport, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ReportPackage.COUNTER_EXAMPLE__REPORT, newReport, newReport));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		String _string = this.getTraceIds().toString();
		String _plus = ("CounterExample with IDs" + _string);
		String _plus_1 = (_plus + " for trace ");
		String _string_1 = this.getTrace().toString();
		String _plus_2 = (_plus_1 + _string_1);
		String _plus_3 = (_plus_2 + " at index ");
		int _index = this.getIndex();
		return (_plus_3 + Integer.valueOf(_index));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ReportPackage.COUNTER_EXAMPLE__REPORT:
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
			case ReportPackage.COUNTER_EXAMPLE__REPORT:
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
			case ReportPackage.COUNTER_EXAMPLE__REPORT:
				return eInternalContainer().eInverseRemove(this, ReportPackage.CONSISTENCY_REPORT__COUNTER_EXAMPLES, ConsistencyReport.class, msgs);
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
			case ReportPackage.COUNTER_EXAMPLE__TRACE_IDS:
				return getTraceIds();
			case ReportPackage.COUNTER_EXAMPLE__TRACE:
				return getTrace();
			case ReportPackage.COUNTER_EXAMPLE__INDEX:
				return getIndex();
			case ReportPackage.COUNTER_EXAMPLE__REPORT:
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
			case ReportPackage.COUNTER_EXAMPLE__TRACE_IDS:
				getTraceIds().clear();
				getTraceIds().addAll((Collection<? extends String>)newValue);
				return;
			case ReportPackage.COUNTER_EXAMPLE__TRACE:
				getTrace().clear();
				getTrace().addAll((Collection<? extends Integer>)newValue);
				return;
			case ReportPackage.COUNTER_EXAMPLE__INDEX:
				setIndex((Integer)newValue);
				return;
			case ReportPackage.COUNTER_EXAMPLE__REPORT:
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
			case ReportPackage.COUNTER_EXAMPLE__TRACE_IDS:
				getTraceIds().clear();
				return;
			case ReportPackage.COUNTER_EXAMPLE__TRACE:
				getTrace().clear();
				return;
			case ReportPackage.COUNTER_EXAMPLE__INDEX:
				setIndex(INDEX_EDEFAULT);
				return;
			case ReportPackage.COUNTER_EXAMPLE__REPORT:
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
			case ReportPackage.COUNTER_EXAMPLE__TRACE_IDS:
				return traceIds != null && !traceIds.isEmpty();
			case ReportPackage.COUNTER_EXAMPLE__TRACE:
				return trace != null && !trace.isEmpty();
			case ReportPackage.COUNTER_EXAMPLE__INDEX:
				return index != INDEX_EDEFAULT;
			case ReportPackage.COUNTER_EXAMPLE__REPORT:
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
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case ReportPackage.COUNTER_EXAMPLE___TO_STRING:
				return toString();
		}
		return super.eInvoke(operationID, arguments);
	}

} //CounterExampleImpl
