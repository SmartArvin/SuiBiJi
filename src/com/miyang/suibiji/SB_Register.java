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
		
//		�ѵ�ǰActivity���뵽CloseApplication��Activitylist��
		CloseApplication.getInstance().addActivity(this);
		
//		����DBHelper�����ݿ⹤����
		regHelper = new DBHelper(getApplicationContext());
		
//		��ÿؼ���Id
		register_et1 = (EditText)findViewById(R.id.register_et1);
		register_et2 = (EditText)findViewById(R.id.register_et2);
		register_ok = (Button)findViewById(R.id.register_ok);
//		Ϊregister_ok�ؼ��󶨼�����
		register_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String et1 = register_et1.getText().toString();
				String et2 = register_et2.getText().toString();
				if(et1.length()==0){
					Toast.makeText(SB_Register.this, "����������", Toast.LENGTH_SHORT).show();
				}else{
					if(et1.equals(et2)){
//						����contentvalues���󣬷�װ��¼��Ϣ
						ContentValues cvs = new ContentValues();
						cvs.put("key1", et1);
//						����������
						regHelper.insert(cvs, "login");
						Intent intent = new Intent(SB_Register.this,SB_Home.class);
						SB_Register.this.startActivity(intent);
						finish();
						}else{
							Toast.makeText(SB_Register.this, "��ȷ������", Toast.LENGTH_SHORT).show();
						}	
				}
			}
		});
//		�ر����ݿ�
		regHelper.close();
	}

	
	//Ϊ���ؼ����ü����������������ǰActivity
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
