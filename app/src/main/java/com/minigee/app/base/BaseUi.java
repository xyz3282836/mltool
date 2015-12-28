package com.minigee.app.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.minigee.app.R;
import com.minigee.app.model.User;
import com.minigee.app.ui.Login;
import com.minigee.app.ui.Main;
import com.minigee.app.util.MyCallback;

import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by Zhou on 2015-11-18.
 */
public class BaseUi extends AppCompatActivity {
    protected BaseApp app;
    protected ProgressDialog pd;
    public boolean loadbar = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.app = (BaseApp) this.getApplicationContext();
        this.pd = new ProgressDialog(this);

        this.app = (BaseApp) x.app();
        x.view().inject(this);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            doFinish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

    }

    public void initToolbar(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    // util method
    public void showLoadBar() {
        pd.show();
    }

    public void hideLoadBar() {
        pd.cancel();
    }

    public void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void openAlertDialog() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);

//        alertDialogBuilder.setTitle();//设置标题
//        alertDialogBuilder.setIcon();//设置图表
//
//    /*设置下方按钮*/
//        alertDialogBuilder.setPositiveButton();
//        alertDialogBuilder.setNegativeButton();
//        alertDialogBuilder.setNeutralButton();
//
//    /*对话框内容区域的设置提供了多种方法*/
//        setMessage();//设置显示文本
//        setItems();//设置对话框内容为简单列表项
//        setSingleChoiceItems();//设置对话框内容为单选列表项
//        setMultiChoiceItems();//设置对话框内容为多选列表项
//        setAdapter();//设置对话框内容为自定义列表项
//        setView();//设置对话框内容为自定义View
//
//        //设置对话框是否可取消
//        setCancelable(booleab cancelable);
//        setCancelListener(onCancelListener);
        adb.setTitle("title");
        adb.setView(new EditText(this));
        adb.setPositiveButton("确定", null);
        adb.setNegativeButton("取消", null);

        AlertDialog ad = adb.create();
        ad.show();

    }

    public void openMDialog() {
//        new MaterialDialog.Builder(this)
//                .title(R.string.input)
//                .content(R.string.input_content)
//                .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)
//                .input(R.string.input_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {
//                    @Override
//                    public void onInput(MaterialDialog dialog, CharSequence input) {
//                        // Do something
//                    }
//                }).show();


        new MaterialDialog.Builder(this)
                .title("输入")
                .inputRangeRes(2, 20, R.color.red500)
                .input(null, null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        toast(input.toString());
                    }
                }).show();
    }

    public void overlay(Class<?> classObj) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        intent.setClass(this, classObj);
        startActivity(intent);
    }

    public void overlay(Class<?> classObj, Bundle params) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        intent.setClass(this, classObj);
        intent.putExtras(params);
        startActivity(intent);
    }

    // 类似 singleTask
    public void forward(Class<?> classObj) {
        Intent intent = new Intent();
        intent.setClass(this, classObj);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);
        this.finish();
    }

    public void forward(Class<?> classObj, Bundle params) {
        Intent intent = new Intent();
        intent.setClass(this, classObj);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtras(params);
        this.startActivity(intent);
        this.finish();
    }

    public Context getContext() {
        return this;
    }

    // logic method
    public void doFinish() {
        this.finish();
    }


    public void onnet() {

    }

    public void overnet() {
        hideLoadBar();
    }


    public void logOut() {

        if (User.getInstance().getLogin()) {
            RequestParams params = new RequestParams(C.api.logout);
            showLoadBar();
            x.http().get(params, new MyCallback<String>(this) {
                @Override
                public void action(String result) {
                    //手动，BaseUi就不得自动登入
                    User.setUser(null);
                    User.getInstance().setLogin(false);
                    ui.onResume();
                }
            });
        } else {
            toast("还未登入");
        }
    }
}
