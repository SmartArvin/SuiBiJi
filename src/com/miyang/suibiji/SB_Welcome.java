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
		
//		把当前Activity加入到CloseApplication的Activitylist中
		CloseApplication.getInstance().addActivity(this);
		
		createSDCardDir();
		
//		创建DBHelper的数据库工具类
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
//			关闭游标
			cursor.close();
		}catch(SQLiteDatabaseLockedException sl){
			System.out.println(sl);
		}
		welcomeHelper.close();
	}
	//在SD卡上创建一个文件夹
    public void createSDCardDir(){
     if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            // 创建一个文件夹对象，赋值为外部存储器的目录
             File sdcardDir =Environment.getExternalStorageDirectory();
           //得到一个路径，内容是sdcard的文件夹路径和名字
             String path=sdcardDir+"/com.miyang.suibiji";
             File path1 = new File(path);
            if (!path1.exists()) {
             //若不存在，创建目录，可以在应用启动的时候创建
             path1.mkdirs();
           }
            }
     else{
      setTitle("false");
      return;
    }
    }
	
}
