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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class SB_Content extends Activity{

	Button content_back;
	RelativeLayout content_rl2;
	ListView content_ls;
	DBHelper contentHelper;
	TextView txt1,txt2,txt3,photo1,photo2,photo3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sb_content_activity);
		
//		�ѵ�ǰActivity���뵽CloseApplication��Activitylist��
		CloseApplication.getInstance().addActivity(this);
		
//		��ÿؼ���Id
		content_rl2 = (RelativeLayout)findViewById(R.id.content_rl2);
		content_ls = (ListView)findViewById(R.id.content_ls);
		content_back = (Button)findViewById(R.id.content_back);
		content_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SB_Content.this,SB_Home.class);
				SB_Content.this.startActivity(intent);
				finish();
			}
		});
//		����DBHelper���ݿ⹤����
		contentHelper = new DBHelper(getApplicationContext());
		Cursor cursor = contentHelper.rawQueryCount("main");
		int count = 0 ;
		while(cursor.moveToNext()){
			count = cursor.getInt(0);
		}
		if(count==0){
			content_rl2.setVisibility(1);
			content_ls.setVisibility(0);
		}else{
//			ѭ����ѯ���ݱ�
			Cursor cur = contentHelper.rawQueryInf("main");
//			���ú�������ѯ����󶨵�listview��
			inflateList(cur);
//			�ж��α��λ�ã�����α�ָ�����һ��ĺ�һ����ر��α�
			if(cur.isAfterLast()){
				cur.close();
			}
		}
//		�ر��α�
		cursor.close();
		
//		Ϊlistview��item�󶨼�����
		content_ls.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {
//				��õ�ǰitem�����ݲ���
				View v = content_ls.getChildAt(position); 
//				���item���ڲ�view
				photo1 = (TextView)v.findViewById(R.id.content_ls_photo1);//��Ƭ1������
				photo2 = (TextView)v.findViewById(R.id.content_ls_photo2);//��Ƭ2������
				photo3 = (TextView)v.findViewById(R.id.content_ls_photo3);//��Ƭ3������
				txt1 = (TextView)v.findViewById(R.id.content_ls_txt1);//����
				txt2 = (TextView)v.findViewById(R.id.content_ls_txt2);//����
				txt3 = (TextView)v.findViewById(R.id.content_ls_txt3);//����
				//�˴�ֱ��newһ��Dialog�����������ʵ������ʱ��������
	            final Dialog dialog_list = new Dialog(SB_Content.this, R.style.MyDialog);
	            //��������ContentView
	            dialog_list.setContentView(R.layout.sb_dialog_content_list);
	            dialog_list.show();
	            
//            �õ�dialog�в鿴���޸ġ��ղء�ɾ����Id
			Button list_btn1 = (Button)dialog_list.findViewById(R.id.content_list_btn1);
			Button list_btn2 = (Button)dialog_list.findViewById(R.id.content_list_btn2);
			Button list_btn3 = (Button)dialog_list.findViewById(R.id.content_list_btn3);
			Button list_btn4 = (Button)dialog_list.findViewById(R.id.content_list_btn4);
			
//			Ϊ�鿴�����󶨼�����
			list_btn1.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog_list.dismiss();
//					����list_btn1�ļ���������
					list_btn1();
				}
			});
			
//			Ϊ�޸İ����󶨼�����
			list_btn2.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog_list.dismiss();
					Intent it = new Intent();
					it.putExtra("actname", "ContentActivity");
					it.putExtra("photo_one", photo1.getText().toString());
					it.putExtra("photo_two", photo2.getText().toString());
					it.putExtra("photo_three", photo3.getText().toString());
					it.putExtra("theme", txt1.getText().toString());
					it.putExtra("date", txt2.getText().toString());
					it.putExtra("content", txt3.getText().toString());
					it.setClass(SB_Content.this, SB_Change.class);
					SB_Content.this.startActivity(it);
					finish();
				}
			});
			
//			Ϊ�ղذ����󶨼�����,��Ҫ�ж��Ƿ��Ѿ��ղع�
			list_btn3.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					int count = 0;
					ContentValues cv = new ContentValues();
//					��ѯ���ݱ��ȫ����Ϣ
					Cursor cu = contentHelper.rawQueryInf("like");
//					��ѯlike���ݱ����������
					Cursor c = contentHelper.rawQueryCount("like");
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
								contentHelper.insert(cv, "like");
