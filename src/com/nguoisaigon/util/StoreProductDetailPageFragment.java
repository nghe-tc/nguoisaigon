package com.nguoisaigon.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nguoisaigon.R;
import com.nguoisaigon.activity.StoreMainActivity;
import com.nguoisaigon.dialog.ImageViewDialog;
import com.nguoisaigon.entity.ImageInfo;
import com.nguoisaigon.entity.ProductInfo;
import com.nguoisaigon.entity.TransactionDetailInfo;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
@SuppressLint({ "SimpleDateFormat", "ValidFragment" })
public class StoreProductDetailPageFragment extends Fragment {

	private ProductInfo product;
	private Context context;

	public StoreProductDetailPageFragment(Context context, ProductInfo product) {
		this.product = product;
		this.context = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.fragment_store_detail_page, container, false);

		final ImageView mainPic = (ImageView) rootView
				.findViewById(R.id.storeDetailProduct);

		LinearLayout ll = (LinearLayout) rootView.findViewById(R.id.imageGroup);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(110, 110);
		lp.setMargins(11, 15, 11, 0);
		for (final ImageInfo imageInfo : product.getImageList()) {
			ImageView image = new ImageView(getActivity());
			image.setLayoutParams(lp);
			image.setScaleType(ScaleType.FIT_XY);
			image.setImageBitmap(BitmapCache.getBitmapFromMemCache(imageInfo
					.getImageId()));
			image.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					mainPic.setImageBitmap(BitmapCache
							.getBitmapFromMemCache(imageInfo.getImageId()));
				}
			});
			ll.addView(image);
		}

		mainPic.setImageBitmap(BitmapCache.getBitmapFromMemCache(product
				.getImageList().get(0).getImageId()));
		mainPic.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				ImageViewDialog imageDialog = new ImageViewDialog(
						getActivity(), product.getImageList());
				imageDialog.createView();
				imageDialog.show();
				return false;
			}
		});

		TextView name = (TextView) rootView
				.findViewById(R.id.tvStoreDetailProductName);
		TextView description = (TextView) rootView
				.findViewById(R.id.tvStoreDetailProductDescription);
		ImageView hotTag = (ImageView) rootView
				.findViewById(R.id.storeDetailProductHotTag);
		ImageView newIcon = (ImageView) rootView
				.findViewById(R.id.storeDetailProductNewIcon);
		TextView sizeText = (TextView) rootView
				.findViewById(R.id.tvStoreDetailProductSizeText);
		TextView unitPrice = (TextView) rootView
				.findViewById(R.id.tvStoreDetailProductUnitPrice);
		TextView unitPriceText = (TextView) rootView
				.findViewById(R.id.tvStoreDetailProductUnitPriceText);
		ImageView salseIcon = (ImageView) rootView
				.findViewById(R.id.storeDetailProductSaleIcon);
		TextView quantityText = (TextView) rootView
				.findViewById(R.id.tvStoreDetailProductQuantityText);
		final TextView quantity = (TextView) rootView
				.findViewById(R.id.tvStoreDetailProductQuantity);
		ImageView btnQuantityPlus = (ImageView) rootView
				.findViewById(R.id.btnStoreDetailProductQuantityPlus);
		ImageView btnQuantityMinus = (ImageView) rootView
				.findViewById(R.id.btnStoreDetailProductQuantityMinus);
		TextView stockQuantity = (TextView) rootView
				.findViewById(R.id.tvStoreDetailProductStockQuantity);
		FrameLayout sizeLayout = (FrameLayout) rootView
				.findViewById(R.id.storeDetailProductSizeLayout);
		FrameLayout quantityLayout = (FrameLayout) rootView
				.findViewById(R.id.storeDetailProductQuantityLayout);

		Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
				"fonts/noteworthy.ttc");

		name.setTypeface(tf);
		description.setTypeface(tf);
		unitPrice.setTypeface(tf);
		unitPriceText.setTypeface(tf);
		sizeText.setTypeface(tf);
		quantityText.setTypeface(tf);
		quantity.setTypeface(tf);
		stockQuantity.setTypeface(tf);

		name.setText(product.getName());
		description.setText(product.getDescription());
		unitPrice.setText(product.getUnitPrice() + " đ");

		if (product.getSizeQtyList().size() > 0) {
			sizeLayout.setVisibility(FrameLayout.VISIBLE);
			quantityLayout.setVisibility(FrameLayout.GONE);

			int[] size = { R.id.storeDetailProductSizeXXS,
					R.id.storeDetailProductSizeXS,
					R.id.storeDetailProductSizeS, R.id.storeDetailProductSizeM,
					R.id.storeDetailProductSizeL, R.id.storeDetailProductSizeXL };
			for (int i = 0; i < product.getSizeQtyList().size(); i++) {
				ImageView imageSize = (ImageView) rootView
						.findViewById(size[product.getSizeQtyList().get(i)
								.getSizeType()]);
				imageSize.setClickable(true);
				imageSize.setFocusableInTouchMode(true);
				imageSize.setImageAlpha(255);
				imageSize.setContentDescription(product.getSizeQtyList().get(i)
						.getSizeType().toString());
				final int sizeSelected = i;
				imageSize
						.setOnFocusChangeListener(new View.OnFocusChangeListener() {
							public void onFocusChange(View v, boolean hasFocus) {
								if (hasFocus) {
									TransactionDetailInfo transaction = new TransactionDetailInfo();
									transaction.setCategoryId(product
											.getCategoryId());
									transaction.setProductId(product
											.getProductId());
									transaction.setProductName(product
											.getName());
									transaction.setQuantity(1);
									if (product.getSizeQtyList().size() > 0) {
										transaction.setSizeType(sizeSelected);
									}
									transaction.setUnitPrice(product
											.getUnitPrice());
									if (transaction.getQuantity() > 0) {
										StoreMainActivity
												.setProductTransactionDetailInfo(transaction);
									}
									System.out
											.println("-----------------------------");
									System.out.println(transaction
											.getProductId());
									System.out.println(transaction
											.getProductName());
									System.out.println(transaction
											.getCategoryId());
									System.out.println(transaction
											.getQuantity());
									System.out.println(transaction
											.getSizeType());
									System.out
											.println("-----------------------------");
								}
							}
						});
			}
		} else {
			sizeLayout.setVisibility(FrameLayout.GONE);
			quantityLayout.setVisibility(FrameLayout.VISIBLE);
			stockQuantity.setText("(Trong kho còn: " + product.getQuantity()
					+ ")");

			btnQuantityPlus.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					TransactionDetailInfo transaction = StoreMainActivity.getProductTransactionDetailInfo();
					if (transaction.getQuantity() < product.getQuantity()) {
						transaction.setQuantity(transaction.getQuantity() + 1);
					} else {
						String message = "Không đáp ứng đủ số lượng yêu cầu.\nChúng tôi chỉ còn ["
								+ product.getQuantity()
								+ "] sản phẩm trong kho";
						Toast.makeText(context, message, Toast.LENGTH_LONG)
								.show();
					}
					StoreMainActivity
							.setProductTransactionDetailInfo(transaction);
					System.out.println("-----------------------------");
					System.out.println(transaction.getProductId());
					System.out.println(transaction.getProductName());
					System.out.println(transaction.getCategoryId());
					System.out.println(transaction.getQuantity());
					System.out.println(transaction.getSizeType());
					System.out.println("-----------------------------");
					quantity.setText(transaction.getQuantity() + "");
				}
			});

			btnQuantityMinus.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					TransactionDetailInfo transaction = StoreMainActivity.getProductTransactionDetailInfo();
					if (transaction.getQuantity() > 1) {
						transaction.setQuantity(transaction.getQuantity() - 1);
					}
					transaction.setCategoryId(product.getCategoryId());
					transaction.setProductId(product.getProductId());
					transaction.setProductName(product.getName());
					transaction.setUnitPrice(product.getUnitPrice());
					StoreMainActivity
							.setProductTransactionDetailInfo(transaction);
					System.out.println("-----------------------------");
					System.out.println(transaction.getProductId());
					System.out.println(transaction.getProductName());
					System.out.println(transaction.getCategoryId());
					System.out.println(transaction.getQuantity());
					System.out.println(transaction.getSizeType());
					System.out.println("-----------------------------");
					quantity.setText(transaction.getQuantity() + "");
				}
			});
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
		mainPic.requestFocus();
		return rootView;
	}
}
