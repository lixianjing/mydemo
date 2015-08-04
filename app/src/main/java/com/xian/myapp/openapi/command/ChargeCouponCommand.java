package com.xian.myapp.openapi.command;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.xian.myapp.openapi.OpenApiController;


/**
 * 优惠券充值
 * User: 孙伟力
 * Date: 15/2/4
 * Time: 上午10:46
 */
public class ChargeCouponCommand extends OpenApiCommand {

    private String specialCode;
    private String productCode;
    private String extendsInfo;
    private String type;
    @Override
    public boolean checkParams() {
        if (model.params.isEmpty()) {
            return false;
        }
        if (TextUtils.isEmpty(model.params.getString("special_code"))) {
           return false;
        }

        if (TextUtils.isEmpty(model.params.getString("type"))) {
            return false;
        }

        type = model.params.getString("type");
        specialCode = model.params.getString("special_code");
        productCode = model.params.getString("product_code");
        extendsInfo = model.params.getString("extends");

        return true;
    }

    @Override
    public void execute(OpenApiController openApiController) {
//        Bundle bundle = new Bundle();
//        bundle.putString(ChargeCouponFragment.PARAMS_KEY_PRODUCT_CODE, productCode);
//        bundle.putString(ChargeCouponFragment.PARAMS_KEY_SPECIAL_CODE, specialCode);
//        bundle.putString(ChargeCouponFragment.PARAMS_KEY_TYPE, type);
//        bundle.putString(ChargeCouponFragment.PARAMS_KEY_EXTENDS_INFO, extendsInfo);
//        SLNavigation.startPage(openApiController.activity, bundle, NavigationFactory.NORMAL_PAGE_CHARGE_COUPON);
        Log.e("lmf", "do something");
    }
}
