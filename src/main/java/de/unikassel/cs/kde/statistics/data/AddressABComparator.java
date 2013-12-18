package de.unikassel.cs.kde.statistics.data;

import java.util.Comparator;

/**
 * Compares two addresses based only on their first two parts (a & b) 
 *  
 * @author rja
 *
 */
public class AddressABComparator implements Comparator<Address> {

	public AddressABComparator() {
		// nothing to do
	}
	
	public int compare(Address o1, Address o2) {
		if (o1.getA() == o2.getA()) {
			return o1.getB() - o2.getB();
		} 
		return o1.getA() - o2.getA();

	}

}

