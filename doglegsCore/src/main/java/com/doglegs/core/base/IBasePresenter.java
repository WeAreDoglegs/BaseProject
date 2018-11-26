package com.doglegs.core.base;

public interface IBasePresenter<T extends IBaseView> {

    void attachView(T view);

    void detachView();

}
