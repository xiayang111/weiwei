package com.dongwukj.weiwei.idea.request;

import org.apache.commons.codec.digest.DigestUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sunjaly on 15/3/20.
 */
public class BaseRequest {

    private String userAccount="0";
    private int version;
    private long time;
    private String tokenId;
    private String imei;
    
    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public long getTime() {
        return System.currentTimeMillis()/1000;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTokenId() {
        if(tokenId==null){
            SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
            String sign="weiweishenghuo"+sdf.format(new Date());
            return DigestUtils.md5Hex(sign);
        }
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}
