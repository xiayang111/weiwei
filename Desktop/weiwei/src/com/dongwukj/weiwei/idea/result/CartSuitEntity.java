package com.dongwukj.weiwei.idea.result;

import com.litesuits.orm.db.annotation.Mapping;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;

import java.util.ArrayList;

import net.tsz.afinal.annotation.sqlite.ManyToOne;

/**
 * Created by sunjaly on 15/3/31.
 */

@Table("cartSuitEntity")
public class CartSuitEntity {

    @PrimaryKey(PrimaryKey.AssignType.AUTO_INCREMENT)
    private long id;

    private Integer BuyCount;

    @Mapping(Mapping.Relation.OneToMany)
    private ArrayList<CartProductEntity> CartProductList;
    
    private Boolean Checked;
    private Integer PmId;
    private Integer SuitAmount;
    private Float SuitPrice;
    @Mapping(Mapping.Relation.OneToOne)
    private SuitPromotion SuitPromotion;
    
    public SuitPromotion getSuitPromotion() {
		return SuitPromotion;
	}

	public void setSuitPromotion(SuitPromotion suitPromotion) {
		SuitPromotion = suitPromotion;
	}

	private Boolean deleteChecked=false;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Boolean getDeleteChecked() {
        return deleteChecked;
    }

    public void setDeleteChecked(Boolean deleteChecked) {
        this.deleteChecked = deleteChecked;
    }

    public Integer getBuyCount() {
        return BuyCount;
    }

    public void setBuyCount(Integer buyCount) {
        BuyCount = buyCount;
    }

    public ArrayList<CartProductEntity> getCartProductList() {
        return CartProductList;
    }

    public void setCartProductList(ArrayList<CartProductEntity> cartProductList) {
        CartProductList = cartProductList;
    }

    public Boolean getChecked() {
        return Checked;
    }

    public void setChecked(Boolean checked) {
        Checked = checked;
    }

    public Integer getPmId() {
        return PmId;
    }

    public void setPmId(Integer pmId) {
        PmId = pmId;
    }

    public Integer getSuitAmount() {
        return SuitAmount;
    }

    public void setSuitAmount(Integer suitAmount) {
        SuitAmount = suitAmount;
    }

    public Float getSuitPrice() {
        return SuitPrice;
    }

    public void setSuitPrice(Float suitPrice) {
        SuitPrice = suitPrice;
    }
}
