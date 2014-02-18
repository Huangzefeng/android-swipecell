package com.lumbi.swipecelltest;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lumbi.widgets.SwipeCell;

public class CustomAdapter extends BaseAdapter {

	private Context context;
	private List<String> list = new ArrayList<String>();

	public CustomAdapter(Context context){
		this.context = context;
		for(int i =0; i< 30; i++){
			list.add("PLACEHOLDER " + i);
		}
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		SwipeCell swipeCell = (SwipeCell) convertView;
		if(swipeCell == null){
			swipeCell = new SwipeCell(context);
			swipeCell.setContentView(R.layout.cell, R.layout.left_action, R.layout.right_action);
		}
		final String s = list.get(position);
		swipeCell.setLeftActionListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Log.d("SwipeCellSample", "LEFT CLICKED! " + s);
			}
		});
		swipeCell.setRightActionListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Log.d("SwipeCellSample", "RIGHT CLICKED! " + s);
			}
		});
		TextView textView = (TextView) swipeCell.findViewById(R.id.text);
		textView.setText(s);

		return swipeCell;
	}
}
