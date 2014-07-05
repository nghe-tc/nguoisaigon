package com.nguoisaigon.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.nguoisaigon.db.UserDB;
import com.nguoisaigon.entity.TransactionDetailInfo;
import com.nguoisaigon.entity.TransactionPost;
import com.nguoisaigon.entity.UserInfo;

public class WebService extends AsyncTask<String, Void, JSONArray> {
	public interface WebServiceDelegate {
		public void taskCompletionResult(JSONArray result);
	}

	public enum productCategory {
		cat_fashion_man("cat_fashion_man", 0), cat_fashion_woman(
				"cat_fashion_woman", 1), cat_fashion_kid("cat_fashion_kid", 2), cat_cos_man(
				"cat_cos_man", 3), cat_cos_woman("cat_cos_woman", 4), cat_food(
				"cat_food", 5), cat_lifeStyle("cat_lifeStyle", 6);

		private String name;
		private int value;

		private productCategory(String vname, int value) {
			setName(vname);
			setIntValue(value);
		}

		public int getIntValue() {
			return value;
		}

		public void setIntValue(int intValue) {
			this.value = intValue;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	public enum productSearchType {
		search_for_client("search_for_client", 0), search_for_admin(
				"search_for_admin", 1);

		private String name;
		private int value;

		private productSearchType(String vname, int value) {
			setName(vname);
			setIntValue(value);
		}

		public int getIntValue() {
			return value;
		}

		public void setIntValue(int intValue) {
			this.value = intValue;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	private final String SERVER_URL = "http://rest.itsleek.vn";
	private final String STR_MAIN_USER_AGENT = "Fiddler";
	private final String STR_MAIN_HOST_INFO = "rest.itsleek.vn";
	private String url;
	private WebServiceDelegate delegate;
	private String musicId;
	Boolean isUserInfoUpdate = false;

	protected JSONObject params;

	/**
	 * true if the request that sends to server is a Post request. Default is
	 * false.
	 */
	public Boolean isPostRequest = false;
	private boolean isDwonloadImageRequest = false;
	private boolean isDwonloadMusicRequest = false;

	/**
	 * Constructor with delegate
	 */
	public WebService(WebServiceDelegate vdelegate) {
		setDelegate(vdelegate);
	}

	/**
	 * Get application setting from server
	 * 
	 * */
	public void setGettingAppSetting() {
		url = SERVER_URL + "/api/Setting";
		this.isPostRequest = false;
	}

	/**
	 * Get music from server
	 * 
	 * */
	public void setGettingMusic() {
		url = SERVER_URL + "/api/playlist";
		this.isPostRequest = false;
	}

	/**
	 * Get all news of specific date from server
	 * 
	 * @param date
	 *            The selected date.
	 * */
	@SuppressLint("SimpleDateFormat")
	public void setGettingNewsData(Date date) {
		// Convert date to string with format yyyy-MM-dd
		SimpleDateFormat fmtOut = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = fmtOut.format(date);
		url = SERVER_URL + "/api/news?selectedDate=" + dateStr;
		this.isPostRequest = false;

	}

	/**
	 * Get all events from server
	 * 
	 * @return JSONObject This object contains all keys/values of event.
	 * */
	public void setGettingEventsData() {
		url = SERVER_URL + "/api/events";
		this.isPostRequest = false;
	}

	/**
	 * Get all products from server by category and search type
	 * 
	 * @param category
	 *            The product category.
	 * @param searchType
	 *            Type of user
	 * @return JSONObject This object contains all keys/values of event.
	 * */
	public void setGettingProducts(productCategory category,
			productSearchType searchType) {
		// http://rest.itsleek.vn/api/product?catId=%i&search_type=%i
		url = SERVER_URL + "/api/product?catId=" + category.getIntValue()
				+ "&search_type=" + searchType.getIntValue();
		this.isPostRequest = false;
	}

	/**
	 * Send Transaction Details to server
	 * 
	 * @param info
	 *            This object contains all keys/values of event.
	 * */
	public void setTransactionDetailRequest(TransactionPost info) {
		// http://rest.itsleek.vn/api/TransactionDetail
		try {
			url = SERVER_URL + "/api/TransactionDetail";
			params = new JSONObject();

			JSONArray transDetailList = new JSONArray();
			for (TransactionDetailInfo transaction : info.getTransDetailList()) {
				JSONObject transDetail = new JSONObject();
				transDetail.put("categoryID", transaction.getCategoryId());
				transDetail.put("productId", transaction.getProductId());
				transDetail.put("productName", transaction.getProductName());
				transDetail.put("sizeType", transaction.getSizeType());
				transDetail.put("transDetailId", "");
				transDetail.put("transactionId", "");
				transDetail.put("unitPrice", transaction.getUnitPrice());
				transDetailList.put(transDetail);
			}

			UserInfo userInfo = info.getUserInfo();
			JSONObject transList = new JSONObject();
			transList.put("address", userInfo.getAddress());
			transList.put("contactPhone", userInfo.getContactPhone());
			transList.put("createDate", "");
			transList.put("note", userInfo.getNote());
			transList.put("ownerInfo", "");
			transList.put("paymentMethod", info.getPaymentMethod());
			transList.put("status", 0);
			transList.put("totalAmount", info.getTotalAmount());
			transList.put("transDetailList", "");
			transList.put("transactionId", "");
			transList.put("userId", userInfo.getUserId());
			transList.put("userName", "");

			params.put("transDetailList", transDetailList);
			params.put("transList", transList);

			Log.i("WebService - setTransactionDetailRequest", "params: "
					+ params.toString());
			this.isPostRequest = true;
		} catch (JSONException e) {
			Log.e("WebService", e.getMessage());
		}
	}
	
	/**
	 * Send User information to server
	 * 
	 * @param info
	 *            This object contains all keys/values of user.
	 * */
	public void setUserInfoRequest(UserInfo info) {
		// http://rest.itsleek.vn/api/TransactionDetail
		try {
			url = SERVER_URL + "/api/User";
			isUserInfoUpdate = true;
			params = new JSONObject();
			
			params.put("address", info.getAddress());
			params.put("contactPhone", info.getContactPhone());
			params.put("earnedPoint", 0);
			params.put("email", info.getEmail());
			params.put("name", info.getName());
			params.put("userId", "");

			Log.i("WebService - setTransactionDetailRequest", "params: "
					+ params.toString());
			this.isPostRequest = true;
		} catch (JSONException e) {
			Log.e("WebService", e.getMessage());
		}
	}
	
	/**
	 * Send updating User information to server
	 * 
	 * @param info
	 *            This object contains all keys/values of user.
	 * */
	public void setEdittingUserInfoRequest(UserInfo info) {
		// http://rest.itsleek.vn/api/TransactionDetail
		try {
			url = SERVER_URL + "/api/User";
			isUserInfoUpdate = true;
			params = new JSONObject();

			params.put("address", info.getAddress());
			params.put("contactPhone", info.getContactPhone());
			params.put("earnedPoint", info.getErnedPoint());
			params.put("email", info.getEmail());
			params.put("name", info.getName());
			params.put("userId", info.getUserId());

			Log.i("WebService - setTransactionDetailRequest", "params: "
					+ params.toString());
			this.isPostRequest = true;
		} catch (JSONException e) {
			Log.e("WebService", e.getMessage());
		}
	}

	/**
	 * Download image from url
	 * 
	 * @param vurl
	 *            link to image.
	 * */
	public void setDownloadingImageRequest(String vurl) {
		url = SERVER_URL + vurl;
		this.isDwonloadImageRequest = true;
		this.isPostRequest = false;
		Log.i("WebService", "setTransactionDetail" + url);
	}

	/**
	 * Download music from url
	 * 
	 * @param vurl
	 *            link to image.
	 * */
	public void setDownloadingMusicRequest(String vurl, String Id) {
		url = SERVER_URL + vurl;
		musicId = Id;
		this.isDwonloadMusicRequest = true;
		this.isDwonloadImageRequest = false;
		this.isPostRequest = false;
		Log.i("WebService", "setTransactionDetail" + url);
	}

	private JSONArray downloadBitmap() {
		// initilize the default HTTP client object
		JSONArray responseString = new JSONArray();
		final DefaultHttpClient client = new DefaultHttpClient();

		// forming a HttoGet request
		final HttpGet getRequest = new HttpGet(url);
		try {

			HttpResponse response = client.execute(getRequest);

			// check 200 OK for success
			final int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode != HttpStatus.SC_OK) {
				Log.w("ImageDownloader", "Error " + statusCode
						+ " while retrieving bitmap from " + url);
				return null;

			}

			final HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream inputStream = null;
				try {
					// getting contents from the stream
					inputStream = entity.getContent();

					// decoding stream data back into image Bitmap that android
					// understands
					final Bitmap bitmap = BitmapFactory
							.decodeStream(inputStream);

					if (bitmap != null)
						responseString.put(bitmap);
					return responseString;
				} finally {
					if (inputStream != null) {
						inputStream.close();
					}
					entity.consumeContent();
				}
			}
		} catch (Exception e) {
			// You Could provide a more explicit error message for IOException
			getRequest.abort();
			Log.e("ImageDownloader", "Something went wrong while"
					+ " retrieving bitmap from " + url + e.toString());
		}

		return null;
	}

	public static final String PREFIX_DOWNLOAD = "/.nguoisaigon/";
	private String path = Environment.getExternalStorageDirectory()
			+ PREFIX_DOWNLOAD;

	/**
	 * Download music.
	 * 
	 * @return the JSON array
	 */
	private JSONArray downloadMusic() {
		File file = new File(path);
		if (!file.mkdirs()) {
			Log.i("WebService",
					"Make directories failure because it already existed.");
		}
		File musicFile = new File(file, musicId);
		if (musicFile.exists()) {
			return null;
		}

		final DefaultHttpClient client = new DefaultHttpClient();

		// forming a HttoGet request
		final HttpGet getRequest = new HttpGet(url);
		Log.i("WebService", "songData 123 " + "-" + url);
		try {

			HttpResponse response = client.execute(getRequest);

			// check 200 OK for success
			final int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode != HttpStatus.SC_OK) {
				Log.w("downloadMusic", "Error " + statusCode
						+ " while retrieving music from " + url);
				return null;
			}

			final HttpEntity entity = response.getEntity();

			FileOutputStream fos = null;
			InputStream is = null;
			if (entity != null) {
				fos = new FileOutputStream(musicFile);

				is = entity.getContent();

				byte[] buffer = new byte[1024];
				int bufferLength = 0;
				while ((bufferLength = is.read(buffer)) != -1) {
					fos.write(buffer, 0, bufferLength);
				}
			}
			Log.w("downloadMusic", "Download music successful: " + musicId);
		} catch (Exception e) {
			// You Could provide a more explicit error message for IOException
			getRequest.abort();
			Log.e("downloadMusic", "Something went wrong while"
					+ " retrieving music from " + url + e.toString());
		}

		return null;
	}

