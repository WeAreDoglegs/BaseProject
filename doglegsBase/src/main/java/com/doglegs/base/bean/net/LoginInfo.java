package com.doglegs.base.bean.net;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;

/**
 * desc : 登录响应
 */

@Data
public class LoginInfo implements Parcelable {


    /**
     * token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJtZXJjaGFudFN0YWZmSWQiOjExOCwicm9sZSI6Im93bmVyIiwibWVyY2hhbnRJZCI6NzYsImV4cCI6MTUzODg5NDg3NCwiaXNvbGF0aW9uIjowLCJpYXQiOjE1MzYzMDI4NzR9.Rr-rNCoNeKUuQf3RHOaeo6JNSXTij0aH4vyg2JJ9BpQ
     * id : 118
     * roleId : 45
     * roleName : 管理员
     * merchantId : 76
     * merchantName : 文文4S店
     * businessStatus : 1
     * staffTrueName : null
     * codeName : null
     * loginName : 13691465207
     * isLoginPlatform : 1
     * imToken : 37q4uDV20DcVK1sFU9+bHY3Xu4lRrn3oJAnD29GoRS60bMihA47jNCUuIg0xHANmGW6/rcygklgU5VUG1dWjHTKklddP+XEM6YewFm/Be5Y=
     * isolation : 0
     * merchantStatus : 2
     * config : {"id":4,"isShock":1,"isHint":1,"merchantStaffId":118}
     */

    private String token;
    private long id;
    private long roleId;
    private String roleName;
    private long merchantId;
    private String merchantName;
    private int businessStatus;
    private String staffTrueName;
    private String codeName;
    private String loginName;
    private int isLoginPlatform;
    private String imToken;
    private int isolation;
    private int merchantStatus;
    private boolean serviceConfig;
    private ConfigBean config;

    private String entryTime;        //入职时间

    private String merchantStaffImage;      //头像

    public LoginInfo() {
    }

    @Data
    public static class ConfigBean implements Parcelable {
        /**
         * id : 4
         * isShock : 1
         * isHint : 1
         * merchantStaffId : 118
         */

        private long id;
        private int isShock;
        private int isHint;
        private long merchantStaffId;


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(this.id);
            dest.writeInt(this.isShock);
            dest.writeInt(this.isHint);
            dest.writeLong(this.merchantStaffId);
        }

        public ConfigBean() {
        }

        protected ConfigBean(Parcel in) {
            this.id = in.readLong();
            this.isShock = in.readInt();
            this.isHint = in.readInt();
            this.merchantStaffId = in.readLong();
        }

        public static final Creator<ConfigBean> CREATOR = new Creator<ConfigBean>() {
            @Override
            public ConfigBean createFromParcel(Parcel source) {
                return new ConfigBean(source);
            }

            @Override
            public ConfigBean[] newArray(int size) {
                return new ConfigBean[size];
            }
        };
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.token);
        dest.writeLong(this.id);
        dest.writeLong(this.roleId);
        dest.writeString(this.roleName);
        dest.writeLong(this.merchantId);
        dest.writeString(this.merchantName);
        dest.writeInt(this.businessStatus);
        dest.writeString(this.staffTrueName);
        dest.writeString(this.codeName);
        dest.writeString(this.loginName);
        dest.writeInt(this.isLoginPlatform);
        dest.writeString(this.imToken);
        dest.writeInt(this.isolation);
        dest.writeInt(this.merchantStatus);
        dest.writeByte(this.serviceConfig ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.config, flags);
        dest.writeString(this.entryTime);
        dest.writeString(this.merchantStaffImage);
    }

    protected LoginInfo(Parcel in) {
        this.token = in.readString();
        this.id = in.readLong();
        this.roleId = in.readLong();
        this.roleName = in.readString();
        this.merchantId = in.readLong();
        this.merchantName = in.readString();
        this.businessStatus = in.readInt();
        this.staffTrueName = in.readString();
        this.codeName = in.readString();
        this.loginName = in.readString();
        this.isLoginPlatform = in.readInt();
        this.imToken = in.readString();
        this.isolation = in.readInt();
        this.merchantStatus = in.readInt();
        this.serviceConfig = in.readByte() != 0;
        this.config = in.readParcelable(ConfigBean.class.getClassLoader());
        this.entryTime = in.readString();
        this.merchantStaffImage = in.readString();
    }

    public static final Creator<LoginInfo> CREATOR = new Creator<LoginInfo>() {
        @Override
        public LoginInfo createFromParcel(Parcel source) {
            return new LoginInfo(source);
        }

        @Override
        public LoginInfo[] newArray(int size) {
            return new LoginInfo[size];
        }
    };
}
