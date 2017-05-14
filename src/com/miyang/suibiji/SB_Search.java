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
		
//		创建DBHelper数据库工具类
		searchHelper = new DBHelper(getApplicationContext());
//		清空search数据表，避免edit为空时点击搜索弹出数据内容
		searchHelper.delete("search");
		
//		把当前Activity加入到CloseApplication的Activitylist中
		CloseApplication.getInstance().addActivity(this);
		
//		获得控件的Id
		search_ls = (ListView)findViewById(R.id.search_ls);
		search_txt = (EditText)findViewById(R.id.search_txt);
		search_back = (Button)findViewById(R.id.search_back);
		search_ok = (Button)findViewById(R.id.search_ok);
//		当点击查看或修改时并跳转回当前Activity，将显示关闭前的界面内容
		backExtra();
		
//		为控件绑定监听器
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
//				插入数据之前清空数据表
				searchHelper.delete("search");
				if(!txt.isEmpty()){
	//					模糊查询数据表
						Cursor cu = searchHelper.rawQueryLike("main",txt);
	//					创建contentvalues对象，封装记录信息
						ContentValues c = new ContentValues();
						while(cu.moveToNext()){
							c.put("photo_one", cu.getString(cu.getColumnIndex("photo_one")));
							c.put("photo_two", cu.getString(cu.getColumnIndex("photo_two")));
							c.put("photo_three", cu.getString(cu.getColumnIndex("photo_three")));
							c.put("theme", cu.getString(cu.getColumnIndex("theme")));
							c.put("date",cu.getString(cu.getColumnIndex("date")));
							c.put("content", cu.getString(cu.getColumnIndex("content")));
	//						向search数据表中插入数据
							searchHelper.insert(c, "search");
					}
					Cursor cur = searchHelper.rawQueryCount("search");
					int count = 0;
				    while(cur.moveToNext())
				    {
				        count = cur.getInt(0);
				    }
				    if(count == 0){
				    	Toast.makeText(SB_Search.this, "相关记录不存在！", Toast.LENGTH_SHORT).show();
				    }else{
	//					查询search数据表并将数据表内容插入到listview中
						Cursor cursor = searchHelper.rawQueryInf("search");
							inflateList(cursor);
//							判断如果游标移动到最后一项数据的后一项时，关闭游标
							if(cursor.isAfterLast()){
								cursor.close();
							}
//							为listview的item绑定监听器
							search_ls.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> arg0, View view,
										int position, long arg3) {
//									获得当前item的内容布局
									View v = search_ls.getChildAt(position); 
//									获得item的内部view
									photo1 = (TextView)v.findViewById(R.id.content_ls_photo1);//照片1的名称
									photo2 = (TextView)v.findViewById(R.id.content_ls_photo2);//照片2的名称
									photo3 = (TextView)v.findViewById(R.id.content_ls_photo3);//照片3的名称
									txt1 = (TextView)v.findViewById(R.id.content_ls_txt1);//主题
									txt2 = (TextView)v.findViewById(R.id.content_ls_txt2);//日期
									txt3 = (TextView)v.findViewById(R.id.content_ls_txt3);//内容
									//此处直接new一个Dialog对象出来，在实例化的时候传入主题
						            dialog_list = new Dialog(SB_Search.this, R.style.MyDialog);
						            //设置它的ContentView
						            dialog_list.setContentView(R.layout.sb_dialog_content_list);
						            dialog_list.show();
						            
//					            得到dialog中查看、修改、收藏、删除的Id
								Button list_btn1 = (Button)dialog_list.findViewById(R.id.content_list_btn1);
								Button list_btn2 = (Button)dialog_list.findViewById(R.id.content_list_btn2);
								Button list_btn3 = (Button)dialog_list.findViewById(R.id.content_list_btn3);
								Button list_btn4 = (Button)dialog_list.findViewById(R.id.content_list_btn4);
								
//								为查看按键绑定监听器
								list_btn1.setOnClickListener(new OnClickListener() {
									public void onClick(View v) {
										// TODO Auto-generated method stub
										dialog_list.dismiss();
//										调用list_btn1的监听器函数
										list_btn1();
									}
								});
								
//								为修改按键绑定监听器
								list_btn2.setOnClickListener(new OnClickListener() {
									public void onClick(View v) {
										// TODO Auto-generated method stub
										dialog_list.dismiss();
										list_btn2();
									}
								});
								
//								为收藏按键绑定监听器,需要判断是否已经收藏过
								list_btn3.setOnClickListener(new OnClickListener() {
									public void onClick(View v) {
										list_btn3();
									 }
								});
//								为删除按键绑定监听器
								list_btn4.setOnClickListener(new OnClickListener() {
									public void onClick(View v) {
										// TODO Auto-generated method stub
										dialog_list.dismiss();
//										调用list_btn5的监听器函数
										list_btn4();
									}
								});
								}
							});
				    }
