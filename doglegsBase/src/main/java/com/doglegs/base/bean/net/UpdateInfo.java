package com.doglegs.base.bean.net;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;

@Data
public class UpdateInfo implements Parcelable {


    /**
     * appChannel : 0
     * appPlatform : 0
     * appUrl : string
     * forceUpdate : 0
     * versionCode : 0
     * versionContent : string
     * versionTitle : string
     */

    private int appChannel;
    private int appPlatform;
    private String appUrl;
    //是否强制更新 1. 不强制 2.强制
    private int forceUpdate;
    private int versionCode;
    private String versionContent;
    private String versionTitle;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.appChannel);
        dest.writeInt(this.appPlatform);
        dest.writeString(this.appUrl);
        dest.writeInt(this.forceUpdate);
        dest.writeInt(this.versionCode);
        dest.writeString(this.versionContent);
        dest.writeString(this.versionTitle);
    }

    protected UpdateInfo(Parcel in) {
        this.appChannel = in.readInt();
        this.appPlatform = in.readInt();
        this.appUrl = in.readString();
        this.forceUpdate = in.readInt();
        this.versionCode = in.readInt();
        this.versionContent = in.readString();
        this.versionTitle = in.readString();
    }

    public static final Creator<UpdateInfo> CREATOR = new Creator<UpdateInfo>() {
        @Override
        public UpdateInfo createFromParcel(Parcel source) {
            return new UpdateInfo(source);
        }

        @Override
        public UpdateInfo[] newArray(int size) {
            return new UpdateInfo[size];
        }
    };
}
