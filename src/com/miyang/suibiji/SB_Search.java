package com.miyang.suibiji;

import java.util.ArrayList;

import com.miyang.suibiji.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SB_Search extends Activity{

	Button search_back,search_ok;
	EditText search_txt;
	ListView search_ls;
	DBHelper searchHelper;
	TextView txt1,txt2,txt3,photo1,photo2,photo3;
	String back_txt = null;
	Dialog dialog_list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sb_search_activity);
		
//		����DBHelper���ݿ⹤����
		searchHelper = new DBHelper(getApplicationContext());
//		���search���ݱ�����editΪ��ʱ�������������������
		searchHelper.delete("search");
		
//		�ѵ�ǰActivity���뵽CloseApplication��Activitylist��
		CloseApplication.getInstance().addActivity(this);
		
//		��ÿؼ���Id
		search_ls = (ListView)findViewById(R.id.search_ls);
		search_txt = (EditText)findViewById(R.id.search_txt);
		search_back = (Button)findViewById(R.id.search_back);
		search_ok = (Button)findViewById(R.id.search_ok);
//		������鿴���޸�ʱ����ת�ص�ǰActivity������ʾ�ر�ǰ�Ľ�������
		backExtra();
		
//		Ϊ�ؼ��󶨼�����
		search_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SB_Search.this,SB_Home.class);
				SB_Search.this.startActivity(intent);
				finish();
			}
		});
		search_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String txt = search_txt.getText().toString();
//				��������֮ǰ������ݱ�
				searchHelper.delete("search");
				if(!txt.isEmpty()){
	//					ģ����ѯ���ݱ�
						Cursor cu = searchHelper.rawQueryLike("main",txt);
	//					����contentvalues���󣬷�װ��¼��Ϣ
						ContentValues c = new ContentValues();
						while(cu.moveToNext()){
							c.put("photo_one", cu.getString(cu.getColumnIndex("photo_one")));
							c.put("photo_two", cu.getString(cu.getColumnIndex("photo_two")));
							c.put("photo_three", cu.getString(cu.getColumnIndex("photo_three")));
							c.put("theme", cu.getString(cu.getColumnIndex("theme")));
							c.put("date",cu.getString(cu.getColumnIndex("date")));
							c.put("content", cu.getString(cu.getColumnIndex("content")));
	//						��search���ݱ��в�������
							searchHelper.insert(c, "search");
					}
					Cursor cur = searchHelper.rawQueryCount("search");
					int count = 0;
				    while(cur.moveToNext())
				    {
				        count = cur.getInt(0);
				    }
				    if(count == 0){
				    	Toast.makeText(SB_Search.this, "��ؼ�¼�����ڣ�", Toast.LENGTH_SHORT).show();
				    }else{
	//					��ѯsearch���ݱ������ݱ����ݲ��뵽listview��
						Cursor cursor = searchHelper.rawQueryInf("search");
							inflateList(cursor);
//							�ж�����α��ƶ������һ�����ݵĺ�һ��ʱ���ر��α�
							if(cursor.isAfterLast()){
								cursor.close();
							}
//							Ϊlistview��item�󶨼�����
							search_ls.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> arg0, View view,
										int position, long arg3) {
//									��õ�ǰitem�����ݲ���
									View v = search_ls.getChildAt(position); 
//									���item���ڲ�view
									photo1 = (TextView)v.findViewById(R.id.content_ls_photo1);//��Ƭ1������
									photo2 = (TextView)v.findViewById(R.id.content_ls_photo2);//��Ƭ2������
									photo3 = (TextView)v.findViewById(R.id.content_ls_photo3);//��Ƭ3������
									txt1 = (TextView)v.findViewById(R.id.content_ls_txt1);//����
									txt2 = (TextView)v.findViewById(R.id.content_ls_txt2);//����
									txt3 = (TextView)v.findViewById(R.id.content_ls_txt3);//����
									//�˴�ֱ��newһ��Dialog�����������ʵ������ʱ��������
						            dialog_list = new Dialog(SB_Search.this, R.style.MyDialog);
						            //��������ContentView
						            dialog_list.setContentView(R.layout.sb_dialog_content_list);
						            dialog_list.show();
						            
//					            �õ�dialog�в鿴���޸ġ��ղء�ɾ����Id
								Button list_btn1 = (Button)dialog_list.findViewById(R.id.content_list_btn1);
								Button list_btn2 = (Button)dialog_list.findViewById(R.id.content_list_btn2);
								Button list_btn3 = (Button)dialog_list.findViewById(R.id.content_list_btn3);
								Button list_btn4 = (Button)dialog_list.findViewById(R.id.content_list_btn4);
								
//								Ϊ�鿴�����󶨼�����
								list_btn1.setOnClickListener(new OnClickListener() {
									public void onClick(View v) {
										// TODO Auto-generated method stub
										dialog_list.dismiss();
//										����list_btn1�ļ���������
										list_btn1();
									}
								});
								
//								Ϊ�޸İ����󶨼�����
								list_btn2.setOnClickListener(new OnClickListener() {
									public void onClick(View v) {
										// TODO Auto-generated method stub
										dialog_list.dismiss();
										list_btn2();
									}
								});
								
//								Ϊ�ղذ����󶨼�����,��Ҫ�ж��Ƿ��Ѿ��ղع�
								list_btn3.setOnClickListener(new OnClickListener() {
									public void onClick(View v) {
										list_btn3();
									 }
								});
//								Ϊɾ�������󶨼�����
								list_btn4.setOnClickListener(new OnClickListener() {
									public void onClick(View v) {
										// TODO Auto-generated method stub
										dialog_list.dismiss();
//										����list_btn5�ļ���������
										list_btn4();
									}
								});
								}
							});
				    }
