package com.example.kk.ddq;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.example.kk.ddq.base.BaseActivity;
import com.example.kk.ddq.view.XWebView;
import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;

public class MainActivity extends BaseActivity {
    @Bind(R.id.xWebView)
    XWebView xWebView;
    private static final int REQUEST_CODE_PERMISSION_CAMERA_SD = 100;
    private boolean mShouldOverrideUrlLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_main);
    }

    @Override
    protected void findWidgets() {
        {
            AndPermission.with(MainActivity.this)
                    .requestCode(REQUEST_CODE_PERMISSION_CAMERA_SD)
                    .permission(new String[]{})
                    // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框，避免用户勾选不再提示。
                    .rationale(new RationaleListener() {
                        @Override
                        public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                            AndPermission.rationaleDialog(MainActivity.this, rationale).show();
                        }
                    })
                    .send();

        }





        WebViewClient client = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                // return true;
                super.shouldOverrideUrlLoading(view,url);
                if (url.startsWith("weixin://wap/pay?")) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                    return true;
                }else if (url.startsWith("tel:")) {//H5打开电话
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    MainActivity.this.startActivity(intent);
                } else {
                    //H5微信支付要用，不然说"商家参数格式有误"
                    Map<String, String> extraHeaders = new HashMap<String, String>();
                    extraHeaders.put("Referer", "http://www.dodohappy.com");
                    view.loadUrl(url, extraHeaders);
                }

                // referer = url;
                Log.i("=====================","shouldOverrideUrlLoading");
                return true;//不调用系统的浏览器打开网页
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                mShouldOverrideUrlLoading = true;

                super.onPageStarted(view, url, favicon);
            }
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //此方法是为了处理在5.0以上Htts的问题，必须加上
                handler.proceed();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (mShouldOverrideUrlLoading) {

                    return;
                }

            }
        };
        xWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView webView, String s, String s1, JsResult jsResult) {
                  super.onJsAlert(webView, s, s1, jsResult);
                return false;
            }

            @Override
            public boolean onJsConfirm(WebView webView, String s, String s1, JsResult jsResult) {
                super.onJsConfirm(webView,s,s1,jsResult);
                return false;
            }

            @Override
            public boolean onJsPrompt(WebView webView, String s, String s1, String s2, JsPromptResult jsPromptResult) {
                super.onJsPrompt(webView,s,s1,s2,jsPromptResult);
                return false;
            }
      });
        xWebView.setWebViewClient(client);
        xWebView.loadUrl("http://www.dodohappy.com");
    }

    @Override
    protected void initComponent() {

    }
}
