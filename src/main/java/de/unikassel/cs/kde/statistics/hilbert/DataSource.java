package de.unikassel.cs.kde.statistics.hilbert;

import de.unikassel.cs.kde.statistics.data.Color;


/**
 * A DataSource provides for each point on a line a color. 
 * 
 * @author:  rja
 * @version: $Id: DataSource.java,v 1.1 2008-07-04 08:40:24 rja Exp $
 * $Author: rja $
 * 
 */
public interface DataSource {
	
	/**
	 * @return The color of the next point.
	 */
	public Color getNextDataPoint();

}

