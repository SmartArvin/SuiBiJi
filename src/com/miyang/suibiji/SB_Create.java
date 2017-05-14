package com.miyang.suibiji;

import java.io.File;
import java.util.ArrayList;

import com.miyang.suibiji.R;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.Time;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SB_Create extends Activity{

	Button create_back,create_camera,create_image,create_preview,create_ok;
	ImageView create_photo1,create_photo2,create_photo3;
	TextView create_warn,create_txt3;
	HorizontalScrollView create_hs;
	EditText create_et1,create_et2;
	String theme_txt,content_txt,photo_time;
	DBHelper createHelper;
	ArrayList<String> list;
	
	private static final int TAKE_PICTURE = 0;
	private String filepath = Environment.getExternalStorageDirectory()+"/com.miyang.suibiji";
	
	private static final int SCALE = 5;//照片缩小比例
	/*
	 * 获得系统时间，并设定时区，输出格式是24小时制
	*/
	public String nowDate()  
    {  
        Time localTime = new Time("Asia/Shanghai");  
        localTime.setToNow();  
        return localTime.format("%Y/%m/%d   %H:%M");  
    }  
	/*
	 * 获得系统时间,作为图片的文件名称
	*/
	public String imageName()  
    {  
        Time localTime = new Time("Asia/Shanghai");  
        localTime.setToNow();  
        return localTime.format("%Y%m%d%H%M%S");  
    }  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sb_create_activity);
		
//		把当前Activity加入到CloseApplication的Activitylist中
		CloseApplication.getInstance().addActivity(this);
		
//		创建数据库工具类DBHelper对象
		createHelper = new DBHelper(getApplicationContext());
	
//		创建文件夹
//		createSDCardDir();
		
//		定义一个数组，用来存放照片的名称
		list = new ArrayList<String>();
		
//		获得控件的Id
		create_warn = (TextView)findViewById(R.id.create_warn);
		create_txt3 = (TextView)findViewById(R.id.create_txt3);
		create_hs = (HorizontalScrollView)findViewById(R.id.create_hs);
		create_back = (Button)findViewById(R.id.create_back);
		create_camera = (Button)findViewById(R.id.create_camera);
//		create_image = (Button)findViewById(R.id.create_image);
//		create_preview = (Button)findViewById(R.id.create_preview);
		create_ok = (Button)findViewById(R.id.create_ok);
		create_et1 = (EditText)findViewById(R.id.create_et1);
		create_et2 = (EditText)findViewById(R.id.create_et2);
		create_photo1 = (ImageView)findViewById(R.id.create_photo1);
		create_photo2 = (ImageView)findViewById(R.id.create_photo2);
		create_photo3 = (ImageView)findViewById(R.id.create_photo3);

		/*
		 * 决定暂不支持点击查看图片详情，一方面必要性不强，
		 * 另一方面可避免用户删除数据影响后续工作
		*/
//		create_photo1.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				File file=new File(filepath+"/"+list.get(0)+".png");
//					Intent it = new Intent(Intent.ACTION_VIEW);
//	                Uri mUri = Uri.parse("file://"+file.getPath());
//	                it.setDataAndType(mUri, "image/*");
//	                startActivity(it);
//			}
//		});
//		create_photo2.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				File file=new File(filepath+"/"+list.get(1)+".png");                                
//                Intent it = new Intent(Intent.ACTION_VIEW);
//                Uri mUri = Uri.parse("file://"+file.getPath());
//                it.setDataAndType(mUri, "image/*");
//                startActivity(it);
//			}
//		});
//		create_photo3.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				File file=new File(filepath+"/"+list.get(2)+".png");                                
//                Intent it = new Intent(Intent.ACTION_VIEW);
//                Uri mUri = Uri.parse("file://"+file.getPath());
//                it.setDataAndType(mUri, "image/*");
//                startActivity(it);
//			}
//		});
		
		
//		为控件绑定监听器
		create_back.setOnClickListener(new createListener());
		create_camera.setOnClickListener(new createListener());
//		create_image.setOnClickListener(new createListener());
//		create_preview.setOnClickListener(new createListener());
		
//		得到preview传来的值
		Intent it = getIntent();
		create_et1.setText(it.getStringExtra("theme"));
		create_et2.setText(it.getStringExtra("content"));
		
		create_ok.setOnClickListener(new createListener());

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case TAKE_PICTURE:
				//将保存在本地的图片取出并缩小后显示在界面上
				Bitmap bitmap = BitmapFactory.decodeFile(filepath+"/image.jpg");
