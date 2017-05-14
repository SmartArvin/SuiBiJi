package com.miyang.suibiji;

import java.io.File;

import com.miyang.suibiji.R;

import android.app.Activity;
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
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SB_LikeCheck extends Activity{

	Button check_back , check_ok;
	HorizontalScrollView check_hs;
	TextView check_date_content ,check_txt2 , check_txt4,check_txt5;
	ImageView check_photo1 , check_photo2,check_photo3;
	String photo1,photo2,photo3;
	
	private String filepath = Environment.getExternalStorageDirectory()+"/com.miyang.suibiji";
	private static final int SCALE = 5;//��Ƭ��С����
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sb_check);
//		��ÿؼ���Id
		check_back = (Button)findViewById(R.id.check_back);
		check_ok = (Button)findViewById(R.id.check_ok);
		check_date_content = (TextView)findViewById(R.id.check_date_content);
		check_txt2 = (TextView)findViewById(R.id.check_txt2);
		check_txt4 = (TextView)findViewById(R.id.check_txt4);
		check_txt5 = (TextView)findViewById(R.id.check_txt5);
		check_hs = (HorizontalScrollView)findViewById(R.id.check_hs);
		check_photo1 = (ImageView)findViewById(R.id.check_photo1);
		check_photo2 = (ImageView)findViewById(R.id.check_photo2);
		check_photo3 = (ImageView)findViewById(R.id.check_photo3);
		
//		���ú����õ�����������
		getdata();
		
