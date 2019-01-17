package com.example.kk.ddq.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.kk.ddq.MainActivity;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kk on 2019/1/10.
 */

public class XWebView  extends WebView {
    private WebViewClient client = new WebViewClient()
    {
        public boolean shouldOverrideUrlLoading(WebView view, String paramAnonymousString)
        {
            Map<String,String> extraHeaders = new HashMap<String, String>();
            extraHeaders.put("Referer", "http://www.dodohappy.com");
            view.loadUrl("http://www.dodohappy.com", extraHeaders);
            // referer = url;
            return true;//不调用系统的浏览器打开网页
           // return true;
        }
    };
    TextView title;

    public XWebView(Context paramContext)
    {
        super(paramContext);
        setBackgroundColor(85621);
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    public XWebView(Context paramContext, AttributeSet paramAttributeSet)
    {
        super(paramContext, paramAttributeSet);
        //setWebViewClient(this.client);
        initWebViewSettings();
        getView().setClickable(true);
    }

    private void initWebViewSettings()
    {
        WebSettings localWebSettings = getSettings();
        localWebSettings.setJavaScriptEnabled(true);
        localWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        localWebSettings.setAllowFileAccess(true);
        localWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        localWebSettings.setSupportZoom(true);
        localWebSettings.setBuiltInZoomControls(false);
        localWebSettings.setUseWideViewPort(false);
        localWebSettings.setSupportMultipleWindows(true);
        localWebSettings.setAppCacheEnabled(true);
        localWebSettings.setDomStorageEnabled(true);
        localWebSettings.setCacheMode(1);
        localWebSettings.setAllowFileAccess(true);
        localWebSettings.setGeolocationEnabled(true);
        localWebSettings.setAppCacheMaxSize(Long.MAX_VALUE);
        //webView.getSettings().setJavaScriptEnabled(true);
        localWebSettings.setPluginState(WebSettings.PluginState.ON_DEMAND);
        if (Build.VERSION.SDK_INT >= 21) {
            localWebSettings.setMixedContentMode(0);
        }
    }



}
