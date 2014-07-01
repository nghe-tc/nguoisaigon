package com.nguoisaigon.entity;

import java.util.Date;

public class EventsInfo {
	
	private String title;
	private String ownerInfo;
	private String eventId;
	private String eventContent;
	private Date eventDate;

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the ownerInfo
	 */
	public String getOwnerInfo() {
		return ownerInfo;
	}

	/**
	 * @param ownerInfo
	 *            the ownerInfo to set
	 */
	public void setOwnerInfo(String ownerInfo) {
		this.ownerInfo = ownerInfo;
	}

	/**
	 * @return the eventId
	 */
	public String getEventId() {
		return eventId;
	}

	/**
	 * @param eventId
	 *            the eventId to set
	 */
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	/**
	 * @return the eventContent
	 */
	public String getEventContent() {
		return eventContent;
	}

	/**
	 * @param eventContent
	 *            the eventContent to set
	 */
	public void setEventContent(String eventContent) {
		this.eventContent = eventContent;
	}

	/**
	 * @return the eventDate
	 */
	public Date getEventDate() {
		return eventDate;
	}

	/**
	 * @param eventDate
	 *            the eventDate to set
	 */
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
}
