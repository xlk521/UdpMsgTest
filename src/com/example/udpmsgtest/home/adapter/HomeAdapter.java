package com.example.udpmsgtest.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class HomeAdapter extends BaseAdapter{

	private int count = 0;
	private Context context = null;
	private int pageType = -1;
	public HomeAdapter(Context con, int type){
		this.context = con;
		this.pageType = type;
	}
	@Override
	public int getCount() {
		
		return count;
	}

	@Override
	public Object getItem(int position) {
		
		return position;
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	@Override
	public View getView(int position, View viewContent, ViewGroup viewGroup) {
		
		return null;
	}

}
