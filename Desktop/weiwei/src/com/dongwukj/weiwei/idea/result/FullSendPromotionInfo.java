package com.dongwukj.weiwei.idea.result;

import com.litesuits.orm.db.annotation.PrimaryKey;

/**
 * Created by sunjaly on 15/3/31.
 */
public class FullSendPromotionInfo {

    @PrimaryKey(PrimaryKey.AssignType.AUTO_INCREMENT)
    private long id;
    private Float AddMoney;
    private String EndTime;
    private Float LimitMoney;
    private String Name;
    private Integer PmId;
    private String StartTime;
    private Integer State;
    private Integer UserRankLower;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Float getAddMoney() {
        return AddMoney;
    }

    public void setAddMoney(Float addMoney) {
        AddMoney = addMoney;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public Float getLimitMoney() {
        return LimitMoney;
    }

    public void setLimitMoney(Float limitMoney) {
        LimitMoney = limitMoney;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getPmId() {
        return PmId;
    }

    public void setPmId(Integer pmId) {
        PmId = pmId;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public Integer getState() {
        return State;
    }

    public void setState(Integer state) {
        State = state;
    }

    public Integer getUserRankLower() {
        return UserRankLower;
    }

    public void setUserRankLower(Integer userRankLower) {
        UserRankLower = userRankLower;
    }
}