//								����dialog����
									dialog_list.dismiss();
									Toast.makeText(SB_Content.this, "����ղأ�", Toast.LENGTH_SHORT).show();
						}
						else{
							ArrayList<String> al = new ArrayList<String>();
							String like_txt2 = null;
//							����like���ݱ�Ԫ��
							while(cu.moveToNext()){
								like_txt2 = cu.getString(cu.getColumnIndex("date"));
//								��like���ݱ��е�date�ֶ�����ȫ�������������al������
								al.add(like_txt2);
							}
							int j = 0 ;
//							ѭ������al����Ԫ��
							while(j<al.size()){
//								�������Ԫ����item�е�txt2����һ�£�����ʾ���˺��Ѿ��ղع������������ݲ���
									if(txt2.getText().toString().equals(al.get(j))){
										dialog_list.dismiss();
										Toast.makeText(SB_Content.this, "���ղ�", Toast.LENGTH_SHORT).show();
										break;
									}
//									�������Ԫ����item�е�txt2���ݲ�һ�£��������±��1������������Ԫ��
									else{
										j++;
									}
								}
//							�ж�����Ԫ���Ƿ�ȫ��������ϣ����������ϣ�����������Ԫ����item�е�txt2���ݲ�һ�£�
//							����ʱ��j=al.size(),Ȼ��ִ�в�������
							if(j>=al.size()){
								cv.put("photo_one", photo1.getText().toString());
								cv.put("photo_two", photo2.getText().toString());
								cv.put("photo_three", photo3.getText().toString());
								cv.put("theme", txt1.getText().toString());
								cv.put("date", txt2.getText().toString());
								cv.put("content", txt3.getText().toString());
								contentHelper.insert(cv, "like");
							
//						����dialog����
							dialog_list.dismiss();
							Toast.makeText(SB_Content.this, "����ղأ�", Toast.LENGTH_SHORT).show();
							}
						}
								
					 }	
//					�ر��α�
					cu.close();
					c.close();
				 }
			});
//			Ϊɾ�������󶨼�����
			list_btn4.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog_list.dismiss();
//					����list_btn5�ļ���������
					list_btn4();
				}
			});
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
		it.putExtra("actname", "ContentActivity");
		it.putExtra("photo_one", photo1.getText().toString());
		it.putExtra("photo_two", photo2.getText().toString());
		it.putExtra("photo_three", photo3.getText().toString());
		it.putExtra("theme",txt1.getText().toString());
		it.putExtra("date",txt2.getText().toString());
		it.putExtra("content",txt3.getText().toString());
		it.setClass(SB_Content.this, SB_ContentCheck.class);
		SB_Content.this.startActivity(it);
		finish();
	}
	
	
//	�����ɾ���˺���Ϣ����ȷ�϶Ի���
	private void list_btn4(){
//		�˴�ֱ��newһ��Dialog�����������ʵ������ʱ��������
        final Dialog dialog_delete = new Dialog(SB_Content.this, R.style.MyDialog);
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
				Cursor c = contentHelper.rawQueryInf("main");
				while(c.moveToNext()){
					String delete_txt2 = c.getString(c.getColumnIndex("date"));
							if(txt2.getText().toString().equals(delete_txt2)){
								contentHelper.deleteDate("main", delete_txt2);
								Cursor cu = contentHelper.rawQueryInf("main");
								inflateList(cu);
//								�ж��α��λ�ã�����α�ָ�����һ��ĺ�һ����ر��α�
								if(cu.isAfterLast()){
									cu.close();
								}
//							�жϸ����ݱ��Ƿ�Ϊ��	
								Cursor cursor = contentHelper.rawQueryCount("main");
								int count = 0;
								while(cursor.moveToNext()){
									count = cursor.getInt(0);
								}
								if(count == 0){
									content_rl2.setVisibility(1);
									content_ls.setVisibility(0);
								}
//								�ر��α�
								cursor.close();
							}
						}
//				�ر��α�
				c.close();
						dialog_delete.dismiss();
					}
		});
	}
	
//	����һ��������������ѯ�������ݰ󶨵�listview��
	private void inflateList(Cursor cursor){
		content_ls = (ListView)findViewById(R.id.content_ls);
		/*
		 * ���SimpleCursorAdapter��ʹ��SimpleCursorAdapter���뱣֤������_id,��������г���
		*/
		SimpleCursorAdapter sc = new SimpleCursorAdapter(SB_Content.this, R.layout.sb_content_ls_item, cursor, 
				new String[]{"photo_one","photo_two","photo_three","theme","date","content"},
				new int[]{R.id.content_ls_photo1,R.id.content_ls_photo2,R.id.content_ls_photo3,R.id.content_ls_txt1,R.id.content_ls_txt2,R.id.content_ls_txt3},
				CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		content_ls.setAdapter(sc);
	}
	
	
	//Ϊ���ؼ����ü����������������ǰActivity
			public boolean onKeyDown(int keyCode, KeyEvent event) {
				if(keyCode == KeyEvent.KEYCODE_BACK
				   && event.getAction() == KeyEvent.ACTION_DOWN){
					Intent intent = new Intent();
					intent.setClass(SB_Content.this,SB_Home.class);
					SB_Content.this.startActivity(intent);
					finish();
				  }
				return true;
				} 
}
