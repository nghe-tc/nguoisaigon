package com.nguoisaigon.entity;

import java.util.Date;

public class NewsInfo {
	private String title;
	private String ownerInfo;
	private String newsId;
	private String newsContent;
	private Date createDate;

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
	 * @return the newsId
	 */
	public String getNewsId() {
		return newsId;
	}

	/**
	 * @param newsId
	 *            the newsId to set
	 */
	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}

	/**
	 * @return the newsContent
	 */
	public String getNewsContent() {
		return newsContent;
	}

	/**
	 * @param newsContent
	 *            the newsContent to set
	 */
	public void setNewsContent(String newsContent) {
		this.newsContent = newsContent;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate
	 *            the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
