package com.doglegs.base.bean.wrap;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

/**
 * 通用WEB页面
 */


@Data
public class CommonWebWrap implements Parcelable {

    private String url;
    private String title;
    private Map<String, String> params = new HashMap<>();
    //默认0显示标题栏,1隐藏标题栏;
    private int isShowToolbar;
    //默认蓝色=0,白色=1;
    private int theme;
    private boolean isRefreshHome;

    public CommonWebWrap() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.title);
        dest.writeInt(this.params.size());
        for (Map.Entry<String, String> entry : this.params.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
        dest.writeInt(this.isShowToolbar);
        dest.writeInt(this.theme);
        dest.writeByte(this.isRefreshHome ? (byte) 1 : (byte) 0);
    }

    protected CommonWebWrap(Parcel in) {
        this.url = in.readString();
        this.title = in.readString();
        int paramsSize = in.readInt();
        this.params = new HashMap<String, String>(paramsSize);
        for (int i = 0; i < paramsSize; i++) {
            String key = in.readString();
            String value = in.readString();
            this.params.put(key, value);
        }
        this.isShowToolbar = in.readInt();
        this.theme = in.readInt();
        this.isRefreshHome = in.readByte() != 0;
    }

    public static final Creator<CommonWebWrap> CREATOR = new Creator<CommonWebWrap>() {
        @Override
        public CommonWebWrap createFromParcel(Parcel source) {
            return new CommonWebWrap(source);
        }

        @Override
        public CommonWebWrap[] newArray(int size) {
            return new CommonWebWrap[size];
        }
    };
}
