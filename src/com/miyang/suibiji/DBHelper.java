package com.miyang.suibiji;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{
	
//	�������ݿⳣ��
	private static final String DB_NAME = "note.db";
//	����ϵ�����ݱ���		
	private static final String TB_MAIN = "main";
	private static final String TB_LOGIN = "login";
	private static final String TB_LIKE = "like";
	private static final String TB_SEARCH = "search";
	private static final String TB_BACKUP = "backup";
	private static final String TB_BACKUPLIKE = "backuplike";
//	�������ݿ����
	private SQLiteDatabase db;
//	�������ݿ⺯��
	public DBHelper(Context context) {
		super(context, DB_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
//		��õ�ǰ���ݿ����
		this.db = db;
//		�������ݿ�����ݱ�
		String CREATE_MAIN = "CREATE TABLE main(_id INTEGER PRIMARY key autoincrement,"+" photo_one text,"+" photo_two text,"+" photo_three text,"+" theme text,"+" date text," + " content text)";
		String CREATE_LOGIN = "CREATE TABLE login(_id INTEGER PRIMARY key autoincrement," + "key1 text)";
		String CREATE_LIKE = "CREATE TABLE like(_id INTEGER PRIMARY key autoincrement,"+" photo_one text,"+" photo_two text,"+" photo_three text,"+" theme text,"+" date text," + " content text)";
		String CREATE_SEARCH = "CREATE TABLE search(_id INTEGER PRIMARY key autoincrement,"+" photo_one text,"+" photo_two text,"+" photo_three text,"+" theme text,"+" date text," + " content text)";
		String CREATE_BACKUP = "CREATE TABLE backup(_id INTEGER PRIMARY key autoincrement,"+" photo_one text,"+" photo_two text,"+" photo_three text,"+" theme text,"+" date text," + " content text)";
		String CREATE_BACKUPLIKE = "CREATE TABLE backuplike(_id INTEGER PRIMARY key autoincrement,"+" photo_one text,"+" photo_two text,"+" photo_three text,"+" theme text,"+" date text," + " content text)";
//		ִ�����ݱ������
		db.execSQL(CREATE_MAIN);
		db.execSQL(CREATE_LOGIN);
		db.execSQL(CREATE_LIKE);
		db.execSQL(CREATE_SEARCH);
		db.execSQL(CREATE_BACKUP);
		db.execSQL(CREATE_BACKUPLIKE);
	}
	
   /*�������ݲ��뺯��
	*/
	public void insert(ContentValues contentValues , String tb_name){
		SQLiteDatabase db = getWritableDatabase();
		db.insert(tb_name, null, contentValues);
		db.close();
	}
	
	/*
	 * ��������ɾ������
	*/
//	ɾ�����ݱ��ȫ������
	public void delete(String tb_name){
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("delete from "+tb_name);
		db.close();
	}
//	������ɾ�����ݱ��Ӧ����
	public void deleteDate(String tb_name , String tb_date){
		SQLiteDatabase db = getWritableDatabase();
		if(db==null)
			db = getWritableDatabase();
		db.delete(tb_name, "date=?", new String[]{String.valueOf(tb_date)});
	}
	
	/*
	 * ���������޸ĺ���
	*/
//	�����ڸ������ݿ���Ϣ
	public void update(String tb_name,ContentValues values,String date){
		SQLiteDatabase db = getWritableDatabase();
		db.update(tb_name, values, "date=?", new String[]{date});
		db.close();
	}
	
	/*
	 * ����������ݺ���
	*/
//	��ѯ���ݱ������ݵ�����
	public Cursor rawQueryCount(String tb_name) {
		SQLiteDatabase db = getReadableDatabase();
		Cursor c = db.rawQuery("select count (_id) from " + tb_name, null);
		return c;
	}
//	��ѯ���ݱ��е�������Ϣ
	public Cursor rawQueryInf(String tb_name) {
		SQLiteDatabase db = getWritableDatabase();
		Cursor c = db.rawQuery("select * from " + tb_name, null);
		return c;
	}
//	ģ����ѯ����
	public Cursor rawQueryLike(String tb_name,String key) {
		SQLiteDatabase db = getWritableDatabase();
//		���ű�ͬʱ���ֶβ�ѯ
//		Cursor c = db.rawQuery("select * from web,chat " + " where web.user like ? or web.password like ? or web.remark like ? or chat.user like ? or chat.password like ? or chat.remark like ?" , new String[]{"%"+key+"%","%"+key+"%","%"+key+"%","%"+key+"%","%"+key+"%","%"+key+"%"});
//		���ű���ֶβ�ѯ
//		Cursor c = db.rawQuery("select * from web " + " where web.user like ? or web.password like ? or web.remark like ?" , new String[]{"%"+key+"%","%"+key+"%","%"+key+"%"});
		Cursor c = db.rawQuery("select * from "+tb_name+" where theme like ? or content like ?" , new String[]{"%"+key+"%","%"+key+"%"});
		return c;
	}
	
//	�ر����ݿ⡾������д�ú�����
//	public void close(){
//		SQLiteDatabase db = getWritableDatabase();
//		if(db!=null&db.isOpen()){
//			db = getWritableDatabase();
////			�ر����ݿ�
//		db.close();
//		}
//	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
