package com.example.lqm.animation.weather;

import android.os.Parcel;
import android.os.Parcelable;

public class CityWeatherInfo implements Parcelable {

	public String mCityName;
	/* 城市code */
	public String mCityCode;
	public String mDate;
	/* 周几 */
	public String mDay;
	public String mTemperature;
	// 最低温度
	public String mLowTemperature;
	// 最高温度
	public String mHighTemperature;
	public String mWeatherType;
	/* 风力 */
	public String mWindDirection;

	public CityWeatherInfo() {

	}

	public CityWeatherInfo(String ctyName, String cityCode, String date,
			String temperature, String lowTemperature, String highTemperature,
			String weatherType, String windDirection, String day) {
		mCityName = ctyName;
		mCityCode = cityCode;
		mDate = date;
		mTemperature = temperature;
		mLowTemperature = lowTemperature;
		mHighTemperature = highTemperature;
		mWeatherType = weatherType;
		mWindDirection = windDirection;
		mDay = day;
	}

	public String getCityName() {
		return mCityName;
	}

	public void setCityName(String mCityName) {
		this.mCityName = mCityName;
	}

	public String getmCityCode() {
		return mCityCode;
	}

	public void setmCityCode(String mCityCode) {
		this.mCityCode = mCityCode;
	}

	public String getTemperature() {
		return mTemperature;
	}

	public void setTemperature(String mTemperature) {
		this.mTemperature = mTemperature;
	}

	public String getLowTemperature() {
		return mLowTemperature;
	}

	public void setmLowTemperature(String mLowTemperature) {
		this.mLowTemperature = mLowTemperature;
	}

	public String getmHighTemperature() {
		return mHighTemperature;
	}

	public void setmHighTemperature(String mHighTemperature) {
		this.mHighTemperature = mHighTemperature;
	}

	public String getmWeatherType() {
		return mWeatherType;
	}

	public void setmWeatherType(String mWeatherType) {
		this.mWeatherType = mWeatherType;
	}

	@Override
	public boolean equals(Object o) {
		CityWeatherInfo info = (CityWeatherInfo) o;
		return mCityName.equals(info.mCityName) && mDate.equals(info.mDate)
				&& mWeatherType.equals(info.mWeatherType);
	}

	@Override
	public String toString() {
		return "mCityName = " + mCityName + ", mCityCode = " + mCityCode
				+ ", mDate = " + mDate + ", mTemperature  = " + mTemperature
				+ ", mWeatherType = " + mWeatherType + ", mWindDirection = "
				+ mWindDirection + ", mHighTmp = " + mHighTemperature
				+ ", mLowTmp = " + mLowTemperature;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mCityName);
		dest.writeString(mCityCode);
		dest.writeString(mDate);
		dest.writeString(mTemperature);
		dest.writeString(mLowTemperature);
		dest.writeString(mHighTemperature);
		dest.writeString(mWeatherType);
		dest.writeString(mWindDirection);
	}

	public static final Parcelable.Creator<CityWeatherInfo> CREATOR = new Parcelable.Creator<CityWeatherInfo>() {
		public CityWeatherInfo createFromParcel(Parcel source) {
			return new CityWeatherInfo(source.readString(),
					source.readString(), source.readString(),
					source.readString(), source.readString(),
					source.readString(), source.readString(),
					source.readString(), source.readString());
		}

		public CityWeatherInfo[] newArray(int size) {
			return new CityWeatherInfo[size];
		}
	};

}
