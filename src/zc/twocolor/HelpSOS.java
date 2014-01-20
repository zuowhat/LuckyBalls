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
	int num = 2;  //���ƿ�������
	int diaosiScr = 0;       //��˿--�ɾͿ��ƿ���
	int longZe = 0;          //����--�ɾͿ��ƿ���
	int [] rePic ;           //���ͼƬ�����ɵ�Ч��ֻ���ڵ�һ�ε��ʱ��Ч
	int moneyScr = 0;        //��Դ����--�ɾͿ��ƿ���
	int ruhuaScr = 0;        //�����绨--�ɾͿ��ƿ���
	int baolongScr = 0;      //����Ů--�ɾͿ��ƿ���
	int num250 = 0;          //������--�ɾͿ��ƿ���      
	
	
	AlertDialog achDialog;
	View achDialogView;
	//Dialog achDialog;
	Button achBt;
	ImageView achImg;
	TextView achText;
	
	PlaySoundPool playSoundPool;  //���ŵ������
	
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
		    	   //Toast.makeText(HelpSOS.this, "��ӭ���������,����3�γ齱����...", 0).show();
		    	   
		    	   rLoad();
	
		       	
		    	   gridview.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						playSoundPool.playSound(1);
						if(num < 0){
							//AppConnect.getInstance(HelpSOS.this).showPopAd(HelpSOS.this); 
							Toast.makeText(HelpSOS.this, "�������ٽ��г齱...", 1).show();
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
								achView(R.drawable.n250, "��ϲ�����'������'�ĳɾ�");
								editor2.putInt("num250", num250);
							}
							Toast.makeText(HelpSOS.this, "��ϲ�����200Ԫ�ֽ�...", 0).show();
						}
						else if(imageAfter[arg2] == R.drawable.money_100 ){
							youScore = youScore + 100;
							moneyScr++;
							Toast.makeText(HelpSOS.this, "��ϲ�����100Ԫ�ֽ�...", 0).show();
						}
						
						else if(imageAfter[arg2] == R.drawable.money_50){
							youScore = youScore + 50;
							moneyScr++;
							num250++;
							if(num250 == 2){
								achView(R.drawable.n250, "��ϲ�����'������'�ĳɾ�");
								editor2.putInt("num250", num250);
							}
							Toast.makeText(HelpSOS.this, "��ϲ�����50Ԫ�ֽ�...", 0).show();
						}
						else if(imageAfter[arg2] == R.drawable.money_20){
							youScore = youScore + 20;
							moneyScr++;
							Toast.makeText(HelpSOS.this, "��ϲ�����20Ԫ�ֽ�...", 0).show();
						}
						else if(imageAfter[arg2] == R.drawable.money_10){
							youScore = youScore + 10;
							moneyScr++;
							Toast.makeText(HelpSOS.this, "��ϲ�����10Ԫ�ֽ�...", 0).show();
						}
						
						
						else if(imageAfter[arg2] == R.drawable.diaosi_chouman){
							Toast.makeText(HelpSOS.this, "��ϲ�������'��˿ϵ��֮����'�ĳƺ�...", 0).show();
							diaosiScr++;
						}
						else if(imageAfter[arg2] == R.drawable.diaosi_sleep){
							Toast.makeText(HelpSOS.this, "��ϲ�������'��˿ϵ��֮˯��'�ĳƺ�...", 0).show();
							diaosiScr++;
						}
						else if(imageAfter[arg2] == R.drawable.diaosi_yyman){
							Toast.makeText(HelpSOS.this, "��ϲ�������'��˿ϵ��֮YY��'�ĳƺ�...", 0).show();
							diaosiScr++;
						}
						
						else if(imageAfter[arg2] == R.drawable.guai_1){
							Toast.makeText(HelpSOS.this, "����������С���޵�Ϯ������ʧ�ֽ�10Ԫ..", 0).show();
							youScore = youScore - 10;
							baolongScr++;
						}
						else if(imageAfter[arg2] == R.drawable.guai_2){
							Toast.makeText(HelpSOS.this, "��������������޵�Ϯ������ʧ�ֽ�20Ԫ..", 0).show();
							youScore = youScore - 20;
							baolongScr++;
						}
						else if(imageAfter[arg2] == R.drawable.guai_3){
							Toast.makeText(HelpSOS.this, "������������������޵�Ϯ������ʧ�ֽ�50Ԫ..", 0).show();
							youScore = youScore - 50;
							baolongScr++;
						}
						else if(imageAfter[arg2] == R.drawable.longze_1){
							longZe++;
							if(longZe == 2){
								achView(R.drawable.luola, "��ϲ�����'����ȫ��'�ĳɾ�");
								editor2.putInt("longZe", longZe);
							}
							Toast.makeText(HelpSOS.this, "��ϲ�����'������ֽ���°��鶯��Ƭ����'���ϰ벿��...", 1).show();
						}
						else if(imageAfter[arg2] == R.drawable.longze_2){
							longZe++;
							if(longZe == 2){
								achView(R.drawable.luola, "��ϲ�����'����ȫ��'�ĳɾ�");
								editor2.putInt("longZe", longZe);
							}
							Toast.makeText(HelpSOS.this, "��ϲ�����'������ֽ���°��鶯��Ƭ����'���°벿��...", 1).show();
						}
						
						else if(imageAfter[arg2] == R.drawable.me){
							Toast.makeText(HelpSOS.this, "��ϲ�������������һ��...����", 0).show();
						}
						
						else if(imageAfter[arg2] == R.drawable.mengnv){
							Toast.makeText(HelpSOS.this, "���磬�˼Һö�..��������˼ҳԺ���...", 1).show();
						}
						else if(imageAfter[arg2] == R.drawable.ruhua_1){
							ruhuaScr++;
							Toast.makeText(HelpSOS.this, "�˼����������绨����...", 0).show();
						}
						
						else if(imageAfter[arg2] == R.drawable.ruhua_5){
							ruhuaScr++;
							Toast.makeText(HelpSOS.this, "���ǰ��ڱǿ׵��绨����...", 0).show();
						}
						
						else if(imageAfter[arg2] == R.drawable.ruhua_2){
							ruhuaScr++;
							Toast.makeText(HelpSOS.this, "�˼����й����绨����...", 0).show();
						}
						
						else if(imageAfter[arg2] == R.drawable.ruhua_3){
							ruhuaScr++;
							Toast.makeText(HelpSOS.this, "���һ���������ڱǿ�...", 0).show();
						}
						
						else if(imageAfter[arg2] == R.drawable.ruhua_4){
							ruhuaScr++;
							Toast.makeText(HelpSOS.this, ".....ɶҲ��˵��", 0).show();
						}
						
						
						editor2.putInt("youScore" , youScore);
						
						
						if(diaosiScr == 3){
							achView(R.drawable.diaosi, "��ϲ�����'��˿��'�ĳɾ�");
							editor2.putInt("diaosi" , diaosiScr);
						}
						
						
						
						if(moneyScr == 3){
							achView(R.drawable.money, "��ϲ�����'��Դ����'�ĳɾ�");
							editor2.putInt("moneyScr", moneyScr);
						}
						
						if(ruhuaScr == 3){
							achView(R.drawable.ruhua, "��ϲ�����'�����绨'�ĳɾ�");
							editor2.putInt("ruhuaScr", ruhuaScr);
						}
						
						if(baolongScr == 3){
							achView(R.drawable.baolong, "��ϲ�����'����Ů'�ĳɾ�");
							editor2.putInt("baolongScr", baolongScr);
						}
						
						
						
						//���Գɾ͵�������
//						if(arg2 == 0){
//							achView(R.drawable.diaosi, "��ϲ�����'��˿��'�ĳɾ�");
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
//		    	   Toast.makeText(HelpSOS.this, "�����ֽ����,�뼰ʱ���Ѻ�����...", 1).show();
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
		
		diaosiScr = 0;       //��˿--�ɾͿ��ƿ���
		longZe = 0;          //����--�ɾͿ��ƿ���
		moneyScr = 0;        //��Դ����--�ɾͿ��ƿ���
		ruhuaScr = 0;        //�����绨--�ɾͿ��ƿ���
		baolongScr = 0;      //����Ů--�ɾͿ��ƿ���
		num250 = 0;          //������--�ɾͿ��ƿ���      
		
		rePic = new int[20];
		for(int i = 0; i < 20; i++){
			rePic[i] = 0;
		}
		
		if(youScore > 1){
			Toast.makeText(HelpSOS.this, "�����ֽ����,�뼰ʱ���Ѻ�����...", 0).show();
			num = -1;
		}
		else{
			
			Toast.makeText(HelpSOS.this, "��ӭ����齱��,����3�γ齱����...", 0).show();
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
	 
	 //��ʼ������
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