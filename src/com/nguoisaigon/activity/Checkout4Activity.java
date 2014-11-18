package com.nguoisaigon.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;
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

	private UiLifecycleHelper uiHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		uiHelper = new UiLifecycleHelper(this, Utils.statusCallback);
		uiHelper.onCreate(savedInstanceState);

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

	@Override
	protected void onResume() {
		super.onResume();
		uiHelper.onResume();
	}

	public void btnCheckout4FacebookClick(View view) {
		if (Utils.isFacebookLogin()) {
			StringBuilder message = new StringBuilder("Tôi đã sử dụng Người Sài Gòn App để mua hàng.\n");
			message.append(getString(R.string.app_url));

			Utils.postFacebookMessage(this, message.toString());
		} else {
			new LoginButton(this).performClick();
		}
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
