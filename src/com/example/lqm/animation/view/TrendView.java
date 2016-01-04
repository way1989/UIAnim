package com.example.lqm.animation.view;

import java.util.ArrayList;
import java.util.List;

import com.example.lqm.animation.R;
import com.example.lqm.animation.weather.CityWeatherInfo;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

@SuppressLint("HandlerLeak")
public class TrendView extends View {

	/* 画背景线的画笔 */
	private Paint mLinePaint;
	// 高温线画笔
	private Paint mLinePaint1;
	// 低温线画笔
	private Paint mLinePaint2;
	// 日期画笔
	private Paint mDateTextPaint;
	// 高温画笔
	private Paint mTempHighPaint;
	// 低温画笔
	private Paint mTempLowPaint;

	private List<String> mTopTem, mLowTem, mTopType, mLowType, dates;
	private int mMaxHightTem = 40, mMinHightTem = 1, mMaxLowTem = 2,
			mMinLowTem = -10;
	/* 将高度分成若干等分 */
	private Bitmap mHotBitmap, mColdBitmap, mLine;
	private Drawable mTrendBg;
	/* 画4条线 */
	private static final int sLineCount = 4;

	private long mLastFrameTime = 0;
	private long mOneFrameTime = 30;
	private int mFrameCount = 40;

	DrawValueAnimator mLineAnimation = new DrawValueAnimator();

