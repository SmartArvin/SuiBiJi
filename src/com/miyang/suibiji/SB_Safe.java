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
		
//		�ѵ�ǰActivity���뵽CloseApplication��Activitylist��
		CloseApplication.getInstance().addActivity(this);
		
//		��ÿؼ���Id
		safe_back = (Button)findViewById(R.id.safe_back);
		safe_delete = (Button)findViewById(R.id.safe_delete);
		safe_backup = (Button)findViewById(R.id.safe_backup);
		safe_return = (Button)findViewById(R.id.safe_return);
		safe_change = (Button)findViewById(R.id.safe_change);
//		Ϊ�ؼ��󶨼�����
		safe_back.setOnClickListener(new safeListener());
		safe_delete.setOnClickListener(new safeListener());
		safe_backup.setOnClickListener(new safeListener());
		safe_return.setOnClickListener(new safeListener());
		safe_change.setOnClickListener(new safeListener());
	}
/*
 * ������������
	*/
//	������������
	class safeListener implements OnClickListener{
//		����DBHelper���ݿ⹤����
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
			//�˴�ֱ��newһ��Dialog�����������ʵ������ʱ��������
            final Dialog dialog_delete = new Dialog(SB_Safe.this, R.style.MyDialog);
            //��������ContentView
            dialog_delete.setContentView(R.layout.sb_dialog_safe_delete);
            dialog_delete.show();
            
//          ��ÿؼ���Id
            Button safe_delete_cancle = (Button)dialog_delete.findViewById(R.id.safe_delete_cancle);
            Button safe_delete_ok = (Button)dialog_delete.findViewById(R.id.safe_delete_ok);
//            Ϊȡ����ť�󶨼�����
            safe_delete_cancle.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dialog_delete.dismiss();
				}
			});
//            Ϊȷ��ɾ����ť�󶨼�����
            safe_delete_ok.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
//					����һ���������ڴ��ϵ�����ݱ�
					String[] tb_name = {"main","like"}; 
//					ѭ����ѯ���ݱ�������ѯ������뵽search���ݱ���
					for(int i = 0;i < tb_name.length ; i++){
//						���ȫ�����ݱ�
						safeHelper.delete(tb_name[i]);
					}
					dialog_delete.dismiss();
				}
			});
			break;
		case R.id.safe_backup:
//			����֮ǰɾ��ԭ���ı�������
			safeHelper.delete("backup");
			safeHelper.delete("backuplike");
			
//			ѭ����ѯ���ݿ��е�ϵ�����ݱ�,����main���ݱ�
//					ѭ����ѯ���ݱ�
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
		//			��ѯlike���ݱ�,����like���ݱ�
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
//					�ر��α�
					cu.close();
					cur.close();
//					��ɱ��ݺ����ʾ��Ϣ
			Toast.makeText(SB_Safe.this, "���ݱ�����ɣ�", Toast.LENGTH_SHORT).show();
			break;
			
		case R.id.safe_return:
			//�˴�ֱ��newһ��Dialog�����������ʵ������ʱ��������
            final Dialog dialog_return = new Dialog(SB_Safe.this, R.style.MyDialog);
            //��������ContentView
            dialog_return.setContentView(R.layout.sb_dialog_safe_return);
            dialog_return.show();
            
//          ��ÿؼ���Id
            Button safe_return_cancle = (Button)dialog_return.findViewById(R.id.safe_return_cancle);
            Button safe_return_ok = (Button)dialog_return.findViewById(R.id.safe_return_ok);
//            Ϊȡ����ť�󶨼�����
            safe_return_cancle.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog_return.dismiss();
				}
			});
//            Ϊȷ����ԭ��ť�󶨼�����
            safe_return_ok.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
//					��ԭ����֮ǰ����յ�ǰ���ݱ���Ϣ���������ݱ�����Ϣ�ظ�
						safeHelper.delete("main");
//					��ԭ����֮ǰ����ղ����ݱ����Ϣ,�������ݱ��������ظ�
					safeHelper.delete("like");
//						��ѯbackup���ݱ�,����backup���ݱ����Ϣ���뵽main���ݱ���
						Cursor c = safeHelper.rawQueryInf("backup");
						while(c.moveToNext()){
//								ѭ����ѯ���ݱ�������ѯ������뵽��Ӧ�����ݱ���
									ContentValues cv = new ContentValues();
									cv.put("photo_one", c.getString(c.getColumnIndex("photo_one")));
									cv.put("photo_two", c.getString(c.getColumnIndex("photo_two")));
									cv.put("photo_three", c.getString(c.getColumnIndex("photo_three")));
									cv.put("theme", c.getString(c.getColumnIndex("theme")));
									cv.put("date", c.getString(c.getColumnIndex("date")));
									cv.put("content", c.getString(c.getColumnIndex("content")));
									safeHelper.insert(cv, "main");
						}
