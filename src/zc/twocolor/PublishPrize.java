package zc.twocolor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import zc.twocolor.util.ArrayBalls;
import zc.twocolor.util.PlaySoundPool;
import zc.twocolor.util.PrizeShowBall;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import cn.waps.AppConnect;


/**
 * ������
 * @author zuowhat 2012-08-20
 * @version 1.0
 */
public class PublishPrize extends Activity{
	
	private ListView listView;
	private Button startButton;
	private Button winButton;
	private AlertDialog achDialog;
	private View achDialogView;
	private Button achBt;
	private ImageView achImg;
	private TextView achText;
	private PlaySoundPool playSoundPool;  //���ŵ������
	private ArrayBalls arrayBalls = new ArrayBalls();
	private Animation animation;
	private GridView gridview;
	private boolean [] boolRed =new boolean[33];
	private int rand = 0;
	private int [] randomBalls = new int[7]; //�洢ͼƬ��Դ���������ݶ���Integer����
	private int [] random = new int[6];     //�洢��ɫ��������������int����
	private int blueRandom = 0;   //�洢��ɫ������
	private int ver = 0;
	private int [] counter;  //�洢Ͷע�ı���
	private int con = 0; //���ƿ����Ͷҽ���ť�Ĵ����¼�
	private SharedPreferences sp1;
	//����ʱ���ڱ���
	private Dialog d;
	private TextView dialogText;
	private Timer timer;
	
	//ListView�е�����
	ArrayList<HashMap<String, Object>> listItem = new
			ArrayList<HashMap<String, Object>>();
	
