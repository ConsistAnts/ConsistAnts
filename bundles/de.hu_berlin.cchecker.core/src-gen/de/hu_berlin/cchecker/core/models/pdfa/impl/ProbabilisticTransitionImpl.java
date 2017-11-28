/**
 */
package de.hu_berlin.cchecker.core.models.pdfa.impl;

import de.hu_berlin.cchecker.core.models.pdfa.PdfaPackage;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticState;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticTransition;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Probabilistic Transition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.hu_berlin.cchecker.core.models.pdfa.impl.ProbabilisticTransitionImpl#getTransitionId <em>Transition Id</em>}</li>
 *   <li>{@link de.hu_berlin.cchecker.core.models.pdfa.impl.ProbabilisticTransitionImpl#getProbability <em>Probability</em>}</li>
 *   <li>{@link de.hu_berlin.cchecker.core.models.pdfa.impl.ProbabilisticTransitionImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link de.hu_berlin.cchecker.core.models.pdfa.impl.ProbabilisticTransitionImpl#getSource <em>Source</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProbabilisticTransitionImpl extends MinimalEObjectImpl.Container implements ProbabilisticTransition {
	/**
	 * The default value of the '{@link #getTransitionId() <em>Transition Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransitionId()
	 * @generated
	 * @ordered
	 */
	protected static final int TRANSITION_ID_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getTransitionId() <em>Transition Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransitionId()
	 * @generated
	 * @ordered
	 */
	protected int transitionId = TRANSITION_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getProbability() <em>Probability</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProbability()
	 * @generated
	 * @ordered
	 */
	protected static final double PROBABILITY_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getProbability() <em>Probability</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProbability()
	 * @generated
	 * @ordered
	 */
	protected double probability = PROBABILITY_EDEFAULT;

	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected ProbabilisticState target;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProbabilisticTransitionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PdfaPackage.Literals.PROBABILISTIC_TRANSITION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getTransitionId() {
		return transitionId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTransitionId(int newTransitionId) {
		int oldTransitionId = transitionId;
		transitionId = newTransitionId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PdfaPackage.PROBABILISTIC_TRANSITION__TRANSITION_ID, oldTransitionId, transitionId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getProbability() {
		return probability;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProbability(double newProbability) {
		double oldProbability = probability;
		probability = newProbability;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PdfaPackage.PROBABILISTIC_TRANSITION__PROBABILITY, oldProbability, probability));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProbabilisticState getTarget() {
		if (target != null && target.eIsProxy()) {
			InternalEObject oldTarget = (InternalEObject)target;
			target = (ProbabilisticState)eResolveProxy(oldTarget);
			if (target != oldTarget) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, PdfaPackage.PROBABILISTIC_TRANSITION__TARGET, oldTarget, target));
			}
		}
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProbabilisticState basicGetTarget() {
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTarget(ProbabilisticState newTarget) {
		ProbabilisticState oldTarget = target;
		target = newTarget;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PdfaPackage.PROBABILISTIC_TRANSITION__TARGET, oldTarget, target));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProbabilisticState getSource() {
		if (eContainerFeatureID() != PdfaPackage.PROBABILISTIC_TRANSITION__SOURCE) return null;
		return (ProbabilisticState)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProbabilisticState basicGetSource() {
		if (eContainerFeatureID() != PdfaPackage.PROBABILISTIC_TRANSITION__SOURCE) return null;
		return (ProbabilisticState)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSource(ProbabilisticState newSource, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newSource, PdfaPackage.PROBABILISTIC_TRANSITION__SOURCE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSource(ProbabilisticState newSource) {
		if (newSource != eInternalContainer() || (eContainerFeatureID() != PdfaPackage.PROBABILISTIC_TRANSITION__SOURCE && newSource != null)) {
			if (EcoreUtil.isAncestor(this, newSource))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newSource != null)
				msgs = ((InternalEObject)newSource).eInverseAdd(this, PdfaPackage.PROBABILISTIC_STATE__OUTGOING_TRANSITIONS, ProbabilisticState.class, msgs);
			msgs = basicSetSource(newSource, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PdfaPackage.PROBABILISTIC_TRANSITION__SOURCE, newSource, newSource));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PdfaPackage.PROBABILISTIC_TRANSITION__SOURCE:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetSource((ProbabilisticState)otherEnd, msgs);
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
			case PdfaPackage.PROBABILISTIC_TRANSITION__SOURCE:
				return basicSetSource(null, msgs);
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
			case PdfaPackage.PROBABILISTIC_TRANSITION__SOURCE:
				return eInternalContainer().eInverseRemove(this, PdfaPackage.PROBABILISTIC_STATE__OUTGOING_TRANSITIONS, ProbabilisticState.class, msgs);
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
			case PdfaPackage.PROBABILISTIC_TRANSITION__TRANSITION_ID:
				return getTransitionId();
			case PdfaPackage.PROBABILISTIC_TRANSITION__PROBABILITY:
				return getProbability();
			case PdfaPackage.PROBABILISTIC_TRANSITION__TARGET:
				if (resolve) return getTarget();
				return basicGetTarget();
			case PdfaPackage.PROBABILISTIC_TRANSITION__SOURCE:
				if (resolve) return getSource();
				return basicGetSource();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case PdfaPackage.PROBABILISTIC_TRANSITION__TRANSITION_ID:
				setTransitionId((Integer)newValue);
				return;
			case PdfaPackage.PROBABILISTIC_TRANSITION__PROBABILITY:
				setProbability((Double)newValue);
				return;
			case PdfaPackage.PROBABILISTIC_TRANSITION__TARGET:
				setTarget((ProbabilisticState)newValue);
				return;
			case PdfaPackage.PROBABILISTIC_TRANSITION__SOURCE:
				setSource((ProbabilisticState)newValue);
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
			case PdfaPackage.PROBABILISTIC_TRANSITION__TRANSITION_ID:
				setTransitionId(TRANSITION_ID_EDEFAULT);
				return;
			case PdfaPackage.PROBABILISTIC_TRANSITION__PROBABILITY:
				setProbability(PROBABILITY_EDEFAULT);
				return;
			case PdfaPackage.PROBABILISTIC_TRANSITION__TARGET:
				setTarget((ProbabilisticState)null);
				return;
			case PdfaPackage.PROBABILISTIC_TRANSITION__SOURCE:
				setSource((ProbabilisticState)null);
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
			case PdfaPackage.PROBABILISTIC_TRANSITION__TRANSITION_ID:
				return transitionId != TRANSITION_ID_EDEFAULT;
			case PdfaPackage.PROBABILISTIC_TRANSITION__PROBABILITY:
				return probability != PROBABILITY_EDEFAULT;
			case PdfaPackage.PROBABILISTIC_TRANSITION__TARGET:
				return target != null;
			case PdfaPackage.PROBABILISTIC_TRANSITION__SOURCE:
				return basicGetSource() != null;
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
		result.append(" (transitionId: ");
		result.append(transitionId);
		result.append(", probability: ");
		result.append(probability);
		result.append(')');
		return result.toString();
	}

} //ProbabilisticTransitionImpl
