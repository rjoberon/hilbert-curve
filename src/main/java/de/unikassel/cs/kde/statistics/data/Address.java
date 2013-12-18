package de.unikassel.cs.kde.statistics.data;


/**
 * Represents an IPv4 address.
 * 
 * @author rja
 * 
 */
public class Address implements Comparable<Address> {
	private int a = 0;
	private int b = 0;
	private int c = 0;
	private int d = 0;

	public Address (final int a, final int b, final int c, final int d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}

	public Address (final String s) {
		final String parts[] = s.trim().split("\\.");
		try {
			a = Integer.parseInt(parts[0]);
			b = Integer.parseInt(parts[1]);
			c = Integer.parseInt(parts[2]);
			d = Integer.parseInt(parts[3]);
		} catch (NumberFormatException e) {
			// just ignore it - defaults are set 
		}
	}

	public int getA() {
		return a;
	}

	public int getB() {
		return b;
	}

	public int getC() {
		return c;
	}

	public int getD() {
		return d;
	}

	public int compareTo(Address o) {
		if (a == o.a) {
			if (b == o.b) {
				if (c == o.c) {
					return d - o.d;
				} else {
					return c - o.c;
				}
			} return b - o.b;
		} else {
			return a - o.a;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof Address) {
			return this.compareTo((Address) obj) == 0;
		} 
		return super.equals(obj);
	}
}