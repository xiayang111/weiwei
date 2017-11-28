package com.dongwukj.weiwei.idea.result;

import com.litesuits.orm.db.annotation.Mapping;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;

import java.util.ArrayList;

/**
 * Created by sunjaly on 15/3/31.
 */

@Table("cartProductEntity")
public class CartProductEntity {

    @PrimaryKey(PrimaryKey.AssignType.AUTO_INCREMENT)
    private long id;

    private ArrayList<String> GiftList;

    @Mapping(Mapping.Relation.OneToOne)
    private OrderProductInfo OrderProductInfo;

    private Boolean Selected;
    private Boolean deleteSelected=false;
    @Mapping(Mapping.Relation.OneToOne)
    private SinglePromotion SinglePromotion;
    
   
    
	public SinglePromotion getSinglePromotion() {
		return SinglePromotion;
	}

	public void setSinglePromotion(SinglePromotion singlePromotion) {
		SinglePromotion = singlePromotion;
	}

	public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Boolean getDeleteSelected() {
        return deleteSelected;
    }

    public void setDeleteSelected(Boolean deleteSelected) {
        this.deleteSelected = deleteSelected;
    }

    public ArrayList<String> getGiftList() {
        return GiftList;
    }

    public void setGiftList(ArrayList<String> giftList) {
        GiftList = giftList;
    }

    public OrderProductInfo getOrderProductInfo() {
        return OrderProductInfo;
    }

    public void setOrderProductInfo(OrderProductInfo orderProductInfo) {
        OrderProductInfo = orderProductInfo;
    }

    public Boolean getSelected() {
        return Selected;
    }

    public void setSelected(Boolean selected) {
        Selected = selected;
    }
}
