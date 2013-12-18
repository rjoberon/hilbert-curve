package de.unikassel.cs.kde.statistics.data;


/**
 * Representation of a Color to be independent of SWT wherever possible.
 * 
 * @author:  rja
 * @version: $Id: Color.java,v 1.1 2008-07-04 08:40:25 rja Exp $
 * $Author: rja $
 * 
 */
public class Color {

	private int r;
	private int g;
	private int b;
	public int getR() {
		return r;
	}
	public int getG() {
		return g;
	}
	public int getB() {
		return b;
	}
	public Color(int r, int g, int b) {
		super();
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
}

