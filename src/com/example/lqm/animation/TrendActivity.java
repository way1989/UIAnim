package com.example.lqm.animation;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.lqm.animation.view.TrendView;
import com.example.lqm.animation.weather.CityWeatherInfo;

public class TrendActivity extends Activity implements OnClickListener{

	TrendView trendview;
	String[] days = {"星期一","星期二","星期三","星期四","星期五"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trend_layout);
		trendview = (TrendView) findViewById(R.id.weather_cycle_trend_view);
		Button btn = (Button) findViewById(R.id.btn_showtrend);
		btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		ArrayList<CityWeatherInfo> infolist = new ArrayList<CityWeatherInfo>();

		for(int i = 0;i<5;i++){
			CityWeatherInfo info = new CityWeatherInfo();
			info.setCityName("深圳");
			info.setmCityCode("001");
			int high = 33+i;
			info.setmHighTemperature(""+high);
			int low = 30 - i;
			info.setmLowTemperature(""+low);
			info.setmWeatherType("");
			info.setTemperature("32");
			int tempnum = i + 17;
			info.mDate = "2015-08-"+tempnum;
			info.mDay = days[i];
			infolist.add(info);
		}	
		
		trendview.updateDateItem(infolist);
		trendview.startLineAnimation();
	}
	
	
	
}
