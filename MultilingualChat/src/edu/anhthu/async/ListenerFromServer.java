package edu.anhthu.async;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;


import edu.anhthu.fragment.ToPrivateClientFragment;
import edu.anhthu.main.MainChat;
import edu.anhthu.main.MyApplication;
import edu.anhthu.main.R;
import edu.anhthu.model.MTranslate;
import edu.anhthu.model.Message;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;

public class ListenerFromServer extends AsyncTask<Socket, String, String> {
	private ActionLogin mlogincallback;
	private Context conText;
	private ActionRegister mregister;
	private ActionUpdateMessegerRomChat mHomeFragment;
	private ActionClientSendPrivate mMainChat;
	private MyApplication myapplication;
	private MainChat fragmentMainChat;
	private ActionUpdatePrivateChat toprivateClient;
	private ToPrivateClientFragment mprivateclientfragment;
	private SharedPreferences pre;
	public static final String USERNAME = "username";

	public interface ActionRegister {
		public void checkRegister(boolean b);
	}

	public interface ActionUpdatePrivateChat {
		public void updatelist();
	}

	public interface ActionLogin {
		public void checkLogin(boolean b);
	}

	public interface ActionUpdateMessegerRomChat {
		public void updatelist();
	}

	public interface ActionClientSendPrivate {
		public void showPrivateClient(String username, MainChat mn);
	}

	public ListenerFromServer(Context context) {
		this.conText = context;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		myapplication = (MyApplication) conText.getApplicationContext();
	}

	@Override
	protected String doInBackground(Socket... params) {
		try {
			DataInputStream dis = new DataInputStream(
					params[0].getInputStream());
			while (true) {
				if(isCancelled()){
					dis.close();
					break;
				}
				String msg = dis.readUTF();
				publishProgress(msg);
			}
		} catch (IOException e) {
			return null;
		}
		return null;
	}

	@Override
	protected void onProgressUpdate(String... values) {
		super.onProgressUpdate(values);
		final String[] arrmsg = values[0].split(",");
		switch (arrmsg[0]) {
		case "1": // xu ly login
			if (arrmsg[1].equals("true")) {
				mlogincallback.checkLogin(true);
			} else {
				mlogincallback.checkLogin(false);
			}
			break;
		case "12":
			if (arrmsg[1].equals("true")) {
				mregister.checkRegister(true);
			} else {
				mregister.checkRegister(false);
			}
			break;
		case "0": // xu ly list contacts
			myapplication.setContactslist(values[0]);
			break;
		case "2": // xu ly Msg Send All User
			boolean b = false;
			if (myapplication.getMyname().equals(arrmsg[1])) {
				b = true;
			}
			// dich ngon ngu tu nguoi gui
			if (!b) {// nguoi gui
				pre = conText.getSharedPreferences(
						MainChat.FILE_NAME_SHARE_PRE, conText.MODE_PRIVATE);
				String[] arrlg = conText.getResources().getStringArray(
						R.array.languages);
				int position = pre.getInt(MainChat.KEY_HOME_LANGUAGE, 0);
				String language = arrlg[position];
				if (position == 0) {// ngon ngu default
					myapplication.getDataRomChat().add(
							new Message(arrmsg[1], arrmsg[2], b));
					if (mHomeFragment != null) {
						mHomeFragment.updatelist();
					}
				} else {// dich ngon ngu
					Handler handler = new Handler();
					ftranslater(handler, arrmsg[2], language, arrmsg[1], true,
							false);
				}
			} else {
				myapplication.getDataRomChat().add(
						new Message(arrmsg[1], arrmsg[2], b));
				if (mHomeFragment != null) {
					mHomeFragment.updatelist();
				}
			}
			break;
		case "22": // xu ly private client to client
			Enumeration e = myapplication.getMapDataPrivateChat().keys();
			boolean pivatecheck = true;// kiem tra xem nickname dang gui da co trong
									// hashtable chua
			pre = conText.getSharedPreferences(MainChat.FILE_NAME_SHARE_PRE,
					conText.MODE_PRIVATE);
			String[] arrlg = conText.getResources().getStringArray(
					R.array.languages);
			int position = pre.getInt(MainChat.KEY_PRIVATE_LANGUAGE, 0);
			String language = arrlg[position];
			while (e.hasMoreElements()) {
				String key = (String) (e.nextElement());
				if (key.equals(arrmsg[1])) {
					pivatecheck = false;
					if (position != 0) {
						Handler handler = new Handler();
						ftranslater(handler, arrmsg[2], language, arrmsg[1],
								false, pivatecheck);
					} else {
						myapplication.getMapDataPrivateChat().get(key)
								.add(new Message(arrmsg[1], arrmsg[2], false));
						if ((!this.mprivateclientfragment.getCheckonpause() || toprivateClient == null)
								|| (this.mprivateclientfragment
										.getCheckonpause() && !this.mprivateclientfragment
										.getUsername().equals(arrmsg[1]))) {
							showdiaglogChatPrivate(arrmsg[1]);
						} else {
							this.toprivateClient.updatelist();
						}
					}
				}
			}
			if (pivatecheck) {
				if (position != 0) {
					Handler handler = new Handler();
					ftranslater(handler, arrmsg[2], language, arrmsg[1], false,
							pivatecheck);
				} else {
					myapplication.getMapDataPrivateChat().put(arrmsg[1],
							new ArrayList<Message>());
					myapplication.getMapDataPrivateChat().get(arrmsg[1])
							.add(new Message(arrmsg[1], arrmsg[2], false));
					showdiaglogChatPrivate(arrmsg[1]);
				}
			}
			break;
		}
	}

