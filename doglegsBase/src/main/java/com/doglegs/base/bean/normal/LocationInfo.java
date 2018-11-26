package com.doglegs.base.bean.normal;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;

@Data
public class LocationInfo implements Parcelable {

    private double longitude;
    private double latitude;
    private String province;
    private String provinceCode;
    private String city;
    private String cityCode;
    private String district;
    private String adCode;
    private String address;
    private String road;
    private String street;
    private String streetNum;
    private String description;
    private String aoiName;
    private String country;
    private Long time;

    public LocationInfo() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.longitude);
        dest.writeDouble(this.latitude);
        dest.writeString(this.province);
        dest.writeString(this.city);
        dest.writeString(this.district);
        dest.writeString(this.address);
        dest.writeString(this.road);
        dest.writeString(this.street);
        dest.writeString(this.streetNum);
        dest.writeString(this.description);
        dest.writeString(this.aoiName);
        dest.writeString(this.country);
        dest.writeString(this.cityCode);
        dest.writeString(this.adCode);
        dest.writeValue(this.time);
    }

    protected LocationInfo(Parcel in) {
        this.longitude = in.readDouble();
        this.latitude = in.readDouble();
        this.province = in.readString();
        this.city = in.readString();
        this.district = in.readString();
        this.address = in.readString();
        this.road = in.readString();
        this.street = in.readString();
        this.streetNum = in.readString();
        this.description = in.readString();
        this.aoiName = in.readString();
        this.country = in.readString();
        this.cityCode = in.readString();
        this.adCode = in.readString();
        this.time = (Long) in.readValue(Long.class.getClassLoader());
    }

    public static final Creator<LocationInfo> CREATOR = new Creator<LocationInfo>() {
        @Override
        public LocationInfo createFromParcel(Parcel source) {
            return new LocationInfo(source);
        }

        @Override
        public LocationInfo[] newArray(int size) {
            return new LocationInfo[size];
        }
    };
}