//				Bitmap newBitmap = ImageTools.zoomBitmap(bitmap, bitmap.getWidth() / SCALE, bitmap.getHeight() / SCALE);
				Bitmap newBitmap = ImageTools.zoomBitmap(bitmap, bitmap.getWidth()/3 , bitmap.getHeight()/3 );
				//由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常
				bitmap.recycle();
				
				//将处理过的图片保存到本地
				ImageTools.savePhotoToSDCard(newBitmap, filepath, String.valueOf(photo_time = imageName()));
//				获取拍照时刻的时间作为照片名称，保存到数组中
//				photo_time = imageName();
				list.add(photo_time);
				
//				新建一个File对象，指定相应的SD卡文件夹目录
				File file = new File(filepath);
//				判断该路径是否存在
				if(file.exists()){
					//数组长度为0时，隐藏相关控件
					if(list.size()==0){
					}else{
//						设置提示信息控件隐藏
						create_warn.setVisibility(View.GONE);
						//数组长度为1时，create_txt3和create_hs控件可见，定义一个Bitmap对象获得路径中的图片
						if(list.size()==1){
							create_txt3.setVisibility(1);
							create_hs.setVisibility(1);
							create_photo1.setVisibility(1);
							Bitmap bm0 = BitmapFactory.decodeFile(filepath+"/"+list.get(0)+".png");
							//为防止原始图片过大导致内存溢出，这里先缩小原图显示，然后释放原始Bitmap占用的内存
							Bitmap smallBitmap0 = ImageTools.zoomBitmap(bm0, bm0.getWidth() / SCALE, bm0.getHeight() / SCALE);
							//释放原始图片占用的内存，防止out of memory异常发生
							bm0.recycle();
							create_photo1.setImageBitmap(smallBitmap0);
						}else{
							//数组长度为2时，create_txt3和create_hs控件可见，定义一个Bitmap对象获得路径中的图片
							if(list.size()==2){
								create_txt3.setVisibility(1);
								create_hs.setVisibility(1);
								create_photo1.setVisibility(1);
								create_photo2.setVisibility(1);
								Bitmap bm0 = BitmapFactory.decodeFile(filepath+"/"+list.get(0)+".png");
								Bitmap bm1 = BitmapFactory.decodeFile(filepath+"/"+list.get(1)+".png");
								//为防止原始图片过大导致内存溢出，这里先缩小原图显示，然后释放原始Bitmap占用的内存
								Bitmap smallBitmap0 = ImageTools.zoomBitmap(bm0, bm0.getWidth() / SCALE, bm0.getHeight() / SCALE);
								Bitmap smallBitmap1 = ImageTools.zoomBitmap(bm1, bm1.getWidth() / SCALE, bm1.getHeight() / SCALE);
								//释放原始图片占用的内存，防止out of memory异常发生
								bm0.recycle();
								bm1.recycle();
								create_photo1.setImageBitmap(smallBitmap0);
								create_photo2.setImageBitmap(smallBitmap1);
							}else{
//								//数组长度为3时，create_txt3和create_hs控件可见，定义一个Bitmap对象获得路径中的图片
								if(list.size()==3){
									create_txt3.setVisibility(1);
									create_hs.setVisibility(1);
									create_photo1.setVisibility(1);
									create_photo2.setVisibility(1);
									create_photo3.setVisibility(1);
									Bitmap bm0 = BitmapFactory.decodeFile(filepath+"/"+list.get(0)+".png");
									Bitmap bm1 = BitmapFactory.decodeFile(filepath+"/"+list.get(1)+".png");
									Bitmap bm2 = BitmapFactory.decodeFile(filepath+"/"+list.get(2)+".png");
									//为防止原始图片过大导致内存溢出，这里先缩小原图显示，然后释放原始Bitmap占用的内存
									Bitmap smallBitmap0 = ImageTools.zoomBitmap(bm0, bm0.getWidth() / SCALE, bm0.getHeight() / SCALE);
									Bitmap smallBitmap1 = ImageTools.zoomBitmap(bm1, bm1.getWidth() / SCALE, bm1.getHeight() / SCALE);
									Bitmap smallBitmap2 = ImageTools.zoomBitmap(bm2, bm2.getWidth() / SCALE, bm2.getHeight() / SCALE);
									//释放原始图片占用的内存，防止out of memory异常发生
									bm0.recycle();
									bm1.recycle();
									bm2.recycle();
									create_photo1.setImageBitmap(smallBitmap0);
									create_photo2.setImageBitmap(smallBitmap1);
									create_photo3.setImageBitmap(smallBitmap2);
								}
							}
						}
					}
				}else{
					Toast.makeText(SB_Create.this, "SD卡路径错误！", Toast.LENGTH_SHORT).show();
				}
				break;
				
			default:
				break;
			}
		}
	}
