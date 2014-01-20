package zc.twocolor;

import java.util.ArrayList;
import java.util.HashMap;

import zc.twocolor.R;
import zc.twocolor.util.ArrayBalls;
import zc.twocolor.util.PlaySoundPool;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import cn.waps.AppConnect;

public class HelpSOS extends Activity{
	
	SharedPreferences sp;
	int youScore;
	ArrayBalls ab;
	int [] imageAfter;
	GridView gridview;
	int num = 2;  //控制开奖次数
	int diaosiScr = 0;       //屌丝--成就控制开关
	int longZe = 0;          //泷泽--成就控制开关
	int [] rePic ;           //点击图片所生成的效果只会在第一次点击时生效
	int moneyScr = 0;        //财源滚滚--成就控制开关
	int ruhuaScr = 0;        //三遇如花--成就控制开关
	int baolongScr = 0;      //暴走女--成就控制开关
	int num250 = 0;          //二百五--成就控制开关      
	
	
	AlertDialog achDialog;
	View achDialogView;
	//Dialog achDialog;
	Button achBt;
	ImageView achImg;
	TextView achText;
	
	PlaySoundPool playSoundPool;  //播放点击声音
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.help_view);
	        System.out.println("HelpSOS --> onCreate");
	        
	        ab = new ArrayBalls();
	        imageAfter = new int[20];
	        playSoundPool=new PlaySoundPool(this);
	        
	        sp = HelpSOS.this.getSharedPreferences("sco", Context.MODE_PRIVATE);
		       youScore = sp.getInt("youScore", 100);
		      
		       if(youScore < 2){
		    	   //Toast.makeText(HelpSOS.this, "欢迎进入求救区,您有3次抽奖机会...", 0).show();
		    	   
		    	   rLoad();
	
		       	
		    	   gridview.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						playSoundPool.playSound(1);
						if(num < 0){
							//AppConnect.getInstance(HelpSOS.this).showPopAd(HelpSOS.this); 
							Toast.makeText(HelpSOS.this, "您不能再进行抽奖...", 1).show();
						}
						else{
							if(rePic[arg2] == 0){
							
						ImageView image = (ImageView)arg1.findViewById(R.id.helpItemImage);
						image.setImageResource(imageAfter[arg2]);
						 Editor editor2 = sp.edit();
						
						
						if(imageAfter[arg2] == R.drawable.money_200 ){
							youScore = youScore + 200;
							moneyScr++;
							num250++;
							if(num250 == 2){
								achView(R.drawable.n250, "恭喜您获得'二百五'的成就");
								editor2.putInt("num250", num250);
							}
							Toast.makeText(HelpSOS.this, "恭喜您获得200元现金...", 0).show();
						}
						else if(imageAfter[arg2] == R.drawable.money_100 ){
							youScore = youScore + 100;
							moneyScr++;
							Toast.makeText(HelpSOS.this, "恭喜您获得100元现金...", 0).show();
						}
						
						else if(imageAfter[arg2] == R.drawable.money_50){
							youScore = youScore + 50;
							moneyScr++;
							num250++;
							if(num250 == 2){
								achView(R.drawable.n250, "恭喜您获得'二百五'的成就");
								editor2.putInt("num250", num250);
							}
							Toast.makeText(HelpSOS.this, "恭喜您获得50元现金...", 0).show();
						}
						else if(imageAfter[arg2] == R.drawable.money_20){
							youScore = youScore + 20;
							moneyScr++;
							Toast.makeText(HelpSOS.this, "恭喜您获得20元现金...", 0).show();
						}
						else if(imageAfter[arg2] == R.drawable.money_10){
							youScore = youScore + 10;
							moneyScr++;
							Toast.makeText(HelpSOS.this, "恭喜您获得10元现金...", 0).show();
						}
						
						
						else if(imageAfter[arg2] == R.drawable.diaosi_chouman){
							Toast.makeText(HelpSOS.this, "恭喜您获得了'屌丝系列之丑男'的称号...", 0).show();
							diaosiScr++;
						}
						else if(imageAfter[arg2] == R.drawable.diaosi_sleep){
							Toast.makeText(HelpSOS.this, "恭喜您获得了'屌丝系列之睡神'的称号...", 0).show();
							diaosiScr++;
						}
						else if(imageAfter[arg2] == R.drawable.diaosi_yyman){
							Toast.makeText(HelpSOS.this, "恭喜您获得了'屌丝系列之YY男'的称号...", 0).show();
							diaosiScr++;
						}
						
						else if(imageAfter[arg2] == R.drawable.guai_1){
							Toast.makeText(HelpSOS.this, "由于您遇到小怪兽的袭击，丢失现金10元..", 0).show();
							youScore = youScore - 10;
							baolongScr++;
						}
						else if(imageAfter[arg2] == R.drawable.guai_2){
							Toast.makeText(HelpSOS.this, "由于您遇到大怪兽的袭击，丢失现金20元..", 0).show();
							youScore = youScore - 20;
							baolongScr++;
						}
						else if(imageAfter[arg2] == R.drawable.guai_3){
							Toast.makeText(HelpSOS.this, "由于您遇到超级大怪兽的袭击，丢失现金50元..", 0).show();
							youScore = youScore - 50;
							baolongScr++;
						}
						else if(imageAfter[arg2] == R.drawable.longze_1){
							longZe++;
							if(longZe == 2){
								achView(R.drawable.luola, "恭喜您获得'萝拉全套'的成就");
								editor2.putInt("longZe", longZe);
							}
							Toast.makeText(HelpSOS.this, "恭喜您获得'泷泽妹纸最新爱情动作片种子'的上半部分...", 1).show();
						}
						else if(imageAfter[arg2] == R.drawable.longze_2){
							longZe++;
							if(longZe == 2){
								achView(R.drawable.luola, "恭喜您获得'萝拉全套'的成就");
								editor2.putInt("longZe", longZe);
							}
							Toast.makeText(HelpSOS.this, "恭喜您获得'泷泽妹纸最新爱情动作片种子'的下半部分...", 1).show();
						}
						
						else if(imageAfter[arg2] == R.drawable.me){
							Toast.makeText(HelpSOS.this, "恭喜您获得作者玉照一张...哈哈", 0).show();
						}
						
						else if(imageAfter[arg2] == R.drawable.mengnv){
							Toast.makeText(HelpSOS.this, "大哥哥，人家好饿..你下面给人家吃好吗...", 1).show();
						}
						else if(imageAfter[arg2] == R.drawable.ruhua_1){
							ruhuaScr++;
							Toast.makeText(HelpSOS.this, "人家是正经的如花妹子...", 0).show();
						}
						
						else if(imageAfter[arg2] == R.drawable.ruhua_5){
							ruhuaScr++;
							Toast.makeText(HelpSOS.this, "我是爱挖鼻孔的如花妹子...", 0).show();
						}
						
						else if(imageAfter[arg2] == R.drawable.ruhua_2){
							ruhuaScr++;
							Toast.makeText(HelpSOS.this, "人家是有沟的如花妹子...", 0).show();
						}
						
						else if(imageAfter[arg2] == R.drawable.ruhua_3){
							ruhuaScr++;
							Toast.makeText(HelpSOS.this, "大家一起来和我挖鼻孔...", 0).show();
						}
						
						else if(imageAfter[arg2] == R.drawable.ruhua_4){
							ruhuaScr++;
							Toast.makeText(HelpSOS.this, ".....啥也不说了", 0).show();
						}
						
						
						editor2.putInt("youScore" , youScore);
						
						
						if(diaosiScr == 3){
							achView(R.drawable.diaosi, "恭喜您获得'屌丝男'的成就");
							editor2.putInt("diaosi" , diaosiScr);
						}
						
						
						
						if(moneyScr == 3){
							achView(R.drawable.money, "恭喜您获得'财源滚滚'的成就");
							editor2.putInt("moneyScr", moneyScr);
						}
						
						if(ruhuaScr == 3){
							achView(R.drawable.ruhua, "恭喜您获得'三遇如花'的成就");
							editor2.putInt("ruhuaScr", ruhuaScr);
						}
						
						if(baolongScr == 3){
							achView(R.drawable.baolong, "恭喜您获得'暴走女'的成就");
							editor2.putInt("baolongScr", baolongScr);
						}
						
						
						
						//测试成就弹出窗口
//						if(arg2 == 0){
//							achView(R.drawable.diaosi, "恭喜您获得'屌丝男'的成就");
//							diaosiScr = 3;
//						}

						editor2.commit();
						num--;
						rePic[arg2]++;
						}
						
						}
						
					}
				});
		    	   
		    	   
		       }
