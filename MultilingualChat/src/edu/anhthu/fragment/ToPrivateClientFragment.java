package edu.anhthu.fragment;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import edu.anhthu.apdapter.MessagesListAdapter;
import edu.anhthu.async.ListenerFromServer;
import edu.anhthu.async.ListenerFromServer.ActionClientSendPrivate;
import edu.anhthu.async.ListenerFromServer.ActionUpdatePrivateChat;
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
import android.widget.TextView;
import android.widget.Toast;

public class ToPrivateClientFragment extends Fragment implements
		ActionUpdatePrivateChat {
	private MyApplication myapplication;
	private Context mcontext;
	private ArrayList<Message> listmsg;
	private ListView listviewmsg;
	private Button btnSend;
	private EditText edtinputMsg;
	private TextView tvusername;
	private String[] arrstrspnlanguage;
	private Spinner spnlanguage;
	private String username;
	private Boolean checkonpause = false;
	private MessagesListAdapter adapter;
	private SharedPreferences pre;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		myapplication = (MyApplication) activity.getApplication();
		mcontext = activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		checkonpause = true;
		super.onCreate(savedInstanceState);
		Bundle b = new Bundle();
		b = getArguments();
		username = b.getString(ListenerFromServer.USERNAME);
		if (myapplication.getMapDataPrivateChat().get(username) != null)
			listmsg = myapplication.getMapDataPrivateChat().get(username);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View myview = inflater.inflate(R.layout.toprivateclient_fragment,
				container, false);

		arrstrspnlanguage = getResources().getStringArray(R.array.languages);
		spnlanguage = (Spinner) myview.findViewById(R.id.pspnlanguage);
		// set adapter cho spinner
		ArrayAdapter<String> spadapter = new ArrayAdapter<>(mcontext,
				android.R.layout.simple_list_item_1, arrstrspnlanguage);
		spnlanguage.setAdapter(spadapter);
		pre = mcontext.getSharedPreferences(MainChat.FILE_NAME_SHARE_PRE,
				mcontext.MODE_PRIVATE);
		// setselect cho spinner
		int position = pre.getInt(MainChat.KEY_PRIVATE_LANGUAGE, 0);
		spnlanguage.setSelection(position);
		myapplication.getMlistennerfromserver().setUpdateLitsPrivatechat(this);
		listviewmsg = (ListView) myview.findViewById(R.id.plist_view_messages);
		btnSend = (Button) myview.findViewById(R.id.pbtnSend);
		edtinputMsg = (EditText) myview.findViewById(R.id.pinputMsg);
		tvusername = (TextView) myview.findViewById(R.id.ptvusernameprv);
		tvusername.setText(username);
		btnSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				sendMesseger();
			}
		});
		setlisvew();

		spnlanguage.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				SharedPreferences.Editor editer = pre.edit();
				editer.putInt(MainChat.KEY_PRIVATE_LANGUAGE, position);
				editer.commit();
				Log.d("TAG", position+"");
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		return myview;
	}

	private void setlisvew() {
		// TODO Auto-generated method stub
		if (listmsg != null) {
			adapter = new MessagesListAdapter(mcontext, listmsg);
			listviewmsg.setAdapter(adapter);
		}
	}

	@Override
	public void updatelist() {
		updatelistmsg();
	}

	private void updatelistmsg() {
		if (listmsg != null) {
			if (listmsg.size() == 1) {
				adapter = new MessagesListAdapter(mcontext, listmsg);
				listviewmsg.setAdapter(adapter);
			}
			listmsg = myapplication.getMapDataPrivateChat().get(username);
			adapter.notifyDataSetChanged();
		}

	}

	protected void sendMesseger() {
		try {
			String msgforsend = edtinputMsg.getText().toString();
			if (!myapplication.getMapDataPrivateChat().containsKey(username)) {
				myapplication.getMapDataPrivateChat().put(username,
						new ArrayList<Message>());
			}
			myapplication
					.getMapDataPrivateChat()
					.get(username)
					.add(new Message(myapplication.getMyname(), msgforsend,
							true));

			DataOutputStream dos = new DataOutputStream(myapplication
					.getMsocket().getOutputStream());
			dos.writeUTF("22," + username + "," + myapplication.getMyname()
					+ "," + msgforsend);
			listmsg = myapplication.getMapDataPrivateChat().get(username);
			updatelistmsg();
			edtinputMsg.setText("");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Boolean getCheckonpause() {
		return checkonpause;
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		checkonpause = false;
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		checkonpause = false;
	}

}
