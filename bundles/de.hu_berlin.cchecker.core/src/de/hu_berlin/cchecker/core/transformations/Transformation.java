package de.hu_berlin.cchecker.core.transformations;

/**
 * A generic transformation from one type to another.
 * 
 * @param <FROM>
 *            The type to transform from
 * @param <TO>
 *            The type to transform to
 */
public abstract interface Transformation<FROM, TO> {

	/**
	 * Checks if the precondition for this transformation is fulfilled for the given
	 * input.
	 */
	public default boolean checkPrecondition(FROM from) {
		return true;
	}

	/**
	 * Performs the transformation.
	 * 
	 * This will not check in- nor output. To use the transformation safely please
	 * refer to {@link #safeTransform(Object)};
	 * 
	 * @param from
	 *            Input element for the transformation
	 * @return The transformed output.
	 */
	public TO transform(FROM from);

	/**
	 * Performs the transformation safely by checking the pre- as well as the
	 * postcondition.
	 */
	public default TO safeTransform(FROM from) throws ConditionNotFulfilledException {
		if (!this.checkPrecondition(from)) {
			throw new PreconditionNotFulfilledException(this, from);
		}
		TO transformed = transform(from);
		if (!this.checkPostCondition(transformed)) {
			throw new PostconditionNotFulfilledException(this, transformed);
		}
		return transformed;
	}

	/**
	 * Checks if the postcondition for this transformation is fulfilled for the
	 * given transformation output.
	 */
	public default boolean checkPostCondition(TO to) {
		return true;
	}

	public static abstract class ConditionNotFulfilledException extends Exception {
		private static final long serialVersionUID = 1L;

		private ConditionNotFulfilledException() {
		}
	}

	/**
	 * This exception is thrown if a transformation cannot be performed safely,
	 * since the precondition of the transformation wasn't fulfilled for the output.
	 */
	public static final class PreconditionNotFulfilledException extends ConditionNotFulfilledException {
		private static final long serialVersionUID = 1L;
		private final Transformation<?, ?> transformation;
		final private Object input;

		public PreconditionNotFulfilledException(Transformation<?, ?> transformation, Object input) {
			this.transformation = transformation;
			this.input = input;
		}

		@Override
		public String toString() {
			return "Failed to perform transformation " + transformation.getClass()
				.getSimpleName() + " on " + input.toString() + ": " + " precondition was not fulfilled.";
		}
	}

	/**
	 * This exception is thrown if a transformation cannot be performed safely,
	 * since the postcondition of the transformation wasn't fulfilled for the
	 * output.
	 */
	public static final class PostconditionNotFulfilledException extends ConditionNotFulfilledException {
		private static final long serialVersionUID = 1L;
		private final Transformation<?, ?> transformation;
		final private Object output;

		public PostconditionNotFulfilledException(Transformation<?, ?> transformation, Object output) {
			this.transformation = transformation;
			this.output = output;
		}

		@Override
		public String toString() {
			return "Failed to perform transformation " + transformation.getClass()
				.getSimpleName() + " on " + output.toString() + ": " + " postcondition was not fulfilled.";
		}
	}
}
