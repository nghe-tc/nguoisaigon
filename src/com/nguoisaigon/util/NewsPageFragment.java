package com.nguoisaigon.util;

import java.text.SimpleDateFormat;

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

@SuppressLint("SimpleDateFormat")
public class NewsPageFragment extends Fragment {

	public static final String NEWS_INFO = "NEWS_INFO";
	
	public static final NewsPageFragment newInstance(NewsInfo news) {
		NewsPageFragment f = new NewsPageFragment();
		Bundle bdl = new Bundle(1);
		String jsonNews = new Gson().toJson(news);
		bdl.putString(NEWS_INFO, jsonNews);
		f.setArguments(bdl);
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		String jsonNews = getArguments().getString(NEWS_INFO);
		NewsInfo news = new Gson().fromJson(jsonNews, NewsInfo.class);

		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.fragment_news_page, container, false);
		
		TextView tvNgayDang = (TextView) rootView
				.findViewById(R.id.TextView1);
		TextView tvCreateDate = (TextView) rootView
				.findViewById(R.id.tvNewsCreateDate);
		TextView tvTitle = (TextView) rootView.findViewById(R.id.tvNewsTitle);
		TextView tvContent = (TextView) rootView.findViewById(R.id.tvNewsContent);
		
		Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
				"fonts/wg_legacy_edition.ttf");
		
		tvNgayDang.setTypeface(tf);
		tvCreateDate.setTypeface(tf);
		tvTitle.setTypeface(tf);
		tvContent.setTypeface(tf);
		
		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
		String createDate = formater.format(news.getCreateDate());
		tvCreateDate.setText(createDate);
		tvTitle.setText(news.getTitle());
		tvContent.setText(news.getNewsContent());

		return rootView;
	}
}
