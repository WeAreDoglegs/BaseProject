package com.doglegs.core.rx;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.doglegs.core.base.BaseResponse;
import com.doglegs.core.constants.HttpCode;

import org.reactivestreams.Publisher;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * author: Mai_Xiao_Peng
 * email  : Mai_Xiao_Peng@163.com
 * time  : 2017/4/25
 */

public class RxUtils {

    /**
     * 统一线程处理
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> rxSchedulerHelper() {    //compose简化线程
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 处理返回结果 返回T
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<BaseResponse<T>, T> handleResult() {
        return httpResponseFlowable -> httpResponseFlowable.flatMap((Function<BaseResponse<T>, Flowable<T>>) httpResponce -> {
            if (httpResponce == null) {
                return Flowable.error(new ApiException(HttpCode.HTTP_NULL, HttpCode.HTTP_NULL.getMessage()));
            }
            if (httpResponce.getCode().equals(HttpCode.HTTP_OK.getCode())) {
                return createData(httpResponce.getData());
            }
            return handleCode(httpResponce);
        });
    }

    /**
     * 处理返回结果 返回T
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<ResponseBody, BaseResponse<T>> handleResponceBody() {
        return httpResponseFlowable -> httpResponseFlowable.flatMap((Function<ResponseBody, Flowable<BaseResponse<T>>>) responce -> {
            Gson gson = new Gson();
            String body = responce.string();
            BaseResponse<T> httpResponce = gson.fromJson(body, new TypeToken<BaseResponse<T>>() {
            }.getType());
            return createData(httpResponce);
        });
    }

    /**
     * 处理返回结果 包装对象
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<BaseResponse<T>, RxOptional<T>> handleResultOptional() {
        return httpResponseFlowable -> httpResponseFlowable.flatMap((Function<BaseResponse<T>, Flowable<RxOptional<T>>>) httpResponce -> {
            if (httpResponce == null) {
                return Flowable.error(new ApiException(HttpCode.HTTP_NULL, HttpCode.HTTP_NULL.getMessage()));
            }
            if (httpResponce.getCode().equals(HttpCode.HTTP_OK.getCode())) {
                return createData(new RxOptional<>(httpResponce.getData()));
            }
            return handleCode(httpResponce);
        });
    }

    /**
     * 处理返回结果返回code
     *
     * @return
     */
    public static FlowableTransformer<BaseResponse, Integer> handleResultCode() {
        return httpResponseFlowable -> httpResponseFlowable.flatMap((Function<BaseResponse, Publisher<Integer>>) httpResponce -> {
            if (httpResponce == null) {
                return Flowable.error(new ApiException(HttpCode.HTTP_NULL, HttpCode.HTTP_NULL.getMessage()));
            }
            if (httpResponce.getCode().equals(HttpCode.HTTP_OK.getCode())) {
                return createData(httpResponce.getCode());
            }
            return handleCode(httpResponce);
        });
    }

    /**
     * 处理请求状态码
     *
     * @param httpResponce
     * @return
     */
    private static Flowable handleCode(BaseResponse httpResponce) {
        if (httpResponce.getCode().equals(HttpCode.HTTP_EXPIRED_401.getCode())) {
            RxBus.getInstance().post(new RxEvent(RxEvent.EventType.SYSTEM_LOGIN_OUT, null));
            return Flowable.error(new ApiException(HttpCode.HTTP_EXPIRED_401, httpResponce.getMessage()));
        } else if (httpResponce.getCode().equals(HttpCode.HTTP_EXPIRED_402.getCode())) {
            RxBus.getInstance().post(new RxEvent(RxEvent.EventType.SYSTEM_LOGIN_OUT, null));
            return Flowable.error(new ApiException(HttpCode.HTTP_EXPIRED_402, httpResponce.getMessage()));
        } else if (httpResponce.getCode().equals(HttpCode.HTTP_MERCHANT_NOT_EXIST.getCode())) {
            return Flowable.error(new ApiException(HttpCode.HTTP_MERCHANT_NOT_EXIST, httpResponce.getMessage()));
        } else {
            return Flowable.error(new ApiException(HttpCode.HTTP_UNKNOWN, httpResponce.getMessage()));
        }
    }


    /**
     * 生成Flowable
     *
     * @param <T>
     * @return
     */
    public static <T> Flowable<T> createData(final T t) {
        return Flowable.create(emitter -> {
            try {
                emitter.onNext(t);
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        }, BackpressureStrategy.BUFFER);
    }

    /**
     * 生成字符Flowable
     *
     * @return
     */
    public static Flowable createData(final String t) {
        return Flowable.create((FlowableOnSubscribe<String>) emitter -> {
            try {
                emitter.onNext(t);
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        }, BackpressureStrategy.BUFFER);
    }

    public static class ApiException extends Exception {

        public ApiException(HttpCode httpCode, String msg) {
            super(msg);
        }

    }

}
