package edu.anhthu.main;

import java.io.DataOutputStream;
import java.io.IOException;

import edu.anhthu.async.ListenerFromServer;
import edu.anhthu.async.ListenerFromServer.ActionLogin;
import edu.anhthu.connectsv.ConnectServerSock;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener,
		ActionLogin {
	public static final String HOST = "192.168.91.1";
	public static final int PORT = 1107;
	public static final String DATA_USER = "user";
	private static final String USER_NAME = "username";
	private static final String PASS_WORD = "password";
	private static final String CHECKED = "checked";
	private ConnectServerSock mconnectsvsk;
	private TextView registerScreen;
	private EditText edtUsername, edtPassword;
	private Button btnLogin;
	private CheckBox cblogin;
	private ListenerFromServer lsfromsver = null;
	private MainChat mainchat;
	private MyApplication mapplication;
	private String username;
	private String password;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		init();
		mconnectsvsk = new ConnectServerSock(this, HOST, PORT);
		loadinfouserlogin();
	}

	private void loadinfouserlogin() {
		SharedPreferences pre= getSharedPreferences(DATA_USER,
				LoginActivity.this.MODE_PRIVATE);
		boolean checkbox = pre.getBoolean(CHECKED, false);
		if (checkbox) {
			cblogin.setChecked(true);
			String username = pre.getString(USER_NAME, "");
			String password = pre.getString(PASS_WORD, "");
			edtUsername.setText(username);
			edtPassword.setText(password);
		}
	}

	private void mconnectsv() {
		mapplication = (MyApplication) getApplication();
		lsfromsver = new ListenerFromServer(this);
		lsfromsver.setLoginCallbak(this);
		lsfromsver.execute(mapplication.getMsocket());
		mainchat = new MainChat();
		lsfromsver.setToPrivateClient(mainchat);
		mapplication.setMlistennerfromserver(lsfromsver);
	}
	private void init() {
		cblogin = (CheckBox) findViewById(R.id.cblogin);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(this);
		edtUsername = (EditText) findViewById(R.id.txtUsername);
		edtPassword = (EditText) findViewById(R.id.txtPassword);
		registerScreen = (TextView) findViewById(R.id.link_to_register);
		registerScreen.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.link_to_register:
			if (lsfromsver == null)
				mconnectsv();
			Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
			startActivity(i);
			break;
		case R.id.btnLogin:
			onLogin();
			break;
		}
	}
	private void onLogin() {
		if (lsfromsver == null)
			mconnectsv();
		username = edtUsername.getText().toString();
		password = edtPassword.getText().toString();
		checkUserLogin(username, password);
	}

	private void checkUserLogin(String username, String password) {
		try {
			DataOutputStream dos = new DataOutputStream(mconnectsvsk
					.getMsocket().getOutputStream());
			dos.writeUTF("1," + username + "," + password + "," + "lol" + ","
					+ "lol");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void checkLogin(boolean b) {
		if (b) {
			mapplication.setMyname(username);
			if (cblogin.isChecked()) {
				saveuser();
			} else {
				clearuser();
			}
			Intent i = new Intent(LoginActivity.this, mainchat.getClass());
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
					| Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(i);
			Toast.makeText(this, "Login Success", Toast.LENGTH_LONG).show();
		} else {
			new AlertDialog.Builder(LoginActivity.this)
					.setIcon(R.drawable.icon)
					.setTitle("Login Fail")
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
								}
							}).create().show();
		}
	}

	private void clearuser() {
		SharedPreferences pre = getSharedPreferences(DATA_USER,
				LoginActivity.this.MODE_PRIVATE);
		SharedPreferences.Editor editor = pre.edit();
		editor.clear();
		editor.commit();
		editor.putBoolean(CHECKED, false);
		editor.commit();
	}

	private void saveuser() {
		SharedPreferences pre = getSharedPreferences(DATA_USER,
				LoginActivity.this.MODE_PRIVATE);
		SharedPreferences.Editor editor = pre.edit();
		editor.putString(USER_NAME, username);
		editor.putString(PASS_WORD, password);
		editor.putBoolean(CHECKED, true);
		editor.commit();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (KeyEvent.KEYCODE_BACK == keyCode) {
			try {
				mapplication = (MyApplication) getApplication();
				// dong luong lang nghe
				if (mapplication.getMlistennerfromserver() != null)
					mapplication.getMlistennerfromserver().cancel(true);
				// dong socket
				mapplication.getMsocket().close();
				mapplication.setMsocket(null);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return super.onKeyDown(keyCode, event);
	}
}