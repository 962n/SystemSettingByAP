package com.example.systemsettingbyap;

import android.util.Log;

public class LLog {
	
	public static boolean DEBUGLOG = true;

	public static void v(String tag,String msg){
		if(DEBUGLOG){
			Log.v(tag, msg);
		}
	}
	public static void d(String tag,String msg){
		if(DEBUGLOG){
			Log.d(tag, msg);
		}
	}
	public static void e(String tag,String msg){
		Log.e(tag, msg);
	}
}
