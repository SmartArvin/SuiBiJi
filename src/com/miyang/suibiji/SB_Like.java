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
		
//		把当前Activity加入到CloseApplication的Activitylist中
		CloseApplication.getInstance().addActivity(this);
		
//		获得控件的Id
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
		 * 将数据表中的数据绑定到listview中
		*/
//		创建DBHelper数据库工具类
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
//			判断如果cur指向最后一个数据的下一行，则关闭游标
			if(c.isAfterLast()){
				c.close();
			}
		}
//		关闭游标
		cursor.close();
		
		like_ls.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				View v = like_ls.getChildAt(position); 
//				获得listview中item的内部view
				photo1 = (TextView)v.findViewById(R.id.content_ls_photo1);//照片1的名称
				photo2 = (TextView)v.findViewById(R.id.content_ls_photo2);//照片2的名称
				photo3 = (TextView)v.findViewById(R.id.content_ls_photo3);//照片3的名称
				txt1 = (TextView)v.findViewById(R.id.content_ls_txt1);
				txt2 = (TextView)v.findViewById(R.id.content_ls_txt2);
				txt3 = (TextView)v.findViewById(R.id.content_ls_txt3);
                
				//此处直接new一个Dialog对象出来，在实例化的时候传入主题
	            dialog_like = new Dialog(SB_Like.this, R.style.MyDialog);
	            //设置它的ContentView
	            dialog_like.setContentView(R.layout.sb_dialog_like);
	            dialog_like.show();
	            
//            得到dialog中查看和取消收藏的Id
			Button like_btn1 = (Button)dialog_like.findViewById(R.id.dialog_like_btn1);
			Button like_btn2 = (Button)dialog_like.findViewById(R.id.dialog_like_btn2);
			Button like_btn3 = (Button)dialog_like.findViewById(R.id.dialog_like_btn3);
//			为查看按键绑定监听器
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
			
//			为修改按键绑定监听器
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
			
//			为取消收藏绑定监听器
			like_btn3.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog_like.dismiss();
//					此处直接new一个Dialog对象出来，在实例化的时候传入主题
			        final Dialog dialog_delete = new Dialog(SB_Like.this, R.style.MyDialog);
			        //设置它的ContentView
			        dialog_delete.setContentView(R.layout.sb_dialog_like_delete);
			        dialog_delete.show();
			        
//			        得到dialog中两个button的Id
					Button like_delete_cancle = (Button)dialog_delete.findViewById(R.id.like_delete_cancle);
					Button like_delete_ok = (Button)dialog_delete.findViewById(R.id.like_delete_ok);
//					为取消按键绑定监听器
					like_delete_cancle.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							dialog_delete.dismiss();
						}
					});
//				为确定按键绑定监听器
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
//									判断如果cur指向最后一个数据的下一行，则关闭游标
									if(cur.isAfterLast()){
										cur.close();
									}
//									判断like数据表是否为空
									Cursor cursor = likeHelper.rawQueryCount("like");
									int count = 0;
									while(cursor.moveToNext()){
										count = cursor.getInt(0);
									}
									if(count == 0){
										like_rl2.setVisibility(1);
										like_ls.setVisibility(0);
									}
//									关闭游标
									cursor.close();
								}
							}
//							关闭游标
							c.close();
							dialog_delete.dismiss();
						}
						
					});
				}
			});
			}
		});
		
		
	}

//	定义一个函数，实现把查询到的数据内容绑定到listview中
	private void inflateList(Cursor cursor){
		like_ls = (ListView)findViewById(R.id.like_ls);
		/*
		 * 填充SimpleCursorAdapter，使用SimpleCursorAdapter必须保证主键是_id,否则会运行出错
		*/
		SimpleCursorAdapter sc = new SimpleCursorAdapter(SB_Like.this, R.layout.sb_content_ls_item, cursor, 
				new String[]{"photo_one","photo_two","photo_three","theme","date","content"},
				new int[]{R.id.content_ls_photo1,R.id.content_ls_photo2,R.id.content_ls_photo3,R.id.content_ls_txt1,R.id.content_ls_txt2,R.id.content_ls_txt3},
				CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		like_ls.setAdapter(sc);
	}
	
	//为返回键设置监听器，点击结束当前Activity
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
