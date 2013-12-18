package de.unikassel.cs.kde.statistics;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import de.unikassel.cs.kde.statistics.data.Address;
import de.unikassel.cs.kde.statistics.data.AddressABComparator;
import de.unikassel.cs.kde.statistics.hilbert.AddressDataSource;
import de.unikassel.cs.kde.statistics.hilbert.DataSource;
import de.unikassel.cs.kde.statistics.hilbert.HilbertCanvasDrawerListener;
import de.unikassel.cs.kde.statistics.hilbert.HilbertCurve;
import de.unikassel.cs.kde.statistics.hilbert.HilbertListener;
import de.unikassel.cs.kde.statistics.hilbert.SWTCanvas;



/** Shows a statistic, where the most spammers are comming from.
 * 
 * <p>
 * On a map according to <a href="http://xkcd.com/195/">http://xkcd.com/195/</a>
 * spammers are drawn in green (the darker, the more spammers from that range), 
 * non-spammers in red (the darker, the more non-spammers from that range).
 * </p>
 * 
 * <p>
 * We only count class B networks, i.e., all 
 * IPs a.b.c.d starting with the same a and b will be counted together.
 * </p>
 * 
 * @author rja
 *
 */
public class SpammerIPs {

	private static final Logger log = Logger.getLogger(SpammerIPs.class);

	public static void main(String[] args) throws IOException, InterruptedException {

		/*
		 * Get counts from database. We only count class B networks, i.e., all 
		 * IPs a.b.c.d starting with the same a and b will be counted together.  
		 */
		final SortedMap<Address, Integer> spammerCounts = getInetAddressCountsFromDatabase(true, new AddressABComparator());
//		final SortedMap<Address, Integer> spammerCounts = new TreeMap<Address, Integer>();
//		final SortedMap<Address, Integer> nonSpammerCounts = new TreeMap<Address, Integer>();//getInetAddressCountsFromDatabase(false, new AddressABComparator());
		final SortedMap<Address, Integer> nonSpammerCounts = getInetAddressCountsFromDatabase(false, new AddressABComparator());

		log.info("Got " + spammerCounts.size() + " spammer networks.");
		log.info("Got " + nonSpammerCounts.size() + " non-spammer networks.");

		/*
		 * draw hilbert curve
		 */
		final int canvasSize = 256;
		final SWTCanvas canvas = new SWTCanvas(canvasSize, 4);
		final DataSource dataSource = new AddressDataSource(spammerCounts, nonSpammerCounts, canvasSize);
		final HilbertListener drawer = new HilbertCanvasDrawerListener(canvas, dataSource);
		final HilbertCurve hilbert = new HilbertCurve(drawer, 0, 0);

		log.info("Drawing Hilbert Curve.");
		hilbert.draw();

		log.info("Displaying canvas.");
		canvas.display();

		log.info("Finished!");
	}

	/** 
	 * Queries the database for counts of addresses from the user table.
	 * 
	 * @param spammersOnly - if <code>true</code>, counts <code>spammers</code>, if false, 
	 * counts non-spammers
	 * @param comp - the comparator to use for the SortedMap.
	 * @return
	 * @throws IOException
	 */
	private static SortedMap<Address, Integer> getInetAddressCountsFromDatabase(final boolean spammersOnly, final Comparator<Address> comp) throws IOException {
		final SortedMap<Address, Integer> counts = new TreeMap<Address, Integer>(comp);
		Properties prop = new Properties();
		prop.load(SpammerIPs.class.getClassLoader().getResourceAsStream("database.properties"));

		final String dbUser = prop.getProperty("db.user");
		final String dbPass = prop.getProperty("db.pass");
		final String dbURL  = prop.getProperty("db.url");


		try {
			Connection conn = null;
			PreparedStatement stmtP = null;
			ResultSet rst = null;
			/*
			 * connect to DB
			 */
			Class.forName ("com.mysql.jdbc.Driver").newInstance ();
			conn = DriverManager.getConnection (dbURL, dbUser, dbPass);
			log.info("Database connection established");

			if (conn != null){
				try {
					/*
					 * query db
					 * layout: data, date, host
					 */
					stmtP = conn.prepareStatement("SELECT COUNT(ip_address) AS count, ip_address, spammer " +
					"FROM user WHERE spammer = ? GROUP BY ip_address LIMIT 100000");
					stmtP.setBoolean(1, spammersOnly);
					rst = stmtP.executeQuery();


					/*
					 * collect matrix rows
					 */
					while (rst.next()) {
						final String inetProxyAddresses = rst.getString("ip_address");
						final int count = rst.getInt("count");
						

						final Address inetAddress = stripProxies(inetProxyAddresses);

						if (!counts.containsKey(inetAddress)) {
							counts.put(inetAddress, count);
						} else {
							/*
							 * add counts
							 */
							final int currCount = counts.get(inetAddress);
							counts.put(inetAddress, currCount + count);
						}

					}

					conn.close ();
				} catch (SQLException e) {
					log.fatal(e);
				}
			}

		} catch (SQLException e) {
			log.fatal(e);
		} catch (InstantiationException e) {
			log.fatal(e);
		} catch (IllegalAccessException e) {
			log.fatal(e);
		} catch (ClassNotFoundException e) {
			log.fatal(e);
		}
		return counts;
	}

	/** Removes proxy addresses.
	 * 
	 *  TODO: if address is a local address, use next address.
	 * 
	 * @param addresses
	 * @return
	 */
	private static Address stripProxies(final String addresses) {
		if (addresses != null) {
			final int pos = addresses.indexOf(',');

			if (pos > -1)
				return new Address(addresses.substring(0, pos));
			return new Address(addresses);
		}
		return new Address(0,0,0,0);

	}


}
