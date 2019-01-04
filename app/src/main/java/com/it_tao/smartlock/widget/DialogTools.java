package com.it_tao.smartlock.widget;



import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class DialogTools {

	static Context context ;

	public static AlertDialog showDialog(Context context , String title,
										 CharSequence message,String buttonMs,OnClickListener listener ){
		
		Builder builder = new Builder(context,AlertDialog.THEME_HOLO_LIGHT);
		AlertDialog dialog = builder.setTitle(title)
				.setCancelable(false)
		.setMessage(message)
		.setNegativeButton(buttonMs,  listener).create();
		dialog.show();
		
		return dialog ;
		 
	}
	
	
	public static AlertDialog showDialog(Context context , String title,
										 CharSequence message,String buttonMs,OnClickListener listener ,String buttons,OnClickListener slistener ){
		
		if (buttons ==null)
			return showDialog(context, title, message, buttonMs, listener);
					
		
		Builder builder = new Builder(context,AlertDialog.THEME_HOLO_LIGHT);
		AlertDialog dialog = builder.setTitle(title)
				.setCancelable(false)
				.setMessage(message)
				.setNegativeButton(buttonMs,  listener)
				. setPositiveButton(buttons, slistener)
				.create();
		dialog.show();
		
		return dialog ;
		
	}
	
	
	 
	
	
	
	
	

}
