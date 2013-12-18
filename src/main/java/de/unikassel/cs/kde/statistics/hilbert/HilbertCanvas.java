package de.unikassel.cs.kde.statistics.hilbert;

import de.unikassel.cs.kde.statistics.data.Color;


/**
 * Provides an area for drawing.
 * 
 * @author:  rja
 * @version: $Id: HilbertCanvas.java,v 1.1 2008-07-04 08:40:25 rja Exp $
 * $Author: rja $
 * 
 */
public interface HilbertCanvas {

	public void drawPoint(final int x, final int y, final Color color);

	public int getSize();
	
	public void display();
}

