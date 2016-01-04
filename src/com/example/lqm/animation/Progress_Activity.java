package com.example.lqm.animation;

import com.example.lqm.animation.view.RoundProgressBar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

@SuppressLint("HandlerLeak")
public class Progress_Activity extends Activity implements OnClickListener {

	ProgressBar progressbar;
	Button downloadBtn;
	boolean stopflag = true;

	ProgressBar pgbar_2;
	Button btn_2;
	boolean stopflag_2 = true;

	RoundProgressBar rbar_1;
	RoundProgressBar rbar_2;
	Button btn_3;
	private int progress = 0;

	private AnimationDrawable mFlyDrawable;
	private ImageView mFlyingIv;
	private Button btn_5;
	boolean show = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.progress_layout);
		initView();
	}

	private void initView() {
		progressbar = (ProgressBar) findViewById(R.id.pbar_download);
		progressbar.setIndeterminate(false);
		progressbar.setMax(100);

		downloadBtn = (Button) findViewById(R.id.btn_pgbar_download);
		downloadBtn.setOnClickListener(this);

		pgbar_2 = (ProgressBar) findViewById(R.id.pbar_download_2);
		pgbar_2.setIndeterminate(false);
		pgbar_2.setMax(100);

		btn_2 = (Button) findViewById(R.id.btn_2);
		btn_2.setOnClickListener(this);

		rbar_1 = (RoundProgressBar) findViewById(R.id.roundProgressBar_1);
		rbar_2 = (RoundProgressBar) findViewById(R.id.roundProgressBar_2);
		btn_3 = (Button) findViewById(R.id.btn_3);
		btn_3.setOnClickListener(this);

		mFlyingIv = (ImageView) findViewById(R.id.fly_icon_iv);
		mFlyDrawable = (AnimationDrawable) mFlyingIv.getDrawable();
		btn_5 = (Button) findViewById(R.id.btn_5);
		btn_5.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_pgbar_download:
			setDownloadTaskProgress();
			break;
		case R.id.btn_2:
			setDownloadTaskProgress_2();
			break;

		case R.id.btn_3:
			changeProgress();
			break;

		case R.id.btn_5:
			show = !show;
			showLoading(show);
			break;
		default:
			break;
		}

	}

	private void changeProgress() {
		progress = 0;
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (progress <= 100) {
					progress += 3;

					System.out.println(progress);

					rbar_1.setProgress(progress);
					rbar_2.setProgress(progress);

					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
		}).start();
	}

	private void setDownloadTaskProgress() {
		if (!stopflag) {
			stopflag = true;
			return;
		} else
			stopflag = false;

		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					for (int i = 0; i <= 100; i++) {
						progressbar.setProgress(i); // 控件请放到ui主线程更新view
						if (!stopflag) {
							Thread.sleep(200);
						} else {
							progressbar.setProgress(0);
							break;
						}

					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();

	}

	private void setDownloadTaskProgress_2() {
		if (!stopflag_2) {
			stopflag_2 = true;
			return;
		} else
			stopflag_2 = false;

		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					for (int i = 0; i <= 100; i++) {
						pgbar_2.setProgress(i); // 控件请放到ui主线程更新view
						if (!stopflag_2) {
							Thread.sleep(200);
							Message msg = mHandler.obtainMessage();
							msg.what = 1;
							msg.obj = i;
							mHandler.sendMessage(msg);
						} else {
							pgbar_2.setProgress(0);
							mHandler.sendEmptyMessage(0);
							break;
						}

					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();

	}

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				btn_2.setText("0%");
				break;
			case 1:
				btn_2.setText(msg.obj + "%");
				break;

			}
		};
	};

	private void showLoading(boolean show) {
		if (mFlyDrawable != null) {
			if (show) {
				mFlyDrawable.start();
			} else {
				mFlyDrawable.stop();
			}
		}
	}

}
