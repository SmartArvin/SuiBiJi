package com.miyang.suibiji;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{
	
//	定义数据库常量
	private static final String DB_NAME = "note.db";
//	定义系列数据表常量		
	private static final String TB_MAIN = "main";
	private static final String TB_LOGIN = "login";
	private static final String TB_LIKE = "like";
	private static final String TB_SEARCH = "search";
	private static final String TB_BACKUP = "backup";
	private static final String TB_BACKUPLIKE = "backuplike";
//	定义数据库对象
	private SQLiteDatabase db;
//	构造数据库函数
	public DBHelper(Context context) {
		super(context, DB_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
//		获得当前数据库对象
		this.db = db;
//		创建数据库和数据表
		String CREATE_MAIN = "CREATE TABLE main(_id INTEGER PRIMARY key autoincrement,"+" photo_one text,"+" photo_two text,"+" photo_three text,"+" theme text,"+" date text," + " content text)";
		String CREATE_LOGIN = "CREATE TABLE login(_id INTEGER PRIMARY key autoincrement," + "key1 text)";
		String CREATE_LIKE = "CREATE TABLE like(_id INTEGER PRIMARY key autoincrement,"+" photo_one text,"+" photo_two text,"+" photo_three text,"+" theme text,"+" date text," + " content text)";
		String CREATE_SEARCH = "CREATE TABLE search(_id INTEGER PRIMARY key autoincrement,"+" photo_one text,"+" photo_two text,"+" photo_three text,"+" theme text,"+" date text," + " content text)";
		String CREATE_BACKUP = "CREATE TABLE backup(_id INTEGER PRIMARY key autoincrement,"+" photo_one text,"+" photo_two text,"+" photo_three text,"+" theme text,"+" date text," + " content text)";
		String CREATE_BACKUPLIKE = "CREATE TABLE backuplike(_id INTEGER PRIMARY key autoincrement,"+" photo_one text,"+" photo_two text,"+" photo_three text,"+" theme text,"+" date text," + " content text)";
//		执行数据表创建语句
		db.execSQL(CREATE_MAIN);
		db.execSQL(CREATE_LOGIN);
		db.execSQL(CREATE_LIKE);
		db.execSQL(CREATE_SEARCH);
		db.execSQL(CREATE_BACKUP);
		db.execSQL(CREATE_BACKUPLIKE);
	}
	
   /*定义数据插入函数
	*/
	public void insert(ContentValues contentValues , String tb_name){
		SQLiteDatabase db = getWritableDatabase();
		db.insert(tb_name, null, contentValues);
		db.close();
	}
	
	/*
	 * 定义数据删除函数
	*/
//	删除数据表的全部数据
	public void delete(String tb_name){
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("delete from "+tb_name);
		db.close();
	}
//	按日期删除数据表对应数据
	public void deleteDate(String tb_name , String tb_date){
		SQLiteDatabase db = getWritableDatabase();
		if(db==null)
			db = getWritableDatabase();
		db.delete(tb_name, "date=?", new String[]{String.valueOf(tb_date)});
	}
	
	/*
	 * 定义数据修改函数
	*/
//	按日期更新数据库信息
	public void update(String tb_name,ContentValues values,String date){
		SQLiteDatabase db = getWritableDatabase();
		db.update(tb_name, values, "date=?", new String[]{date});
		db.close();
	}
	
	/*
	 * 定义查找数据函数
	*/
//	查询数据表中数据的条数
	public Cursor rawQueryCount(String tb_name) {
		SQLiteDatabase db = getReadableDatabase();
		Cursor c = db.rawQuery("select count (_id) from " + tb_name, null);
		return c;
	}
//	查询数据表中的数据信息
	public Cursor rawQueryInf(String tb_name) {
		SQLiteDatabase db = getWritableDatabase();
		Cursor c = db.rawQuery("select * from " + tb_name, null);
		return c;
	}
//	模糊查询数据
	public Cursor rawQueryLike(String tb_name,String key) {
		SQLiteDatabase db = getWritableDatabase();
//		两张表同时多字段查询
//		Cursor c = db.rawQuery("select * from web,chat " + " where web.user like ? or web.password like ? or web.remark like ? or chat.user like ? or chat.password like ? or chat.remark like ?" , new String[]{"%"+key+"%","%"+key+"%","%"+key+"%","%"+key+"%","%"+key+"%","%"+key+"%"});
//		单张表多字段查询
//		Cursor c = db.rawQuery("select * from web " + " where web.user like ? or web.password like ? or web.remark like ?" , new String[]{"%"+key+"%","%"+key+"%","%"+key+"%"});
		Cursor c = db.rawQuery("select * from "+tb_name+" where theme like ? or content like ?" , new String[]{"%"+key+"%","%"+key+"%"});
		return c;
	}
	
//	关闭数据库【不用重写该函数】
//	public void close(){
//		SQLiteDatabase db = getWritableDatabase();
//		if(db!=null&db.isOpen()){
//			db = getWritableDatabase();
////			关闭数据库
//		db.close();
//		}
//	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
