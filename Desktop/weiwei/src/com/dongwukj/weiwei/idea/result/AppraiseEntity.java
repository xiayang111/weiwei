package com.dongwukj.weiwei.idea.result;

import java.util.ArrayList;

/**
 * Created by pc on 2015/3/17.
 */
public class AppraiseEntity {
    private String userName;
    private String userId;
    private String userIcon;
    private String appraiseContent;
    private ArrayList<String> appraiseImages;


    public ArrayList<String> getAppraiseImages() {
        return appraiseImages;
    }

    public void setAppraiseImages(ArrayList<String> appraiseImages) {
        this.appraiseImages = appraiseImages;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getAppraiseContent() {
        return appraiseContent;
    }

    public void setAppraiseContent(String appraiseContent) {
        this.appraiseContent = appraiseContent;
    }
}
