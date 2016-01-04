package com.example.lqm.animation;

import com.example.lqm.animation.flower.FllowerAnimation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;


public class FlowerActivity extends Activity{

	private Button run;
	private RelativeLayout rlt_animation_layout;
	private FllowerAnimation fllowerAnimation;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flower_layout);
		
		run = (Button) findViewById(R.id.but_run);
		rlt_animation_layout = (RelativeLayout) findViewById(R.id.rlt_animation_layout);
		
		
		rlt_animation_layout.setVisibility(View.VISIBLE);
		
		fllowerAnimation = new FllowerAnimation(this);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
		fllowerAnimation.setLayoutParams(params);
		rlt_animation_layout.addView(fllowerAnimation);
		
		
		run.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				fllowerAnimation.startAnimation();
			}
		});
		
	}
}
