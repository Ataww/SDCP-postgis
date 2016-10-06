/**
 * 
 */
package main;

import java.sql.Connection;

import database.Utils;

/**
 * @author couretn
 *
 */
public class Runner {
	
	private Connection connection;

	/**
	 * 
	 */
	public Runner() {
		connection = Utils.getConnection();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Runner runner = new Runner();
		runner.run();
		runner.tearDow();
	}
	
	public void run() {
	}
	
	public void tearDow() {
		Utils.closeConnection();
		connection = null;
	}

}
