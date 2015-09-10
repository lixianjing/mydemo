package com.xian.myapp.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.xian.myapp.R;
import com.xian.myapp.base.BaseActivity;
import com.xian.myapp.openapi.OpenApiController;
import com.xian.myapp.utils.ToastUtils;
import com.xian.myapp.volley.VolleyActivity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;

public class WebViewActivity extends BaseActivity {

    private Context mContext;
    private WebView mWebView;
    private EditText inputEt;
    private Button backBtn, forwardBtn, freshBtn,goBtn;
    private ProgressBar bar;


    private View.OnClickListener myClick=new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.web_go:
                    String url=inputEt.getText().toString();
                    if(!TextUtils.isEmpty(url)){
                        if(!url.startsWith("http://")){
                            url="http://"+url;
                        }
                        mWebView.loadUrl(url);
                    }
                    break;
                case R.id.web_back:
                    if(mWebView.canGoBack()){
                        mWebView.goBack();
                    }else{
                        ToastUtils.showToast("不能后退");
                    }
                    break;
                case R.id.web_forward:
                    if(mWebView.canGoForward()){
                        mWebView.goForward();
                    }else{
                        ToastUtils.showToast("不能前进");
                    }
                    break;
                case R.id.web_refresh:
                    mWebView.reload();
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        mContext = this;
        mWebView = (WebView) findViewById(R.id.web_main);
        inputEt = (EditText) findViewById(R.id.web_et);
        goBtn = (Button) findViewById(R.id.web_go);
        backBtn = (Button) findViewById(R.id.web_back);
        forwardBtn = (Button) findViewById(R.id.web_forward);
        freshBtn = (Button) findViewById(R.id.web_refresh);
        bar = (ProgressBar) findViewById(R.id.web_progress);
        goBtn.setOnClickListener(myClick);
        backBtn.setOnClickListener(myClick);
        forwardBtn.setOnClickListener(myClick);
        freshBtn.setOnClickListener(myClick);



        mWebView.clearHistory();
        mWebView.clearCache(true);

        enableHtml5(mWebView);
        setWebClient();

//		mWebView.loadUrl("http://10.0.2.2:8080/html5/audiotest.html");
//		mWebView.loadUrl("http://10.0.2.2:8080/webapp/snippets/01/backgroundtest.html");
//		mWebView.loadUrl("http://10.0.2.2:8080/webapp/snippets/01/consoletest.html");
//		mWebView.loadUrl("http://10.0.2.2:8080/webapp/snippets/02/create-task-form-simple.html");
//		mWebView.loadUrl("http://10.0.2.2:8080/webapp/snippets/02/create-task-form-styled.html");
//		mWebView.loadUrl("http://10.0.2.2:8080/webapp/snippets/02/create-task-form-validating.html");
//        mWebView.loadUrl("http://10.0.2.2:8080/webapp/snippets/03/webstorage-test.html");


    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void setWebClient(){
        //主要处理解析，渲染网页等浏览器做的事情
        mWebView.setWebViewClient(new WebViewClient() {
            //本地处理还是webview处理
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("lmf", "webview shouldOverrideUrlLoading: " + url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view,
                                                              String url) {
                Log.d("lmf", "webview shouldInterceptRequest: " + url);

                if (url != null && url.endsWith(".jpg")) {
                    int index = url.lastIndexOf(File.separator);
                    String img = url.substring(index + 1);
                    Log.d("lmf", "webview shouldInterceptRequest: " + img);
                    return getJpgWebResourceResponseFromAsset("image/" + img);
                }

                return super.shouldInterceptRequest(view, "url");
            }
            //使用本地资源还是系统资源
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                Log.d("lmf", "webview shouldInterceptRequest: " + request.toString());
                return super.shouldInterceptRequest(view, request);
            }
            //webview 将要加载的资源
            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                Log.d("lmf", "webview load resource: " + url);

            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                Log.e("lmf", "webview receive error: " + description);
                bar.setVisibility(View.GONE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.e("lmf", "webview onPageFinished: " + url);
                bar.setVisibility(View.GONE);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                bar.setVisibility(View.VISIBLE);
                Log.e("lmf", "webview onPageStarted: " + url);
            }

        });
//辅助WebView处理Javascript的对话框，网站图标，网站title，加载进度等
        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onJsAlert(WebView view, String url, String message,
                                     JsResult result) {
                Log.d("lmf", "webview onJsAlert: " + url);
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsConfirm(WebView view, String url,
                                       String message, JsResult result) {
                Log.d("lmf", "webview onJsConfirm: " + url);
                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Log.d("lmf", "onConsoleMessage: " + consoleMessage.message()
                        + ", sourceID: " + consoleMessage.sourceId()
                        + ", lineNumber: " + consoleMessage.lineNumber());
                return super.onConsoleMessage(consoleMessage);
            }

            @Override
            public void onExceededDatabaseQuota(String url,
                                                String databaseIdentifier, long quota,
                                                long estimatedDatabaseSize, long totalQuota,
                                                WebStorage.QuotaUpdater quotaUpdater) {
                quotaUpdater.updateQuota(estimatedDatabaseSize * 2);
            }

            @Override
            public void onReachedMaxAppCacheSize(long requiredStorage,
                                                 long quota, WebStorage.QuotaUpdater quotaUpdater) {
                quotaUpdater.updateQuota(requiredStorage * 2);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

                if (newProgress == 100) {
                    bar.setVisibility(View.GONE);
                } else {
                    if (bar.getVisibility() == View.GONE) {
                        bar.setVisibility(View.VISIBLE);
                    }
                    bar.setProgress(newProgress);
                }
            }

        });
    }

    public void enableHtml5(WebView webView) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //全局有效只应该被调用一次
        webSettings.setDatabaseEnabled(true);
        webSettings.setDatabasePath("/data/data/" + getPackageName()
                + "/localstorage/");
        webSettings.setDomStorageEnabled(true);
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(true);
        webSettings.setSupportZoom(false);

        webSettings.setAppCacheEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheMaxSize(100 * 1024 * 1024);
        webSettings.setAppCachePath("/data/data/" + getPackageName()
                + "/cache/");
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

    }

    /**
     * Return WebResourceResponse with CSS markup from a String.
     */
    private WebResourceResponse getCssWebResourceResponseFromString() {
        return getUtf8EncodedCssWebResourceResponse(new StringBufferInputStream("body { background-color: #F781F3; }"));
    }

    /**
     * Return WebResourceResponse with CSS markup from an asset (e.g. "assets/style.css").
     */
    private WebResourceResponse getCssWebResourceResponseFromAsset() {
        try {
            return getUtf8EncodedCssWebResourceResponse(getAssets().open("style.css"));
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Return WebResourceResponse with CSS markup from an asset (e.g. "assets/style.css").
     */
    private WebResourceResponse getJpgWebResourceResponseFromAsset(String str) {
        try {
            return getUtf8EncodedCssWebResourceResponse(getAssets().open(str));
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Return WebResourceResponse with CSS markup from a raw resource (e.g. "raw/style.css").
     */
//    private WebResourceResponse getCssWebResourceResponseFromRawResource() {
//        return getUtf8EncodedCssWebResourceResponse(getResources().openRawResource(R.raw.style));
//    }
    private WebResourceResponse getUtf8EncodedCssWebResourceResponse(InputStream data) {
        return new WebResourceResponse("text/css", "UTF-8", data);
    }


}
