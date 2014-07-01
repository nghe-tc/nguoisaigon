package com.nguoisaigon.util;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nguoisaigon.R;
import com.nguoisaigon.entity.EventsInfo;

public class EventsPageFragment extends Fragment {

	public static final String EVENTS_INFO = "EVENTS_INFO";
	
	public static final EventsPageFragment newInstance(EventsInfo event) {
		EventsPageFragment f = new EventsPageFragment();
		Bundle bdl = new Bundle(1);
		String jsonNews = new Gson().toJson(event);
		bdl.putString(EVENTS_INFO, jsonNews);
		f.setArguments(bdl);
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		String jsonNews = getArguments().getString(EVENTS_INFO);
		EventsInfo event = new Gson().fromJson(jsonNews, EventsInfo.class);

		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.fragment_events_page, container, false);
		
		TextView tvTitle = (TextView) rootView.findViewById(R.id.tvEventsTitle);
		TextView tvContent = (TextView) rootView.findViewById(R.id.tvEventsContent);
		
		Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
				"fonts/wg_legacy_edition.ttf");
		tvTitle.setTypeface(tf);
		//tvContent.setTypeface(tf);
		
		tvTitle.setText(event.getTitle());
		tvContent.setText(event.getEventContent());

		return rootView;
	}
}
