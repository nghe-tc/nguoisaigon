package com.nguoisaigon.activity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.nguoisaigon.R;
import com.nguoisaigon.db.TransactionDetailDB;
import com.nguoisaigon.entity.ImageInfo;
import com.nguoisaigon.entity.ProductInfo;
import com.nguoisaigon.entity.StoreProductPageInfo;
import com.nguoisaigon.entity.TransactionDetailInfo;
import com.nguoisaigon.util.BitmapCache;
import com.nguoisaigon.util.CustomPagerAdapter;
import com.nguoisaigon.util.Emailplugin;
import com.nguoisaigon.util.ImageLoadTask;
import com.nguoisaigon.util.StoreProductDetailPageFragment;
import com.nguoisaigon.util.StoreProductPageAdapter;
import com.nguoisaigon.util.Utils;
import com.nguoisaigon.util.WebService;
import com.nguoisaigon.util.WebService.WebServiceDelegate;
import com.nguoisaigon.util.WebService.productCategory;
import com.nguoisaigon.util.WebService.productSearchType;

@SuppressLint("UseSparseArrays")
public class StoreMainActivity extends FragmentActivity implements WebServiceDelegate {
	private static final int[] type = { R.id.menuStoreFashionMan, R.id.menuStoreLifeStyle, R.id.menuStoreFood,
			R.id.menuStoreCosmeticMan, R.id.menuStoreCosmeticWoman, R.id.menuStoreFashionWoman,
			R.id.menuStoreFashionKid };

	private static final int[] typeClick = { R.drawable.storemenu_fashion_man_clicked,
			R.drawable.storemenu_lifestyle_clicked, R.drawable.storemenu_food_clicked,
			R.drawable.storemenu_cosmetic_man_clicked, R.drawable.storemenu_cosmetic_woman_clicked,
			R.drawable.storemenu_fashion_woman_clicked, R.drawable.storemenu_fashion_kid_clicked };

	private static final int[] typeNormal = { R.drawable.storemenu_fashion_man_normal,
			R.drawable.storemenu_lifestyle_normal, R.drawable.storemenu_food_normal,
			R.drawable.storemenu_cosmetic_man_normal, R.drawable.storemenu_cosmetic_woman_normal,
			R.drawable.storemenu_fashion_woman_normal, R.drawable.storemenu_fashion_kid_normal };

	private static TransactionDetailInfo productTransactionDetailInfo = new TransactionDetailInfo();

	/**
	 * @return the productTransactionDetailInfo
	 */
	public static TransactionDetailInfo getProductTransactionDetailInfo() {
		return productTransactionDetailInfo;
	}

	/**
	 * @param productTransactionDetailInfo
	 *            the productTransactionDetailInfo to set
	 */
	public static void setProductTransactionDetailInfo(TransactionDetailInfo productTransactionDetailInfo) {
		StoreMainActivity.productTransactionDetailInfo = productTransactionDetailInfo;
	}

	/**
	 * The pager widget, which handles animation and allows swiping horizontally
	 * to access previous and next wizard steps.
	 */
	private ViewPager mPager;

	/**
	 * The pager adapter, which provides the pages to the view pager widget.
	 */
	private PagerAdapter mPagerAdapter;

	private ListView storeMainListViewProduct;
	private StoreProductPageAdapter storeMainProductAdapter;

	private ArrayList<ProductInfo> listProduct;
	private HashMap<String, Integer> hsProduct;
	private FrameLayout storeProduct;
	private FrameLayout storeProductDetail;

	private TextView tvStoreCart;

