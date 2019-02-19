package com.doglegs.core.base;

public interface IBasePresenter<V extends IBaseView> {

    void attachV(V view);

    void detachV();

    boolean hasV();

}
