package com.minigee.app.base;

import android.os.Bundle;

import com.minigee.app.model.User;
import com.minigee.app.ui.Login;

/**
 * Created by Zhou on 2015-12-17.
 */
public class BaseUiAuth extends BaseUi {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 必须登入状态
        if (!User.getInstance().getLogin()) {
            overlay(Login.class);
            doFinish();
//            forward(Login.class);
        }
    }



}
