package zc.twocolor.util;

/*
 * 将球添加到对应的GirdView中
 * 
 * */


import java.util.ArrayList;

import zc.twocolor.BallView;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter
{
	BallView activity;
	private Context		mContext;
	ImageView imageView;
	ArrayBalls abTwo;

	private ArrayList<Integer> filter = new ArrayList<Integer>();

	public ImageAdapter(Context c,BallView a ,ArrayList<Integer> ballAL )
	{
		
		activity = a;
		mContext = c;
		filter = ballAL;
	}
	
	public int getCount()
	{
		
		if (filter.size() == 20){
			return 20;
		}
		else if(filter.size() == 16){
			return 16;
		}
//		else if(filter.size() == 33){
//			return 33;
//		}
		else{
			return 13;
		}
		
		
	}

	public Object getItem(int position)
	{
		return position;
	}

	public long getItemId(int position)
	{
		return position;
	}


	public View getView(final int position, View convertView, ViewGroup parent)
	{
		
		SharedPreferences sp = mContext.getSharedPreferences("sco", Context.MODE_PRIVATE);
		float heightDpi = sp.getInt("dpi", 0);
		//System.out.println("heightDpi -->" + heightDpi);
		abTwo = new ArrayBalls();
		int baGird = (int)(60f * (heightDpi / 160f));
		//System.out.println("baGird -->" + baGird);
		
		if (convertView == null)
		{
			imageView = new ImageView(mContext);
			//imageView.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.WRAP_CONTENT,GridView.LayoutParams.WRAP_CONTENT));
			imageView.setLayoutParams(new GridView.LayoutParams(baGird,baGird));
			//imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
		}
		else
		{
			imageView = (ImageView) convertView;
		}
		
		
		if (filter.get(position) == 0 && filter.size()== 20){
			imageView.setBackgroundResource(abTwo.arrayRedOne[position]);
		}
		else if (filter.get(position) != 0 && filter.size()== 20){
			imageView.setBackgroundResource(abTwo.arrayRedOneChanged[position]);
		}
		else if (filter.get(position) == 0 && filter.size() == 13){
			imageView.setBackgroundResource(abTwo.arrayRedTwo[position]);
		}
	
		else if (filter.get(position) != 0 && filter.size() == 13){
			imageView.setBackgroundResource(abTwo.arrayRedTwoChanged[position]);
		}
		else if (filter.get(position) == 0 && filter.size() == 16){
			imageView.setBackgroundResource(abTwo.arrayBlueOne[position]);
		}
	
		else if (filter.get(position) != 0 && filter.size() == 16){
			imageView.setBackgroundResource(abTwo.arrayBlueOneChanged[position]);
		}
//		else if (filter.get(position) == 0 && filter.size() == 33){
//			imageView.setBackgroundResource(abTwo.arrayRedAll[position]);
//		}
//	
//		else if (filter.get(position) != 0 && filter.size() == 33){
//			imageView.setBackgroundResource(abTwo.arrayRedChangedAll[position]);
//		}
		
		return imageView;
	}

}

