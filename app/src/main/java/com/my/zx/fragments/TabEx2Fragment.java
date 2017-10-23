package com.my.zx.fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.my.zx.R;


@SuppressLint("NewApi")
public class TabEx2Fragment extends Fragment  {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.plan_2,container, false);
		
		return v;
	}
	
}
