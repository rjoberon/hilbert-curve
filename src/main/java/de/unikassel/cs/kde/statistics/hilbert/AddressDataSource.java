package de.unikassel.cs.kde.statistics.hilbert;

import java.util.SortedMap;

import de.unikassel.cs.kde.statistics.data.Address;
import de.unikassel.cs.kde.statistics.data.Color;

/**
 * This datasource walks over all class B networks and looks for each, 
 * how many spammers/non-spammers it contains.
 * 
 * <p>
 * The spammers are then marked green, the non-spammers red. 
 * </p>
 * 
 * @author:  rja
 * @version: $Id: AddressDataSource.java,v 1.1 2008-07-04 08:40:25 rja Exp $
 * $Author: rja $
 * 
 */
public class AddressDataSource implements DataSource {

	private SortedMap<Address, Integer> spammerCounts;
	private SortedMap<Address, Integer> nonSpammerCounts;

	private int maxCountSpammer = 0;
	private int maxCountNonSpammer = 0;

	/**
	 * Size of the field on which we draw.
	 */
	private int size;
	
	/**
	 * Current position (i.e. address)
	 */
	private int pos = 0;

	/**
	 * Maximal brightness of points
	 */
	private static final double maxBrightness = 150.0;

	public AddressDataSource(final SortedMap<Address, Integer> spammerCounts, final SortedMap<Address, Integer> nonSpammerCounts, int size) {
		this.spammerCounts = spammerCounts;
		this.nonSpammerCounts = nonSpammerCounts;
		this.size = size;
		calculateColorScalingFactor();
	}
	
	/**
	 * Calculates the scaling factor to scale counts to [0...255].
	 */
	private void calculateColorScalingFactor() {
		/*
		 * for spammers
		 */
		for (final Integer count: spammerCounts.values()) {
			if (count > maxCountSpammer ) maxCountSpammer = count;
		}
		
		/*
		 * for non-spammers
		 */
		for (final Integer count: nonSpammerCounts.values()) {
			if (count > maxCountNonSpammer ) maxCountNonSpammer = count;
		}
	}
	
	public Color getNextDataPoint() {
		final Address address = new Address(pos / size, pos % size,0,0);
		pos++;
		/*
		 * initialize color to white 
		 */
		int r = 255;
		int g = 255;
		int b = 255;
		if (spammerCounts.containsKey(address)) {
			final int col = getColor(spammerCounts.get(address), maxCountSpammer);
			// red
			g = col;
			b = col;
		}
		if (nonSpammerCounts.containsKey(address)) {
			final int col = getColor(nonSpammerCounts.get(address), maxCountNonSpammer);
			// blue
			r = col;
			g = col;
		}
		return new Color(r, g, b);
	}
	
	public int getColor (int count, int max) {
		return new Double(maxBrightness - (Math.log(count) * ((maxBrightness) / Math.log(max)))).intValue(); 
	}
	
	
}

