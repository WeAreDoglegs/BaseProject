package com.doglegs.baseproject.start.presenter.contract;


import com.doglegs.core.base.IBaseView;

/**
 * Description: 登录注册界面契约类
 *
 * @Author: czk
 * @Date: 2018/11/21
 */

public interface ILoginRegisteredContract {

    /**
     * 视图处理接口
     */
    interface IView extends IBaseView {

    }

    /**
     * 网络任务接口
     */
    interface IPresenter {

        /**
         * 解析剪切板信息
         *
         * @param code
         */
        void parseClipboard(String code);
    }
}
