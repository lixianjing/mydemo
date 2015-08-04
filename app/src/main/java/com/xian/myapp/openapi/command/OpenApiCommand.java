package com.xian.myapp.openapi.command;


import com.xian.myapp.openapi.OpenApiController;
import com.xian.myapp.openapi.OpenApiModel;

/**
 * User: 孙伟力
 * Date: 15/2/3
 * Time: 下午8:47
 */
public abstract  class OpenApiCommand {
    public OpenApiModel model;

    public void setModel(OpenApiModel m) {
        model = m;
    }

    public abstract boolean checkParams();

    public abstract void execute(OpenApiController openApiController);
}
