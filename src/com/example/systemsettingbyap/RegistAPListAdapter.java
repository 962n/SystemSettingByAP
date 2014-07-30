package com.example.systemsettingbyap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RegistAPListAdapter extends BaseAdapter {
	
	APListProvider mApProvider;
	LayoutInflater mInflater;
	public RegistAPListAdapter(Context context){
		mApProvider = APListProvider.getSharedInstance(context);
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return mApProvider.getCount();
	}

	@Override
	public Object getItem(int position) {
		return mApProvider.getItem(position);
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
		
		holder.text.setText(this.mApProvider.getItem(position));

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
