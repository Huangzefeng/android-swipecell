package com.lumbi.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class PullToView extends ScrollView {

	//Should be in calculated from dpi
	private int OVER_SCROLL_THRESHOLD_TOP = -300;
	private int OVER_SCROLL_THRESHOLD_BOTTOM = 300;
	private int overScrollYAccumulator = 0;
	private int overScrollYCancelAccumulator = 0;

	private boolean isDragging = false;

	private boolean didPullTop = false;
	private boolean didPullBottom = false;

	private PullToViewListener listener;

	public PullToView(Context context) {
		super(context);
		init();
	}

	public PullToView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public PullToView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init(){
		setOverScrollMode(OVER_SCROLL_ALWAYS);
	}

	public void setListener(PullToViewListener l){
		this.listener = l;
	}

	@Override
	protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
			int scrollY, int scrollRangeX, int scrollRangeY,
			int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

		boolean cancelOverScroll = false;

		if(listener != null){
			if(scrollY <= 0 || scrollY >= scrollRangeY){
				overScrollYAccumulator += deltaY;
			}
			Log.d("TEST", "ACC: "  + overScrollYAccumulator + " deltaY " +deltaY + " cancel = " + overScrollYCancelAccumulator);

			if(isDragging && isTouchEvent){
				if(overScrollYAccumulator < 0){
					float intensity = overScrollYAccumulator / (float)OVER_SCROLL_THRESHOLD_TOP;
					if(intensity > 1.0f) intensity = 1.0f;
					listener.onTopPulling(intensity);
				}else if(overScrollYAccumulator > 0){
					float intensity = overScrollYAccumulator / (float)OVER_SCROLL_THRESHOLD_BOTTOM;
					if(intensity > 1.0f) intensity = 1.0f;
					listener.onBottomPulling(intensity);
				}
			}

			if(didPullTop || didPullBottom){
				overScrollYCancelAccumulator += deltaY;
				cancelOverScroll = true;
				if(didPullTop && overScrollYCancelAccumulator > -OVER_SCROLL_THRESHOLD_TOP/2.0f ||
						didPullBottom && overScrollYCancelAccumulator < -OVER_SCROLL_THRESHOLD_BOTTOM/2.0f){
					cancelTopAndBottom();
				}
			}
		}

		if(!cancelOverScroll){
			return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
					scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
		}else{
			return true;
		}

	}

	@Override
	protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
		super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);

		if(listener == null) return;

		if(isDragging){
			if(overScrollYAccumulator < OVER_SCROLL_THRESHOLD_TOP){
				if(!didPullTop){
					didPullTop = true;
					listener.onTopPulled();
				}
			}else if(overScrollYAccumulator > OVER_SCROLL_THRESHOLD_BOTTOM){
				if(!didPullBottom){
					didPullBottom = true;
					listener.onBottomPulled();
				}
			}
		}
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);

		switch (event.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
			isDragging = true;
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:
			isDragging = false;
			if(didPullTop){
				didPullTop = false;
				listener.onTopReleased();
			}else if(didPullBottom){
				didPullBottom = false;
				listener.onBottomReleased();
			}else{
				cancelTopAndBottom();
			}
			overScrollYAccumulator = 0;
			break;
		case MotionEvent.ACTION_CANCEL:
			overScrollYAccumulator = 0;
			cancelTopAndBottom();
			break;
		}
		return true;
	}

	private void cancelTopAndBottom(){
		overScrollYAccumulator = 0;
		overScrollYCancelAccumulator = 0;
		if(didPullTop || didPullBottom){
			didPullTop = didPullBottom = false;
			if(listener != null){
				listener.onPullCancelled();
			}
		}
	}

	public interface PullToViewListener{
		void onTopPulling(float intensity);
		void onTopPulled();
		void onTopReleased();
		void onBottomPulling(float intensity);
		void onBottomPulled();
		void onBottomReleased();
		void onPullCancelled();
	}
}
