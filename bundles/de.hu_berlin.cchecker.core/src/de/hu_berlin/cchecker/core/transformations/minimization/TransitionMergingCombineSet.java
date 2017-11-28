package de.hu_berlin.cchecker.core.transformations.minimization;

/**
 * A {@link CombineSet} that combines {@link ProbabilisticFSATransition} by their 
 * from, to and actualLabel. 
 * 
 * If two transitions are equal in these properties and are added to this set, they are 
 * replaced by a new {@link ProbabilisticFSATransition} that is equal to them, only that 
 * it has their summed probabilities.
 *
 */
class TransitionMergingCombineSet extends CombineSet<ProbabilisticFSATransition> {
	protected ProbabilisticFSATransition combine(ProbabilisticFSATransition valueBefore,
			ProbabilisticFSATransition newValue) {
		// combine probabilistic FSA transitions by adding their probabilities
		return new ProbabilisticFSATransition(valueBefore.getFromState(), valueBefore.getToState(),
				valueBefore.getActualLabel(), valueBefore.getProbability() + newValue.getProbability());
	}

	/*
	 * (non-Javadoc)
	 * @see de.hu_berlin.cchecker.core.transformations.minimization.CombineSet#hashFunction(java.lang.Object)
	 * 
	 * Make sure the hash value only depends on fromState, toState and the actual label.
	 */
	@Override
	protected int hashFunction(ProbabilisticFSATransition element) {
		int prime1 = 1300139;
		int prime2 = 1300297;
		return (element.getToState().hashCode() * prime1) ^ (element.getFromState().hashCode() * prime2)
				^ element.getActualLabel().hashCode();
	}

	/*
	 * (non-Javadoc)
	 * @see de.hu_berlin.cchecker.core.transformations.minimization.CombineSet#equal(java.lang.Object, java.lang.Object)
	 * 
	 * Make sure equality is solely based on fromState, toState and the actual label.
	 */
	@Override
	protected boolean equal(ProbabilisticFSATransition element1, ProbabilisticFSATransition element2) {
		return element1.getActualLabel().equals(element2.getActualLabel()) &&
				element1.getToState().getID() == element2.getToState().getID() &&
				element1.getFromState().getID() == element2.getFromState().getID();
	}
}