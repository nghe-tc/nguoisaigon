package com.nguoisaigon.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper {

	protected SQLiteDatabase sqlite;
	public Context context;
	private static final String DB_NAME = "nguoisaigondb.sqlite";

	public DBHelper(Context vContext) {
		this.context = vContext;
		sqlite = openDatabase();
	}

	/**
	 * Open database connections
	 */
	public SQLiteDatabase openDatabase() {
		File dbFile = context.getDatabasePath(DB_NAME);

		if (!dbFile.exists()) {
			try {
				copyDatabase(dbFile);
			} catch (IOException e) {
				throw new RuntimeException("Error creating source database", e);
			}
		}
		return SQLiteDatabase.openDatabase(dbFile.getPath(), null,
				SQLiteDatabase.OPEN_READWRITE);
	}

	/**
	 * Copy database file
	 */
	private void copyDatabase(File dbFile) throws IOException {
		// Check folder database
		File dbPath = new File(dbFile.getParent());
		if (!dbPath.exists()) {
			dbPath.mkdirs();
		}
		
		// Copy database file
		InputStream is = context.getAssets().open(DB_NAME);
		OutputStream os = new FileOutputStream(dbFile);

		byte[] buffer = new byte[1024];
		while (is.read(buffer) > 0) {
			os.write(buffer);
		}

		os.flush();
		os.close();
		is.close();
	}

	/**
	 * Close database connections
	 */
	public void closeDatabase() {
		sqlite.close();
	}
}
