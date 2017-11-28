package com.dongwukj.weiwei.idea.data;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;

/**
 * Created by pc on 2015/5/6.
 *
 * 消息内容体
 */
@Table("businessContent")
public class BusinessContent {
    @PrimaryKey(PrimaryKey.AssignType.AUTO_INCREMENT)
    private long id;

    private String title;
    private String msg;
    private int businessId;
    private String businessUrl;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
