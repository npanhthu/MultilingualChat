package edu.anhthu.fragment;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import edu.anhthu.apdapter.MessagesListAdapter;
import edu.anhthu.async.ListenerFromServer.ActionUpdateMessegerRomChat;
import edu.anhthu.main.MainChat;
import edu.anhthu.main.MyApplication;
import edu.anhthu.main.R;
import edu.anhthu.model.Message;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class HomeFragment extends Fragment implements
		ActionUpdateMessegerRomChat {
	private MyApplication myapplication;
	private Context mcontext;
	private ArrayList<Message> listmsg;
	private Spinner spnlanguage;
	private String[] arrstrspnlanguage;
	private ListView listviewmsg;
	private Button btnSend;
	private EditText edtinputMsg;
	private MessagesListAdapter adapter;
	private SharedPreferences pre;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		myapplication = (MyApplication) activity.getApplication();
		listmsg = myapplication.getDataRomChat();
		mcontext = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View myview = inflater.inflate(R.layout.home_fragment_layout,
				container, false);
		arrstrspnlanguage = getResources().getStringArray(R.array.languages);
		myapplication.getMlistennerfromserver().setUpdateListRomChat(this);
		spnlanguage = (Spinner) myview.findViewById(R.id.spnlanguage);
		// set adapter cho spinner
		ArrayAdapter<String> spadapter = new ArrayAdapter<>(mcontext,
				android.R.layout.simple_list_item_1, arrstrspnlanguage);
		spnlanguage.setAdapter(spadapter);
		pre = mcontext.getSharedPreferences(MainChat.FILE_NAME_SHARE_PRE,
				mcontext.MODE_PRIVATE);
		//setselect cho spinner
		int position=pre.getInt(MainChat.KEY_HOME_LANGUAGE, 0);
		spnlanguage.setSelection(position);
		spnlanguage.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				SharedPreferences.Editor editer = pre.edit();
				editer.putInt(MainChat.KEY_HOME_LANGUAGE, position);
				editer.commit();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		listviewmsg = (ListView) myview.findViewById(R.id.list_view_messages);
		btnSend = (Button) myview.findViewById(R.id.btnSend);
		edtinputMsg = (EditText) myview.findViewById(R.id.inputMsg);
		setlisvew();
		btnSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				sendMesseger();
			}
		});
		setlisvew();
		return myview;
	}

	protected void sendMesseger() {
		try {
			String msgforsend = edtinputMsg.getText().toString().trim();
			if (!msgforsend.equals("")) {
				DataOutputStream dos = new DataOutputStream(myapplication
						.getMsocket().getOutputStream());
				dos.writeUTF("2," + myapplication.getMyname() + ","
						+ msgforsend);
				updatelistmsg();
				edtinputMsg.setText("");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setlisvew() {
		// TODO Auto-generated method stub
		if (listmsg.size() > -1) {
			adapter = new MessagesListAdapter(mcontext, listmsg);
			listviewmsg.setAdapter(adapter);
		}
	}

	@Override
	public void updatelist() {
		updatelistmsg();
	}

	private void updatelistmsg() {
		if (listmsg.size() > -1 && listmsg.size() == 1) {
			adapter = new MessagesListAdapter(mcontext, listmsg);
			listviewmsg.setAdapter(adapter);
		}
		listmsg = myapplication.getDataRomChat();
		adapter.notifyDataSetChanged();
	}

}
