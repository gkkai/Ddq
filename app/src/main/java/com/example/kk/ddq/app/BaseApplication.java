package com.example.kk.ddq.app;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.example.kk.ddq.base.BaseActivity;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.smtt.sdk.QbSdk;

import java.util.Stack;

/**
 * Created by kk on 2019/1/10.
 */

public class BaseApplication extends Application {
    private static Stack<Activity> atyStack = new Stack<Activity>();
    @Override
    public void onCreate() {
        super.onCreate();
    // 三个参数分别是上下文、应用的appId、是否检查签名（默认为false）
        IWXAPI mWxApi = WXAPIFactory.createWXAPI(this, "wx2b72d27804dc29c3", true);
    // 注册
        mWxApi.registerApp("wx2b72d27804dc29c3");

        //初始化X5内核
        QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                //x5内核初始化完成回调接口，此接口回调并表示已经加载起来了x5，有可能特殊情况下x5内核加载失败，切换到系统内核。

            }

            @Override
            public void onViewInitFinished(boolean b) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.e("@@","加载内核是否成功:"+b);
            }
        });

    }

    public static void push(BaseActivity aty) {
        atyStack.push(aty);
    }
    public static void pop(Activity aty) {
        atyStack.remove(aty);
    }
    public static void remove() {
        for (int i=0;i<atyStack.size();i++){
            atyStack.get(i).finish();
        }
    }
}
