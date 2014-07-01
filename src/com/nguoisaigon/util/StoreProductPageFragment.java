package com.nguoisaigon.util;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nguoisaigon.R;
import com.nguoisaigon.entity.ProductInfo;
import com.nguoisaigon.entity.StoreProductPageInfo;

@SuppressLint("SimpleDateFormat")
public class StoreProductPageFragment extends Fragment {
	FrameLayout product1;
	FrameLayout product2;
	FrameLayout product3;
	FrameLayout product4;
	FrameLayout product5;
	FrameLayout product6;
	FrameLayout product7;
	FrameLayout product8;

	public static final String LIST_PRODUCT_INFO = "LIST_PRODUCT_INFO";

	public static final StoreProductPageFragment newInstance(
			StoreProductPageInfo pageInfo) {
		StoreProductPageFragment f = new StoreProductPageFragment();
		Bundle bdl = new Bundle(1);
		String jsonListProduct = new Gson().toJson(pageInfo);
		bdl.putString(LIST_PRODUCT_INFO, jsonListProduct);
		f.setArguments(bdl);
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.fragment_store_page, container, false);

		String jsonListProduct = getArguments().getString(LIST_PRODUCT_INFO);
		Log.i("StoreProductPageFragment - onCreateView", "json list product: "
				+ jsonListProduct);

		StoreProductPageInfo pageInfo = new StoreProductPageInfo();
		pageInfo = new Gson().fromJson(jsonListProduct,
				StoreProductPageInfo.class);

		ArrayList<ProductInfo> listProduct = pageInfo.getListProducInfo();
		Log.i("StoreProductPageFragment - onCreateView",
				"size of list product: " + listProduct.size());
		//LayoutInflater inflater2 = (LayoutInflater) getActivity().getSystemService( getActivity().LAYOUT_INFLATER_SERVICE );
		//View view = inflater2.inflate( R.layout.fragment_store_page, null );
		
		this.product1 = (FrameLayout) rootView
				.findViewById(R.id.product1);
		this.product2 = (FrameLayout) rootView
				.findViewById(R.id.product2);
		this.product3 = (FrameLayout) rootView
				.findViewById(R.id.product3);
		this.product4 = (FrameLayout) rootView
				.findViewById(R.id.product4);
		this.product5 = (FrameLayout) rootView
				.findViewById(R.id.product5);
		this.product6 = (FrameLayout) rootView
				.findViewById(R.id.product6);
		this.product7 = (FrameLayout) rootView
				.findViewById(R.id.product7);
		this.product8 = (FrameLayout) rootView
				.findViewById(R.id.product8);

		this.product1.setVisibility(FrameLayout.GONE);
		this.product2.setVisibility(FrameLayout.GONE);
		this.product3.setVisibility(FrameLayout.GONE);
		this.product4.setVisibility(FrameLayout.GONE);
		this.product5.setVisibility(FrameLayout.GONE);
		this.product6.setVisibility(FrameLayout.GONE);
		this.product7.setVisibility(FrameLayout.GONE);
		this.product8.setVisibility(FrameLayout.GONE);
		ImageView newIcon = null;
		ImageView salesIcon = null;
		TextView productId = (TextView) rootView.findViewById(R.id.tvProduct1Id);
		// show product
		Integer index = 0;
		for (ProductInfo product : listProduct) {
			index++;
			switch (index) {
			case 1:
				this.product1.setVisibility(FrameLayout.VISIBLE);
				newIcon = (ImageView) getActivity().findViewById(
						R.id.storeProduct1NewIcon);
				salesIcon = (ImageView) getActivity().findViewById(
						R.id.storeProduct1SaleIcon);
				productId = (TextView) rootView.findViewById(R.id.tvProduct1Id);
				productId.setText(product.getProductId());
				break;
			case 2:
				this.product2.setVisibility(FrameLayout.VISIBLE);
				newIcon = (ImageView) getActivity().findViewById(
						R.id.storeProduct2NewIcon);
				salesIcon = (ImageView) getActivity().findViewById(
						R.id.storeProduct2SaleIcon);
				productId = (TextView) rootView.findViewById(R.id.tvProduct2Id);
				productId.setText(product.getProductId());
				break;
			case 3:
				this.product3.setVisibility(FrameLayout.VISIBLE);
				newIcon = (ImageView) getActivity().findViewById(
						R.id.storeProduct3NewIcon);
				salesIcon = (ImageView) getActivity().findViewById(
						R.id.storeProduct3SaleIcon);
				productId = (TextView) rootView.findViewById(R.id.tvProduct3Id);
				productId.setText(product.getProductId());
				break;
			case 4:
				this.product4.setVisibility(FrameLayout.VISIBLE);
				newIcon = (ImageView) getActivity().findViewById(
						R.id.storeProduct4NewIcon);
				salesIcon = (ImageView) getActivity().findViewById(
						R.id.storeProduct4SaleIcon);
				productId = (TextView) rootView.findViewById(R.id.tvProduct4Id);
				productId.setText(product.getProductId());
				break;
			case 5:
				this.product5.setVisibility(FrameLayout.VISIBLE);
				newIcon = (ImageView) getActivity().findViewById(
						R.id.storeProduct5NewIcon);
				salesIcon = (ImageView) getActivity().findViewById(
						R.id.storeProduct5SaleIcon);
				productId = (TextView) rootView.findViewById(R.id.tvProduct5Id);
				productId.setText(product.getProductId());
				break;
			case 6:
				this.product6.setVisibility(FrameLayout.VISIBLE);
				newIcon = (ImageView) getActivity().findViewById(
						R.id.storeProduct6NewIcon);
				salesIcon = (ImageView) getActivity().findViewById(
						R.id.storeProduct6SaleIcon);
				productId = (TextView) rootView.findViewById(R.id.tvProduct6Id);
				productId.setText(product.getProductId());
				break;
			case 7:
				this.product7.setVisibility(FrameLayout.VISIBLE);
				newIcon = (ImageView) getActivity().findViewById(
						R.id.storeProduct7NewIcon);
				salesIcon = (ImageView) getActivity().findViewById(
						R.id.storeProduct7SaleIcon);
				productId = (TextView) rootView.findViewById(R.id.tvProduct7Id);
				productId.setText(product.getProductId());
				break;
			case 8:
				this.product8.setVisibility(FrameLayout.VISIBLE);
				newIcon = (ImageView) getActivity().findViewById(
						R.id.storeProduct8NewIcon);
				salesIcon = (ImageView) getActivity().findViewById(
						R.id.storeProduct8SaleIcon);
				productId = (TextView) rootView.findViewById(R.id.tvProduct8Id);
				productId.setText(product.getProductId());
				break;

			default:
				break;
			}
			
			if (newIcon != null && product.getIsNew() != 1) {
				newIcon.setVisibility(ImageView.GONE);
			}
			if (salesIcon != null && product.getIsSale() != 1) {
				salesIcon.setVisibility(ImageView.GONE);
			}
		}

		return rootView;
	}
}
