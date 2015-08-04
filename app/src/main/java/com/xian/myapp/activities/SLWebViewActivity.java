package com.xian.myapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import com.xian.myapp.R;
import com.xian.myapp.base.BaseActivity;
import com.xian.myapp.logs.SLLog;
import com.xian.myapp.utils.SLNotifyUtil;
import com.xian.myapp.widget.SLActionBar;

/**
 * @author 吴书永
 */
public class SLWebViewActivity extends BaseActivity {

    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_URL = "url";
    public static final String PRESSED_BACKICON_IMMEDIATE_BACK = "PRESSED_BACKICON_IMMEDIATE_BACK";

    private SLActionBar mSlActionBar;
    private ProgressBar mHorizontalProgress;
    private WebView mWebView;
    private boolean mIsImmediateBack = false;
    private String url;

    private Handler refreshProgressHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.arg1 >= 100) {
                if (mHorizontalProgress != null) {
                    mHorizontalProgress.setVisibility(View.GONE);
                }
            } else {
                if (mHorizontalProgress != null && msg.arg1 >= 0) {
                    mHorizontalProgress.setVisibility(View.VISIBLE);
                    mHorizontalProgress.setProgress(msg.arg1);
                }
            }
        }
    };

    public String getUrl() {
        return url;
    }

    public SLActionBar getSlActionBar() {
        return mSlActionBar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.sl_web_view);

        mSlActionBar = (SLActionBar) findViewById(R.id.sl_actionbar);
        final String title = getIntent().getStringExtra(EXTRA_TITLE);
        mIsImmediateBack = getIntent().getBooleanExtra(PRESSED_BACKICON_IMMEDIATE_BACK, false);
        if (title != null && title.length() > 0) {
            mSlActionBar.setTitle(title);
        } else {
            mSlActionBar.setTitle(null);
        }
        mSlActionBar.setBackImage(0, mBackListener);
        mHorizontalProgress = (ProgressBar) findViewById(R.id.progress_horizontal);
        mWebView = (WebView) findViewById(R.id.webview);

        url = getIntent().getStringExtra(EXTRA_URL);
        setWebView(title);

        loadUrl();
    }

    public void setWebView(final String title) {
        // 设置支持JavaScript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setDomStorageEnabled(true);

        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        mWebView.setWebViewClient(new WebViewClient() {
            //			@Override
//			public boolean shouldOverrideUrlLoading(WebView view, String url) {
//				/*view.loadUrl(url);
//				return true;*/
//				return false; // 返回false 仍然由该 WebView 处理 url
//			}
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onFormResubmission(WebView view, Message dontResend, Message resend) {
                resend.sendToTarget();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                SLNotifyUtil.showToast("网络异常！");
            }

        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (refreshProgressHandler != null) {
                    if (refreshProgressHandler.hasMessages(0)) {
                        refreshProgressHandler.removeMessages(0);
                    }
                    Message mMessage = refreshProgressHandler.obtainMessage(0, newProgress, 0, null);
                    refreshProgressHandler.sendMessageDelayed(mMessage, 100);
                }

            }

            @Override
            public void onReceivedTitle(WebView view, String t) {
                super.onReceivedTitle(view, t);
                SLLog.d("", "title:" + t);
                if (TextUtils.isEmpty(title)) {
                    mSlActionBar.setTitle(t);
                }
            }
        });

        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                finish();
            }
        });
    }

    public WebView getWebView() {
        return mWebView;
    }

    public void loadUrl() {
        mWebView.loadUrl(url);
    }

    OnClickListener mBackListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.btn_title_left) {
                if (mIsImmediateBack) {
                    onBackPressed();
                } else {
                    /*if (mWebView.canGoBack()) {
                        mWebView.goBack();
                    } else {*/
                    onBackPressed();
                    //}
                }
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
    }

    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK) {
            if (mIsImmediateBack) {
                onBackPressed();
            } else {
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                } else {
                    onBackPressed();
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }*/
}