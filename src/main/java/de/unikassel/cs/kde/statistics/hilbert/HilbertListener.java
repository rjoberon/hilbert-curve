package de.unikassel.cs.kde.statistics.hilbert;


/**
 * The Listener reacts on calls to {@link #next(int, int)}. 
 * 
 * <p>
 * Whenever the {@link HilbertCurve} reaches a new point, it calls the 
 * {@link HilbertListener} with the coordinates of the new point.
 * </p>
 * 
 * @author:  rja
 * @version: $Id: HilbertListener.java,v 1.1 2008-07-04 08:40:25 rja Exp $
 * $Author: rja $
 * 
 */
public interface HilbertListener {
	
	public void next(final int x, final int y);

	public int getSize();
	
}

