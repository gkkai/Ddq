package com.example.kk.ddq.wxapi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.kk.ddq.R;
import com.example.kk.ddq.app.BaseApplication;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.mm.opensdk.utils.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kk on 2019/1/22.
 */

public class WXPayEntryActivity  extends Activity implements IWXAPIEventHandler {
    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.pay_result);

        api = WXAPIFactory.createWXAPI(this, "wx2b72d27804dc29c3");
        //api.registerApp("wx2b72d27804dc29c3");
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {
        Log.i("ansen", "微信支付回调 返回错误码:"+resp.errCode+" 错误名称:"+resp.errStr);
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX){
            //微信支付
//            WeiXin weiXin=new WeiXin(3,resp.errCode,"");
//            EventBus.getDefault().post(weiXin);
        }
        if(resp.errCode==0){
            BaseApplication.x5WebView.loadUrl("javascript:toOrderList("+1+")");
        }else {
            BaseApplication.x5WebView.loadUrl("javascript:toOrderList("+0+")");
        }

        finish();
//        JSONObject localJSONObject=new JSONObject();
//        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX)
//        {
//            //finish();
//            //localJSONObject =
//        }
//        try {
//            localJSONObject.put("status", resp.errCode);
//             localJSONObject.put("weixinPayOrderNo", BaseApplication.out_trade_no);
//             localJSONObject.toString();
//            BaseApplication.x5WebView.loadUrl("javascript:+toOrderList('" + localJSONObject.toString() + "')");
//            return;
//        }
//        catch (JSONException paramBaseResp)
//        {
//            paramBaseResp.printStackTrace();
//        }
    }
}
