package com.nguoisaigon.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.nguoisaigon.R;
import com.nguoisaigon.util.Utils;

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

		tvCheckoutStep1Title = (TextView) findViewById(R.id.tvCheckoutStep1Title);
		tvCheckoutStep2Title = (TextView) findViewById(R.id.tvCheckoutStep2Title);
		tvCheckoutStep3Title = (TextView) findViewById(R.id.tvCheckoutStep3Title);
		tvCheckoutStep4Title = (TextView) findViewById(R.id.tvCheckoutStep4Title);
		tvCheckoutStep4MainTitle = (TextView) findViewById(R.id.tvCheckoutStep4MainTitle);
		tvCheckoutStep4Detail = (TextView) findViewById(R.id.tvCheckoutStep4Detail);
		tvCheckoutStep3Title = (TextView) findViewById(R.id.tvCheckoutStep3Title);
		tvCheckout4FacebookTitle = (TextView) findViewById(R.id.tvCheckout4FacebookTitle);
		tvCheckout4StoreTitle = (TextView) findViewById(R.id.tvCheckout4StoreTitle);
		tvCheckout4HomeTitle = (TextView) findViewById(R.id.tvCheckout4HomeTitle);

		tvCheckoutStep1Title.setTypeface(Utils.tf);
		tvCheckoutStep2Title.setTypeface(Utils.tf);
		tvCheckoutStep3Title.setTypeface(Utils.tf);
		tvCheckoutStep4Title.setTypeface(Utils.tf);
		tvCheckoutStep4MainTitle.setTypeface(Utils.tf);
		tvCheckoutStep4Detail.setTypeface(Utils.tf);
		tvCheckoutStep3Title.setTypeface(Utils.tf);
		tvCheckout4FacebookTitle.setTypeface(Utils.tf);
		tvCheckout4StoreTitle.setTypeface(Utils.tf);
		tvCheckout4HomeTitle.setTypeface(Utils.tf);
	}

	public void btnCheckout4FacebookClick(View view) {

	}

	public void btnCheckout4StoreClick(View view) {
		Intent intent = new Intent(this, StoreMainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);
		finish();
	}

	public void btnCheckout4HomeClick(View view) {
		Intent intent = new Intent(this, HomeScreenActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);
		finish();
	}

	@Override
	protected void onPause() {
		super.onPause();
		Utils.unbindDrawables(findViewById(R.id.container));
		System.gc();
	}
}
