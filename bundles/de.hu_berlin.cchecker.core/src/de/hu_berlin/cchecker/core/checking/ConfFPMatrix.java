package de.hu_berlin.cchecker.core.checking;

import java.util.Arrays;

/**
 * Conformance Footprint Matrix that holds information on how well trace data
 * matrices conform to given automata matrices.
 * 
 * Also contains information about the whereabouts of counterexamples. You can
 * use {@link ConfFPMatrix#getConformanceDegree()} to get a summarising degree of
 * conformance.
 */
public class ConfFPMatrix extends FPMatrix {

	/**
	 * String based matrix that holds the same values as the original matrix, but
	 * "f" characters for cells that indicate counter-examples.
	 */
	private String[][] counterExampleMatrix;

	private double magicalConformanceValue;

	/**
	 * Instantiates a new {@link ConfFPMatrix} from the given trace data set matrix
	 * and automata matrix.
	 * 
	 * This will compute the difference for each cell and also detect
	 * counter-examples.
	 */
	public ConfFPMatrix(final int length, FPMatrix traces, FPMatrix automaton, final int dimension) {
		super(length, traces.getTraceCount(), dimension);
		
		this.matrix = new double[dimension][dimension];
		this.counterExampleMatrix = new String[dimension][dimension];

		// calculation stuffs
		double sum = 0.0; // adding up entries for convormancevalue later
		int entrycount = 0; // number of unfaulted entries also later
		for (int i = 0; i < dimension; i++) {
			for (int k = 0; k < dimension; k++) {
				matrix[i][k] = automaton.getElement(i, k) - traces.getElement(i, k); // (model-trace)
				if (traces.getElement(i, k) != 0 && automaton.getElement(i, k) == 0) {// fill out upperyard
																						// matrix(visual stringmatrix
																						// filled with f at spot of
																						// counterExample)
					counterExampleMatrix[i][k] = "f";
				} else {
					counterExampleMatrix[i][k] = String.valueOf(matrix[i][k]);
					sum += Math.abs(matrix[i][k]);
					entrycount++;
				}
			}
		}

		// conformance value as fraction of 1 not as %
		this.magicalConformanceValue = 1 / (1 + (sum / entrycount));
	}

	/**
	 * Returns string matrix representation with "f" for counterexample and and
	 * original probability value if fine
	 */
	public String[][] getCounterExampleMatrix() {
		return this.counterExampleMatrix;
	}
	
	/**
	 * Returns the cell values of this conformance matrix.
	 * 
	 * This does not include information regarding counter example pairs.
	 */
	public double[][] getResultMatrix() {
		return this.matrix;
	}

	/**
	 * Returns element at [a][b] of the counter examples matrix. (See {@link #getCounterExampleMatrix()})
	 */
	public String getCounterExampleElement(int a, int b) {
		return this.counterExampleMatrix[a][b];
	}

	/**
	 * Returns degree of conformance as fraction of 1.
	 */
	public double getConformanceDegree() {
		return this.magicalConformanceValue;
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append("length=" + this.length + "\n");
		b.append("numberOfTraces=" + this.numberOfTraces + "\n");
		b.append("conformance=" + this.getConformanceDegree() + "\n");
		b.append("matrix=\n");

		for (int i = 0; i < this.matrix.length; i++) {
			b.append(Arrays.toString(this.matrix[i]) + "\n");
		}

		b.append("counterexamples=\n");
		String[][] counterExamples = this.getCounterExampleMatrix();

		for (int i = 0; i < counterExamples.length; i++) {
			b.append(String.join(" ", counterExamples[i]) + "\n");
		}

		return b.toString();
	}
}
