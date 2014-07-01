package com.nguoisaigon.entity;

import java.util.Calendar;

public class UserInfo {

	private String userId;
	private String name;
	private String email;
	private String address;
	private String contactPhone;
	private Double ernedPoint;

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the contactPhone
	 */
	public String getContactPhone() {
		return contactPhone;
	}

	/**
	 * @param contactPhone
	 *            the contactPhone to set
	 */
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	/**
	 * @return the ernedPoint
	 */
	public Double getErnedPoint() {
		return ernedPoint;
	}

	/**
	 * @param ernedPoint
	 *            the ernedPoint to set
	 */
	public void setErnedPoint(Double ernedPoint) {
		this.ernedPoint = ernedPoint;
	}
	
	public UserInfo(){
		Calendar cal = Calendar.getInstance();
		Long interval = cal.getTime().getTime();
		setUserId(interval.toString());
	}

}
