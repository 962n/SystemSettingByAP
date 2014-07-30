package com.example.systemsettingbyap;

import java.util.ArrayList;

import android.content.Context;

public class APListProvider {
	private static String TAG = APListProvider.class.getSimpleName();

	private static Object sLock = new Object();
	private static APListProvider sProvider;
	private static int LIST_MAX_SIZE = 10;


	private ArrayList<String> mAPList;
	private Context mContext;
	
	public static APListProvider getSharedInstance(Context context){
		synchronized(sLock){
			if(sProvider == null){
				sProvider = new APListProvider(context);
			}
		}
		return sProvider;
	}


	private APListProvider(Context context){
		mContext = context;

		mAPList = new ArrayList<String>();

		String str = PreferenceUtil.getRegistAPList(context);

		String[] aplist = str.split(",");

		if(aplist != null && aplist.length > 0){
			for(int i = 0;i<aplist.length;i++){
				String ap = aplist[i];
				if(!ap.isEmpty()){
					mAPList.add(ap);
				}
			}
		}
	}


	/**
	 * APを登録する
	 * @param targetApName 登録したAP名
	 * @return true:成功/false:失敗
	 */
	public boolean registAPName(String targetApName){
		if(mAPList.size() >= LIST_MAX_SIZE){
			return false;
		}
		if(targetApName.isEmpty()){
			return false;
		}
		mAPList.add(targetApName);
		return commitAPList();
	}

	/**
	 * APを削除する
	 * @param index 削除したいAPのリスト位置
	 */
	public void deleteAPName(int index ){
		
		if(index < 0 || index > mAPList.size()-1 ){
			return;
		}

		String apName = mAPList.get(index);
		deleteAPName(apName);
	}
	
	public void deleteAPName(String targetAPName){
		if(!mAPList.remove(targetAPName)){
			return;
		}
		commitAPList();
	}
	
	private boolean commitAPList(){
		StringBuilder builder = new StringBuilder();
		String value = null;

		if(mAPList.size() > 0){
			for(String ap:mAPList){
				builder.append(ap+",");
			}
			value = builder.substring(0, builder.length() -1);
		} else {
			value = "";
		}
		LLog.d(TAG,"commitAPList value = "+value);
		return PreferenceUtil.putRegistAPList(mContext, value);
	}
	
	public void dumpAPList(){
		StringBuilder builder = new StringBuilder();
		builder.append("-------dumpAPList dump start------- \n");
		for(String ap:mAPList){
			builder.append(ap + "¥n");
		}
		builder.append("-------dumpAPList dump end------- \n");
		LLog.d(TAG,builder.toString());
	}
	public boolean isExist(String targetName){
		LLog.d(TAG,"isExist targetName = "+targetName);
		if(targetName == null){
			return false;
		}
		dumpAPList();
		boolean isExist = false;
		for(String apName:mAPList){
			if(apName.indexOf(targetName) != -1 ||
					targetName.indexOf(apName) != -1){
				isExist = true;
			}
		}
		LLog.d(TAG,"isExist = "+isExist);
		return isExist;
	}

	public String getItem(int index){
		return mAPList.get(index);
	}
	
	public int getCount(){
		return mAPList.size();
	}


}
