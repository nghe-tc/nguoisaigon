package com.nguoisaigon.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.nguoisaigon.entity.TransactionDetailInfo;

@SuppressLint("SimpleDateFormat")
public class TransactionDetailDB extends DBHelper {

	/**
	 * Constructor
	 * 
	 * @param vContext
	 */
	public TransactionDetailDB(Context vContext) {
		super(vContext);
	}

	public static final String TABLE_NAME = "transactiondetail";
	public static final String COLUMN_ID = "serial";
	public static final String COLUMN_PRODUCT_ID = "productid";
	public static final String COLUMN_PRODUCT_NAME = "productname";
	public static final String COLUMN_CATEGORY_ID = "categoryid";
	public static final String COLUMN_QUANTITY = "quantity";
	public static final String COLUMN_SIZE_TYPE = "sizetype";
	public static final String COLUMN_STOCK_QUANTITY = "stockquantity";
	public static final String COLUMN_ADDED_DATE = "addeddate";
	public static final String COLUMN_UNIT_PRICE = "unitprice";

	/**
	 * Insert TransactionDetailInfo into database
	 * 
	 * @param info
	 * @return
	 */
	public Long insert(TransactionDetailInfo info) {
		ContentValues values = new ContentValues();
		values.put(COLUMN_PRODUCT_ID, info.getProductId());
		values.put(COLUMN_PRODUCT_NAME, info.getProductName());
		values.put(COLUMN_CATEGORY_ID, info.getCategoryId());
		values.put(COLUMN_QUANTITY, info.getQuantity());
		values.put(COLUMN_SIZE_TYPE, info.getSizeType());
		values.put(COLUMN_STOCK_QUANTITY, info.getQuantity());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		String addedDate = formatter.format(info.getAddedDate());
		values.put(COLUMN_ADDED_DATE, addedDate);
		values.put(COLUMN_UNIT_PRICE, info.getUnitPrice());
		return sqlite.insert(TABLE_NAME, null, values);
	}

	/**
	 * Update TransactionDetailInfo
	 * 
	 * @param info
	 * @return
	 */
	public Integer update(TransactionDetailInfo info) {
		ContentValues values = new ContentValues();
		values.put(COLUMN_PRODUCT_ID, info.getProductId());
		values.put(COLUMN_PRODUCT_NAME, info.getProductName());
		values.put(COLUMN_CATEGORY_ID, info.getCategoryId());
		values.put(COLUMN_QUANTITY, info.getQuantity());
		values.put(COLUMN_SIZE_TYPE, info.getSizeType());
		values.put(COLUMN_STOCK_QUANTITY, info.getQuantity());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		String addedDate = formatter.format(info.getAddedDate());
		values.put(COLUMN_ADDED_DATE, addedDate);
		values.put(COLUMN_UNIT_PRICE, info.getUnitPrice());

		String selection = COLUMN_ID + " = ?";
		String[] selectionArgs = { String.valueOf(info.getId()) };

		return sqlite.update(TABLE_NAME, values, selection, selectionArgs);
	}

	/**
	 * Delete TransactionDetailInfo by id
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
	 * Delete TransactionDetailInfo by productId
	 * 
	 * @param id
	 * @return
	 */
	public Integer deleteByProductId(Integer id) {
		String selection = COLUMN_PRODUCT_ID + " = ?";
		String[] selectionArgs = { String.valueOf(1) };
		return sqlite.delete(TABLE_NAME, selection, selectionArgs);
	}

	/**
	 * Get List of transactions
	 * 
	 * @return
	 * @throws ParseException
	 */
	public ArrayList<TransactionDetailInfo> getTransactions()
			throws ParseException {
		ArrayList<TransactionDetailInfo> listTrans = new ArrayList<TransactionDetailInfo>();
		String[] projection = { COLUMN_ID, COLUMN_PRODUCT_ID, COLUMN_PRODUCT_NAME,
				COLUMN_CATEGORY_ID, COLUMN_QUANTITY, COLUMN_SIZE_TYPE,
				COLUMN_STOCK_QUANTITY, COLUMN_ADDED_DATE, COLUMN_UNIT_PRICE };

		Cursor c = sqlite.query(TABLE_NAME, projection, null, null, null, null,
				null);
		while (c.moveToNext()) {
			TransactionDetailInfo info = new TransactionDetailInfo();
			info.setId(c.getInt(0));
			info.setProductId(c.getString(1));
			info.setProductName(c.getString(2));
			info.setCategoryId(c.getInt(3));
			info.setQuantity(c.getInt(4));
			info.setSizeType(c.getInt(5));
			info.setStockQuantity(c.getInt(6));
			String strAddDate = c.getString(7);
			Log.i("TransactionDetailDB - getTransactions", strAddDate);
			SimpleDateFormat fm = new SimpleDateFormat("yyyy/MM/dd");
			Date addDate = (Date) fm.parse(strAddDate);
			info.setAddedDate(addDate);
			info.setUnitPrice(c.getDouble(8));
			listTrans.add(info);
		}
		return listTrans;
	}
}
