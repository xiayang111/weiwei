package com.dongwukj.weiwei.idea.result;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sunjaly on 15/4/2.
 */
public class ProductReview {
    private String avatar;
    private String buyTime;
    private String message;
    private String nickName;
    private Integer oId;
    private Integer oprId;
    private Integer pId;
    private Integer parentId;
    private Integer quality;
    private Integer reviewId;
    private String reviewTime;
    private Integer star;
    private Integer state;
    private String title;
    private Integer uId;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(String buyTime) {
        this.buyTime = buyTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getoId() {
        return oId;
    }

    public void setoId(Integer oId) {
        this.oId = oId;
    }

    public Integer getOprId() {
        return oprId;
    }

    public void setOprId(Integer oprId) {
        this.oprId = oprId;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getQuality() {
        return quality;
    }

    public void setQuality(Integer quality) {
        this.quality = quality;
    }

    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public String getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(String reviewTime) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(reviewTime);
        if(matcher.find()){
            String time=matcher.group();
            if(!TextUtils.isEmpty(time)){
                Date date=new Date(Long.decode(time));
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                if(date!=null){
                    this.reviewTime=sdf.format(date);
                    return;
                }

            }
        }
        this.reviewTime = reviewTime;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }
}