//				    �ر��α�
				    cu.close();
				    cur.close();
				}else{
					search_ls.setVisibility(View.GONE);
					Toast.makeText(SB_Search.this, "�������ѯ�ؼ��֣�", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	/*
	 * ���������ĸ�Button�ļ�������
	 * list_btn1()
	 * list_btn2()
	 * list_btn3()
	 * list_btn4()
	 * 
	*/
	
//	���ǲ鿴��Ϣ
	private void list_btn1(){
		Intent it = new Intent();
		it.putExtra("actname", "SearchActivity");
		it.putExtra("search_txt",search_txt.getText().toString());
		it.putExtra("photo_one", photo1.getText().toString());
		it.putExtra("photo_two", photo2.getText().toString());
		it.putExtra("photo_three", photo3.getText().toString());
		it.putExtra("theme",txt1.getText().toString());
		it.putExtra("date",txt2.getText().toString());
		it.putExtra("content",txt3.getText().toString());
		it.setClass(SB_Search.this, SB_ContentCheck.class);
		SB_Search.this.startActivity(it);
		finish();
	}
//	�����޸���Ϣ
	private void list_btn2(){
		Intent it = new Intent();
		it.putExtra("actname", "SearchActivity");
		it.putExtra("search_txt",search_txt.getText().toString());
		it.putExtra("photo_one", photo1.getText().toString());
		it.putExtra("photo_two", photo2.getText().toString());
		it.putExtra("photo_three", photo3.getText().toString());
		it.putExtra("theme", txt1.getText().toString());
		it.putExtra("date", txt2.getText().toString());
		it.putExtra("content", txt3.getText().toString());
		it.setClass(SB_Search.this, SB_Change.class);
		SB_Search.this.startActivity(it);
		finish();
	}
//	�����ղ���Ϣ�ĺ���
	private void list_btn3(){
		int count = 0;
		ContentValues cv = new ContentValues();
//		��ѯ���ݱ��ȫ����Ϣ
		Cursor cu = searchHelper.rawQueryInf("like");
//		��ѯlike���ݱ����������
		Cursor c = searchHelper.rawQueryCount("like");
		while(c.moveToNext()){
			count = c.getInt(0);
	/*
	 * �ж�like���ݱ��Ƿ��ǿձ�Ϊ����ִ�����ݲ������
	 * ��Ϊ�գ���������ݱ�
	*/
			if(count==0){
					cv.put("photo_one", photo1.getText().toString());
					cv.put("photo_two", photo2.getText().toString());
					cv.put("photo_three", photo3.getText().toString());
					cv.put("theme", txt1.getText().toString());
					cv.put("date", txt2.getText().toString());
					cv.put("content", txt3.getText().toString());
					searchHelper.insert(cv, "like");
//					����dialog����
						dialog_list.dismiss();
						Toast.makeText(SB_Search.this, "����ղأ�", Toast.LENGTH_SHORT).show();
			}
			else{
				ArrayList<String> al = new ArrayList<String>();
				String like_txt2 = null;
//				����like���ݱ�Ԫ��
				while(cu.moveToNext()){
					like_txt2 = cu.getString(cu.getColumnIndex("date"));
//					��like���ݱ��е�date�ֶ�����ȫ�������������al������
					al.add(like_txt2);
				}
				int j = 0 ;
//				ѭ������al����Ԫ��
				while(j<al.size()){
//					�������Ԫ����item�е�txt2����һ�£�����ʾ���˺��Ѿ��ղع������������ݲ���
						if(txt2.getText().toString().equals(al.get(j))){
							dialog_list.dismiss();
							Toast.makeText(SB_Search.this, "���ղ�", Toast.LENGTH_SHORT).show();
							break;
						}
//						�������Ԫ����item�е�txt2���ݲ�һ�£��������±��1������������Ԫ��
						else{
							j++;
						}
					}
//				�ж�����Ԫ���Ƿ�ȫ��������ϣ����������ϣ�����������Ԫ����item�е�txt2���ݲ�һ�£�
//				����ʱ��j=al.size(),Ȼ��ִ�в�������
				if(j>=al.size()){
					cv.put("photo_one", photo1.getText().toString());
					cv.put("photo_two", photo2.getText().toString());
					cv.put("photo_three", photo3.getText().toString());
					cv.put("theme", txt1.getText().toString());
					cv.put("date", txt2.getText().toString());
					cv.put("content", txt3.getText().toString());
					searchHelper.insert(cv, "like");
				
//			����dialog����
				dialog_list.dismiss();
				Toast.makeText(SB_Search.this, "����ղأ�", Toast.LENGTH_SHORT).show();
				}
			}
					
		 }	
//		�ر��α�
		cu.close();
		c.close();
	}
//	�����ɾ���˺���Ϣ����ȷ�϶Ի���
	private void list_btn4(){
//		�˴�ֱ��newһ��Dialog�����������ʵ������ʱ��������
        final Dialog dialog_delete = new Dialog(SB_Search.this, R.style.MyDialog);
        //��������ContentView
        dialog_delete.setContentView(R.layout.sb_dialog_list_delete);
        dialog_delete.show();
        
//        �õ�dialog������TextView��Id
		Button list_delete_cancle = (Button)dialog_delete.findViewById(R.id.list_delete_cancle);
		Button list_delete_ok = (Button)dialog_delete.findViewById(R.id.list_delete_ok);
//		Ϊȡ��ɾ���󶨼�����
		list_delete_cancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog_delete.dismiss();
			}
		});
