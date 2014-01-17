package zc.twocolor;
import java.util.ArrayList;

import cn.waps.AppConnect;

import zc.twocolor.R;
import zc.twocolor.util.ArrayBalls;
import zc.twocolor.util.BallGridView;
import zc.twocolor.util.BallScrollView;
import zc.twocolor.util.ImageAdapter;
import zc.twocolor.util.PlaySoundPool;
import zc.twocolor.util.ShowBallArrayAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

/*
 * ѡ����
 * 
 * 
 * */


public class BallView extends Activity implements android.view.GestureDetector.OnGestureListener{
	
	//private ViewGallery vg;
	private ViewFlipper flipper;
    private GestureDetector detector;
    int distance;
    private Activity mActivity = null; 
    
    PlaySoundPool playSoundPool;  //���ŵ������

	
	ArrayBalls abOne;
	public static ArrayList<Integer> redOneBalls= new ArrayList<Integer>();
	public static ArrayList<Integer> redTwoBalls= new ArrayList<Integer>();
	public static ArrayList<Integer> blueOneBalls= new ArrayList<Integer>();
	
	public static ArrayList<Integer> showRedDialog = new ArrayList<Integer>();
	
	//����һ�ͺ����ֵ���ݵ�reRed,�ٽ�reRed��ֵ���ݸ�showRedDialog
	public static ArrayList<Integer> reRed = new ArrayList<Integer>();
	
	View viewRedOne;
	View viewRedTwo;
	View viewBlueOne;
	
	int rCount = 6;  //����ѡ����ֻ��Ϊ6������1������
	int bCount = 1;
	int ver = 0; //��ѡ�õĺ���������ݣ����ݵ��������Ĵ���  (���Ͷע�Ĵ���)
	
	Button buttonOk;
	Button buttonRefrsh;
	
	Button showDialogOk;  //�Ի����е� <Ͷע> ��ť
	Button dialogBack;
	Button dialogGo;
	
	EditText scrNum;
	
	private AlertDialog showBallDialog;
	View showBallDialogView;
	
	GridView redDialog;  //�Ի����еĺ���GirdView
	GridView blueDialog;
	
	GridView gridViewOne;
	GridView gridViewTwo;
	GridView gridViewThree;
	
	TextView youScoreText;
	TextView systemScoreText;
	
	int youScore;
	int systemScore;
	int multiple;
	SharedPreferences sp;
	
	
	int hDpi = 0;  //��ȡ�ֻ���Ļ��dpi
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ball_view);
		abOne = new ArrayBalls();
		
		playSoundPool=new PlaySoundPool(this);
		
		
		
		
		mActivity = this;
		detector = new GestureDetector(this);
        flipper = (ViewFlipper)findViewById(R.id.ViewFlipper1);
        
        viewRedOne = new View(this);
        viewRedTwo = new View(this);
        viewBlueOne = new View(this);
        
        LayoutInflater minflater = LayoutInflater.from(this);
//        LinearLayout  llRedOne = (LinearLayout) inflater.inflate(R.layout.ball_view_redone, null);
//		LinearLayout llRedTwo = (LinearLayout)inflater.inflate(R.layout.ball_view_redtwo, null);
//		LinearLayout llBlueOne = (LinearLayout)inflater.inflate(R.layout.ball_view_blueone, null);
        
        
        viewRedOne = minflater.inflate(R.layout.ball_view_redone, null);
		viewRedTwo = minflater.inflate(R.layout.ball_view_redtwo, null);
		viewBlueOne = minflater.inflate(R.layout.ball_view_blueone, null);

        BallScrollView bs1 = (BallScrollView)viewRedOne.findViewById(R.id.scrollOne);
        bs1.setOnTouchListener(onTouchListener);
        bs1.setGestureDetector(detector);
		
		
        BallScrollView bs2 = (BallScrollView)viewRedTwo.findViewById(R.id.scrollTwo);
        bs2.setOnTouchListener(onTouchListener);
        bs2.setGestureDetector(detector);
        
        
        BallScrollView bs3 = (BallScrollView)viewBlueOne.findViewById(R.id.scrollThree);
        bs3.setOnTouchListener(onTouchListener);
        bs3.setGestureDetector(detector);
		
        
