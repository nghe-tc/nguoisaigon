package com.nguoisaigon.activity;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;
import com.google.gson.Gson;
import com.nguoisaigon.R;
import com.nguoisaigon.entity.EventsInfo;
import com.nguoisaigon.util.CustomPagerAdapter;
import com.nguoisaigon.util.Emailplugin;
import com.nguoisaigon.util.EventsPageFragment;
import com.nguoisaigon.util.Utils;
import com.nguoisaigon.util.WebService;
import com.nguoisaigon.util.WebService.WebServiceDelegate;

@SuppressLint("SimpleDateFormat")
public class EventsActivity extends FragmentActivity implements WebServiceDelegate {

	/**
	 * The pager widget, which handles animation and allows swiping horizontally
	 * to access previous and next wizard steps.
	 */
	private ViewPager mPager;

	/**
	 * The pager adapter, which provides the pages to the view pager widget.
	 */
	private PagerAdapter mPagerAdapter;

	private ArrayList<EventsInfo> listEvents = new ArrayList<EventsInfo>();

	private UiLifecycleHelper uiHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		uiHelper = new UiLifecycleHelper(this, Utils.statusCallback);
		uiHelper.onCreate(savedInstanceState);

		setContentView(R.layout.events_layout);

		TextView tvPage = (TextView) findViewById(R.id.tvEventsPage);
		TextView tvLoading = (TextView) findViewById(R.id.tvEventsLoading);
		TextView tvNoEvent = (TextView) findViewById(R.id.noEvent);
		mPager = (ViewPager) findViewById(R.id.eventsPager);

		tvPage.setTypeface(Utils.tf);
		tvLoading.setTypeface(Utils.tf);
		tvNoEvent.setTypeface(Utils.tf);

		loadData();
		setEventsPageChageLisener();
	}

	@Override
	public void taskCompletionResult(JSONArray result) {
		Log.i("EventsActivity", (result == null) ? "null" : result.toString());
		if (result != null) {
			try {
				listEvents.clear();
				for (int i = 0; i < result.length(); i++) {
					JSONObject eventJSON = result.getJSONObject(i);
					EventsInfo event = new Gson().fromJson(eventJSON.toString(), EventsInfo.class);
					event.setEventId(eventJSON.getString("eventId"));
					event.setEventContent(eventJSON.getString("eventContent"));
					event.setTitle(eventJSON.getString("title"));
					listEvents.add(event);
				}
			} catch (Exception e) {
				Log.i("EventsActivity", e.getMessage());
			}
			Log.i("EventsActivity - total event", listEvents.size() + "");
		}
		updateData();
	}

	public void loadData() {
		// Download data
		if (WebService.isNetworkAvailable(this)) {
			WebService ws = new WebService(this);
			ws.setGettingEventsData();
			ws.execute();
		} else {
			ProgressBar indicator = (ProgressBar) findViewById(R.id.eventsIndicator);
			indicator.setVisibility(ProgressBar.GONE);
			TextView tvLoading = (TextView) findViewById(R.id.tvEventsLoading);
			tvLoading.setVisibility(TextView.GONE);
		}
	}

	public void updatePageNumView() {
		TextView tvPage = (TextView) findViewById(R.id.tvEventsPage);
		if (listEvents.size() > 0) {
			String pageDisplay = mPager.getCurrentItem() + 1 + "/" + mPagerAdapter.getCount();
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
		mPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager(), fragments);
		mPager = (ViewPager) findViewById(R.id.eventsPager);
		mPager.setAdapter(mPagerAdapter);
		updatePageNumView();

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

		ImageView email = (ImageView) findViewById(R.id.btnEventsEmail);
		ImageView facebook = (ImageView) findViewById(R.id.btnEventsFaceBook);
		if (listEvents.size() == 0) {
			email.setImageAlpha(70);
			facebook.setImageAlpha(70);
		} else {
			email.setImageAlpha(255);
			facebook.setImageAlpha(255);
		}
	}

	private List<Fragment> getFragments() {
		Log.i("EventsActivity - getFragments", listEvents.size() + "");
		List<Fragment> fList = new ArrayList<Fragment>();
		for (EventsInfo event : listEvents) {
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
		finish();
	}

	/**
	 * Share with Facebook
	 * 
	 * @param view
	 */
	public void btnFacebookClick(View view) {
		if (Utils.isFacebookLogin()) {
			if (listEvents.size() > 0) {
				EventsInfo event = listEvents.get(mPager.getCurrentItem());
				if (event != null) {
					StringBuilder message = new StringBuilder(event.getTitle() + "\n");
					message.append("Thời gian: " + event.getEventDate() + "\n");
					message.append(event.getEventContent() + "\n\n");
					message.append(getString(R.string.line_break));
					message.append(getString(R.string.title));
					message.append(getString(R.string.app_url));

					Utils.postFacebookMessage(this, message.toString());
				}
			}
		} else {
			new LoginButton(this).performClick();
		}
	}

	/**
	 * Share with Email
	 * 
	 * @param view
	 */
	public void btnEmailClick(View view) {
		if (listEvents.size() > 0) {
			Emailplugin.SendEmailFromEventView(this, listEvents.get(mPager.getCurrentItem()));
		}
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
			updatePageNumView();
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
			updatePageNumView();
		}
	}

	void setEventsPageChageLisener() {
		mPager.setOnPageChangeListener(new OnPageChangeListener() {

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
					String pageDisplay = mPager.getCurrentItem() + 1 + "/" + mPagerAdapter.getCount();
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
		});
	}

	@Override
	protected void onResume() {
		super.onResume();

		uiHelper.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();

		Utils.unbindDrawables(findViewById(R.id.container));
		System.gc();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}
}
