package com.miyang.suibiji;

import java.io.File;

import com.miyang.suibiji.R;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SB_Change extends Activity{
	
	Button change_back , change_ok;
	EditText change_txt2 , change_txt4;
	TextView change_txt5;
	HorizontalScrollView change_hs;
	ImageView change_photo1 , change_photo2 ,change_photo3;
	String actname,date,photo1,photo2,photo3,search_txt;
	DBHelper changeHelper;
	
	private String filepath = Environment.getExternalStorageDirectory()+"/com.miyang.suibiji";
	private static final int SCALE = 5;//照片缩小比例
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sb_change_activity);
		
//		把当前Activity加入到CloseApplication的Activitylist中
		CloseApplication.getInstance().addActivity(this);
		
//		创建数据库工具类
		changeHelper = new DBHelper(getApplicationContext());
		
//		获得控件的Id
		change_back = (Button)findViewById(R.id.change_back);
		change_ok = (Button)findViewById(R.id.change_ok);
		change_txt2 = (EditText)findViewById(R.id.change_txt2);
		change_txt4 = (EditText)findViewById(R.id.change_txt4);
		change_txt5 = (TextView)findViewById(R.id.change_txt5);
		change_hs = (HorizontalScrollView)findViewById(R.id.change_hs);
		change_photo1 = (ImageView)findViewById(R.id.change_photo1);
		change_photo2 = (ImageView)findViewById(R.id.change_photo2);
		change_photo3 = (ImageView)findViewById(R.id.change_photo3);
//		得到intent传来的数据
		Intent intent = getIntent();
		actname = intent.getStringExtra("actname");
		search_txt = intent.getStringExtra("search_txt");
		photo1 = intent.getStringExtra("photo_one");
		photo2 = intent.getStringExtra("photo_two");
		photo3 = intent.getStringExtra("photo_three");
		date = intent.getStringExtra("date");
		change_txt2.setText(intent.getStringExtra("theme"));
		change_txt4.setText(intent.getStringExtra("content"));
