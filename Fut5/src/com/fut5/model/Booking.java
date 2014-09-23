/**
 * 
 */
package com.fut5.model;

/**
 * @author james_r_bray
 *
 */
public class Booking {
	
	String bookingTime;
	boolean available;

	/**
	 * 
	 */
	public Booking() {
		// TODO Auto-generated constructor stub
	}

	public String getBookingTime() {
		return bookingTime;
	}

	public void setBookingTime(String bookingTime) {
		this.bookingTime = bookingTime;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

}
