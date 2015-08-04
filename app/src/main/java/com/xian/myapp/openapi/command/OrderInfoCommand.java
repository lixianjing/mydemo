package com.xian.myapp.openapi.command;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.xian.myapp.openapi.OpenApiController;


/**
 * 订单推送处理command
 * User: 孙伟力
 * Date: 15/2/4
 * Time: 下午6:01
 */
public class OrderInfoCommand extends OpenApiCommand {
    private String puid;
    private int status;
    private String content;
    private Activity activity;

    @Override
    public boolean checkParams() {
        if (model != null && model.params != null) {
            puid = model.params.getString("needsPuid");
            String str = model.params.getString("status");
            if (TextUtils.isDigitsOnly(str)) {
                status = Integer.parseInt(str);
            }
            content = model.params.getString("desc");
        }

        return !TextUtils.isEmpty(puid) && !TextUtils.isEmpty(content);
    }

    @Override
    public void execute(OpenApiController openApiController) {
//        //刷新订单列表页面
//        activity = openApiController.activity;
//        String componentName = SLApolloUtils.getTopActivity(GJApplication.getContext());
//        if (componentName != null) {
//            CNeedsEntity entity = new CNeedsEntity();
//            entity.mNeedsPuid = puid;
//            entity.mNeedsStatus = status;
//            if (componentName.contains(COrderActivity.class.getName())) {//在订单列表页面，直接刷新页面数据
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("needs_entity", entity);
//                SLNavigation.startPage(activity, bundle, NavigationFactory.NORMAL_PAGE_ORDER);
//            }else{//弹出提示框
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("data", entity);
//                bundle.putString("content", content);
//                SLNavigation.startPage(activity,bundle,NavigationFactory.NORMAL_PAGE_NOTIFY, Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            }
//        }
    }
}