//		viewRedOne = View.inflate(BallView.this, R.layout.ball_view_redone, null);
//		viewRedTwo = View.inflate(BallView.this, R.layout.ball_view_redtwo, null);
//		viewBlueOne = View.inflate(BallView.this, R.layout.ball_view_blueone, null);

		flipper.addView(viewRedOne);
		flipper.addView(viewRedTwo);
		flipper.addView(viewBlueOne);
		//flipper.addTouchables(views);
//		flipper.setOnTouchListener(new OnTouchListener() {
//			
//			@Override
//			public boolean onTouch(View arg0, MotionEvent arg1) {
//				// TODO Auto-generated method stub
//				return detector.onTouchEvent(arg1); 
//			}
//		});
		

		
		
		
		
		//initView();
		//forArrayBalls();
		
		//��ȡ�ֻ���Ļ��dpi
		DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        hDpi = metric.densityDpi;
        //System.out.println("dpi -->" + hDpi);
        
        
		
		youScoreText = (TextView)findViewById(R.id.youScore);
		systemScoreText = (TextView)findViewById(R.id.systemScore);
		
		System.out.println("BallView_onCreate");
		sp = BallView.this.getSharedPreferences("sco", Context.MODE_PRIVATE);
		 youScore = sp.getInt("youScore", 100);
		 systemScore = sp.getInt("systemScore", 10000);
		 
		 Editor editorDpi = sp.edit();
		 editorDpi.putInt("dpi", hDpi);
		 editorDpi.commit();
		
		youScoreText.setText(youScore + "");
		systemScoreText.setText(systemScore + "");
		
		
		//����һ�����������1-20�ź���
		gridViewOne = (BallGridView)viewRedOne.findViewById(R.id.gridviewOne);
		gridViewOne.setAdapter(new ImageAdapter(BallView.this, BallView.this, redOneBalls));
		
		//���ڶ������������20-33�ź���
		gridViewTwo = (BallGridView)viewRedTwo.findViewById(R.id.gridviewTwo);
		gridViewTwo.setAdapter(new ImageAdapter(BallView.this, BallView.this, redTwoBalls));
		
		
		
		
		//�����������������1-16������
		gridViewThree = (BallGridView)viewBlueOne.findViewById(R.id.gridviewThree);
		gridViewThree.setAdapter(new ImageAdapter(BallView.this, BallView.this, blueOneBalls));
		
		//�ڶ��ν������ʱ����Ҫ�ô˷������ؽ��棬��Ȼ�޷���ʾ������
		reLoad();
		
			gridViewOne.setOnItemClickListener(new ItemClickListenerRedOne());
			gridViewTwo.setOnItemClickListener(new ItemClickListenerRedTwo());
			gridViewThree.setOnItemClickListener(new ItemClickListenerBlueOne());
			
			buttonOk = (Button)findViewById(R.id.blueOk);
			buttonRefrsh = (Button)findViewById(R.id.blueRefresh);
		
			
			//����ѡ�ĺ�����ȫ������
			buttonRefrsh.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					playSoundPool.playSound(1);
					
					reLoad();
					
					Toast.makeText(BallView.this, "������ѡ�������", 0).show();
				}
			});
			
			
			//����ѡ��ĺ�������ʾ�ڵ���������
			buttonOk.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					playSoundPool.playSound(1);
					
					
					SharedPreferences spView = BallView.this.getSharedPreferences("view", Context.MODE_PRIVATE);
					Editor editor = spView.edit();
					editor.putInt("viewId" , 0);
					editor.commit();
					
					showBallDialog = new AlertDialog.Builder(BallView.this).create();
					showBallDialogView = View.inflate(BallView.this, R.layout.ball_view_dialog, null);
					redDialog = (GridView)showBallDialogView.findViewById(R.id.show_red_gridview);
					
					blueDialog = (GridView)showBallDialogView.findViewById(R.id.show_blue_gridview);
				
					showRedDialog = reRed;
					redDialog.setAdapter(new ShowBallArrayAdapter(BallView.this,  showRedDialog));
					
					blueDialog.setAdapter(new ShowBallArrayAdapter(BallView.this, blueOneBalls));
			
					showBallDialog.setView(showBallDialogView);
					
					showDialogOk = (Button)showBallDialogView.findViewById(R.id.showDialogOk);
					dialogBack = (Button)showBallDialogView.findViewById(R.id.showDialogBack);
					dialogGo = (Button)showBallDialogView.findViewById(R.id.showDialogGo);
					scrNum = (EditText)showBallDialogView.findViewById(R.id.scrNum);
					
					
					showBallDialog.show();
					
					//Ͷע
					showDialogOk.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							playSoundPool.playSound(1);
							if(scrNum.getText().toString().length() > 0){
								
								if(rCount == 0 & bCount == 0){
						    //�õ�Ͷע�ı���
							multiple =  Integer.parseInt(scrNum.getText().toString()); 
							//youScore = youScore - multiple*2;
							//systemScore = systemScore + multiple*2;
							     if(youScore < multiple*2 ){
							    	 if(youScore < 2){
							    	 
							    	 Toast.makeText(BallView.this, "�����ֽ���,�����齱�����г齱...", 1).show();
							    	 }
							    	 else{
							    		 Toast.makeText(BallView.this, "�����ֽ𲻹���"+multiple+"ע...", 1).show();	 
							    	 }
							     }
							     else{
							    youScore = youScore - multiple*2;
							    systemScore = systemScore + multiple*2; 
								youScoreText.setText(youScore+"");
							systemScoreText.setText(systemScore+"");
							sp = BallView.this.getSharedPreferences("sco", Context.MODE_PRIVATE);
							Editor editor2 = sp.edit();
							editor2.putInt("youScore" , youScore);
							editor2.putInt("systemScore", systemScore);
							editor2.commit();
							
							Toast.makeText(BallView.this, "Ͷע�ɹ�...", 0).show();
							SharedPreferences spBalls = BallView.this.getSharedPreferences("balls" + ver, Context.MODE_PRIVATE);
							Editor editor4 = spBalls.edit();
							for(int i = 0;i < showRedDialog.size(); i++){
								editor4.putInt(i + "red", showRedDialog.get(i));
							}
							for(int i = 0;i < blueOneBalls.size(); i++){
								editor4.putInt(i + "blue", blueOneBalls.get(i));
							}
							editor4.putInt("mu", multiple);
							//System.out.println("BallView_over_mu -->" + multiple);
							editor4.commit();
							ver++;
							
							          }
							}
								
								
								else{
									Toast.makeText(BallView.this, "��ֻ��ѡ��6�������1������...", 0).show();
								}
						}
							else{
								Toast.makeText(BallView.this, "��������Ҫ�����ע��...", 0).show();
							}
							
							
							
							
						}
					});
					
					//����ѡ��
					dialogBack.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							playSoundPool.playSound(1);
							showBallDialog.dismiss();
						}
					});
					
					//ȥ����
					dialogGo.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
