package com.lumbi.widgets;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;

public class SwipeCell extends HorizontalScrollView{

	protected ViewGroup contentLayout;
	protected View contentView;
	protected View leftActionView;
	protected View rightActionView;
	protected boolean shouldDismissActionOnClick = true;

	public SwipeCell(Context context) {
		super(context);

		this.setHorizontalScrollBarEnabled(false);
		this.setVerticalScrollBarEnabled(false);

		contentLayout = new FrameLayout(context);
		this.addView(contentLayout);
	}

	public void setRightActionListener(final OnClickListener l){
		if(rightActionView != null){
			rightActionView.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					l.onClick(SwipeCell.this);
					if(shouldDismissActionOnClick){
						dismissAction(true);
					}
				}
			});
		}
	}

	public void setLeftActionListener(final OnClickListener l){
		if(leftActionView != null){
			leftActionView.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					l.onClick(SwipeCell.this);
					if(shouldDismissActionOnClick){
						dismissAction(true);
					}
				}
			});
		}
	}

	public void setAutoDismiss(boolean autoDismiss){
		shouldDismissActionOnClick = autoDismiss;
	}

	public void dismissAction(boolean animated){
		int leftActionWidth = leftActionView != null ? leftActionView.getWidth() : 0; 
		if(animated){
			smoothScrollTo(leftActionWidth, 0);
		}else{
			scrollTo(leftActionWidth, 0);
		}
	}

	public void setContentView(int contentId, int leftActionId, int rightActionId){
		contentView = View.inflate(getContext(), contentId, null);
		ViewGroup.LayoutParams contentParams = new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT
				);
		contentView.setLayoutParams(contentParams);
		contentLayout.addView(contentView);

		if(leftActionId > 0){
			leftActionView = View.inflate(getContext(), leftActionId, null);
			ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.MATCH_PARENT
					);
			leftActionView.setLayoutParams(params);
			contentLayout.addView(leftActionView);
		}		

		if(rightActionId > 0){
			rightActionView = View.inflate(getContext(), rightActionId, null);
			ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.MATCH_PARENT
					);
			rightActionView.setLayoutParams(params);
			contentLayout.addView(rightActionView);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		super.onTouchEvent(ev);
		switch(ev.getAction()){
		case MotionEvent.ACTION_UP:
			int lw = leftActionView != null ? leftActionView.getWidth() : 0;
			int rw = rightActionView != null ? rightActionView.getWidth() : 0;
			if(getScrollX() <= 0){ //Full left scroll
			}else if(getScrollX() >= lw + rw){ //Full right scroll				
			}else{
				smoothScrollTo(lw, 0);
			}
			break;
		}
		return true;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);

		int lw = leftActionView != null ? leftActionView.getWidth() : 0;
		int rw = rightActionView != null ? rightActionView.getWidth() : 0;
		int w = getWidth();
		int h = getHeight();

		contentLayout.layout(0, 0, w+lw+rw, h);
		contentView.layout(lw, 0, w+lw, h);
		if(leftActionView != null) leftActionView.layout(0, 0, lw, h);
		if(rightActionView != null) rightActionView.layout(w+lw, 0, w+lw+rw, h);

		scrollTo(lw,0);
	}
}