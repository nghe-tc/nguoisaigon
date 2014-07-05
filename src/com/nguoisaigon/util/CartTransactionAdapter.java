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
		ViewHolder holder;
		if (convertView == null) {
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.fragment_cart_transaction, null);

			holder = new ViewHolder();
			holder.tvCartTransactionDeleteIndex = (TextView) view
					.findViewById(R.id.tvCartTransactionDeleteIndex);
			holder.tvCartTransactionPlusIndex = (TextView) view
					.findViewById(R.id.tvCartTransactionPlusIndex);
			holder.tvCartTransactionMunisIndex = (TextView) view
					.findViewById(R.id.tvCartTransactionMunisIndex);
			holder.tvCartProductName = (TextView) view
					.findViewById(R.id.tvCartProductName);
			holder.tvCartQuantity = (TextView) view
					.findViewById(R.id.tvCartTransactionQuantity);
			holder.tvCartPrice = (TextView) view
					.findViewById(R.id.tvCartTransactionPrice);
			holder.tvCartTotal = (TextView) view
					.findViewById(R.id.tvCartTransactionTotal);

			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		FrameLayout cartTransactionLayout = (FrameLayout) view
				.findViewById(R.id.cartTransactionLayout);
		if (this.listTransaction.size() > 0) {
			TransactionDetailInfo transaction = this.listTransaction
					.get(position);
			Typeface tf = Typeface.createFromAsset(this.activity.getAssets(),
					"fonts/noteworthy.ttc");

			holder.tvCartProductName.setTypeface(tf);
			holder.tvCartQuantity.setTypeface(tf);
			holder.tvCartPrice.setTypeface(tf);
			holder.tvCartTotal.setTypeface(tf);

			cartTransactionLayout.setVisibility(FrameLayout.VISIBLE);
			Double total = 0.0;
			NumberFormat formatter = new DecimalFormat("#,###,###");
			holder.tvCartTransactionDeleteIndex.setText(position + "");
			holder.tvCartTransactionPlusIndex.setText(position + "");
			holder.tvCartTransactionMunisIndex.setText(position + "");
			holder.tvCartProductName.setText(transaction.getProductName());
			holder.tvCartQuantity.setText(transaction.getQuantity() + "");
			holder.tvCartPrice.setText(formatter.format(transaction
					.getUnitPrice()));
			total = transaction.getQuantity() * transaction.getUnitPrice();
			holder.tvCartTotal.setText(formatter.format(total));
		}
		return view;
	}

	public static class ViewHolder {

		public TextView tvCartTransactionDeleteIndex;
		public TextView tvCartTransactionPlusIndex;
		public TextView tvCartTransactionMunisIndex;
		public TextView tvCartProductName;
		public TextView tvCartQuantity;
		public TextView tvCartPrice;
		public TextView tvCartTotal;

	}
}
