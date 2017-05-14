package com.miyang.suibiji;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;

import com.miyang.suibiji.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Files;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SB_Safe extends Activity{

	Button safe_back,safe_delete,safe_backup,safe_return,safe_change;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sb_safe_activity);
		
//		把当前Activity加入到CloseApplication的Activitylist中
		CloseApplication.getInstance().addActivity(this);
		
//		获得控件的Id
		safe_back = (Button)findViewById(R.id.safe_back);
		safe_delete = (Button)findViewById(R.id.safe_delete);
		safe_backup = (Button)findViewById(R.id.safe_backup);
		safe_return = (Button)findViewById(R.id.safe_return);
		safe_change = (Button)findViewById(R.id.safe_change);
//		为控件绑定监听器
		safe_back.setOnClickListener(new safeListener());
		safe_delete.setOnClickListener(new safeListener());
		safe_backup.setOnClickListener(new safeListener());
		safe_return.setOnClickListener(new safeListener());
		safe_change.setOnClickListener(new safeListener());
	}
/*
 * 创建监听器类
	*/
//	创建监听器类
	class safeListener implements OnClickListener{
//		创建DBHelper数据库工具类
		DBHelper safeHelper = new DBHelper(getApplicationContext());
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.safe_back:
			intent.setClass(SB_Safe.this,SB_Home.class);
			SB_Safe.this.startActivity(intent);
			finish();
			break;
		case R.id.safe_delete:
			//此处直接new一个Dialog对象出来，在实例化的时候传入主题
            final Dialog dialog_delete = new Dialog(SB_Safe.this, R.style.MyDialog);
            //设置它的ContentView
            dialog_delete.setContentView(R.layout.sb_dialog_safe_delete);
            dialog_delete.show();
            
//          获得控件的Id
            Button safe_delete_cancle = (Button)dialog_delete.findViewById(R.id.safe_delete_cancle);
            Button safe_delete_ok = (Button)dialog_delete.findViewById(R.id.safe_delete_ok);
//            为取消按钮绑定监听器
            safe_delete_cancle.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dialog_delete.dismiss();
				}
			});
//            为确定删除按钮绑定监听器
            safe_delete_ok.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
//					定义一个数组用于存放系列数据表
					String[] tb_name = {"main","like"}; 
//					循环查询数据表，并将查询结果插入到search数据表中
					for(int i = 0;i < tb_name.length ; i++){
//						清空全部数据表
						safeHelper.delete(tb_name[i]);
					}
					dialog_delete.dismiss();
				}
			});
			break;
		case R.id.safe_backup:
//			备份之前删除原来的备份数据
			safeHelper.delete("backup");
			safeHelper.delete("backuplike");
			
//			循环查询数据库中的系列数据表,备份main数据表
//					循环查询数据表
					Cursor cur = safeHelper.rawQueryInf("main");
					ContentValues cv = new ContentValues();
					while(cur.moveToNext()){
						cv.put("photo_one", cur.getString(cur.getColumnIndex("photo_one")));
						cv.put("photo_two", cur.getString(cur.getColumnIndex("photo_two")));
						cv.put("photo_three", cur.getString(cur.getColumnIndex("photo_three")));
						cv.put("theme", cur.getString(cur.getColumnIndex("theme")));
						cv.put("date", cur.getString(cur.getColumnIndex("date")));
						cv.put("content", cur.getString(cur.getColumnIndex("content")));
						safeHelper.insert(cv, "backup");
			}
		//			查询like数据表,备份like数据表
					Cursor cu = safeHelper.rawQueryInf("like");
					ContentValues c = new ContentValues();
					while(cu.moveToNext()){
						c.put("photo_one", cu.getString(cu.getColumnIndex("photo_one")));
						c.put("photo_two", cu.getString(cu.getColumnIndex("photo_two")));
						c.put("photo_three", cu.getString(cu.getColumnIndex("photo_three")));
						c.put("theme", cu.getString(cu.getColumnIndex("theme")));
						c.put("date", cu.getString(cu.getColumnIndex("date")));
						c.put("content", cu.getString(cu.getColumnIndex("content")));
						safeHelper.insert(c, "backuplike");
					}
//					关闭游标
					cu.close();
					cur.close();
//					完成备份后的提示信息
			Toast.makeText(SB_Safe.this, "数据备份完成！", Toast.LENGTH_SHORT).show();
			break;
			
		case R.id.safe_return:
			//此处直接new一个Dialog对象出来，在实例化的时候传入主题
            final Dialog dialog_return = new Dialog(SB_Safe.this, R.style.MyDialog);
            //设置它的ContentView
            dialog_return.setContentView(R.layout.sb_dialog_safe_return);
            dialog_return.show();
            
//          获得控件的Id
            Button safe_return_cancle = (Button)dialog_return.findViewById(R.id.safe_return_cancle);
            Button safe_return_ok = (Button)dialog_return.findViewById(R.id.safe_return_ok);
//            为取消按钮绑定监听器
            safe_return_cancle.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog_return.dismiss();
				}
			});
//            为确定还原按钮绑定监听器
            safe_return_ok.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
//					还原数据之前先清空当前数据表信息，避免数据表中信息重复
						safeHelper.delete("main");
//					还原数据之前清空收藏数据表的信息,避免数据表中数据重复
					safeHelper.delete("like");
