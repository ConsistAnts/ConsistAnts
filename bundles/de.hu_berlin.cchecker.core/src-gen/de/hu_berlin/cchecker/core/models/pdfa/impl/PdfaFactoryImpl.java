/**
 */
package de.hu_berlin.cchecker.core.models.pdfa.impl;

import de.hu_berlin.cchecker.core.models.pdfa.*;

import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PdfaFactoryImpl extends EFactoryImpl implements PdfaFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PdfaFactory init() {
		try {
			PdfaFactory thePdfaFactory = (PdfaFactory)EPackage.Registry.INSTANCE.getEFactory(PdfaPackage.eNS_URI);
			if (thePdfaFactory != null) {
				return thePdfaFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new PdfaFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PdfaFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case PdfaPackage.PROBABILISTIC_TRANSITION: return createProbabilisticTransition();
			case PdfaPackage.PROBABILISTIC_STATE: return createProbabilisticState();
			case PdfaPackage.TRANSITION_LABEL_PAIR: return (EObject)createTransitionLabelPair();
			case PdfaPackage.PROBABILISTIC_AUTOMATA: return createProbabilisticAutomata();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProbabilisticTransition createProbabilisticTransition() {
		ProbabilisticTransitionImpl probabilisticTransition = new ProbabilisticTransitionImpl();
		return probabilisticTransition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProbabilisticState createProbabilisticState() {
		ProbabilisticStateImpl probabilisticState = new ProbabilisticStateImpl();
		return probabilisticState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<Integer, String> createTransitionLabelPair() {
		TransitionLabelPairImpl transitionLabelPair = new TransitionLabelPairImpl();
		return transitionLabelPair;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProbabilisticAutomata createProbabilisticAutomata() {
		ProbabilisticAutomataImpl probabilisticAutomata = new ProbabilisticAutomataImpl();
		return probabilisticAutomata;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PdfaPackage getPdfaPackage() {
		return (PdfaPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static PdfaPackage getPackage() {
		return PdfaPackage.eINSTANCE;
	}

} //PdfaFactoryImpl
