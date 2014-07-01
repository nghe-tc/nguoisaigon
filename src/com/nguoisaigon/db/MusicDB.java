package com.nguoisaigon.db;

import java.util.ArrayList;

import com.nguoisaigon.entity.MusicInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class MusicDB extends DBHelper {

	/**
	 * Constructor
	 * 
	 * @param vContext
	 */
	public MusicDB(Context vContext) {
		super(vContext);
	}

	public static final String TABLE_NAME = "music";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_OWNER_INFO = "ownerinfo";
	public static final String COLUMN_PLAYLIST_ID = "playlistid";
	public static final String COLUMN_PLAY_URL = "playurl";
	public static final String COLUMN_SINGER = "singer";
	public static final String COLUMN_TITLE = "title";

	/**
	 * Insert MusicInfo into database
	 * 
	 * @param info
	 * @return
	 */
	public Long insert(MusicInfo info) {
		ContentValues values = new ContentValues();
		values.put(COLUMN_OWNER_INFO, info.getOwnerInfo());
		values.put(COLUMN_PLAYLIST_ID, info.getPlayListId());
		values.put(COLUMN_PLAY_URL, info.getPlayUrl());
		values.put(COLUMN_SINGER, info.getSinger());
		values.put(COLUMN_TITLE, info.getTitle());
		return sqlite.insert(TABLE_NAME, null, values);
	}

	/**
	 * Get list of Musics
	 * @return
	 */
	public ArrayList<MusicInfo> getMusics() {
		ArrayList<MusicInfo> listMusics = new ArrayList<MusicInfo>();
		String[] projection = { COLUMN_OWNER_INFO, COLUMN_PLAYLIST_ID,
				COLUMN_PLAY_URL, COLUMN_SINGER, COLUMN_TITLE };

		Cursor c = sqlite.query(TABLE_NAME, projection, null, null, null, null,
				null);
		while (c.moveToNext()) {
			MusicInfo info = new MusicInfo();
			info.setOwnerInfo(c.getString(0));
			info.setPlayListId(c.getString(1));
			info.setPlayUrl(c.getString(2));
			info.setSinger(c.getString(3));
			info.setTitle(c.getString(4));
			listMusics.add(info);
		}
		
		return listMusics;
	}

}
