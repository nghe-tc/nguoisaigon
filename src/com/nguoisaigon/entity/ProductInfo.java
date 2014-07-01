package com.nguoisaigon.entity;

import java.util.ArrayList;

public class ProductInfo {
	private String productId;
	private Integer categoryId;
	private String name;
	private String createDate;
	private String description;
	private Double unitPrice;
	private Double salePrice;
	private Integer quantity;
	private Integer isNew;
	private Integer isHot;
	private Integer isSale;
	private Integer isDelete;
	private String CategoryName;
	private ArrayList<ImageInfo> imageList;
	private ArrayList<SizeInfo> sizeQtyList;
	private String ownerInfo;

	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * @param productId
	 *            the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * @return the categoryId
	 */
	public Integer getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId
	 *            the categoryId to set
	 */
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
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
	 * @return the createDate
	 */
	public String getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate
	 *            the createDate to set
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the unitPrice
	 */
	public Double getUnitPrice() {
		return unitPrice;
	}

	/**
	 * @param unitPrice
	 *            the unitPrice to set
	 */
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * @return the salePrice
	 */
	public Double getSalePrice() {
		return salePrice;
	}

	/**
	 * @param salePrice
	 *            the salePrice to set
	 */
	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	/**
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity
	 *            the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the isNew
	 */
	public Integer getIsNew() {
		return isNew;
	}

	/**
	 * @param isNew
	 *            the isNew to set
	 */
	public void setIsNew(Integer isNew) {
		this.isNew = isNew;
	}

	/**
	 * @return the isHot
	 */
	public Integer getIsHot() {
		return isHot;
	}

	/**
	 * @param isHot
	 *            the isHot to set
	 */
	public void setIsHot(Integer isHot) {
		this.isHot = isHot;
	}

	/**
	 * @return the isSale
	 */
	public Integer getIsSale() {
		return isSale;
	}

	/**
	 * @param isSale
	 *            the isSale to set
	 */
	public void setIsSale(Integer isSale) {
		this.isSale = isSale;
	}

	/**
	 * @return the isDelete
	 */
	public Integer getIsDelete() {
		return isDelete;
	}

	/**
	 * @param isDelete
	 *            the isDelete to set
	 */
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return CategoryName;
	}

	/**
	 * @param categoryName
	 *            the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		CategoryName = categoryName;
	}

	/**
	 * @return the imageList
	 */
	public ArrayList<ImageInfo> getImageList() {
		return imageList;
	}

	/**
	 * @param imageList
	 *            the imageList to set
	 */
	public void setImageList(ArrayList<ImageInfo> imageList) {
		this.imageList = imageList;
	}

	/**
	 * @return the sizeQtyList
	 */
	public ArrayList<SizeInfo> getSizeQtyList() {
		return sizeQtyList;
	}

	/**
	 * @param sizeQtyList
	 *            the sizeQtyList to set
	 */
	public void setSizeQtyList(ArrayList<SizeInfo> sizeQtyList) {
		this.sizeQtyList = sizeQtyList;
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

}
