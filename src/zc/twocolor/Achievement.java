package zc.twocolor;

/*
 * 成就区
 * 
 * 
 * */



import java.util.ArrayList;
import java.util.HashMap;

import zc.twocolor.util.ArrayBalls;
import zc.twocolor.util.PlaySoundPool;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import cn.waps.AppConnect;


public class Achievement extends Activity{
	
	GridView achGrid;
	ArrayBalls suoPic;
	ImageView imv;
	String [] sText = new String[]{"???","???","???","???","???","???","???"
			,"???","???","???","???","???"};
	SharedPreferences sp;
	
	int diaosi = 0;
	int moneyScr = 0;
	int ruhuaScr = 0;
	int baolongScr = 0;
	int num250 = 0;
	int longZe = 0;
	int sixPri = 0;
	int fivePri = 0;
	int fourPri = 0;
	int threePri = 0;
	int twoPri = 0;
	int onePri = 0;
	 PlaySoundPool playSoundPool;  //播放点击声音
	
	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.achievement_view);
		playSoundPool=new PlaySoundPool(this);
		reLoad();
		
		
		achGrid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				//System.out.println("arg2 -->" + arg2);
				AppConnect.getInstance(Achievement.this).showPopAd(Achievement.this); 
				playSoundPool.playSound(1);
				switch (arg2) {
				
				case 0:
					if(diaosi ==3){
						Toast.makeText(Achievement.this, "穷玩车，富玩表，一个屌丝玩电脑...", 1).show();
					}
					else{
						Toast.makeText(Achievement.this, "您还没有获得这个成就...", 1).show();
					}
					System.out.println("1 -->" + arg2);
					System.out.println("diaosi -->" + diaosi);
					break;

				case 1:
					if(moneyScr ==3){
						Toast.makeText(Achievement.this, "金钱不是万能的，但没有钱是万万不能的...", 1).show();
					}
					else{
						Toast.makeText(Achievement.this, "您还没有获得这个成就...", 1).show();
					}
					break;
					
				case 2:
					if(ruhuaScr ==3){
						Toast.makeText(Achievement.this, "公子，人家可是黄花大闺女，你再这样我就要喊啦...", 1).show();
					}
					else{
						Toast.makeText(Achievement.this, "您还没有获得这个成就...", 1).show();
					}
					break;
					
				case 3:
					if(baolongScr ==3){
						Toast.makeText(Achievement.this, "暴走女生一回头，宿舍男生齐跳楼。暴走女生二回头，不爱美女爱猿猴。暴走女生三回头，人类发展到尽头...", 1).show();
					}
					else{
						Toast.makeText(Achievement.this, "您还没有获得这个成就...", 1).show();
					}
					break;	
					
				case 4:
					if(num250 == 2){
						Toast.makeText(Achievement.this, "那是一个夜黑风高的夜晚，我和她在小树林里xx@&*!$^oo(以下略去250个字)...", 1).show();
					}
					else{
						Toast.makeText(Achievement.this, "您还没有获得这个成就...", 1).show();
					}
					break;
					
				case 5:
					if(longZe == 2){
						Toast.makeText(Achievement.this, "http://115.com/file/dpsxb4sx...你懂的", 1).show();
					}
					else{
						Toast.makeText(Achievement.this, "您还没有获得这个成就...", 1).show();
					}
					break;
					
				case 6:
					if(sixPri == 7){
						Toast.makeText(Achievement.this, "妈妈！妈妈！我得了两个三等奖，加起来六等奖...", 1).show();
					}
					else{
						Toast.makeText(Achievement.this, "您还没有获得这个成就...", 1).show();
					}
					break;	

				case 7:
					if(fivePri == 6){
						Toast.makeText(Achievement.this, "一个小小的五等奖是不够滴...", 1).show();
					}
					else{
						Toast.makeText(Achievement.this, "您还没有获得这个成就...", 1).show();
					}
					break;	
					
				case 8:
					if(fourPri == 5){
						Toast.makeText(Achievement.this, "四等奖也还是不够滴...", 1).show();
					}
					else{
						Toast.makeText(Achievement.this, "您还没有获得这个成就...", 1).show();
					}
					break;		
					
				case 9:
					if(threePri == 4){
						Toast.makeText(Achievement.this, "三等奖是个不错的开始，可能我的要求高了点...", 1).show();
					}
					else{
						Toast.makeText(Achievement.this, "您还没有获得这个成就...", 1).show();
					}
					break;	
					
				case 10:
					if(twoPri == 3){
						Toast.makeText(Achievement.this, "运气不错嘛，这可是有上万的收入哦...", 1).show();
					}
					else{
						Toast.makeText(Achievement.this, "您还没有获得这个成就...", 1).show();
					}
					break;	
					
				case 11:
					if(onePri == 2){
						Toast.makeText(Achievement.this, "您已经完全的脱贫致富了，现在爱干啥就干啥吧...", 1).show();
					}
					else{
						Toast.makeText(Achievement.this, "您还没有获得这个成就...", 1).show();
					}
					break;		
					

				}
				
			}
			
		});
			
	}


	@Override
	protected void onResume() {
		reLoad();
		
		
		super.onResume();
	}
	
	private void reLoad(){
		suoPic = new ArrayBalls();
		
		sp = Achievement.this.getSharedPreferences("sco", Context.MODE_PRIVATE);
		diaosi = sp.getInt("diaosi", 100);
		moneyScr = sp.getInt("moneyScr", 100);
		ruhuaScr = sp.getInt("ruhuaScr", 100);
		baolongScr = sp.getInt("baolongScr", 100);
		 num250 = sp.getInt("num250", 100);
		 longZe = sp.getInt("longZe", 100);
		 sixPri = sp.getInt("sixPri", 100);
		 fivePri = sp.getInt("fivePri", 100);
		 fourPri = sp.getInt("fourPri", 100);
		 threePri = sp.getInt("threePri", 100);
		 twoPri = sp.getInt("twoPri", 100);
		 onePri = sp.getInt("onePri", 100);
		
		if(moneyScr == 3){
			suoPic.suo[1] = R.drawable.money;
			sText[1] = "财源滚滚";
		}
		
		if(diaosi == 3){
			suoPic.suo[0] = R.drawable.diaosi;
			sText[0] = "屌丝男";
		}
		
		if(ruhuaScr == 3){
			suoPic.suo[2] = R.drawable.ruhua;
			sText[2] = "三遇如花";
		}
		
		if(baolongScr == 3){
			suoPic.suo[3] = R.drawable.baolong;
			sText[3] = "暴走女";
		}
		
		if(num250 == 2){
			suoPic.suo[4] = R.drawable.n250;
			sText[4] = "二百五";
		}
		
		if(longZe == 2){
			suoPic.suo[5] = R.drawable.luola;
			sText[5] = "萝拉全套";
		}
		
		if(sixPri == 7){
			suoPic.suo[6] = R.drawable.six;
			sText[6] = "六等奖";
		}
		
		if(fivePri == 6){
			suoPic.suo[7] = R.drawable.five;
			sText[7] = "五等奖";
		}
		
		if(fourPri == 5){
			suoPic.suo[8] = R.drawable.four;
			sText[8] = "四等奖";
		}
		
		if(threePri == 4){
			suoPic.suo[9] = R.drawable.three;
			sText[9] = "三等奖";
		}
		
		if(twoPri == 3){
			suoPic.suo[10] = R.drawable.two;
			sText[10] = "二等奖";
		}
		
		if(onePri == 2){
			suoPic.suo[11] = R.drawable.one;
			sText[11] = "一等奖";
		}
		
		
		achGrid = (GridView)findViewById(R.id.achGridView);
		
		ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
	  	   for(int i = 0; i < 12; i++){
	  		   HashMap<String, Object> map = new HashMap<String, Object>();
	  		   map.put("ItemPic", suoPic.suo[i]);
	  		 //map.put("ItemPic", suoPic.chPic[i]);
	  		   map.put("ItemText", sText[i]);
	  		   lstImageItem.add(map);
	  	   }
	  	   SimpleAdapter saImageItems = new SimpleAdapter(this, lstImageItem, R.layout.ach_view_grid, 
	  			   new String[]{"ItemPic","ItemText"}, new int[]{R.id.achItemImage, R.id.achItemText});
	  	 achGrid.setAdapter(saImageItems);
	  	 
	  	
	  	 
		
	}
	
	
	
	
}