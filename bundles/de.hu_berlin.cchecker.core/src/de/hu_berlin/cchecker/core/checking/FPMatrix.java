package de.hu_berlin.cchecker.core.checking;

import java.util.Arrays;

/**
 * Generic probabilistic Footprint-Matrix that holds probabilistic and structural information
 * about a trace data set or automata.
 * 
 * @author HorazVitae
 */
public class FPMatrix {
	
	/**
	 * The multidimensional value array of this matrix. 
	 */
	protected double[][] matrix;
	
	/**
	 * The dimension of the quadratic matrix. 
	 */
	private final int dimension;
	
	/**
	 * The length of trace represented by this matrix  (depth of automaton for autos -- still length of trace tho even in auto just a side note)
	 */
	protected int length;
	
	/**
	 * The number of traces and prefixes for current length set 
	 */
	protected int numberOfTraces;
	
	/**
	 * Instantiates a new matrix with the given alphabet, length of current set and number of traces in the length set.
	 */
	public FPMatrix(int len, int num, final int dimension) {
		this.numberOfTraces = num;
		this.matrix = new double[dimension][dimension];
		this.length = len;
		this.dimension = dimension;
	}
	
	/**
	 * Returns length of current set.
	 */
	public int getLength() {
		return this.length;
	}

	
	/**
	 * Returns value of cell[a][b]
	 */
	public double getElement(int a, int b) {
		return this.matrix[a][b];
	}
	
	/**
	 * Increases value of cell[a][b] by the given value
	 */
	public void increaseElementBy(int a, int b, double value) {
		matrix[a][b] += value;
	}
	
	/**
	 * Sets the value of cell[a][b] to given value
	 */
	public void setElement(int a, int b, double value) {
		matrix[a][b] = value;
	}
	
	/**
	 * Returns dimension of this matrix. Note that all FP matrices are quadratic.
	 */
	public int getDimension() {
		return this.dimension;
	}
	
	/**
	 * Returns the number of traces this matrix holds information for.
	 * 
	 * Returns <code>0</code> if this is an automata matrix.
	 */
	public int getTraceCount() {
		return this.numberOfTraces;
	}
	
	public String toString() {
		StringBuilder b = new StringBuilder();
		
		b.append("length=" + this.length + "\n");
		b.append("numberOfTraces=" + this.numberOfTraces + "\n");
		b.append("matrix=\n");
		
		for(int i=0; i<this.matrix.length; i++) {
			b.append(Arrays.toString(this.matrix[i]) + "\n");
		}
		
		return b.toString();
	}

}
