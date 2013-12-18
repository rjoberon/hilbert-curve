package de.unikassel.cs.kde.statistics.hilbert;

import de.unikassel.cs.kde.statistics.data.Color;

/**
 * A simple canvas drawing into an array.
 * 
 * @author:  rja
 * @version: $Id: ArrayCanvas.java,v 1.1 2008-07-04 08:40:25 rja Exp $
 * $Author: rja $
 * 
 */
public class ArrayCanvas implements HilbertCanvas {

	private int[][] array;
	
	public ArrayCanvas(final int size) {
		this.array = new int[size][size];
	}
	
	public void drawPoint(int x, int y, final Color color) {
		array[x][y] = (color.getR() + color.getG() + color.getB()) / 3;
	}
	
	/** Returns a string represenation of this canvas.
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		final StringBuffer buf = new StringBuffer();
		for (int x = 0; x < array.length; x++) {
			for (int y = 0; y < array.length; y++) {
				buf.append(array[x][y] + "\t");
			}
			buf.append("\n");
		}
		return buf.toString();
	}
	
	/** Prints the array to System.out
	 * 
	 * @see de.unikassel.cs.kde.statistics.hilbert.HilbertCanvas#display()
	 */
	public void display() {
		System.out.println(toString());
	}

	public int getSize() {
		return array.length;
	}
	
	

}

