package com.minigee.app.util;

import com.alibaba.fastjson.JSON;
import com.minigee.app.base.BaseMessage;
import com.minigee.app.base.BaseUi;
import com.minigee.app.base.C;

import org.xutils.common.Callback;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * Created by Zhou on 2015-12-16.
 */
public abstract class MyCallback<ResultType> implements Callback.CacheCallback<ResultType> {
    public BaseUi ui;

    public MyCallback(BaseUi ui) {
        this.ui = ui;
    }

    @Override
    public boolean onCache(ResultType result) {
        return false;
    }

    public abstract void action(ResultType result);

    @Override
    public void onSuccess(ResultType result){

        try {
            BaseMessage message = AppUtil.getMessage((String) result);

            if (message.getStatus().equals("1")){
                action((ResultType) message.getResult());
            }else if(message.getStatus().equals("2")){
                //need login
                
            }else{
                ui.toast(message.getInfo());

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        if (ex instanceof ConnectException) {  // 没有网络   网址错（快速反映，无等待）
            if (ex instanceof SocketException) { // 网址错
                ui.toast(C.err.netnone);
            } else {
                ui.toast(C.err.netdown);
            }
        } else if (ex instanceof SocketTimeoutException) {  // 有网   正在连接中状态，无响应（nginx error页面）
            ui.toast(C.err.nettime);
        } else {
            ui.toast("没有此控制器");
        }
    }

    @Override
    public void onCancelled(CancelledException cex) {
        ui.toast(C.err.netnone);
    }

    @Override
    public void onFinished() {
        ui.overnet();
    }
}