//        Ϊȷ��ɾ����ť�󶨼�����
		list_delete_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Cursor c = searchHelper.rawQueryInf("main");
				while(c.moveToNext()){
					String delete_txt2 = c.getString(c.getColumnIndex("date"));
							if(txt2.getText().toString().equals(delete_txt2)){
								searchHelper.deleteDate("main", delete_txt2);
							}
						}
//				�ر��α�
				c.close();
						dialog_delete.dismiss();
						
						String txt = search_txt.getText().toString();
//						��������֮ǰ������ݱ�
						searchHelper.delete("search");
						if(!txt.isEmpty()){
			//					ģ����ѯ���ݱ�
								Cursor cu = searchHelper.rawQueryLike("main",txt);
			//					����contentvalues���󣬷�װ��¼��Ϣ
								ContentValues cv = new ContentValues();
								while(cu.moveToNext()){
									cv.put("photo_one", cu.getString(cu.getColumnIndex("photo_one")));
									cv.put("photo_two", cu.getString(cu.getColumnIndex("photo_two")));
									cv.put("photo_three", cu.getString(cu.getColumnIndex("photo_three")));
									cv.put("theme", cu.getString(cu.getColumnIndex("theme")));
									cv.put("date",cu.getString(cu.getColumnIndex("date")));
									cv.put("content", cu.getString(cu.getColumnIndex("content")));
			//						��search���ݱ��в�������
									searchHelper.insert(cv, "search");
							}
								
							Cursor cur = searchHelper.rawQueryCount("search");
							int count = 0;
						    while(cur.moveToNext())
						    {
						        count = cur.getInt(0);
						    }
						    if(count == 0){
						    	search_ls.setAdapter(null);
						    	Toast.makeText(SB_Search.this, "��ؼ�¼�����ڣ�", Toast.LENGTH_SHORT).show();
						    }else{
//								��ѯsearch���ݱ������ݱ����ݲ��뵽listview��
								Cursor cursor = searchHelper.rawQueryInf("search");
									inflateList(cursor);
//									�ж�����α��ƶ������һ�����ݵĺ�һ��ʱ���ر��α�
									if(cursor.isAfterLast()){
										cursor.close();
									}
//									�ر��α�
									cur.close();
						    }
					}
			}
		});
	}
	/*
	 * ���鿴���޸���ɺ��˳�����ǰActivityʱ
	 * ��ǰ����Ҫ��ʾ�رյ�����
	*/
	private void backExtra(){
//		���鿴���޸���ɺ��˳�����ǰActivity
		Intent it = getIntent();
		back_txt = it.getStringExtra("back_txt");
		if(back_txt!=null){
			search_txt.setText(back_txt);
			//ģ����ѯ���ݱ�
			Cursor cu = searchHelper.rawQueryLike("main",back_txt);
//					����contentvalues���󣬷�װ��¼��Ϣ
			ContentValues c = new ContentValues();
			while(cu.moveToNext()){
				c.put("photo_one", cu.getString(cu.getColumnIndex("photo_one")));
				c.put("photo_two", cu.getString(cu.getColumnIndex("photo_two")));
				c.put("photo_three", cu.getString(cu.getColumnIndex("photo_three")));
				c.put("theme", cu.getString(cu.getColumnIndex("theme")));
				c.put("date",cu.getString(cu.getColumnIndex("date")));
				c.put("content", cu.getString(cu.getColumnIndex("content")));
//						��search���ݱ��в�������
				searchHelper.insert(c, "search");
		    }
//					��ѯsearch���ݱ������ݱ����ݲ��뵽listview��
			Cursor cursor = searchHelper.rawQueryInf("search");
				inflateList(cursor);
//				�ж�����α��ƶ������һ�����ݵĺ�һ��ʱ���ر��α�
				if(cursor.isAfterLast()){
					cursor.close();
				}
				cu.close();
				
//				Ϊlistview��item�󶨼�����
				search_ls.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View view,
							int position, long arg3) {
//						��õ�ǰitem�����ݲ���
						View v = search_ls.getChildAt(position); 
//						���item���ڲ�view
						photo1 = (TextView)v.findViewById(R.id.content_ls_photo1);//��Ƭ1������
						photo2 = (TextView)v.findViewById(R.id.content_ls_photo2);//��Ƭ2������
						photo3 = (TextView)v.findViewById(R.id.content_ls_photo3);//��Ƭ3������
						txt1 = (TextView)v.findViewById(R.id.content_ls_txt1);//����
						txt2 = (TextView)v.findViewById(R.id.content_ls_txt2);//����
						txt3 = (TextView)v.findViewById(R.id.content_ls_txt3);//����
						//�˴�ֱ��newһ��Dialog�����������ʵ������ʱ��������
			            dialog_list = new Dialog(SB_Search.this, R.style.MyDialog);
			            //��������ContentView
			            dialog_list.setContentView(R.layout.sb_dialog_content_list);
			            dialog_list.show();
			            
//		            �õ�dialog�в鿴���޸ġ��ղء�ɾ����Id
					Button list_btn1 = (Button)dialog_list.findViewById(R.id.content_list_btn1);
					Button list_btn2 = (Button)dialog_list.findViewById(R.id.content_list_btn2);
					Button list_btn3 = (Button)dialog_list.findViewById(R.id.content_list_btn3);
					Button list_btn4 = (Button)dialog_list.findViewById(R.id.content_list_btn4);
					
//					Ϊ�鿴�����󶨼�����
					list_btn1.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog_list.dismiss();
//							����list_btn1�ļ���������
							list_btn1();
						}
					});
					