//							if(youScore == 0){
//								Toast.makeText(BallView.this, "�����ֽ���,�������������г齱...", 1).show();
//							}
							playSoundPool.playSound(1);
							SharedPreferences spView = BallView.this.getSharedPreferences("view", Context.MODE_PRIVATE);
							Editor editorOne = spView.edit();
							editorOne.putInt("viewId" , 2); //��TwoColor������������
							editorOne.putInt("ver", ver);   //����  Ͷע�Ĵ���
							editorOne.commit();
							
							Intent intent = new Intent();

							intent.setClass(BallView.this , MainActivity.class);
							startActivity(intent);
							
							showBallDialog.cancel();
							
						}
					});
				}
			});
			
			
		
		
	}
	

	
	//�������һ�еĵ���¼�
	 class  ItemClickListenerRedOne implements OnItemClickListener
	{
	public void onItemClick(AdapterView<?> arg0,//The AdapterView where the click happened
	View arg1,//The view within the AdapterView that was clicked
	int arg2,//The position of the view in the adapter
	long arg3//The row id of the item that was clicked
	) {
		if(redOneBalls.get(arg2) == 0){
			//int a = redOneBalls.get(arg2);
			arg1.setBackgroundResource(abOne.arrayRedOneChanged[arg2]);
			redOneBalls.set(arg2,arg2+1);
			reRed.set(arg2, redOneBalls.get(arg2));
			rCount = rCount - 1;
		}
		else{
			arg1.setBackgroundResource(abOne.arrayRedOne[arg2]);
			redOneBalls.set(arg2, 0);
			reRed.set(arg2, redOneBalls.get(arg2));
			rCount = rCount + 1;
		}
	}
		
	}
	
	//���������еĵ���¼�
		 class  ItemClickListenerRedTwo implements OnItemClickListener
		{
		public void onItemClick(AdapterView<?> arg0,//The AdapterView where the click happened
		View arg1,//The view within the AdapterView that was clicked
		int arg2,//The position of the view in the adapter
		long arg3//The row id of the item that was clicked
		) {
			if(redTwoBalls.get(arg2) == 0){
				arg1.setBackgroundResource(abOne.arrayRedTwoChanged[arg2]);
				redTwoBalls.set(arg2,arg2+1);
					
					reRed.set(arg2+20, redTwoBalls.get(arg2) + 20);
					rCount = rCount - 1;
			}
			else{
				arg1.setBackgroundResource(abOne.arrayRedTwo[arg2]);
				redTwoBalls.set(arg2, 0);	
				
					reRed.set(arg2+20, redTwoBalls.get(arg2));
					rCount = rCount + 1;
			}
		}
			
		}
	
		//�������һ�еĵ���¼�
		 class  ItemClickListenerBlueOne implements OnItemClickListener
		{
		public void onItemClick(AdapterView<?> arg0,//The AdapterView where the click happened
		View arg1,//The view within the AdapterView that was clicked
		int arg2,//The position of the view in the adapter
		long arg3//The row id of the item that was clicked
		) {
			if(blueOneBalls.get(arg2) == 0){
				arg1.setBackgroundResource(abOne.arrayBlueOneChanged[arg2]);
				blueOneBalls.set(arg2,arg2+1);
				bCount = bCount - 1;
			}
			else{
				arg1.setBackgroundResource(abOne.arrayBlueOne[arg2]);
				blueOneBalls.set(arg2, 0);	
				bCount = bCount + 1;
			}	
		}
			
		}
	
	
	
	//��ÿ��������г��ȳ�ʼ��
	private void forArrayBalls(){
		if(showRedDialog.size() == 0){
		for(int i=0;i<33;i++){
			showRedDialog.add(i,0);
			
		}
		}
		
		if(reRed.size() == 0){
		for(int i=0;i<33;i++){
			
			reRed.add(i,0);
		}
		}
		
		
		for(int i=0;i<20;i++){
			redOneBalls.add(i,0);
		}
		
		for(int i=0;i<13;i++){
			redTwoBalls.add(i,0);
		}
		for(int i=0;i<16;i++){
			blueOneBalls.add(i,0);
		}
	}
	
	//���¼��ؽ���
	private void reLoad(){
		rCount = 6;  
		bCount = 1;
		redOneBalls.clear();
		redTwoBalls.clear();
		blueOneBalls.clear();
		showRedDialog.clear();
		reRed.clear();
		forArrayBalls();
	
		gridViewOne.setAdapter(new ImageAdapter(BallView.this, BallView.this, redOneBalls));
		gridViewTwo.setAdapter(new ImageAdapter(BallView.this, BallView.this, redTwoBalls));
		gridViewThree.setAdapter(new ImageAdapter(BallView.this, BallView.this, blueOneBalls));
	}
	
	
	protected void onDestroy() {
		//System.out.println("BallView --> onDestroy");
		super.onDestroy();
	}


	@Override
	protected void onPause() {
		//System.out.println("BallView --> onPause");
		super.onPause();
	}

	protected void onStart() {
		//System.out.println("BallView --> onStart");
		super.onStart();
	}
	
	
	@Override
	protected void onResume() {
		//���ڿ����������������ˢ�ֽ��ʱ�򣬼�ʱ�����ֽ���Ŀ
		if(youScore < 2){
		youScore = sp.getInt("youScore", 100);
		youScoreText.setText(youScore + "");
		}
		reLoad();
		//System.out.println("BallView --> onResume");
		super.onResume();
	}
	


	@Override
	protected void onStop() {
		//System.out.println("BallView --> onStop");
		super.onStop();
	}
	
	protected void onRestart() {
		//System.out.println("BallView --> onRestart");
		super.onRestart();
	}
	

	
    public boolean onTouchEvent(MotionEvent ev) {
        
    	
    	
        return detector.onTouchEvent(ev);
    }
    
    @Override
    public boolean onDown(MotionEvent e) {
        // TODO Auto-generated method stub
    	//System.out.println("BallView --> onDown");
    	
        return false;
    }
    
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
            float velocityY) {
    	if (e2.getX() - e1.getX() > 90) {            // �������һ���������ҳ���  
            Animation rInAnim = AnimationUtils.loadAnimation(mActivity, R.anim.push_right_in);  // ���һ���������Ľ���Ч����alpha  0.1 -> 1.0��  
            Animation rOutAnim = AnimationUtils.loadAnimation(mActivity, R.anim.push_right_out); // ���һ����Ҳ໬���Ľ���Ч����alpha 1.0  -> 0.1��  
  
            flipper.setInAnimation(rInAnim);  
            flipper.setOutAnimation(rOutAnim);  
            flipper.showPrevious();  
            return true;  
        } else if (e2.getX() - e1.getX() < -90) {        // �������󻬶����ҽ������  
            Animation lInAnim = AnimationUtils.loadAnimation(mActivity, R.anim.push_left_in);       // ���󻬶�������Ľ���Ч����alpha 0.1  -> 1.0��  
            Animation lOutAnim = AnimationUtils.loadAnimation(mActivity, R.anim.push_left_out);     // ���󻬶��Ҳ໬���Ľ���Ч����alpha 1.0  -> 0.1��  
  
            flipper.setInAnimation(lInAnim);  
            flipper.setOutAnimation(lOutAnim);  
            flipper.showNext();  
            return true;  
        } 
        
        return true;
    }
    
    @Override
    public void onLongPress(MotionEvent e) {
        // TODO Auto-generated method stub
        
    	//System.out.println("BallView --> onLongPress");
    	
    }
    
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
            float distanceY) {
    	//distance = (int) (e1.getX() - e2.getX());
    	
    	
    	//System.out.println("distance --> " + distance);
    	//System.out.println("BallView --> onScroll");
        return false;
    }
    
    @Override
    public void onShowPress(MotionEvent e) {
        // TODO Auto-generated method stub
    	//System.out.println("BallView --> onShowPress");
    }
    
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        // TODO Auto-generated method stub
    	
    	//System.out.println("BallView --> onSingleTapUp");
    	
        return false;
    }
    
    
    
	
private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        
        public boolean onTouch(View v, MotionEvent event) {
            // TODO Auto-generated method stub
            return detector.onTouchEvent(event);
        }
    };
	


	 
}