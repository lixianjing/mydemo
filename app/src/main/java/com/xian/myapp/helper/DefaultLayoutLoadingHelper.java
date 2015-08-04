package com.xian.myapp.helper;

import android.view.View;

/**
 * User: 孙伟力
 * Date: 2014/11/6
 * Time: 10:09
 */
public class DefaultLayoutLoadingHelper {

    private View rootView;
    private View contentView;
    private View errorView;
    private View loadingView;
    private Command command;

    public DefaultLayoutLoadingHelper(View root, int contentResId, int errorResId, int loadingResId) {
        rootView = root;
        contentView = rootView.findViewById(contentResId);
        errorView = rootView.findViewById(errorResId);
        loadingView = rootView.findViewById(loadingResId);
    }

    public void setLoadCommand(Command c) {
        this.command = c;
    }

    public interface Command {
        public void exe();
    }

    public void loading() {
        errorView.setVisibility(View.GONE);
        contentView.setVisibility(View.GONE);
        loadingView.setVisibility(View.VISIBLE);
    }

    public void showContent() {
        errorView.setVisibility(View.GONE);
        contentView.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);
    }

    public void showError() {
        errorView.setVisibility(View.VISIBLE);
        contentView.setVisibility(View.GONE);
        loadingView.setVisibility(View.GONE);
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (command != null) {
                    command.exe();
                }
            }
        });
    }
}