//					Ϊ�޸İ����󶨼�����
					list_btn2.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog_list.dismiss();
							list_btn2();
						}
					});
					
//					Ϊ�ղذ����󶨼�����,��Ҫ�ж��Ƿ��Ѿ��ղع�
					list_btn3.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							list_btn3();
						 }
					});
//					Ϊɾ�������󶨼�����
					list_btn4.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog_list.dismiss();
//							����list_btn5�ļ���������
							list_btn4();
						}
					});
					}
				});
		}
	}
	
//	����һ��������������ѯ�������ݰ󶨵�listview��
	private void inflateList(Cursor cursor){
		search_ls = (ListView)findViewById(R.id.search_ls);
		/*
		 * ���SimpleCursorAdapter��ʹ��SimpleCursorAdapter���뱣֤������_id,��������г���
		*/
		SimpleCursorAdapter sc = new SimpleCursorAdapter(SB_Search.this, R.layout.sb_content_ls_item, cursor, 
				new String[]{"photo_one","photo_two","photo_three","theme","date","content"},
				new int[]{R.id.content_ls_photo1,R.id.content_ls_photo2,R.id.content_ls_photo3,R.id.content_ls_txt1,R.id.content_ls_txt2,R.id.content_ls_txt3},
				CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		search_ls.setAdapter(sc);
	}
	

	//Ϊ���ؼ����ü����������������ǰActivity
			public boolean onKeyDown(int keyCode, KeyEvent event) {
				if(keyCode == KeyEvent.KEYCODE_BACK
				   && event.getAction() == KeyEvent.ACTION_DOWN){
					Intent intent = new Intent();
					intent.setClass(SB_Search.this,SB_Home.class);
					SB_Search.this.startActivity(intent);
					finish();
				  }
				return true;
				} 
}
