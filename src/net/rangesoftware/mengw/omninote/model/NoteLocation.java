/**
 * @author Xuewei Wu
 */
package net.rangesoftware.mengw.omninote.model;

/**
 * 
 *
 */
public class NoteLocation {
	private double latitude;

	private double longitude;

	/**
	 * 
	 */
	public NoteLocation() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param latitude
	 * @param longitude
	 */
	public NoteLocation(double latitude, double longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 *            the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

}
