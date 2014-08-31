package com.nguoisaigon.activity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
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
import com.nguoisaigon.util.Utils;
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

		Log.i("Checkout3Activity - onCreate", "PAYMENT_METHOD: " + getIntent().getIntExtra("PAYMENT_METHOD", 1));
		paymentMethod = getIntent().getIntExtra("PAYMENT_METHOD", 1);

		tvCheckoutStep1Title = (TextView) findViewById(R.id.tvCheckoutStep1Title);
		tvCheckoutStep2Title = (TextView) findViewById(R.id.tvCheckoutStep2Title);
		tvCheckoutStep3Title = (TextView) findViewById(R.id.tvCheckoutStep3Title);
		tvCheckoutStep4Title = (TextView) findViewById(R.id.tvCheckoutStep4Title);

		tvCheckoutStep3MainTitle = (TextView) findViewById(R.id.tvCheckoutStep3MainTitle);
		tvCheckoutStep3NameTitle = (TextView) findViewById(R.id.tvCheckoutStep3NameTitle);
		tvCheckoutStep3Name = (TextView) findViewById(R.id.tvCheckoutStep3Name);
		tvCheckoutStep3AddressTitle = (TextView) findViewById(R.id.tvCheckoutStep3AddressTitle);
		tvCheckoutStep3Address = (TextView) findViewById(R.id.tvCheckoutStep3Address);
		tvCheckoutStep3PhoneTitle = (TextView) findViewById(R.id.tvCheckoutStep3PhoneTitle);
		tvCheckoutStep3Phone = (TextView) findViewById(R.id.tvCheckoutStep3Phone);
		tvCheckoutStep3NoteTitle = (TextView) findViewById(R.id.tvCheckoutStep3NoteTitle);
		tvCheckoutStep3Note = (TextView) findViewById(R.id.tvCheckoutStep3Note);
		tvCheckoutStep3ProductNameTitle = (TextView) findViewById(R.id.tvCheckoutStep3ProductNameTitle);
		tvCheckoutStep3ProductQuantityTitle = (TextView) findViewById(R.id.tvCheckoutStep3ProductQuantityTitle);
		tvCheckoutStep3ProductPriceTitle = (TextView) findViewById(R.id.tvCheckoutStep3ProductPriceTitle);
		tvCheckoutStep3ProductTotalTitle = (TextView) findViewById(R.id.tvCheckoutStep3ProductTotalTitle);
		checkout3ListTransaction = (ListView) findViewById(R.id.checkout3ListTransaction);
		tvCheckoutStep3PaymentMethod = (TextView) findViewById(R.id.tvCheckoutStep3PaymentMethod);
		tvCheckoutStep3PaymentTotalTitle = (TextView) findViewById(R.id.tvCheckoutStep3PaymentTotalTitle);
		tvCheckoutStep3PaymentTotal = (TextView) findViewById(R.id.tvCheckoutStep3PaymentTotal);

		tvCheckoutStep1Title.setTypeface(Utils.tf);
		tvCheckoutStep2Title.setTypeface(Utils.tf);
		tvCheckoutStep3Title.setTypeface(Utils.tf);
		tvCheckoutStep4Title.setTypeface(Utils.tf);
		tvCheckoutStep3MainTitle.setTypeface(Utils.tf);
		tvCheckoutStep3NameTitle.setTypeface(Utils.tf);
		tvCheckoutStep3AddressTitle.setTypeface(Utils.tf);
		tvCheckoutStep3PhoneTitle.setTypeface(Utils.tf);
		tvCheckoutStep3NoteTitle.setTypeface(Utils.tf);
		tvCheckoutStep3ProductNameTitle.setTypeface(Utils.tf);
		tvCheckoutStep3ProductQuantityTitle.setTypeface(Utils.tf);
		tvCheckoutStep3ProductPriceTitle.setTypeface(Utils.tf);
		tvCheckoutStep3ProductTotalTitle.setTypeface(Utils.tf);
		tvCheckoutStep3PaymentMethod.setTypeface(Utils.tf);
		tvCheckoutStep3PaymentTotalTitle.setTypeface(Utils.tf);
		tvCheckoutStep3PaymentTotal.setTypeface(Utils.tf);

		loadData();
	}

	private void loadData() {
		loadUserInfo();
		loadTransaction();
		switch (paymentMethod) {
		case 1:
			tvCheckoutStep3PaymentMethod.setText(getText(R.string.checkout_step2_payment_type1));
			break;

		case 2:
			tvCheckoutStep3PaymentMethod.setText(getText(R.string.checkout_step2_payment_type2));
			break;
		case 3:
			tvCheckoutStep3PaymentMethod.setText(getText(R.string.checkout_step2_payment_type3));
			break;
		default:
			break;
		}

		for (TransactionDetailInfo transaction : listTransaction) {
			totalAmount += transaction.getQuantity() * transaction.getUnitPrice();
		}
		NumberFormat formatter = new DecimalFormat("#,###,###");
		tvCheckoutStep3PaymentTotal.setText(formatter.format(totalAmount) + "đ");
	}

	private void loadUserInfo() {
		UserDB db = new UserDB(this);
		userInfo = db.getUser();
		if (userInfo != null) {
			tvCheckoutStep3Name.setText(userInfo.getName());
			tvCheckoutStep3Address.setText(userInfo.getAddress());
			tvCheckoutStep3Phone.setText(userInfo.getContactPhone());
			tvCheckoutStep3Note.setText(userInfo.getNote());
		}
	}

	private void loadTransaction() {
		TransactionDetailDB db = new TransactionDetailDB(this);
		try {
			listTransaction = db.getTransactions();
			adapter = new Checkout3TransactionAdapter(this, listTransaction);
			checkout3ListTransaction.setAdapter(adapter);
			checkout3ListTransaction.setDivider(null);
			checkout3ListTransaction.invalidate();
			((BaseAdapter) checkout3ListTransaction.getAdapter()).notifyDataSetChanged();
		} catch (Exception e) {
			Log.e("Checkout3Activity - loadTransaction", e.getMessage());
		}
	}

	public void btnCheckout3PaymentClick(View view) {
		TransactionPost transactionPost = new TransactionPost(listTransaction, userInfo, paymentMethod,
				totalAmount);
		if (WebService.isNetworkAvailable(this)) {
			WebService ws = new WebService(this);
			ws.setTransactionDetailRequest(transactionPost);
			ws.execute();
		}
	}

	public void btnCheckout3PreviousClick(View view) {
		finish();
	}

	@Override
	public void taskCompletionResult(JSONArray result) {
		Log.i("Checkout3Activity - taskCompletionResult", result.toString());
		try {
			JSONObject json = result.getJSONObject(0);
			if (json.getInt("returnCode") == 0) {
				TransactionDetailDB db = new TransactionDetailDB(this);
				db.deleteAll();
				Utils.isUnbindDrawables = false;
				Intent intent = new Intent(this, Checkout4Activity.class);
				startActivity(intent);
			} else {
				Toast.makeText(this, json.getString("message"), Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			Log.e("Checkout3Activity - taskCompletionResult", e.getMessage());
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		Utils.isUnbindDrawables = true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (Utils.isUnbindDrawables) {
			Utils.unbindDrawables(findViewById(R.id.container));
		}
		System.gc();
	}
}
