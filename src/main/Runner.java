/**
 * 
 */
package main;

import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	 */
	public static void main(String[] args) {
		if(args.length < 1) {
			logger.log(Level.SEVERE, "1 argument expected, none received.");
			return;
		}
		Runner runner = new Runner(args[0]);
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
