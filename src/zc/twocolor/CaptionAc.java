package zc.twocolor;

import zc.twocolor.R;
import zc.twocolor.util.BallScrollView;
import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;


public class CaptionAc extends Activity implements android.view.GestureDetector.OnGestureListener{
	private ViewFlipper flipper;
    private GestureDetector detector;
    private Activity mActivity = null; 
    
    View viewOne;
	View viewTwo;
	View viewThree;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.caption_view);
		
		mActivity = this;
		detector = new GestureDetector(this);
        flipper = (ViewFlipper)findViewById(R.id.ViewFlipper2);
        
        viewOne = new View(this);
        viewTwo = new View(this);
        viewThree = new View(this);
        
        LayoutInflater minflater = LayoutInflater.from(this);
        
        viewOne = minflater.inflate(R.layout.caption_view_one, null);
		viewTwo = minflater.inflate(R.layout.caption_view_two, null);
		viewThree = minflater.inflate(R.layout.caption_view_three, null);

        BallScrollView bs1 = (BallScrollView)viewOne.findViewById(R.id.captionScrollOne);
        bs1.setOnTouchListener(onTouchListener);
        bs1.setGestureDetector(detector);
		
		
        BallScrollView bs2 = (BallScrollView)viewTwo.findViewById(R.id.captionScrollTwo);
        bs2.setOnTouchListener(onTouchListener);
        bs2.setGestureDetector(detector);
        
        
        BallScrollView bs3 = (BallScrollView)viewThree.findViewById(R.id.captionScrollThree);
        bs3.setOnTouchListener(onTouchListener);
        bs3.setGestureDetector(detector);
		
        


		flipper.addView(viewOne);
		flipper.addView(viewTwo);
		flipper.addView(viewThree);
		
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
	    	if (e2.getX() - e1.getX() > 90) {            // 从左向右滑动（左进右出）  
	            Animation rInAnim = AnimationUtils.loadAnimation(mActivity, R.anim.push_right_in);  // 向右滑动左侧进入的渐变效果（alpha  0.1 -> 1.0）  
	            Animation rOutAnim = AnimationUtils.loadAnimation(mActivity, R.anim.push_right_out); // 向右滑动右侧滑出的渐变效果（alpha 1.0  -> 0.1）  
	  
	            flipper.setInAnimation(rInAnim);  
	            flipper.setOutAnimation(rOutAnim);  
	            flipper.showPrevious();  
	            return true;  
	        } else if (e2.getX() - e1.getX() < -90) {        // 从右向左滑动（右进左出）  
	            Animation lInAnim = AnimationUtils.loadAnimation(mActivity, R.anim.push_left_in);       // 向左滑动左侧进入的渐变效果（alpha 0.1  -> 1.0）  
	            Animation lOutAnim = AnimationUtils.loadAnimation(mActivity, R.anim.push_left_out);     // 向左滑动右侧滑出的渐变效果（alpha 1.0  -> 0.1）  
	  
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