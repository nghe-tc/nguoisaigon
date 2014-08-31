package com.nguoisaigon.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.nguoisaigon.R;
import com.nguoisaigon.db.DBHelper;
import com.nguoisaigon.util.Emailplugin;
import com.nguoisaigon.util.Utils;

public class HomeScreenActivity extends Activity {
	private DBHelper datahelper;

	private ImageView storeHelp;
	private ImageView eventHelp;
	private ImageView newsHelp;
	private ImageView musicHelp;
	private ImageView btnhomestore;
	private ImageView btnhomehelp;
	private ImageView btnhomefacebook;
	private ImageView btnhomephone;
	private ImageView btnhomemusic;
	private ImageView btnhometv;
	private ImageView btnhomeevents;
	Handler handler = new Handler();

	private FrameLayout contactView;
	private TextView homeContactCafe;
	private TextView homeContactNguoiSaiGon;
	private TextView homeContactAddress1;
	private TextView homeContactAddress2;
	private TextView homeContactEmail;
	private TextView homeContactphone1;
	private TextView homeContactphone2;
	private TextView homeContactFacebook;

	Runnable hideHelp = new Runnable() {

		@Override
		public void run() {
			storeHelp.setVisibility(ImageView.GONE);
			eventHelp.setVisibility(ImageView.GONE);
			newsHelp.setVisibility(ImageView.GONE);
			musicHelp.setVisibility(ImageView.GONE);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homescreen);

		Utils.tf = Typeface.createFromAsset(getAssets(), "fonts/noteworthy.ttc");

		storeHelp = (ImageView) findViewById(R.id.homeStoreHelp);
		eventHelp = (ImageView) findViewById(R.id.homeCalendarHelp);
		newsHelp = (ImageView) findViewById(R.id.homeNewsHelp);
		musicHelp = (ImageView) findViewById(R.id.homeMusicHelp);

		btnhomestore = (ImageView) findViewById(R.id.btnhomestore);
		btnhomehelp = (ImageView) findViewById(R.id.btnhomehelp);
		btnhomefacebook = (ImageView) findViewById(R.id.btnhomefacebook);
		btnhomephone = (ImageView) findViewById(R.id.btnhomephone);
		btnhomemusic = (ImageView) findViewById(R.id.btnhomemusic);
		btnhometv = (ImageView) findViewById(R.id.btnhometv);
		btnhomeevents = (ImageView) findViewById(R.id.btnhomeevents);

		contactView = (FrameLayout) findViewById(R.id.homeContactView);
		homeContactCafe = (TextView) findViewById(R.id.homeContactCafe);
		homeContactNguoiSaiGon = (TextView) findViewById(R.id.homeContactNguoiSaiGon);
		homeContactAddress1 = (TextView) findViewById(R.id.homeContactAddress1);
		homeContactAddress2 = (TextView) findViewById(R.id.homeContactAddress2);
		homeContactEmail = (TextView) findViewById(R.id.homeContactEmail);
		homeContactphone1 = (TextView) findViewById(R.id.homeContactphone1);
		homeContactphone2 = (TextView) findViewById(R.id.homeContactphone2);
		homeContactFacebook = (TextView) findViewById(R.id.homeContactFacebook);

		setupContactView();

		// setDatahelper(new DBHelper(this));

		this.showHelp();

		this.setOntouchListener();
	}

	private ImageView tvImage, tvLight, musicImage, phoneImage;

	@Override
	protected void onResume() {
		super.onResume();
		tvImage = (ImageView) findViewById(R.id.homeTVAnimation);
		tvImage.setBackgroundResource(R.drawable.tvanimation);
		AnimationDrawable tvAnimation = (AnimationDrawable) tvImage.getBackground();
		tvAnimation.start();

		tvLight = (ImageView) findViewById(R.id.homeTVLight);
		tvLight.setBackgroundResource(R.drawable.tvlightanimation);
		AnimationDrawable tvLightAnimation = (AnimationDrawable) tvLight.getBackground();
		tvLightAnimation.start();

		musicImage = (ImageView) findViewById(R.id.homeMusic);
		musicImage.setBackgroundResource(R.drawable.musicanimation);
		AnimationDrawable musicAnimation = (AnimationDrawable) musicImage.getBackground();
		musicAnimation.start();

		phoneImage = (ImageView) findViewById(R.id.homePhone);
		phoneImage.setBackgroundResource(R.drawable.phoneanimation);
		AnimationDrawable phoneAnimation = (AnimationDrawable) phoneImage.getBackground();
		phoneAnimation.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		System.gc();
	}

