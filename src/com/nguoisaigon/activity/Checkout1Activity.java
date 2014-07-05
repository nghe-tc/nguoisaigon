package com.nguoisaigon.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nguoisaigon.R;
import com.nguoisaigon.db.UserDB;
import com.nguoisaigon.entity.UserInfo;

public class Checkout1Activity extends Activity {
	private TextView tvCheckoutStep1Title;
	private TextView tvCheckoutStep2Title;
	private TextView tvCheckoutStep3Title;
	private TextView tvCheckoutStep4Title;
	private TextView tvCheckoutStep1MainTitle;
	private TextView tvCheckoutNameTitle;
	private TextView tvCheckoutEmailTitle;
	private TextView tvCheckoutPhoneTitle;
	private TextView tvCheckoutAddressTitle;
	private TextView tvCheckoutNoteTitle;
	private TextView tvCheckoutDetailTitle;
	private EditText txtCheckoutName;
	private EditText txtCheckoutEmail;
	private EditText txtCheckoutPhone;
	private EditText txtCheckoutAddress;
	private EditText txtCheckoutNote;

	private UserInfo userInfo;
	private Boolean isNewUser = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkout1_layout);
		String mainTitle = "Bước 1 Thông tin liên hệ & giao hàng";

		this.tvCheckoutStep1Title = (TextView) findViewById(R.id.tvCheckoutStep1Title);
		this.tvCheckoutStep2Title = (TextView) findViewById(R.id.tvCheckoutStep2Title);
		this.tvCheckoutStep3Title = (TextView) findViewById(R.id.tvCheckoutStep3Title);
		this.tvCheckoutStep4Title = (TextView) findViewById(R.id.tvCheckoutStep4Title);
		this.tvCheckoutStep1MainTitle = (TextView) findViewById(R.id.tvCheckoutStep1MainTitle);
		this.tvCheckoutNameTitle = (TextView) findViewById(R.id.tvCheckoutNameTitle);
		this.tvCheckoutEmailTitle = (TextView) findViewById(R.id.tvCheckoutEmailTitle);
		this.tvCheckoutPhoneTitle = (TextView) findViewById(R.id.tvCheckoutPhoneTitle);
		this.tvCheckoutAddressTitle = (TextView) findViewById(R.id.tvCheckoutAddressTitle);
		this.tvCheckoutNoteTitle = (TextView) findViewById(R.id.tvCheckoutNoteTitle);
		this.tvCheckoutDetailTitle = (TextView) findViewById(R.id.tvCheckoutDetailTitle);
		this.txtCheckoutName = (EditText) findViewById(R.id.txtCheckoutName);
		this.txtCheckoutEmail = (EditText) findViewById(R.id.txtCheckoutEmail);
		this.txtCheckoutPhone = (EditText) findViewById(R.id.txtCheckoutPhone);
		this.txtCheckoutAddress = (EditText) findViewById(R.id.txtCheckoutAddress);
		this.txtCheckoutNote = (EditText) findViewById(R.id.txtCheckoutNote);

		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/wg_legacy_edition.ttf");

		this.tvCheckoutStep1Title.setTypeface(tf);
		this.tvCheckoutStep2Title.setTypeface(tf);
		this.tvCheckoutStep3Title.setTypeface(tf);
		this.tvCheckoutStep4Title.setTypeface(tf);
		this.tvCheckoutStep1MainTitle.setTypeface(tf);
		this.tvCheckoutNameTitle.setTypeface(tf);
		this.tvCheckoutEmailTitle.setTypeface(tf);
		this.tvCheckoutPhoneTitle.setTypeface(tf);
		this.tvCheckoutAddressTitle.setTypeface(tf);
		this.tvCheckoutNoteTitle.setTypeface(tf);
		this.tvCheckoutDetailTitle.setTypeface(tf);
//		this.txtCheckoutName.setTypeface(tf);
//		this.txtCheckoutEmail.setTypeface(tf);
//		this.txtCheckoutPhone.setTypeface(tf);
//		this.txtCheckoutAddress.setTypeface(tf);
//		this.txtCheckoutNote.setTypeface(tf);
		
		tvCheckoutStep1MainTitle.setText(mainTitle);
		
		this.loadData();
	}

	private void loadData() {
		UserDB db = new UserDB(this);
		this.userInfo = db.getUser();
		if (this.userInfo != null) {
			this.isNewUser = false;
			this.txtCheckoutName.setText(this.userInfo.getName());
			this.txtCheckoutEmail.setText(this.userInfo.getEmail());
			this.txtCheckoutPhone.setText(this.userInfo.getContactPhone());
			this.txtCheckoutAddress.setText(this.userInfo.getAddress());
		} else {
			this.userInfo = new UserInfo();
		}
	}

	public void btnCheckout1NextClick(View view) {
		if (this.checkInput()) {
			UserDB db = new UserDB(this);
			if (this.isNewUser) {
				db.insert(this.userInfo);
			} else {
				db.update(this.userInfo);
			}
			Intent intent = new Intent(this, Checkout2Activity.class);
			startActivity(intent);
		}
	}
	
	public void btnBackClick(View view) {
		this.onBackPressed();
	}

	private Boolean checkInput() {
		String name = this.txtCheckoutName.getText().toString();
		String email = this.txtCheckoutEmail.getText().toString();
		String phone = this.txtCheckoutPhone.getText().toString();
		String address = this.txtCheckoutAddress.getText().toString();
		String note = this.txtCheckoutNote.getText().toString();

		if (name.isEmpty()) {
			Toast.makeText(this, "Xin vui lòng nhập tên người dùng",
					Toast.LENGTH_LONG).show();
			this.txtCheckoutName.requestFocus();
			return false;
		}

		if (phone.isEmpty()) {
			Toast.makeText(this, "Xin vui lòng nhập số điện thoại",
					Toast.LENGTH_LONG).show();
			this.txtCheckoutPhone.requestFocus();
			return false;
		}

		if (address.isEmpty()) {
			Toast.makeText(this, "Xin vui lòng nhập địa chỉ giao hàng",
					Toast.LENGTH_LONG).show();
			this.txtCheckoutAddress.requestFocus();
			return false;
		}

		this.userInfo.setName(name);
		this.userInfo.setEmail(email);
		this.userInfo.setContactPhone(phone);
		this.userInfo.setAddress(address);
		this.userInfo.setNote(note);
		
		return true;
	}
}
