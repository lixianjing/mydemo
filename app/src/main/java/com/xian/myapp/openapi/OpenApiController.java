package com.xian.myapp.openapi;

import android.app.Activity;
import android.net.Uri;

import com.xian.myapp.openapi.command.OpenApiCommand;


/**
 * OpenApi控制器
 * User: 孙伟力
 * Date: 15/2/3
 * Time: 下午8:11
 */
public class OpenApiController {
    public Activity activity;
    private OpenApiModel openApiModel;
    private boolean isExecuted = false;
    private OpenApiCommand command;

    public OpenApiController(Activity act) {
        activity = act;
        init();
    }

    private void init() {
        if (activity == null || activity.getIntent() == null || activity.getIntent().getData() == null) {
            return;
        }

        Uri uri = activity.getIntent().getData();
        openApiModel = new OpenApiModel().parse(uri);
        if (openApiModel == null) {
            return;
        }
        command = OpenApiCommandFactory.getCommand(openApiModel);
    }

    public void execute() {
        if (!isExecuted) {
            if (command!=null && command.checkParams()) {
                command.execute(this);
            }
        }
        isExecuted = true;
    }
}
