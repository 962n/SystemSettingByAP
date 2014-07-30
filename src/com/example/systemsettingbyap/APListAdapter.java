package com.example.systemsettingbyap;

import java.util.List;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class APListAdapter extends BaseAdapter {
	
	List<ScanResult> mList;
	APListProvider mApProvider;
	LayoutInflater mInflater;
	
	public APListAdapter(Context context){
		mApProvider = APListProvider.getSharedInstance(context);
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		WifiManager manager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
		// APをスキャン
		manager.startScan();
		// スキャン結果を取得
		mList = manager.getScanResults();
		
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder;
		if(convertView == null){
			convertView = this.mInflater.inflate(R.layout.regist_aplist_item, parent, false);
			holder = new ViewHolder();
			holder.text = (TextView)convertView.findViewById(R.id.text);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.text.setText(this.mList.get(position).SSID);

		return convertView;
	}

	@Override
	public long getItemId(int position) {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	private static class ViewHolder{
		TextView text;
	}

}
