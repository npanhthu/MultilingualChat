package edu.anhthu.main;

import java.io.DataOutputStream;
import java.io.IOException;

import edu.anhthu.apdapter.DrawerListAdapter;
import edu.anhthu.async.ListenerFromServer.ActionClientSendPrivate;
import edu.anhthu.fragment.ContactsListFragment;
import edu.anhthu.fragment.HomeFragment;
import edu.anhthu.fragment.MessengerFragment;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainChat extends FragmentActivity implements
		ActionClientSendPrivate {
	public static final String FILE_NAME_SHARE_PRE = "mydata";
	public static final String KEY_HOME_LANGUAGE = "home";
	public static final String LIST_CONTACTS = "LISTCONTACTS";
	public static String KEY_PRIVATE_LANGUAGE = "clientprivate";
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private FragmentTransaction ftransaction;
	private boolean checklogout=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_chat_layout);
		checklogout=false;
		MyApplication mapplication = (MyApplication) getApplication();
		mapplication.setMainchatct(MainChat.this);
		mTitle = mDrawerTitle = getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		DrawerListAdapter ladapter=new DrawerListAdapter(MainChat.this, R.layout.item_drawerlist);
		mDrawerList.setAdapter(ladapter);
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		if (savedInstanceState == null) {
			selectItem(0);
		}
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	public class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			selectItem(position);
			Log.d("TAG", position + "  dsfsdkjfkldjs");
		}
	}

	public void selectItem(int position) {
		ftransaction = getSupportFragmentManager().beginTransaction();
		switch (position) {
		case 0:
			ftransaction.replace(R.id.content_frame, new HomeFragment())
					.commit();
			mDrawerList.setItemChecked(position, true);
			mDrawerLayout.closeDrawer(mDrawerList);
			break;
			
		case 1:
			ContactsListFragment mcontactslfragment = new ContactsListFragment();
			ftransaction.replace(R.id.content_frame, mcontactslfragment)
					.addToBackStack(null).commit();
			mDrawerList.setItemChecked(position, true);
			mDrawerLayout.closeDrawer(mDrawerList);
			break;

		case 2:
			MessengerFragment mydialog = new MessengerFragment();
			ftransaction.replace(R.id.content_frame, mydialog)
					.addToBackStack(null).commit();
			break;
		case 3:
			break;
			
		case 4:
			mDrawerList.setItemChecked(position, true);
			mDrawerLayout.closeDrawer(mDrawerList);
			logout();
			break;
		}
	}

	private void logout() {
		new AlertDialog.Builder(MainChat.this).setTitle("Logout")
				.setMessage("Bạn Có Muốn Logout")
				.setNegativeButton("Cancel", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				}).setPositiveButton("OK", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						try {
							MyApplication myapplication = (MyApplication) getApplication();
							//gửi đến server thông báo logout
							DataOutputStream dos = new DataOutputStream(myapplication.getMsocket().getOutputStream());
							dos.writeUTF("111,true");
							dos.close();
							dos=null;
							//dong luong lang nghe
							myapplication.getMlistennerfromserver().cancel(true);
							// dong socket
							myapplication.getMsocket().close();
							myapplication.setMsocket(null);
				
						} catch (IOException e) {
							e.printStackTrace();
						}
						checklogout=true;
						MainChat.this.finish();
						//tro activity login
						Intent i=new Intent(getBaseContext(), LoginActivity.class);
						i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(i);
					}
				}).create().show();
	}

	@Override
	public void showPrivateClient(String username, MainChat mn) {
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		try {
			if (!checklogout) {
				MyApplication myapplication = (MyApplication) getApplication();
				//gui msg logout cho sv
				DataOutputStream dos = new DataOutputStream(myapplication.getMsocket().getOutputStream());
				dos.writeUTF("111,true");
				dos.close();
				dos=null;
				// dong luong lang nghe
				myapplication.getMlistennerfromserver().cancel(true);
				// dong socket
				myapplication.getMsocket().close();
				myapplication.setMsocket(null);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
