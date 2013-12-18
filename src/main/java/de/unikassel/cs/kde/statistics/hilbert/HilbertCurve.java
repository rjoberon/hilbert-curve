package de.unikassel.cs.kde.statistics.hilbert;


/**
 * Implementation of a 2-dimensional Hilbert Curve.
 * 
 * <p>A Hilbert Curve can be used, to transform a linear sequence into a
 * 2-dimensional area. The resulting area has the property, that points
 * which were adjacent/near in the sequence, are adjacent/near in the 
 * area, too.</p>
 * 
 * @author:  rja
 * @version: $Id: HilbertCurve.java,v 1.1 2008-07-04 08:40:25 rja Exp $
 * $Author: rja $
 * 
 */
public class HilbertCurve {

	/**
	 * Will be called upon reaching a new point of the curve.
	 */
	private HilbertListener listener;
	/*
	 * current positions
	 */
	private int x = 0;
	private int y = 0;
	
	/**
	 * maximal depth (= size) of the Hilbert Curve.
	 */
	private int maxLevel = 2; 


	/**
	 * Test method which draws a Hilbert Curve on an array and 
	 * prints it as a string.
	 * @param args
	 */
	public static void main(String[] args) {
		final int size = 16; // 8 = 64 steps, 16 = 256 steps, 256 = 65536 steps
		
		//final HilbertCanvas canvas = new ArrayCanvas(size); // simple array canvas
		final HilbertCanvas canvas = new SWTCanvas(size, 20); // 20 is good for size of 16, 4 for 256
		final HilbertListener drawer = new HilbertCanvasDrawerListener(canvas, new DummyDataSource());
		final HilbertCurve hilbert = new HilbertCurve(drawer, 0, 0); 
		
		hilbert.draw(); 
		
		canvas.display();
	}

	/** 
	 * @param listener - get's notified upon reaching a new point of the Hilbert Curve.
	 * @param startX - starting point's x position
	 * @param startY - starting point's y position
	 */
	public HilbertCurve (final HilbertListener listener, final int startX, final int startY) {
		this.x = startX;
		this.y = startY;
		this.listener = listener;
		/*
		 * Compute the level of the Hilbert Curve.
		 * With a level of A, we can fill a square array of size 2^A.
		 * Hence, we take the logarithm to the base 2 to get the level,
		 * given the size.
		 */
		this.maxLevel = ((Double) (Math.log(listener.getSize()) / Math.log(2))).intValue();
	}
	
	/**
	 * Starts drawing the Hilbert Curve.
	 */
	public void draw() {
		/*
		 * start direction important, subject to change
		 */
		this.hilbertLevel(maxLevel, Direction.UP); // UP
	}

	/**
	 * Code from 
	 * <a href="http://www.compuphase.com/hilbert.htm">http://www.compuphase.com/hilbert.htm</a>.
	 * @param level
	 * @param direction
	 */
	private void hilbertLevel(final int level, final Direction direction) {
		if (level==1) {
			switch (direction) {
			case LEFT:
				move(Direction.RIGHT);      /* move() could draw a line in... */
				move(Direction.DOWN);       /* ...the indicated direction */
				move(Direction.LEFT);
				break;
			case RIGHT:
				move(Direction.LEFT);
				move(Direction.UP);
				move(Direction.RIGHT);
				break;
			case UP:
				move(Direction.DOWN);
				move(Direction.RIGHT);
				move(Direction.UP);
				break;
			case DOWN:
				move(Direction.UP);
				move(Direction.LEFT);
				move(Direction.DOWN);
				break;
			} /* switch */
		} else {
			switch (direction) {
			case LEFT:
				hilbertLevel(level-1,Direction.UP);
				move(Direction.RIGHT);
				hilbertLevel(level-1,Direction.LEFT);
				move(Direction.DOWN);
				hilbertLevel(level-1,Direction.LEFT);
				move(Direction.LEFT);
				hilbertLevel(level-1,Direction.DOWN);
				break;
			case RIGHT:
				hilbertLevel(level-1,Direction.DOWN);
				move(Direction.LEFT);
				hilbertLevel(level-1,Direction.RIGHT);
				move(Direction.UP);
				hilbertLevel(level-1,Direction.RIGHT);
				move(Direction.RIGHT);
				hilbertLevel(level-1,Direction.UP);
				break;
			case UP:
				hilbertLevel(level-1,Direction.LEFT);
				move(Direction.DOWN);
				hilbertLevel(level-1,Direction.UP);
				move(Direction.RIGHT);
				hilbertLevel(level-1,Direction.UP);
				move(Direction.UP);
				hilbertLevel(level-1,Direction.RIGHT);
				break;
			case DOWN:
				hilbertLevel(level-1,Direction.RIGHT);
				move(Direction.UP);
				hilbertLevel(level-1,Direction.DOWN);
				move(Direction.LEFT);
				hilbertLevel(level-1,Direction.DOWN);
				move(Direction.DOWN);
				hilbertLevel(level-1,Direction.LEFT);
				break;
			} /* switch */
		} /* if */
	}

	private void move (final Direction direction) {
		switch (direction) {
		case LEFT:
			x = x - 1;
			break;
		case RIGHT:
			x = x + 1;
			break;
		case UP:
			y = y - 1;
			break;
		case DOWN:
			y = y + 1;
			break;
		}
		listener.next(x, y);
	}
	

	private static enum Direction {
		UP,
		LEFT,
		DOWN,
		RIGHT;
	}

}

