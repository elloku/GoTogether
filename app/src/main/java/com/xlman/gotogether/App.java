package com.xlman.gotogether;

import android.content.Context;

import com.igexin.sdk.PushManager;
import com.xlman.common.app.Application;
import com.xlman.factory.Factory;


public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // 调用Factory进行初始化
        Factory.setup();
        // 推送进行初始化
        PushManager.getInstance().initialize(this.getApplicationContext(),null);
    }

    @Override
    protected void showAccountView(Context context) {

        // 登录界面的显示


    }
}