	//������������������
	ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.publish_view);
		System.out.println("Publish --> onCreate");
		listView = (ListView)findViewById(R.id.publishList);
		gridview = (GridView)findViewById(R.id.showPrizeGridView);
		playSoundPool=new PlaySoundPool(this);
		
		//���õ���Ч��
		animation = AnimationUtils.loadAnimation(PublishPrize.this, R.anim.alpha_publish);
		sp1 = PublishPrize.this.getSharedPreferences("view", Context.MODE_PRIVATE);
		publishLoad();
		
		//����6����������1�����������Դ
		for(int i=0; i < 6; i++){
		do{
			rand = (int)(Math.random()*33);
		}while(boolRed[rand]);
		boolRed[rand] = true;
		randomBalls[i] = arrayBalls.arrayRedChangedAll[rand]; 
		random[i] = rand + 1;
		}
		
		rand = (int)(Math.random()*16);
		randomBalls[6] = arrayBalls.arrayBlueOneChanged[rand]; 
		blueRandom = rand + 1;
		
		
		//���ɵ���ʱ����
		//showBallDialog = new AlertDialog.Builder(PublishPrize.this).create();
		d = new Dialog(PublishPrize.this,R.style.dialog);
		d.setContentView(R.layout.publish_view_dialog);
		//showBallDialogView = View.inflate(PublishPrize.this, R.layout.publish_view_dialog, null);
		dialogText = (TextView)d.findViewById(R.id.publishDialogText);
		
		//���յ���ʱ����
		 final Handler handler = new Handler(){

				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					d.show();
					if(msg.what > 0){
						dialogText.setText("" + msg.what);
					}
					else if(msg.what == 0){
						dialogText.setText("����!");
					}
					else{
						dialogText.setText("");
						timer.cancel();
						d.cancel();
						showPrize();
					}
				}
	        };
		
		//���������ť
		startButton = (Button)findViewById(R.id.showPrize);
		startButton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				playSoundPool.playSound(1);
				 if(ver == 0){
			        	Toast.makeText(PublishPrize.this, "�����Ƚ���ѡ��...", 1).show();
			        }
				 else{
				if(con == 0){
				timer = new Timer();
				con++;
				timer.schedule(new TimerTask(){
					int i = 5;
					public void run(){
						Message meg = new Message();
						meg.what = i--;
						handler.sendMessage(meg);
					}
				}, 1000, 1000); //�����ť���ӳ�1�뵹��ʱ������ʱ����Ϊ1��
				
				}
				else{
					Toast.makeText(PublishPrize.this, "�����ѽ���,�����ѡ��������ѡ��...", 1).show();
					
				}
			}
			}
		});
	        
		//����ҽ���ť
		winButton = (Button)findViewById(R.id.winPrize);
		winButton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				playSoundPool.playSound(1);
				if(ver == 0){
		        	Toast.makeText(PublishPrize.this, "�����Ƚ���ѡ��...", 1).show();
		        }
				else{
				if(con == 1){
				compareNumber();
				con++;
				}
				else if(con == 0){
					Toast.makeText(PublishPrize.this, "�����Ƚ��п���...", 1).show();
				}
				else{
					AppConnect.getInstance(PublishPrize.this).showPopAd(PublishPrize.this);
					Toast.makeText(PublishPrize.this, "�ҽ��ѽ���", 1).show();
				}
			}
			}
		});
	}
	
	private void showPrize(){
		for(int i = 0; i < 7; i++){
			HashMap<String, Object> mapOne = new HashMap<String, Object>();
			mapOne.put("ItemBall", randomBalls[i]);
			lstImageItem.add(mapOne);
		}
		SimpleAdapter sim = new SimpleAdapter(PublishPrize.this, lstImageItem, R.layout.publish_view_grid, 
				new String[]{"ItemBall"}, new int[]{R.id.ItemImage});
		LayoutAnimationController lac=new LayoutAnimationController(animation);
		
	       //���ÿؼ���ʾ��˳��Ϊ������
	       lac.setOrder(LayoutAnimationController.ORDER_NORMAL);
	       
	       //���ÿؼ���ʾ���ʱ��2�룻
	       lac.setDelay(2);
	       
		gridview.setAdapter(sim);
		
		//ΪGridView����LayoutAnimationController���ԣ�
	       gridview.setLayoutAnimation(lac);
	}
	
	//�����������ĺ�������Ͷע�ĺ�������бȽϣ�Ȼ��õ���õĽ���
	private void compareNumber(){
		int [] number = new int[6];
		int blueNumber = 0;
		int youScore = 0;
		int systemScore = 0;
		
		for(int i = 0; i < ver; i++){
			int numPu = i + 1;   //��ӦͶע�������еĸ���
			int b = 0;
			
			ArrayList<Integer> rList = (ArrayList<Integer>)listItem.get(i).get("red");
			ArrayList<Integer> bList = (ArrayList<Integer>)listItem.get(i).get("blue");
			for(int t = 0; t < rList.size(); t++){
				int scr = (int)rList.get(t);
				if(scr != 0){
					number[b] = scr;
					b++;
				}
			}
			
			for(int t = 0; t < bList.size(); t++){
				int scr = (int)bList.get(t);
				if(scr != 0){
					blueNumber = scr;
					
				}
			}
			
			//�������С��������
			Arrays.sort(random);
			Arrays.sort(number);
			
			SharedPreferences sp = PublishPrize.this.getSharedPreferences("sco", Context.MODE_PRIVATE);
			youScore = sp.getInt("youScore", 99999);
			systemScore = sp.getInt("systemScore", 99999);
			Editor editor2 = sp.edit();
			
			int sixPri = 0;   //���Ƚ�--�ɾͿ��ƿ���
			int fivePri = sp.getInt("fivePri", 5);  //��Ƚ�--�ɾͿ��ƿ���
			int fourPri = sp.getInt("fourPri", 4);  //�ĵȽ�--�ɾͿ��ƿ���
			int threePri = sp.getInt("threePri", 3);  //���Ƚ�--�ɾͿ��ƿ���
			int twoPri = sp.getInt("twoPri", 2);  //���Ƚ�--�ɾͿ��ƿ���
			int onePri = sp.getInt("onePri", 1);  //һ�Ƚ�--�ɾͿ��ƿ���
			
			 int x = 0;
			 int y = 0;
			 int z = 0;  //�����н��¼��ı���
			 int src = 0;    //��Ͷע�ĺ����뿪���ĺ����У�������ͬ�ĸ���
	          while(x < random.length && y < number.length){
	               if(random[x] == number[y]){
	                         src++;
	                         x++;
	                         y++;
	               }
	               else if(random[x] > number[y]){
	                    y++;}
	               else if(random[x] < number[y]){
	                    x++;}
	          }

			//����õ��Ľ�����Ŀ
			if(blueNumber == blueRandom ){
				if(src == 0 || src == 1 || src == 2){
				youScore = youScore + counter[i]*5;
				systemScore = systemScore - counter[i]*5;
				sixPri = sp.getInt("sixPri", 6);
				  if(sixPri == 6){
					  achView(R.drawable.six, "��ϲ�����'���Ƚ�'�ĳɾ�");
					  sixPri++;
					  editor2.putInt("sixPri" , sixPri);  //�óɾ�ֻ��ʾһ��
				  }
				
				Toast.makeText(PublishPrize.this, "��ϲ������" + numPu +"��Ͷע�������Ƚ�,��ý���" + counter[i]*5 + "Ԫ", 1).show();
				}
				
				else if(src == 3){
					if(fivePri == 5){
						 achView(R.drawable.five, "��ϲ�����'��Ƚ�'�ĳɾ�");
						 fivePri++;
						 editor2.putInt("fivePri" , fivePri);
					}
					youScore = youScore + counter[i]*10;
					systemScore = systemScore - counter[i]*10;
					Toast.makeText(PublishPrize.this, "��ϲ������"+numPu+"��Ͷע������Ƚ�,��ý���" + counter[i]*10 + "Ԫ", 1).show();
				}
				
				else if(src == 4){
					if(fourPri == 4){
						 achView(R.drawable.four, "��ϲ�����'�ĵȽ�'�ĳɾ�");
						 fourPri++;
						 editor2.putInt("fourPri" , fourPri);
					}
					youScore = youScore + counter[i]*200;
					systemScore = systemScore - counter[i]*200;
					Toast.makeText(PublishPrize.this, "��ϲ������"+numPu+"��Ͷע�����ĵȽ�,��ý���" + counter[i]*200 + "Ԫ", 1).show();
				}
				
				else if(src == 5){
					if(threePri == 3){
						 achView(R.drawable.three, "��ϲ�����'���Ƚ�'�ĳɾ�");
						 threePri++;
						  editor2.putInt("threePri" , threePri);
					}
					
					youScore = youScore + counter[i]*3000;
					systemScore = systemScore - counter[i]*3000;
					Toast.makeText(PublishPrize.this, "��ϲ������"+numPu+"��Ͷע�������Ƚ�,��ý���" + counter[i]*3000 + "Ԫ", 1).show();
				}
				
				else if(src == 6){
					if(onePri == 1){
						 achView(R.drawable.one, "��ϲ�����'һ�Ƚ�'�ĳɾ�");
						 onePri++;
						  editor2.putInt("onePri" , onePri);
					}
					youScore = youScore + systemScore;
					systemScore = 0;
					Toast.makeText(PublishPrize.this, "�ҵ���,����Ȼ���˳����󽱡��Ͽ���ϵ���ˣ��������Դ��!!!", 1).show();
				}
			}
			
			else{
				if(src == 4){
					if(fivePri == 5){
						 achView(R.drawable.five, "��ϲ�����'��Ƚ�'�ĳɾ�");
						 fivePri++;
						  editor2.putInt("fivePri" , fivePri);
					}
					youScore = youScore + counter[i]*10;
					systemScore = systemScore - counter[i]*10;
					Toast.makeText(PublishPrize.this, "��ϲ������"+numPu+"��Ͷע������Ƚ�,��ý���" + counter[i]*10 + "Ԫ", 1).show();
				}
				
				else if(src == 5){
					if(fourPri == 4){
						 achView(R.drawable.four, "��ϲ�����'�ĵȽ�'�ĳɾ�");
						 fourPri++;
						  editor2.putInt("fourPri" , fourPri);
					}
					youScore = youScore + counter[i]*200;
					systemScore = systemScore - counter[i]*200;
					Toast.makeText(PublishPrize.this, "��ϲ������"+numPu+"��Ͷע�����ĵȽ�,��ý���" + counter[i]*200 + "Ԫ", 1).show();
				}
				
				else if( src == 6){
					if(twoPri == 2){
						 achView(R.drawable.two, "��ϲ�����'���Ƚ�'�ĳɾ�");
						 twoPri++;
						  editor2.putInt("twoPri" , twoPri);
					}
					BigDecimal yScore = new BigDecimal(youScore + systemScore*0.3).setScale(0, BigDecimal.ROUND_HALF_UP);
					youScore = yScore.intValue();
					BigDecimal sScore = new BigDecimal(systemScore - systemScore*0.3).setScale(0, BigDecimal.ROUND_HALF_UP);
					systemScore = sScore.intValue();
					Toast.makeText(PublishPrize.this, "��ϲ�����˶��Ƚ�,��õ�ǰ�����ܶ��30%", 1).show();
				}
				else{
					Toast.makeText(PublishPrize.this, "���ź�,����"+numPu+"��Ͷעû���н��������Ŭ����...", 0).show();
				}
				
			}			
			
			editor2.putInt("youScore" , youScore);
			editor2.putInt("systemScore", systemScore);
			editor2.commit();
		}
		
		Editor editorOne = sp1.edit();
		editorOne.putInt("ver", 0);   //��ʼ�� --Ͷע����
		editorOne.commit();
		AppConnect.getInstance(this).showPopAd(this); 
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(con != 0){
			switch (keyCode) {
		        case KeyEvent.KEYCODE_BACK:
		        return true;
		    }
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private void achView(int drawable, String achStr){
		 achDialog = new AlertDialog.Builder(PublishPrize.this).create();
			achDialogView = View.inflate(PublishPrize.this, R.layout.help_view_dialog, null);
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
	
	private void publishLoad(){
		//�õ�Ͷע�Ĵ������ɴ����ж����ListView�е����ݵĴ���
				ver = sp1.getInt("ver", 0);
				counter = new int[ver];
				
				for(int a = 0; a < ver; a++){
					ArrayList<Integer> redList = new ArrayList<Integer>();
					ArrayList<Integer> blueList = new ArrayList<Integer>();
					int mu = 0;
					
					for(int i=0;i<33;i++){
						redList.add(i,0);
						
					}
					for(int i=0;i<16;i++){
						blueList.add(i,0);
					}
					
				SharedPreferences spTwo = PublishPrize.this.getSharedPreferences("balls" + a, Context.MODE_PRIVATE);
				for(int i = 0; i < redList.size(); i++){
					redList.set(i, spTwo.getInt(i + "red" , 0));
				}
				for(int b = 0; b < blueList.size(); b++){
					blueList.set(b, spTwo.getInt(b + "blue" , 0));
				}
				mu = spTwo.getInt("mu", 0);
				counter[a] = mu; //�洢ÿ��Ͷע�ı���
				HashMap<String, Object> map = new HashMap<String, Object>();
				
				map.put("blue", blueList);
				map.put("mu", mu);
				map.put("red", redList);
				listItem.add(map);
				
				}
				
				PrizeShowBall ps = new PrizeShowBall(this,listItem);
				listView.setAdapter(ps);
		
	}

}