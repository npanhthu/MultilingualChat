package edu.anhthu.apdapter;

import java.util.List;

import edu.anhthu.main.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ContactsAdapter extends ArrayAdapter<String>{
	private LayoutInflater inflater;
	private List<String> listcontacts;
	private int resource;
	public ContactsAdapter(Context context, int resource,
			 List<String> objects) {
		super(context, resource, objects);
		inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		listcontacts=objects;
		this.resource=resource;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if(convertView==null){
			convertView=inflater.inflate(resource, parent,false);
			holder=new ViewHolder();
			holder.tvNickname=(TextView) convertView.findViewById(R.id.tvNickname);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.tvNickname.setText(listcontacts.get(position));
		return convertView;
	}
	
	class ViewHolder{
		public TextView tvNickname;
	}
}
