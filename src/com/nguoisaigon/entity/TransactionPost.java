package com.nguoisaigon.entity;

import java.util.ArrayList;

public class TransactionPost {
	private ArrayList<TransactionDetailInfo> transDetailList;
	private UserInfo userInfo;
	private Integer paymentMethod;
	private Double totalAmount;

	public TransactionPost() {

	}

	public TransactionPost(ArrayList<TransactionDetailInfo> transDetailList,
			UserInfo userInfo, Integer paymentMethod, Double totalAmount) {
		this.transDetailList = transDetailList;
		this.userInfo = userInfo;
		this.paymentMethod = paymentMethod;
		this.totalAmount = totalAmount;
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

	/**
	 * @return the totalAmount
	 */
	public Double getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

}
