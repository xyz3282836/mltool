package com.minigee.app.ui;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.minigee.app.R;
import com.minigee.app.base.BaseUi;
import com.minigee.app.base.C;
import com.minigee.app.server.SmsObserver;
import com.minigee.app.util.MyCallback;

import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Zhou on 2015-12-18.
 */
@ContentView(R.layout.ui_reg)
public class Reg extends BaseUi {

    @ViewInject(R.id.btn_get_mcode)
    private Button btnGetmcode;

    @ViewInject(R.id.btn_reg)
    private Button btnReg;

    @ViewInject(R.id.mobile)
    private EditText mobile;

    @ViewInject(R.id.mcode)
    private EditText mcode;


    private Handler mhandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    String code = (String) msg.obj;
                    mcode.setText(code);
                    break;
            }


        }
    };
    public SmsObserver smsObserver;
    @Event(value = R.id.btn_get_mcode)
    private void getMcodeClick(View view){
        final String mobileNum = mobile.getText().toString();
        if (TextUtils.isEmpty(mobileNum)){
            mobile.setError("不能为空");
            return;
        }else if(!chekcMobile(mobileNum)){
            return;
        }
        RequestParams params = new RequestParams(C.api.get_mcode);
        params.addQueryStringParameter("type","app_reg");
        params.addQueryStringParameter("mobile",mobileNum);
        x.http().get(params, new MyCallback<String>(this) {
            @Override
            public void action(String result) {
                toast("发送中");
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        getContentResolver().unregisterContentObserver(smsObserver);
    }

    private boolean chekcMobile(String mobile) {
        return true;
    }

    @Event(value = R.id.btn_reg)
    private void regClick(View view){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar(getResources().getString(R.string.ui_title_reg));
        smsObserver = new SmsObserver(Reg.this,mhandler);
        Uri uri = Uri.parse("content://sms");
        getContentResolver().registerContentObserver(uri,true,smsObserver);
    }
}