	private TextView textLoading;
	private ProgressBar downloading;
	private TextView textNoItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i("StoreMainActivity - onCreate", "Start");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.store_main_layout);

		BitmapCache.createBitmapCache();

		this.storeProduct = (FrameLayout) findViewById(R.id.storeMain);
		this.storeProductDetail = (FrameLayout) findViewById(R.id.storeDetail);
		storeProductDetail.setVisibility(FrameLayout.GONE);
		this.hsProduct = new HashMap<String, Integer>();
		this.listProduct = new ArrayList<ProductInfo>();
		this.storeMainListViewProduct = (ListView) findViewById(R.id.storeMainListProduct);

		textLoading = (TextView) findViewById(R.id.tvLoading);
		textLoading.setTypeface(Utils.tf);
		downloading = (ProgressBar) findViewById(R.id.downloadIndicator);
		textNoItem = (TextView) findViewById(R.id.tvNoItem);
		textNoItem.setTypeface(Utils.tf);

		menuStoreFashionManClick(null);
		for (int i = 0; i < type.length; i++) {
			final ImageView image = (ImageView) findViewById(type[i]);
			if (i == 0) {
				image.setImageResource(typeClick[i]);
			}
			final int resourceId = i;
			image.setOnTouchListener(new View.OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent arg1) {
					System.out.println("========== " + resourceId);
					image.setImageResource(typeClick[resourceId]);
					for (int i = 0; i < type.length; i++) {
						final ImageView image = (ImageView) findViewById(type[i]);
						if (i != resourceId) {
							image.setImageResource(typeNormal[i]);
						}
					}
					return false;
				}
			});
		}
		this.updateStoreCart();
	}

	/**
	 * Show downloading indicator
	 */
	private void showDownloadingIndicator() {
		textLoading.setVisibility(View.VISIBLE);
		downloading.setVisibility(View.VISIBLE);
		textNoItem.setVisibility(View.GONE);
	}

	/**
	 * Hide downloading indicator
	 */
	private void hideDownloadingIndicator() {
		textLoading.setVisibility(View.GONE);
		downloading.setVisibility(View.GONE);
	}

	public void loadData(productCategory category, productSearchType searchType) {
		btnStoreDetailBackClick(null);
		showDownloadingIndicator();
		Log.i("StoreMainActivity - loadData", "Start");
		if (storeMainProductAdapter != null) {
			this.storeMainProductAdapter = new StoreProductPageAdapter(this, new ArrayList<StoreProductPageInfo>());
			storeMainListViewProduct.setAdapter(storeMainProductAdapter);
			storeMainProductAdapter.notifyDataSetChanged();
		}
		// Download data
		if (WebService.isNetworkAvailable(this)) {
			WebService ws = new WebService(this);
			ws.setGettingProducts(category, searchType);
			ws.execute();
		} else {
			hideDownloadingIndicator();
		}
	}

	@Override
	public void taskCompletionResult(JSONArray result) {
		Log.i("StoreMainActivity - taskCompletionResult", "Start");
		Log.i("StoreMainActivity - taskCompletionResult",
				"JSONArray result: " + ((result == null) ? "null" : result.toString()));
		if (result != null) {
			this.listProduct.clear();
			this.hsProduct.clear();
			Log.i("StoreMainActivity - taskCompletionResult", "result's length: " + result.length());
			for (int i = 0; i < result.length(); i++) {
				try {
					ProductInfo info = new Gson().fromJson(result.getString(i), ProductInfo.class);
					// Get all image for product
					for (ImageInfo imageInfo : info.getImageList()) {
						new ImageLoadTask(imageInfo).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
					}
					this.listProduct.add(info);
					this.hsProduct.put(info.getProductId(), i);
				} catch (Exception e) {
					Log.e("StoreMainActivity - Get Product", e.getMessage());
				}
			}

			if (result.length() == 0) {
				hideDownloadingIndicator();
				textNoItem.setVisibility(View.VISIBLE);
				return;
			}
			Log.i("StoreMainActivity - taskCompletionResult", "size of listProduct: " + listProduct.size());
		}
		updateData();
	}

	private void updateData() {
		Log.i("StoreMainActivity - updateData", "Start");
		ArrayList<StoreProductPageInfo> fragments = getProductFragments();
		this.storeMainProductAdapter = new StoreProductPageAdapter(this, fragments);
		this.storeMainListViewProduct.setAdapter(storeMainProductAdapter);
		this.storeMainListViewProduct.setDivider(null);
		Log.i("StoreMainActivity - updateData", "Num of page: " + fragments.size());
	}

	private ArrayList<StoreProductPageInfo> getProductFragments() {
		Log.i("StoreMainActivity - getProductFragments", "Start");
		ArrayList<StoreProductPageInfo> fList = new ArrayList<StoreProductPageInfo>();
		StoreProductPageInfo pageInfo = new StoreProductPageInfo();
		Integer index = 0;
		if (listProduct.size() > 8) {
			for (ProductInfo product : this.listProduct) {
				index++;
				pageInfo.addProduct(product);
				if (index == 8) {
					fList.add(pageInfo);
					pageInfo = new StoreProductPageInfo();
				} else if (index == this.listProduct.size()) {
					fList.add(pageInfo);
				}
			}
		} else {
			for (ProductInfo product : this.listProduct) {
				index++;
				pageInfo.addProduct(product);
				if (index == this.listProduct.size()) {
					fList.add(pageInfo);
				}
			}
		}

		Log.i("StoreMainActivity - getFragments", "size of list fragment: " + fList.size());
		return fList;
	}

	public void menuStoreFashionManClick(View view) {
		loadData(productCategory.cat_fashion_man, productSearchType.search_for_client);
	}

	public void menuStoreFashionWomanClick(View view) {
		loadData(productCategory.cat_fashion_woman, productSearchType.search_for_client);
	}

	public void menuStoreFashionKidClick(View view) {
		loadData(productCategory.cat_fashion_kid, productSearchType.search_for_client);
	}

	public void menuStoreCosmeticManClick(View view) {
		loadData(productCategory.cat_cos_man, productSearchType.search_for_client);
	}

	public void menuStoreCosmeticWomanClick(View view) {
		loadData(productCategory.cat_cos_woman, productSearchType.search_for_client);
	}

	public void menuStoreLifeStyleClick(View view) {
		loadData(productCategory.cat_lifeStyle, productSearchType.search_for_client);
	}

	public void menuStoreFoodClick(View view) {
		loadData(productCategory.cat_food, productSearchType.search_for_client);
	}

	public void btnCloseClick(View view) {
		this.finish();
	}

	public void btnStoreCartClick(View view) {
		Utils.isUnbindDrawables = false;
		TransactionDetailDB db = new TransactionDetailDB(this);
		try {
			if (db.getTransactions().size() > 0) {
				Intent intent = new Intent(this, PreviewCartActivity.class);
				startActivity(intent);
			} else {
				Toast.makeText(this, "Không có sản phẩm trong giỏ hàng", Toast.LENGTH_SHORT).show();
			}
		} catch (ParseException e) {
			Log.e("StoreMainActivity - btnStoreCartClick", e.getMessage());
		}
	}

	public void btnStoreDetailBackClick(View view) {
		this.storeProduct.setVisibility(FrameLayout.VISIBLE);
		this.storeProductDetail.setVisibility(FrameLayout.GONE);
	}

	public void btnStoreDetailEmailClick(View view) {
		Utils.isUnbindDrawables = false;
		Emailplugin.SendEmailFromStoreView(this, listProduct.get(mPager.getCurrentItem()));
	}

	public void btnStoreDetailFacebookClick(View view) {
		Utils.isUnbindDrawables = false;
	}

	public void btnAddToCartClick(View view) {
		Log.i("StoreMainActivity - btnAddToCartClick", "Start");
		try {
			TransactionDetailInfo transaction = StoreMainActivity.productTransactionDetailInfo;
			if (transaction == null || (transaction.getCategoryId() < 5 && transaction.getSizeType() == null)) {
				Toast.makeText(this, "Xin vui lòng chọn size sản phẩm", Toast.LENGTH_SHORT).show();
				return;
			}

			TransactionDetailDB db = new TransactionDetailDB(this);
			ArrayList<TransactionDetailInfo> transactions = db.getTransactions();
			for (TransactionDetailInfo transactionDetailInfo : transactions) {
				if (transaction.getProductId().equals(transactionDetailInfo.getProductId())) {
					Toast.makeText(this, "Sản phẩm này đã có trong giỏ hàng.\nVui lòng xem giỏ hàng để biết chi tiết.",
							Toast.LENGTH_SHORT).show();
					return;
				}
			}

			StoreMainActivity.productTransactionDetailInfo.setAddedDate(Calendar.getInstance().getTime());
			db.insert(StoreMainActivity.productTransactionDetailInfo);
			Toast.makeText(this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
			this.updateStoreCart();
			this.storeProduct.setVisibility(FrameLayout.VISIBLE);
			this.storeProductDetail.setVisibility(FrameLayout.GONE);
			StoreMainActivity.productTransactionDetailInfo = null;

		} catch (Exception e) {
			Log.e("StoreMainActivity - btnAddToCartClick", "StoreMainActivity - btnAddToCartClick", e);
		}
	}

	public void storeProduct1Click(View view) {
		if (checkViewVisible(findViewById(R.id.downloadIndicator))) {
			return;
		}
		Log.i("StoreMainActivity - storeProduct1Click", "start");
		TextView productId = (TextView) view.findViewById(R.id.tvProduct1Id);
		storeProductClick(productId);
	}

	public void storeProduct2Click(View view) {
		if (checkViewVisible(findViewById(R.id.downloadIndicator))) {
			return;
		}
		Log.i("StoreMainActivity - storeProduct2Click", "start");
		TextView productId = (TextView) view.findViewById(R.id.tvProduct2Id);
		storeProductClick(productId);
	}

	public void storeProduct3Click(View view) {
		if (checkViewVisible(findViewById(R.id.downloadIndicator))) {
			return;
		}
		Log.i("StoreMainActivity - storeProduct3Click", "start");
		TextView productId = (TextView) view.findViewById(R.id.tvProduct3Id);
		storeProductClick(productId);
	}

	public void storeProduct4Click(View view) {
		if (checkViewVisible(findViewById(R.id.downloadIndicator))) {
			return;
		}
		Log.i("StoreMainActivity - storeProduct4Click", "start");
		TextView productId = (TextView) view.findViewById(R.id.tvProduct4Id);
		storeProductClick(productId);
	}

	/**
	 * Check view visible.
	 * 
	 * @param view
	 *            the view
	 * @return true, if view visible
	 */
	private boolean checkViewVisible(View view) {
		if (View.VISIBLE == view.getVisibility()) {
			return true;
		}
		return false;
	}

	public void storeProductClick(TextView view) {
		this.storeProduct.setVisibility(FrameLayout.GONE);
		this.storeProductDetail.setVisibility(FrameLayout.VISIBLE);
		Integer index = this.hsProduct.get(view.getText());
		Log.i("StoreMainActivity - storeProductClick", "index: " + index);
		this.updateDataDetail(index);
		ProductInfo product = listProduct.get(index);
		if (productTransactionDetailInfo == null) {
			productTransactionDetailInfo = new TransactionDetailInfo();
		}
		productTransactionDetailInfo.setCategoryId(product.getCategoryId());
		productTransactionDetailInfo.setProductId(product.getProductId());
		productTransactionDetailInfo.setProductName(product.getName());
		productTransactionDetailInfo.setUnitPrice(product.getUnitPrice());
	}

	private void updateDataDetail(Integer index) {
		Log.i("StoreMainActivity - updateData", "Start");
		List<Fragment> fragments = this.getProductDetailFragments();
		// Instantiate a ViewPager and a PagerAdapter.
		mPager = (ViewPager) findViewById(R.id.storeDetailPager);
		mPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager(), fragments);
		mPager.setAdapter(mPagerAdapter);
		mPagerAdapter.notifyDataSetChanged();
		mPager.setCurrentItem(index);
		mPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

				Log.i("StoreMainActivity - onPageScrollStateChanged", "Start");
				TextView tvPage = (TextView) findViewById(R.id.tvStoreDetailPage);
				if (listProduct.size() > 0) {
					String pageDisplay = mPager.getCurrentItem() + 1 + "/" + mPagerAdapter.getCount();
					tvPage.setText(pageDisplay);
				}

				ImageView btnPagePrevious = (ImageView) findViewById(R.id.btnStoreDetailPagePrevious);
				ImageView btnPageNext = (ImageView) findViewById(R.id.btnStoreDetailPageNext);

				if (mPagerAdapter.getCount() > 1) {
					if (mPager.getCurrentItem() == 0) {
						btnPagePrevious.setImageAlpha(70);
						btnPageNext.setImageAlpha(255);
					} else if (mPager.getCurrentItem() == (mPagerAdapter.getCount() - 1)) {
						btnPageNext.setImageAlpha(70);
						btnPagePrevious.setImageAlpha(255);
					} else {
						btnPagePrevious.setImageAlpha(255);
					}
				} else {
					btnPageNext.setImageAlpha(70);
					btnPagePrevious.setImageAlpha(70);
				}

				ProductInfo product = listProduct.get(mPager.getCurrentItem());
				if (productTransactionDetailInfo == null) {
					productTransactionDetailInfo = new TransactionDetailInfo();
				}
				productTransactionDetailInfo.setCategoryId(product.getCategoryId());
				productTransactionDetailInfo.setProductId(product.getProductId());
				productTransactionDetailInfo.setProductName(product.getName());
				productTransactionDetailInfo.setUnitPrice(product.getUnitPrice());
			}

		});
		this.updatePageNumView();
	}

	private List<Fragment> getProductDetailFragments() {
		Log.i("StoreMainActivity - getProductDetailFragments", "Start");
		List<Fragment> fList = new ArrayList<Fragment>();
		for (ProductInfo product : this.listProduct) {
			fList.add(new StoreProductDetailPageFragment(this, product));
		}
		return fList;
	}

	public void btnStoreDetailPreviousClick(View view) {
		Log.i("StoreMainActivity - btnStoreDetailPreviousClick", "Start");
		int currentItem = mPager.getCurrentItem();
		if (currentItem > 0) {
			mPager.setCurrentItem(mPager.getCurrentItem() - 1, true);
			this.updatePageNumView();
		}
	}

	public void btnStoreDetailPageNextClick(View view) {
		Log.i("StoreMainActivity - btnStoreDetailPageNextClick", "Start");
		int currentItem = mPager.getCurrentItem();
		if (currentItem < mPager.getAdapter().getCount()) {
			mPager.setCurrentItem(mPager.getCurrentItem() + 1, true);
			this.updatePageNumView();
		}
	}

	public void updatePageNumView() {
		Log.i("StoreMainActivity - updatePageNumView", "Start");
		TextView tvPage = (TextView) findViewById(R.id.tvStoreDetailPage);
		if (this.listProduct.size() > 0) {
			String pageDisplay = mPager.getCurrentItem() + 1 + "/" + mPagerAdapter.getCount();
			tvPage.setText(pageDisplay);
		}

		ImageView btnPagePrevious = (ImageView) findViewById(R.id.btnStoreDetailPagePrevious);
		ImageView btnPageNext = (ImageView) findViewById(R.id.btnStoreDetailPageNext);

		if (mPagerAdapter.getCount() > 1) {
			if (mPager.getCurrentItem() == 0) {
				btnPagePrevious.setImageAlpha(70);
				btnPageNext.setImageAlpha(255);
			} else if (mPager.getCurrentItem() == (mPagerAdapter.getCount() - 1)) {
				btnPageNext.setImageAlpha(70);
				btnPagePrevious.setImageAlpha(255);
			} else {
				btnPagePrevious.setImageAlpha(255);
			}
		} else {
			btnPageNext.setImageAlpha(70);
			btnPagePrevious.setImageAlpha(70);
		}

		StoreMainActivity.productTransactionDetailInfo = new TransactionDetailInfo();
	}

	private void updateStoreCart() {
		tvStoreCart = (TextView) findViewById(R.id.tvStoreCart);
		TransactionDetailDB db = new TransactionDetailDB(this);
		ArrayList<TransactionDetailInfo> listTransactionDetail = new ArrayList<TransactionDetailInfo>();
		try {
			listTransactionDetail = db.getTransactions();
		} catch (ParseException e) {
			Log.e("StoreMainActivity - updateStoreCart", e.getMessage());
		}
		if (tvStoreCart != null) {
			tvStoreCart.setText(listTransactionDetail.size() + "");
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		Utils.isUnbindDrawables = true;
		updateStoreCart();
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
