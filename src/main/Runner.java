/**
 * 
 */
package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import database.Point;
import database.Utils;

/**
 * @author couretn
 *
 */
public class Runner {
	
	private static final Logger logger = Logger.getLogger(Runner.class.getName());
	
	private Connection connection;
	String queryArg;

	/**
	 * 
	 */
	public Runner(String arg) {
		connection = Utils.getConnection();
		queryArg = arg;
	}

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		if(args.length < 1) {
			logger.log(Level.SEVERE, "1 argument expected, none received.");
			return;
		}
		Runner runner = new Runner(args[0]);
		runner.run();
		runner.tearDow();
	}
	
	public void run() throws SQLException {
		String sql = "select tags->'name' from nodes where "; // TODO
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, queryArg);
		ResultSet rs = ps.executeQuery();
		System.out.println("Nom   | Longitude | Latitude");
		System.out.println("------+-----------+---------");
		while(rs.next()) {
			Point p = new Point(rs.getString(1),rs.getFloat(2), rs.getFloat(3));
			System.out.println(p.toString());
		}
	}
	
	public void tearDow() {
		Utils.closeConnection();
		connection = null;
	}

}