//				    关闭游标
				    cu.close();
				    cur.close();
				}else{
					search_ls.setVisibility(View.GONE);
					Toast.makeText(SB_Search.this, "请输入查询关键字！", Toast.LENGTH_SHORT).show();
				}
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
//	这是修改信息
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
//	这是收藏信息的函数
	private void list_btn3(){
		int count = 0;
		ContentValues cv = new ContentValues();
//		查询数据表的全部信息
		Cursor cu = searchHelper.rawQueryInf("like");
//		查询like数据表的数据条数
		Cursor c = searchHelper.rawQueryCount("like");
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
					searchHelper.insert(cv, "like");
//					隐藏dialog窗口
						dialog_list.dismiss();
						Toast.makeText(SB_Search.this, "完成收藏！", Toast.LENGTH_SHORT).show();
			}
			else{
				ArrayList<String> al = new ArrayList<String>();
				String like_txt2 = null;
//				检索like数据表元素
				while(cu.moveToNext()){
					like_txt2 = cu.getString(cu.getColumnIndex("date"));
//					将like数据表中的date字段数据全部检索并存放在al数组中
					al.add(like_txt2);
				}
				int j = 0 ;
//				循环检索al数组元素
				while(j<al.size()){
//					如果数组元素与item中的txt2内容一致，则提示该账号已经收藏过，不进行数据插入
						if(txt2.getText().toString().equals(al.get(j))){
							dialog_list.dismiss();
							Toast.makeText(SB_Search.this, "已收藏", Toast.LENGTH_SHORT).show();
							break;
						}
//						如果数组元素与item中的txt2内容不一致，则将数组下标加1继续检索数组元素
						else{
							j++;
						}
					}
//				判断数组元素是否全部检索完毕，如果检索完毕（即数组所有元素与item中的txt2内容不一致）
//				，此时的j=al.size(),然后执行插入数据
				if(j>=al.size()){
					cv.put("photo_one", photo1.getText().toString());
					cv.put("photo_two", photo2.getText().toString());
					cv.put("photo_three", photo3.getText().toString());
					cv.put("theme", txt1.getText().toString());
					cv.put("date", txt2.getText().toString());
					cv.put("content", txt3.getText().toString());
					searchHelper.insert(cv, "like");
				
//			隐藏dialog窗口
				dialog_list.dismiss();
				Toast.makeText(SB_Search.this, "完成收藏！", Toast.LENGTH_SHORT).show();
				}
			}
					
		 }	
//		关闭游标
		cu.close();
		c.close();
	}
