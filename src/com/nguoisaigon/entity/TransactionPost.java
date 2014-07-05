package com.nguoisaigon.entity;

import java.util.ArrayList;

public class TransactionPost {
	private ArrayList<TransactionDetailInfo> transDetailList;
	private UserInfo userInfo;
	private Integer paymentMethod;

	public TransactionPost() {

	}

	public TransactionPost(ArrayList<TransactionDetailInfo> transDetailList,
			UserInfo userInfo, Integer paymentMethod) {
		this.transDetailList = transDetailList;
		this.userInfo = userInfo;
		this.paymentMethod = paymentMethod;
	}

	/**
	 * @return the transDetailList
	 */
	public ArrayList<TransactionDetailInfo> getTransDetailList() {
		return transDetailList;
	}

	/**
	 * @param transDetailList
	 *            the transDetailList to set
	 */
	public void setTransDetailList(
			ArrayList<TransactionDetailInfo> transDetailList) {
		this.transDetailList = transDetailList;
	}

	/**
	 * @return the userInfo
	 */
	public UserInfo getUserInfo() {
		return userInfo;
	}

	/**
	 * @param userInfo
	 *            the userInfo to set
	 */
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	/**
	 * @return the paymentMethod
	 */
	public Integer getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * @param paymentMethod
	 *            the paymentMethod to set
	 */
	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

}
