package com.doglegs.baseproject.start.presenter.contract;

import com.doglegs.base.bean.net.LoginInfo;
import com.doglegs.base.bean.net.UpdateInfo;
import com.doglegs.core.base.IBaseView;

public interface ISplashContract {

    /**
     * 视图处理接口
     */
    interface IView extends IBaseView {
        void refreshUserInfoSuccess(LoginInfo loginInfo);

        void refreshUserInfoError();

        void getVersionLastSuccess(UpdateInfo updateInfo);

    }

    /**
     * 网络任务接口
     */
    interface IPresenter {
        void getUserInfo();

        void getVersionLast(int appTerminal, int versionCode, int appPlatform, int appChannel);
    }

}
