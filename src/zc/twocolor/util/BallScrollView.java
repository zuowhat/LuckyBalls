package zc.twocolor.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ScrollView;



public class BallScrollView extends ScrollView {
    
    GestureDetector gestureDetector;
    
    public BallScrollView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }
    
    public BallScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }
    
    public BallScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }
    
    public void setGestureDetector(GestureDetector gestureDetector) {
        this.gestureDetector = gestureDetector;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        return gestureDetector.onTouchEvent(event);
    }

    @Override 
    public boolean dispatchTouchEvent(MotionEvent ev){
        gestureDetector.onTouchEvent(ev);
        super.dispatchTouchEvent(ev); 
        return true;
    } 

}