package com.nguoisaigon.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nguoisaigon.R;
import com.nguoisaigon.entity.NewsInfo;
import com.nguoisaigon.entity.ProductInfo;
import com.nguoisaigon.entity.StoreProductPageInfo;

@SuppressLint("SimpleDateFormat")
public class StoreProductPageFragment extends Fragment {

	public static final String LIST_PRODUCT_INFO = "LIST_PRODUCT_INFO";
	
	public static final StoreProductPageFragment newInstance(StoreProductPageInfo info) {
		StoreProductPageFragment f = new StoreProductPageFragment();
		Bundle bdl = new Bundle(1);
		String jsonNews = new Gson().toJson(info);
		bdl.putString(LIST_PRODUCT_INFO, jsonNews);
		f.setArguments(bdl);
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		String jsonNews = getArguments().getString(LIST_PRODUCT_INFO);
		StoreProductPageInfo info = new Gson().fromJson(jsonNews, StoreProductPageInfo.class);

		//show product
		
		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.fragment_news_page, container, false);
		

		return rootView;
	}
}