//		       else{
//		    	   Toast.makeText(HelpSOS.this, "您的现金过多,请及时消费后再来...", 1).show();
//		       }
     
	 }

	 
	 private void achView(int drawable, String achStr){
		 achDialog = new AlertDialog.Builder(HelpSOS.this).create();
		 //LayoutInflater factory = LayoutInflater.from(this);
		 //final View achDialogView = factory.inflate(R.layout.help_view_dialog, null);
			achDialogView = View.inflate(HelpSOS.this, R.layout.help_view_dialog, null);
		    //achDialog = new Dialog(this);
			achDialog.setView(achDialogView);
			achBt = (Button)achDialogView.findViewById(R.id.helpExit);
			achImg = (ImageView)achDialogView.findViewById(R.id.helpAchImage);
			achText = (TextView)achDialogView.findViewById(R.id.helpAchText);
			achDialog.show();
			
			achBt.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0) {
					achDialog.cancel();
				}
			});
			
			achImg.setBackgroundResource(drawable);
			achText.setText(achStr);
			
	 }
	 
	 
	 
	 
	 

	@Override
	protected void onRestart() {
		System.out.println("HelpSOS --> onRestart");
		super.onRestart();
	}


	@Override
	protected void onDestroy() {
		System.out.println("HelpSOS --> onDestroy");
		super.onDestroy();
	}


	@Override
	protected void onPause() {
		System.out.println("HelpSOS --> onPause");
		super.onPause();
	}


	@Override
	protected void onResume() {
		System.out.println("HelpSOS --> onResume");
		rLoad();
		youScore = sp.getInt("youScore", 100);
		
		diaosiScr = 0;       //屌丝--成就控制开关
		longZe = 0;          //泷泽--成就控制开关
		moneyScr = 0;        //财源滚滚--成就控制开关
		ruhuaScr = 0;        //三遇如花--成就控制开关
		baolongScr = 0;      //暴龙女--成就控制开关
		num250 = 0;          //二百五--成就控制开关      
		
		rePic = new int[20];
		for(int i = 0; i < 20; i++){
			rePic[i] = 0;
		}
		
		if(youScore > 1){
			Toast.makeText(HelpSOS.this, "您的现金过多,请及时消费后再来...", 0).show();
			num = -1;
		}
		else{
			
			Toast.makeText(HelpSOS.this, "欢迎进入抽奖区,您有3次抽奖机会...", 0).show();
			num = 2;
		}
		super.onResume();
	}


	@Override
	protected void onStart() {
		System.out.println("HelpSOS --> onStart");
		super.onStart();
	}


	@Override
	protected void onStop() {
		System.out.println("HelpSOS --> onStop");
		super.onStop();
	}
	 
	 //初始化界面
	 private void rLoad(){
		 gridview = (GridView) findViewById(R.id.helpGridView);
  	   ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
  	   for(int i = 0; i < 20; i++){
  		   HashMap<String, Object> map = new HashMap<String, Object>();
  		   map.put("ItemPic", R.drawable.scr_back);
  		   lstImageItem.add(map);
  	   }
  	   SimpleAdapter saImageItems = new SimpleAdapter(this, lstImageItem, R.layout.help_view_grid, 
  			   new String[]{"ItemPic"}, new int[]{R.id.helpItemImage});
  	   gridview.setAdapter(saImageItems);
  	   
  	 int rand = 0;
  	boolean [] bool =new boolean[20];
 	for(int i = 0; i < 20; i++){
 		do{
 			rand = (int)(Math.random()*20);
 		}while(bool[rand]);
 		bool[rand] = true;
 		imageAfter[i] =  ab.pic[rand];
 		
 	}
 	
 	
 	
 	
 	
  	   
	 }
	 
	 
	 @Override
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
			
			 switch (keyCode) {
		        case KeyEvent.KEYCODE_BACK:
		        return true;
		    }
			
			return super.onKeyDown(keyCode, event);
		}
	 

	 
	
}