package com.miyang.suibiji;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

public class CloseApplication extends Application {  
	  
	private List<Activity> activityList = new LinkedList();
	 private static CloseApplication instance;

	 private CloseApplication()
	 {
	   }
	    //����ģʽ�л�ȡΨһ��AgentApplicationʵ��
	    public static CloseApplication getInstance()
	    {
		   if(null == instance)
		    {
		    instance = new CloseApplication();
		    }
		    return instance;

	    }
	    //���Activity��������
	    public void addActivity(Activity activity){
	    	activityList.add(activity);
	    }
	    //��������Activity��finish
	    public void exit()
	    {

		    for(Activity activity:activityList)
		    {
			   if(activity!=null){
			             activity.finish();
			            }   
		    }

		    System.exit(0);   //�ر�JVM

	    }
}