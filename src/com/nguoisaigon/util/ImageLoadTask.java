package com.nguoisaigon.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import com.nguoisaigon.entity.ImageInfo;

public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

	/**
	 * The Constant TAG.
	 */
	private static final String TAG = ImageLoadTask.class.getSimpleName();

	public static final String SERVER_URL = "http://rest.itsleek.vn";
	private String imageUrl, id;

	private ImageView imageView;

	/**
	 * Instantiates a new image load task.
	 * 
	 * @param imageInfo
	 *            the image info
	 */
	public ImageLoadTask(ImageInfo imageInfo) {
		this.imageUrl = SERVER_URL + imageInfo.getImageUrl();
		this.id = imageInfo.getImageId();
	}

	public ImageLoadTask(ImageInfo imageInfo, ImageView imageView) {
		this.imageUrl = SERVER_URL + imageInfo.getImageUrl();
		this.id = imageInfo.getImageId();
		this.imageView = imageView;
	}

	@Override
	protected Bitmap doInBackground(Void... params) {
		Bitmap bitmap = BitmapCache.getBitmapFromMemCache(id);
		if (bitmap != null) {
			return bitmap;
		}

		HttpURLConnection con = null;
		InputStream is = null;
		try {
			URL url = new URL(imageUrl);
			con = (HttpURLConnection) url.openConnection();
			is = con.getInputStream();

			BitmapFactory.Options bounds = new BitmapFactory.Options();
			bounds.inSampleSize = 4;
			Bitmap bmp = BitmapFactory.decodeStream(is, null, bounds);
			BitmapCache.addBitmapToMemoryCache(id, bmp);
			return bmp;
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					Log.e(TAG, e.getMessage(), e);
				}
			}
			if (con != null) {
				con.disconnect();
			}
		}
		return null;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);
		if (result != null) {
			if (imageView != null) {
				imageView.setImageBitmap(result);
			}
		} else {
			Log.i(TAG, "The Bitmap is NULL");
		}
	}
}