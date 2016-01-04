package com.example.lqm.animation;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class RocketActivity extends Activity implements OnClickListener{

	
	private Handler mHandler = new Handler();
	Button btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rocket_layout);
		btn = (Button) findViewById(R.id.getup);
		btn.setOnClickListener(this);
	}
	
	private void launcherTheRocket() {
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				final View rocket = findViewById(R.id.rocket);
				Animation rocketAnimation = AnimationUtils.loadAnimation(
						getApplicationContext(), R.anim.rocket);
				rocketAnimation
						.setAnimationListener(new VisibilityAnimationListener(
								rocket));
				rocket.startAnimation(rocketAnimation);

				final View cloud = findViewById(R.id.cloud);
				Animation cloudAnimation = AnimationUtils.loadAnimation(
						getApplicationContext(), R.anim.cloud);
				cloudAnimation
						.setAnimationListener(new VisibilityAnimationListener(
								cloud));
				cloud.startAnimation(cloudAnimation);

				final View launcher = findViewById(R.id.launcher);
				Animation launcherAnimation = AnimationUtils.loadAnimation(
						getApplicationContext(), R.anim.launcher);
				launcherAnimation
						.setAnimationListener(new VisibilityAnimationListener(
								launcher));
				launcher.startAnimation(launcherAnimation);

			}
		}, 150);
	
	}
	
	public class VisibilityAnimationListener implements AnimationListener {

		private View mVisibilityView;

		public VisibilityAnimationListener(View view) {
			mVisibilityView = view;
		}

		public void setVisibilityView(View view) {
			mVisibilityView = view;
		}

		@Override
		public void onAnimationStart(Animation animation) {
			if (mVisibilityView != null) {
				mVisibilityView.setVisibility(View.VISIBLE);
				// mVisibilityView.setVisibility(View.GONE);
			}


		}

		@Override
		public void onAnimationEnd(Animation animation) {
			if (mVisibilityView != null) {
				mVisibilityView.setVisibility(View.GONE);
			}
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}
	}

	@Override
	public void onClick(View v) {
          launcherTheRocket();		
	}
}
