package edu.anhthu.fragment;

import java.util.ArrayList;
import java.util.List;

import edu.anhthu.apdapter.ContactsAdapter;
import edu.anhthu.async.ListenerFromServer;
import edu.anhthu.main.MainChat;
import edu.anhthu.main.MyApplication;
import edu.anhthu.main.R;
import edu.anhthu.model.Contacts;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

public class ContactsListFragment extends Fragment {
private ListView lvContacts;
private List<String> contactslist;
private MyApplication mapplycation;

	@Override
public void onAttach(Activity activity) {
	// TODO Auto-generated method stub
	super.onAttach(activity);
	 mapplycation= (MyApplication)activity.getApplication();
	String list=mapplycation.getContactslist();
	contactslist=new Contacts(list).getLcontacts();
}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View myview = inflater.inflate(R.layout.list_contacts_fragment,
				container, false);
		lvContacts=(ListView) myview.findViewById(R.id.lvContacts);
		ContactsAdapter adapter=new ContactsAdapter(getActivity().getBaseContext(), R.layout.item_contacts_list, contactslist);
		lvContacts.setAdapter(adapter);
		lvContacts.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long arg3) {
				itemClicked(position);
			}
		});
		return myview;
	}

	private void itemClicked(int position) {
		String username=contactslist.get(position);
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
}
