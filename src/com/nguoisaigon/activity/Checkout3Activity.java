package com.nguoisaigon.activity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nguoisaigon.R;
import com.nguoisaigon.db.TransactionDetailDB;
import com.nguoisaigon.db.UserDB;
import com.nguoisaigon.entity.TransactionDetailInfo;
import com.nguoisaigon.entity.TransactionPost;
import com.nguoisaigon.entity.UserInfo;
import com.nguoisaigon.util.Checkout3TransactionAdapter;
import com.nguoisaigon.util.WebService;
import com.nguoisaigon.util.WebService.WebServiceDelegate;

public class Checkout3Activity extends Activity implements WebServiceDelegate {
	private TextView tvCheckoutStep1Title;
	private TextView tvCheckoutStep2Title;
	private TextView tvCheckoutStep3Title;
	private TextView tvCheckoutStep4Title;
	private TextView tvCheckoutStep3MainTitle;
	private TextView tvCheckoutStep3NameTitle;
	private TextView tvCheckoutStep3Name;
	private TextView tvCheckoutStep3AddressTitle;
	private TextView tvCheckoutStep3Address;
	private TextView tvCheckoutStep3PhoneTitle;
	private TextView tvCheckoutStep3Phone;
	private TextView tvCheckoutStep3NoteTitle;
	private TextView tvCheckoutStep3Note;
	private TextView tvCheckoutStep3ProductNameTitle;
	private TextView tvCheckoutStep3ProductQuantityTitle;
	private TextView tvCheckoutStep3ProductPriceTitle;
	private TextView tvCheckoutStep3ProductTotalTitle;
	private ListView checkout3ListTransaction;
	private TextView tvCheckoutStep3PaymentMethod;
	private TextView tvCheckoutStep3PaymentTotalTitle;
	private TextView tvCheckoutStep3PaymentTotal;