//	这个是删除账号信息，加确认对话框
	private void list_btn4(){
//		此处直接new一个Dialog对象出来，在实例化的时候传入主题
        final Dialog dialog_delete = new Dialog(SB_Search.this, R.style.MyDialog);
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
				Cursor c = searchHelper.rawQueryInf("main");
				while(c.moveToNext()){
					String delete_txt2 = c.getString(c.getColumnIndex("date"));
							if(txt2.getText().toString().equals(delete_txt2)){
								searchHelper.deleteDate("main", delete_txt2);
							}
						}
//				关闭游标
				c.close();
						dialog_delete.dismiss();
						
						String txt = search_txt.getText().toString();
//						插入数据之前清空数据表
						searchHelper.delete("search");
						if(!txt.isEmpty()){
			//					模糊查询数据表
								Cursor cu = searchHelper.rawQueryLike("main",txt);
			//					创建contentvalues对象，封装记录信息
								ContentValues cv = new ContentValues();
								while(cu.moveToNext()){
									cv.put("photo_one", cu.getString(cu.getColumnIndex("photo_one")));
									cv.put("photo_two", cu.getString(cu.getColumnIndex("photo_two")));
									cv.put("photo_three", cu.getString(cu.getColumnIndex("photo_three")));
									cv.put("theme", cu.getString(cu.getColumnIndex("theme")));
									cv.put("date",cu.getString(cu.getColumnIndex("date")));
									cv.put("content", cu.getString(cu.getColumnIndex("content")));
			//						向search数据表中插入数据
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
						    	Toast.makeText(SB_Search.this, "相关记录不存在！", Toast.LENGTH_SHORT).show();
						    }else{
//								查询search数据表并将数据表内容插入到listview中
								Cursor cursor = searchHelper.rawQueryInf("search");
									inflateList(cursor);
//									判断如果游标移动到最后一项数据的后一项时，关闭游标
									if(cursor.isAfterLast()){
										cursor.close();
									}
//									关闭游标
									cur.close();
						    }
					}
			}
		});
	}
	/*
	 * 当查看或修改完成后退出到当前Activity时
	 * 当前界面要显示关闭的内容
	*/
	private void backExtra(){
//		当查看或修改完成后退出到当前Activity
		Intent it = getIntent();
		back_txt = it.getStringExtra("back_txt");
		if(back_txt!=null){
			search_txt.setText(back_txt);
			//模糊查询数据表
			Cursor cu = searchHelper.rawQueryLike("main",back_txt);
//					创建contentvalues对象，封装记录信息
			ContentValues c = new ContentValues();
			while(cu.moveToNext()){
				c.put("photo_one", cu.getString(cu.getColumnIndex("photo_one")));
				c.put("photo_two", cu.getString(cu.getColumnIndex("photo_two")));
				c.put("photo_three", cu.getString(cu.getColumnIndex("photo_three")));
				c.put("theme", cu.getString(cu.getColumnIndex("theme")));
				c.put("date",cu.getString(cu.getColumnIndex("date")));
				c.put("content", cu.getString(cu.getColumnIndex("content")));
//						向search数据表中插入数据
				searchHelper.insert(c, "search");
		    }
//					查询search数据表并将数据表内容插入到listview中
			Cursor cursor = searchHelper.rawQueryInf("search");
				inflateList(cursor);
//				判断如果游标移动到最后一项数据的后一项时，关闭游标
				if(cursor.isAfterLast()){
					cursor.close();
				}
				cu.close();
				
//				为listview的item绑定监听器
				search_ls.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View view,
							int position, long arg3) {
//						获得当前item的内容布局
						View v = search_ls.getChildAt(position); 
//						获得item的内部view
						photo1 = (TextView)v.findViewById(R.id.content_ls_photo1);//照片1的名称
						photo2 = (TextView)v.findViewById(R.id.content_ls_photo2);//照片2的名称
						photo3 = (TextView)v.findViewById(R.id.content_ls_photo3);//照片3的名称
						txt1 = (TextView)v.findViewById(R.id.content_ls_txt1);//主题
						txt2 = (TextView)v.findViewById(R.id.content_ls_txt2);//日期
						txt3 = (TextView)v.findViewById(R.id.content_ls_txt3);//内容
						//此处直接new一个Dialog对象出来，在实例化的时候传入主题
			            dialog_list = new Dialog(SB_Search.this, R.style.MyDialog);
			            //设置它的ContentView
			            dialog_list.setContentView(R.layout.sb_dialog_content_list);
			            dialog_list.show();
			            
//		            得到dialog中查看、修改、收藏、删除的Id
					Button list_btn1 = (Button)dialog_list.findViewById(R.id.content_list_btn1);
					Button list_btn2 = (Button)dialog_list.findViewById(R.id.content_list_btn2);
					Button list_btn3 = (Button)dialog_list.findViewById(R.id.content_list_btn3);
					Button list_btn4 = (Button)dialog_list.findViewById(R.id.content_list_btn4);
					
//					为查看按键绑定监听器
					list_btn1.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog_list.dismiss();
//							调用list_btn1的监听器函数
							list_btn1();
						}
					});
					
//					为修改按键绑定监听器
					list_btn2.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog_list.dismiss();
							list_btn2();
						}
					});
					
//					为收藏按键绑定监听器,需要判断是否已经收藏过
					list_btn3.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							list_btn3();
						 }
					});
//					为删除按键绑定监听器
					list_btn4.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog_list.dismiss();
//							调用list_btn5的监听器函数
							list_btn4();
						}
					});
					}
				});
		}
	}
	
//	定义一个函数用来将查询到的数据绑定到listview中
	private void inflateList(Cursor cursor){
		search_ls = (ListView)findViewById(R.id.search_ls);
		/*
		 * 填充SimpleCursorAdapter，使用SimpleCursorAdapter必须保证主键是_id,否则会运行出错
		*/
		SimpleCursorAdapter sc = new SimpleCursorAdapter(SB_Search.this, R.layout.sb_content_ls_item, cursor, 
				new String[]{"photo_one","photo_two","photo_three","theme","date","content"},
				new int[]{R.id.content_ls_photo1,R.id.content_ls_photo2,R.id.content_ls_photo3,R.id.content_ls_txt1,R.id.content_ls_txt2,R.id.content_ls_txt3},
				CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		search_ls.setAdapter(sc);
	}
	

	//为返回键设置监听器，点击结束当前Activity
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
