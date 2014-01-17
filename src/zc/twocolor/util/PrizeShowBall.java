package zc.twocolor.util;

import java.util.ArrayList;
import java.util.HashMap;

import zc.twocolor.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;


public class PrizeShowBall extends BaseAdapter{
	private Context context;
	private ArrayList<Integer> redList;
	private ArrayList<Integer> blueList;
	private int mu;
	private ArrayList<HashMap<String,Object>> list;
	
	
	//public PrizeShowBall(Context mcon, ArrayList<Integer> rList, ArrayList<Integer> bList, int m){
	public PrizeShowBall(Context mcon, ArrayList<HashMap<String,Object>> list){
		this.context = mcon;
		this.list = list;
//		this.redList = rList;
//		this.blueList = bList;
//		this.mu = m;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater mInflater = LayoutInflater.from(context);
		View view = null;
		view = mInflater.inflate(R.layout.publish_view_list, null);
		GridView redGridView = (GridView)view.findViewById(R.id.publishRedGridView);
		GridView blueGridView = (GridView)view.findViewById(R.id.publishBlueGridView);
		TextView textView = (TextView)view.findViewById(R.id.publishTextView);
		
		
		redList = (ArrayList<Integer>)list.get(position).get("red");
		blueList = (ArrayList<Integer>)list.get(position).get("blue");
		mu = (Integer) list.get(position).get("mu");
		
		redGridView.setAdapter(new ShowBallArrayAdapter(context,redList));
		blueGridView.setAdapter(new ShowBallArrayAdapter(context,blueList));
		textView.setText(mu + "ע");
		
		
		return view;
	}
	
	
}