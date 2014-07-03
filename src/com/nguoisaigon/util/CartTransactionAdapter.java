package com.nguoisaigon.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.nguoisaigon.R;
import com.nguoisaigon.entity.TransactionDetailInfo;

@SuppressLint("SimpleDateFormat")
public class CartTransactionAdapter extends BaseAdapter {

	private Activity activity;
	ArrayList<TransactionDetailInfo> listTransaction;
	private static LayoutInflater inflater = null;

	public CartTransactionAdapter(Activity activity,
			ArrayList<TransactionDetailInfo> listTransaction) {
		this.activity = activity;
		this.listTransaction = listTransaction;
	}

	@Override
	public int getCount() {
		return this.listTransaction.size();
	}

	@Override
	public TransactionDetailInfo getItem(int position) {
		return this.listTransaction.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;

		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.fragment_cart_transaction, null);

		FrameLayout cartTransactionLayout = (FrameLayout) view
				.findViewById(R.id.cartTransactionLayout);
		TextView tvCartTransactionDeleteIndex = (TextView) view
				.findViewById(R.id.tvCartTransactionDeleteIndex);
		TextView tvCartTransactionPlusIndex = (TextView) view
				.findViewById(R.id.tvCartTransactionPlusIndex);
		TextView tvCartTransactionMunisIndex = (TextView) view
				.findViewById(R.id.tvCartTransactionMunisIndex);
		TextView tvCartProductName = (TextView) view
				.findViewById(R.id.tvCartProductName);
		TextView tvCartQuantity = (TextView) view
				.findViewById(R.id.tvCartTransactionQuantity);
		TextView tvCartPrice = (TextView) view
				.findViewById(R.id.tvCartTransactionPrice);
		TextView tvCartTotal = (TextView) view
				.findViewById(R.id.tvCartTransactionTotal);

		Typeface tf = Typeface.createFromAsset(this.activity.getAssets(),
				"fonts/wg_legacy_edition.ttf");

		tvCartProductName.setTypeface(tf);
		tvCartQuantity.setTypeface(tf);
		tvCartPrice.setTypeface(tf);
		tvCartTotal.setTypeface(tf);

		cartTransactionLayout.setVisibility(FrameLayout.VISIBLE);
		Double total = 0.0;
		NumberFormat formatter = new DecimalFormat("#,###,###");
		tvCartTransactionDeleteIndex.setText(position);
		tvCartTransactionPlusIndex.setText(position);
		tvCartTransactionMunisIndex.setText(position);
		tvCartProductName.setText(this.listTransaction.get(position)
				.getProductName());
		tvCartQuantity
				.setText(this.listTransaction.get(position).getQuantity());
		tvCartPrice.setText(formatter.format(this.listTransaction.get(position)
				.getUnitPrice()));
		total = this.listTransaction.get(position).getQuantity()
				* this.listTransaction.get(position).getUnitPrice();
		tvCartTotal.setText(formatter.format(total));
		return view;
	}
}