//						查询backup数据表,并把backup数据表的信息插入到main数据表中
						Cursor c = safeHelper.rawQueryInf("backup");
						while(c.moveToNext()){
//								循环查询数据表，并将查询结果插入到对应的数据表中
									ContentValues cv = new ContentValues();
									cv.put("photo_one", c.getString(c.getColumnIndex("photo_one")));
									cv.put("photo_two", c.getString(c.getColumnIndex("photo_two")));
									cv.put("photo_three", c.getString(c.getColumnIndex("photo_three")));
									cv.put("theme", c.getString(c.getColumnIndex("theme")));
									cv.put("date", c.getString(c.getColumnIndex("date")));
									cv.put("content", c.getString(c.getColumnIndex("content")));
									safeHelper.insert(cv, "main");
						}
//						关闭游标
						c.close();
						
//					查询backup_like数据表，并把back_like数据表中的数据插入到like表中
					Cursor cu = safeHelper.rawQueryInf("backuplike");
					ContentValues cvl = new ContentValues();
					while(cu.moveToNext()){
						cvl.put("photo_one", cu.getString(cu.getColumnIndex("photo_one")));
						cvl.put("photo_two", cu.getString(cu.getColumnIndex("photo_two")));
						cvl.put("photo_three", cu.getString(cu.getColumnIndex("photo_three")));
						cvl.put("theme", cu.getString(cu.getColumnIndex("theme")));
						cvl.put("date", cu.getString(cu.getColumnIndex("date")));
						cvl.put("content", cu.getString(cu.getColumnIndex("content")));
//						向like数据表中插入数据
						safeHelper.insert(cvl, "like");
					}
//					关闭游标
					cu.close();
					
//					隐藏dialog
					dialog_return.dismiss();
					Toast.makeText(SB_Safe.this, "数据已成功还原！", Toast.LENGTH_SHORT).show();
				}
			});
			break;
			
		case R.id.safe_change:
			
			//此处直接new一个Dialog对象出来，在实例化的时候传入主题
            final Dialog dialog_change = new Dialog(SB_Safe.this, R.style.MyDialog);
            //设置它的ContentView
            dialog_change.setContentView(R.layout.sb_dialog_safe_change);
            dialog_change.show();
			
			Cursor curs = safeHelper.rawQueryCount("login");
			int cn = 0;
			while(curs.moveToNext()){
				cn = curs.getInt(0);
			}
//			如果login数据表为空，则意味着用户选择的是无密模式
			if(cn == 0){
				dialog_change.dismiss();
				Toast.makeText(SB_Safe.this, "无密码信息，请选择加密模式！", Toast.LENGTH_SHORT).show();
			}
//			如果login数据表不为空，则意味着用户已经选择了加密模式，允许修改登陆密码
			else{

//	          获得控件的Id
	            Button safe_change_cancle = (Button)dialog_change.findViewById(R.id.safe_change_cancle);
	            Button safe_change_ok = (Button)dialog_change.findViewById(R.id.safe_change_ok);
//	            为取消按钮绑定监听器
	            safe_change_cancle.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog_change.dismiss();
					}
				});
//	            为确定修改按钮绑定监听器
	            safe_change_ok.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
//						得到dialog中三个EditText编辑框的Id
						EditText safe_change_et1 = (EditText)dialog_change.findViewById(R.id.safe_change_et1);
						EditText safe_change_et2 = (EditText)dialog_change.findViewById(R.id.safe_change_et2);
						EditText safe_change_et3 = (EditText)dialog_change.findViewById(R.id.safe_change_et3);
//						获得以上三个编辑框的内容
						String safe_change_e1 = safe_change_et1.getText().toString();
						String safe_change_e2 = safe_change_et2.getText().toString();
						String safe_change_e3 = safe_change_et3.getText().toString();
							Cursor c = safeHelper.rawQueryInf("login");
							while(c.moveToNext()){
								String temp = c.getString(c.getColumnIndex("key1"));
								if(safe_change_e1.equals(temp)){
									if(safe_change_e2.isEmpty()&&safe_change_e3.isEmpty()){
//										如果修改项为空则清除login数据表的数据,作无密码处理
										safeHelper.delete("login");
										dialog_change.dismiss();
										Toast.makeText(SB_Safe.this, "密码已清空！", Toast.LENGTH_SHORT).show();
									}else{
										if(safe_change_e2.equals(safe_change_e3)){
//											更新密码之前先清除login数据表的数据
											safeHelper.delete("login");
//											创建contentvalues对象，封装记录信息
											ContentValues cv = new ContentValues();
											cv.put("key1", safe_change_e2);
//											插入新密码
											safeHelper.insert(cv, "login");
											dialog_change.dismiss();
											Toast.makeText(SB_Safe.this, "修改成功！", Toast.LENGTH_SHORT).show();
										}else{
											Toast.makeText(SB_Safe.this, "请确认输入密码正确！", Toast.LENGTH_SHORT).show();
										}
									}
								}else{
									dialog_change.dismiss();
									Toast.makeText(SB_Safe.this, "对不起，你没有修改权限！", Toast.LENGTH_SHORT).show();
								}
							}
//							关闭游标
							c.close();
						}
				});
			}
//			关闭游标
			curs.close();
			break;
			
		default:
			break;
		}
	  }
	}
	
	//为返回键设置监听器，点击结束当前Activity
			public boolean onKeyDown(int keyCode, KeyEvent event) {
				if(keyCode == KeyEvent.KEYCODE_BACK
				   && event.getAction() == KeyEvent.ACTION_DOWN){
					Intent intent = new Intent();
					intent.setClass(SB_Safe.this,SB_Home.class);
					SB_Safe.this.startActivity(intent);
					finish();
				  }
				return true;
				} 
}