	private Integer paymentMethod = 1;
	private Double totalAmount = 0.0;
	private ArrayList<TransactionDetailInfo> listTransaction;
	private Checkout3TransactionAdapter adapter;
	private UserInfo userInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkout3_layout);

		Log.i("Checkout3Activity - onCreate", "PAYMENT_METHOD: "
				+ this.getIntent().getIntExtra("PAYMENT_METHOD", 1));
		this.paymentMethod = this.getIntent().getIntExtra("PAYMENT_METHOD", 1);

		this.tvCheckoutStep1Title = (TextView) findViewById(R.id.tvCheckoutStep1Title);
		this.tvCheckoutStep2Title = (TextView) findViewById(R.id.tvCheckoutStep2Title);
		this.tvCheckoutStep3Title = (TextView) findViewById(R.id.tvCheckoutStep3Title);
		this.tvCheckoutStep4Title = (TextView) findViewById(R.id.tvCheckoutStep4Title);

		this.tvCheckoutStep3MainTitle = (TextView) findViewById(R.id.tvCheckoutStep3MainTitle);
		this.tvCheckoutStep3NameTitle = (TextView) findViewById(R.id.tvCheckoutStep3NameTitle);
		this.tvCheckoutStep3Name = (TextView) findViewById(R.id.tvCheckoutStep3Name);
		this.tvCheckoutStep3AddressTitle = (TextView) findViewById(R.id.tvCheckoutStep3AddressTitle);
		this.tvCheckoutStep3Address = (TextView) findViewById(R.id.tvCheckoutStep3Address);
		this.tvCheckoutStep3PhoneTitle = (TextView) findViewById(R.id.tvCheckoutStep3PhoneTitle);
		this.tvCheckoutStep3Phone = (TextView) findViewById(R.id.tvCheckoutStep3Phone);
		this.tvCheckoutStep3NoteTitle = (TextView) findViewById(R.id.tvCheckoutStep3NoteTitle);
		this.tvCheckoutStep3Note = (TextView) findViewById(R.id.tvCheckoutStep3Note);
		this.tvCheckoutStep3ProductNameTitle = (TextView) findViewById(R.id.tvCheckoutStep3ProductNameTitle);
		this.tvCheckoutStep3ProductQuantityTitle = (TextView) findViewById(R.id.tvCheckoutStep3ProductQuantityTitle);
		this.tvCheckoutStep3ProductPriceTitle = (TextView) findViewById(R.id.tvCheckoutStep3ProductPriceTitle);
		this.tvCheckoutStep3ProductTotalTitle = (TextView) findViewById(R.id.tvCheckoutStep3ProductTotalTitle);
		this.checkout3ListTransaction = (ListView) findViewById(R.id.checkout3ListTransaction);
		this.tvCheckoutStep3PaymentMethod = (TextView) findViewById(R.id.tvCheckoutStep3PaymentMethod);
		this.tvCheckoutStep3PaymentTotalTitle = (TextView) findViewById(R.id.tvCheckoutStep3PaymentTotalTitle);
		this.tvCheckoutStep3PaymentTotal = (TextView) findViewById(R.id.tvCheckoutStep3PaymentTotal);

		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/noteworthy.ttc");

		this.tvCheckoutStep1Title.setTypeface(tf);
		this.tvCheckoutStep2Title.setTypeface(tf);
		this.tvCheckoutStep3Title.setTypeface(tf);
		this.tvCheckoutStep4Title.setTypeface(tf);
		this.tvCheckoutStep3MainTitle.setTypeface(tf);
		this.tvCheckoutStep3NameTitle.setTypeface(tf);
		this.tvCheckoutStep3AddressTitle.setTypeface(tf);
		this.tvCheckoutStep3PhoneTitle.setTypeface(tf);
		this.tvCheckoutStep3NoteTitle.setTypeface(tf);
		this.tvCheckoutStep3ProductNameTitle.setTypeface(tf);
		this.tvCheckoutStep3ProductQuantityTitle.setTypeface(tf);
		this.tvCheckoutStep3ProductPriceTitle.setTypeface(tf);
		this.tvCheckoutStep3ProductTotalTitle.setTypeface(tf);
		this.tvCheckoutStep3PaymentMethod.setTypeface(tf);
		this.tvCheckoutStep3PaymentTotalTitle.setTypeface(tf);
		this.tvCheckoutStep3PaymentTotal.setTypeface(tf);

		this.loadData();
	}

	private void loadData() {
		this.loadUserInfo();
		this.loadTransaction();
		switch (this.paymentMethod) {
		case 1:
			this.tvCheckoutStep3PaymentMethod
					.setText(getText(R.string.checkout_step2_payment_type1));
			break;

		case 2:
			this.tvCheckoutStep3PaymentMethod
					.setText(getText(R.string.checkout_step2_payment_type2));
			break;
		case 3:
			this.tvCheckoutStep3PaymentMethod
					.setText(getText(R.string.checkout_step2_payment_type3));
			break;
		default:
			break;
		}

		for (TransactionDetailInfo transaction : this.listTransaction) {
			totalAmount += transaction.getQuantity()
					* transaction.getUnitPrice();
		}
		NumberFormat formatter = new DecimalFormat("#,###,###");
		this.tvCheckoutStep3PaymentTotal.setText(formatter.format(totalAmount)
				+ "đ");
	}

	private void loadUserInfo() {
		UserDB db = new UserDB(this);
		this.userInfo = db.getUser();
		if (this.userInfo != null) {
			this.tvCheckoutStep3Name.setText(this.userInfo.getName());
			this.tvCheckoutStep3Address.setText(this.userInfo.getAddress());
			this.tvCheckoutStep3Phone.setText(this.userInfo.getContactPhone());
			this.tvCheckoutStep3Note.setText(this.userInfo.getNote());
		}
	}

	private void loadTransaction() {
		TransactionDetailDB db = new TransactionDetailDB(this);
		try {
			this.listTransaction = db.getTransactions();
			this.adapter = new Checkout3TransactionAdapter(this,
					this.listTransaction);
			this.checkout3ListTransaction.setAdapter(adapter);
			this.checkout3ListTransaction.setDivider(null);
			this.checkout3ListTransaction.invalidate();
			((BaseAdapter) this.checkout3ListTransaction.getAdapter())
					.notifyDataSetChanged();
		} catch (Exception e) {
			Log.e("Checkout3Activity - loadTransaction", e.getMessage());
		}
	}

	public void btnCheckout3PaymentClick(View view) {
		TransactionPost transactionPost = new TransactionPost(
				this.listTransaction, this.userInfo, this.paymentMethod,
				this.totalAmount);
		if (WebService.isNetworkAvailable(this)) {
			WebService ws = new WebService(this);
			ws.setTransactionDetailRequest(transactionPost);
			ws.execute();
		}
	}

	public void btnCheckout3PreviousClick(View view) {
		onBackPressed();
	}

	@Override
	public void taskCompletionResult(JSONArray result) {
		try {
			if (result.getBoolean(0)) {
				Intent intent = new Intent(this, Checkout4Activity.class);
				startActivity(intent);
			} else {
				Toast.makeText(this,
						"Lỗi kết nối mạng\nXin vui lòng kiểm tra lại.",
						Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			Log.e("Checkout3Activity - taskCompletionResult", e.getMessage());
		}

	}
}
