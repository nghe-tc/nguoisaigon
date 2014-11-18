package com.nguoisaigon.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.nguoisaigon.R;
import com.nguoisaigon.util.Utils;

public class Checkout2Activity extends Activity {
	private TextView tvCheckoutStep1Title;
	private TextView tvCheckoutStep2Title;
	private TextView tvCheckoutStep3Title;
	private TextView tvCheckoutStep4Title;
	private TextView tvCheckoutStep2MainTitle;
	private ImageView btnCheckout2Type1;
	private TextView tvCheckoutStep2Type1Title;
	private TextView tvCheckoutStep2Type1Detail;
	private ImageView btnCheckout2Type2;
	private TextView tvCheckoutStep2Type2Title;
	private TextView tvCheckoutStep2Type2Detail;
	private TextView tvCheckoutStep2Type2AccountName;
	private TextView tvCheckoutStep2Type2AccountNumber;
	private ImageView btnCheckout2Type3;
	private TextView tvCheckoutStep2Type3Title;
	private TextView tvCheckoutStep2Type3Detail;
	private Integer paymentMethod = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	public void btnCheckout2NextClick(View view) {
		if (paymentMethod > 0) {
			Intent intent = new Intent(this, Checkout3Activity.class);
			intent.putExtra("PAYMENT_METHOD", paymentMethod);
			startActivity(intent);
		} else {
			Toast.makeText(this, "Xin vui lòng chọn hình thức thanh toán", Toast.LENGTH_SHORT).show();
		}
	}

	public void btnCheckout2PreviousClick(View view) {
		finish();
	}

	public void btnCheckout2Type1Click(View view) {
		uncheckAll();
		((ImageView) view).setImageResource(R.drawable.checkbox_checked);
		paymentMethod = 1;
	}

	public void btnCheckout2Type2Click(View view) {
		uncheckAll();
		((ImageView) view).setImageResource(R.drawable.checkbox_checked);
		paymentMethod = 2;
	}

	public void btnCheckout2Type3Click(View view) {
		uncheckAll();
		((ImageView) view).setImageResource(R.drawable.checkbox_checked);
		paymentMethod = 3;
	}

	private void uncheckAll() {
		btnCheckout2Type1.setImageResource(R.drawable.checkbox_unchecked);
		btnCheckout2Type2.setImageResource(R.drawable.checkbox_unchecked);
		btnCheckout2Type3.setImageResource(R.drawable.checkbox_unchecked);
	}

	@Override
	protected void onResume() {
		super.onResume();

		setContentView(R.layout.checkout2_layout);
		tvCheckoutStep1Title = (TextView) findViewById(R.id.tvCheckoutStep1Title);
		tvCheckoutStep2Title = (TextView) findViewById(R.id.tvCheckoutStep2Title);
		tvCheckoutStep3Title = (TextView) findViewById(R.id.tvCheckoutStep3Title);
		tvCheckoutStep4Title = (TextView) findViewById(R.id.tvCheckoutStep4Title);
		tvCheckoutStep2MainTitle = (TextView) findViewById(R.id.tvCheckoutStep2MainTitle);
		btnCheckout2Type1 = (ImageView) findViewById(R.id.btnCheckout2Type1);
		tvCheckoutStep2Type1Title = (TextView) findViewById(R.id.tvCheckoutStep2Type1Title);
		tvCheckoutStep2Type1Detail = (TextView) findViewById(R.id.tvCheckoutStep2Type1Detail);
		btnCheckout2Type2 = (ImageView) findViewById(R.id.btnCheckout2Type2);
		tvCheckoutStep2Type2Title = (TextView) findViewById(R.id.tvCheckoutStep2Type2Title);
		tvCheckoutStep2Type2Detail = (TextView) findViewById(R.id.tvCheckoutStep2Type2Detail);
		tvCheckoutStep2Type2AccountName = (TextView) findViewById(R.id.tvCheckoutStep2Type2AccountName);
		tvCheckoutStep2Type2AccountNumber = (TextView) findViewById(R.id.tvCheckoutStep2Type2AccountNumber);
		btnCheckout2Type3 = (ImageView) findViewById(R.id.btnCheckout2Type3);
		tvCheckoutStep2Type3Title = (TextView) findViewById(R.id.tvCheckoutStep2Type3Title);
		tvCheckoutStep2Type3Detail = (TextView) findViewById(R.id.tvCheckoutStep2Type3Detail);

		tvCheckoutStep1Title.setTypeface(Utils.tf);
		tvCheckoutStep2Title.setTypeface(Utils.tf);
		tvCheckoutStep3Title.setTypeface(Utils.tf);
		tvCheckoutStep4Title.setTypeface(Utils.tf);
		tvCheckoutStep2MainTitle.setTypeface(Utils.tf);
		tvCheckoutStep2Type1Title.setTypeface(Utils.tf);
		tvCheckoutStep2Type1Detail.setTypeface(Utils.tf);
		tvCheckoutStep2Type2Title.setTypeface(Utils.tf);
		tvCheckoutStep2Type2Detail.setTypeface(Utils.tf);
		tvCheckoutStep2Type2AccountName.setTypeface(Utils.tf);
		tvCheckoutStep2Type2AccountNumber.setTypeface(Utils.tf);
		tvCheckoutStep2Type3Title.setTypeface(Utils.tf);
		tvCheckoutStep2Type3Detail.setTypeface(Utils.tf);
	}

	@Override
	protected void onPause() {
		super.onPause();
		Utils.unbindDrawables(findViewById(R.id.container));
		System.gc();
	}
}
