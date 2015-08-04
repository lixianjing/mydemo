package com.xian.myapp.openapi.command;


import com.xian.myapp.openapi.OpenApiController;

/**
 * User: 孙伟力
 * Date: 15/2/4
 * Time: 下午6:01
 */
public class OpenTaskListPageCommand extends OpenApiCommand {
    @Override
    public boolean checkParams() {
        return true;
    }

    @Override
    public void execute(OpenApiController openApiController) {
//        SLNavigation.startPage(openApiController.activity, null, NavigationFactory.NORMAL_PAGE_TASK_LIST);
    }
}
