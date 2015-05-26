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
	
	protected final static String TAG = "Booking";
	
	int userId;  // Not sure if I will need this
	int bookingTimeId;
	int soccerFieldId;
	String bookingTime;
	Date dateTime;
	boolean available;
	String soccerFieldName;
	int duration;

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

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getSoccerFieldName() {
		return soccerFieldName;
	}

	public void setSoccerFieldName(String soccerFieldName) {
		this.soccerFieldName = soccerFieldName;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getBookingTimeId() {
		return bookingTimeId;
	}

	public void setBookingTimeId(int bookingTimeId) {
		this.bookingTimeId = bookingTimeId;
	}

	public int getSoccerFieldId() {
		return soccerFieldId;
	}

	public void setSoccerFieldId(int soccerFieldId) {
		this.soccerFieldId = soccerFieldId;
	}

}
