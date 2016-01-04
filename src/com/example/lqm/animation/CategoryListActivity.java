package com.example.lqm.animation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class CategoryListActivity extends ListActivity {
	private List<String> mData = new ArrayList<String>(Arrays.asList(
			"loading动画与进度条","开源loading动画", "火箭升空", "撒花动画", "气温趋势图"));
	private ListAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, mData);
		getListView().setAdapter(mAdapter);

	}

	// http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2014/1020/1808.html
	// http://blog.csdn.net/dianyueneo/article/details/40683285
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		Intent intent = null;
		switch (position) {
		case 0:
			intent = new Intent(this, Progress_Activity.class);
			break;
		case 1:
            intent = new Intent(this,OpenLoadingActivity.class);
			break;
		case 2:
			intent = new Intent(this, RocketActivity.class);
			break;
		case 3:
			intent = new Intent(this, FlowerActivity.class);
			break;
		case 4:
			intent = new Intent(this, TrendActivity.class);
			break;
		// case 4:
		// intent = new Intent(this, LayoutAnimaActivity.class);
		// break;
		// case 5:
		// intent = new Intent(this, ValueAnimatorActivity.class);
		// break;
		}
		startActivity(intent);
	}
}