//	调用相机完成拍照，保存图片
	public void showPicturePicker(Context context){
					Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					Uri imageUri = Uri.fromFile(new File(filepath,"image.jpg"));
					//指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
					openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
					startActivityForResult(openCameraIntent, TAKE_PICTURE);
	}
	
//	//在SD卡上创建一个文件夹
//    public void createSDCardDir(){
//     if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
//            // 创建一个文件夹对象，赋值为外部存储器的目录
//             File sdcardDir =Environment.getExternalStorageDirectory();
//           //得到一个路径，内容是sdcard的文件夹路径和名字
//             String path=sdcardDir+"/com.miyang.suibiji";
//             File path1 = new File(path);
//            if (!path1.exists()) {
//             //若不存在，创建目录，可以在应用启动的时候创建
//             path1.mkdirs();
//           }
//            }
//     else{
//      setTitle("false");
//      return;
//    }
//    }
	
/*
 * 创建监听器类
	*/
	class createListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			switch (v.getId()) {
			case R.id.create_back:
				intent.setClass(SB_Create.this, SB_Home.class);
				SB_Create.this.startActivity(intent);
				finish();
				break;
			case R.id.create_camera:
				showPicturePicker(SB_Create.this);
				break;
//			case R.id.create_record:
//				break;
//			case R.id.create_preview:
//				if(create_et1.getText().toString().isEmpty()&&create_et2.getText().toString().isEmpty()){
//					Toast.makeText(CreateActivity.this, "亲,你还未输入任何信息哦~", Toast.LENGTH_SHORT).show();
//				}else{
//					intent.putExtra("theme", create_et1.getText().toString());
//					intent.putExtra("content", create_et2.getText().toString());
//					intent.setClass(CreateActivity.this,PreviewActivity.class);
//					CreateActivity.this.startActivity(intent);
//					finish();
//				}
//				break;
			case R.id.create_ok:
				editData("main");
				break;

			default:
				break;
			}
		}
	}
	
//	获得新建页面各编辑框内容，并对输入内容进行判断和处理
	public void editData(String tb_name){
//		获得EditView的编辑内容
		theme_txt = create_et1.getText().toString();
		content_txt = create_et2.getText().toString();
//		判断主题是否为空，为空则提示
		if(theme_txt.isEmpty()){
			Toast.makeText(SB_Create.this, "亲，编辑主题更方便管理哦！", Toast.LENGTH_SHORT).show();
		}else{
//				创建contentvalues对象，封装记录信息
				ContentValues c = new ContentValues();
//				判断数组的长度，因为ArrayList是长度自动改变的数组
				if(list.size()==0){//数组长度为0时，定义数组长度为3且为null
					list.add(0, null);
					list.add(1, null);
					list.add(2, null);
				}else{
					//数组长度为1时，定义数组长度为3且第1和2位为null
					if(list.size()==1){
						list.add(1, null);
						list.add(2, null);
					}else{
						//数组长度为2时，定义数组长度为3且第2位为null
						if(list.size()==2){
							list.add(2, null);
						}
					}
				}
				c.put("photo_one", list.get(0));
				c.put("photo_two", list.get(1));
				c.put("photo_three", list.get(2));
				c.put("theme", theme_txt);
				c.put("date",nowDate());
				c.put("content", content_txt);
//				插入数据
				createHelper.insert(c, tb_name);
//				跳转到主界面
				Intent intent = new Intent(SB_Create.this,SB_Home.class);
				SB_Create.this.startActivity(intent);
				finish();
			
		}
	}
	
	//为返回键设置监听器，点击结束当前Activity
			public boolean onKeyDown(int keyCode, KeyEvent event) {
				if(keyCode == KeyEvent.KEYCODE_BACK
				   && event.getAction() == KeyEvent.ACTION_DOWN){
					Intent intent = new Intent();
					intent.setClass(SB_Create.this,SB_Home.class);
					SB_Create.this.startActivity(intent);
					finish();
				  }
				return true;
				} 
			
			

}
