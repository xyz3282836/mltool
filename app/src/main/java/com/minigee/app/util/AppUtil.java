package com.minigee.app.util;

import com.alibaba.fastjson.JSON;
import com.minigee.app.base.BaseMessage;
import com.minigee.app.base.BaseModel;
import com.minigee.app.base.C;
import com.minigee.app.model.Region;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Zhou on 2015-12-4.
 */
public class AppUtil {
    /* md5 加密 */
    static public String md5(String str) {
        MessageDigest algorithm = null;
        try {
            algorithm = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (algorithm != null) {
            algorithm.reset();
            algorithm.update(str.getBytes());
            byte[] bytes = algorithm.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte b : bytes) {
                hexString.append(Integer.toHexString(0xFF & b));
            }
            return hexString.toString();
        }
        return "";
    }

    /* 首字母大写 */
    static public String ucfirst(String str) {
        if (str != null && str != "") {
            str = str.substring(0, 1).toUpperCase() + str.substring(1);
        }
        return str;
    }

    /* 获取 Message */
    static public BaseMessage getMessage(String jsonStr) throws Exception {
        BaseMessage message = new BaseMessage();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonStr);
            if (jsonObject != null) {
                message.setStatus(jsonObject.getString("status"));
                message.setInfo(jsonObject.getString("info"));

                if (!jsonObject.getString("result").equals("")) {
                    message.setResult(jsonObject.getString("result"));
                }

            }
        } catch (JSONException e) {
            throw new Exception("Json format error");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

    /* Model 数组转化成 Map 列表 */
    static public List<? extends Map<String, ?>> dataToList(List<? extends BaseModel> data, String[] fields) {
        ArrayList<HashMap<String, ?>> list = new ArrayList<HashMap<String, ?>>();
        for (BaseModel item : data) {
            list.add((HashMap<String, ?>) dataToMap(item, fields));
        }
        return list;
    }

    /* Model 转化成 Map */
    static public Map<String, ?> dataToMap(BaseModel data, String[] fields) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        try {
            for (String fieldName : fields) {
                Field field = data.getClass().getDeclaredField(fieldName);
                field.setAccessible(true); // have private to be accessable
                map.put(fieldName, field.get(data));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


    static public ArrayList<? extends BaseModel> jsonToModel(JSONArray ja, String modelName) throws Exception {
        String modelClassName = "com.minigee.app.model." + modelName;

        ArrayList<BaseModel> mlist = new ArrayList<BaseModel>();
        for (int i = 0; i < ja.length(); i++) {
            mlist.add(json2model(modelClassName, ja.optJSONObject(i)));
        }
        return mlist;
    }


    static public BaseModel json2model(String modelClassName, JSONObject jo) throws Exception {
        Iterator<String> it = jo.keys();

        BaseModel bm = (BaseModel) Class.forName(modelClassName).newInstance();

        Class<? extends BaseModel> bmm = bm.getClass();
        while (it.hasNext()) {
            String varField = it.next();
            Field field = bmm.getDeclaredField(varField);
            String varValue = jo.getString(varField);
            field.setAccessible(true);
            field.set(bm, varValue);
        }
        return bm;

    }


}
