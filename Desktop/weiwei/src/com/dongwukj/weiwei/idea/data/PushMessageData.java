package com.dongwukj.weiwei.idea.data;

import java.util.Date;

/**
 * Created by sunjaly on 15/5/7.
 */
public class PushMessageData {

    private int id;
    /**
     * 1:商品详情 2:url页面 3:纯文本 4:特殊标示(目前为通知账户升级)
     */
    private int msgType;
    /**
     * 业务相关 1:交易，2:资讯，3:物流，4:提醒
     */
    private int businessType;

    private boolean isReaded;

    private Date createTime;


    private String title;
    private String msg;
    private int businessId;
    private String businessUrl;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public int getBusinessType() {
        return businessType;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }

    public boolean isReaded() {
        return isReaded;
    }

    public void setReaded(boolean isReaded) {
        this.isReaded = isReaded;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getBusinessId() {
        return businessId;
    }

    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    public String getBusinessUrl() {
        return businessUrl;
    }

    public void setBusinessUrl(String businessUrl) {
        this.businessUrl = businessUrl;
    }
}
