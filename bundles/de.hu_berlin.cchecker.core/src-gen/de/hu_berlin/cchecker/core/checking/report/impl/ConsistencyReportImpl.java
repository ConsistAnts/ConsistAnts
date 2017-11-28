/**
 */
package de.hu_berlin.cchecker.core.checking.report.impl;

import de.hu_berlin.cchecker.core.checking.report.ConsistencyLengthResult;
import de.hu_berlin.cchecker.core.checking.report.ConsistencyReport;
import de.hu_berlin.cchecker.core.checking.report.CounterExample;
import de.hu_berlin.cchecker.core.checking.report.ReportPackage;

import de.hu_berlin.cchecker.core.models.pdfa.PdfaPackage;

import de.hu_berlin.cchecker.core.models.pdfa.impl.TransitionLabelPairImpl;

import java.util.Collection;
import java.util.Date;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Consistency Report</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.hu_berlin.cchecker.core.checking.report.impl.ConsistencyReportImpl#getCreatedOn <em>Created On</em>}</li>
 *   <li>{@link de.hu_berlin.cchecker.core.checking.report.impl.ConsistencyReportImpl#getModelPath <em>Model Path</em>}</li>
 *   <li>{@link de.hu_berlin.cchecker.core.checking.report.impl.ConsistencyReportImpl#getModelChecksum <em>Model Checksum</em>}</li>
 *   <li>{@link de.hu_berlin.cchecker.core.checking.report.impl.ConsistencyReportImpl#getTraceDataSetPath <em>Trace Data Set Path</em>}</li>
 *   <li>{@link de.hu_berlin.cchecker.core.checking.report.impl.ConsistencyReportImpl#getTraceDataSetChecksum <em>Trace Data Set Checksum</em>}</li>
 *   <li>{@link de.hu_berlin.cchecker.core.checking.report.impl.ConsistencyReportImpl#getLabelMapping <em>Label Mapping</em>}</li>
 *   <li>{@link de.hu_berlin.cchecker.core.checking.report.impl.ConsistencyReportImpl#getFootprintMatrices <em>Footprint Matrices</em>}</li>
 *   <li>{@link de.hu_berlin.cchecker.core.checking.report.impl.ConsistencyReportImpl#getCounterExamples <em>Counter Examples</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConsistencyReportImpl extends MinimalEObjectImpl.Container implements ConsistencyReport {
	/**
	 * The default value of the '{@link #getCreatedOn() <em>Created On</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreatedOn()
	 * @generated
	 * @ordered
	 */
	protected static final Date CREATED_ON_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCreatedOn() <em>Created On</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreatedOn()
	 * @generated
	 * @ordered
	 */
	protected Date createdOn = CREATED_ON_EDEFAULT;

	/**
	 * The default value of the '{@link #getModelPath() <em>Model Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModelPath()
	 * @generated
	 * @ordered
	 */
	protected static final String MODEL_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getModelPath() <em>Model Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModelPath()
	 * @generated
	 * @ordered
	 */
	protected String modelPath = MODEL_PATH_EDEFAULT;

	/**
	 * The default value of the '{@link #getModelChecksum() <em>Model Checksum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModelChecksum()
	 * @generated
	 * @ordered
	 */
	protected static final String MODEL_CHECKSUM_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getModelChecksum() <em>Model Checksum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModelChecksum()
	 * @generated
	 * @ordered
	 */
	protected String modelChecksum = MODEL_CHECKSUM_EDEFAULT;

	/**
	 * The default value of the '{@link #getTraceDataSetPath() <em>Trace Data Set Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTraceDataSetPath()
	 * @generated
	 * @ordered
	 */
	protected static final String TRACE_DATA_SET_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTraceDataSetPath() <em>Trace Data Set Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTraceDataSetPath()
	 * @generated
	 * @ordered
	 */
	protected String traceDataSetPath = TRACE_DATA_SET_PATH_EDEFAULT;

	/**
	 * The default value of the '{@link #getTraceDataSetChecksum() <em>Trace Data Set Checksum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTraceDataSetChecksum()
	 * @generated
	 * @ordered
	 */
	protected static final String TRACE_DATA_SET_CHECKSUM_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTraceDataSetChecksum() <em>Trace Data Set Checksum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTraceDataSetChecksum()
	 * @generated
	 * @ordered
	 */
	protected String traceDataSetChecksum = TRACE_DATA_SET_CHECKSUM_EDEFAULT;

	/**
	 * The cached value of the '{@link #getLabelMapping() <em>Label Mapping</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabelMapping()
	 * @generated
	 * @ordered
	 */
	protected EMap<Integer, String> labelMapping;

	/**
	 * The cached value of the '{@link #getFootprintMatrices() <em>Footprint Matrices</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFootprintMatrices()
	 * @generated
	 * @ordered
	 */
	protected EList<ConsistencyLengthResult> footprintMatrices;

	/**
	 * The cached value of the '{@link #getCounterExamples() <em>Counter Examples</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCounterExamples()
	 * @generated
	 * @ordered
	 */
	protected EList<CounterExample> counterExamples;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConsistencyReportImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ReportPackage.Literals.CONSISTENCY_REPORT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getCreatedOn() {
		return createdOn;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCreatedOn(Date newCreatedOn) {
		Date oldCreatedOn = createdOn;
		createdOn = newCreatedOn;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ReportPackage.CONSISTENCY_REPORT__CREATED_ON, oldCreatedOn, createdOn));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getModelPath() {
		return modelPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setModelPath(String newModelPath) {
		String oldModelPath = modelPath;
		modelPath = newModelPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ReportPackage.CONSISTENCY_REPORT__MODEL_PATH, oldModelPath, modelPath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getModelChecksum() {
		return modelChecksum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setModelChecksum(String newModelChecksum) {
		String oldModelChecksum = modelChecksum;
		modelChecksum = newModelChecksum;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ReportPackage.CONSISTENCY_REPORT__MODEL_CHECKSUM, oldModelChecksum, modelChecksum));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTraceDataSetPath() {
		return traceDataSetPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTraceDataSetPath(String newTraceDataSetPath) {
		String oldTraceDataSetPath = traceDataSetPath;
		traceDataSetPath = newTraceDataSetPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ReportPackage.CONSISTENCY_REPORT__TRACE_DATA_SET_PATH, oldTraceDataSetPath, traceDataSetPath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTraceDataSetChecksum() {
		return traceDataSetChecksum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTraceDataSetChecksum(String newTraceDataSetChecksum) {
		String oldTraceDataSetChecksum = traceDataSetChecksum;
		traceDataSetChecksum = newTraceDataSetChecksum;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ReportPackage.CONSISTENCY_REPORT__TRACE_DATA_SET_CHECKSUM, oldTraceDataSetChecksum, traceDataSetChecksum));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<Integer, String> getLabelMapping() {
		if (labelMapping == null) {
			labelMapping = new EcoreEMap<Integer,String>(PdfaPackage.Literals.TRANSITION_LABEL_PAIR, TransitionLabelPairImpl.class, this, ReportPackage.CONSISTENCY_REPORT__LABEL_MAPPING);
		}
		return labelMapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ConsistencyLengthResult> getFootprintMatrices() {
		if (footprintMatrices == null) {
			footprintMatrices = new EObjectContainmentWithInverseEList<ConsistencyLengthResult>(ConsistencyLengthResult.class, this, ReportPackage.CONSISTENCY_REPORT__FOOTPRINT_MATRICES, ReportPackage.CONSISTENCY_LENGTH_RESULT__REPORT);
		}
		return footprintMatrices;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<CounterExample> getCounterExamples() {
		if (counterExamples == null) {
			counterExamples = new EObjectContainmentWithInverseEList<CounterExample>(CounterExample.class, this, ReportPackage.CONSISTENCY_REPORT__COUNTER_EXAMPLES, ReportPackage.COUNTER_EXAMPLE__REPORT);
		}
		return counterExamples;
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
			case ReportPackage.CONSISTENCY_REPORT__FOOTPRINT_MATRICES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getFootprintMatrices()).basicAdd(otherEnd, msgs);
			case ReportPackage.CONSISTENCY_REPORT__COUNTER_EXAMPLES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getCounterExamples()).basicAdd(otherEnd, msgs);
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
			case ReportPackage.CONSISTENCY_REPORT__LABEL_MAPPING:
				return ((InternalEList<?>)getLabelMapping()).basicRemove(otherEnd, msgs);
			case ReportPackage.CONSISTENCY_REPORT__FOOTPRINT_MATRICES:
				return ((InternalEList<?>)getFootprintMatrices()).basicRemove(otherEnd, msgs);
			case ReportPackage.CONSISTENCY_REPORT__COUNTER_EXAMPLES:
				return ((InternalEList<?>)getCounterExamples()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ReportPackage.CONSISTENCY_REPORT__CREATED_ON:
				return getCreatedOn();
			case ReportPackage.CONSISTENCY_REPORT__MODEL_PATH:
				return getModelPath();
			case ReportPackage.CONSISTENCY_REPORT__MODEL_CHECKSUM:
				return getModelChecksum();
			case ReportPackage.CONSISTENCY_REPORT__TRACE_DATA_SET_PATH:
				return getTraceDataSetPath();
			case ReportPackage.CONSISTENCY_REPORT__TRACE_DATA_SET_CHECKSUM:
				return getTraceDataSetChecksum();
			case ReportPackage.CONSISTENCY_REPORT__LABEL_MAPPING:
				if (coreType) return getLabelMapping();
				else return getLabelMapping().map();
			case ReportPackage.CONSISTENCY_REPORT__FOOTPRINT_MATRICES:
				return getFootprintMatrices();
			case ReportPackage.CONSISTENCY_REPORT__COUNTER_EXAMPLES:
				return getCounterExamples();
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
			case ReportPackage.CONSISTENCY_REPORT__CREATED_ON:
				setCreatedOn((Date)newValue);
				return;
			case ReportPackage.CONSISTENCY_REPORT__MODEL_PATH:
				setModelPath((String)newValue);
				return;
			case ReportPackage.CONSISTENCY_REPORT__MODEL_CHECKSUM:
				setModelChecksum((String)newValue);
				return;
			case ReportPackage.CONSISTENCY_REPORT__TRACE_DATA_SET_PATH:
				setTraceDataSetPath((String)newValue);
				return;
			case ReportPackage.CONSISTENCY_REPORT__TRACE_DATA_SET_CHECKSUM:
				setTraceDataSetChecksum((String)newValue);
				return;
			case ReportPackage.CONSISTENCY_REPORT__LABEL_MAPPING:
				((EStructuralFeature.Setting)getLabelMapping()).set(newValue);
				return;
			case ReportPackage.CONSISTENCY_REPORT__FOOTPRINT_MATRICES:
				getFootprintMatrices().clear();
				getFootprintMatrices().addAll((Collection<? extends ConsistencyLengthResult>)newValue);
				return;
			case ReportPackage.CONSISTENCY_REPORT__COUNTER_EXAMPLES:
				getCounterExamples().clear();
				getCounterExamples().addAll((Collection<? extends CounterExample>)newValue);
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
			case ReportPackage.CONSISTENCY_REPORT__CREATED_ON:
				setCreatedOn(CREATED_ON_EDEFAULT);
				return;
			case ReportPackage.CONSISTENCY_REPORT__MODEL_PATH:
				setModelPath(MODEL_PATH_EDEFAULT);
				return;
			case ReportPackage.CONSISTENCY_REPORT__MODEL_CHECKSUM:
				setModelChecksum(MODEL_CHECKSUM_EDEFAULT);
				return;
			case ReportPackage.CONSISTENCY_REPORT__TRACE_DATA_SET_PATH:
				setTraceDataSetPath(TRACE_DATA_SET_PATH_EDEFAULT);
				return;
			case ReportPackage.CONSISTENCY_REPORT__TRACE_DATA_SET_CHECKSUM:
				setTraceDataSetChecksum(TRACE_DATA_SET_CHECKSUM_EDEFAULT);
				return;
			case ReportPackage.CONSISTENCY_REPORT__LABEL_MAPPING:
				getLabelMapping().clear();
				return;
			case ReportPackage.CONSISTENCY_REPORT__FOOTPRINT_MATRICES:
				getFootprintMatrices().clear();
				return;
			case ReportPackage.CONSISTENCY_REPORT__COUNTER_EXAMPLES:
				getCounterExamples().clear();
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
			case ReportPackage.CONSISTENCY_REPORT__CREATED_ON:
				return CREATED_ON_EDEFAULT == null ? createdOn != null : !CREATED_ON_EDEFAULT.equals(createdOn);
			case ReportPackage.CONSISTENCY_REPORT__MODEL_PATH:
				return MODEL_PATH_EDEFAULT == null ? modelPath != null : !MODEL_PATH_EDEFAULT.equals(modelPath);
			case ReportPackage.CONSISTENCY_REPORT__MODEL_CHECKSUM:
				return MODEL_CHECKSUM_EDEFAULT == null ? modelChecksum != null : !MODEL_CHECKSUM_EDEFAULT.equals(modelChecksum);
			case ReportPackage.CONSISTENCY_REPORT__TRACE_DATA_SET_PATH:
				return TRACE_DATA_SET_PATH_EDEFAULT == null ? traceDataSetPath != null : !TRACE_DATA_SET_PATH_EDEFAULT.equals(traceDataSetPath);
			case ReportPackage.CONSISTENCY_REPORT__TRACE_DATA_SET_CHECKSUM:
				return TRACE_DATA_SET_CHECKSUM_EDEFAULT == null ? traceDataSetChecksum != null : !TRACE_DATA_SET_CHECKSUM_EDEFAULT.equals(traceDataSetChecksum);
			case ReportPackage.CONSISTENCY_REPORT__LABEL_MAPPING:
				return labelMapping != null && !labelMapping.isEmpty();
			case ReportPackage.CONSISTENCY_REPORT__FOOTPRINT_MATRICES:
				return footprintMatrices != null && !footprintMatrices.isEmpty();
			case ReportPackage.CONSISTENCY_REPORT__COUNTER_EXAMPLES:
				return counterExamples != null && !counterExamples.isEmpty();
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
		result.append(" (createdOn: ");
		result.append(createdOn);
		result.append(", modelPath: ");
		result.append(modelPath);
		result.append(", modelChecksum: ");
		result.append(modelChecksum);
		result.append(", traceDataSetPath: ");
		result.append(traceDataSetPath);
		result.append(", traceDataSetChecksum: ");
		result.append(traceDataSetChecksum);
		result.append(')');
		return result.toString();
	}

} //ConsistencyReportImpl
