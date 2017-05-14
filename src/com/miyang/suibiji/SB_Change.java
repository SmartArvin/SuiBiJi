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
	private static final int SCALE = 5;//��Ƭ��С����
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sb_change_activity);
		
//		�ѵ�ǰActivity���뵽CloseApplication��Activitylist��
		CloseApplication.getInstance().addActivity(this);
		
//		�������ݿ⹤����
		changeHelper = new DBHelper(getApplicationContext());
		
//		��ÿؼ���Id
		change_back = (Button)findViewById(R.id.change_back);
		change_ok = (Button)findViewById(R.id.change_ok);
		change_txt2 = (EditText)findViewById(R.id.change_txt2);
		change_txt4 = (EditText)findViewById(R.id.change_txt4);
		change_txt5 = (TextView)findViewById(R.id.change_txt5);
		change_hs = (HorizontalScrollView)findViewById(R.id.change_hs);
		change_photo1 = (ImageView)findViewById(R.id.change_photo1);
		change_photo2 = (ImageView)findViewById(R.id.change_photo2);
		change_photo3 = (ImageView)findViewById(R.id.change_photo3);
//		�õ�intent����������
		Intent intent = getIntent();
		actname = intent.getStringExtra("actname");
		search_txt = intent.getStringExtra("search_txt");
		photo1 = intent.getStringExtra("photo_one");
		photo2 = intent.getStringExtra("photo_two");
		photo3 = intent.getStringExtra("photo_three");
		date = intent.getStringExtra("date");
		change_txt2.setText(intent.getStringExtra("theme"));
		change_txt4.setText(intent.getStringExtra("content"));
