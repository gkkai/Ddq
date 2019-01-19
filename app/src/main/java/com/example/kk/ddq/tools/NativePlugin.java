package com.example.kk.ddq.tools;

import android.content.Context;
import android.webkit.JavascriptInterface;
import com.example.kk.ddq.ShareBean;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import org.json.JSONException;
import org.json.JSONObject;

public class NativePlugin {
    private Context context;
    public NativePlugin(Context context) {
        this.context=context;
    }

    @JavascriptInterface
    public void shareweixixn(String paramString)
    {
//        '{"type":"1",
//        "url":"https://www.dodohappy.com/template/qdd/html/yaoqingzhuce.html?yaoqing='+$scope.invitationCode+'",
//            "title":"终于发现这么好的一个地方",
//            "content":"旧的玩具可以回收了，大家快来看呀！"}';

        // 通过appId得到IWXAPI这个对象
        Gson gson=new Gson();
        ShareBean shareBean=gson.fromJson(paramString,ShareBean.class);

        IWXAPI wxapi = WXAPIFactory.createWXAPI(context, "wx2b72d27804dc29c3");
        // 检查手机或者模拟器是否安装了微信
        if (!wxapi.isWXAppInstalled()) {
            ToastUtils.show("您还没有安装微信");
            return;
        }

        // 初始化一个WXWebpageObject对象
        WXWebpageObject webpageObject = new WXWebpageObject();
        // 填写网页的url
        webpageObject.webpageUrl = shareBean.getUrl();

        // 用WXWebpageObject对象初始化一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage(webpageObject);
        msg.title = shareBean.getTitle();
        msg.description = shareBean.getContent();
        // 如果没有位图，可以传null，会显示默认的图片
        msg.setThumbImage(null);

        // 构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        // transaction用于唯一标识一个请求（可自定义）
        req.transaction = "webpage";
        // 上文的WXMediaMessage对象
        req.message = msg;
        if(shareBean.getType().equals("1")){
            //是分享到好友会话
            req.scene= SendMessageToWX.Req.WXSceneSession;
        }else if(shareBean.getTitle().equals("2")){

            //是分享到朋友圈
            req.scene= SendMessageToWX.Req.WXSceneTimeline;
            //req.scene = SendMessageToWX.Req.WXSceneSession;
        }
            // SendMessageToWX.Req.WXSceneSession是分享到好友会话
            // SendMessageToWX.Req.WXSceneTimeline是分享到朋友圈
            //req.scene = SendMessageToWX.Req.WXSceneSession;

        // 向微信发送请求
        wxapi.sendReq(req);

    }

}