	/**
	 * Send a HTML request to server (POST)
	 */
	private JSONArray postDataToServer() {
		Log.i("WebService", "WebService: postDataToServer " + url);

		try {
			DefaultHttpClient httpclient = new DefaultHttpClient();

			HttpPost httppost = new HttpPost(url);

//			httppost.addHeader("User-Agent", STR_MAIN_USER_AGENT);
//			httppost.addHeader("Host", STR_MAIN_USER_AGENT);
//			httppost.addHeader("Content-Type", STR_MAIN_USER_AGENT);
//			httppost.addHeader("Content-Length", STR_MAIN_USER_AGENT);

//			ByteArrayEntity entity;
//
//			entity = new ByteArrayEntity(params.toString().getBytes("UTF8"));
//			httppost.setEntity(entity);

			StringEntity se = new StringEntity(params.toString());
			se.setContentEncoding(HTTP.UTF_8);
			se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			httppost.setEntity(se);
			httppost.setHeader(HTTP.USER_AGENT, STR_MAIN_USER_AGENT);
			httppost.setHeader(HTTP.CONTENT_TYPE, "application/json");
			HttpResponse response = httpclient.execute(httppost);
			Log.i("WebService - response", response.toString());

			// check 200 OK for success
			final int statusCode = response.getStatusLine().getStatusCode();
			JSONArray result = new JSONArray();
			
			if(isUserInfoUpdate)
			{
				String jsonText = EntityUtils.toString(response.getEntity(),
						HTTP.UTF_8);
				Log.i("WebService", "WebService: response " + jsonText);
				
				JSONObject userData = new JSONObject(jsonText);
				result.put(userData);
			}
			else
			{
				if (statusCode != HttpStatus.SC_OK) {
					result.put(false);
					return result;
				}
				
				result.put(true);
			}
			
			return result;

		} catch (Exception e) {
			Log.e("WebService - postDataToServer", e.getMessage());
		}
		return null;
	}

