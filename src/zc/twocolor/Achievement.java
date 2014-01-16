package zc.twocolor;

/*
 * �ɾ���
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
	 PlaySoundPool playSoundPool;  //���ŵ������
	
	

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
						Toast.makeText(Achievement.this, "���泵�������һ����˿�����...", 1).show();
					}
					else{
						Toast.makeText(Achievement.this, "����û�л������ɾ�...", 1).show();
					}
					System.out.println("1 -->" + arg2);
					System.out.println("diaosi -->" + diaosi);
					break;

				case 1:
					if(moneyScr ==3){
						Toast.makeText(Achievement.this, "��Ǯ�������ܵģ���û��Ǯ�������ܵ�...", 1).show();
					}
					else{
						Toast.makeText(Achievement.this, "����û�л������ɾ�...", 1).show();
					}
					break;
					
				case 2:
					if(ruhuaScr ==3){
						Toast.makeText(Achievement.this, "���ӣ��˼ҿ��ǻƻ����Ů�����������Ҿ�Ҫ����...", 1).show();
					}
					else{
						Toast.makeText(Achievement.this, "����û�л������ɾ�...", 1).show();
					}
					break;
					
				case 3:
					if(baolongScr ==3){
						Toast.makeText(Achievement.this, "����Ů��һ��ͷ��������������¥������Ů������ͷ��������Ů��Գ�����Ů������ͷ�����෢չ����ͷ...", 1).show();
					}
					else{
						Toast.makeText(Achievement.this, "����û�л������ɾ�...", 1).show();
					}
					break;	
					
				case 4:
					if(num250 == 2){
						Toast.makeText(Achievement.this, "����һ��ҹ�ڷ�ߵ�ҹ���Һ�����С������xx@&*!$^oo(������ȥ250����)...", 1).show();
					}
					else{
						Toast.makeText(Achievement.this, "����û�л������ɾ�...", 1).show();
					}
					break;
					
				case 5:
					if(longZe == 2){
						Toast.makeText(Achievement.this, "http://115.com/file/dpsxb4sx...�㶮��", 1).show();
					}
					else{
						Toast.makeText(Achievement.this, "����û�л������ɾ�...", 1).show();
					}
					break;
					
				case 6:
					if(sixPri == 7){
						Toast.makeText(Achievement.this, "���裡���裡�ҵ����������Ƚ������������Ƚ�...", 1).show();
					}
					else{
						Toast.makeText(Achievement.this, "����û�л������ɾ�...", 1).show();
					}
					break;	

				case 7:
					if(fivePri == 6){
						Toast.makeText(Achievement.this, "һ��СС����Ƚ��ǲ�����...", 1).show();
					}
					else{
						Toast.makeText(Achievement.this, "����û�л������ɾ�...", 1).show();
					}
					break;	
					
				case 8:
					if(fourPri == 5){
						Toast.makeText(Achievement.this, "�ĵȽ�Ҳ���ǲ�����...", 1).show();
					}
					else{
						Toast.makeText(Achievement.this, "����û�л������ɾ�...", 1).show();
					}
					break;		
					
				case 9:
					if(threePri == 4){
						Toast.makeText(Achievement.this, "���Ƚ��Ǹ�����Ŀ�ʼ�������ҵ�Ҫ����˵�...", 1).show();
					}
					else{
						Toast.makeText(Achievement.this, "����û�л������ɾ�...", 1).show();
					}
					break;	
					
				case 10:
					if(twoPri == 3){
						Toast.makeText(Achievement.this, "�������������������������Ŷ...", 1).show();
					}
					else{
						Toast.makeText(Achievement.this, "����û�л������ɾ�...", 1).show();
					}
					break;	
					
				case 11:
					if(onePri == 2){
						Toast.makeText(Achievement.this, "���Ѿ���ȫ����ƶ�¸��ˣ����ڰ���ɶ�͸�ɶ��...", 1).show();
					}
					else{
						Toast.makeText(Achievement.this, "����û�л������ɾ�...", 1).show();
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
			sText[1] = "��Դ����";
		}
		
		if(diaosi == 3){
			suoPic.suo[0] = R.drawable.diaosi;
			sText[0] = "��˿��";
		}
		
		if(ruhuaScr == 3){
			suoPic.suo[2] = R.drawable.ruhua;
			sText[2] = "�����绨";
		}
		
		if(baolongScr == 3){
			suoPic.suo[3] = R.drawable.baolong;
			sText[3] = "����Ů";
		}
		
		if(num250 == 2){
			suoPic.suo[4] = R.drawable.n250;
			sText[4] = "������";
		}
		
		if(longZe == 2){
			suoPic.suo[5] = R.drawable.luola;
			sText[5] = "����ȫ��";
		}
		
		if(sixPri == 7){
			suoPic.suo[6] = R.drawable.six;
			sText[6] = "���Ƚ�";
		}
		
		if(fivePri == 6){
			suoPic.suo[7] = R.drawable.five;
			sText[7] = "��Ƚ�";
		}
		
		if(fourPri == 5){
			suoPic.suo[8] = R.drawable.four;
			sText[8] = "�ĵȽ�";
		}
		
		if(threePri == 4){
			suoPic.suo[9] = R.drawable.three;
			sText[9] = "���Ƚ�";
		}
		
		if(twoPri == 3){
			suoPic.suo[10] = R.drawable.two;
			sText[10] = "���Ƚ�";
		}
		
		if(onePri == 2){
			suoPic.suo[11] = R.drawable.one;
			sText[11] = "һ�Ƚ�";
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