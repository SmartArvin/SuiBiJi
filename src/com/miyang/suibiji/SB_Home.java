package com.miyang.suibiji;

import com.miyang.suibiji.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class SB_Home extends Activity implements OnTouchListener{

	RelativeLayout home_rl1,home_rl2,home_rl3,home_rl4,home_rl5,home_rl6;
	private Animation translate1,translate2,translate3,translate4;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sb_home_activity);
		
//		�ѵ�ǰActivity���뵽CloseApplication��Activitylist��
		CloseApplication.getInstance().addActivity(this);
		
		Animation();
		
//		��ÿؼ���Id
		home_rl1 = (RelativeLayout)findViewById(R.id.home_rl1);
		home_rl2 = (RelativeLayout)findViewById(R.id.home_rl2);
		home_rl3 = (RelativeLayout)findViewById(R.id.home_rl3);
		home_rl4 = (RelativeLayout)findViewById(R.id.home_rl4);
		home_rl5 = (RelativeLayout)findViewById(R.id.home_rl5);
		home_rl6 = (RelativeLayout)findViewById(R.id.home_rl6);
//		Ϊ�ؼ��󶨼�����
		home_rl1.setOnTouchListener(this);
		home_rl2.setOnTouchListener(this);
		home_rl3.setOnTouchListener(this);
		home_rl4.setOnTouchListener(this);
		home_rl5.setOnTouchListener(this);
		home_rl6.setOnTouchListener(this);
	}
	
	/*
	 * Animation������Ϊhomemore�Ŀؼ�����
	 * ����Ч����
	*/
		private void Animation(){
				//���ÿؼ�
			home_rl1 = (RelativeLayout)findViewById(R.id.home_rl1);
			home_rl3 = (RelativeLayout)findViewById(R.id.home_rl3);
			home_rl4 = (RelativeLayout)findViewById(R.id.home_rl4);
			home_rl6 = (RelativeLayout)findViewById(R.id.home_rl6);
				
				//�ı�ͼ���λ��
				translate1 = new TranslateAnimation(-200.0f, 0.0f, 0.0f, 0.0f);  
				translate1.setDuration(1000);
				translate2 = new TranslateAnimation(200.0f, 0.0f, 0.0f, 0.0f);  
				translate2.setDuration(1000);
				translate3 = new TranslateAnimation(-200.0f, 0.0f, 0.0f, 0.0f);  
				translate3.setDuration(1200);
				translate4 = new TranslateAnimation(200.0f, 0.0f, 0.0f, 0.0f);  
				translate4.setDuration(1200);
				
				//����Fill����
				translate1.setFillEnabled(true);
				translate2.setFillEnabled(true);
				translate3.setFillEnabled(true);
				translate4.setFillEnabled(true);
				//���ö��������һ֡�Ǳ�����View����
				translate1.setFillAfter(true);
				translate2.setFillAfter(true);
				translate3.setFillAfter(true);
				translate4.setFillAfter(true);
				
				home_rl1.startAnimation(translate1);
				home_rl3.startAnimation(translate2);
				home_rl4.startAnimation(translate3);
				home_rl6.startAnimation(translate4);
			}
	
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
//		����Animation  
        Animation animDwon = AnimationUtils.loadAnimation(this, R.anim.show_down);  
        Animation animUp = AnimationUtils.loadAnimation(this, R.anim.show_up); 
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.home_rl1:
			 switch (event.getAction()) { 
//			 ��������ʱ�Ķ���
		        case MotionEvent.ACTION_DOWN:  
		        	home_rl1.startAnimation(animDwon);  
		            animDwon.setFillAfter(true);  
		            break;  
//		             ����̧��ʱ�Ķ���
		        case MotionEvent.ACTION_UP:  
		        	home_rl1.startAnimation(animUp);  
		            animUp.setFillAfter(true); 
		            new Handler().postDelayed(new Runnable(){    
		                public void run() {    
		                //execute the task
		                	Intent intent = new Intent(SB_Home.this,SB_Create.class);
		        			SB_Home.this.startActivity(intent);
		        			finish();
		                }    
		             }, 200); 
		            break;  
		        } 
     			break;
		case R.id.home_rl2:
			 switch (event.getAction()) { 
//			 ��������ʱ�Ķ���
		        case MotionEvent.ACTION_DOWN:  
		        	home_rl2.startAnimation(animDwon);  
		            animDwon.setFillAfter(true);  
		            break;  
//		             ����̧��ʱ�Ķ���
		        case MotionEvent.ACTION_UP:  
		        	home_rl2.startAnimation(animUp);  
		            animUp.setFillAfter(true); 
		            new Handler().postDelayed(new Runnable(){    
		                public void run() {    
		                //execute the task
		                	Intent intent = new Intent(SB_Home.this,SB_Search.class);
		        			SB_Home.this.startActivity(intent);
		        			finish();
		                }    
		             }, 200); 
		            break;  
		        } 
     			break;
		case R.id.home_rl3:
			switch (event.getAction()) {  
	        case MotionEvent.ACTION_DOWN:  
	        	home_rl3.startAnimation(animDwon);  
	            animDwon.setFillAfter(true);  
	            break;  
	  
	        case MotionEvent.ACTION_UP:  
	        	home_rl3.startAnimation(animUp);  
	            animUp.setFillAfter(true); 
	            new Handler().postDelayed(new Runnable(){    
	                public void run() {    
	                //execute the task
	                	Intent intent = new Intent(SB_Home.this,SB_Content.class);
	        			SB_Home.this.startActivity(intent);
	        			finish();
	                }    
	             }, 200); 
	            break;  
	        } 
 			break;
		case R.id.home_rl4:
			switch (event.getAction()) {  
	        case MotionEvent.ACTION_DOWN:  
	        	home_rl4.startAnimation(animDwon);  
	            animDwon.setFillAfter(true);  
	            break;  
	  
	        case MotionEvent.ACTION_UP:  
	        	home_rl4.startAnimation(animUp);  
	            animUp.setFillAfter(true); 
	            new Handler().postDelayed(new Runnable(){    
	                public void run() {    
	                //execute the task
	                	Intent intent = new Intent(SB_Home.this,SB_Like.class);
	        			SB_Home.this.startActivity(intent);
	        			finish();
	                }    
	             }, 200); 
	            break;  
	        } 
 			break;
		case R.id.home_rl5:
			switch (event.getAction()) {  
	        case MotionEvent.ACTION_DOWN:  
	        	home_rl5.startAnimation(animDwon);  
	            animDwon.setFillAfter(true);  
	            break;  
	  
	        case MotionEvent.ACTION_UP:  
	        	home_rl5.startAnimation(animUp);  
	            animUp.setFillAfter(true); 
	            new Handler().postDelayed(new Runnable(){    
	                public void run() {    
	                //execute the task
	                	Intent intent = new Intent(SB_Home.this,SB_Safe.class);
	        			SB_Home.this.startActivity(intent);
	        			finish();
	                }    
	             }, 200); 
	            break;  
	        } 
 			break;
		case R.id.home_rl6:
			switch (event.getAction()) {  
	        case MotionEvent.ACTION_DOWN:  
	        	home_rl6.startAnimation(animDwon);  
	            animDwon.setFillAfter(true);  
	            break;  
	  
	        case MotionEvent.ACTION_UP:  
	        	home_rl6.startAnimation(animUp);  
	            animUp.setFillAfter(true); 
	            new Handler().postDelayed(new Runnable(){    
	                public void run() {    
	                //execute the task
	                	Intent intent = new Intent(SB_Home.this,SB_About.class);
	        			SB_Home.this.startActivity(intent);
	        			finish();
	                }    
	             }, 200); 
	            break;  
	        } 
 			break;
		default:
			break;
		}
		return true;
	} 
	//����Ӳ�����ذ�ť�ļ�������ʵ���ٰ�һ���˳�����Ĺ���
	private long exitTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	if(keyCode == KeyEvent.KEYCODE_BACK
	   && event.getAction() == KeyEvent.ACTION_DOWN){
	     if((System.currentTimeMillis()-exitTime) > 2000){
	         Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����", Toast.LENGTH_SHORT).show();
	         exitTime = System.currentTimeMillis();
	  } else {
		  CloseApplication.getInstance().exit();
	  }
	     return true;
	    }
	return super.onKeyDown(keyCode, event);
	}

}
