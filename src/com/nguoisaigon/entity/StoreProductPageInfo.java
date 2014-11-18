package com.nguoisaigon.entity;

import java.util.ArrayList;

public class StoreProductPageInfo {

	public StoreProductPageInfo() {
		this.listProducInfo = new ArrayList<ProductInfo>();
	}

	private ArrayList<ProductInfo> listProducInfo;

	public ArrayList<ProductInfo> getListProducInfo() {
		return listProducInfo;
	}

	public void setListProducInfo(ArrayList<ProductInfo> listProducInfo) {
		this.listProducInfo = listProducInfo;
	}

	public void addProduct(ProductInfo product) {
		this.listProducInfo.add(product);
	}
}
