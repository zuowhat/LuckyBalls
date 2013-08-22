package zc.twocolor.util;


/*
 * 将选中的球显示到弹出的对话框中
 * 
 * 
 * */

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ShowBallArrayAdapter extends BaseAdapter {
	// BallView activity;
	private ArrayList<Integer> filterresult;
	private Context mContext;
	ImageView imageView;
	private int[] imageI;
	ArrayBalls abDialog = new ArrayBalls();
	

	StringBuffer sb = new StringBuffer();
	String[] temStr;
	Integer j = 0;
	String tempS;

	//public ShowBallArrayAdapter(Context c, BallView at, ArrayList<Integer> al) {
	public ShowBallArrayAdapter(Context c,  ArrayList<Integer> al) {
		
		
		
		mContext = c;
		//activity = at;
		filterresult = al;
		for (int i = 0; i < filterresult.size(); i++) {
			if (filterresult.get(i) != 0) {
				sb.append(filterresult.get(i) + ",");
			}
		}
		;
		String s = sb.toString();
		if (s.length() != 0) {
			tempS = s.substring(0, s.length() - 1);
			temStr = tempS.split(",");
			imageI = new int[temStr.length];
			for (int i = 0; i < temStr.length; i++) {
				j = Integer.parseInt(temStr[i]);
				imageI[i] = j - 1;
			}
			
			for(int k=0;k<temStr.length;k++){
				if (filterresult.size() == 33){
					imageI[k] = abDialog.arrayRedChangedAll[imageI[k]];
				}
				else if (filterresult.size() == 16){
					imageI[k] = abDialog.arrayBlueOneChanged[imageI[k]];   
				}
				
			}
		} else {
			tempS = s.substring(0, s.length());
		}

	}

	public int getCount() {
		if (temStr == null) {
			return 0;
		} else {
			return temStr.length;
		}
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
		
		
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		
		SharedPreferences sp = mContext.getSharedPreferences("view", Context.MODE_PRIVATE);
		int v = sp.getInt("viewId", 0);
		SharedPreferences spDpi = mContext.getSharedPreferences("sco", Context.MODE_PRIVATE);
		float heightDpi = spDpi.getInt("dpi", 0);
		int puListHeight = (int) (40f * (heightDpi / 160f));
		int baGridDialog = (int)(50f * (heightDpi / 160f));
		
		if (convertView == null) {
			imageView = new ImageView(mContext);
			imageView.setClickable(true);
			//显示每个球的尺寸大小
			if(v == 2){
				//开奖区ListView中号码球的大小
				imageView.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.WRAP_CONTENT, puListHeight));
				//imageView.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.WRAP_CONTENT,GridView.LayoutParams.WRAP_CONTENT));
				imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
			}
			else{
			//选号区弹出的Dialog窗口中号码球的大小
			imageView.setLayoutParams(new GridView.LayoutParams(baGridDialog, baGridDialog));
				//imageView.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.WRAP_CONTENT,GridView.LayoutParams.WRAP_CONTENT));
			imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
			}
		} else {
			imageView = (ImageView) convertView;
		}

		//imageView.setBackgroundResource(imageI[position]);
		imageView.setImageResource(imageI[position]);
		
		return imageView;
	}

}
