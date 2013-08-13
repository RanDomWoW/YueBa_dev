package com.qihoo.yueba.db.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.qihoo.yueba.dto.ActivityMessage;

public class DBService {

	@SuppressWarnings("unused")
	private Context context;
	private DBOpenHelper dbHelper;
	private List<ActivityMessage> LAM = new ArrayList<ActivityMessage>();
	public DBService(Context context) {
		dbHelper = new DBOpenHelper(context);
	}

	/**
	 * 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�閿熸枻鎷烽敓鑺ュ崟閿熸枻鎷烽敓鐭紮鎷烽敓鏂ゆ嫹鎭�
	 * 
	 * @param p
	 *            閿熺煫浼欐嫹瀹為敓鏂ゆ嫹
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
	 * 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�閿熸枻鎷烽敓鏂ゆ嫹閿熺煫浼欐嫹閿熸枻鎷锋伅
	 * 
	 * @param p
	 *            閿熺煫浼欐嫹瀹為敓鏂ゆ嫹
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
	 * 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�閫氶敓鏂ゆ嫹閿熺煫浼欐嫹id閿熸枻鎷疯閿熺煫浼欐嫹閿熸枻鎷锋伅
	 * 
	 * @param id
	 *            閿熺煫浼欐嫹id
	 * @return 閿熸枻鎷烽敓鏂ゆ嫹鍊奸敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熻妭锝忔嫹閿熸触杩斾紮鎷烽敓鐭紮鎷峰疄閿熸枻鎷烽敓鏂ゆ嫹铓嶇シ閿熻娇锟絬ll
	 */
	public ActivityMessage findByTitle(String title) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cs = db.rawQuery("select * from t_ActivityMessage where title = ? ",
				new String[] { String.valueOf(title) });
		ActivityMessage p = new ActivityMessage();
		//可能出现标题相同的事件
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
	 * 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�閫氶敓鏂ゆ嫹閿熺煫浼欐嫹閿熸枻鎷烽敓鏂ゆ嫹閿熺獤锟芥嫹娌￠敓鏂ゆ嫹閿熻緝?
	 * 
	 * @param name
	 *            閿熺煫浼欐嫹閿熸枻鎷烽敓鏂ゆ嫹
	 * @return 閿熸枻鎷烽敓鏂ゆ嫹鍊奸敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熻妭锝忔嫹閿熸触杩斾紮鎷烽敓鐭紮鎷峰疄閿熸枻鎷烽敓鏂ゆ嫹铓嶇シ閿熻娇锟絬ll
	 */
	public List<ActivityMessage> findByName(String name) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cs = db.rawQuery("select * from t_ActivityMessage where name = ? ",
				new String[] { name });
		if (cs.moveToNext()) {
			ActivityMessage p = new ActivityMessage();
			p.setIsDate(cs.getInt(cs.getColumnIndex("isdate")));
			p.setName(cs.getString(cs.getColumnIndex("name")));
			p.setTitle(cs.getString(cs.getColumnIndex("title")));
			p.setBody(cs.getString(cs.getColumnIndex("body")));
			p.setStartTime(cs.getString(cs.getColumnIndex("stime")));
			p.setEndTime(cs.getString(cs.getColumnIndex("etime")));
			LAM.add(p);
		}
		cs.close();
		return LAM;
	}

	/**
	 * 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�閫氶敓鏂ゆ嫹閿熺煫浼欐嫹id鍒犻敓鏂ゆ嫹閿熺煫浼欐嫹閿熸枻鎷锋伅
	 * 
	 * @param ids
	 *            閿熺煫浼欐嫹id
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
	 * 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�閫氶敓鏂ゆ嫹閿熸彮绛规嫹閿熸枻鎷锋嚳閿熺獤锟芥嫹娌￠敓鏂ゆ嫹鏂滈敓?
	 * 
	 * @param startNum
	 *            閿熸枻鎷峰閿熸枻鎷烽敓鏂ゆ嫹鍊�
	 * @param perNum
	 *            姣忛〉閿熸枻鎷风ず閿熸枻鎷烽敓鏂ゆ嫹鐩�
	 * @return 閿熸枻鎷烽敓鏂ゆ嫹鍊奸敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熺煫浼欐嫹閿熷彨鎲嬫嫹閿熸枻鎷锋伅
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
	 * 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�缁熼敓鏂ゆ嫹閿熸枻鎷疯彉閿熸枻鎷烽敓鏂ゆ嫹閿熸嵎纰夋嫹閿熸枻鎷风洰
	 * 
	 * @return 閿熸枻鎷烽敓鏂ゆ嫹鍊奸敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯幐閿熸枻鎷烽敓鏂ゆ嫹閿�
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
	 * 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�浣块敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽攲閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熻緝锟芥嫹姣嶉敓鏂ゆ嫹铏忛敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熺殕杈炬嫹娌￠敓闃讹拷鎷烽敓琛楅潻鎷烽敓鏂ゆ嫹閿熺瓔鍒扮洰閿熸枻鎷烽敓鐭紮鎷�
	 * 
	 * @param id
	 *            婧愰敓鐭紮鎷穒d
	 * @param amount
	 *            杞敓鏂ゆ嫹閿熸枻鎷烽敓?
	 * @param tid
	 *            鐩敓鏂ゆ嫹閿熺煫浼欐嫹id
	 */
//	public void transAmount(int id, float amount, int tid) {
//		SQLiteDatabase db = dbHelper.getWritableDatabase();
//		// 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
//		db.beginTransaction();
//		try {
//			if (findById(id) != null && findById(tid) != null) {
//				db.execSQL("update t_ActivityMessage set amount = amount - ? where id = ? ", new Object[] { amount, id });
//				db.execSQL("update t_ActivityMessage set amount = amount + ? where id = ? ", new Object[] { amount, tid });
//				// 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹鏌愭檼閿熸枻鎷烽敓琛�
//				db.setTransactionSuccessful();
//			}
//		} catch (SQLException e) {
//		} finally {
//			// 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹娆犻敓缂寸櫢鎷烽敓鏂ゆ嫹蹇椾负閿熺即鐧告嫹鏃堕敓鏂ゆ嫹閿熸枻鎷烽敓缁炴唻鎷烽敓鏂ゆ嫹宄や紮鎷烽敓鏂ゆ嫹閿熸枻鎷蜂砍閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹鑵旀瘬閿熸枻鎷烽敓鏂ゆ嫹鐧挃閿熸枻鎷烽敓?
//			db.endTransaction();
//		}
//	}
	
	/**
	 * 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
	 *         閿熸埅鎲嬫嫹閿熸枻鎷疯彉閿熸枻鎷烽敓鐨�
	 */
	public void closeDB(){
		dbHelper.close();
	}

}
