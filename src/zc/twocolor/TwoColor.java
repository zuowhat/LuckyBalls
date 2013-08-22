package zc.twocolor;

import zc.twocolor.util.PlaySoundPool;
import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/*
 * 主界面
 * 
 * */

public class TwoColor extends ActivityGroup {
    private LocalActivityManager lam = null;
    private LinearLayout ll;
    private Intent mainIntent = null;
    PlaySoundPool playSoundPool;  //播放点击声音
    Button exitButton;
    Button setButton;
    TextView textTitle;
    private String [] ares = new String[]{"选号区" , "抽奖区" , "开奖区" , 
    		"成就区" , "说明区"};
    
    private Class<?> [] clName = new Class<?> []{BallView.class, HelpSOS.class, PublishPrize.class, Achievement.class, CaptionAc.class};
    
	
    public void onCreate(Bundle savedInstanceState) {
    	
    	//SharedPreferences spTwo = TwoColor.this.getSharedPreferences("view", Context.MODE_PRIVATE);
		//int vTwo = spTwo.getInt("viewId", 99);
		//System.out.println("TwoColor --> onCreate" + vTwo);
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);   //控制音量调节
        
        playSoundPool=new PlaySoundPool(this);
        setButton = (Button)findViewById(R.id.set_button);
        textTitle = (TextView)findViewById(R.id.title);
        exitButton = (Button)findViewById(R.id.exit_button);
        ll = (LinearLayout)findViewById(R.id.main_frame);
        lam = getLocalActivityManager();
        
        //让ActivityGroup中的子Activity之间互相跳转
        SharedPreferences sp = TwoColor.this.getSharedPreferences("view", Context.MODE_PRIVATE);
		int v = sp.getInt("viewId", 0);
		if(v == 2){

			//Bundle bundle = this.getIntent().getExtras();
			//if(bundle != null){

			ll.removeAllViews();
	    	mainIntent = new Intent(this,PublishPrize.class);

	    	//mainIntent.putExtras(bundle);
	    	ll.addView(lam.startActivity("开奖区", mainIntent).getDecorView());
	    	textTitle.setText("开奖区");
			
			//}
		}
		else{
        setContainerView(ares[v] , clName[v]);
		}
        
        //退出Activtiy
        exitButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				playSoundPool.playSound(1);
				AlertDialog.Builder exitBuilder = new AlertDialog.Builder(TwoColor.this);
				exitBuilder.setMessage("确定要退出吗?");
				exitBuilder.setCancelable(false); //返回键是否可以关闭对话框
				exitBuilder.setPositiveButton("是", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
//						ActivityManager  am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
//						am.restartPackage(getPackageName());
//						System.exit(0);
						playSoundPool.playSound(1);
						Intent i = new Intent(Intent.ACTION_MAIN);  
						  i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
						  i.addCategory(Intent.CATEGORY_HOME);  
						  startActivity(i); 
						
					}
				});
				
				exitBuilder.setNegativeButton("否", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						playSoundPool.playSound(1);
						dialog.cancel();
					}
				});
				exitBuilder.show();
				exitBuilder.create();
			}
		});
        
        
        
        //系统设置 --> 弹出列表菜单窗口
        setButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				playSoundPool.playSound(1);
				new AlertDialog.Builder(TwoColor.this)
				.setTitle("访问区域")
				.setItems(ares,new DialogInterface.OnClickListener(){  
				      public void onClick(DialogInterface dialog, int which){  
				    	  
				       //点击菜单中元素后，所触发的事件
				       //Toast.makeText(TwoColor.this, "您已经选择了: " + which + ":" + ares[which],Toast.LENGTH_LONG).show();  
				       switch(which){
				       case 0:
				    	   playSoundPool.playSound(1);
				    	   setContainerView(ares[which] , BallView.class);
				    	   break;
				       case 1:
				    	   playSoundPool.playSound(1);
				    	   setContainerView(ares[which] , HelpSOS.class);
				    	   break;   
				       
				       case 2:
				    	   playSoundPool.playSound(1);
				    	   setContainerView(ares[which] , PublishPrize.class);
				    	   
				    	   break;  
				       
				       case 3:
				    	   playSoundPool.playSound(1);
				    	   setContainerView(ares[which] , Achievement.class);
				    	   break;  
				    	   
				       case 4:
				    	   playSoundPool.playSound(1);
				    	   setContainerView(ares[which] , CaptionAc.class);
				    	   break;  
				       }
				    	  
				    	  dialog.dismiss();  
				       
				      }  
				   }).show();	
			}
		});
  
    }
    
    
    //在主界面切换不同的Activity界面
    public void setContainerView(String id , Class<?> activity){
    	ll.removeAllViews();
    	mainIntent = new Intent(this,activity);
    	ll.addView(lam.startActivity(id, mainIntent).getDecorView());
    	//ll.addView(lam.startActivity(id, mainIntent).getCurrentFocus());
    	textTitle.setText(id);
    }


	
	protected void onDestroy() {
		// TODO Auto-generated method stub
		
		SharedPreferences spView = TwoColor.this.getSharedPreferences("view", Context.MODE_PRIVATE);
		Editor editor = spView.edit();
		editor.putInt("viewId" , 0);
		
		editor.commit();
		System.out.println("TwoColor --> onDestroy");
		super.onDestroy();
	}

/*
	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		System.out.println("TwoColor --> onPause");
		super.onPause();
	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		SharedPreferences spT = TwoColor.this.getSharedPreferences("view", Context.MODE_PRIVATE);
		int vT = spT.getInt("viewId", 99);
		System.out.println("TwoColor --> onResume" + vT);
		super.onResume();
	}


	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		System.out.println("TwoColor --> onStop");
		super.onStop();
	}
    
    */
    
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
			
		 switch (keyCode) {
	        case KeyEvent.KEYCODE_BACK:
	        return true;
	    }
		
		return super.onKeyDown(keyCode, event);
	}




    
    
}