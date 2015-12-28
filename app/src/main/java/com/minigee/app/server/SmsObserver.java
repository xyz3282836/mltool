package com.minigee.app.server;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Zhou on 2015-12-18.
 */
public class SmsObserver extends ContentObserver {
    private Context mContext;
    private Handler mHandler;

    public SmsObserver(Context context,Handler handler) {
        super(handler);
        mContext = context;
        mHandler = handler;
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);

//        Log.w("AppDebug",uri.toString());

        if (uri.toString().equals("content://sms/raw")){
            return;
        }

        Uri inboxUri = Uri.parse("content://sms/inbox");

        Cursor c = mContext.getContentResolver().query(uri,null,null,null,"date desc");
        if (c != null){
            if (c.moveToFirst()){
                String smscont = c.getString(c.getColumnIndex("body"));

                Pattern pattern = Pattern.compile("(\\d{4})");
                Matcher matcher = pattern.matcher(smscont);
                if (matcher.find()){
                    String code = matcher.group(0);
                    mHandler.obtainMessage(1,code).sendToTarget();
                }
            }
        }
        c.close();
    }
}
