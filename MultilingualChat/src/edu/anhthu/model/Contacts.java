package edu.anhthu.model;

import java.util.ArrayList;
import java.util.List;

public class Contacts {
	List<String> lcontacts;
	String mlist;
	public Contacts(String list){
		this.mlist=list;
		this.lcontacts=new ArrayList<String>();
		ParseStringArrToList();
	}
	public void ParseStringArrToList(){
		String[] arrlist= mlist.split(",");
		for(int i=1;i<arrlist.length;i++){
			lcontacts.add(arrlist[i]);
		}
	}
	public List<String> getLcontacts() {
		return lcontacts;
	}	
}
