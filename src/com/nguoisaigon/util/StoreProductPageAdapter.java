package com.nguoisaigon.util;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.nguoisaigon.R;
import com.nguoisaigon.entity.ProductInfo;
import com.nguoisaigon.entity.StoreProductPageInfo;

@SuppressLint("SimpleDateFormat")
public class StoreProductPageAdapter extends BaseAdapter {

	private Activity activity;
	ArrayList<StoreProductPageInfo> listProduct;
	private static LayoutInflater inflater = null;

	public StoreProductPageAdapter(Activity activity, ArrayList<StoreProductPageInfo> listProduct) {
		this.activity = activity;
		this.listProduct = listProduct;
	}

	@Override
	public int getCount() {
		return this.listProduct.size();
	}

	@Override
	public StoreProductPageInfo getItem(int position) {
		return this.listProduct.get(position);
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

			inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.fragment_store_page, null);

			holder = new ViewHolder();
			holder.product1 = (FrameLayout) view.findViewById(R.id.product1);
			holder.product2 = (FrameLayout) view.findViewById(R.id.product2);
			holder.product3 = (FrameLayout) view.findViewById(R.id.product3);
			holder.product4 = (FrameLayout) view.findViewById(R.id.product4);

			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		if (this.listProduct.size() > 0) {

			holder.product1.setVisibility(FrameLayout.GONE);
			holder.product2.setVisibility(FrameLayout.GONE);
			holder.product3.setVisibility(FrameLayout.GONE);
			holder.product4.setVisibility(FrameLayout.GONE);

			ImageView mainPic = null;
			ImageView newIcon = null;
			ImageView salesIcon = null;
			TextView productId = null;
			// show product
			Integer index = 0;
			ArrayList<ProductInfo> products = this.listProduct.get(position).getListProducInfo();
			for (ProductInfo product : products) {
				index++;
				switch (index) {
				case 1:
					holder.product1.setVisibility(FrameLayout.VISIBLE);
					newIcon = (ImageView) view.findViewById(R.id.storeProduct1NewIcon);
					salesIcon = (ImageView) view.findViewById(R.id.storeProduct1SaleIcon);
					productId = (TextView) view.findViewById(R.id.tvProduct1Id);
					productId.setText(product.getProductId());
					mainPic = (ImageView) view.findViewById(R.id.storeProduct1);
					break;
				case 2:
					holder.product2.setVisibility(FrameLayout.VISIBLE);
					newIcon = (ImageView) view.findViewById(R.id.storeProduct2NewIcon);
					salesIcon = (ImageView) view.findViewById(R.id.storeProduct2SaleIcon);
					productId = (TextView) view.findViewById(R.id.tvProduct2Id);
					productId.setText(product.getProductId());
					mainPic = (ImageView) view.findViewById(R.id.storeProduct2);
					break;
				case 3:
					holder.product3.setVisibility(FrameLayout.VISIBLE);
					newIcon = (ImageView) view.findViewById(R.id.storeProduct3NewIcon);
					salesIcon = (ImageView) view.findViewById(R.id.storeProduct3SaleIcon);
					productId = (TextView) view.findViewById(R.id.tvProduct3Id);
					productId.setText(product.getProductId());
					mainPic = (ImageView) view.findViewById(R.id.storeProduct3);
					break;
				case 4:
					holder.product4.setVisibility(FrameLayout.VISIBLE);
					newIcon = (ImageView) view.findViewById(R.id.storeProduct4NewIcon);
					salesIcon = (ImageView) view.findViewById(R.id.storeProduct4SaleIcon);
					productId = (TextView) view.findViewById(R.id.tvProduct4Id);
					productId.setText(product.getProductId());
					mainPic = (ImageView) view.findViewById(R.id.storeProduct4);
					break;
				default:
					break;
				}

				if (newIcon != null && product.getIsNew() != 1) {
					newIcon.setVisibility(ImageView.GONE);
				}
				if (salesIcon != null && product.getIsSale() != 1) {
					salesIcon.setVisibility(ImageView.GONE);
				}

				if (product.getImageList().size() > 0) {
					new ImageLoadTask(product.getImageList().get(0), mainPic)
							.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
				}
			}
		}
		return view;
	}

	public static class ViewHolder {

		public FrameLayout product1;
		public FrameLayout product2;
		public FrameLayout product3;
		public FrameLayout product4;

	}
}
