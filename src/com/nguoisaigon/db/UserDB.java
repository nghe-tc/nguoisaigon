package com.nguoisaigon.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.nguoisaigon.entity.UserInfo;

public class UserDB extends DBHelper {

	/**
	 * Constructor
	 * 
	 * @param vContext
	 */
	public UserDB(Context vContext) {
		super(vContext);
	}

	public static final String TABLE_NAME = "user";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_USER_ID = "userid";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_EMAIL = "email";
	public static final String COLUMN_CONTACT_PHONE = "contactphone";
	public static final String COLUMN_ADDRESS = "address";
	public static final String COLUMN_ERNED_POINT = "ernedpoint";

	/**
	 * Insert UserInfo into database
	 * 
	 * @param info
	 * @return
	 */
	public Long insert(UserInfo info) {
		ContentValues values = new ContentValues();
		values.put(COLUMN_USER_ID, info.getUserId());
		values.put(COLUMN_NAME, info.getName());
		values.put(COLUMN_EMAIL, info.getEmail());
		values.put(COLUMN_CONTACT_PHONE, info.getContactPhone());
		values.put(COLUMN_ADDRESS, info.getAddress());
		values.put(COLUMN_ERNED_POINT, info.getErnedPoint());
		return sqlite.insert(TABLE_NAME, null, values);
	}

	/**
	 * Insert UserInfo
	 * 
	 * @param info
	 * @return
	 */
	public Integer update(UserInfo info) {
		ContentValues values = new ContentValues();
		values.put(COLUMN_USER_ID, info.getUserId());
		values.put(COLUMN_NAME, info.getName());
		values.put(COLUMN_EMAIL, info.getEmail());
		values.put(COLUMN_CONTACT_PHONE, info.getContactPhone());
		values.put(COLUMN_ADDRESS, info.getAddress());
		values.put(COLUMN_ERNED_POINT, info.getErnedPoint());

		String selection = COLUMN_ID + " = ?";
		String[] selectionArgs = { String.valueOf(1) };

		return sqlite.update(TABLE_NAME, values, selection, selectionArgs);
	}

	/**
	 * Delete UserInfo by id
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id) {
		String selection = COLUMN_ID + " = ?";
		String[] selectionArgs = { String.valueOf(1) };
		return sqlite.delete(TABLE_NAME, selection, selectionArgs);
	}

	/**
	 * Get user's information from database
	 * 
	 * @return
	 */
	public UserInfo getUser() {
		String[] projection = { COLUMN_USER_ID, COLUMN_NAME, COLUMN_EMAIL,
				COLUMN_CONTACT_PHONE, COLUMN_ADDRESS, COLUMN_ERNED_POINT };

		Cursor c = sqlite.query(TABLE_NAME, projection, null, null, null, null,
				null);
		if (c.moveToFirst()) {
			UserInfo user = new UserInfo();
			user.setUserId(c.getString(0));
			user.setName(c.getString(1));
			user.setEmail(c.getString(2));
			user.setContactPhone(c.getString(3));
			user.setAddress(c.getString(4));
			user.setErnedPoint(c.getDouble(5));
			return user;
		}
		return null;
	}
}
