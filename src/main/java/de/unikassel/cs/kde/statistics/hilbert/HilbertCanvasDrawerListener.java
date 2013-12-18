package de.unikassel.cs.kde.statistics.hilbert;

/**
 * This listener connects a {@link DataSource} with a {@link HilbertCanvas}. 
 * 
 * <p>
 * The DataSource provides data in linear form,
 * the HilbertCanvas provides a 2-dimensional area for drawing.
 * </p>
 * 
 * @author:  rja
 * @version: $Id: HilbertCanvasDrawerListener.java,v 1.1 2008-07-04 08:40:25 rja Exp $
 * $Author: rja $
 * 
 */
public class HilbertCanvasDrawerListener implements HilbertListener {

	private HilbertCanvas canvas;
	private DataSource dataSource;
	
	public HilbertCanvasDrawerListener (final HilbertCanvas canvas, final DataSource dataSource) {
		this.canvas = canvas;
		this.dataSource = dataSource;
		canvas.drawPoint(0, 0, dataSource.getNextDataPoint());
	}

	public void next(int x, int y) {
		canvas.drawPoint(x, y, dataSource.getNextDataPoint());
	}

	public int getSize() {
		return canvas.getSize();
	}
	
}

