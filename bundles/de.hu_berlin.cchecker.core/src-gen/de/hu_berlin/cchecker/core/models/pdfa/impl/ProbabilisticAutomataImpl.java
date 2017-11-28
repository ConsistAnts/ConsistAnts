/**
 */
package de.hu_berlin.cchecker.core.models.pdfa.impl;

import de.hu_berlin.cchecker.core.models.pdfa.PdfaPackage;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomataUtils;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticState;

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;

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
 * An implementation of the model object '<em><b>Probabilistic Automata</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.hu_berlin.cchecker.core.models.pdfa.impl.ProbabilisticAutomataImpl#getStates <em>States</em>}</li>
 *   <li>{@link de.hu_berlin.cchecker.core.models.pdfa.impl.ProbabilisticAutomataImpl#getStartState <em>Start State</em>}</li>
 *   <li>{@link de.hu_berlin.cchecker.core.models.pdfa.impl.ProbabilisticAutomataImpl#getTransitionLabels <em>Transition Labels</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProbabilisticAutomataImpl extends MinimalEObjectImpl.Container implements ProbabilisticAutomata {
	/**
	 * The cached value of the '{@link #getStates() <em>States</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStates()
	 * @generated
	 * @ordered
	 */
	protected EList<ProbabilisticState> states;

	/**
	 * The cached value of the '{@link #getStartState() <em>Start State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartState()
	 * @generated
	 * @ordered
	 */
	protected ProbabilisticState startState;

	/**
	 * The cached value of the '{@link #getTransitionLabels() <em>Transition Labels</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransitionLabels()
	 * @generated
	 * @ordered
	 */
	protected EMap<Integer, String> transitionLabels;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProbabilisticAutomataImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PdfaPackage.Literals.PROBABILISTIC_AUTOMATA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ProbabilisticState> getStates() {
		if (states == null) {
			states = new EObjectContainmentWithInverseEList<ProbabilisticState>(ProbabilisticState.class, this, PdfaPackage.PROBABILISTIC_AUTOMATA__STATES, PdfaPackage.PROBABILISTIC_STATE__AUTOMATA);
		}
		return states;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProbabilisticState getStartState() {
		if (startState != null && startState.eIsProxy()) {
			InternalEObject oldStartState = (InternalEObject)startState;
			startState = (ProbabilisticState)eResolveProxy(oldStartState);
			if (startState != oldStartState) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, PdfaPackage.PROBABILISTIC_AUTOMATA__START_STATE, oldStartState, startState));
			}
		}
		return startState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProbabilisticState basicGetStartState() {
		return startState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStartState(ProbabilisticState newStartState) {
		ProbabilisticState oldStartState = startState;
		startState = newStartState;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PdfaPackage.PROBABILISTIC_AUTOMATA__START_STATE, oldStartState, startState));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<Integer, String> getTransitionLabels() {
		if (transitionLabels == null) {
			transitionLabels = new EcoreEMap<Integer,String>(PdfaPackage.Literals.TRANSITION_LABEL_PAIR, TransitionLabelPairImpl.class, this, PdfaPackage.PROBABILISTIC_AUTOMATA__TRANSITION_LABELS);
		}
		return transitionLabels;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		return ProbabilisticAutomataUtils.toTextualRepresentation(this);
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
			case PdfaPackage.PROBABILISTIC_AUTOMATA__STATES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getStates()).basicAdd(otherEnd, msgs);
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
			case PdfaPackage.PROBABILISTIC_AUTOMATA__STATES:
				return ((InternalEList<?>)getStates()).basicRemove(otherEnd, msgs);
			case PdfaPackage.PROBABILISTIC_AUTOMATA__TRANSITION_LABELS:
				return ((InternalEList<?>)getTransitionLabels()).basicRemove(otherEnd, msgs);
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
			case PdfaPackage.PROBABILISTIC_AUTOMATA__STATES:
				return getStates();
			case PdfaPackage.PROBABILISTIC_AUTOMATA__START_STATE:
				if (resolve) return getStartState();
				return basicGetStartState();
			case PdfaPackage.PROBABILISTIC_AUTOMATA__TRANSITION_LABELS:
				if (coreType) return getTransitionLabels();
				else return getTransitionLabels().map();
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
			case PdfaPackage.PROBABILISTIC_AUTOMATA__STATES:
				getStates().clear();
				getStates().addAll((Collection<? extends ProbabilisticState>)newValue);
				return;
			case PdfaPackage.PROBABILISTIC_AUTOMATA__START_STATE:
				setStartState((ProbabilisticState)newValue);
				return;
			case PdfaPackage.PROBABILISTIC_AUTOMATA__TRANSITION_LABELS:
				((EStructuralFeature.Setting)getTransitionLabels()).set(newValue);
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
			case PdfaPackage.PROBABILISTIC_AUTOMATA__STATES:
				getStates().clear();
				return;
			case PdfaPackage.PROBABILISTIC_AUTOMATA__START_STATE:
				setStartState((ProbabilisticState)null);
				return;
			case PdfaPackage.PROBABILISTIC_AUTOMATA__TRANSITION_LABELS:
				getTransitionLabels().clear();
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
			case PdfaPackage.PROBABILISTIC_AUTOMATA__STATES:
				return states != null && !states.isEmpty();
			case PdfaPackage.PROBABILISTIC_AUTOMATA__START_STATE:
				return startState != null;
			case PdfaPackage.PROBABILISTIC_AUTOMATA__TRANSITION_LABELS:
				return transitionLabels != null && !transitionLabels.isEmpty();
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
			case PdfaPackage.PROBABILISTIC_AUTOMATA___TO_STRING:
				return toString();
		}
		return super.eInvoke(operationID, arguments);
	}

} //ProbabilisticAutomataImpl