//		如果无照片信息则隐藏相关控件
		if(photo1.isEmpty()){
			change_txt5.setVisibility(View.GONE);
			change_hs.setVisibility(View.GONE);
		}else{
			if(photo2.isEmpty()){
				File file0 = new File(filepath+"/"+photo1+".png");
				if(!file0.exists()){//当当前路径不存在时，缩略图显示none图片
					change_photo1.setPadding(50, 80, 50, 80);
					change_photo1.setImageResource(R.drawable.sb_none);
				}else{
					change_photo1.setVisibility(1);
					Bitmap bm0 = BitmapFactory.decodeFile(filepath+"/"+photo1+".png");
					//为防止原始图片过大导致内存溢出，这里先缩小原图显示，然后释放原始Bitmap占用的内存
					Bitmap smallBitmap0 = ImageTools.zoomBitmap(bm0, bm0.getWidth() / SCALE, bm0.getHeight() / SCALE);
					//释放原始图片占用的内存，防止out of memory异常发生
					bm0.recycle();
					change_photo1.setImageBitmap(smallBitmap0);
				}
			}else{
				if(photo3.isEmpty()){
					File file0 = new File(filepath+"/"+photo1+".png");
					File file1 = new File(filepath+"/"+photo2+".png");
					change_photo1.setVisibility(1);
					change_photo2.setVisibility(1);
					if(!file0.exists()&&file1.exists()){//photo1的路径不存在，photo2路径存在
						change_photo1.setPadding(50, 80, 50, 80);
						change_photo1.setImageResource(R.drawable.sb_none);
						//photo2的显示内容
						Bitmap bm1 = BitmapFactory.decodeFile(filepath+"/"+photo2+".png");
						Bitmap smallBitmap1 = ImageTools.zoomBitmap(bm1, bm1.getWidth() / SCALE, bm1.getHeight() / SCALE);
						bm1.recycle();
						change_photo2.setImageBitmap(smallBitmap1);
					}
					if(!file0.exists()&&!file1.exists()){//photo1的路径不存在，photo2路径也不存在
						change_photo1.setPadding(50, 80, 50, 80);
						change_photo1.setImageResource(R.drawable.sb_none);
						change_photo2.setPadding(50, 80, 50, 80);
						change_photo2.setImageResource(R.drawable.sb_none);
					}
					if(file0.exists()&&!file1.exists()){//photo1的路径存在，photo2路径不存在
						Bitmap bm0 = BitmapFactory.decodeFile(filepath+"/"+photo1+".png");
						Bitmap smallBitmap0 = ImageTools.zoomBitmap(bm0, bm0.getWidth() / SCALE, bm0.getHeight() / SCALE);
						bm0.recycle();
						change_photo1.setImageBitmap(smallBitmap0);
						change_photo2.setPadding(50, 80, 50, 80);
						change_photo2.setImageResource(R.drawable.sb_none);
					}
					if(file0.exists()&&file1.exists()){//photo1的路径存在，photo2路径存在
						Bitmap bm0 = BitmapFactory.decodeFile(filepath+"/"+photo1+".png");
						Bitmap bm1 = BitmapFactory.decodeFile(filepath+"/"+photo2+".png");
						//为防止原始图片过大导致内存溢出，这里先缩小原图显示，然后释放原始Bitmap占用的内存
						Bitmap smallBitmap0 = ImageTools.zoomBitmap(bm0, bm0.getWidth() / SCALE, bm0.getHeight() / SCALE);
						Bitmap smallBitmap1 = ImageTools.zoomBitmap(bm1, bm1.getWidth() / SCALE, bm1.getHeight() / SCALE);
						//释放原始图片占用的内存，防止out of memory异常发生
						bm0.recycle();
						bm1.recycle();
						change_photo1.setImageBitmap(smallBitmap0);
						change_photo2.setImageBitmap(smallBitmap1);
					}
				}else{
					File file0 = new File(filepath+"/"+photo1+".png");
					File file1 = new File(filepath+"/"+photo2+".png");
					File file2 = new File(filepath+"/"+photo3+".png");
					change_photo1.setVisibility(1);
					change_photo2.setVisibility(1);
					change_photo3.setVisibility(1);
					if(!file0.exists()&&!file1.exists()&&!file2.exists()){//photo1路径不存在，photo2路径不存在，photo3路径不存在时
						change_photo1.setPadding(50, 80, 50, 80);
						change_photo1.setImageResource(R.drawable.sb_none);
						change_photo2.setPadding(50, 80, 50, 80);
						change_photo2.setImageResource(R.drawable.sb_none);
						change_photo3.setPadding(50, 80, 50, 80);
						change_photo3.setImageResource(R.drawable.sb_none);
					}
					if(!file0.exists()&&!file1.exists()&&file2.exists()){//photo1路径不存在，photo2路径不存在，photo3路径不存在时
						change_photo1.setPadding(50, 80, 50, 80);
						change_photo1.setImageResource(R.drawable.sb_none);
						change_photo2.setPadding(50, 80, 50, 80);
						change_photo2.setImageResource(R.drawable.sb_none);
						Bitmap bm2 = BitmapFactory.decodeFile(filepath+"/"+photo3+".png");
						Bitmap smallBitmap2 = ImageTools.zoomBitmap(bm2, bm2.getWidth() / SCALE, bm2.getHeight() / SCALE);
						bm2.recycle();
						change_photo3.setImageBitmap(smallBitmap2);
					}
					if(!file0.exists()&&file1.exists()&&!file2.exists()){//photo1路径不存在，photo2路径不存在，photo3路径不存在时
						change_photo1.setPadding(50, 80, 50, 80);
						change_photo1.setImageResource(R.drawable.sb_none);
						change_photo3.setPadding(50, 80, 50, 80);
						change_photo3.setImageResource(R.drawable.sb_none);
						Bitmap bm1 = BitmapFactory.decodeFile(filepath+"/"+photo2+".png");
						Bitmap smallBitmap1 = ImageTools.zoomBitmap(bm1, bm1.getWidth() / SCALE, bm1.getHeight() / SCALE);
						bm1.recycle();
						change_photo2.setImageBitmap(smallBitmap1);
					}
					if(!file0.exists()&&file1.exists()&&file2.exists()){//photo1路径不存在，photo2路径不存在，photo3路径不存在时
						change_photo1.setPadding(50, 80, 50, 80);
						change_photo1.setImageResource(R.drawable.sb_none);
						Bitmap bm1 = BitmapFactory.decodeFile(filepath+"/"+photo2+".png");
						Bitmap bm2 = BitmapFactory.decodeFile(filepath+"/"+photo3+".png");
						//为防止原始图片过大导致内存溢出，这里先缩小原图显示，然后释放原始Bitmap占用的内存
						Bitmap smallBitmap1 = ImageTools.zoomBitmap(bm1, bm1.getWidth() / SCALE, bm1.getHeight() / SCALE);
						Bitmap smallBitmap2 = ImageTools.zoomBitmap(bm2, bm2.getWidth() / SCALE, bm2.getHeight() / SCALE);
						//释放原始图片占用的内存，防止out of memory异常发生
						bm1.recycle();
						bm2.recycle();
						change_photo2.setImageBitmap(smallBitmap1);
						change_photo3.setImageBitmap(smallBitmap2);
					}
					if(file0.exists()&&!file1.exists()&&!file2.exists()){//photo1路径不存在，photo2路径不存在，photo3路径不存在时
						Bitmap bm0 = BitmapFactory.decodeFile(filepath+"/"+photo1+".png");
						Bitmap smallBitmap0 = ImageTools.zoomBitmap(bm0, bm0.getWidth() / SCALE, bm0.getHeight() / SCALE);
						bm0.recycle();
						change_photo1.setImageBitmap(smallBitmap0);
						change_photo2.setPadding(50, 80, 50, 80);
						change_photo2.setImageResource(R.drawable.sb_none);
						change_photo3.setPadding(50, 80, 50, 80);
						change_photo3.setImageResource(R.drawable.sb_none);
					}
					if(file0.exists()&&!file1.exists()&&file2.exists()){//photo1路径不存在，photo2路径不存在，photo3路径不存在时
						Bitmap bm0 = BitmapFactory.decodeFile(filepath+"/"+photo1+".png");
						Bitmap smallBitmap0 = ImageTools.zoomBitmap(bm0, bm0.getWidth() / SCALE, bm0.getHeight() / SCALE);
						bm0.recycle();
						change_photo1.setImageBitmap(smallBitmap0);
						change_photo2.setPadding(50, 80, 50, 80);
						change_photo2.setImageResource(R.drawable.sb_none);
						Bitmap bm2 = BitmapFactory.decodeFile(filepath+"/"+photo3+".png");
						Bitmap smallBitmap2 = ImageTools.zoomBitmap(bm2, bm2.getWidth() / SCALE, bm2.getHeight() / SCALE);
						bm2.recycle();
						change_photo3.setImageBitmap(smallBitmap2);
					}
					if(file0.exists()&&file1.exists()&&!file2.exists()){//photo1路径不存在，photo2路径不存在，photo3路径不存在时
						Bitmap bm0 = BitmapFactory.decodeFile(filepath+"/"+photo1+".png");
						Bitmap bm1 = BitmapFactory.decodeFile(filepath+"/"+photo2+".png");
						Bitmap smallBitmap0 = ImageTools.zoomBitmap(bm0, bm0.getWidth() / SCALE, bm0.getHeight() / SCALE);
						Bitmap smallBitmap1 = ImageTools.zoomBitmap(bm1, bm1.getWidth() / SCALE, bm1.getHeight() / SCALE);
						bm0.recycle();
						bm1.recycle();
						change_photo1.setImageBitmap(smallBitmap0);
						change_photo2.setImageBitmap(smallBitmap1);
						change_photo3.setPadding(50, 80, 50, 80);
						change_photo3.setImageResource(R.drawable.sb_none);
					}
					if(file0.exists()&&file1.exists()&&file2.exists()){//photo1路径不存在，photo2路径不存在，photo3路径不存在时
						Bitmap bm0 = BitmapFactory.decodeFile(filepath+"/"+photo1+".png");
						Bitmap bm1 = BitmapFactory.decodeFile(filepath+"/"+photo2+".png");
						Bitmap bm2 = BitmapFactory.decodeFile(filepath+"/"+photo3+".png");
						//为防止原始图片过大导致内存溢出，这里先缩小原图显示，然后释放原始Bitmap占用的内存
						Bitmap smallBitmap0 = ImageTools.zoomBitmap(bm0, bm0.getWidth() / SCALE, bm0.getHeight() / SCALE);
						Bitmap smallBitmap1 = ImageTools.zoomBitmap(bm1, bm1.getWidth() / SCALE, bm1.getHeight() / SCALE);
						Bitmap smallBitmap2 = ImageTools.zoomBitmap(bm2, bm2.getWidth() / SCALE, bm2.getHeight() / SCALE);
						//释放原始图片占用的内存，防止out of memory异常发生
						bm0.recycle();
						bm1.recycle();
						bm2.recycle();
						change_photo1.setImageBitmap(smallBitmap0);
						change_photo2.setImageBitmap(smallBitmap1);
						change_photo3.setImageBitmap(smallBitmap2);
					}
				}
			}
			
		}