//						�ر��α�
						c.close();
						
//					��ѯbackup_like���ݱ�����back_like���ݱ��е����ݲ��뵽like����
					Cursor cu = safeHelper.rawQueryInf("backuplike");
					ContentValues cvl = new ContentValues();
					while(cu.moveToNext()){
						cvl.put("photo_one", cu.getString(cu.getColumnIndex("photo_one")));
						cvl.put("photo_two", cu.getString(cu.getColumnIndex("photo_two")));
						cvl.put("photo_three", cu.getString(cu.getColumnIndex("photo_three")));
						cvl.put("theme", cu.getString(cu.getColumnIndex("theme")));
						cvl.put("date", cu.getString(cu.getColumnIndex("date")));
						cvl.put("content", cu.getString(cu.getColumnIndex("content")));
//						��like���ݱ��в�������
						safeHelper.insert(cvl, "like");
					}
//					�ر��α�
					cu.close();
					
//					����dialog
					dialog_return.dismiss();
					Toast.makeText(SB_Safe.this, "�����ѳɹ���ԭ��", Toast.LENGTH_SHORT).show();
				}
			});
			break;
			
		case R.id.safe_change:
			
			//�˴�ֱ��newһ��Dialog�����������ʵ������ʱ��������
            final Dialog dialog_change = new Dialog(SB_Safe.this, R.style.MyDialog);
            //��������ContentView
            dialog_change.setContentView(R.layout.sb_dialog_safe_change);
            dialog_change.show();
			
			Cursor curs = safeHelper.rawQueryCount("login");
			int cn = 0;
			while(curs.moveToNext()){
				cn = curs.getInt(0);
			}
//			���login���ݱ�Ϊ�գ�����ζ���û�ѡ���������ģʽ
			if(cn == 0){
				dialog_change.dismiss();
				Toast.makeText(SB_Safe.this, "��������Ϣ����ѡ�����ģʽ��", Toast.LENGTH_SHORT).show();
			}
//			���login���ݱ�Ϊ�գ�����ζ���û��Ѿ�ѡ���˼���ģʽ�������޸ĵ�½����
			else{

//	          ��ÿؼ���Id
	            Button safe_change_cancle = (Button)dialog_change.findViewById(R.id.safe_change_cancle);
	            Button safe_change_ok = (Button)dialog_change.findViewById(R.id.safe_change_ok);
//	            Ϊȡ����ť�󶨼�����
	            safe_change_cancle.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog_change.dismiss();
					}
				});
//	            Ϊȷ���޸İ�ť�󶨼�����
	            safe_change_ok.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
//						�õ�dialog������EditText�༭���Id
						EditText safe_change_et1 = (EditText)dialog_change.findViewById(R.id.safe_change_et1);
						EditText safe_change_et2 = (EditText)dialog_change.findViewById(R.id.safe_change_et2);
						EditText safe_change_et3 = (EditText)dialog_change.findViewById(R.id.safe_change_et3);
//						������������༭�������
						String safe_change_e1 = safe_change_et1.getText().toString();
						String safe_change_e2 = safe_change_et2.getText().toString();
						String safe_change_e3 = safe_change_et3.getText().toString();
							Cursor c = safeHelper.rawQueryInf("login");
							while(c.moveToNext()){
								String temp = c.getString(c.getColumnIndex("key1"));
								if(safe_change_e1.equals(temp)){
									if(safe_change_e2.isEmpty()&&safe_change_e3.isEmpty()){
//										����޸���Ϊ�������login���ݱ������,�������봦��
										safeHelper.delete("login");
										dialog_change.dismiss();
										Toast.makeText(SB_Safe.this, "��������գ�", Toast.LENGTH_SHORT).show();
									}else{
										if(safe_change_e2.equals(safe_change_e3)){
//											��������֮ǰ�����login���ݱ������
											safeHelper.delete("login");
//											����contentvalues���󣬷�װ��¼��Ϣ
											ContentValues cv = new ContentValues();
											cv.put("key1", safe_change_e2);
//											����������
											safeHelper.insert(cv, "login");
											dialog_change.dismiss();
											Toast.makeText(SB_Safe.this, "�޸ĳɹ���", Toast.LENGTH_SHORT).show();
										}else{
											Toast.makeText(SB_Safe.this, "��ȷ������������ȷ��", Toast.LENGTH_SHORT).show();
										}
									}
								}else{
									dialog_change.dismiss();
									Toast.makeText(SB_Safe.this, "�Բ�����û���޸�Ȩ�ޣ�", Toast.LENGTH_SHORT).show();
								}
							}
//							�ر��α�
							c.close();
						}
				});
			}
//			�ر��α�
			curs.close();
			break;
			
		default:
			break;
		}
	  }
	}
	
	//Ϊ���ؼ����ü����������������ǰActivity
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
