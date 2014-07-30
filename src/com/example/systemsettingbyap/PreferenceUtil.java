package com.example.systemsettingbyap;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtil {

	private static final String PREF_NAME = "SYSTEM_SETTING_BY_APP_PRE";

	private static final String REGIST_AP_NAME_LIST = "REGIST_AP_LIST";
	
	private static final String AUTO_SET_MANNER = "AUTO_SET_MANNER";
	
	/**
	 * String型をプリファレンスに保存します。
	 * @param context アプリケーションコンテキスト
	 * @param keyName データキー名
	 * @param value   データ
	 * @return
	 */
	private static boolean putString(Context context ,String keyName, String value){
		SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE );
		return pref.edit().putString(keyName, value).commit();
	}
	/**
	 * String型をプリファレンスに取得します。
	 * @param context アプリケーションコンテキスト
	 * @param keyName データキー名
	 * @param value   データ
	 * @return
	 */
	private static String getString(Context context , String keyName ,String defValue){
		SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE );
		return pref.getString(keyName, defValue);
	}
	/**
	 * boolean型をプリファレンスに保存します。
	 * @param context アプリケーションコンテキスト
	 * @param keyName データキー名
	 * @param value   データ
	 * @return
	 */
	private static boolean putBoolean(Context context ,String keyName, boolean value){
		SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE );
		return pref.edit().putBoolean(keyName, value).commit();
	}
	/**
	 * boolean型をプリファレンスに取得します。
	 * @param context アプリケーションコンテキスト
	 * @param keyName データキー名
	 * @param value   データ
	 * @return
	 */
	private static boolean getBoolean(Context context , String keyName ,boolean defValue){
		SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE );
		return pref.getBoolean(keyName, defValue);
	}

	public static boolean putRegistAPList(Context context,String value){
		return putString(context,REGIST_AP_NAME_LIST,value);
	}

	public static String getRegistAPList(Context context){
		return getString(context,REGIST_AP_NAME_LIST,"");
	}

	public static boolean getAutoSetManner(Context context){
		return getBoolean(context,AUTO_SET_MANNER,true);
	}

	public static boolean setAutoSetManner(Context context,boolean value){
		return putBoolean(context,AUTO_SET_MANNER,value);
	}
}
