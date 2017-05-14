package com.miyang.suibiji;

import java.io.File;

import com.miyang.suibiji.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;

public class SB_Welcome extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sb_welcome);
		
//		�ѵ�ǰActivity���뵽CloseApplication��Activitylist��
		CloseApplication.getInstance().addActivity(this);
		
		createSDCardDir();
		
//		����DBHelper�����ݿ⹤����
		DBHelper welcomeHelper = new DBHelper(getApplicationContext());
		try{
			Cursor cursor = welcomeHelper.rawQueryCount("login");
			while(cursor.moveToFirst()){
				int count = cursor.getInt(0);
				if(count==0){
					new Handler().postDelayed(new Runnable(){    
						   public void run() {
							   Intent intent = new Intent();
							   intent.setClass(SB_Welcome.this, SB_Choice.class);
								SB_Welcome.this.startActivity(intent);
								finish();
						    }    
						 }, 1500);
					
				}else{
					new Handler().postDelayed(new Runnable(){    
						   public void run() {
							   Intent intent = new Intent();
							   intent.setClass(SB_Welcome.this, SB_Login.class);
								SB_Welcome.this.startActivity(intent);
								finish();
						    }    
						 }, 1500);
				}
				if(!cursor.isAfterLast())
					break;
			}
//			�ر��α�
			cursor.close();
		}catch(SQLiteDatabaseLockedException sl){
			System.out.println(sl);
		}
		welcomeHelper.close();
	}
	//��SD���ϴ���һ���ļ���
    public void createSDCardDir(){
     if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            // ����һ���ļ��ж��󣬸�ֵΪ�ⲿ�洢����Ŀ¼
             File sdcardDir =Environment.getExternalStorageDirectory();
           //�õ�һ��·����������sdcard���ļ���·��������
             String path=sdcardDir+"/com.miyang.suibiji";
             File path1 = new File(path);
            if (!path1.exists()) {
             //�������ڣ�����Ŀ¼��������Ӧ��������ʱ�򴴽�
             path1.mkdirs();
           }
            }
     else{
      setTitle("false");
      return;
    }
    }
	
}
