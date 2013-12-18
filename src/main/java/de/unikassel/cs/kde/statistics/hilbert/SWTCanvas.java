package de.unikassel.cs.kde.statistics.hilbert;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Drawing on an SWT canvas.
 * 
 * @author:  rja
 * @version: $Id: SWTCanvas.java,v 1.1 2008-07-04 08:40:25 rja Exp $
 * $Author: rja $
 * 
 */
public class SWTCanvas implements HilbertCanvas {

	private int realSize;
	private int size;
	private Display display;
	private GC gc;
	private Shell shell;

	private int factor = 10; // 4

	/**
	 * @param size - logical size of the array to be drawn on
	 * @param factor - scaling factor. physical size = size * factor.
	 */
	public SWTCanvas(int size, int factor) {
		this.size = size;
		this.factor = factor;
		this.realSize = this.size * this.factor;

		display = new Display();
		shell = new Shell(display);
		shell.setText("Map of BibSonomy Spammers (red = spammers, blue = users, brown = both)");
		shell.setSize(this.realSize + 25, this.realSize + 40);


		Canvas canvas = new Canvas(shell, SWT.NONE);
		canvas.setSize(this.realSize, this.realSize);
		canvas.setBackground(new Color(null, 255, 255, 255)); // white background
		canvas.setLocation(5, 5);
		shell.open();

		gc = new GC(canvas);


	}

	/**
	 * Draws raster lines.
	 */
	private void drawRaster() {
		gc.setForeground(new Color(null, 127, 127, 127)); // grey lines
		for (int i = 0; i < this.size; i++) {
			if (i % 16 == 0) {
				gc.drawLine(0, i * factor, this.realSize, i * factor);
				gc.drawLine(i * factor, 0, i * factor, this.realSize);
			}
		}
	}

	private Color getSWTColor(final de.unikassel.cs.kde.statistics.data.Color color) {
		return new Color(null, color.getR(), color.getG(), color.getB());
	}
	
	public void drawPoint(int x, int y, final de.unikassel.cs.kde.statistics.data.Color color) {
		gc.setForeground(getSWTColor(color));
		gc.setBackground(getSWTColor(color));
		gc.drawRectangle(x * factor, y * factor, factor, factor);
		gc.fillRectangle(x * factor, y * factor, factor, factor);
	}

	public int getSize() {
		return size;
	}

	/**
	 * Displays the result of drawing.
	 */
	public void display() {
		
		drawRaster();
		
		gc.dispose();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}

