package com.miyang.suibiji;

import com.miyang.suibiji.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class SB_Like extends Activity{

	Button like_back;
	DBHelper likeHelper;
	RelativeLayout like_rl2;
	ListView like_ls;
	TextView txt1,txt2,txt3,photo1,photo2,photo3;
	Dialog dialog_like;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sb_like_activity);
		
//		�ѵ�ǰActivity���뵽CloseApplication��Activitylist��
		CloseApplication.getInstance().addActivity(this);
		
//		��ÿؼ���Id
		like_rl2 = (RelativeLayout)findViewById(R.id.like_rl2);
		like_ls = (ListView)findViewById(R.id.like_ls);
		like_back = (Button)findViewById(R.id.like_back);
		like_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SB_Like.this,SB_Home.class);
				SB_Like.this.startActivity(intent);
				finish();
			}
		});
		
		/*
		 * �����ݱ��е����ݰ󶨵�listview��
		*/
//		����DBHelper���ݿ⹤����
		likeHelper = new DBHelper(getApplicationContext());
		Cursor cursor = likeHelper.rawQueryCount("like");
		int count = 0;
		while(cursor.moveToNext()){
			count = cursor.getInt(0);
		}
		if(count == 0){
			like_rl2.setVisibility(1);
			like_ls.setVisibility(0);
		}else{
			Cursor c = likeHelper.rawQueryInf("like");
			inflateList(c);
//			�ж����curָ�����һ�����ݵ���һ�У���ر��α�
			if(c.isAfterLast()){
				c.close();
			}
		}
//		�ر��α�
		cursor.close();
		
		like_ls.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				View v = like_ls.getChildAt(position); 
//				���listview��item���ڲ�view
				photo1 = (TextView)v.findViewById(R.id.content_ls_photo1);//��Ƭ1������
				photo2 = (TextView)v.findViewById(R.id.content_ls_photo2);//��Ƭ2������
				photo3 = (TextView)v.findViewById(R.id.content_ls_photo3);//��Ƭ3������
				txt1 = (TextView)v.findViewById(R.id.content_ls_txt1);
				txt2 = (TextView)v.findViewById(R.id.content_ls_txt2);
				txt3 = (TextView)v.findViewById(R.id.content_ls_txt3);
                
				//�˴�ֱ��newһ��Dialog�����������ʵ������ʱ��������
	            dialog_like = new Dialog(SB_Like.this, R.style.MyDialog);
	            //��������ContentView
	            dialog_like.setContentView(R.layout.sb_dialog_like);
	            dialog_like.show();
	            
//            �õ�dialog�в鿴��ȡ���ղص�Id
			Button like_btn1 = (Button)dialog_like.findViewById(R.id.dialog_like_btn1);
			Button like_btn2 = (Button)dialog_like.findViewById(R.id.dialog_like_btn2);
			Button like_btn3 = (Button)dialog_like.findViewById(R.id.dialog_like_btn3);
//			Ϊ�鿴�����󶨼�����
			like_btn1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog_like.dismiss();
					Intent it = new Intent();
					it.putExtra("photo_one", photo1.getText().toString());
					it.putExtra("photo_two", photo2.getText().toString());
					it.putExtra("photo_three", photo3.getText().toString());
					it.putExtra("theme",txt1.getText().toString());
					it.putExtra("date",txt2.getText().toString());
					it.putExtra("content",txt3.getText().toString());
					it.setClass(SB_Like.this, SB_LikeCheck.class);
					SB_Like.this.startActivity(it);
					finish();
				}
			});
			
//			Ϊ�޸İ����󶨼�����
			like_btn2.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog_like.dismiss();
					Intent it = new Intent();
					it.putExtra("actname", "LikeActivity");
					it.putExtra("photo_one", photo1.getText().toString());
					it.putExtra("photo_two", photo2.getText().toString());
					it.putExtra("photo_three", photo3.getText().toString());
					it.putExtra("theme", txt1.getText().toString());
					it.putExtra("date", txt2.getText().toString());
					it.putExtra("content", txt3.getText().toString());
					it.setClass(SB_Like.this, SB_Change.class);
					SB_Like.this.startActivity(it);
					finish();
				}
			});
			
//			Ϊȡ���ղذ󶨼�����
			like_btn3.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog_like.dismiss();
//					�˴�ֱ��newһ��Dialog�����������ʵ������ʱ��������
			        final Dialog dialog_delete = new Dialog(SB_Like.this, R.style.MyDialog);
			        //��������ContentView
			        dialog_delete.setContentView(R.layout.sb_dialog_like_delete);
			        dialog_delete.show();
			        
//			        �õ�dialog������button��Id
					Button like_delete_cancle = (Button)dialog_delete.findViewById(R.id.like_delete_cancle);
					Button like_delete_ok = (Button)dialog_delete.findViewById(R.id.like_delete_ok);
//					Ϊȡ�������󶨼�����
					like_delete_cancle.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							dialog_delete.dismiss();
						}
					});
//				Ϊȷ�������󶨼�����
					like_delete_ok.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							Cursor c = likeHelper.rawQueryInf("like");
							while(c.moveToNext()){
								String delete_txt1 = c.getString(c.getColumnIndex("date"));
								if(txt2.getText().toString().equals(delete_txt1)){
									likeHelper.deleteDate("like", delete_txt1);
									Cursor cur = likeHelper.rawQueryInf("like");
									inflateList(cur);
//									�ж����curָ�����һ�����ݵ���һ�У���ر��α�
									if(cur.isAfterLast()){
										cur.close();
									}
//									�ж�like���ݱ��Ƿ�Ϊ��
									Cursor cursor = likeHelper.rawQueryCount("like");
									int count = 0;
									while(cursor.moveToNext()){
										count = cursor.getInt(0);
									}
									if(count == 0){
										like_rl2.setVisibility(1);
										like_ls.setVisibility(0);
									}
//									�ر��α�
									cursor.close();
								}
							}
//							�ر��α�
							c.close();
							dialog_delete.dismiss();
						}
						
					});
				}
			});
			}
		});
		
		
	}

//	����һ��������ʵ�ְѲ�ѯ�����������ݰ󶨵�listview��
	private void inflateList(Cursor cursor){
		like_ls = (ListView)findViewById(R.id.like_ls);
		/*
		 * ���SimpleCursorAdapter��ʹ��SimpleCursorAdapter���뱣֤������_id,��������г���
		*/
		SimpleCursorAdapter sc = new SimpleCursorAdapter(SB_Like.this, R.layout.sb_content_ls_item, cursor, 
				new String[]{"photo_one","photo_two","photo_three","theme","date","content"},
				new int[]{R.id.content_ls_photo1,R.id.content_ls_photo2,R.id.content_ls_photo3,R.id.content_ls_txt1,R.id.content_ls_txt2,R.id.content_ls_txt3},
				CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		like_ls.setAdapter(sc);
	}
	
	//Ϊ���ؼ����ü����������������ǰActivity
			public boolean onKeyDown(int keyCode, KeyEvent event) {
				if(keyCode == KeyEvent.KEYCODE_BACK
				   && event.getAction() == KeyEvent.ACTION_DOWN){
					Intent intent = new Intent();
					intent.setClass(SB_Like.this,SB_Home.class);
					SB_Like.this.startActivity(intent);
					finish();
				  }
				return true;
				} 
}
