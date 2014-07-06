package com.nguoisaigon.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.nguoisaigon.R;

public class Checkout4Activity extends Activity {
	private TextView tvCheckoutStep1Title;
	private TextView tvCheckoutStep2Title;
	private TextView tvCheckoutStep3Title;
	private TextView tvCheckoutStep4Title;
	private TextView tvCheckoutStep4MainTitle;
	private TextView tvCheckoutStep4Detail;
	private TextView tvCheckout4FacebookTitle;
	private TextView tvCheckout4StoreTitle;
	private TextView tvCheckout4HomeTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkout4_layout);

		this.tvCheckoutStep1Title = (TextView) findViewById(R.id.tvCheckoutStep1Title);
		this.tvCheckoutStep2Title = (TextView) findViewById(R.id.tvCheckoutStep2Title);
		this.tvCheckoutStep3Title = (TextView) findViewById(R.id.tvCheckoutStep3Title);
		this.tvCheckoutStep4Title = (TextView) findViewById(R.id.tvCheckoutStep4Title);
		this.tvCheckoutStep4MainTitle = (TextView) findViewById(R.id.tvCheckoutStep4MainTitle);
		this.tvCheckoutStep4Detail = (TextView) findViewById(R.id.tvCheckoutStep4Detail);
		this.tvCheckoutStep3Title = (TextView) findViewById(R.id.tvCheckoutStep3Title);
		this.tvCheckout4FacebookTitle = (TextView) findViewById(R.id.tvCheckout4FacebookTitle);
		this.tvCheckout4StoreTitle = (TextView) findViewById(R.id.tvCheckout4StoreTitle);
		this.tvCheckout4HomeTitle = (TextView) findViewById(R.id.tvCheckout4HomeTitle);

		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/noteworthy.ttc");

		this.tvCheckoutStep1Title.setTypeface(tf);
		this.tvCheckoutStep2Title.setTypeface(tf);
		this.tvCheckoutStep3Title.setTypeface(tf);
		this.tvCheckoutStep4Title.setTypeface(tf);
		this.tvCheckoutStep4MainTitle.setTypeface(tf);
		this.tvCheckoutStep4Detail.setTypeface(tf);
		this.tvCheckoutStep3Title.setTypeface(tf);
		this.tvCheckout4FacebookTitle.setTypeface(tf);
		this.tvCheckout4StoreTitle.setTypeface(tf);
		this.tvCheckout4HomeTitle.setTypeface(tf);
	}

	public void btnCheckout4FacebookClick(View view) {

	}
	
	public void btnCheckout4StoreClick(View view) {
		Intent intent = new Intent(this, StoreMainActivity.class);
		startActivity(intent);
	}
	
	public void btnCheckout4HomeClick(View view) {
		Intent intent = new Intent(this, HomeScreenActivity.class);
		startActivity(intent);
	}
}
