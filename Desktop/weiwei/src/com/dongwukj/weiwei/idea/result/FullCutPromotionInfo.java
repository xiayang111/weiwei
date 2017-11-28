package com.dongwukj.weiwei.idea.result;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;

/**
 * Created by sunjaly on 15/3/31.
 */

@Table("fullCutPromotionInfo")
public class FullCutPromotionInfo{

    @PrimaryKey(PrimaryKey.AssignType.AUTO_INCREMENT)
    private long id;

    private Float CutMoney1;
    private Float CutMoney2;
    private Float CutMoney3;
    private String EndTime;
    private Float LimitMoney1;
    private Float LimitMoney2;
    private Float LimitMoney3;
    private String Name;
    private Integer PmId;
    private String StartTime;
    private Integer State;
    private Integer Type;
    private Integer UserRankLower;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Float getCutMoney1() {
        return CutMoney1;
    }

    public void setCutMoney1(Float cutMoney1) {
        CutMoney1 = cutMoney1;
    }

    public Float getCutMoney2() {
        return CutMoney2;
    }

    public void setCutMoney2(Float cutMoney2) {
        CutMoney2 = cutMoney2;
    }

    public Float getCutMoney3() {
        return CutMoney3;
    }

    public void setCutMoney3(Float cutMoney3) {
        CutMoney3 = cutMoney3;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public Float getLimitMoney1() {
        return LimitMoney1;
    }

    public void setLimitMoney1(Float limitMoney1) {
        LimitMoney1 = limitMoney1;
    }

    public Float getLimitMoney2() {
        return LimitMoney2;
    }

    public void setLimitMoney2(Float limitMoney2) {
        LimitMoney2 = limitMoney2;
    }

    public Float getLimitMoney3() {
        return LimitMoney3;
    }

    public void setLimitMoney3(Float limitMoney3) {
        LimitMoney3 = limitMoney3;
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

    public Integer getType() {
        return Type;
    }

    public void setType(Integer type) {
        Type = type;
    }

    public Integer getUserRankLower() {
        return UserRankLower;
    }

    public void setUserRankLower(Integer userRankLower) {
        UserRankLower = userRankLower;
    }
}
