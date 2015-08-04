package com.xian.myapp.openapi.command;

import android.text.TextUtils;


import com.xian.myapp.openapi.OpenApiController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * User: 孙伟力
 * Date: 15/2/4
 * Time: 上午10:46
 */
public class OpenWebViewPageCommand extends OpenApiCommand {

    @Override
    public boolean checkParams() {
        if (model.params.isEmpty()) {
            return false;
        }
        if (TextUtils.isEmpty(model.params.getString("url"))) {
           return false;
        }

        return true;
    }

    @Override
    public void execute(OpenApiController openApiController) {
//        String url = model.params.getString("url");
//        if (!TextUtils.isEmpty(url)) {
//            try {
//                url = URLDecoder.decode(url , "UTF-8");
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//        }
//        String title = model.params.getString("title");
//        if (!TextUtils.isEmpty(title)) {
//            try {
//                title = URLDecoder.decode(title, "UTF-8");
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//        }
//        CAppUtils.startWebview(url, title, openApiController.activity);
    }
}
