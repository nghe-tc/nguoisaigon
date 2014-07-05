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
public class Checkout3TransactionAdapter extends BaseAdapter {

	private Activity activity;
	ArrayList<TransactionDetailInfo> listTransaction;
	private static LayoutInflater inflater = null;

	public Checkout3TransactionAdapter(Activity activity,
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
		ViewHolder holder;
		if (convertView == null) {
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.fragment_checkout3_transaction, null);

			holder = new ViewHolder();
			holder.tvCheckout3ProductName = (TextView) view
					.findViewById(R.id.tvCheckout3ProductName);
			holder.tvCheckout3Quantity = (TextView) view
					.findViewById(R.id.tvCheckout3TransactionQuantity);
			holder.tvCheckout3Price = (TextView) view
					.findViewById(R.id.tvCheckout3TransactionPrice);
			holder.tvCheckout3Total = (TextView) view
					.findViewById(R.id.tvCheckout3TransactionTotal);

			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		FrameLayout checkout3TransactionLayout = (FrameLayout) view
				.findViewById(R.id.checkout3TransactionLayout);
		if (this.listTransaction.size() > 0) {
			TransactionDetailInfo transaction = this.listTransaction
					.get(position);
			Typeface tf = Typeface.createFromAsset(this.activity.getAssets(),
					"fonts/noteworthy.ttc");

			holder.tvCheckout3ProductName.setTypeface(tf);
			holder.tvCheckout3Quantity.setTypeface(tf);
			holder.tvCheckout3Price.setTypeface(tf);
			holder.tvCheckout3Total.setTypeface(tf);

			checkout3TransactionLayout.setVisibility(FrameLayout.VISIBLE);
			Double total = 0.0;
			NumberFormat formatter = new DecimalFormat("#,###,###");
			holder.tvCheckout3ProductName.setText(transaction.getProductName());
			holder.tvCheckout3Quantity.setText(transaction.getQuantity() + "");
			holder.tvCheckout3Price.setText(formatter.format(transaction
					.getUnitPrice()));
			total = transaction.getQuantity() * transaction.getUnitPrice();
			holder.tvCheckout3Total.setText(formatter.format(total));
		}
		return view;
	}

	public static class ViewHolder {

		public TextView tvCheckout3ProductName;
		public TextView tvCheckout3Quantity;
		public TextView tvCheckout3Price;
		public TextView tvCheckout3Total;

	}
}
