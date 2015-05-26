/**
 * 
 */
package com.fut5.model;

import java.util.List;

/**
 * @author james_r_bray
 *
 */
public class User {
	
	private int userId;
	private String username;
	private String firstname;
	private String lastname;
	private List<Booking> myBookings;
	private SoccerField currentSoccerField;
	private static User user;

	/**
	 * 
	 */
	private User() {
		// TODO Auto-generated constructor stub
	}
	
	public static User getInstance() {
		if(user == null) {
			user = new User();
		}
		return user;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public List<Booking> getMyBookings() {
		return myBookings;
	}

	public void setMyBookings(List<Booking> myBookings) {
		this.myBookings = myBookings;
	}

	public SoccerField getCurrentSoccerField() {
		return currentSoccerField;
	}

	public void setCurrentSoccerField(SoccerField currentSoccerField) {
		this.currentSoccerField = currentSoccerField;
	}
	

}
