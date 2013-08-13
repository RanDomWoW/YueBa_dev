package com.qihoo.yueba.db.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 
 * @author  SQLite3֧��
 *         NULL��INTEGER��REAL���������֣���TEXT(�ַ��ı�)��BLOB(�����ƶ���)�������?
 *         sqlite3Ҳ����varchar(n)��char(n)��decimal(p,s) ���������?
 */
public class DBOpenHelper extends SQLiteOpenHelper {

	private static final String DBNAME = "myclover.db";
	private static final int VERSION = 1;

	public DBOpenHelper(Context context) {
		super(context, DBNAME, null, VERSION);
		Log.d("DB", "helper construct");
	}

	public DBOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d("DB", "create db");
		db.execSQL("create table t_ActivityMessage (id integer primary key autoincrement , isdate int, name varchar(20), title varchar(20) , body varchar(40) ,stime Date , etime Date)");
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists t_ActivityMessage");
		onCreate(db);
	}

}
