package com.example.systemsettingbyap;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.wifi.WifiManager;

public class WifiStateReceiver extends BroadcastReceiver {
	private static String TAG = WifiStateReceiver.class.getSimpleName();

	@Override
	public void onReceive(Context context, Intent intent) {
		LLog.d(TAG, "intent.getAction() = "+intent.getAction());
		WifiManager manager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);

		LLog.d(TAG , "wifistate = "+manager.getWifiState());
		LLog.d(TAG,"" +manager.getConnectionInfo().getSSID());
		LLog.d(TAG , "intent = "+intent.toString());
		
//		if(!WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())){
//			return;
//		}

//		Bundle extras = intent.getExtras();
//		if(extras != null){
//			//インテント情報がなければ何もできないので、処理終了
//			LLog.d(TAG, "インテント情報がない");
//			return;
//		}
//		//ネットワーク情報を取得する
//		NetworkInfo info = extras.getParcelable(WifiManager.EXTRA_NETWORK_INFO);
//		if(info != null){
//			//エラー処理
//			LLog.d(TAG, "ネットワーク情報がない");
//			return;
//		}
//		if (!info.isConnected()) {
//			LLog.d(TAG, "ネットワークが接続されていない");
//			return;
//		}

		APListProvider provider = APListProvider.getSharedInstance(context);

		switch(manager.getWifiState()){
		case WifiManager.WIFI_STATE_ENABLED:
			if(provider.isExist(manager.getConnectionInfo().getSSID())){
				setRingerNormal(context);
			} else {
				setRingerManner(context);
			}
			break;

		case WifiManager.WIFI_STATE_DISABLED:
			setRingerManner(context);
			break;
		}

	}
	
	
	private static void setRingerNormal(Context context){
		AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		int mode = audioManager.getRingerMode();
		LLog.d(TAG,"setRingerMode Normal rignerMode = "+mode);
		switch (mode) {
		case AudioManager.RINGER_MODE_SILENT:
		case AudioManager.RINGER_MODE_VIBRATE:
			// マナーモードなので何もしない
			LLog.d(TAG,"setRingerMode normal");
			audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
			break;
		}
	}
	private static void setRingerManner(Context context){
		if(!PreferenceUtil.getAutoSetManner(context)){
			return;
		}

		AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		int mode = audioManager.getRingerMode();
		LLog.d(TAG,"setRingerMode Manner rignerMode = "+mode);
		switch (mode) {
		case AudioManager.RINGER_MODE_NORMAL:
			LLog.d(TAG,"setRingerMode Manner");
			audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
			break;
		}
	}

}