	private Handler mPaintHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			if (mLineAnimation.isRunning()) {
				mLineAnimation.cancel();
			}
			Log.d("herecome", "mLineAnimation");
			mLineAnimation.start();
		};
	};

	public TrendView(Context context) {
		this(context, null);
	}

	public TrendView(Context context, AttributeSet attr) {
		super(context, attr);
		init(context);
	}

	private void init(Context context) {

		mLinePaint = new Paint();
		mLinePaint.setAntiAlias(true);
		// 温度趋势的线条画笔
		mLinePaint1 = new Paint();
		mLinePaint1.setColor(getResources().getColor(
				R.color.trend_view_yellow_color));
		mLinePaint1.setAntiAlias(true);
		mLinePaint1.setStrokeWidth(2);
		mLinePaint1.setStyle(Style.STROKE);

		mLinePaint2 = new Paint();
		mLinePaint2.setColor(getResources().getColor(
				R.color.trend_view_blue_color));
		mLinePaint2.setAntiAlias(true);
		mLinePaint2.setStrokeWidth(2);
		mLinePaint2.setStyle(Style.FILL);
		// 温度值画笔
		mTempHighPaint = new Paint();
		mTempHighPaint.setColor(getResources().getColor(
				R.color.trend_view_yellow_color));
		mTempHighPaint.setAntiAlias(true);
		mTempHighPaint.setTextSize(getResources().getDimension(
				R.dimen.weather_trend_temp_text_size));
		mTempHighPaint.setTextAlign(Align.CENTER);

		mTempLowPaint = new Paint();
		mTempLowPaint.setColor(getResources().getColor(
				R.color.trend_view_blue_color));
		mTempLowPaint.setAntiAlias(true);
		mTempLowPaint.setTextSize(getResources().getDimension(
				R.dimen.weather_trend_temp_text_size));
		mTempLowPaint.setTextAlign(Align.CENTER);
		// 时间显示画笔
		mDateTextPaint = new Paint();
		mDateTextPaint.setColor(getResources().getColor(
				R.color.trend_view_text_color_backup));
		mDateTextPaint.setAntiAlias(true);
		mDateTextPaint.setTextSize(getResources().getDimension(
				R.dimen.weather_trend_date_text_size));
		mDateTextPaint.setTextAlign(Align.CENTER);

		mTopTem = new ArrayList<String>(); // 高温
		mLowTem = new ArrayList<String>(); // 低温
		mTopType = new ArrayList<String>(); // 高温上的天气类型
		mLowType = new ArrayList<String>(); // 低温上的天气类型
		dates = new ArrayList<String>();

		mHotBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.trend_view_yellow);
		mColdBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.trend_view_blue);
		mLine = BitmapFactory.decodeResource(getResources(),
				R.drawable.trend_line);
		mTrendBg = getResources().getDrawable(R.drawable.trend_bg);
		mTrendBg.setBounds(0, 0, mTrendBg.getIntrinsicWidth(),
				mTrendBg.getIntrinsicHeight());

		mLineAnimation.setFloatValues(0f, 1f);
		mLineAnimation.setDuration(1000 + sLineCount * 80);
		mOneFrameTime = mLineAnimation.getDuration() / mFrameCount;
		mLineAnimation.addUpdateListener(new AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {

				float r = ((Float) animation.getAnimatedValue()).floatValue();
				if (r == 1 || r * 1000 - mLastFrameTime >= mOneFrameTime) {
					mLastFrameTime = ((int) (r * 1000));
					Log.w("herecome", "addUpdateListener");
					invalidate();
				}
			}
		});
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		/* 画背景 */
		// canvas.setDrawFilter(new PaintFlagsDrawFilter(0,
		// Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));
		mTrendBg.draw(canvas);
		drawLineAnimate(canvas);
	}

	private synchronized void drawLineAnimate(final Canvas canvas) {
		// 获得字体的高度
		Log.i("herecome", "drawLineAnimate");
		final int width = getMeasuredWidth();
		final int drawPaddingLeft, drawPaddingRight;
		/* 设置padding为总宽度的1/10 */
		drawPaddingLeft = drawPaddingRight = width / 10;
		int hightTmpGap = mMaxHightTem - mMinHightTem;
		int lowTmpGap = mMaxLowTem - mMinLowTem;
		final int vectorWidth = (width - drawPaddingLeft - drawPaddingRight)
				/ sLineCount;
		/* 高低温去各占一半显示区 */
		final int height = mTrendBg != null ? mTrendBg.getIntrinsicHeight() / 2
				: getMeasuredHeight() / 2;
		final float vectorHeight = height / hightTmpGap;
		final float vectorLow = height / lowTmpGap;
		/* 画线 */
		for (int i = 0; i <= sLineCount; i++) {
			canvas.drawBitmap(mLine, drawPaddingLeft + vectorWidth * i, 0,
					mLinePaint1);
		}

		Log.d("herecome", mTopTem.toString());
		Log.d("herecome", dates.toString());
		if (mTopTem.size() > 0 && mLowTem.size() > 0) {

			final int[] topX = new int[mTopTem.size()];
			final int[] topY = new int[mTopTem.size()];
			for (int i = 0; i < mTopTem.size(); i++) {
				topX[i] = drawPaddingLeft + vectorWidth * i;
				int temp = Integer.parseInt(mTopTem.get(i));
				int gap = temp - mMinHightTem;
				topY[i] = height - (int) (gap * vectorHeight);
			}

			final int[] lowX = new int[mLowTem.size()];
			final int[] lowY = new int[mLowTem.size()];
			for (int i = 0; i < mLowTem.size(); i++) {
				lowX[i] = drawPaddingLeft + vectorWidth * i;
				int temp = Integer.parseInt(mLowTem.get(i));
				int gap = temp - mMinLowTem;
				lowY[i] = height * 2 - (int) (gap * vectorLow);
			}
			// 高温区画线
			if (mTopTem.size() > 0) {
				for (int i = 0; i <= sLineCount && i < mTopTem.size(); i++) {
					float r = mLineAnimation.getInnerAnimatedValue(i);
					if (i != mTopTem.size() - 1) {
						canvas.drawLine(topX[i], topY[i], topX[i] + r
								* (topX[i + 1] - topX[i]), topY[i] + r
								* (topY[i + 1] - topY[i]), mLinePaint1);
					}
					/*
					 * 修改高度，使绘制时图标不晃动
					 */
					canvas.drawBitmap(mHotBitmap,
							topX[i] - mHotBitmap.getWidth() / 2, (int) topY[i]
									- mHotBitmap.getHeight() / 2 - 0.1f
									* mHotBitmap.getHeight() / 4, mLinePaint1);
					/*
					 * 
					 */
					String tempText = mTopTem.get(i) + "°";
					Rect tempTextBound = new Rect();
					mTempHighPaint.setAlpha((int) (255 * r));
					mTempHighPaint.getTextBounds(tempText, 0,
							tempText.length(), tempTextBound);
					canvas.drawText(tempText, topX[i],
							topY[i] - tempTextBound.height() - 5,
							mTempHighPaint);
					canvas.drawText(mTopType.get(i), topX[i], topY[i] - 2
							* tempTextBound.height() - 2 * 5, mDateTextPaint);
				}
			}
			// 低温区画线
			if (mLowTem.size() > 0) {
				for (int i = 0; i <= sLineCount && i < mLowTem.size(); i++) {
					float r = mLineAnimation.getInnerAnimatedValue(i);
					if (i != mLowTem.size() - 1) {
						canvas.drawLine(lowX[i], lowY[i], lowX[i] + r
								* (lowX[i + 1] - lowX[i]), lowY[i] + r
								* (lowY[i + 1] - lowY[i]), mLinePaint2);
					}
					String tempText = mLowTem.get(i) + "°";
					Rect tempTextBound = new Rect();
					mTempHighPaint.getTextBounds(tempText, 0,
							tempText.length(), tempTextBound);
					/*
					 * 修改高度，使绘制时图标不晃动
					 */
					canvas.drawBitmap(mColdBitmap,
							lowX[i] - mColdBitmap.getWidth() / 2, (int) lowY[i]
									- mColdBitmap.getHeight() / 2 + 0.1f
									* mColdBitmap.getHeight() / 4, mLinePaint2);
					/*
					 * 
					 */
					mTempLowPaint.setAlpha((int) (255 * r));
					canvas.drawText(tempText, lowX[i],
							lowY[i] + mColdBitmap.getHeight() / 2 + 5,
							mTempLowPaint);
					canvas.drawText(mLowType.get(i), lowX[i],
							lowY[i] + mColdBitmap.getHeight() / 2
									+ tempTextBound.height() + 2 * 5,
							mDateTextPaint);
					if (dates != null && dates.size() >= mLowTem.size()) {
						String text = dates.get(i);
						if (text != null) {
							mDateTextPaint.setAlpha((int) (255 * r));
							canvas.drawText(text, lowX[i],
									mTrendBg.getIntrinsicHeight()
											+ mDateTextPaint.getTextSize(),
									mDateTextPaint);
						}
					}
				}
			}
		}
	}

	/**
	 * 根据背景图片，设置view的大小
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (mTrendBg != null) {
			setMeasuredDimension(
					mTrendBg.getIntrinsicWidth(),
					mTrendBg.getIntrinsicHeight() + 2
							* (int) mDateTextPaint.getTextSize());
		} else {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		// mLineAnimation.start();
	}

	public void startLineAnimation() {
		mLineAnimation.start();
	}

	public synchronized void updateDateItem(
			ArrayList<CityWeatherInfo> resultObject) {
		Log.d("herecome", "updateDateItem1");
		if (resultObject != null) {
			Log.d("herecome", "updateDateItem2");
			ArrayList<CityWeatherInfo> cityWeatherInfos = resultObject;
			mTopTem.clear();
			mLowTem.clear();
			/*
			 * 获取最高、最低温天气类型
			 */
			mTopType.clear();
			mLowType.clear();
			dates.clear();
			for (int i = 0; i < cityWeatherInfos.size(); i++) {
				mTopTem.add(cityWeatherInfos.get(i).mHighTemperature);
				mLowTem.add(cityWeatherInfos.get(i).mLowTemperature);
				mTopType.add(cityWeatherInfos.get(i).mWeatherType);
				mLowType.add(cityWeatherInfos.get(i).mWeatherType);
				/*
				 * 
				 */
				/*
				 * 规范化日期
				 */
				if (null == cityWeatherInfos
						|| (null != cityWeatherInfos && null == cityWeatherInfos
								.get(i))
						|| (null != cityWeatherInfos
								&& null != cityWeatherInfos.get(i) && null == cityWeatherInfos
								.get(i).mDate)) {
					return;
				}
				String[] strs = cityWeatherInfos.get(i).mDate.split("-");
				if (strs.length >= 3) {
					String dateStr = strs[1] + "/" + strs[2] + " ";
					if (i == 0) {
						dateStr += getResources().getString(R.string.today);
					} else if (i == 1) {
						dateStr += getResources().getString(R.string.tomorrow);
					} else {
						dateStr += cityWeatherInfos.get(i).mDay;
					}
					dates.add(dateStr);
				}
				/*
				 * 
				 */
				if (i == 0) {
					mMaxHightTem = mMinHightTem = Integer.parseInt(mTopTem
							.get(i));
					mMaxLowTem = mMinLowTem = Integer.parseInt(mLowTem.get(i));
				}

				mMaxHightTem = Math.max(mMaxHightTem,
						Integer.parseInt(mTopTem.get(i)) + 15);
				mMinHightTem = Math.min(mMaxHightTem,
						Integer.parseInt(mTopTem.get(i)) - 5);
				mMaxLowTem = Math.max(mMaxLowTem,
						Integer.parseInt(mLowTem.get(i)) + 5);
				mMinLowTem = Math.min(mMinLowTem,
						Integer.parseInt(mLowTem.get(i)) - 15);
			}
			Log.d("herecome", "" + cityWeatherInfos.toString());
			mPaintHandler.sendEmptyMessage(0);
		}
	}

	class DrawValueAnimator extends ValueAnimator {

		ArrayList<ValueAnimator> mDrawAnimations = new ArrayList<ValueAnimator>();

		public DrawValueAnimator() {
			for (int i = 0; i <= sLineCount; i++) {
				ValueAnimator animation = ValueAnimator.ofFloat(0f, 1f);
				animation.setStartDelay(i * 100);
				animation.setDuration(1000);
				mDrawAnimations.add(animation);
			}
		}

		public float getInnerAnimatedValue(int index) {
			return ((Float) mDrawAnimations.get(index).getAnimatedValue())
					.floatValue();
		}

		@Override
		public void start() {
			for (ValueAnimator animation : mDrawAnimations) {
				animation.start();
			}
			super.start();
		}

		@Override
		public void cancel() {
			for (ValueAnimator animation : mDrawAnimations) {
				if (animation.isRunning()) {
					animation.cancel();
				}
				animation.start();
			}

			super.cancel();
		}
	}

}
