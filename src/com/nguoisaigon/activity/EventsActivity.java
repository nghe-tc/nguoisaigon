package com.nguoisaigon.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nguoisaigon.R;
import com.nguoisaigon.entity.EventsInfo;
import com.nguoisaigon.util.CustomPagerAdapter;
import com.nguoisaigon.util.EventsPageFragment;
import com.nguoisaigon.util.WebService;
import com.nguoisaigon.util.WebService.WebServiceDelegate;

@SuppressLint("SimpleDateFormat")
public class EventsActivity extends FragmentActivity implements
		WebServiceDelegate {

	/**
	 * The pager widget, which handles animation and allows swiping horizontally
	 * to access previous and next wizard steps.
	 */
	private ViewPager mPager;

	/**
	 * The pager adapter, which provides the pages to the view pager widget.
	 */
	private PagerAdapter mPagerAdapter;

	private ArrayList<EventsInfo> listEvents;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.events_layout);

		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/wg_legacy_edition.ttf");
		TextView tvCurrentDate = (TextView) findViewById(R.id.tvEventsCurrentDate);
		TextView tvPage = (TextView) findViewById(R.id.tvEventsPage);
		TextView tvLoading = (TextView) findViewById(R.id.tvEventsLoading);
		TextView tvNoEvent = (TextView) findViewById(R.id.noEvent);

		tvCurrentDate.setTypeface(tf);
		tvPage.setTypeface(tf);
		tvLoading.setTypeface(tf);
		tvNoEvent.setTypeface(tf);

		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
		String currentDate = formater.format(Calendar.getInstance().getTime());
		tvCurrentDate.setText(currentDate);
		this.loadData();
		this.setEventsPageChageLisener();
	}

	@Override
	public void taskCompletionResult(JSONArray result) {
		Log.i("EventsActivity", (result == null) ? "null" : result.toString());
		if (result != null) {
			try {
				for (int i = 0; i < result.length(); i++) {
					JSONObject eventJSON = result.getJSONObject(i);
					EventsInfo event = new EventsInfo();
					event.setEventId(eventJSON.getString("eventId"));
					event.setEventContent(eventJSON.getString("eventContent"));
					event.setTitle(eventJSON.getString("title"));
					this.listEvents.add(event);
				}
			} catch (Exception e) {
				Log.i("EventsActivity", e.getMessage());
			}
			Log.i("EventsActivity - total event", this.listEvents.size() + "");
		}
		updateData();
	}

	public void loadData() {
		this.listEvents = new ArrayList<EventsInfo>();
		// Download data
		WebService ws = new WebService(this);
		ws.setGettingEventsData();
		ws.execute();
	}

	public void updatePageNumView() {
		TextView tvPage = (TextView) findViewById(R.id.tvEventsPage);
		if (this.listEvents.size() > 0) {
			String pageDisplay = mPager.getCurrentItem() + 1 + "/"
					+ mPagerAdapter.getCount();
			tvPage.setText(pageDisplay);
		}

		ImageView btnPagePrevious = (ImageView) findViewById(R.id.btnEventsPagePrevious);
		ImageView btnPageNext = (ImageView) findViewById(R.id.btnEventsPageNext);

		if (mPagerAdapter.getCount() > 1) {
			if (mPager.getCurrentItem() == 0) {
				btnPagePrevious.setImageAlpha(70);
				btnPageNext.setImageAlpha(255);
			} else if (mPager.getCurrentItem() == (mPagerAdapter.getCount() - 1)) {
				btnPageNext.setImageAlpha(70);
				btnPagePrevious.setImageAlpha(255);
			} else {
				btnPagePrevious.setImageAlpha(255);
			}
		} else {
			btnPageNext.setImageAlpha(70);
			btnPagePrevious.setImageAlpha(70);
		}
	}

	public void updateData() {

		List<Fragment> fragments = getFragments();
		// Instantiate a ViewPager and a PagerAdapter.
		mPager = (ViewPager) findViewById(R.id.eventsPager);
		mPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager(),
				fragments);
		mPager.setAdapter(mPagerAdapter);
		this.updatePageNumView();

		TextView tvNoEvent = (TextView) findViewById(R.id.noEvent);
		if (mPagerAdapter.getCount() < 1) {
			tvNoEvent.setVisibility(TextView.VISIBLE);
		} else {
			tvNoEvent.setVisibility(TextView.GONE);
		}

		ProgressBar indicator = (ProgressBar) findViewById(R.id.eventsIndicator);
		indicator.setVisibility(ProgressBar.GONE);
		TextView tvLoading = (TextView) findViewById(R.id.tvEventsLoading);
		tvLoading.setVisibility(TextView.GONE);

	}

	private List<Fragment> getFragments() {
		Log.i("EventsActivity - getFragments", this.listEvents.size() + "");
		List<Fragment> fList = new ArrayList<Fragment>();
		for (EventsInfo event : this.listEvents) {
			fList.add(EventsPageFragment.newInstance(event));
		}
		return fList;
	}

	/**
	 * Close Events
	 * 
	 * @param view
	 */
	public void btnCloseClick(View view) {
		this.finish();
	}

	/**
	 * Share with Facebook
	 * 
	 * @param view
	 */
	public void btnFacebookClick(View view) {

	}

	/**
	 * Share with Email
	 * 
	 * @param view
	 */
	public void btnEmailClick(View view) {

	}

	/**
	 * Next Event
	 * 
	 * @param view
	 */
	public void btnPageNextClick(View view) {
		int currentItem = mPager.getCurrentItem();
		if (currentItem < mPager.getAdapter().getCount()) {
			mPager.setCurrentItem(mPager.getCurrentItem() + 1, true);
			this.updatePageNumView();
		}
	}

	/**
	 * Previous Event
	 * 
	 * @param view
	 */
	public void btnPagePreviousClick(View view) {
		int currentItem = mPager.getCurrentItem();
		if (currentItem > 0) {
			mPager.setCurrentItem(mPager.getCurrentItem() - 1, true);
			this.updatePageNumView();
		}
	}

	void setEventsPageChageLisener() {
		ViewPager pager = (ViewPager) findViewById(R.id.newsPager);
		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				Log.i("EventsActivity - onPageScrollStateChanged", "Start");
				TextView tvPage = (TextView) findViewById(R.id.tvEventsPage);
				if (listEvents.size() > 0) {
					String pageDisplay = mPager.getCurrentItem() + 1 + "/"
							+ mPagerAdapter.getCount();
					tvPage.setText(pageDisplay);
				}

				ImageView btnPagePrevious = (ImageView) findViewById(R.id.btnEventsPagePrevious);
				ImageView btnPageNext = (ImageView) findViewById(R.id.btnEventsPageNext);

				if (mPagerAdapter.getCount() > 1) {
					if (mPager.getCurrentItem() == 0) {
						btnPagePrevious.setImageAlpha(70);
						btnPageNext.setImageAlpha(255);
					} else if (mPager.getCurrentItem() == (mPagerAdapter
							.getCount() - 1)) {
						btnPageNext.setImageAlpha(70);
						btnPagePrevious.setImageAlpha(255);
					} else {
						btnPagePrevious.setImageAlpha(255);
					}
				} else {
					btnPageNext.setImageAlpha(70);
					btnPagePrevious.setImageAlpha(70);
				}
			}
		});
	}
}
