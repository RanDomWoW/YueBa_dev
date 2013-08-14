package com.qihoo.yueba.db.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.qihoo.yueba.dto.ActivityMessage;

public class DBService {

	@SuppressWarnings("unused")
	private Context context;
	private DBOpenHelper dbHelper;
	
	public DBService(Context context) {
		dbHelper = new DBOpenHelper(context);
	}

	/**
	 * 闁跨喐鏋婚幏鐑芥晸閺傘倖瀚归柨鐔告灮閹风兘鏁撻弬銈嗗闁跨喐鏋婚幏锟介柨鐔告灮閹风兘鏁撻懞銉ュ礋闁跨喐鏋婚幏鐑芥晸閻偂绱幏鐑芥晸閺傘倖瀚归幁锟�
	 * 
	 * @param p
	 *            闁跨喓鐓导娆愬鐎圭偤鏁撻弬銈嗗
	 * @throws ParseException 
	 * @throws SQLException 
	 */
	public void save(ActivityMessage p) throws SQLException, ParseException {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		//db.execSQL("create table t_ActivityMessage (id integer primary key autoincrement , name varchar(20), title varchar(20) , body varchar(50) , stime varchar(10) , etime varchar(10))");
		
		db.execSQL("insert into t_ActivityMessage (isdate, name , title , body, stime, etime) values (? , ? , ?, ? , ? , ?)",
				new Object[] { p.getIsDate(), p.getName(), p.getTitle(), p.getBody(),
						p.getStartTime(), p.getEndTime() });
	}

	/**
	 * 闁跨喐鏋婚幏鐑芥晸閺傘倖瀚归柨鐔告灮閹风兘鏁撻弬銈嗗闁跨喐鏋婚幏锟介柨鐔告灮閹风兘鏁撻弬銈嗗闁跨喓鐓导娆愬闁跨喐鏋婚幏閿嬩紖
	 * 
	 * @param p
	 *            闁跨喓鐓导娆愬鐎圭偤鏁撻弬銈嗗
	 * @throws ParseException 
	 * @throws SQLException 
	 */
	public void update(ActivityMessage p) throws SQLException, ParseException {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.execSQL("update t_ActivityMessage set isdate = ?, name = ? , title = ? ,body = ? ,stime = ?, etime = ? where id = ? ",
				new Object[] {p.getIsDate(),  p.getName(), p.getTitle(),p.getBody(), 
					p.getStartTime(), p.getEndTime() });
	}