	void setupContactView() {
		homeContactCafe.setTypeface(Utils.tf);
		homeContactNguoiSaiGon.setTypeface(Utils.tf);
		homeContactAddress1.setTypeface(Utils.tf);
		homeContactAddress2.setTypeface(Utils.tf);
		homeContactEmail.setTypeface(Utils.tf);
		homeContactphone1.setTypeface(Utils.tf);
		homeContactphone2.setTypeface(Utils.tf);
		homeContactFacebook.setTypeface(Utils.tf);
	}

	public void emailOnClick(View view) {
		try {
			Emailplugin.SendEmailFromHomeView(this);
		} catch (Exception e) {
			Log.e("HomeScreen", "emailOnClick: " + e.getMessage());
		}
	}

	public void phone2OnClick(View view) {
		try {
			String number = "tel:" + "0932113183";
			Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
			startActivity(callIntent);
		} catch (Exception e) {
			Log.e("HomeScreen", "phone2OnClick: " + e.getMessage());
		}
	}

	public void phone1OnClick(View view) {
		try {
			String number = "tel:" + "0932113103";
			Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
			startActivity(callIntent);
		} catch (Exception e) {
			Log.e("HomeScreen", "phone1OnClick: " + e.getMessage());
		}
	}

	public void facebookOnClick(View view) {
		try {
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/Cafedansaigon"));
			startActivity(browserIntent);
		} catch (Exception e) {
			Log.e("HomeScreen", "facebookOnClick: " + e.getMessage());
		}
	}

	public void hideContactOnClick(View view) {
		contactView.setVisibility(View.INVISIBLE);
	}

	public void btnstore_click(View view) {
		btnhomestore.setImageResource(R.drawable.store_normal);
		Intent intent = new Intent(this, StoreMainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);
	}

	public void btnhelp_click(View view) {
		btnhomehelp.setImageResource(R.drawable.help_normal);
		this.showHelp();
	}

	public void btnfacebook_click(View view) {
		btnhomefacebook.setImageResource(R.drawable.fbboard_normal);
	}

	public void btnphone_click(View view) {
		btnhomephone.setImageResource(R.drawable.phone_normal);
		contactView.setVisibility(View.VISIBLE);
	}

	public void btnmusic_click(View view) {
		btnhomemusic.setImageResource(R.drawable.musicplayer_normal);
		Intent intent = new Intent(this, MusicActivity.class);
		startActivity(intent);
	}

	public void btnevents_click(View view) {
		btnhomeevents.setImageResource(R.drawable.calendar_normal);
		Intent intent = new Intent(this, EventsActivity.class);
		startActivity(intent);
	}

	public void btntv_click(View view) {
		btnhometv.setImageResource(R.drawable.tv_normal);
		Intent intent = new Intent(this, NewsActivity.class);
		startActivity(intent);
	}

	public DBHelper getDatahelper() {
		return datahelper;
	}

	public void setDatahelper(DBHelper datahelper) {
		this.datahelper = datahelper;
	}

	@Override
	public void onBackPressed() {
		// Exit application
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	void showHelp() {
		storeHelp.setVisibility(ImageView.VISIBLE);
		eventHelp.setVisibility(ImageView.VISIBLE);
		newsHelp.setVisibility(ImageView.VISIBLE);
		musicHelp.setVisibility(ImageView.VISIBLE);

		handler.postDelayed(hideHelp, 5000);
	}

	private void setOntouchListener() {
		btnhomehelp.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				btnhomehelp.setImageResource(R.drawable.help_over);
				return false;
			}
		});

		btnhomefacebook.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				btnhomefacebook.setImageResource(R.drawable.fbboard_over);
				return false;
			}
		});

		btnhomestore.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				btnhomestore.setImageResource(R.drawable.store_over);
				return false;
			}
		});

		btnhomemusic.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				btnhomemusic.setImageResource(R.drawable.musicplayer_over);
				return false;
			}
		});

		btnhomephone.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				btnhomephone.setImageResource(R.drawable.phone_over);
				return false;
			}
		});

		btnhometv.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				btnhometv.setImageResource(R.drawable.tv_over);
				return false;
			}
		});

		btnhomeevents.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				btnhomeevents.setImageResource(R.drawable.calendar_over);
				return false;
			}
		});
	}
}
