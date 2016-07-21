package edu.anhthu.main;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;

import edu.anhthu.async.ListenerFromServer;
import edu.anhthu.model.Message;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
	String myname;
	Socket msocket;
	ListenerFromServer mlistennerfromserver;
	String contactslist;
	ArrayList<Message> dataRomChat = new ArrayList<Message>();
	Hashtable<String, ArrayList<Message>> mapDataPrivateChat = new Hashtable<String, ArrayList<Message>>();
	MainChat mainchatct;
	public MainChat getMainchatct() {
		return mainchatct;
	}

	public void setMainchatct(MainChat mainchatct) {
		this.mainchatct = mainchatct;
	}

	public String getMyname() {
		return myname;
	}

	public void setMyname(String myname) {
		this.myname = myname;
	}

	public Socket getMsocket() {
		return msocket;
	}

	public void setMsocket(Socket msocket) {
		this.msocket = msocket;
	}

	public ListenerFromServer getMlistennerfromserver() {
		return mlistennerfromserver;
	}

	public void setMlistennerfromserver(ListenerFromServer mlistennerfromserver) {
		this.mlistennerfromserver = mlistennerfromserver;
	}

	public String getContactslist() {
		return contactslist;
	}

	public void setContactslist(String contactslist) {
		this.contactslist = contactslist;
	}

	public ArrayList<Message> getDataRomChat() {
		return dataRomChat;
	}

	public void setDataRomChat(ArrayList<Message> dataRomChat) {
		this.dataRomChat = dataRomChat;
	}

	public Hashtable<String, ArrayList<Message>> getMapDataPrivateChat() {
		return mapDataPrivateChat;
	}

	public void setMapDataPrivateChat(
			Hashtable<String, ArrayList<Message>> mapDataPrivateChat) {
		this.mapDataPrivateChat = mapDataPrivateChat;
	}

}
