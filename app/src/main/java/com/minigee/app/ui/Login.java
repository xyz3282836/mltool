package com.minigee.app.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.minigee.app.R;
import com.minigee.app.base.BaseUi;
import com.minigee.app.base.C;
import com.minigee.app.model.User;
import com.minigee.app.util.MyCallback;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Zhou on 2015-12-12.
 */
@ContentView(R.layout.ui_login)
public class Login extends BaseUi {
    @ViewInject(R.id.account)
    private EditText account;

    @ViewInject(R.id.pass)
    private EditText pass;

    @Event(R.id.btn_login)
    private void loginClick(View view){
        RequestParams params = new RequestParams(C.api.login_mix);
        params.setConnectTimeout(C.netconn.conntime);
        params.addQueryStringParameter("mix",account.getText().toString());
        params.addQueryStringParameter("password",pass.getText().toString());
        showLoadBar();
        x.http().get(params, new MyCallback<String>(this) {

            @Override
            public void action(String result) {
                User user = JSON.parseObject(result,User.class);
                User.setUser(user);
                User.getInstance().setLogin(true);
            }
        });
    }

    @Event(R.id.btn_reg)
    private void regClick(View view){
        overlay(Reg.class);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initToolbar(getResources().getString(R.string.ui_title_login));
    }
}
