package com.dongwukj.weiwei.idea.result;

import com.litesuits.orm.db.annotation.Mapping;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;

import java.util.ArrayList;

/**
 * Created by sunjaly on 15/3/31.
 */

@Table("cartFullSendEntity")
public class CartFullSendEntity {

    @PrimaryKey(PrimaryKey.AssignType.AUTO_INCREMENT)
    private long id;

    @Mapping(Mapping.Relation.OneToMany)
    private ArrayList<CartProductEntity> FullSendMainCartProductList;

    private String FullSendMinorOrderProductInfo;

    @Mapping(Mapping.Relation.OneToOne)
    private FullSendPromotionInfo FullSendPromotionInfo;

    private Boolean IsEnough;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public FullSendPromotionInfo getFullSendPromotionInfo() {
        return FullSendPromotionInfo;
    }

    public void setFullSendPromotionInfo(FullSendPromotionInfo fullSendPromotionInfo) {
        FullSendPromotionInfo = fullSendPromotionInfo;
    }

    public Boolean getIsEnough() {
        return IsEnough;
    }

    public void setIsEnough(Boolean isEnough) {
        IsEnough = isEnough;
    }

    public ArrayList<CartProductEntity> getFullSendMainCartProductList() {
        return FullSendMainCartProductList;
    }

    public void setFullSendMainCartProductList(ArrayList<CartProductEntity> fullSendMainCartProductList) {
        FullSendMainCartProductList = fullSendMainCartProductList;
    }

    public String getFullSendMinorOrderProductInfo() {
        return FullSendMinorOrderProductInfo;
    }

    public void setFullSendMinorOrderProductInfo(String fullSendMinorOrderProductInfo) {
        FullSendMinorOrderProductInfo = fullSendMinorOrderProductInfo;
    }
}
