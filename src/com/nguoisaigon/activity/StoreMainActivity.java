package com.nguoisaigon.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nguoisaigon.R;
import com.nguoisaigon.entity.ProductInfo;
import com.nguoisaigon.entity.StoreProductPageInfo;
import com.nguoisaigon.util.CustomPagerAdapter;
import com.nguoisaigon.util.StoreProductDetailPageFragment;
import com.nguoisaigon.util.StoreProductPageFragment;
import com.nguoisaigon.util.WebService;
import com.nguoisaigon.util.WebService.WebServiceDelegate;
import com.nguoisaigon.util.WebService.productCategory;
import com.nguoisaigon.util.WebService.productSearchType;

@SuppressLint("UseSparseArrays")
public class StoreMainActivity extends FragmentActivity implements
		WebServiceDelegate {

	/**
	 * The pager widget, which handles animation and allows swiping horizontally
	 * to access previous and next wizard steps.
	 */
	private ViewPager mPager;

	/**
	 * The pager adapter, which provides the pages to the view pager widget.
	 */
	private PagerAdapter mPagerAdapter;

	private ArrayList<ProductInfo> listProduct;
	private HashMap<String, Integer> hsProduct;
	private FrameLayout storeProduct;
	private FrameLayout storeProductDetail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i("StoreMainActivity - onCreate", "Start");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.store_main_layout);
		this.storeProduct = (FrameLayout) findViewById(R.id.storeMain);
		this.storeProductDetail = (FrameLayout) findViewById(R.id.storeDetail);
		storeProductDetail.setVisibility(FrameLayout.GONE);
		this.hsProduct = new HashMap<String, Integer>();
		this.listProduct = new ArrayList<ProductInfo>();
		this.loadData(productCategory.cat_fashion_man,
				productSearchType.search_for_client);
		this.setStoreDetailPageChageLisener();
	}

	public void loadData(productCategory category, productSearchType searchType) {
		Log.i("StoreMainActivity - loadData", "Start");
		this.listProduct.clear();
		this.hsProduct.clear();
		// Download data
		WebService ws = new WebService(this);
		ws.setGettingProducts(category, searchType);
		ws.execute();
	}

	@Override
	public void taskCompletionResult(JSONArray result) {
		Log.i("StoreMainActivity - taskCompletionResult", "Start");
		Log.i("StoreMainActivity - taskCompletionResult", "JSONArray result: "
				+ ((result == null) ? "null" : result.toString()));
		if (result != null) {
			Log.i("StoreMainActivity - taskCompletionResult",
					"result's length: " + result.length());
			for (int i = 0; i < result.length(); i++) {
				try {
					ProductInfo info = new Gson().fromJson(result.getString(i),
							ProductInfo.class);
					this.listProduct.add(info);
					this.hsProduct.put(info.getProductId(), i);
				} catch (Exception e) {
					Log.e("StoreMainActivity - Get Product", e.getMessage());
				}
			}
			Log.i("StoreMainActivity - taskCompletionResult",
					"size of listProduct: " + listProduct.size());
		}
		updateData();
	}

	private void updateData() {
		Log.i("StoreMainActivity - updateData", "Start");
		List<Fragment> fragments = getProductFragments();
		// Instantiate a ViewPager and a PagerAdapter.
		mPager = (ViewPager) findViewById(R.id.storeMainPager);
		mPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager(),
				fragments);
		mPager.setAdapter(mPagerAdapter);
		Log.i("StoreMainActivity - updateData",
				"Num of page: " + mPagerAdapter.getCount());
	}

	private List<Fragment> getProductFragments() {
		Log.i("StoreMainActivity - getProductFragments", "Start");
		List<Fragment> fList = new ArrayList<Fragment>();
		StoreProductPageInfo pageInfo = new StoreProductPageInfo();
		Integer index = 0;
		if (listProduct.size() > 8) {
			for (ProductInfo product : this.listProduct) {
				index++;
				pageInfo.addProduct(product);
				if (index == 8) {
					fList.add(StoreProductPageFragment.newInstance(pageInfo));
					pageInfo = new StoreProductPageInfo();
				} else if (index == this.listProduct.size()) {
					fList.add(StoreProductPageFragment.newInstance(pageInfo));
				}
			}
		} else {
			for (ProductInfo product : this.listProduct) {
				index++;
				pageInfo.addProduct(product);
				if (index == this.listProduct.size()) {
					fList.add(StoreProductPageFragment.newInstance(pageInfo));
				}
			}
		}

		Log.i("StoreMainActivity - getFragments", "size of list fragment: "
				+ fList.size());
		return fList;
	}

	public void menuStoreFashionManClick(View view) {
		loadData(productCategory.cat_fashion_man,
				productSearchType.search_for_client);
	}

	public void menuStoreFashionWomanClick(View view) {
		loadData(productCategory.cat_fashion_woman,
				productSearchType.search_for_client);
	}

	public void menuStoreFashionKidClick(View view) {
		loadData(productCategory.cat_fashion_kid,
				productSearchType.search_for_client);
	}

	public void menuStoreCosmeticManClick(View view) {
		loadData(productCategory.cat_cos_man,
				productSearchType.search_for_client);
	}

	public void menuStoreCosmeticWomanClick(View view) {
		loadData(productCategory.cat_cos_woman,
				productSearchType.search_for_client);
	}

	public void menuStoreLifeStyleClick(View view) {
		loadData(productCategory.cat_lifeStyle,
				productSearchType.search_for_client);
	}

	public void menuStoreFoodClick(View view) {
		loadData(productCategory.cat_food, productSearchType.search_for_client);
	}

	public void btnCloseClick(View view) {
		this.finish();
	}

	public void btnStoreCartClick(View view) {

	}

	public void btnStoreDetailBackClick(View view) {
		this.storeProduct.setVisibility(FrameLayout.VISIBLE);
		this.storeProductDetail.setVisibility(FrameLayout.GONE);
	}

	public void btnStoreDetailEmailClick(View view) {

	}

	public void btnStoreDetailFacebookClick(View view) {

	}

	public void btnAddToCartClick(View view) {

	}

	public void storeProduct1Click(View view) {
		TextView productId = (TextView) view.findViewById(R.id.tvProduct1Id);
		storeProductClick(productId);
	}

	public void storeProduct2Click(View view) {
		TextView productId = (TextView) view.findViewById(R.id.tvProduct2Id);
		storeProductClick(productId);
	}

	public void storeProduct3Click(View view) {
		TextView productId = (TextView) view.findViewById(R.id.tvProduct3Id);
		storeProductClick(productId);
	}

	public void storeProduct4Click(View view) {
		TextView productId = (TextView) view.findViewById(R.id.tvProduct4Id);
		storeProductClick(productId);
	}

	public void storeProduct5Click(View view) {
		TextView productId = (TextView) view.findViewById(R.id.tvProduct5Id);
		storeProductClick(productId);
	}

	public void storeProduct6Click(View view) {
		TextView productId = (TextView) view.findViewById(R.id.tvProduct6Id);
		storeProductClick(productId);
	}

	public void storeProduct7Click(View view) {
		TextView productId = (TextView) view.findViewById(R.id.tvProduct7Id);
		storeProductClick(productId);
	}

	public void storeProduct8Click(View view) {
		TextView productId = (TextView) view.findViewById(R.id.tvProduct8Id);
		storeProductClick(productId);
	}

	public void storeProductClick(TextView view) {
		this.storeProduct.setVisibility(FrameLayout.GONE);
		this.storeProductDetail.setVisibility(FrameLayout.VISIBLE);
		Integer index = this.hsProduct.get(view.getText());
		Log.i("StoreMainActivity - storeProductClick", "index: " + index);
		this.updateDataDetail(index);
	}

	private void updateDataDetail(Integer index) {
		Log.i("StoreMainActivity - updateData", "Start");
		List<Fragment> fragments = this.getProductDetailFragments();
		// Instantiate a ViewPager and a PagerAdapter.
		mPager = (ViewPager) findViewById(R.id.storeDetailPager);
		mPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager(),
				fragments);
		mPager.setAdapter(mPagerAdapter);
		mPager.setCurrentItem(index);
		Log.i("StoreMainActivity - updateDataDetail", "Num of page: "
				+ mPagerAdapter.getCount());
		this.updatePageNumView();
	}

	private List<Fragment> getProductDetailFragments() {
		Log.i("StoreMainActivity - getProductDetailFragments", "Start");
		List<Fragment> fList = new ArrayList<Fragment>();
		for (ProductInfo product : this.listProduct) {
			fList.add(StoreProductDetailPageFragment.newInstance(product));
		}
		return fList;
	}

	public void disableAllSize() {
		ImageView sizeXXS = (ImageView) findViewById(R.id.storeDetailProductSizeXXS);
		ImageView sizeXS = (ImageView) findViewById(R.id.storeDetailProductSizeXS);
		ImageView sizeS = (ImageView) findViewById(R.id.storeDetailProductSizeS);
		ImageView sizeM = (ImageView) findViewById(R.id.storeDetailProductSizeM);
		ImageView sizeL = (ImageView) findViewById(R.id.storeDetailProductSizeL);
		ImageView sizeXL = (ImageView) findViewById(R.id.storeDetailProductSizeXL);

		sizeXXS.setImageAlpha(70);
		sizeXS.setImageAlpha(70);
		sizeS.setImageAlpha(70);
		sizeM.setImageAlpha(70);
		sizeL.setImageAlpha(70);
		sizeXL.setImageAlpha(70);
	}

	public void storeDetailProductSizeClick(View view) {
		Log.i("StoreMainActivity - storeDetailProductSizeClick", "Start");
		this.disableAllSize();
		ImageView sizeImage = (ImageView) view;
		sizeImage.setImageAlpha(255);
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
			String pageDisplay = mPager.getCurrentItem() + 1 + "/"
					+ mPagerAdapter.getCount();
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

	}

	void setStoreDetailPageChageLisener() {
		ViewPager pager = (ViewPager) findViewById(R.id.storeDetailPager);
		pager.setOnPageChangeListener(new OnPageChangeListener() {

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
					String pageDisplay = mPager.getCurrentItem() + 1 + "/"
							+ mPagerAdapter.getCount();
					tvPage.setText(pageDisplay);
				}

				ImageView btnPagePrevious = (ImageView) findViewById(R.id.btnStoreDetailPagePrevious);
				ImageView btnPageNext = (ImageView) findViewById(R.id.btnStoreDetailPageNext);

				if (mPagerAdapter.getCount() > 1) {
					if (mPager.getCurrentItem() == 0) {
						btnPagePrevious.setImageAlpha(70);
						btnPageNext.setImageAlpha(255);
					} else if (mPager.getCurrentItem() == (mPagerAdapter
							.getCount() - 1)) {
						btnPageNext.setImageAlpha(70);
						btnPagePrevious.setImageAlpha(255);
					} else {
						btnPagePrevious.setImageAlpha(255);
					}
				} else {
					btnPageNext.setImageAlpha(70);
					btnPagePrevious.setImageAlpha(70);
				}
			}
		});
	}
}
