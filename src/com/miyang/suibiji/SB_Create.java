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
	
	private static final int SCALE = 5;//��Ƭ��С����
	/*
	 * ���ϵͳʱ�䣬���趨ʱ���������ʽ��24Сʱ��
	*/
	public String nowDate()  
    {  
        Time localTime = new Time("Asia/Shanghai");  
        localTime.setToNow();  
        return localTime.format("%Y/%m/%d   %H:%M");  
    }  
	/*
	 * ���ϵͳʱ��,��ΪͼƬ���ļ�����
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
		
//		�ѵ�ǰActivity���뵽CloseApplication��Activitylist��
		CloseApplication.getInstance().addActivity(this);
		
//		�������ݿ⹤����DBHelper����
		createHelper = new DBHelper(getApplicationContext());
	
//		�����ļ���
//		createSDCardDir();
		
//		����һ�����飬���������Ƭ������
		list = new ArrayList<String>();
		
//		��ÿؼ���Id
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
		 * �����ݲ�֧�ֵ���鿴ͼƬ���飬һ�����Ҫ�Բ�ǿ��
		 * ��һ����ɱ����û�ɾ������Ӱ���������
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
		
		
//		Ϊ�ؼ��󶨼�����
		create_back.setOnClickListener(new createListener());
		create_camera.setOnClickListener(new createListener());
//		create_image.setOnClickListener(new createListener());
//		create_preview.setOnClickListener(new createListener());
		
//		�õ�preview������ֵ
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
				//�������ڱ��ص�ͼƬȡ������С����ʾ�ڽ�����
				Bitmap bitmap = BitmapFactory.decodeFile(filepath+"/image.jpg");
//				Bitmap newBitmap = ImageTools.zoomBitmap(bitmap, bitmap.getWidth() / SCALE, bitmap.getHeight() / SCALE);
				Bitmap newBitmap = ImageTools.zoomBitmap(bitmap, bitmap.getWidth()/3 , bitmap.getHeight()/3 );
				//����Bitmap�ڴ�ռ�ýϴ�������Ҫ�����ڴ棬����ᱨout of memory�쳣
				bitmap.recycle();
				
				//���������ͼƬ���浽����
				ImageTools.savePhotoToSDCard(newBitmap, filepath, String.valueOf(photo_time = imageName()));
//				��ȡ����ʱ�̵�ʱ����Ϊ��Ƭ���ƣ����浽������
//				photo_time = imageName();
				list.add(photo_time);
				
//				�½�һ��File����ָ����Ӧ��SD���ļ���Ŀ¼
				File file = new File(filepath);
//				�жϸ�·���Ƿ����
				if(file.exists()){
					//���鳤��Ϊ0ʱ��������ؿؼ�
					if(list.size()==0){
					}else{
//						������ʾ��Ϣ�ؼ�����
						create_warn.setVisibility(View.GONE);
						//���鳤��Ϊ1ʱ��create_txt3��create_hs�ؼ��ɼ�������һ��Bitmap������·���е�ͼƬ
						if(list.size()==1){
							create_txt3.setVisibility(1);
							create_hs.setVisibility(1);
							create_photo1.setVisibility(1);
							Bitmap bm0 = BitmapFactory.decodeFile(filepath+"/"+list.get(0)+".png");
							//Ϊ��ֹԭʼͼƬ�������ڴ��������������Сԭͼ��ʾ��Ȼ���ͷ�ԭʼBitmapռ�õ��ڴ�
							Bitmap smallBitmap0 = ImageTools.zoomBitmap(bm0, bm0.getWidth() / SCALE, bm0.getHeight() / SCALE);
							//�ͷ�ԭʼͼƬռ�õ��ڴ棬��ֹout of memory�쳣����
							bm0.recycle();
							create_photo1.setImageBitmap(smallBitmap0);
						}else{
							//���鳤��Ϊ2ʱ��create_txt3��create_hs�ؼ��ɼ�������һ��Bitmap������·���е�ͼƬ
							if(list.size()==2){
								create_txt3.setVisibility(1);
								create_hs.setVisibility(1);
								create_photo1.setVisibility(1);
								create_photo2.setVisibility(1);
								Bitmap bm0 = BitmapFactory.decodeFile(filepath+"/"+list.get(0)+".png");
								Bitmap bm1 = BitmapFactory.decodeFile(filepath+"/"+list.get(1)+".png");
								//Ϊ��ֹԭʼͼƬ�������ڴ��������������Сԭͼ��ʾ��Ȼ���ͷ�ԭʼBitmapռ�õ��ڴ�
								Bitmap smallBitmap0 = ImageTools.zoomBitmap(bm0, bm0.getWidth() / SCALE, bm0.getHeight() / SCALE);
								Bitmap smallBitmap1 = ImageTools.zoomBitmap(bm1, bm1.getWidth() / SCALE, bm1.getHeight() / SCALE);
								//�ͷ�ԭʼͼƬռ�õ��ڴ棬��ֹout of memory�쳣����
								bm0.recycle();
								bm1.recycle();
								create_photo1.setImageBitmap(smallBitmap0);
								create_photo2.setImageBitmap(smallBitmap1);
							}else{
//								//���鳤��Ϊ3ʱ��create_txt3��create_hs�ؼ��ɼ�������һ��Bitmap������·���е�ͼƬ
								if(list.size()==3){
									create_txt3.setVisibility(1);
									create_hs.setVisibility(1);
									create_photo1.setVisibility(1);
									create_photo2.setVisibility(1);
									create_photo3.setVisibility(1);
									Bitmap bm0 = BitmapFactory.decodeFile(filepath+"/"+list.get(0)+".png");
									Bitmap bm1 = BitmapFactory.decodeFile(filepath+"/"+list.get(1)+".png");
									Bitmap bm2 = BitmapFactory.decodeFile(filepath+"/"+list.get(2)+".png");
									//Ϊ��ֹԭʼͼƬ�������ڴ��������������Сԭͼ��ʾ��Ȼ���ͷ�ԭʼBitmapռ�õ��ڴ�
									Bitmap smallBitmap0 = ImageTools.zoomBitmap(bm0, bm0.getWidth() / SCALE, bm0.getHeight() / SCALE);
									Bitmap smallBitmap1 = ImageTools.zoomBitmap(bm1, bm1.getWidth() / SCALE, bm1.getHeight() / SCALE);
									Bitmap smallBitmap2 = ImageTools.zoomBitmap(bm2, bm2.getWidth() / SCALE, bm2.getHeight() / SCALE);
									//�ͷ�ԭʼͼƬռ�õ��ڴ棬��ֹout of memory�쳣����
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
					Toast.makeText(SB_Create.this, "SD��·������", Toast.LENGTH_SHORT).show();
				}
				break;
				
			default:
				break;
			}
		}
	}
//	�������������գ�����ͼƬ
	public void showPicturePicker(Context context){
					Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					Uri imageUri = Uri.fromFile(new File(filepath,"image.jpg"));
					//ָ����Ƭ����·����SD������image.jpgΪһ����ʱ�ļ���ÿ�����պ����ͼƬ���ᱻ�滻
					openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
					startActivityForResult(openCameraIntent, TAKE_PICTURE);
	}
	
//	//��SD���ϴ���һ���ļ���
//    public void createSDCardDir(){
//     if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
//            // ����һ���ļ��ж��󣬸�ֵΪ�ⲿ�洢����Ŀ¼
//             File sdcardDir =Environment.getExternalStorageDirectory();
//           //�õ�һ��·����������sdcard���ļ���·��������
//             String path=sdcardDir+"/com.miyang.suibiji";
//             File path1 = new File(path);
//            if (!path1.exists()) {
//             //�������ڣ�����Ŀ¼��������Ӧ��������ʱ�򴴽�
//             path1.mkdirs();
//           }
//            }
//     else{
//      setTitle("false");
//      return;
//    }
//    }
	
/*
 * ������������
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
//					Toast.makeText(CreateActivity.this, "��,�㻹δ�����κ���ϢŶ~", Toast.LENGTH_SHORT).show();
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
	
//	����½�ҳ����༭�����ݣ������������ݽ����жϺʹ���
	public void editData(String tb_name){
//		���EditView�ı༭����
		theme_txt = create_et1.getText().toString();
		content_txt = create_et2.getText().toString();
//		�ж������Ƿ�Ϊ�գ�Ϊ������ʾ
		if(theme_txt.isEmpty()){
			Toast.makeText(SB_Create.this, "�ף��༭������������Ŷ��", Toast.LENGTH_SHORT).show();
		}else{
//				����contentvalues���󣬷�װ��¼��Ϣ
				ContentValues c = new ContentValues();
//				�ж�����ĳ��ȣ���ΪArrayList�ǳ����Զ��ı������
				if(list.size()==0){//���鳤��Ϊ0ʱ���������鳤��Ϊ3��Ϊnull
					list.add(0, null);
					list.add(1, null);
					list.add(2, null);
				}else{
					//���鳤��Ϊ1ʱ���������鳤��Ϊ3�ҵ�1��2λΪnull
					if(list.size()==1){
						list.add(1, null);
						list.add(2, null);
					}else{
						//���鳤��Ϊ2ʱ���������鳤��Ϊ3�ҵ�2λΪnull
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
//				��������
				createHelper.insert(c, tb_name);
//				��ת��������
				Intent intent = new Intent(SB_Create.this,SB_Home.class);
				SB_Create.this.startActivity(intent);
				finish();
			
		}
	}
	
	//Ϊ���ؼ����ü����������������ǰActivity
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
