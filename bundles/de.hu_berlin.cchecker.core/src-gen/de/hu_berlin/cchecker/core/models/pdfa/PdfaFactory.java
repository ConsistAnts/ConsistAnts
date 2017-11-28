/**
 */
package de.hu_berlin.cchecker.core.models.pdfa;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see de.hu_berlin.cchecker.core.models.pdfa.PdfaPackage
 * @generated
 */
public interface PdfaFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	PdfaFactory eINSTANCE = de.hu_berlin.cchecker.core.models.pdfa.impl.PdfaFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Probabilistic Transition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Probabilistic Transition</em>'.
	 * @generated
	 */
	ProbabilisticTransition createProbabilisticTransition();

	/**
	 * Returns a new object of class '<em>Probabilistic State</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Probabilistic State</em>'.
	 * @generated
	 */
	ProbabilisticState createProbabilisticState();

	/**
	 * Returns a new object of class '<em>Probabilistic Automata</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Probabilistic Automata</em>'.
	 * @generated
	 */
	ProbabilisticAutomata createProbabilisticAutomata();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	PdfaPackage getPdfaPackage();

} //PdfaFactory