//		�������Ƭ��Ϣ��������ؿؼ�
		if(photo1.isEmpty()){
			change_txt5.setVisibility(View.GONE);
			change_hs.setVisibility(View.GONE);
		}else{
			if(photo2.isEmpty()){
				File file0 = new File(filepath+"/"+photo1+".png");
				if(!file0.exists()){//����ǰ·��������ʱ������ͼ��ʾnoneͼƬ
					change_photo1.setPadding(50, 80, 50, 80);
					change_photo1.setImageResource(R.drawable.sb_none);
				}else{
					change_photo1.setVisibility(1);
					Bitmap bm0 = BitmapFactory.decodeFile(filepath+"/"+photo1+".png");
					//Ϊ��ֹԭʼͼƬ�������ڴ��������������Сԭͼ��ʾ��Ȼ���ͷ�ԭʼBitmapռ�õ��ڴ�
					Bitmap smallBitmap0 = ImageTools.zoomBitmap(bm0, bm0.getWidth() / SCALE, bm0.getHeight() / SCALE);
					//�ͷ�ԭʼͼƬռ�õ��ڴ棬��ֹout of memory�쳣����
					bm0.recycle();
					change_photo1.setImageBitmap(smallBitmap0);
				}
			}else{
				if(photo3.isEmpty()){
					File file0 = new File(filepath+"/"+photo1+".png");
					File file1 = new File(filepath+"/"+photo2+".png");
					change_photo1.setVisibility(1);
					change_photo2.setVisibility(1);
					if(!file0.exists()&&file1.exists()){//photo1��·�������ڣ�photo2·������
						change_photo1.setPadding(50, 80, 50, 80);
						change_photo1.setImageResource(R.drawable.sb_none);
						//photo2����ʾ����
						Bitmap bm1 = BitmapFactory.decodeFile(filepath+"/"+photo2+".png");
						Bitmap smallBitmap1 = ImageTools.zoomBitmap(bm1, bm1.getWidth() / SCALE, bm1.getHeight() / SCALE);
						bm1.recycle();
						change_photo2.setImageBitmap(smallBitmap1);
					}
					if(!file0.exists()&&!file1.exists()){//photo1��·�������ڣ�photo2·��Ҳ������
						change_photo1.setPadding(50, 80, 50, 80);
						change_photo1.setImageResource(R.drawable.sb_none);
						change_photo2.setPadding(50, 80, 50, 80);
						change_photo2.setImageResource(R.drawable.sb_none);
					}
					if(file0.exists()&&!file1.exists()){//photo1��·�����ڣ�photo2·��������
						Bitmap bm0 = BitmapFactory.decodeFile(filepath+"/"+photo1+".png");
						Bitmap smallBitmap0 = ImageTools.zoomBitmap(bm0, bm0.getWidth() / SCALE, bm0.getHeight() / SCALE);
						bm0.recycle();
						change_photo1.setImageBitmap(smallBitmap0);
						change_photo2.setPadding(50, 80, 50, 80);
						change_photo2.setImageResource(R.drawable.sb_none);
					}
					if(file0.exists()&&file1.exists()){//photo1��·�����ڣ�photo2·������
						Bitmap bm0 = BitmapFactory.decodeFile(filepath+"/"+photo1+".png");
						Bitmap bm1 = BitmapFactory.decodeFile(filepath+"/"+photo2+".png");
						//Ϊ��ֹԭʼͼƬ�������ڴ��������������Сԭͼ��ʾ��Ȼ���ͷ�ԭʼBitmapռ�õ��ڴ�
						Bitmap smallBitmap0 = ImageTools.zoomBitmap(bm0, bm0.getWidth() / SCALE, bm0.getHeight() / SCALE);
						Bitmap smallBitmap1 = ImageTools.zoomBitmap(bm1, bm1.getWidth() / SCALE, bm1.getHeight() / SCALE);
						//�ͷ�ԭʼͼƬռ�õ��ڴ棬��ֹout of memory�쳣����
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
					if(!file0.exists()&&!file1.exists()&&!file2.exists()){//photo1·�������ڣ�photo2·�������ڣ�photo3·��������ʱ
						change_photo1.setPadding(50, 80, 50, 80);
						change_photo1.setImageResource(R.drawable.sb_none);
						change_photo2.setPadding(50, 80, 50, 80);
						change_photo2.setImageResource(R.drawable.sb_none);
						change_photo3.setPadding(50, 80, 50, 80);
						change_photo3.setImageResource(R.drawable.sb_none);
					}
					if(!file0.exists()&&!file1.exists()&&file2.exists()){//photo1·�������ڣ�photo2·�������ڣ�photo3·��������ʱ
						change_photo1.setPadding(50, 80, 50, 80);
						change_photo1.setImageResource(R.drawable.sb_none);
						change_photo2.setPadding(50, 80, 50, 80);
						change_photo2.setImageResource(R.drawable.sb_none);
						Bitmap bm2 = BitmapFactory.decodeFile(filepath+"/"+photo3+".png");
						Bitmap smallBitmap2 = ImageTools.zoomBitmap(bm2, bm2.getWidth() / SCALE, bm2.getHeight() / SCALE);
						bm2.recycle();
						change_photo3.setImageBitmap(smallBitmap2);
					}
					if(!file0.exists()&&file1.exists()&&!file2.exists()){//photo1·�������ڣ�photo2·�������ڣ�photo3·��������ʱ
						change_photo1.setPadding(50, 80, 50, 80);
						change_photo1.setImageResource(R.drawable.sb_none);
						change_photo3.setPadding(50, 80, 50, 80);
						change_photo3.setImageResource(R.drawable.sb_none);
						Bitmap bm1 = BitmapFactory.decodeFile(filepath+"/"+photo2+".png");
						Bitmap smallBitmap1 = ImageTools.zoomBitmap(bm1, bm1.getWidth() / SCALE, bm1.getHeight() / SCALE);
						bm1.recycle();
						change_photo2.setImageBitmap(smallBitmap1);
					}
					if(!file0.exists()&&file1.exists()&&file2.exists()){//photo1·�������ڣ�photo2·�������ڣ�photo3·��������ʱ
						change_photo1.setPadding(50, 80, 50, 80);
						change_photo1.setImageResource(R.drawable.sb_none);
						Bitmap bm1 = BitmapFactory.decodeFile(filepath+"/"+photo2+".png");
						Bitmap bm2 = BitmapFactory.decodeFile(filepath+"/"+photo3+".png");
						//Ϊ��ֹԭʼͼƬ�������ڴ��������������Сԭͼ��ʾ��Ȼ���ͷ�ԭʼBitmapռ�õ��ڴ�
						Bitmap smallBitmap1 = ImageTools.zoomBitmap(bm1, bm1.getWidth() / SCALE, bm1.getHeight() / SCALE);
						Bitmap smallBitmap2 = ImageTools.zoomBitmap(bm2, bm2.getWidth() / SCALE, bm2.getHeight() / SCALE);
						//�ͷ�ԭʼͼƬռ�õ��ڴ棬��ֹout of memory�쳣����
						bm1.recycle();
						bm2.recycle();
						change_photo2.setImageBitmap(smallBitmap1);
						change_photo3.setImageBitmap(smallBitmap2);
					}
					if(file0.exists()&&!file1.exists()&&!file2.exists()){//photo1·�������ڣ�photo2·�������ڣ�photo3·��������ʱ
						Bitmap bm0 = BitmapFactory.decodeFile(filepath+"/"+photo1+".png");
						Bitmap smallBitmap0 = ImageTools.zoomBitmap(bm0, bm0.getWidth() / SCALE, bm0.getHeight() / SCALE);
						bm0.recycle();
						change_photo1.setImageBitmap(smallBitmap0);
						change_photo2.setPadding(50, 80, 50, 80);
						change_photo2.setImageResource(R.drawable.sb_none);
						change_photo3.setPadding(50, 80, 50, 80);
						change_photo3.setImageResource(R.drawable.sb_none);
					}
					if(file0.exists()&&!file1.exists()&&file2.exists()){//photo1·�������ڣ�photo2·�������ڣ�photo3·��������ʱ
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
					if(file0.exists()&&file1.exists()&&!file2.exists()){//photo1·�������ڣ�photo2·�������ڣ�photo3·��������ʱ
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
					if(file0.exists()&&file1.exists()&&file2.exists()){//photo1·�������ڣ�photo2·�������ڣ�photo3·��������ʱ
						Bitmap bm0 = BitmapFactory.decodeFile(filepath+"/"+photo1+".png");
						Bitmap bm1 = BitmapFactory.decodeFile(filepath+"/"+photo2+".png");
						Bitmap bm2 = BitmapFactory.decodeFile(filepath+"/"+photo3+".png");
						//Ϊ��ֹԭʼͼƬ�������ڴ��������������Сԭͼ��ʾ��Ȼ���ͷ�ԭʼBitmapռ�õ��ڴ�
						Bitmap smallBitmap0 = ImageTools.zoomBitmap(bm0, bm0.getWidth() / SCALE, bm0.getHeight() / SCALE);
						Bitmap smallBitmap1 = ImageTools.zoomBitmap(bm1, bm1.getWidth() / SCALE, bm1.getHeight() / SCALE);
						Bitmap smallBitmap2 = ImageTools.zoomBitmap(bm2, bm2.getWidth() / SCALE, bm2.getHeight() / SCALE);
						//�ͷ�ԭʼͼƬռ�õ��ڴ棬��ֹout of memory�쳣����
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
//		Ϊ�ؼ��󶨼�����
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
//				����main���ݱ���date��Ӧ��
					changeHelper.update("main", cv, date);
//					����like���ݱ���date��Ӧ��
					changeHelper.update("like", cv, date);
//					��תActivity
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
						Toast.makeText(SB_Change.this, "ͼƬ��Դ����ɾ����", Toast.LENGTH_SHORT).show();
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
						Toast.makeText(SB_Change.this, "ͼƬ��Դ����ɾ����", Toast.LENGTH_SHORT).show();
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
						Toast.makeText(SB_Change.this, "ͼƬ��Դ����ɾ����", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
		
	}
	
	
	//Ϊ���ؼ����ü����������������ǰActivity
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