	/**
	 * 闁跨喐鏋婚幏鐑芥晸閺傘倖瀚归柨鐔告灮閹风兘鏁撻弬銈嗗闁跨喐鏋婚幏锟介柅姘舵晸閺傘倖瀚归柨鐔虹叓娴兼瑦瀚筰d闁跨喐鏋婚幏鐤嚄闁跨喓鐓导娆愬闁跨喐鏋婚幏閿嬩紖
	 * 
	 * @param id
	 *            闁跨喓鐓导娆愬id
	 * @return 闁跨喐鏋婚幏鐑芥晸閺傘倖瀚归崐濂告晸閺傘倖瀚归柨鐔告灮閹风兘鏁撻弬銈嗗闁跨喕濡敐蹇斿闁跨喐瑙︽潻鏂剧串閹风兘鏁撻惌顐＄串閹峰嘲鐤勯柨鐔告灮閹风兘鏁撻弬銈嗗閾撳秶銈烽柨鐔诲▏閿熺惮ll
	 */
	public ActivityMessage findByTitle(String title) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cs = db.rawQuery("select * from t_ActivityMessage where title = ? ",
				new String[] { String.valueOf(title) });
		ActivityMessage p = new ActivityMessage();
		//鍙兘鍑虹幇鏍囬鐩稿悓鐨勪簨浠�
		if (cs.moveToNext()) {
			
			p.setIsDate(cs.getInt(cs.getColumnIndex("isdate")));
			p.setName(cs.getString(cs.getColumnIndex("name")));
			p.setTitle(cs.getString(cs.getColumnIndex("title")));
			p.setBody(cs.getString(cs.getColumnIndex("body")));
			p.setStartTime(cs.getString(cs.getColumnIndex("stime")));
			p.setEndTime(cs.getString(cs.getColumnIndex("etime")));
			
		}
		cs.close();
		return p;
	}
	
	/**
	 * 闁跨喐鏋婚幏鐑芥晸閺傘倖瀚归柨鐔告灮閹风兘鏁撻弬銈嗗闁跨喐鏋婚幏锟介柅姘舵晸閺傘倖瀚归柨鐔虹叓娴兼瑦瀚归柨鐔告灮閹风兘鏁撻弬銈嗗闁跨喓鐛ら敓鑺ュ濞岋繝鏁撻弬銈嗗闁跨喕绶�
	 * 
	 * @param name
	 *            闁跨喓鐓导娆愬闁跨喐鏋婚幏鐑芥晸閺傘倖瀚�
	 * @return 闁跨喐鏋婚幏鐑芥晸閺傘倖瀚归崐濂告晸閺傘倖瀚归柨鐔告灮閹风兘鏁撻弬銈嗗闁跨喕濡敐蹇斿闁跨喐瑙︽潻鏂剧串閹风兘鏁撻惌顐＄串閹峰嘲鐤勯柨鐔告灮閹风兘鏁撻弬銈嗗閾撳秶銈烽柨鐔诲▏閿熺惮ll
	 */
	public List<ActivityMessage> findByName(String name) {
		List<ActivityMessage> LAM = new ArrayList<ActivityMessage>();
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		ActivityMessage p;
		Cursor cs = db.rawQuery("select * from t_ActivityMessage where name = ? ",
				new String[] { name });
		while (cs.moveToNext()) {
			p = new ActivityMessage();
			p.setIsDate(cs.getInt(cs.getColumnIndex("isdate")));
			p.setName(cs.getString(cs.getColumnIndex("name")));
			p.setTitle(cs.getString(cs.getColumnIndex("title")));
			p.setBody(cs.getString(cs.getColumnIndex("body")));
			p.setStartTime(cs.getString(cs.getColumnIndex("stime")));
			p.setEndTime(cs.getString(cs.getColumnIndex("etime")));
			
			LAM.add(p);
		}
		Log.d("ab", Integer.toString(LAM.size()));
		cs.close();
		return LAM;
	}

	/**
	 * 闁跨喐鏋婚幏鐑芥晸閺傘倖瀚归柨鐔告灮閹风兘鏁撻弬銈嗗闁跨喐鏋婚幏锟介柅姘舵晸閺傘倖瀚归柨鐔虹叓娴兼瑦瀚筰d閸掔娀鏁撻弬銈嗗闁跨喓鐓导娆愬闁跨喐鏋婚幏閿嬩紖
	 * 
	 * @param ids
	 *            闁跨喓鐓导娆愬id
	 */
	public void delete(Integer... ids) {
		if (ids.length > 0) {
			StringBuffer sb = new StringBuffer();
			for (@SuppressWarnings("unused")
			int id : ids) {
				sb.append("?").append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			db.execSQL("delete from t_ActivityMessage where id in (" + sb + ")",(Object[]) ids);
		}
	}

	/**
	 * 闁跨喐鏋婚幏鐑芥晸閺傘倖瀚归柨鐔告灮閹风兘鏁撻弬銈嗗闁跨喐鏋婚幏锟介柅姘舵晸閺傘倖瀚归柨鐔稿疆缁涜瀚归柨鐔告灮閹烽攱鍤抽柨鐔虹崵閿熻姤瀚瑰▽锟犳晸閺傘倖瀚归弬婊堟晸?
	 * 
	 * @param startNum
	 *            闁跨喐鏋婚幏宄邦瀶闁跨喐鏋婚幏鐑芥晸閺傘倖瀚归崐锟�
	 * @param perNum
	 *            濮ｅ繘銆夐柨鐔告灮閹烽銇氶柨鐔告灮閹风兘鏁撻弬銈嗗閻╋拷
	 * @return 闁跨喐鏋婚幏鐑芥晸閺傘倖瀚归崐濂告晸閺傘倖瀚归柨鐔告灮閹风兘鏁撻弬銈嗗闁跨喓鐓导娆愬闁跨喎褰ㄩ幉瀣闁跨喐鏋婚幏閿嬩紖
	 */
	public List<ActivityMessage> getPersons(int startNum, int perNum) {
		List<ActivityMessage> lists = new ArrayList<ActivityMessage>();
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cs = db
				.rawQuery("select * from t_ActivityMessage limit ? , ? ", new String[] {
						String.valueOf(startNum), String.valueOf(perNum) });
		while (cs.moveToNext()) {
			ActivityMessage p = new ActivityMessage();
			p.setIsDate(cs.getInt(cs.getColumnIndex("isdate")));
			p.setName(cs.getString(cs.getColumnIndex("name")));
			p.setTitle(cs.getString(cs.getColumnIndex("title")));
			//p.setStartTime((Date)cs.get(cs.getColumnIndex("stime")));
			//p.setEndTime(cs.getString(cs.getColumnIndex("etime")));

			lists.add(p);
		}
		cs.close();
		return lists;
	}

	/**
	 * 闁跨喐鏋婚幏鐑芥晸閺傘倖瀚归柨鐔告灮閹风兘鏁撻弬銈嗗闁跨喐鏋婚幏锟界紒鐔兼晸閺傘倖瀚归柨鐔告灮閹风柉褰夐柨鐔告灮閹风兘鏁撻弬銈嗗闁跨喐宓庣喊澶嬪闁跨喐鏋婚幏椋庢窗
	 * 
	 * @return 闁跨喐鏋婚幏鐑芥晸閺傘倖瀚归崐濂告晸閺傘倖瀚归柨鐔告灮閹风兘鏁撻弬銈嗗闁跨喐鏋婚幏鐤箰闁跨喐鏋婚幏鐑芥晸閺傘倖瀚归柨锟�
	 */
	public long getTotalCounts() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cs = db.rawQuery("select count(*) from t_ActivityMessage ", null);
		if (cs.moveToNext()) {
			return cs.getLong(0);
		}
		return 0;
	}

	/**
	 * 闁跨喐鏋婚幏鐑芥晸閺傘倖瀚归柨鐔告灮閹风兘鏁撻弬銈嗗闁跨喐鏋婚幏锟芥担鍧楁晸閺傘倖瀚归柨鐔告灮閹风兘鏁撻弬銈嗗闁跨喐鏋婚幏鐑芥敳闁跨喐鏋婚幏鐑芥晸閺傘倖瀚归柨鐔告灮閹风兘鏁撻弬銈嗗闁跨喕绶濋敓鑺ュ濮ｅ秹鏁撻弬銈嗗閾忓繘鏁撻弬銈嗗闁跨喐鏋婚幏鐑芥晸閺傘倖瀚归柨鐔烘畷鏉堢偓瀚瑰▽锟犳晸闂冭鎷烽幏鐑芥晸鐞涙娼婚幏鐑芥晸閺傘倖瀚归柨鐔虹摂閸掓壆娲伴柨鐔告灮閹风兘鏁撻惌顐＄串閹凤拷
	 * 
	 * @param id
	 *            濠ф劙鏁撻惌顐＄串閹风d
	 * @param amount
	 *            鏉烆剟鏁撻弬銈嗗闁跨喐鏋婚幏鐑芥晸?
	 * @param tid
	 *            閻╊噣鏁撻弬銈嗗闁跨喓鐓导娆愬id
	 */
