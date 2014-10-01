/**
 * 
 */
package com.fut5.model;

import java.util.Date;

/**
 * @author james_r_bray
 *
 */
public class Booking {
	
	String bookingTime;
	Date dateTime;
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

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

}
