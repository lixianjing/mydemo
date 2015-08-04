package com.xian.myapp.openapi;

import android.net.Uri;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * User: 孙伟力
 * Date: 15/2/4
 * Time: 下午6:44
 */
public class OpenApiUtils {
    public static final String SCHEME_HOST = "gjcw://openapi/";
    public static final String ACTION = "action";
    public static Uri jsonToUri(String json) {
        Uri uri = null;
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
            String action = jsonObject.optString(ACTION);
            StringBuilder sb = new StringBuilder();
            sb.append(SCHEME_HOST);
            sb.append(action);
            Iterator<String> it = jsonObject.keys();
            boolean firstChar = true;
            while (it.hasNext()) {
                String key = it.next();
                String value = jsonObject.getString(key);
                if (!TextUtils.equals(ACTION, key)) {
                    sb.append(firstChar ? "?" : "&");
                    sb.append(key);
                    sb.append("=");
                    sb.append(value);
                    firstChar = false;
                }
            }

            uri = Uri.parse(sb.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return uri;
    }
}
