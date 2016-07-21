package edu.anhthu.apdapter;

import edu.anhthu.main.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DrawerListAdapter extends ArrayAdapter<String[]> {
	private int mresource;
	private String[] mlisttitle;
	private int mlistimage[] = { R.drawable.home, R.drawable.contacts,
			R.drawable.messenger, R.drawable.about, R.drawable.logout };
	private Context mcontext;
	private LayoutInflater inflater;

	public DrawerListAdapter(Context context, int resource) {
		super(context, resource);
		this.mcontext = context;
		this.mresource = resource;
		mlisttitle = context.getResources().getStringArray(
				R.array.planets_array);
		inflater = (LayoutInflater) mcontext
				.getSystemService(mcontext.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if (convertView == null) {

			convertView = inflater.inflate(mresource, parent, false);
			holder = new ViewHolder();
			holder.tvtile = (TextView) convertView.findViewById(R.id.row_title);
			holder.image = (ImageView) convertView.findViewById(R.id.row_icon);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvtile.setText(mlisttitle[position]);
		
		Drawable res = mcontext.getResources().getDrawable(mlistimage[position]);
		holder.image.setImageDrawable(res);
		return convertView;
	}

	class ViewHolder {
		public ImageView image;
		public TextView tvtile;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mlisttitle.length;
	}
	
}
