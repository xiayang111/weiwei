package com.dongwukj.weiwei.idea.result;

import com.litesuits.orm.db.annotation.Mapping;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;

import java.util.ArrayList;

/**
 * Created by sunjaly on 15/3/31.
 */

@Table("cartFullCutEntity")
public class CartFullCutEntity{

    @PrimaryKey(PrimaryKey.AssignType.AUTO_INCREMENT)
    private long id;

    private Float CutMoney;


    @Mapping(Mapping.Relation.OneToMany)
    private ArrayList<CartProductEntity> FullCutCartProductList;

    @Mapping(Mapping.Relation.OneToOne)
    private FullCutPromotionInfo FullCutPromotionInfo;

    private Boolean IsEnough;
    private Integer LimitMoney;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public Float getCutMoney() {
        return CutMoney;
    }

    public void setCutMoney(Float cutMoney) {
        CutMoney = cutMoney;
    }

    public ArrayList<CartProductEntity> getFullCutCartProductList() {
        return FullCutCartProductList;
    }

    public void setFullCutCartProductList(ArrayList<CartProductEntity> fullCutCartProductList) {
        FullCutCartProductList = fullCutCartProductList;
    }

    public FullCutPromotionInfo getFullCutPromotionInfo() {
        return FullCutPromotionInfo;
    }

    public void setFullCutPromotionInfo(FullCutPromotionInfo fullCutPromotionInfo) {
        FullCutPromotionInfo = fullCutPromotionInfo;
    }

    public Boolean getIsEnough() {
        return IsEnough;
    }

    public void setIsEnough(Boolean isEnough) {
        IsEnough = isEnough;
    }

    public Integer getLimitMoney() {
        return LimitMoney;
    }

    public void setLimitMoney(Integer limitMoney) {
        LimitMoney = limitMoney;
    }
}