//		Ϊ�ؼ��󶨼�����
		check_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SB_LikeCheck.this,SB_Like.class);
				SB_LikeCheck.this.startActivity(intent);
				finish();
			}
		});
		check_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SB_LikeCheck.this,SB_Like.class);
				SB_LikeCheck.this.startActivity(intent);
				finish();
			}
		});
		
		check_photo1.setOnClickListener(new OnClickListener() {
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
						Toast.makeText(SB_LikeCheck.this, "ͼƬ��Դ����ɾ����", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
		check_photo2.setOnClickListener(new OnClickListener() {
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
						Toast.makeText(SB_LikeCheck.this, "ͼƬ��Դ����ɾ����", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
		check_photo3.setOnClickListener(new OnClickListener() {
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
						Toast.makeText(SB_LikeCheck.this, "ͼƬ��Դ����ɾ����", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
	}

//	���ContentActivity����������
	private void getdata(){
		Intent it = getIntent();
		photo1 = it.getStringExtra("photo_one");
		photo2 = it.getStringExtra("photo_two");
		photo3 = it.getStringExtra("photo_three");
		check_date_content.setText(it.getStringExtra("date"));
		check_txt2.setText(it.getStringExtra("theme"));
		check_txt4.setText(it.getStringExtra("content"));
//		�������Ƭ��Ϣ��������ؿؼ�
		if(photo1.isEmpty()){
			check_txt5.setVisibility(View.GONE);
			check_hs.setVisibility(View.GONE);
		}else{
			if(photo2.isEmpty()){
				File file0 = new File(filepath+"/"+photo1+".png");
				if(!file0.exists()){//����ǰ·��������ʱ������ͼ��ʾnoneͼƬ
					check_photo1.setPadding(50, 80, 50, 80);
					check_photo1.setImageResource(R.drawable.sb_none);
				}else{
					check_photo1.setVisibility(1);
					Bitmap bm0 = BitmapFactory.decodeFile(filepath+"/"+photo1+".png");
					//Ϊ��ֹԭʼͼƬ�������ڴ��������������Сԭͼ��ʾ��Ȼ���ͷ�ԭʼBitmapռ�õ��ڴ�
					Bitmap smallBitmap0 = ImageTools.zoomBitmap(bm0, bm0.getWidth() / SCALE, bm0.getHeight() / SCALE);
					//�ͷ�ԭʼͼƬռ�õ��ڴ棬��ֹout of memory�쳣����
					bm0.recycle();
					check_photo1.setImageBitmap(smallBitmap0);
				}
			}else{
				if(photo3.isEmpty()){
					File file0 = new File(filepath+"/"+photo1+".png");
					File file1 = new File(filepath+"/"+photo2+".png");
					check_photo1.setVisibility(1);
					check_photo2.setVisibility(1);
					if(!file0.exists()&&file1.exists()){//photo1��·�������ڣ�photo2·������
						check_photo1.setPadding(50, 80, 50, 80);
						check_photo1.setImageResource(R.drawable.sb_none);
						//photo2����ʾ����
						Bitmap bm1 = BitmapFactory.decodeFile(filepath+"/"+photo2+".png");
						Bitmap smallBitmap1 = ImageTools.zoomBitmap(bm1, bm1.getWidth() / SCALE, bm1.getHeight() / SCALE);
						bm1.recycle();
						check_photo2.setImageBitmap(smallBitmap1);
					}
					if(!file0.exists()&&!file1.exists()){//photo1��·�������ڣ�photo2·��Ҳ������
						check_photo1.setPadding(50, 80, 50, 80);
						check_photo1.setImageResource(R.drawable.sb_none);
						check_photo2.setPadding(50, 80, 50, 80);
						check_photo2.setImageResource(R.drawable.sb_none);
					}
					if(file0.exists()&&!file1.exists()){//photo1��·�����ڣ�photo2·��������
						Bitmap bm0 = BitmapFactory.decodeFile(filepath+"/"+photo1+".png");
						Bitmap smallBitmap0 = ImageTools.zoomBitmap(bm0, bm0.getWidth() / SCALE, bm0.getHeight() / SCALE);
						bm0.recycle();
						check_photo1.setImageBitmap(smallBitmap0);
						check_photo2.setPadding(50, 80, 50, 80);
						check_photo2.setImageResource(R.drawable.sb_none);
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
						check_photo1.setImageBitmap(smallBitmap0);
						check_photo2.setImageBitmap(smallBitmap1);
					}
				}else{
					File file0 = new File(filepath+"/"+photo1+".png");
					File file1 = new File(filepath+"/"+photo2+".png");
					File file2 = new File(filepath+"/"+photo3+".png");
					check_photo1.setVisibility(1);
					check_photo2.setVisibility(1);
					check_photo3.setVisibility(1);
					if(!file0.exists()&&!file1.exists()&&!file2.exists()){//photo1·�������ڣ�photo2·�������ڣ�photo3·��������ʱ
						check_photo1.setPadding(50, 80, 50, 80);
						check_photo1.setImageResource(R.drawable.sb_none);
						check_photo2.setPadding(50, 80, 50, 80);
						check_photo2.setImageResource(R.drawable.sb_none);
						check_photo3.setPadding(50, 80, 50, 80);
						check_photo3.setImageResource(R.drawable.sb_none);
					}
					if(!file0.exists()&&!file1.exists()&&file2.exists()){//photo1·�������ڣ�photo2·�������ڣ�photo3·��������ʱ
						check_photo1.setPadding(50, 80, 50, 80);
						check_photo1.setImageResource(R.drawable.sb_none);
						check_photo2.setPadding(50, 80, 50, 80);
						check_photo2.setImageResource(R.drawable.sb_none);
						Bitmap bm2 = BitmapFactory.decodeFile(filepath+"/"+photo3+".png");
						Bitmap smallBitmap2 = ImageTools.zoomBitmap(bm2, bm2.getWidth() / SCALE, bm2.getHeight() / SCALE);
						bm2.recycle();
						check_photo3.setImageBitmap(smallBitmap2);
					}
					if(!file0.exists()&&file1.exists()&&!file2.exists()){//photo1·�������ڣ�photo2·�������ڣ�photo3·��������ʱ
						check_photo1.setPadding(50, 80, 50, 80);
						check_photo1.setImageResource(R.drawable.sb_none);
						check_photo3.setPadding(50, 80, 50, 80);
						check_photo3.setImageResource(R.drawable.sb_none);
						Bitmap bm1 = BitmapFactory.decodeFile(filepath+"/"+photo2+".png");
						Bitmap smallBitmap1 = ImageTools.zoomBitmap(bm1, bm1.getWidth() / SCALE, bm1.getHeight() / SCALE);
						bm1.recycle();
						check_photo2.setImageBitmap(smallBitmap1);
					}
					if(!file0.exists()&&file1.exists()&&file2.exists()){//photo1·�������ڣ�photo2·�������ڣ�photo3·��������ʱ
						check_photo1.setPadding(50, 80, 50, 80);
						check_photo1.setImageResource(R.drawable.sb_none);
						Bitmap bm1 = BitmapFactory.decodeFile(filepath+"/"+photo2+".png");
						Bitmap bm2 = BitmapFactory.decodeFile(filepath+"/"+photo3+".png");
						//Ϊ��ֹԭʼͼƬ�������ڴ��������������Сԭͼ��ʾ��Ȼ���ͷ�ԭʼBitmapռ�õ��ڴ�
						Bitmap smallBitmap1 = ImageTools.zoomBitmap(bm1, bm1.getWidth() / SCALE, bm1.getHeight() / SCALE);
						Bitmap smallBitmap2 = ImageTools.zoomBitmap(bm2, bm2.getWidth() / SCALE, bm2.getHeight() / SCALE);
						//�ͷ�ԭʼͼƬռ�õ��ڴ棬��ֹout of memory�쳣����
						bm1.recycle();
						bm2.recycle();
						check_photo2.setImageBitmap(smallBitmap1);
						check_photo3.setImageBitmap(smallBitmap2);
					}
					if(file0.exists()&&!file1.exists()&&!file2.exists()){//photo1·�������ڣ�photo2·�������ڣ�photo3·��������ʱ
						Bitmap bm0 = BitmapFactory.decodeFile(filepath+"/"+photo1+".png");
						Bitmap smallBitmap0 = ImageTools.zoomBitmap(bm0, bm0.getWidth() / SCALE, bm0.getHeight() / SCALE);
						bm0.recycle();
						check_photo1.setImageBitmap(smallBitmap0);
						check_photo2.setPadding(50, 80, 50, 80);
						check_photo2.setImageResource(R.drawable.sb_none);
						check_photo3.setPadding(50, 80, 50, 80);
						check_photo3.setImageResource(R.drawable.sb_none);
					}
					if(file0.exists()&&!file1.exists()&&file2.exists()){//photo1·�������ڣ�photo2·�������ڣ�photo3·��������ʱ
						Bitmap bm0 = BitmapFactory.decodeFile(filepath+"/"+photo1+".png");
						Bitmap smallBitmap0 = ImageTools.zoomBitmap(bm0, bm0.getWidth() / SCALE, bm0.getHeight() / SCALE);
						bm0.recycle();
						check_photo1.setImageBitmap(smallBitmap0);
						check_photo2.setPadding(50, 80, 50, 80);
						check_photo2.setImageResource(R.drawable.sb_none);
						Bitmap bm2 = BitmapFactory.decodeFile(filepath+"/"+photo3+".png");
						Bitmap smallBitmap2 = ImageTools.zoomBitmap(bm2, bm2.getWidth() / SCALE, bm2.getHeight() / SCALE);
						bm2.recycle();
						check_photo3.setImageBitmap(smallBitmap2);
					}
					if(file0.exists()&&file1.exists()&&!file2.exists()){//photo1·�������ڣ�photo2·�������ڣ�photo3·��������ʱ
						Bitmap bm0 = BitmapFactory.decodeFile(filepath+"/"+photo1+".png");
						Bitmap bm1 = BitmapFactory.decodeFile(filepath+"/"+photo2+".png");
						Bitmap smallBitmap0 = ImageTools.zoomBitmap(bm0, bm0.getWidth() / SCALE, bm0.getHeight() / SCALE);
						Bitmap smallBitmap1 = ImageTools.zoomBitmap(bm1, bm1.getWidth() / SCALE, bm1.getHeight() / SCALE);
						bm0.recycle();
						bm1.recycle();
						check_photo1.setImageBitmap(smallBitmap0);
						check_photo2.setImageBitmap(smallBitmap1);
						check_photo3.setPadding(50, 80, 50, 80);
						check_photo3.setImageResource(R.drawable.sb_none);
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
						check_photo1.setImageBitmap(smallBitmap0);
						check_photo2.setImageBitmap(smallBitmap1);
						check_photo3.setImageBitmap(smallBitmap2);
					}
				}
			}
			
		}
	}
	
	
	//Ϊ���ؼ����ü����������������ǰActivity
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK
		   && event.getAction() == KeyEvent.ACTION_DOWN){
			Intent intent = new Intent();
			intent.setClass(SB_LikeCheck.this,SB_Like.class);
			SB_LikeCheck.this.startActivity(intent);
			finish();
		  }
		return true;
		} 
}