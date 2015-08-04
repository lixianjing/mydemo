package com.xian.myapp.openapi;


import com.xian.myapp.openapi.command.ChargeCouponCommand;
import com.xian.myapp.openapi.command.OpenApiCommand;

import com.xian.myapp.openapi.command.OpenHomePageCommand;
import com.xian.myapp.openapi.command.OpenTaskListPageCommand;
import com.xian.myapp.openapi.command.OpenWebViewPageCommand;
import com.xian.myapp.openapi.command.OrderInfoCommand;

import java.util.HashMap;

/**
 * User: 孙伟力
 * Date: 15/2/3
 * Time: 下午8:49
 */
public class OpenApiCommandFactory {
    public static HashMap<String, OpenApiCommand> commandList;

    static {
        commandList = new HashMap<String, OpenApiCommand>();
        commandList.put("gotoHome", new OpenHomePageCommand());
        commandList.put("gotoWebview", new OpenWebViewPageCommand());
        commandList.put("gotoTaskList", new OpenTaskListPageCommand());
        commandList.put("orderInfo", new OrderInfoCommand());
        commandList.put("chargeCoupon", new ChargeCouponCommand());
    }

    public static OpenApiCommand getCommand(OpenApiModel model) {
        OpenApiCommand command = commandList.get(model.action);
        if (command == null) {
            return null;
        }
        command.setModel(model);
        return command;
    }
}
