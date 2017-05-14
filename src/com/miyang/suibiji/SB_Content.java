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
		
//		把当前Activity加入到CloseApplication的Activitylist中
		CloseApplication.getInstance().addActivity(this);
		
//		获得控件的Id
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
//		创建DBHelper数据库工具类
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
//			循环查询数据表
			Cursor cur = contentHelper.rawQueryInf("main");
//			调用函数将查询结果绑定到listview中
			inflateList(cur);
//			判断游标的位置，如果游标指向最后一项的后一项则关闭游标
			if(cur.isAfterLast()){
				cur.close();
			}
		}
//		关闭游标
		cursor.close();
		
//		为listview的item绑定监听器
		content_ls.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {
//				获得当前item的内容布局
				View v = content_ls.getChildAt(position); 
//				获得item的内部view
				photo1 = (TextView)v.findViewById(R.id.content_ls_photo1);//照片1的名称
				photo2 = (TextView)v.findViewById(R.id.content_ls_photo2);//照片2的名称
				photo3 = (TextView)v.findViewById(R.id.content_ls_photo3);//照片3的名称
				txt1 = (TextView)v.findViewById(R.id.content_ls_txt1);//主题
				txt2 = (TextView)v.findViewById(R.id.content_ls_txt2);//日期
				txt3 = (TextView)v.findViewById(R.id.content_ls_txt3);//内容
				//此处直接new一个Dialog对象出来，在实例化的时候传入主题
	            final Dialog dialog_list = new Dialog(SB_Content.this, R.style.MyDialog);
	            //设置它的ContentView
	            dialog_list.setContentView(R.layout.sb_dialog_content_list);
	            dialog_list.show();
	            
//            得到dialog中查看、修改、收藏、删除的Id
			Button list_btn1 = (Button)dialog_list.findViewById(R.id.content_list_btn1);
			Button list_btn2 = (Button)dialog_list.findViewById(R.id.content_list_btn2);
			Button list_btn3 = (Button)dialog_list.findViewById(R.id.content_list_btn3);
			Button list_btn4 = (Button)dialog_list.findViewById(R.id.content_list_btn4);
			
//			为查看按键绑定监听器
			list_btn1.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog_list.dismiss();
//					调用list_btn1的监听器函数
					list_btn1();
				}
			});
			
//			为修改按键绑定监听器
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
			
//			为收藏按键绑定监听器,需要判断是否已经收藏过
			list_btn3.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					int count = 0;
					ContentValues cv = new ContentValues();
//					查询数据表的全部信息
					Cursor cu = contentHelper.rawQueryInf("like");
//					查询like数据表的数据条数
					Cursor c = contentHelper.rawQueryCount("like");
					while(c.moveToNext()){
						count = c.getInt(0);
				/*
				 * 判断like数据表是否是空表，为空则执行数据插入操作
				 * 不为空，则检索数据表
				*/
						if(count==0){
								cv.put("photo_one", photo1.getText().toString());
								cv.put("photo_two", photo2.getText().toString());
								cv.put("photo_three", photo3.getText().toString());
								cv.put("theme", txt1.getText().toString());
								cv.put("date", txt2.getText().toString());
								cv.put("content", txt3.getText().toString());
								contentHelper.insert(cv, "like");
//								隐藏dialog窗口
									dialog_list.dismiss();
									Toast.makeText(SB_Content.this, "完成收藏！", Toast.LENGTH_SHORT).show();
						}
						else{
							ArrayList<String> al = new ArrayList<String>();
							String like_txt2 = null;
//							检索like数据表元素
							while(cu.moveToNext()){
								like_txt2 = cu.getString(cu.getColumnIndex("date"));
//								将like数据表中的date字段数据全部检索并存放在al数组中
								al.add(like_txt2);
							}
							int j = 0 ;
//							循环检索al数组元素
							while(j<al.size()){
//								如果数组元素与item中的txt2内容一致，则提示该账号已经收藏过，不进行数据插入
									if(txt2.getText().toString().equals(al.get(j))){
										dialog_list.dismiss();
										Toast.makeText(SB_Content.this, "已收藏", Toast.LENGTH_SHORT).show();
										break;
									}
//									如果数组元素与item中的txt2内容不一致，则将数组下标加1继续检索数组元素
									else{
										j++;
									}
								}
//							判断数组元素是否全部检索完毕，如果检索完毕（即数组所有元素与item中的txt2内容不一致）
//							，此时的j=al.size(),然后执行插入数据
							if(j>=al.size()){
								cv.put("photo_one", photo1.getText().toString());
								cv.put("photo_two", photo2.getText().toString());
								cv.put("photo_three", photo3.getText().toString());
								cv.put("theme", txt1.getText().toString());
								cv.put("date", txt2.getText().toString());
								cv.put("content", txt3.getText().toString());
								contentHelper.insert(cv, "like");
							
//						隐藏dialog窗口
							dialog_list.dismiss();
							Toast.makeText(SB_Content.this, "完成收藏！", Toast.LENGTH_SHORT).show();
							}
						}
								
					 }	
