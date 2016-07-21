package edu.anhthu.fragment;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import edu.anhthu.async.ListenerFromServer;
import edu.anhthu.main.MyApplication;
import edu.anhthu.main.R;
import edu.anhthu.model.Message;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MessengerFragment extends Fragment {
	private MyApplication mapplycation;
	Hashtable<String, ArrayList<Message>> mapDataPrivateChat;
	ArrayList<String> listmesseger;
	ListView lvmesseger;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mapplycation = (MyApplication) activity.getApplication();
		mapDataPrivateChat = mapplycation.getMapDataPrivateChat();
		getlistMesseger();
	}

	private void getlistMesseger() {
		listmesseger = new ArrayList<String>();
		Enumeration<String> e = mapDataPrivateChat.keys();
		while (e.hasMoreElements()) {
			String username = e.nextElement();
			listmesseger.add(username);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View myview = inflater.inflate(R.layout.list_messenger_fragment,
				container, false);
		lvmesseger = (ListView) myview.findViewById(R.id.lvMessenger);
		if (listmesseger.size() > 0) {
			ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity()
					.getBaseContext(), R.layout.item_contacts_list,
					listmesseger);
			lvmesseger.setAdapter(adapter);
		}
		lvmesseger.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				itemClicked(position);
			}

			private void itemClicked(int position) {
				String username=listmesseger.get(position);
				FragmentTransaction ftransaction = mapplycation
						.getMainchatct().getSupportFragmentManager()
						.beginTransaction();
				ToPrivateClientFragment userprivtachat = new ToPrivateClientFragment();
				Bundle b = new Bundle();
				b.putString(ListenerFromServer.USERNAME, username);
				userprivtachat.setArguments(b);
				ftransaction
						.replace(R.id.content_frame, userprivtachat)
						.addToBackStack(null).commit();				
			}
		});
		return myview;
	}

}
