package com.example.lqm.animation;


import com.example.lqm.animation.loadingview.DotsTextView;
import com.example.lqm.animation.widget.LoadingView;
import com.example.lqm.animation.widget.ShapeLoadingDialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class OpenLoadingActivity extends Activity {

	DotsTextView dotsTextView;
    Button buttonPlay;
    Button buttonHide;
    Button buttonHideAndStop;

    LoadingView loadingview;
    boolean flag = false;
    ShapeLoadingDialog shapeLoadingDialog;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.openloading_layout);
        dotsTextView = (DotsTextView) findViewById(R.id.dots);
        buttonPlay = (Button) findViewById(R.id.buttonPlay);
        buttonHide = (Button) findViewById(R.id.buttonHide);
        buttonHideAndStop = (Button) findViewById(R.id.buttonHideAndStop);


        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dotsTextView.isPlaying()) {
                    dotsTextView.stop();
                } else {
                    dotsTextView.start();
                }
            }
        });

        buttonHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dotsTextView.isHide()) {
                    dotsTextView.show();
                } else {
                    dotsTextView.hide();
                }
            }
        });

        buttonHideAndStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dotsTextView.isHide()) {
                    dotsTextView.showAndPlay();
                } else {
                    dotsTextView.hideAndStop();
                }
            }
        });
        
        
        loadingview = (LoadingView) findViewById(R.id.loadView);
        Button btn1 = (Button)findViewById(R.id.button1);
        Button btn2 = (Button)findViewById(R.id.button2);
        
        btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				flag = !flag;
				loadingview.setVisibility(flag?View.VISIBLE:View.INVISIBLE);
			}
		});
        
        
        shapeLoadingDialog=new ShapeLoadingDialog(this);
        shapeLoadingDialog.setLoadingText("加载中..");
        btn2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 shapeLoadingDialog.show();
			}
		});
    }
}
