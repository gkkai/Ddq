package com.example.kk.ddq.bean;

/**
 * Created by kk on 2019/1/23.
 */

public class PayBean {
    private String appId;
    private String partnerid;
    private String prepayid;
    private String nonceStr;
    private String timeStamp;
    private String sign;

    public PayBean() {
        super();
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }



    public String getNoncestr() {
        return nonceStr;
    }

    public void setNoncestr(String noncestr) {
        this.nonceStr = noncestr;
    }

    public String getTimestamp() {
        return timeStamp;
    }

    public void setTimestamp(String timestamp) {
        this.timeStamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
