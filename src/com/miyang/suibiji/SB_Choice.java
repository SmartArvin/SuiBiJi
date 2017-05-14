package com.miyang.suibiji;

import com.miyang.suibiji.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SB_Choice extends Activity implements OnTouchListener{

	TextView choice_recommend;
	RelativeLayout choice_rl1,choice_rl2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sb_cipher_choice);
		
//		把当前Activity加入到CloseApplication的Activitylist中
		CloseApplication.getInstance().addActivity(this);
		
//		获得控件的Id
		choice_recommend = (TextView)findViewById(R.id.choice_recommend);
		choice_rl1 = (RelativeLayout)findViewById(R.id.choice_rl1);
		choice_rl2 = (RelativeLayout)findViewById(R.id.choice_rl2);
		
//		推荐提示颤动（震动100次）怎么设置时间和当前Activity声明周期一致
			choice_recommend.setAnimation(shakeAnimation(100));
			
//		绑定监听器
		choice_rl1.setOnTouchListener(this);
		choice_rl2.setOnTouchListener(this);
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
//		设置Animation  
        Animation animDwon = AnimationUtils.loadAnimation(this, R.anim.show_down);  
        Animation animUp = AnimationUtils.loadAnimation(this, R.anim.show_up); 
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.choice_rl1:
			 switch (event.getAction()) { 
//			 按键按下时的动作
		        case MotionEvent.ACTION_DOWN:  
		        	choice_rl1.startAnimation(animDwon);  
		            animDwon.setFillAfter(true);  
		            break;  
//		             按键抬起时的动作
		        case MotionEvent.ACTION_UP:  
		        	choice_rl1.startAnimation(animUp);  
		            animUp.setFillAfter(true); 
		            new Handler().postDelayed(new Runnable(){    
		                public void run() {    
		                //execute the task
		                	Intent intent = new Intent(SB_Choice.this,SB_Register.class);
		        			SB_Choice.this.startActivity(intent);
		        			finish();
		                }    
		             }, 200); 
		            break;  
		        } 
     			break;
		case R.id.choice_rl2:
			 switch (event.getAction()) { 
//			 按键按下时的动作
		        case MotionEvent.ACTION_DOWN:  
		        	choice_rl2.startAnimation(animDwon);  
		            animDwon.setFillAfter(true);  
		            break;  
//		             按键抬起时的动作
		        case MotionEvent.ACTION_UP:  
		        	choice_rl2.startAnimation(animUp);  
		            animUp.setFillAfter(true); 
		            new Handler().postDelayed(new Runnable(){    
		                public void run() {    
		                //execute the task
		                	Intent intent = new Intent(SB_Choice.this,SB_Home.class);
		        			SB_Choice.this.startActivity(intent);
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

	//CycleTimes动画重复的次数  
	    public Animation shakeAnimation(int CycleTimes) {  
//	    	动画位移大小设定
	        Animation translateAnimation = new TranslateAnimation(0, 0, 0, 5);  
	        translateAnimation.setInterpolator(new CycleInterpolator(CycleTimes));  
//	        动画持续的时间
	        	translateAnimation.setDuration(CycleTimes*1000);  
		        return translateAnimation;  
	    }  
	
}

