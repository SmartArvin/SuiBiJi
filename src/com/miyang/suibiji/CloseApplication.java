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
	    //单例模式中获取唯一的AgentApplication实例
	    public static CloseApplication getInstance()
	    {
		   if(null == instance)
		    {
		    instance = new CloseApplication();
		    }
		    return instance;

	    }
	    //添加Activity到容器中
	    public void addActivity(Activity activity){
	    	activityList.add(activity);
	    }
	    //遍历所有Activity并finish
	    public void exit()
	    {

		    for(Activity activity:activityList)
		    {
			   if(activity!=null){
			             activity.finish();
			            }   
		    }

		    System.exit(0);   //关闭JVM

	    }
}