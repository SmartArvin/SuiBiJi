package com.miyang.suibiji;

import com.miyang.suibiji.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SB_Login extends Activity{

	EditText login_et;
	Button login_ok;
	DBHelper loginHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sb_login_activity);
		
//		�ѵ�ǰActivity���뵽CloseApplication��Activitylist��
		CloseApplication.getInstance().addActivity(this);
		
//		����DBHelper���ݿ⹤����
		loginHelper = new DBHelper(getApplicationContext());
		
//		��ÿؼ���Id
		login_ok = (Button)findViewById(R.id.login_ok);
		login_et = (EditText)findViewById(R.id.login_et);
//		Ϊ�ؼ��󶨼�����
		login_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String et = login_et.getText().toString();
				try{
//					��ѯ���ݱ�
					Cursor cursor = loginHelper.rawQueryInf("login");
					while(cursor.moveToNext()){
						String temp = cursor.getString(cursor.getColumnIndex("key1"));
						if(et.equals(temp)){
							Intent intent = new Intent(SB_Login.this,SB_Home.class);
							SB_Login.this.startActivity(intent);
							finish();
						}
						else{
							Toast.makeText(SB_Login.this, "�Բ�����û�з���Ȩ�ޣ�", Toast.LENGTH_SHORT).show();
						}
					}
//					�ر��α�
					cursor.close();
				}catch(SQLiteDatabaseLockedException sl){
					System.out.println(sl);
				}
			}
		});
//		�ر����ݿ�
		loginHelper.close();
	}

}