//					关闭游标
					cu.close();
					c.close();
				 }
			});
//			为删除按键绑定监听器
			list_btn4.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog_list.dismiss();
//					调用list_btn5的监听器函数
					list_btn4();
				}
			});
			}
		});
	}

	/*
	 * 创建以上四个Button的监听器类
	 * list_btn1()
	 * list_btn2()
	 * list_btn3()
	 * list_btn4()
	 * 
	*/
	
//	这是查看信息
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
	
	
//	这个是删除账号信息，加确认对话框
	private void list_btn4(){
//		此处直接new一个Dialog对象出来，在实例化的时候传入主题
        final Dialog dialog_delete = new Dialog(SB_Content.this, R.style.MyDialog);
        //设置它的ContentView
        dialog_delete.setContentView(R.layout.sb_dialog_list_delete);
        dialog_delete.show();
        
//        得到dialog中三个TextView的Id
		Button list_delete_cancle = (Button)dialog_delete.findViewById(R.id.list_delete_cancle);
		Button list_delete_ok = (Button)dialog_delete.findViewById(R.id.list_delete_ok);
//		为取消删除绑定监听器
		list_delete_cancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog_delete.dismiss();
			}
		});
//        为确定删除按钮绑定监听器
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
//								判断游标的位置，如果游标指向最后一项的后一项则关闭游标
								if(cu.isAfterLast()){
									cu.close();
								}
//							判断该数据表是否为空	
								Cursor cursor = contentHelper.rawQueryCount("main");
								int count = 0;
								while(cursor.moveToNext()){
									count = cursor.getInt(0);
								}
								if(count == 0){
									content_rl2.setVisibility(1);
									content_ls.setVisibility(0);
								}
//								关闭游标
								cursor.close();
							}
						}
//				关闭游标
				c.close();
						dialog_delete.dismiss();
					}
		});
	}
	
//	定义一个函数用来将查询到的数据绑定到listview中
	private void inflateList(Cursor cursor){
		content_ls = (ListView)findViewById(R.id.content_ls);
		/*
		 * 填充SimpleCursorAdapter，使用SimpleCursorAdapter必须保证主键是_id,否则会运行出错
		*/
		SimpleCursorAdapter sc = new SimpleCursorAdapter(SB_Content.this, R.layout.sb_content_ls_item, cursor, 
				new String[]{"photo_one","photo_two","photo_three","theme","date","content"},
				new int[]{R.id.content_ls_photo1,R.id.content_ls_photo2,R.id.content_ls_photo3,R.id.content_ls_txt1,R.id.content_ls_txt2,R.id.content_ls_txt3},
				CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		content_ls.setAdapter(sc);
	}
	
	
	//为返回键设置监听器，点击结束当前Activity
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
