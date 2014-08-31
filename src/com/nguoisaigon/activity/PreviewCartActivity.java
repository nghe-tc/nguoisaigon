package com.nguoisaigon.activity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.nguoisaigon.R;
import com.nguoisaigon.db.TransactionDetailDB;
import com.nguoisaigon.entity.TransactionDetailInfo;
import com.nguoisaigon.util.CartTransactionAdapter;
import com.nguoisaigon.util.Utils;

public class PreviewCartActivity extends Activity {
	private TextView tvCartProductTitle;
	private TextView tvCartQuantityTitle;
	private TextView tvCartPriceTitle;
	private TextView tvCartTotalTitle;
	private ListView cartListTransaction;
	private TextView tvCartTotalText;
	private TextView tvCartTotal;
	private TransactionDetailDB db;

	private ArrayList<TransactionDetailInfo> listTransaction = new ArrayList<TransactionDetailInfo>();;
	private CartTransactionAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i("PreviewCartActivity - onCreate", "Start");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preview_cart_layout);
		tvCartProductTitle = (TextView) findViewById(R.id.tvCartProductTitle);
		tvCartQuantityTitle = (TextView) findViewById(R.id.tvCartQuantityTitle);
		tvCartPriceTitle = (TextView) findViewById(R.id.tvCartPriceTitle);
		tvCartTotalTitle = (TextView) findViewById(R.id.tvCartTotalTitle);
		cartListTransaction = (ListView) findViewById(R.id.cartListTransaction);
		tvCartTotalText = (TextView) findViewById(R.id.tvCartTotalText);
		tvCartTotal = (TextView) findViewById(R.id.tvCartTotal);

		tvCartProductTitle.setTypeface(Utils.tf);
		tvCartQuantityTitle.setTypeface(Utils.tf);
		tvCartPriceTitle.setTypeface(Utils.tf);
		tvCartTotalTitle.setTypeface(Utils.tf);
		tvCartTotalText.setTypeface(Utils.tf);
		tvCartTotal.setTypeface(Utils.tf);

		db = new TransactionDetailDB(this);
		loadData();
	}

	private void loadData() {
		Log.i("PreviewCartActivity - loadData", "Start");
		listTransaction.clear();
		try {
			listTransaction = db.getTransactions();
			Log.i("PreviewCartActivity - loadData", "num of transaction: " + listTransaction.size());
			adapter = new CartTransactionAdapter(this, listTransaction);
			cartListTransaction.setAdapter(adapter);
			cartListTransaction.setDivider(null);
			cartListTransaction.invalidate();
			((BaseAdapter) cartListTransaction.getAdapter()).notifyDataSetChanged();

			Double total = 0.0;
			for (TransactionDetailInfo transaction : listTransaction) {
				total += transaction.getQuantity() * transaction.getUnitPrice();
			}
			NumberFormat formatter = new DecimalFormat("#,###,###");
			tvCartTotal.setText(formatter.format(total) + "đ");

		} catch (Exception ex) {
			Log.e("PreviewCartActivity - loadData", ex.getMessage());
		}
	}

	public void cartTransactionDeleteClick(View view) {
		Log.i("PreviewCartActivity - cartTransactionDeleteClick", "Start");
		TextView tvCartTransactionDeleteIndex = (TextView) view.findViewById(R.id.tvCartTransactionDeleteIndex);
		Integer index = Integer.parseInt(tvCartTransactionDeleteIndex.getText().toString());
		db.delete(listTransaction.get(index).getId());
		if (listTransaction.size() == 1) {
			ImageView cartCheckout = (ImageView) findViewById(R.id.btnCartCheckout);
			cartCheckout.setImageAlpha(70);
		}
		loadData();
	}

	public void cartTransactionPlusClick(View view) {
		Log.i("PreviewCartActivity - cartTransactionPlusClick", "Start");
		TextView tvCartTransactionPlusIndex = (TextView) view.findViewById(R.id.tvCartTransactionPlusIndex);
		Integer index = Integer.parseInt(tvCartTransactionPlusIndex.getText().toString());
		Log.i("PreviewCartActivity - cartTransactionPlusClick", "index: " + index);
		TransactionDetailInfo transaction = listTransaction.get(index);
		Log.i("PreviewCartActivity - cartTransactionPlusClick", "Quantity: " + transaction.getQuantity());
		transaction.setQuantity(transaction.getQuantity() + 1);
		Log.i("PreviewCartActivity - cartTransactionPlusClick", "Quantity plus: " + transaction.getQuantity());
		db.update(transaction);
		loadData();
	}

	public void cartTransactionMunisClick(View view) {
		Log.i("PreviewCartActivity - cartTransactionMunisClick", "Start");
		TextView tvCartTransactionMunisIndex = (TextView) view.findViewById(R.id.tvCartTransactionMunisIndex);
		Integer index = Integer.parseInt(tvCartTransactionMunisIndex.getText().toString());
		Log.i("PreviewCartActivity - cartTransactionMunisClick", "index: " + index);
		TransactionDetailInfo transaction = listTransaction.get(index);
		if (transaction.getQuantity() > 1) {
			Log.i("PreviewCartActivity - cartTransactionMunisClick", "Quantity: " + transaction.getQuantity());
			transaction.setQuantity(transaction.getQuantity() - 1);
			Log.i("PreviewCartActivity - cartTransactionMunisClick", "Quantity munis: " + transaction.getQuantity());
			db.update(transaction);
			loadData();
		}
	}

	public void btnBackClick(View view) {
		finish();
	}

	public void btnCloseClick(View view) {
		finish();
	}

	public void btnCartCheckoutClick(View view) {
		if (listTransaction.size() > 0) {
			Utils.isUnbindDrawables = false;
			Intent intent = new Intent(this, Checkout1Activity.class);
			startActivity(intent);
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
