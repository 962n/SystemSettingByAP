package com.example.systemsettingbyap;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;

public class RegistAPListFragment extends Fragment {

	private final String TAG = RegistAPListFragment.class.getSimpleName();
	
	ListView mListView;
	RegistAPListAdapter mAdapter;
	FragmentManager mFM;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mFM = this.getChildFragmentManager();
		mAdapter = new RegistAPListAdapter(this.getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.aplist_fragment, container,false);

		mListView = (ListView)rootView.findViewById(R.id.listView);
		
		
		View header =  inflater.inflate(R.layout.aplist_header, null,false);
		Button button = (Button)header.findViewById(R.id.button);
		button.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				AddListFragment addFragemnt = new AddListFragment();
				mFM.beginTransaction()
				.add(addFragemnt, "alertDialog")
				.commit();
			}});
		CheckBox checkbox = (CheckBox)header.findViewById(R.id.checkBox);
		checkbox.setChecked(PreferenceUtil.getAutoSetManner(this.getActivity()));
		checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,	boolean isChecked) {
				LLog.d(TAG, "isChecked = "+isChecked);
				PreferenceUtil.setAutoSetManner(getActivity(), isChecked);
			}});
		
		mListView.addHeaderView(header, null, false);

		mListView.setOnItemLongClickListener(new OnItemLongClickListener(){
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				int count = ((ListView)parent).getHeaderViewsCount();
				LLog.d(TAG, "count = "+count);
				LLog.d(TAG, "position = "+position);
				position = position - count;

				DeleteFragment delFragemnt = new DeleteFragment();
				Bundle bundle = new Bundle();
				bundle.putInt(DeleteFragment.BUNDLE_KEY_DEL_POS, position);
				delFragemnt.setArguments(bundle);

				mFM.beginTransaction()
				.add(delFragemnt, "alertDialog")
				.commit();

				return false;
			}});
		
		mListView.setAdapter(this.mAdapter);
		
		return rootView;
	}
	
	private static class DeleteFragment extends DialogFragment {

		public static String BUNDLE_KEY_DEL_POS = "BUNDLE_KEY_DEL_POS";
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            final Fragment fragment = this.getParentFragment();
            final int index = this.getArguments().getInt(BUNDLE_KEY_DEL_POS);

            builder.setTitle("削除確認じゃ");
            builder.setMessage("おい、本当に削除すんのか？");
            builder.setPositiveButton("はっ、はい",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(fragment instanceof RegistAPListFragment){
                                APListProvider.getSharedInstance(getActivity()).deleteAPName(index);
                                ((RegistAPListFragment)fragment).mAdapter.notifyDataSetChanged();
                            }
                        }
                    });

            builder.setNegativeButton("やっ、やめます",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            return dialog;
        }
    }

	private static class AddListFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            final Fragment fragment = this.getParentFragment();

            builder.setTitle("選べ");
            final APListAdapter adapter = new APListAdapter(this.getActivity());
            ListView listView = new ListView(this.getActivity());
            TextView text = new TextView(this.getActivity());
            text.setText("wifiみつかんねーよ");
            listView.setEmptyView(text);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new OnItemClickListener(){
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					ScanResult result = (ScanResult)adapter.getItem(position);
                    APListProvider provider = APListProvider.getSharedInstance(getActivity());
                    if(!provider.isExist(result.SSID) ){
                        provider.registAPName(result.SSID);
                    }
                    if(fragment instanceof RegistAPListFragment){
                        ((RegistAPListFragment)fragment).mAdapter.notifyDataSetChanged();
                    }
                    dismiss();
				}});
            builder.setView(listView);


            builder.setNegativeButton("やっ、やめます",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            return dialog;
        }
    }

	

}
