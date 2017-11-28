package com.dongwukj.weiwei.idea.result;

import java.io.Serializable;

import com.litesuits.orm.db.annotation.Mapping;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;

/**
 * Created by sunjaly on 15/3/31.
 */

@Table("cartItemEntity")
public class CartItemEntity  implements Serializable{

    @PrimaryKey(PrimaryKey.AssignType.AUTO_INCREMENT)
    private long id;

    private Integer Type;


    @Mapping(Mapping.Relation.OneToOne)
    private CartFullCutEntity cartFullCut;

    @Mapping(Mapping.Relation.OneToOne)
    private CartFullSendEntity cartFullSend;


    @Mapping(Mapping.Relation.OneToOne)
    private CartProductEntity cartProduct;

    @Mapping(Mapping.Relation.OneToOne)
    private CartSuitEntity cartSuit;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getType() {
        return Type;
    }

    public void setType(Integer type) {
        Type = type;
    }

    public CartFullCutEntity getCartFullCut() {
        if(cartFullCut==null){
            return new CartFullCutEntity();
        }
        return cartFullCut;
    }

    public void setCartFullCut(CartFullCutEntity cartFullCut) {
        this.cartFullCut = cartFullCut;
    }

    public CartFullSendEntity getCartFullSend() {
        if(cartFullSend==null){
            return new CartFullSendEntity();
        }
        return cartFullSend;
    }

    public void setCartFullSend(CartFullSendEntity cartFullSend) {
        this.cartFullSend = cartFullSend;
    }

    public CartProductEntity getCartProduct() {
        return cartProduct;
    }

    public void setCartProduct(CartProductEntity cartProduct) {
        this.cartProduct = cartProduct;
    }

    public CartSuitEntity getCartSuit() {
        return cartSuit;
    }

    public void setCartSuit(CartSuitEntity cartSuit) {
        this.cartSuit = cartSuit;
    }


}
