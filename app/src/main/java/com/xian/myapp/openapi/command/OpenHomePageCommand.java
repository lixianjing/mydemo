package com.xian.myapp.openapi.command;


import com.xian.myapp.openapi.OpenApiController;

/**
 * 打开首页的命令
 * User: 孙伟力
 * Date: 15/2/3
 * Time: 下午8:53
 */
public class OpenHomePageCommand extends OpenApiCommand {
    @Override
    public boolean checkParams() {
        return true;
    }

    @Override
    public void execute(OpenApiController openApiController) {
    }
}
