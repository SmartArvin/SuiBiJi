package com.miyang.suibiji;

import com.miyang.suibiji.R;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SB_Register extends Activity{

	EditText register_et1,register_et2;
	Button register_ok;
	DBHelper regHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sb_register_activity);
		
//		把当前Activity加入到CloseApplication的Activitylist中
		CloseApplication.getInstance().addActivity(this);
		
//		创建DBHelper的数据库工具类
		regHelper = new DBHelper(getApplicationContext());
		
//		获得控件的Id
		register_et1 = (EditText)findViewById(R.id.register_et1);
		register_et2 = (EditText)findViewById(R.id.register_et2);
		register_ok = (Button)findViewById(R.id.register_ok);
//		为register_ok控件绑定监听器
		register_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String et1 = register_et1.getText().toString();
				String et2 = register_et2.getText().toString();
				if(et1.length()==0){
					Toast.makeText(SB_Register.this, "请输入密码", Toast.LENGTH_SHORT).show();
				}else{
					if(et1.equals(et2)){
//						创建contentvalues对象，封装记录信息
						ContentValues cvs = new ContentValues();
						cvs.put("key1", et1);
//						插入新密码
						regHelper.insert(cvs, "login");
						Intent intent = new Intent(SB_Register.this,SB_Home.class);
						SB_Register.this.startActivity(intent);
						finish();
						}else{
							Toast.makeText(SB_Register.this, "请确认密码", Toast.LENGTH_SHORT).show();
						}	
				}
			}
		});
//		关闭数据库
		regHelper.close();
	}

	
	//为返回键设置监听器，点击结束当前Activity
			public boolean onKeyDown(int keyCode, KeyEvent event) {
				if(keyCode == KeyEvent.KEYCODE_BACK
				   && event.getAction() == KeyEvent.ACTION_DOWN){
					Intent intent = new Intent();
					intent.setClass(SB_Register.this,SB_Choice.class);
					SB_Register.this.startActivity(intent);
					finish();
				  }
				return true;
				} 
}
