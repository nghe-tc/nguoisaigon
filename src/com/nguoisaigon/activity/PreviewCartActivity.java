package com.nguoisaigon.activity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.nguoisaigon.R;
import com.nguoisaigon.db.TransactionDetailDB;
import com.nguoisaigon.entity.TransactionDetailInfo;
import com.nguoisaigon.util.CartTransactionAdapter;

public class PreviewCartActivity extends Activity {
	private TextView tvCartProductTitle;
	private TextView tvCartQuantityTitle;
	private TextView tvCartPriceTitle;
	private TextView tvCartTotalTitle;
	private ListView cartListTransaction;
	private TextView tvCartTotalText;
	private TextView tvCartTotal;
	private TransactionDetailDB db;

	private ArrayList<TransactionDetailInfo> listTransaction;
	private CartTransactionAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i("PreviewCartActivity - onCreate", "Start");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preview_cart_layout);
		this.tvCartProductTitle = (TextView) findViewById(R.id.tvCartProductTitle);
		this.tvCartQuantityTitle = (TextView) findViewById(R.id.tvCartQuantityTitle);
		this.tvCartPriceTitle = (TextView) findViewById(R.id.tvCartPriceTitle);
		this.tvCartTotalTitle = (TextView) findViewById(R.id.tvCartTotalTitle);
		this.cartListTransaction = (ListView) findViewById(R.id.cartListTransaction);
		this.tvCartTotalText = (TextView) findViewById(R.id.tvCartTotalText);
		this.tvCartTotal = (TextView) findViewById(R.id.tvCartTotal);

		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/noteworthy.ttc");

		this.tvCartProductTitle.setTypeface(tf);
		this.tvCartQuantityTitle.setTypeface(tf);
		this.tvCartPriceTitle.setTypeface(tf);
		this.tvCartTotalTitle.setTypeface(tf);
		this.tvCartTotalText.setTypeface(tf);
		this.tvCartTotal.setTypeface(tf);

		this.listTransaction = new ArrayList<TransactionDetailInfo>();
		this.db = new TransactionDetailDB(this);
		for (int i = 1; i < 5; i++) {
			TransactionDetailInfo info = new TransactionDetailInfo();
			info.setAddedDate(Calendar.getInstance().getTime());
			info.setCategoryId(i);
			info.setProductId("anghghhghgh1242314532");
			info.setProductName("Sản phẩm thứ 00" + (i + 1));
			info.setQuantity(i);
			info.setSizeType(i);
			info.setStockQuantity(i);
			info.setUnitPrice(1000000.0 * i);
			//this.db.insert(info);
		}
		this.loadData();
	}

	private void loadData() {
		Log.i("PreviewCartActivity - loadData", "Start");
		this.listTransaction.clear();
		try {
			this.listTransaction = this.db.getTransactions();
			Log.i("PreviewCartActivity - loadData", "num of transaction: "
					+ this.listTransaction.size());
			this.adapter = new CartTransactionAdapter(this,
					this.listTransaction);
			this.cartListTransaction.setAdapter(adapter);
			this.cartListTransaction.setDivider(null);
			this.cartListTransaction.invalidate();
			((BaseAdapter) this.cartListTransaction.getAdapter()).notifyDataSetChanged();

			Double total = 0.0;
			for (TransactionDetailInfo transaction : this.listTransaction) {
				total += transaction.getQuantity() * transaction.getUnitPrice();
			}
			NumberFormat formatter = new DecimalFormat("#,###,###");
			this.tvCartTotal.setText(formatter.format(total) + "đ");

		} catch (Exception ex) {
			Log.e("PreviewCartActivity - loadData", ex.getMessage());
		}
	}

	public void cartTransactionDeleteClick(View view) {
		Log.i("PreviewCartActivity - cartTransactionDeleteClick", "Start");
		TextView tvCartTransactionDeleteIndex = (TextView) view
				.findViewById(R.id.tvCartTransactionDeleteIndex);
		Integer index = Integer.parseInt(tvCartTransactionDeleteIndex.getText()
				.toString());
		this.db.delete(this.listTransaction.get(index).getId());
		this.loadData();
	}

	public void cartTransactionPlusClick(View view) {
		Log.i("PreviewCartActivity - cartTransactionPlusClick", "Start");
		TextView tvCartTransactionPlusIndex = (TextView) view
				.findViewById(R.id.tvCartTransactionPlusIndex);
		Integer index = Integer.parseInt(tvCartTransactionPlusIndex.getText()
				.toString());
		Log.i("PreviewCartActivity - cartTransactionPlusClick", "index: " + index);
		TransactionDetailInfo transaction = this.listTransaction.get(index);
		Log.i("PreviewCartActivity - cartTransactionPlusClick", "Quantity: " + transaction.getQuantity());
		transaction.setQuantity(transaction.getQuantity() + 1);
		Log.i("PreviewCartActivity - cartTransactionPlusClick", "Quantity plus: " + transaction.getQuantity());
		this.db.update(transaction);
		this.loadData();
	}

	public void cartTransactionMunisClick(View view) {
		Log.i("PreviewCartActivity - cartTransactionMunisClick", "Start");
		TextView tvCartTransactionMunisIndex = (TextView) view
				.findViewById(R.id.tvCartTransactionMunisIndex);
		Integer index = Integer.parseInt(tvCartTransactionMunisIndex.getText()
				.toString());
		Log.i("PreviewCartActivity - cartTransactionMunisClick", "index: " + index);
		TransactionDetailInfo transaction = this.listTransaction.get(index);
		if (transaction.getQuantity() > 1) {
			Log.i("PreviewCartActivity - cartTransactionMunisClick", "Quantity: " + transaction.getQuantity());
			transaction.setQuantity(transaction.getQuantity() - 1);
			Log.i("PreviewCartActivity - cartTransactionMunisClick", "Quantity munis: " + transaction.getQuantity());
			this.db.update(transaction);
			this.loadData();
		}
	}

	public void btnBackClick(View view) {
		Intent intent = new Intent(this, StoreMainActivity.class);
		startActivity(intent);
	}

	public void btnCloseClick(View view) {
		this.finish();
	}

	public void btnCartCheckoutClick(View view) {
		Intent intent = new Intent(this, Checkout1Activity.class);
		startActivity(intent);
	}
}
