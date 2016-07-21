package edu.anhthu.main;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.channels.AlreadyConnectedException;

import edu.anhthu.async.ListenerFromServer.ActionRegister;
import edu.anhthu.connectsv.ConnectServerSock;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnClickListener,
		ActionRegister {
	private EditText edtUsername, edtPassword, edtCofirmPass, edtFullname,
			edtAddress;
	private Button btnRegister;
	private TextView loginScreen;

	public RegisterActivity() {
		
	}

	public RegisterActivity(ConnectServerSock mconnectsvsk) {
		// this.mconnectsv = mconnectsvsk;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		edtUsername = (EditText) findViewById(R.id.username);
		edtPassword = (EditText) findViewById(R.id.password);
		edtCofirmPass = (EditText) findViewById(R.id.Confirm_password);
		edtFullname = (EditText) findViewById(R.id.reg_fullname);
		edtAddress = (EditText) findViewById(R.id.address);
		loginScreen = (TextView) findViewById(R.id.link_to_login);
		loginScreen.setOnClickListener(this);
		btnRegister = (Button) findViewById(R.id.btnRegister);
		btnRegister.setOnClickListener(this);
		MyApplication mapplication = (MyApplication) getApplication();
		mapplication.getMlistennerfromserver().setRegisterCallbak(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.link_to_login:
			finish();
			break;

		case R.id.btnRegister:
			eventRegister();
			break;
		}

	}

	private void eventRegister() {
		String username = edtUsername.getText().toString();
		String password = edtPassword.getText().toString();
		String cfpassword = edtCofirmPass.getText().toString();
		String fullname = edtFullname.getText().toString();
		String address = edtAddress.getText().toString();
		if (username.equals("") || password.equals("")) {
			Toast.makeText(this, "Username va Password khong duoc de trong",
					Toast.LENGTH_LONG).show();
		} else if (!password.equals(cfpassword)) {
			Toast.makeText(this, "Password Khong khop", Toast.LENGTH_LONG)
					.show();
		} else {
			try {
				MyApplication mapplication = (MyApplication) getApplication();
				DataOutputStream dos = new DataOutputStream(
						mapplication.getMsocket().getOutputStream());
				dos.writeUTF("11," + username + "," + password + "," + fullname
						+ "," + address);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public void checkRegister(boolean b) {
		if(b){
			AlertDialog.Builder malerdialog=new Builder(RegisterActivity.this);
			malerdialog.setMessage("Dang Ky Thanh Cong! Nhap Ok De Quay Lai Dang Nhap");
			malerdialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			}).create().show();
		}else{
			AlertDialog.Builder malerdialog=new Builder(RegisterActivity.this);
			malerdialog.setMessage("Ten Nguoi Dung Da Ton Tai Vui Long Thu Voi Ten Khac");
			malerdialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
				}
			}).create().show();
		}
	}

}