	/**
	 * Send a HTML request to server (GET)
	 * */
	private JSONArray getDataFromServer() {
		Log.i("WebService", "WebService: getDataFromServer " + url);
		JSONArray responseString = new JSONArray();
		try {
			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpResponse response;
			response = httpclient.execute(new HttpGet(url));
			StatusLine statusLine = response.getStatusLine();
			Log.i("WebService",
					"WebService: getDataFromUrl " + response.getStatusLine());
			if (statusLine.getStatusCode() == HttpStatus.SC_ACCEPTED) {
				String jsonText = EntityUtils.toString(response.getEntity(),
						HTTP.UTF_8);
				Log.i("WebService", "WebService: response " + jsonText);
				if (jsonText.startsWith("{")) {
					JSONObject jsonObject = new JSONObject(jsonText);
					responseString.put(jsonObject);
				} else {
					responseString = new JSONArray(jsonText);
				}
			} else {
				// Closes the connection.
				response.getEntity().getContent().close();
				throw new IOException(statusLine.getReasonPhrase());
			}
		} catch (ClientProtocolException e) {
			Log.e("WebService", e.getMessage());
			responseString = null;
		} catch (IOException e) {
			Log.e("WebService", e.getMessage());
			responseString = null;
		} catch (JSONException e) {
			Log.e("WebService", e.getMessage());
			responseString = null;
		}
		url = null;
		return responseString;

	}

	@Override
	protected JSONArray doInBackground(String... arg0) {
		if (url != null) {
			if (this.isPostRequest) {
				return postDataToServer();
			} else if (this.isDwonloadImageRequest) {
				return downloadBitmap();
			} else if (this.isDwonloadMusicRequest) {
				return downloadMusic();
			} else {
				return getDataFromServer();
			}
		}
		return null;
	}

	@Override
	protected void onPostExecute(JSONArray result) {
		super.onPostExecute(result);
		Log.i("onPostExecute", "onPostExecute" + result);
		delegate.taskCompletionResult(result);
	}

	public WebServiceDelegate getDelegate() {
		return delegate;
	}

	public void setDelegate(WebServiceDelegate delegate) {
		this.delegate = delegate;
	}
}