	private void ftranslater(final Handler handler, final String msg,
			final String lg, final String formane, final boolean check,
			final boolean privatecheck) {
		Thread t = new Thread(new Runnable() {
			private String translatedText;

			@Override
			public void run() {
				try {
					MTranslate translater = new MTranslate();
					translatedText = translater.TranslateST(msg, lg);

				} catch (Exception e) {
					e.printStackTrace();
				}
				handler.post(new Runnable() {
					@Override
					public void run() {
						if (check) {
							myapplication.getDataRomChat()
									.add(new Message(formane, translatedText,
											false));
							if (mHomeFragment != null) {
								mHomeFragment.updatelist();
							}
						} else {
							if (privatecheck) {
								myapplication.getMapDataPrivateChat().put(
										formane, new ArrayList<Message>());
								myapplication
										.getMapDataPrivateChat()
										.get(formane)
										.add(new Message(formane,
												translatedText, false));
								showdiaglogChatPrivate(formane);
							} else {
								myapplication
										.getMapDataPrivateChat()
										.get(formane)
										.add(new Message(formane,
												translatedText, false));
								if ((!mprivateclientfragment.getCheckonpause() || toprivateClient == null)
										|| (mprivateclientfragment
												.getCheckonpause() && !mprivateclientfragment
												.getUsername().equals(formane))) {
									showdiaglogChatPrivate(formane);
								} else {
									toprivateClient.updatelist();
								}
							}
						}
					}
				});
			}
		});
		t.start();
	}

	private void showdiaglogChatPrivate(final String username) {
		new AlertDialog.Builder(myapplication.getMainchatct())
				.setTitle(username + " gui cho ban 1 tin nhan")
				.setPositiveButton("OK", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						FragmentTransaction ftransaction = myapplication
								.getMainchatct().getSupportFragmentManager()
								.beginTransaction();
						ToPrivateClientFragment userprivtachat = new ToPrivateClientFragment();
						Bundle b = new Bundle();
						b.putString(USERNAME, username);
						userprivtachat.setArguments(b);
						ftransaction
								.replace(R.id.content_frame, userprivtachat)
								.addToBackStack(null).commit();
					}
				}).setNegativeButton("Cancel", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				}).create().show();
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
	}

	public void setLoginCallbak(ActionLogin actionlogin) {
		this.mlogincallback = actionlogin;
	}

	public void setRegisterCallbak(ActionRegister mregister) {
		this.mregister = mregister;

	}

	public void setUpdateListRomChat(ActionUpdateMessegerRomChat mhome) {
		this.mHomeFragment = mhome;
	}

	public void setToPrivateClient(ActionClientSendPrivate mainchat) {
		this.mMainChat = mainchat;
	}

	public void setUpdateLitsPrivatechat(
			ToPrivateClientFragment toprivateclientfragment) {
		this.toprivateClient = toprivateclientfragment;
		mprivateclientfragment = toprivateclientfragment;
	}
}
