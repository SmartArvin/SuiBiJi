package com.miyang.suibiji;

import com.miyang.suibiji.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

public class MyDialog extends Dialog{
	 Context context;
	    public MyDialog(Context context) {
	        super(context);
	        this.context = context;
	    }
	    public MyDialog(Context context, int theme){
	        super(context, theme);
	        this.context = context;
	    }
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        // TODO Auto-generated method stub
	        super.onCreate(savedInstanceState);
	        this.setContentView(R.layout.sb_dialog_content_list);
	    }

	}