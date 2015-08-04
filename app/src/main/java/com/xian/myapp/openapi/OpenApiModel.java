package com.xian.myapp.openapi;

import android.net.Uri;
import android.os.Bundle;

/**
 * User: 孙伟力
 * Date: 15/2/3
 * Time: 下午9:44
 */
public class OpenApiModel {
    public Bundle params;
    public String action;

    public OpenApiModel parse(Uri uri) {
        params = new Bundle();
        String tmpUri = uri.toString();
        if (tmpUri.endsWith("/")) {
            tmpUri = tmpUri.substring(0, tmpUri.length() - 1);
        }
        int len = tmpUri.length();
        int baseLen = tmpUri.length();
        if (tmpUri.contains("?")) {
            len = tmpUri.indexOf("?");
        }
        action = tmpUri.substring(0, len);
        action = action.replace(OpenApiUtils.SCHEME_HOST, "");
        if (tmpUri.contains("?")) {
            tmpUri = tmpUri.substring(len + 1, baseLen);
            String[] args = tmpUri.split("&");
            for (String arg : args) {
                String key = arg.split("=")[0];
                String value = arg.split("=")[1];
                params.putString(key, value);
            }
        }

        return this;
    }
}
