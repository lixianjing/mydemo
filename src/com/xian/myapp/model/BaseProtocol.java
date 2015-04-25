package com.xian.myapp.model;

import android.text.TextUtils;
import com.xian.myapp.event.CommonEvent;
import de.greenrobot.event.EventBus;
import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by 贾远 on 14/11/4.
 * protocol 对应一次请求返回的数据结构
 */
public abstract class BaseProtocol implements IParseFormJSON {

    public static final int ERROR_NETWORK = 1;
    public static final int ERROR_JSON = 2;
    public static final int ERROR_NOT_LOGIN = 3;
    public static final int ERROR_SERVER_ERROR = 4;
    public static final int ERROR_UNKNOW = 5;
    public static final int ERROR_LOGIN_EXPIRED = -2;
    public static final int SUCCESS = 0;

    public int status;
    private String errMessage;
    private String errDetail;
    private Header[] header;

    /**
     * 洗车业务的错误描述
     * @return
     */
    public String getErrorDetail() {
        if (TextUtils.isEmpty(errDetail)) {
            return "获取数据失败，请检查网络";
        }
        return errDetail;
    }

    /**
     * 通用错误描述
     * @return
     */
    public String getErrMessage() {
        if (TextUtils.isEmpty(errMessage)) {
            return "获取数据失败，请检查网络";
        }
        return errMessage;
    }

    @Override
    public boolean parseFromJSON(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            status = jsonObject.optInt("status");
            errMessage = jsonObject.optString("errMessage");
            errDetail = jsonObject.optString("errDetail");

            switch (status) {
                case SUCCESS:
                    return parseFromJSONProtocol(jsonObject);

                case ERROR_LOGIN_EXPIRED: //登录过期
                    EventBus.getDefault().post(new CommonEvent.LoginExpiredEvent());
                    return false;
                default:
                    return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public abstract boolean parseFromJSONProtocol(JSONObject result);

    @Override
    public String toString() {
        return "BaseProtocol{" +
                "status=" + status +
                ", errMessage='" + errMessage + '\'' +
                ", errDetail='" + errDetail + '\'' +
                '}';
    }

    public void setHeader(Header[] h) {
        header = h;
    }

    public Header[] getHeader() {
        return header;
    }
}
