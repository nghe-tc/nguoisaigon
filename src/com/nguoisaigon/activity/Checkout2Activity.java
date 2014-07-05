package com.nguoisaigon.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nguoisaigon.R;

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
		setContentView(R.layout.checkout2_layout);
		this.tvCheckoutStep1Title = (TextView) findViewById(R.id.tvCheckoutStep1Title);
		this.tvCheckoutStep2Title = (TextView) findViewById(R.id.tvCheckoutStep2Title);
		this.tvCheckoutStep3Title = (TextView) findViewById(R.id.tvCheckoutStep3Title);
		this.tvCheckoutStep4Title = (TextView) findViewById(R.id.tvCheckoutStep4Title);
		this.tvCheckoutStep2MainTitle = (TextView) findViewById(R.id.tvCheckoutStep2MainTitle);
		this.btnCheckout2Type1 = (ImageView) findViewById(R.id.btnCheckout2Type1);
		this.tvCheckoutStep2Type1Title = (TextView) findViewById(R.id.tvCheckoutStep2Type1Title);
		this.tvCheckoutStep2Type1Detail = (TextView) findViewById(R.id.tvCheckoutStep2Type1Detail);
		this.btnCheckout2Type2 = (ImageView) findViewById(R.id.btnCheckout2Type2);
		this.tvCheckoutStep2Type2Title = (TextView) findViewById(R.id.tvCheckoutStep2Type2Title);
		this.tvCheckoutStep2Type2Detail = (TextView) findViewById(R.id.tvCheckoutStep2Type2Detail);
		this.tvCheckoutStep2Type2AccountName = (TextView) findViewById(R.id.tvCheckoutStep2Type2AccountName);
		this.tvCheckoutStep2Type2AccountNumber = (TextView) findViewById(R.id.tvCheckoutStep2Type2AccountNumber);
		this.btnCheckout2Type3 = (ImageView) findViewById(R.id.btnCheckout2Type3);
		this.tvCheckoutStep2Type3Title = (TextView) findViewById(R.id.tvCheckoutStep2Type3Title);
		this.tvCheckoutStep2Type3Detail = (TextView) findViewById(R.id.tvCheckoutStep2Type3Detail);

		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/wg_legacy_edition.ttf");

		this.tvCheckoutStep1Title.setTypeface(tf);
		this.tvCheckoutStep2Title.setTypeface(tf);
		this.tvCheckoutStep3Title.setTypeface(tf);
		this.tvCheckoutStep4Title.setTypeface(tf);
		this.tvCheckoutStep2MainTitle.setTypeface(tf);
		this.tvCheckoutStep2Type1Title.setTypeface(tf);
		this.tvCheckoutStep2Type1Detail.setTypeface(tf);
		this.tvCheckoutStep2Type2Title.setTypeface(tf);
		this.tvCheckoutStep2Type2Detail.setTypeface(tf);
		this.tvCheckoutStep2Type2AccountName.setTypeface(tf);
		this.tvCheckoutStep2Type2AccountNumber.setTypeface(tf);
		this.tvCheckoutStep2Type3Title.setTypeface(tf);
		this.tvCheckoutStep2Type3Detail.setTypeface(tf);
	}

	public void btnCheckout2NextClick(View view) {
		if (this.paymentMethod > 0) {
			Intent intent = new Intent(this, Checkout3Activity.class);
			intent.putExtra("PAYMENT_METHOD", this.paymentMethod);
			startActivity(intent);
		} else {
			Toast.makeText(this, "Xin vui lòng chọn hình thức thanh toán",
					Toast.LENGTH_LONG).show();
		}
	}

	public void btnCheckout2PreviousClick(View view) {
		Intent intent = new Intent(this, Checkout1Activity.class);
		startActivity(intent);
	}

	public void btnCheckout2Type1Click(View view) {
		this.uncheckAll();
		((ImageView)view).setImageResource(R.drawable.checkbox_checked);
		this.paymentMethod = 1;
	}

	public void btnCheckout2Type2Click(View view) {
		this.uncheckAll();
		((ImageView)view).setImageResource(R.drawable.checkbox_checked);
		this.paymentMethod = 2;
	}

	public void btnCheckout2Type3Click(View view) {
		this.uncheckAll();
		((ImageView)view).setImageResource(R.drawable.checkbox_checked);
		this.paymentMethod = 3;
	}

	private void uncheckAll() {
		this.btnCheckout2Type1.setImageResource(R.drawable.checkbox_unchecked);
		this.btnCheckout2Type2.setImageResource(R.drawable.checkbox_unchecked);
		this.btnCheckout2Type3.setImageResource(R.drawable.checkbox_unchecked);
	}
}
