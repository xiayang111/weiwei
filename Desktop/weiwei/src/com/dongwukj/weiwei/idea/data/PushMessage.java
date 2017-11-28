package com.dongwukj.weiwei.idea.data;

import com.litesuits.orm.db.annotation.Mapping;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;

import java.util.Date;

/**
 * Created by pc on 2015/5/6.
 * 推送消息的实体
 *
 */

@Table("pushMessage")
public class PushMessage {

    @PrimaryKey(PrimaryKey.AssignType.AUTO_INCREMENT)
    private long id;
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

    @Mapping(Mapping.Relation.OneToOne)
    private BusinessContent businessContent;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public BusinessContent getBusinessContent() {
        return businessContent;
    }

    public void setBusinessContent(BusinessContent businessContent) {
        this.businessContent = businessContent;
    }



    public PushMessageData getData(){
        PushMessageData data=new PushMessageData();
        if(getBusinessContent()!=null){
            data.setBusinessId(getBusinessContent().getBusinessId());
            data.setBusinessUrl(getBusinessContent().getBusinessUrl());
            data.setMsg(getBusinessContent().getMsg());
            data.setTitle(getBusinessContent().getTitle());
        }
        data.setMsgType(msgType);
        data.setCreateTime(createTime);
        data.setBusinessType(businessType);

        return data;
    }
}
