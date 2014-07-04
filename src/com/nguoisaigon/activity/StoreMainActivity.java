package com.nguoisaigon.activity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import android.annotation.SuppressLint;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.nguoisaigon.R;
import com.nguoisaigon.db.TransactionDetailDB;
import com.nguoisaigon.entity.ProductInfo;
import com.nguoisaigon.entity.StoreProductPageInfo;
import com.nguoisaigon.entity.TransactionDetailInfo;
import com.nguoisaigon.util.BitmapCache;
import com.nguoisaigon.util.CustomPagerAdapter;
import com.nguoisaigon.util.StoreProductDetailPageFragment;
import com.nguoisaigon.util.StoreProductPageAdapter;
import com.nguoisaigon.util.WebService;
import com.nguoisaigon.util.WebService.WebServiceDelegate;
import com.nguoisaigon.util.WebService.productCategory;
import com.nguoisaigon.util.WebService.productSearchType;

@SuppressLint("UseSparseArrays")
public class StoreMainActivity extends FragmentActivity implements WebServiceDelegate {

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
	private TransactionDetailInfo transactionDetailInfo;
	private FrameLayout storeProduct;
	private FrameLayout storeProductDetail;

	private ImageView lifeStyle;
	private ImageView food;
	private ImageView cosMan;
	private ImageView cosWoman;
	private ImageView fasMan;
	private ImageView fasWoman;
	private ImageView fasKid;
	private TextView tvStoreCart;

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
		this.transactionDetailInfo = new TransactionDetailInfo();
		this.storeMainListViewProduct = (ListView) findViewById(R.id.storeMainListProduct);

		fasMan = (ImageView) findViewById(R.id.menuStoreFashionMan);
		menuStoreFashionManClick(fasMan);
		this.loadData(productCategory.cat_fashion_man, productSearchType.search_for_client);

