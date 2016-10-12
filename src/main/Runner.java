/**
 * 
 */
package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;

import database.SigBDD;
import database.Utils;
import geoexplorer.gui.*;
import geoexplorer.gui.LineString;
import geoexplorer.gui.Polygon;

/**
 * @author couretn
 *
 */
public class Runner {
	
	private static final Logger logger = Logger.getLogger(Runner.class.getName());
	private static final double LONG_MIN = 5.7;
	private static final double LONG_MAX = 5.8;
	private static final double LAT_MIN = 45.1;
	private static final double LAT_MAX = 45.2;
	private Connection connection;

	public Runner() {
		connection = Utils.getConnection();
	}

	public void searchByNameMode(String pattern){
		try {
			System.out.print(SigBDD.getPositionFromName(pattern, connection));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void mapMode() throws SQLException {
		try {
			MapPanel map = new MapPanel(
					LONG_MIN+((LONG_MAX-LONG_MIN)/2),
					LAT_MIN+((LAT_MAX-LAT_MIN)/2),
					LONG_MAX - LONG_MIN);

			ArrayList<Polygon> lPolygon = SigBDD.getBuilding(LONG_MIN, LONG_MAX, LAT_MIN, LAT_MAX, connection);
			for (Polygon polygon : lPolygon)
                map.addPrimitive(polygon);

			ArrayList<LineString> lLineString = SigBDD.getRoads(LONG_MIN, LONG_MAX, LAT_MIN, LAT_MAX, connection);
			for(LineString lineString : lLineString)
                map.addPrimitive(lineString);

			GeoMainFrame mainFrame = new GeoMainFrame("Test", map);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void tearDow() {
		Utils.closeConnection();
		connection = null;
	}

}