//		为控件绑定监听器
		change_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				if(actname.equals("ContentActivity")){
					intent.setClass(SB_Change.this,SB_Content.class);
				}else{
					if(actname.equals("SearchActivity")){
						intent.putExtra("back_txt", search_txt);
						intent.setClass(SB_Change.this,SB_Search.class);
					}else{
						if(actname.equals("LikeActivity")){
							intent.setClass(SB_Change.this,SB_Like.class);
					     }
					}
				}
				SB_Change.this.startActivity(intent);
				finish();
			}
		});
		change_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ContentValues cv = new ContentValues();
				cv.put("theme",change_txt2.getText().toString());
				cv.put("date",date);
				cv.put("content",change_txt4.getText().toString());
//				更新main数据表中date对应项
					changeHelper.update("main", cv, date);
//					更新like数据表中date对应项
					changeHelper.update("like", cv, date);
//					跳转Activity
					Intent intent = new Intent();
					if(actname.equals("ContentActivity")){
						intent.setClass(SB_Change.this,SB_Content.class);
					}else{
						if(actname.equals("SearchActivity")){
							intent.putExtra("back_txt", search_txt);
							intent.setClass(SB_Change.this,SB_Search.class);
						}else{
							if(actname.equals("LikeActivity")){
								intent.setClass(SB_Change.this,SB_Like.class);
						     }
						}
					}
					SB_Change.this.startActivity(intent);
					finish();
			}
		});
		
		change_photo1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				File file=new File(filepath+"/"+photo1+".png"); 
				if(file.exists()){
					 Intent it = new Intent(Intent.ACTION_VIEW);
		                Uri mUri = Uri.parse("file://"+file.getPath());
		                it.setDataAndType(mUri, "image/*");
		                startActivity(it);
				}else{
					if(!photo1.isEmpty()){
						Toast.makeText(SB_Change.this, "图片资源或已删除！", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
		change_photo2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				File file=new File(filepath+"/"+photo2+".png");                                
				if(file.exists()){
					 Intent it = new Intent(Intent.ACTION_VIEW);
		                Uri mUri = Uri.parse("file://"+file.getPath());
		                it.setDataAndType(mUri, "image/*");
		                startActivity(it);
				}else{
					if(!photo2.isEmpty()){
						Toast.makeText(SB_Change.this, "图片资源或已删除！", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
		change_photo3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				File file=new File(filepath+"/"+photo3+".png");                                
				if(file.exists()){
					 Intent it = new Intent(Intent.ACTION_VIEW);
		                Uri mUri = Uri.parse("file://"+file.getPath());
		                it.setDataAndType(mUri, "image/*");
		                startActivity(it);
				}else{
					if(!photo3.isEmpty()){
						Toast.makeText(SB_Change.this, "图片资源或已删除！", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
		
	}
	
	
	//为返回键设置监听器，点击结束当前Activity
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK
		   && event.getAction() == KeyEvent.ACTION_DOWN){
			Intent intent = new Intent();
			if(actname.equals("ContentActivity")){
				intent.setClass(SB_Change.this,SB_Content.class);
			}else{
				if(actname.equals("SearchActivity")){
					intent.putExtra("back_txt", search_txt);
					intent.setClass(SB_Change.this,SB_Search.class);
				}else{
					if(actname.equals("LikeActivity")){
						intent.setClass(SB_Change.this,SB_Like.class);
				     }
				}
			}
			SB_Change.this.startActivity(intent);
			finish();
		  }
		return true;
		} 

}
