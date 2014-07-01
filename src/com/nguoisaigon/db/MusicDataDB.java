package com.nguoisaigon.db;

import java.util.ArrayList;

import com.nguoisaigon.entity.MusicDataInfo;
import com.nguoisaigon.entity.MusicInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class MusicDataDB extends DBHelper {

	/**
	 * Constructor
	 * 
	 * @param vContext
	 */
	public MusicDataDB(Context vContext) {
		super(vContext);
	}

	public static final String TABLE_NAME = "musicdata";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_PLAYLIST_ID = "playlistid";
	public static final String COLUMN_MUSIC_DATA = "musicdata";

	/**
	 * Insert MusicInfo into database
	 * 
	 * @param info
	 * @return
	 */
	public Long insert(MusicDataInfo info) {
		ContentValues values = new ContentValues();
		values.put(COLUMN_PLAYLIST_ID, info.getPlayListId());
		values.put(COLUMN_MUSIC_DATA, info.getMusicData());
		return sqlite.insert(TABLE_NAME, null, values);
	}

	/**
	 * Get list of Musics
	 * @return
	 */
	public MusicDataInfo getMusicDataByPlayListId(String id) {
		String[] projection = {COLUMN_PLAYLIST_ID, COLUMN_MUSIC_DATA };
		String selection = COLUMN_PLAYLIST_ID + " = ?";
		String[] selectionArgs = { String.valueOf(id) };
		Cursor c = sqlite.query(TABLE_NAME, projection, selection, selectionArgs, null, null,
				null);
		MusicDataInfo info = null;
		if (c.moveToFirst()) {
			info = new MusicDataInfo();
			info.setPlayListId(c.getString(0));
			info.setMusicData(c.getBlob(1));
		}
		
		return info;
	}

}
