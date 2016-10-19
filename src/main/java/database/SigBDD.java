package database;

import geoexplorer.gui.Polygon;
import org.postgis.Point;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Tabs on 12/10/2016.
 */
public class SigBDD {

    public static ArrayList<Polygon> getBuilding(double longMin, double longMax, double latMin, double latMax, Connection connection) throws SQLException {
        String sql = "select ST_Transform(bbox,2154) from ways where ST_X(ST_Centroid(bbox)) BETWEEN ? AND ? AND ST_Y(ST_Centroid(bbox)) BETWEEN ? AND ? AND exist(tags,'building')";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setDouble(1, longMin);
        ps.setDouble(2, longMax);
        ps.setDouble(3, latMin);
        ps.setDouble(4, latMax);
        ResultSet rs = ps.executeQuery();
        ArrayList<Polygon> lPolygon = new ArrayList<>();

        while(rs.next()){
            org.postgis.PGgeometry pGgeometry = (org.postgis.PGgeometry) rs.getObject(1);
            org.postgis.Polygon bbox = (org.postgis.Polygon) pGgeometry.getGeometry();
            geoexplorer.gui.Polygon polygon = new geoexplorer.gui.Polygon(Color.decode("#D9D0C9"), Color.decode("#D9D0C9"));

            for (int i = 0; i<bbox.numPoints(); i++)
            {
                Point point = bbox.getPoint(i);
                polygon.addPoint(new geoexplorer.gui.Point(point.x, point.y));
            }
            lPolygon.add(polygon);
        }
        return lPolygon;
    }

    public static ArrayList<geoexplorer.gui.LineString> getRoads(double longMin, double longMax, double latMin, double latMax, Connection connection) throws SQLException {
        String sql = "select ST_Transform(linestring,2154) from ways where ST_X(ST_Centroid(linestring)) BETWEEN ? AND ? AND ST_Y(ST_Centroid(linestring)) BETWEEN ? AND ? AND exist(tags,'highway')";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setDouble(1, longMin);
        ps.setDouble(2, longMax);
        ps.setDouble(3, latMin);
        ps.setDouble(4, latMax);
        ResultSet rs = ps.executeQuery();
        ArrayList<geoexplorer.gui.LineString> lLineString = new ArrayList<>();

        while (rs.next()) {
            org.postgis.PGgeometry pGgeometry = (org.postgis.PGgeometry) rs.getObject(1);
            org.postgis.LineString bbox = (org.postgis.LineString) pGgeometry.getGeometry();
            geoexplorer.gui.LineString linestring = new geoexplorer.gui.LineString(Color.decode("#FFFFFF"));

            for (int i = 0; i < bbox.numPoints(); i++) {
                Point point = bbox.getPoint(i);
                linestring.addPoint(new geoexplorer.gui.Point(point.x, point.y, Color.red));
            }
            lLineString.add(linestring);
        }
        return lLineString;
    }

    public static ArrayList<Polygon> getStores(double longMin, double longMax, double latMin, double latMax, Connection connection) throws SQLException {
        String sql = "select ST_Transform(bbox,2154) from ways where ST_X(ST_Centroid(bbox)) BETWEEN ? AND ? AND ST_Y(ST_Centroid(bbox)) BETWEEN ? AND ? AND exist(tags,'shop')";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setDouble(1, longMin);
        ps.setDouble(2, longMax);
        ps.setDouble(3, latMin);
        ps.setDouble(4, latMax);
        ResultSet rs = ps.executeQuery();
        ArrayList<geoexplorer.gui.Polygon> lPolygon = new ArrayList<>();

        while(rs.next()){
            org.postgis.PGgeometry pGgeometry = (org.postgis.PGgeometry) rs.getObject(1);
            org.postgis.Polygon bbox = (org.postgis.Polygon) pGgeometry.getGeometry();
            geoexplorer.gui.Polygon polygon = new geoexplorer.gui.Polygon(Color.decode("#b20000"), Color.decode("#b20000"));

            for (int i = 0; i<bbox.numPoints(); i++)
            {
                Point point = bbox.getPoint(i);
                polygon.addPoint(new geoexplorer.gui.Point(point.x, point.y));
            }
            lPolygon.add(polygon);
        }
        return lPolygon;
    }


    public static String getPositionFromName(String pattern, Connection connection) throws SQLException {
        String sql = "select tags->'name', ST_X(ST_Centroid(linestring)), ST_Y(ST_Centroid(linestring)) from ways where tags->'name' LIKE ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, pattern);
        ResultSet rs = ps.executeQuery();
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Nom   | Longitude | Latitude" + "\n");
        stringBuilder.append("------+-----------+---------"+ "\n");

        while(rs.next()) {
            stringBuilder.append(rs.getString(1) + " | " + rs.getFloat(2) + " | " + rs.getFloat(3) + "\n");
        }

        return stringBuilder.toString();
    }

}
