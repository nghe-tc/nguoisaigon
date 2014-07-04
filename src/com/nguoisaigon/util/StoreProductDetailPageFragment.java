package com.nguoisaigon.util;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import com.nguoisaigon.R;
import com.nguoisaigon.entity.ImageInfo;
import com.nguoisaigon.entity.ProductInfo;

@SuppressLint("SimpleDateFormat")
public class StoreProductDetailPageFragment extends Fragment {

	public static final String LIST_PRODUCT_INFO = "LIST_PRODUCT_INFO";

	public static final StoreProductDetailPageFragment newInstance(ProductInfo product) {
		StoreProductDetailPageFragment f = new StoreProductDetailPageFragment();
		Bundle bdl = new Bundle(1);
		String jsonListProduct = new Gson().toJson(product);
		bdl.putString(LIST_PRODUCT_INFO, jsonListProduct);
		f.setArguments(bdl);
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_store_detail_page, container, false);

		String jsonProduct = getArguments().getString(LIST_PRODUCT_INFO);
		Log.i("StoreProductDetailPageFragment - onCreateView", "json product: " + jsonProduct);

		ProductInfo product = new ProductInfo();
		product = new Gson().fromJson(jsonProduct, ProductInfo.class);

		final ImageView mainPic = (ImageView) rootView.findViewById(R.id.storeDetailProduct);

		LinearLayout ll = (LinearLayout) rootView.findViewById(R.id.imageGroup);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(110, 110);
		lp.setMargins(11, 15, 11, 0);
		for (final ImageInfo imageInfo : product.getImageList()) {
			ImageView image = new ImageView(getActivity());
			image.setLayoutParams(lp);
			image.setScaleType(ScaleType.FIT_XY);
			image.setImageBitmap(BitmapCache.getBitmapFromMemCache(imageInfo.getImageId()));
			image.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					mainPic.setImageBitmap(BitmapCache.getBitmapFromMemCache(imageInfo.getImageId()));
				}
			});
			ll.addView(image);
		}

		mainPic.setImageBitmap(BitmapCache.getBitmapFromMemCache(product.getImageList().get(0).getImageId()));

		TextView name = (TextView) rootView.findViewById(R.id.tvStoreDetailProductName);
		TextView description = (TextView) rootView.findViewById(R.id.tvStoreDetailProductDescription);
		ImageView hotTag = (ImageView) rootView.findViewById(R.id.storeDetailProductHotTag);
		ImageView newIcon = (ImageView) rootView.findViewById(R.id.storeDetailProductNewIcon);
		TextView sizeText = (TextView) rootView.findViewById(R.id.tvStoreDetailProductSizeText);
		ImageView sizeXXS = (ImageView) rootView.findViewById(R.id.storeDetailProductSizeXXS);
		ImageView sizeXS = (ImageView) rootView.findViewById(R.id.storeDetailProductSizeXS);
		ImageView sizeS = (ImageView) rootView.findViewById(R.id.storeDetailProductSizeS);
		ImageView sizeM = (ImageView) rootView.findViewById(R.id.storeDetailProductSizeM);
		ImageView sizeL = (ImageView) rootView.findViewById(R.id.storeDetailProductSizeL);
		ImageView sizeXL = (ImageView) rootView.findViewById(R.id.storeDetailProductSizeXL);
		TextView unitPrice = (TextView) rootView.findViewById(R.id.tvStoreDetailProductUnitPrice);
		TextView unitPriceText = (TextView) rootView.findViewById(R.id.tvStoreDetailProductUnitPriceText);
		ImageView salseIcon = (ImageView) rootView.findViewById(R.id.storeDetailProductSaleIcon);
		TextView quantityText = (TextView) rootView.findViewById(R.id.tvStoreDetailProductQuantityText);
		TextView quantity = (TextView) rootView.findViewById(R.id.tvStoreDetailProductQuantity);
		FrameLayout sizeLayout = (FrameLayout) rootView.findViewById(R.id.storeDetailProductSizeLayout);
		FrameLayout quantityLayout = (FrameLayout) rootView.findViewById(R.id.storeDetailProductQuantityLayout);

		Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/wg_legacy_edition.ttf");

		name.setTypeface(tf);
		description.setTypeface(tf);
		unitPrice.setTypeface(tf);
		unitPriceText.setTypeface(tf);
		sizeText.setTypeface(tf);
		quantityText.setTypeface(tf);
		quantity.setTypeface(tf);

		name.setText(product.getName());
		description.setText(product.getDescription());
		unitPrice.setText(product.getUnitPrice() + " đ");

		if (product.getSizeQtyList().size() > 0) {
			sizeLayout.setVisibility(FrameLayout.VISIBLE);
			quantityLayout.setVisibility(FrameLayout.GONE);
			for (int i = 0; i < product.getSizeQtyList().size(); i++) {
				switch (i) {
				case 0:
					sizeXXS.setClickable(true);
					sizeXXS.setFocusableInTouchMode(true);
					sizeXXS.setImageAlpha(255);
					sizeXXS.setContentDescription(product.getSizeQtyList().get(i).getSizeType().toString());
					break;

				case 1:
					sizeXS.setClickable(true);
					sizeXS.setFocusableInTouchMode(true);
					sizeXS.setImageAlpha(255);
					sizeXS.setContentDescription(product.getSizeQtyList().get(i).getSizeType().toString());
					break;

				case 2:
					sizeS.setClickable(true);
					sizeS.setFocusableInTouchMode(true);
					sizeS.setImageAlpha(255);
					sizeS.setContentDescription(product.getSizeQtyList().get(i).getSizeType().toString());
					break;

				case 3:
					sizeM.setClickable(true);
					sizeM.setFocusableInTouchMode(true);
					sizeM.setImageAlpha(255);
					sizeM.setContentDescription(product.getSizeQtyList().get(i).getSizeType().toString());
					break;

				case 4:
					sizeL.setClickable(true);
					sizeL.setFocusableInTouchMode(true);
					sizeL.setImageAlpha(255);
					sizeL.setContentDescription(product.getSizeQtyList().get(i).getSizeType().toString());
					break;

				case 5:
					sizeXL.setClickable(true);
					sizeXL.setFocusableInTouchMode(true);
					sizeXL.setImageAlpha(255);
					sizeXL.setContentDescription(product.getSizeQtyList().get(i).getSizeType().toString());
					break;

				default:
					break;
				}
			}
		} else {
			sizeLayout.setVisibility(FrameLayout.GONE);
			quantityLayout.setVisibility(FrameLayout.VISIBLE);
		}

		if (product.getIsHot() < 1) {
			hotTag.setVisibility(ImageView.GONE);
		}

		if (product.getIsNew() < 1) {
			newIcon.setVisibility(ImageView.GONE);
		}

		if (product.getIsSale() < 1) {
			salseIcon.setVisibility(ImageView.GONE);
		}

		return rootView;
	}
}
