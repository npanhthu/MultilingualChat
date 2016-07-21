package edu.anhthu.connectsv;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import edu.anhthu.main.MyApplication;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class ConnectServerSock {
	private Socket msocket;
	private Context mcontext;
	private String host;
	private int port;
	private ProgressDialog progress=null;
	public ConnectServerSock(Context mcontext, String host, int port) {
		super();
		this.mcontext = mcontext;
		this.host = host;
		this.port = port;
			new ProgressDialog(mcontext);
			progress=ProgressDialog.show(mcontext, "Connect Server", "Waiting..");
			connectServer();
	}
	public void connectServer(){
		new ConnectSocketSV().execute();
	}
	class ConnectSocketSV extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			try {
				msocket=new Socket(host, port);
			} catch (UnknownHostException e) {
			} catch (IOException e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
				progress.dismiss();
				progress=null;
				MyApplication mapplication=(MyApplication) mcontext.getApplicationContext();
				mapplication.setMsocket(msocket);
				Toast.makeText(mcontext,"Connect Server Success", Toast.LENGTH_LONG).show();
		}
		
		
	}
	public Socket getMsocket() {
		return msocket;
	}
	public void setMsocket(Socket msocket) {
		this.msocket = msocket;
	}
	public Context getMcontext() {
		return mcontext;
	}
	public void setMcontext(Context mcontext) {
		this.mcontext = mcontext;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
}
