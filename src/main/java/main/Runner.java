/**
 * 
 */
package main;

import java.sql.Connection;
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

	public void mapMode(MapMode mode) throws SQLException {
		try {
			MapPanel map = new MapPanel(1,1,1);

			if(mode.equals(MapMode.BASIC)){
				drawBuildings(map);
				drawRoads(map);
			}else if(mode.equals(MapMode.Q11)){
				drawBuildings(map);
				drawRoads(map);
				drawStores(map);
			}else if(mode.equals(MapMode.Q13)){
				drawBuildings(map);
				drawRoads(map);
				drawBoulangeries(map);
			}else{
				System.out.println("No mode selected ...");
			}
			
			GeoMainFrame mainFrame = new GeoMainFrame("TP SIG", map);
			map.autoAdjust();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void drawRoads(MapPanel map) throws SQLException{
		ArrayList<LineString> lLineString = SigBDD.getRoads(LONG_MIN, LONG_MAX, LAT_MIN, LAT_MAX, connection);
		for(LineString lineString : lLineString)
			map.addPrimitive(lineString);
	}

	public void drawBuildings(MapPanel map) throws SQLException{
		ArrayList<Polygon> lPolygon = SigBDD.getBuilding(LONG_MIN, LONG_MAX, LAT_MIN, LAT_MAX, connection);
		for (Polygon polygon : lPolygon)
			map.addPrimitive(polygon);
	}

	public void drawBoulangeries(MapPanel map) throws SQLException{
		ArrayList<Polygon> boulangeries = SigBDD.getQuartiersBoulangeries(connection);
		for(Polygon polygon: boulangeries)
			map.addPrimitive(polygon);
	}

	public void drawStores(MapPanel map) throws SQLException{
		ArrayList<Polygon> stores = SigBDD.getStores(LONG_MIN, LONG_MAX, LAT_MIN, LAT_MAX, connection);
		for(Polygon polygon: stores)
			map.addPrimitive(polygon);
	}

	public void tearDow() {
		Utils.closeConnection();
		connection = null;
	}

}
