/**
 */
package de.hu_berlin.cchecker.core.models.pdfa.impl;

import de.hu_berlin.cchecker.core.models.pdfa.PdfaPackage;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticState;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticTransition;

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
 * An implementation of the model object '<em><b>Probabilistic State</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.hu_berlin.cchecker.core.models.pdfa.impl.ProbabilisticStateImpl#getStateId <em>State Id</em>}</li>
 *   <li>{@link de.hu_berlin.cchecker.core.models.pdfa.impl.ProbabilisticStateImpl#getOutgoingTransitions <em>Outgoing Transitions</em>}</li>
 *   <li>{@link de.hu_berlin.cchecker.core.models.pdfa.impl.ProbabilisticStateImpl#isTerminating <em>Terminating</em>}</li>
 *   <li>{@link de.hu_berlin.cchecker.core.models.pdfa.impl.ProbabilisticStateImpl#getTerminatingProbability <em>Terminating Probability</em>}</li>
 *   <li>{@link de.hu_berlin.cchecker.core.models.pdfa.impl.ProbabilisticStateImpl#getAutomata <em>Automata</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProbabilisticStateImpl extends MinimalEObjectImpl.Container implements ProbabilisticState {
	/**
	 * The default value of the '{@link #getStateId() <em>State Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStateId()
	 * @generated
	 * @ordered
	 */
	protected static final int STATE_ID_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getStateId() <em>State Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStateId()
	 * @generated
	 * @ordered
	 */
	protected int stateId = STATE_ID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getOutgoingTransitions() <em>Outgoing Transitions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutgoingTransitions()
	 * @generated
	 * @ordered
	 */
	protected EList<ProbabilisticTransition> outgoingTransitions;

	/**
	 * The default value of the '{@link #isTerminating() <em>Terminating</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTerminating()
	 * @generated
	 * @ordered
	 */
	protected static final boolean TERMINATING_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isTerminating() <em>Terminating</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTerminating()
	 * @generated
	 * @ordered
	 */
	protected boolean terminating = TERMINATING_EDEFAULT;

	/**
	 * The default value of the '{@link #getTerminatingProbability() <em>Terminating Probability</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTerminatingProbability()
	 * @generated
	 * @ordered
	 */
	protected static final double TERMINATING_PROBABILITY_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getTerminatingProbability() <em>Terminating Probability</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTerminatingProbability()
	 * @generated
	 * @ordered
	 */
	protected double terminatingProbability = TERMINATING_PROBABILITY_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProbabilisticStateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PdfaPackage.Literals.PROBABILISTIC_STATE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getStateId() {
		return stateId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStateId(int newStateId) {
		int oldStateId = stateId;
		stateId = newStateId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PdfaPackage.PROBABILISTIC_STATE__STATE_ID, oldStateId, stateId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ProbabilisticTransition> getOutgoingTransitions() {
		if (outgoingTransitions == null) {
			outgoingTransitions = new EObjectContainmentWithInverseEList<ProbabilisticTransition>(ProbabilisticTransition.class, this, PdfaPackage.PROBABILISTIC_STATE__OUTGOING_TRANSITIONS, PdfaPackage.PROBABILISTIC_TRANSITION__SOURCE);
		}
		return outgoingTransitions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isTerminating() {
		return terminating;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTerminating(boolean newTerminating) {
		boolean oldTerminating = terminating;
		terminating = newTerminating;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PdfaPackage.PROBABILISTIC_STATE__TERMINATING, oldTerminating, terminating));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getTerminatingProbability() {
		return terminatingProbability;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTerminatingProbability(double newTerminatingProbability) {
		double oldTerminatingProbability = terminatingProbability;
		terminatingProbability = newTerminatingProbability;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PdfaPackage.PROBABILISTIC_STATE__TERMINATING_PROBABILITY, oldTerminatingProbability, terminatingProbability));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProbabilisticAutomata getAutomata() {
		if (eContainerFeatureID() != PdfaPackage.PROBABILISTIC_STATE__AUTOMATA) return null;
		return (ProbabilisticAutomata)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProbabilisticAutomata basicGetAutomata() {
		if (eContainerFeatureID() != PdfaPackage.PROBABILISTIC_STATE__AUTOMATA) return null;
		return (ProbabilisticAutomata)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAutomata(ProbabilisticAutomata newAutomata, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newAutomata, PdfaPackage.PROBABILISTIC_STATE__AUTOMATA, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAutomata(ProbabilisticAutomata newAutomata) {
		if (newAutomata != eInternalContainer() || (eContainerFeatureID() != PdfaPackage.PROBABILISTIC_STATE__AUTOMATA && newAutomata != null)) {
			if (EcoreUtil.isAncestor(this, newAutomata))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newAutomata != null)
				msgs = ((InternalEObject)newAutomata).eInverseAdd(this, PdfaPackage.PROBABILISTIC_AUTOMATA__STATES, ProbabilisticAutomata.class, msgs);
			msgs = basicSetAutomata(newAutomata, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PdfaPackage.PROBABILISTIC_STATE__AUTOMATA, newAutomata, newAutomata));
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
			case PdfaPackage.PROBABILISTIC_STATE__OUTGOING_TRANSITIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getOutgoingTransitions()).basicAdd(otherEnd, msgs);
			case PdfaPackage.PROBABILISTIC_STATE__AUTOMATA:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetAutomata((ProbabilisticAutomata)otherEnd, msgs);
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
			case PdfaPackage.PROBABILISTIC_STATE__OUTGOING_TRANSITIONS:
				return ((InternalEList<?>)getOutgoingTransitions()).basicRemove(otherEnd, msgs);
			case PdfaPackage.PROBABILISTIC_STATE__AUTOMATA:
				return basicSetAutomata(null, msgs);
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
			case PdfaPackage.PROBABILISTIC_STATE__AUTOMATA:
				return eInternalContainer().eInverseRemove(this, PdfaPackage.PROBABILISTIC_AUTOMATA__STATES, ProbabilisticAutomata.class, msgs);
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
			case PdfaPackage.PROBABILISTIC_STATE__STATE_ID:
				return getStateId();
			case PdfaPackage.PROBABILISTIC_STATE__OUTGOING_TRANSITIONS:
				return getOutgoingTransitions();
			case PdfaPackage.PROBABILISTIC_STATE__TERMINATING:
				return isTerminating();
			case PdfaPackage.PROBABILISTIC_STATE__TERMINATING_PROBABILITY:
				return getTerminatingProbability();
			case PdfaPackage.PROBABILISTIC_STATE__AUTOMATA:
				if (resolve) return getAutomata();
				return basicGetAutomata();
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
			case PdfaPackage.PROBABILISTIC_STATE__STATE_ID:
				setStateId((Integer)newValue);
				return;
			case PdfaPackage.PROBABILISTIC_STATE__OUTGOING_TRANSITIONS:
				getOutgoingTransitions().clear();
				getOutgoingTransitions().addAll((Collection<? extends ProbabilisticTransition>)newValue);
				return;
			case PdfaPackage.PROBABILISTIC_STATE__TERMINATING:
				setTerminating((Boolean)newValue);
				return;
			case PdfaPackage.PROBABILISTIC_STATE__TERMINATING_PROBABILITY:
				setTerminatingProbability((Double)newValue);
				return;
			case PdfaPackage.PROBABILISTIC_STATE__AUTOMATA:
				setAutomata((ProbabilisticAutomata)newValue);
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
			case PdfaPackage.PROBABILISTIC_STATE__STATE_ID:
				setStateId(STATE_ID_EDEFAULT);
				return;
			case PdfaPackage.PROBABILISTIC_STATE__OUTGOING_TRANSITIONS:
				getOutgoingTransitions().clear();
				return;
			case PdfaPackage.PROBABILISTIC_STATE__TERMINATING:
				setTerminating(TERMINATING_EDEFAULT);
				return;
			case PdfaPackage.PROBABILISTIC_STATE__TERMINATING_PROBABILITY:
				setTerminatingProbability(TERMINATING_PROBABILITY_EDEFAULT);
				return;
			case PdfaPackage.PROBABILISTIC_STATE__AUTOMATA:
				setAutomata((ProbabilisticAutomata)null);
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
			case PdfaPackage.PROBABILISTIC_STATE__STATE_ID:
				return stateId != STATE_ID_EDEFAULT;
			case PdfaPackage.PROBABILISTIC_STATE__OUTGOING_TRANSITIONS:
				return outgoingTransitions != null && !outgoingTransitions.isEmpty();
			case PdfaPackage.PROBABILISTIC_STATE__TERMINATING:
				return terminating != TERMINATING_EDEFAULT;
			case PdfaPackage.PROBABILISTIC_STATE__TERMINATING_PROBABILITY:
				return terminatingProbability != TERMINATING_PROBABILITY_EDEFAULT;
			case PdfaPackage.PROBABILISTIC_STATE__AUTOMATA:
				return basicGetAutomata() != null;
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
		result.append(" (stateId: ");
		result.append(stateId);
		result.append(", terminating: ");
		result.append(terminating);
		result.append(", terminatingProbability: ");
		result.append(terminatingProbability);
		result.append(')');
		return result.toString();
	}

} //ProbabilisticStateImpl
