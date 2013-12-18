package de.unikassel.cs.kde.statistics.hilbert;

import de.unikassel.cs.kde.statistics.data.Color;


/**
 * Just increases a counter on each call of {@link #getNextDataPoint()} and 
 * returns it's value as gray color. After 256 calls, resets the color to 0. 
 *  
 * @author:  rja
 * @version: $Id: DummyDataSource.java,v 1.1 2008-07-04 08:40:25 rja Exp $
 * $Author: rja $
 * 
 */
public class DummyDataSource implements DataSource {

	private int pos = 0;
	
	public Color getNextDataPoint() {
		final Color color = new Color(pos, pos, pos);
		pos++;
		if (pos > 255) {
			pos = 0;
		}
		return color;
	}

}