//	public void transAmount(int id, float amount, int tid) {
//		SQLiteDatabase db = dbHelper.getWritableDatabase();
//		// 闁跨喐鏋婚幏鐑芥晸閺傘倖瀚归柨鐔告灮閹风兘鏁撻弬銈嗗
//		db.beginTransaction();
//		try {
//			if (findById(id) != null && findById(tid) != null) {
//				db.execSQL("update t_ActivityMessage set amount = amount - ? where id = ? ", new Object[] { amount, id });
//				db.execSQL("update t_ActivityMessage set amount = amount + ? where id = ? ", new Object[] { amount, tid });
//				// 闁跨喐鏋婚幏鐑芥晸閺傘倖瀚归柨鐔告灮閹风兘鏁撻弬銈嗗閺屾劖妾奸柨鐔告灮閹风兘鏁撶悰锟�
//				db.setTransactionSuccessful();
//			}
//		} catch (SQLException e) {
//		} finally {
//			// 闁跨喐鏋婚幏鐑芥晸閺傘倖瀚归柨鐔告灮閹风兘鏁撻弬銈嗗闁跨喐鏋婚幏鐑芥晸閺傘倖瀚归柨鐔告灮閹风兘鏁撻弬銈嗗闁跨喐鏋婚幏鐑芥晸閺傘倖瀚瑰▎鐘绘晸缂傚娅㈤幏鐑芥晸閺傘倖瀚硅箛妞捐礋闁跨喓鍗抽惂鍛婂閺冨爼鏁撻弬銈嗗闁跨喐鏋婚幏鐑芥晸缂佺偞鍞婚幏鐑芥晸閺傘倖瀚瑰畡銈勭串閹风兘鏁撻弬銈嗗闁跨喐鏋婚幏铚傜爫闁跨喐鏋婚幏鐑芥晸閺傘倖瀚归柨鐔告灮閹风兘鏁撻弬銈嗗閼垫梹鐦柨鐔告灮閹风兘鏁撻弬銈嗗閻ь偀鎸冮柨鐔告灮閹风兘鏁�
//			db.endTransaction();
//		}
//	}
	
	/**
	 * 闁跨喐鏋婚幏鐑芥晸閺傘倖瀚归柨鐔告灮閹风兘鏁撻弬銈嗗闁跨喐鏋婚幏锟�
	 *         闁跨喐鍩呴幉瀣闁跨喐鏋婚幏鐤綁闁跨喐鏋婚幏鐑芥晸閻拷
	 */
	public void closeDB(){
		dbHelper.close();
	}

}