		setOnFocusChangeListener();
		this.updateStoreCart();
	}

	public void loadData(productCategory category, productSearchType searchType) {
		Log.i("StoreMainActivity - loadData", "Start");
		this.listProduct.clear();
		this.hsProduct.clear();
		this.transactionDetailInfo.clear();
		this.transactionDetailInfo.setCategoryId(category.getIntValue());
		if (storeMainProductAdapter != null) {
			this.storeMainProductAdapter = new StoreProductPageAdapter(this, new ArrayList<StoreProductPageInfo>());
			storeMainListViewProduct.setAdapter(storeMainProductAdapter);
			storeMainProductAdapter.notifyDataSetChanged();
		}
		// Download data
		WebService ws = new WebService(this);
		ws.setGettingProducts(category, searchType);
		ws.execute();
	}

	@Override
	public void taskCompletionResult(JSONArray result) {
		Log.i("StoreMainActivity - taskCompletionResult", "Start");
		Log.i("StoreMainActivity - taskCompletionResult",
				"JSONArray result: " + ((result == null) ? "null" : result.toString()));
		if (result != null) {
			Log.i("StoreMainActivity - taskCompletionResult", "result's length: " + result.length());
			for (int i = 0; i < result.length(); i++) {
				try {
					ProductInfo info = new Gson().fromJson(result.getString(i), ProductInfo.class);
					this.listProduct.add(info);
					this.hsProduct.put(info.getProductId(), i);
				} catch (Exception e) {
					Log.e("StoreMainActivity - Get Product", e.getMessage());
				}
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
		view.requestFocus();
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
		Intent intent = new Intent(this, PreviewCartActivity.class);
		startActivity(intent);
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
		Log.i("StoreMainActivity - btnAddToCartClick", "Start");
		this.transactionDetailInfo.setAddedDate(Calendar.getInstance().getTime());
		TransactionDetailDB db = new TransactionDetailDB(this);
		db.insert(this.transactionDetailInfo);
		Toast.makeText(this, "Ä�Ã£ thÃªm vÃ o giá»� hÃ ng", Toast.LENGTH_LONG).show();
		this.updateStoreCart();
		this.storeProduct.setVisibility(FrameLayout.VISIBLE);
		this.storeProductDetail.setVisibility(FrameLayout.GONE);
	}

	public void storeProduct1Click(View view) {
		Log.i("StoreMainActivity - storeProduct1Click", "start");
		TextView productId = (TextView) view.findViewById(R.id.tvProduct1Id);
		storeProductClick(productId);
	}

	public void storeProduct2Click(View view) {
		Log.i("StoreMainActivity - storeProduct2Click", "start");
		TextView productId = (TextView) view.findViewById(R.id.tvProduct2Id);
		storeProductClick(productId);
	}

	public void storeProduct3Click(View view) {
		Log.i("StoreMainActivity - storeProduct3Click", "start");
		TextView productId = (TextView) view.findViewById(R.id.tvProduct3Id);
		storeProductClick(productId);
	}

	public void storeProduct4Click(View view) {
		Log.i("StoreMainActivity - storeProduct4Click", "start");
		TextView productId = (TextView) view.findViewById(R.id.tvProduct4Id);
		storeProductClick(productId);
	}

	public void storeProductClick(TextView view) {
		this.storeProduct.setVisibility(FrameLayout.GONE);
		this.storeProductDetail.setVisibility(FrameLayout.VISIBLE);
		Integer index = this.hsProduct.get(view.getText());
		Log.i("StoreMainActivity - storeProductClick", "index: " + index);
		this.updateDataDetail(index);
		ProductInfo product = this.listProduct.get(index);
		this.transactionDetailInfo.setProductId(product.getProductId());
		this.transactionDetailInfo.setProductName(product.getName());
		this.transactionDetailInfo.setQuantity(1);
		if (product.getSizeQtyList().size() > 0) {
			this.transactionDetailInfo.setSizeType(product.getSizeQtyList().get(3).getSizeType());
		}
		this.transactionDetailInfo.setUnitPrice(product.getUnitPrice());
	}

	private void updateDataDetail(Integer index) {
		Log.i("StoreMainActivity - updateData", "Start");
		List<Fragment> fragments = this.getProductDetailFragments();
		// Instantiate a ViewPager and a PagerAdapter.
		mPager = (ViewPager) findViewById(R.id.storeDetailPager);
		mPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager(), fragments);
		mPager.setAdapter(mPagerAdapter);
		mPager.setCurrentItem(index);
		Log.i("StoreMainActivity - updateDataDetail", "Num of page: " + mPagerAdapter.getCount());
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

	// public void disableAllSize() {
	// ImageView sizeXXS = (ImageView)
	// findViewById(R.id.storeDetailProductSizeXXS);
	// ImageView sizeXS = (ImageView)
	// findViewById(R.id.storeDetailProductSizeXS);
	// ImageView sizeS = (ImageView) findViewById(R.id.storeDetailProductSizeS);
	// ImageView sizeM = (ImageView) findViewById(R.id.storeDetailProductSizeM);
	// ImageView sizeL = (ImageView) findViewById(R.id.storeDetailProductSizeL);
	// ImageView sizeXL = (ImageView)
	// findViewById(R.id.storeDetailProductSizeXL);
	//
	// sizeXXS.setImageAlpha(70);
	// sizeXS.setImageAlpha(70);
	// sizeS.setImageAlpha(70);
	// sizeM.setImageAlpha(70);
	// sizeL.setImageAlpha(70);
	// sizeXL.setImageAlpha(70);
	// }

	public void storeDetailProductSizeClick(View view) {
		Log.i("StoreMainActivity - storeDetailProductSizeClick", "Start");
		ImageView sizeImage = (ImageView) view;
		Integer sizeType = Integer.parseInt(sizeImage.getContentDescription().toString());
		Log.i("StoreMainActivity - storeDetailProductSizeClick", "size type: " + sizeType.toString());
		this.transactionDetailInfo.setSizeType(sizeType);
		sizeImage.setImageAlpha(255);
		// this.disableAllSize();
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

		ProductInfo product = this.listProduct.get(mPager.getCurrentItem());
		this.transactionDetailInfo.setProductId(product.getProductId());
		this.transactionDetailInfo.setProductName(product.getName());
		this.transactionDetailInfo.setQuantity(1);
		if (product.getSizeQtyList().size() > 0) {
			this.transactionDetailInfo.setSizeType(product.getSizeQtyList().get(3).getSizeType());
		}
		this.transactionDetailInfo.setUnitPrice(product.getUnitPrice());
	}

	void setStoreDetailPageChageLisener() {
		this.mPager.setOnPageChangeListener(new OnPageChangeListener() {

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
				transactionDetailInfo.setProductId(product.getProductId());
				transactionDetailInfo.setProductName(product.getName());
				transactionDetailInfo.setQuantity(1);
				transactionDetailInfo.setSizeType(product.getSizeQtyList().get(3).getSizeType());
				transactionDetailInfo.setUnitPrice(product.getUnitPrice());
			}

		});
	}

	private void setOnFocusChangeListener() {
		lifeStyle = (ImageView) findViewById(R.id.menuStoreLifeStyle);
		food = (ImageView) findViewById(R.id.menuStoreFood);
		cosMan = (ImageView) findViewById(R.id.menuStoreCosmeticMan);
		cosWoman = (ImageView) findViewById(R.id.menuStoreCosmeticWoman);
		fasMan = (ImageView) findViewById(R.id.menuStoreFashionMan);
		fasWoman = (ImageView) findViewById(R.id.menuStoreFashionWoman);
		fasKid = (ImageView) findViewById(R.id.menuStoreFashionKid);

		lifeStyle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					v.performClick();
				}
			}
		});

		food.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					v.performClick();
				}
			}
		});

		cosMan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					v.performClick();
				}
			}
		});

		cosWoman.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					v.performClick();
				}
			}
		});

		fasMan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					v.performClick();
				}
			}
		});

		fasWoman.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					v.performClick();
				}
			}
		});

		fasKid.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					v.performClick();
				}
			}
		});
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
		tvStoreCart.setText(listTransactionDetail.size() + "");
	}
}
