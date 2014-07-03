package com.nguoisaigon.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

	/**
	 * The Constant TAG.
	 */
	private static final String TAG = ImageLoadTask.class.getSimpleName();
	public static final String PREFIX_DOWNLOAD = "/.nguoisaigon/";
	private String path = Environment.getExternalStorageDirectory() + PREFIX_DOWNLOAD;

	public static final String SERVER_URL = "http://rest.itsleek.vn";
	private String imageUrl, id;
	private ImageView imageView;

	public ImageLoadTask(ImageView imageView, String url, String id) {
		this.imageView = imageView;
		this.imageUrl = SERVER_URL + url;
		this.id = id;
	}

	@Override
	protected Bitmap doInBackground(Void... params) {
		File file = new File(path);
		if (!file.mkdirs()) {
			Log.i("WebService", "Make directories failure because it already existed.");
		}
		File picFile = new File(file, id);
		if (picFile.exists()) {
			Bitmap bmp = BitmapFactory.decodeFile(picFile.getAbsolutePath());
			return bmp;
		}

		HttpURLConnection con = null;
		InputStream is = null;
		OutputStream fOut = null;
		try {
			URL url = new URL(imageUrl);
			con = (HttpURLConnection) url.openConnection();
			is = con.getInputStream();
			Bitmap bmp = BitmapFactory.decodeStream(is);

			fOut = new FileOutputStream(picFile);
			bmp.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
			fOut.flush();
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
			if (fOut != null) {
				try {
					fOut.close();
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
			imageView.setImageBitmap(result);
		} else {
			Log.i(TAG, "The Bitmap is NULL");
		}
	}
}
