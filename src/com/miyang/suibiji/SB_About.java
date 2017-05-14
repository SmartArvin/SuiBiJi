package com.miyang.suibiji;

import com.miyang.suibiji.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SB_About extends Activity{

	Button about_back,about_web;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sb_about_activity);
		
//		�ѵ�ǰActivity���뵽CloseApplication��Activitylist��
		CloseApplication.getInstance().addActivity(this);
		
//		��ÿؼ���Id
		about_back = (Button)findViewById(R.id.about_back);
		about_web = (Button)findViewById(R.id.about_web);
//		Ϊ�ؼ��󶨼�����
		about_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SB_About.this,SB_Home.class);
				SB_About.this.startActivity(intent);
				finish();
			}
		});
		about_web.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				Uri uri=Uri.parse("http://soft.leidian.com/detail/index/soft_id/2014255");
	            intent.setAction(Intent.ACTION_VIEW);
	            intent.setData(uri);
	            startActivity(intent);
			}
		});
	}
	
	//Ϊ���ؼ����ü����������������ǰActivity
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			if(keyCode == KeyEvent.KEYCODE_BACK
			   && event.getAction() == KeyEvent.ACTION_DOWN){
				Intent intent = new Intent();
				intent.setClass(SB_About.this,SB_Home.class);
				SB_About.this.startActivity(intent);
				finish();
			  }
			return true;
			} 
}
