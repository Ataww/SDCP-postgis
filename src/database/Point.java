/**
 * 
 */
package database;

/**
 * @author couretn
 *
 */
public class Point {
	
	private String name;
	private float longi, lati;

	/**
	 * 
	 */
	public Point(String name, float longi, float lati) {
		this.name = name;
		this.longi = longi;
		this.lati = lati;
	}

	public String getName() {
		return name;
	}

	public float getLongi() {
		return longi;
	}

	public float getLati() {
		return lati;
	}

	@Override
	public String toString() {
		return name + " | " + longi + " | " + lati;
	}

}
