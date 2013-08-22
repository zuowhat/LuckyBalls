package zc.twocolor.util;

/* 
 * 滑动屏幕的效果
 * 
 * */


import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.SeekBar;

public class ViewGallery extends HorizontalScrollView implements
		OnGestureListener {

	private GestureDetector gd;
	private LinearLayout root;

	private int ScreenWidth, ScreenHeight;
	private int distance;
	private Rect r;

	private int tab = 1;
	private boolean scrollable = true;
	private boolean leftScrollable = true, rightScrollable = true;
	private boolean hasFlinged = false;
	private boolean auto = false;
	private boolean focusChild = false;

	private View touchedChild;

	private static final int MIN_FLING_VELOCITY = 1500;
	private static final int MIN_FLING_DISTANCE = 100;

	public interface OnTabChangedListener {
		public void onTabChanged(int tab, View v);
	}

	private OnTabChangedListener onTabChangedListener;

	public ViewGallery(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public ViewGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Activity ac = (Activity) context;
		DisplayMetrics dm = new DisplayMetrics();
		ac.getWindowManager().getDefaultDisplay().getMetrics(dm);
		ScreenWidth = dm.widthPixels;
		ScreenHeight = dm.heightPixels;
		gd = new GestureDetector(this);
		root = new LinearLayout(context);
		addView(root, new LayoutParams(ScreenWidth, ScreenHeight));
		setHorizontalScrollBarEnabled(false);
		Rect r = new Rect();
		root.getLocalVisibleRect(r);
		root.scrollBy(ScreenWidth - r.left, 0);
	}

	
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		
		int action = ev.getAction();
		if (action == MotionEvent.ACTION_UP) {
			// do something
			scrollable = true;
			if (Math.abs(distance) > ScreenWidth / 2) {
				// move left to the first item
				if (distance > 0) {
					tab--;
					moveToTab(tab, true);
				} else if(distance < 0){
					tab++;
					moveToTab(tab, true);
				}
			} 
			
			//当滑动屏幕达不到一定距离，则回到原来界面
			else {
				moveToTab(tab, false);
			}
		}
		
		
		return true;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		auto = false;
		r = new Rect();
		root.getLocalVisibleRect(r);

		// 判断当前页试图是否有子试图
		View current = root.getChildAt(tab);
		if (current instanceof ViewGroup) {
			ViewGroup group = (ViewGroup) current;
			int childCount = group.getChildCount();
			if (childCount > 0) {
				// 如果有，则让触摸到的子试图响应触摸事件
				for (int j = 0; j < group.getChildCount(); j++) {
					View child = group.getChildAt(j);
					Rect rect = new Rect();
					child.getLocalVisibleRect(rect);
					if (rect.contains((int) ev.getX(), (int) ev.getY())) {
						child.dispatchTouchEvent(ev);
						if (child instanceof SeekBar) {
							focusChild = true;
							touchedChild = child;
							return true;
						}
					}
				}
			}
		}

		int action = ev.getAction();
		if (action == MotionEvent.ACTION_MOVE && focusChild) {
			touchedChild.onTouchEvent(ev);
			return true;
		} 
		else if (action == MotionEvent.ACTION_UP && focusChild) {
			touchedChild.onTouchEvent(ev);
			focusChild = false;
			touchedChild = null;
			return true;
		}

		current.dispatchTouchEvent(ev);

		gd.onTouchEvent(ev);
		if (!hasFlinged) {
			onTouchEvent(ev);
		}
		hasFlinged = false;
		
		
		return true;
	}

	
	public void addItem(View v) {
		LayoutParams params = new LayoutParams(ScreenWidth, ScreenHeight);
		v.setLayoutParams(params);
		root.addView(v);
	}

	@Override
	protected void onAttachedToWindow() {
		// TODO Auto-generated method stub
		super.onAttachedToWindow();
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		int distanceX = (int) (e2.getX() - e1.getX());
		if (Math.abs(distanceX) >= MIN_FLING_DISTANCE
				&& Math.abs(velocityX) >= MIN_FLING_VELOCITY) {
			// startFling
			if (distanceX > 0) {
				// rightFling
				if (rightScrollable) {
					--tab;
					moveToTab(tab, true);
				}
			} else {
				// leftFling
				if (leftScrollable) {
					++tab;
					moveToTab(tab, true);
				}
			}
			hasFlinged = true;
		}
		return false;
	}

	private void moveToTab(int tab, boolean changed) {
		// TODO Auto-generated method stub
		
		if (tab >= 0 && tab < root.getChildCount()) {
			int flipping = tab * ScreenWidth - r.left;
			this.tab = tab;
			new ViewFlippingThread(flipping, changed).start();
		} else {
			this.tab = tab < 0 ? 0 : root.getChildCount() - 1;
		}
		
		
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		distance = (int) (e2.getX() - e1.getX());
		
		
		if (scrollable) {
			int leftBorder = 0;
			int rightBorder = (root.getChildCount() - 1) * ScreenWidth;
			int newBorder = (int) (r.left + distanceX);
			if (distanceX > 0) {
				if (leftScrollable) {
					if (newBorder <= rightBorder) {
						root.scrollBy((int) distanceX, 0);
					} else {
						root.scrollBy(rightBorder - r.left, 0);
						leftScrollable = false;
					}
					rightScrollable = true;
				}
			} else {
				if (rightScrollable) {
					if (newBorder >= leftBorder) {
						root.scrollBy((int) distanceX, 0);
					} else {
						root.scrollBy(leftBorder - r.left, 0);
						rightScrollable = false;
					}
					leftScrollable = true;
				}
			}
		}
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	class ViewFlippingThread extends Thread {

		private int flipping;
		private boolean changed;

		public ViewFlippingThread(int flipping, boolean changed) {
			// TODO Auto-generated constructor stub
			this.flipping = flipping;
			this.changed = changed;
			auto = true;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (flipping >= 0) {
				for (int i = 0; i < flipping; i += 2) {
					if (auto) {
						int offset = i == flipping - 1 ? 1 : 2;
						handler.sendMessage(handler.obtainMessage(0, offset, 0));
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						break;
					}
				}
			} else {

				for (int i = 0; i > flipping; i += -2) {
					if (auto) {
						int offset = i == flipping + 1 ? -1 : -2;
						handler.sendMessage(handler.obtainMessage(0, offset, 0));
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						break;
					}
				}
			}
			handler.sendMessage(handler.obtainMessage(1, 0, 0, changed));
			super.run();
		}
	}

	private Handler handler = new Handler(new Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			
			if (msg.what == 0) {
				root.scrollBy(msg.arg1, 0);
			} else if (msg.what == 1) {
				if (tab == 0) {
					leftScrollable = true;
					rightScrollable = false;
				} else if (tab == root.getChildCount() - 1) {
					rightScrollable = true;
					leftScrollable = false;
				} else {
					scrollable = true;
					rightScrollable = true;
					leftScrollable = true;
				}

				boolean changed = (Boolean) msg.obj;
				if (onTabChangedListener != null && changed) {
					onTabChangedListener.onTabChanged(tab, root.getChildAt(tab));
				}
			}
			distance = 0;
			return false;
		}
	});

	public void setOnTabChangedListener(
			OnTabChangedListener onTabChangedListener) {
		this.onTabChangedListener = onTabChangedListener;
	}

}
