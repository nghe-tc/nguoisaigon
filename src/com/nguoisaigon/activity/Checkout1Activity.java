package com.nguoisaigon.activity;

import org.json.JSONArray;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nguoisaigon.R;
import com.nguoisaigon.db.UserDB;
import com.nguoisaigon.entity.UserInfo;
import com.nguoisaigon.util.WebService;
import com.nguoisaigon.util.WebService.WebServiceDelegate;

public class Checkout1Activity extends Activity implements WebServiceDelegate {
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

		tvCheckoutStep1Title = (TextView) findViewById(R.id.tvCheckoutStep1Title);
		tvCheckoutStep2Title = (TextView) findViewById(R.id.tvCheckoutStep2Title);
		tvCheckoutStep3Title = (TextView) findViewById(R.id.tvCheckoutStep3Title);
		tvCheckoutStep4Title = (TextView) findViewById(R.id.tvCheckoutStep4Title);
		tvCheckoutStep1MainTitle = (TextView) findViewById(R.id.tvCheckoutStep1MainTitle);
		tvCheckoutNameTitle = (TextView) findViewById(R.id.tvCheckoutNameTitle);
		tvCheckoutEmailTitle = (TextView) findViewById(R.id.tvCheckoutEmailTitle);
		tvCheckoutPhoneTitle = (TextView) findViewById(R.id.tvCheckoutPhoneTitle);
		tvCheckoutAddressTitle = (TextView) findViewById(R.id.tvCheckoutAddressTitle);
		tvCheckoutNoteTitle = (TextView) findViewById(R.id.tvCheckoutNoteTitle);
		tvCheckoutDetailTitle = (TextView) findViewById(R.id.tvCheckoutDetailTitle);
		txtCheckoutName = (EditText) findViewById(R.id.txtCheckoutName);
		txtCheckoutEmail = (EditText) findViewById(R.id.txtCheckoutEmail);
		txtCheckoutPhone = (EditText) findViewById(R.id.txtCheckoutPhone);
		txtCheckoutAddress = (EditText) findViewById(R.id.txtCheckoutAddress);
		txtCheckoutNote = (EditText) findViewById(R.id.txtCheckoutNote);

		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/noteworthy.ttc");

		tvCheckoutStep1Title.setTypeface(tf);
		tvCheckoutStep2Title.setTypeface(tf);
		tvCheckoutStep3Title.setTypeface(tf);
		tvCheckoutStep4Title.setTypeface(tf);
		tvCheckoutStep1MainTitle.setTypeface(tf);
		tvCheckoutNameTitle.setTypeface(tf);
		tvCheckoutEmailTitle.setTypeface(tf);
		tvCheckoutPhoneTitle.setTypeface(tf);
		tvCheckoutAddressTitle.setTypeface(tf);
		tvCheckoutNoteTitle.setTypeface(tf);
		tvCheckoutDetailTitle.setTypeface(tf);
		tvCheckoutStep1MainTitle.setText(mainTitle);

		loadData();
	}

	private void loadData() {
		UserDB db = new UserDB(this);
		userInfo = db.getUser();
		if (userInfo != null) {
			isNewUser = false;
			txtCheckoutName.setText(userInfo.getName());
			txtCheckoutEmail.setText(userInfo.getEmail());
			txtCheckoutPhone.setText(userInfo.getContactPhone());
			txtCheckoutAddress.setText(userInfo.getAddress());
		} else {
			userInfo = new UserInfo();
		}
	}

	public void btnCheckout1NextClick(View view) {
		if (checkInput()) {
			final WebService ws = new WebService(this);
			if (isNewUser) {
				ws.setUserInfoRequest(userInfo);
				ws.execute();
			} else {
				if (checkUpdateUserInfo()) {
					final Context context = this;
					AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
					dlgAlert.setMessage("Thông tin liên lạc của bạn đã thay đổi?\nBạn có muốn chúng tôi lưu lại không?");
					dlgAlert.setTitle("Thông báo");
					dlgAlert.setPositiveButton("Đồng ý", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							ws.setEdittingUserInfoRequest(userInfo);
							ws.execute();
						}
					});
					dlgAlert.setNegativeButton("Không cần",
							new OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									Intent intent = new Intent(context,
											Checkout2Activity.class);
									startActivity(intent);
								}
							});
					dlgAlert.create().show();
				} else {
					Intent intent = new Intent(this, Checkout2Activity.class);
					startActivity(intent);
				}
			}
		}
	}

	public void btnBackClick(View view) {
		onBackPressed();
	}

	private Boolean checkUpdateUserInfo() {
		UserDB db = new UserDB(this);
		UserInfo oldUserInfo = db.getUser();
		String oldUser = new Gson().toJson(oldUserInfo);
		String newUser = new Gson().toJson(userInfo);
		if (oldUser.equals(newUser)) {
			return false;
		}
		return true;
	}

	private Boolean checkInput() {
		String name = txtCheckoutName.getText().toString();
		String email = txtCheckoutEmail.getText().toString();
		String phone = txtCheckoutPhone.getText().toString();
		String address = txtCheckoutAddress.getText().toString();
		String note = txtCheckoutNote.getText().toString();

		if (name.isEmpty()) {
			Toast.makeText(this, "Xin vui lòng nhập tên người dùng",
					Toast.LENGTH_SHORT).show();
			txtCheckoutName.requestFocus();
			return false;
		}

		if (phone.isEmpty()) {
			Toast.makeText(this, "Xin vui lòng nhập số điện thoại",
					Toast.LENGTH_SHORT).show();
			txtCheckoutPhone.requestFocus();
			return false;
		}

		if (address.isEmpty()) {
			Toast.makeText(this, "Xin vui lòng nhập địa chỉ giao hàng",
					Toast.LENGTH_SHORT).show();
			txtCheckoutAddress.requestFocus();
			return false;
		}

		userInfo.setName(name);
		userInfo.setEmail(email);
		userInfo.setContactPhone(phone);
		userInfo.setAddress(address);
		userInfo.setNote(note);

		return true;
	}

	@Override
	public void taskCompletionResult(JSONArray result) {
		Log.i("Checkout1Activity - taskCompletionResult", result.toString());
		try {
			userInfo = new Gson().fromJson(result.get(0).toString(),
					UserInfo.class);
		} catch (Exception e) {
			Log.e("Checkout1Activity - taskCompletionResult", e.getMessage());
		}

		if (!userInfo.getName().isEmpty()) {
			UserDB db = new UserDB(this);
			if (isNewUser) {
				db.insert(userInfo);
				Toast.makeText(this, "Thông tin người dùng đã được đăng ký mới",
						Toast.LENGTH_SHORT).show();
			} else {
				db.update(userInfo);
				Toast.makeText(this, "Thông tin người dùng đã được cập nhật mới",
						Toast.LENGTH_SHORT).show();
			}

			Intent intent = new Intent(this, Checkout2Activity.class);
			startActivity(intent);
		} else {
			
		}
	}
}
