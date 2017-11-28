package com.dongwukj.weiwei.idea.result;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;

/**
 * Created by sunjaly on 15/3/30.
 */

@Table("orderProductInfo")
public class OrderProductInfo implements Serializable{

    @PrimaryKey(PrimaryKey.AssignType.AUTO_INCREMENT)
    private long id;

    private Integer RecordId;//记录id
    private Integer Oid;//订单id
    private Integer Uid;//用户id
    private String Sid;
    private Integer Pid;//商品id
    private String PSN;//商品编码
    private Integer CateId;//分类id
    private Integer BrandId;//品牌id
    private String Name;//商品名称
    private String ShowImg;//商品展示图片
    private Float DiscountPrice;//商品折扣价格
    private Float ShopPrice;	//商品商城价格
    private Float CostPrice;	//商品成本价格
    private Float MarketPrice;	//商品市场价格
    private Integer Weight;	//商品重量
    private Integer IsReview;//是否评价
    private Integer RealCount;//真实数量
    private Integer BuyCount;//商品购买数量
    private Integer SendCount;//商品邮寄数量
    private Integer Type;//商品类型(0为普遍商品,1为普通商品赠品,2为套装商品赠品,3为套装商品,4满赠商品)
    private Integer PayCredits;//支付积分
    private Integer CouponTypeId;//赠送优惠劵类型id
    private Integer ExtCode1;//普通商品时为单品促销活动id,赠品时为赠品促销活动id,套装商品时为套装促销活动id,满赠赠品时为满赠促销活动id
    private Integer ExtCode2;//普通商品时为买送促销活动id,赠品时为赠品赠送数量,套装商品时为套装商品数量
    private Integer ExtCode3;//普通商品时为赠品促销活动id,套装商品时为赠品促销活动id
    private Integer ExtCode4;//普通商品时为满赠促销活动id
    private Integer ExtCode5;//普通商品时为满减促销活动id
    private String AddTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getRecordId() {
		return RecordId;
	}
	public void setRecordId(Integer recordId) {
		RecordId = recordId;
	}
	public Integer getOid() {
		return Oid;
	}
	public void setOid(Integer oid) {
		Oid = oid;
	}
	public Integer getUid() {
		return Uid;
	}
	public void setUid(Integer uid) {
		Uid = uid;
	}
	public String getSid() {
		return Sid;
	}
	public void setSid(String sid) {
		Sid = sid;
	}
	public Integer getPid() {
		return Pid;
	}
	public void setPid(Integer pid) {
		Pid = pid;
	}
	public String getPSN() {
		return PSN;
	}
	public void setPSN(String pSN) {
		PSN = pSN;
	}
	public Integer getCateId() {
		return CateId;
	}
	public void setCateId(Integer cateId) {
		CateId = cateId;
	}
	public Integer getBrandId() {
		return BrandId;
	}
	public void setBrandId(Integer brandId) {
		BrandId = brandId;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getShowImg() {
		return ShowImg;
	}
	public void setShowImg(String showImg) {
		ShowImg = showImg;
	}
	public Float getDiscountPrice() {
		return DiscountPrice;
	}
	public void setDiscountPrice(Float discountPrice) {
		DiscountPrice = discountPrice;
	}
	public Float getShopPrice() {
		return ShopPrice;
	}
	public void setShopPrice(Float shopPrice) {
		ShopPrice = shopPrice;
	}
	public Float getCostPrice() {
		return CostPrice;
	}
	public void setCostPrice(Float costPrice) {
		CostPrice = costPrice;
	}
	public Float getMarketPrice() {
		return MarketPrice;
	}
	public void setMarketPrice(Float marketPrice) {
		MarketPrice = marketPrice;
	}
	public Integer getWeight() {
		return Weight;
	}
	public void setWeight(Integer weight) {
		Weight = weight;
	}
	public Integer getIsReview() {
		return IsReview;
	}
	public void setIsReview(Integer isReview) {
		IsReview = isReview;
	}
	public Integer getRealCount() {
		return RealCount;
	}
	public void setRealCount(Integer realCount) {
		RealCount = realCount;
	}
	public Integer getBuyCount() {
		return BuyCount;
	}
	public void setBuyCount(Integer buyCount) {
		BuyCount = buyCount;
	}
	public Integer getSendCount() {
		return SendCount;
	}
	public void setSendCount(Integer sendCount) {
		SendCount = sendCount;
	}
	public Integer getType() {
		return Type;
	}
	public void setType(Integer type) {
		Type = type;
	}
	public Integer getPayCredits() {
		return PayCredits;
	}
	public void setPayCredits(Integer payCredits) {
		PayCredits = payCredits;
	}
	public Integer getCouponTypeId() {
		return CouponTypeId;
	}
	public void setCouponTypeId(Integer couponTypeId) {
		CouponTypeId = couponTypeId;
	}
	public Integer getExtCode1() {
		return ExtCode1;
	}
	public void setExtCode1(Integer extCode1) {
		ExtCode1 = extCode1;
	}
	public Integer getExtCode2() {
		return ExtCode2;
	}
	public void setExtCode2(Integer extCode2) {
		ExtCode2 = extCode2;
	}
	public Integer getExtCode3() {
		return ExtCode3;
	}
	public void setExtCode3(Integer extCode3) {
		ExtCode3 = extCode3;
	}
	public Integer getExtCode4() {
		return ExtCode4;
	}
	public void setExtCode4(Integer extCode4) {
		ExtCode4 = extCode4;
	}
	public Integer getExtCode5() {
		return ExtCode5;
	}
	public void setExtCode5(Integer extCode5) {
		ExtCode5 = extCode5;
	}
	public String getAddTime() {
		return AddTime;
	}
	public void setAddTime(String addTime) {
		AddTime = addTime;
	}
	public OrderProductInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrderProductInfo(Integer recordId, Integer oid, Integer uid,
			String sid, Integer pid, String pSN, Integer cateId,
			Integer brandId, String name, String showImg, Float discountPrice,
			Float shopPrice, Float costPrice, Float marketPrice,
			Integer weight, Integer isReview, Integer realCount,
			Integer buyCount, Integer sendCount, Integer type,
			Integer payCredits, Integer couponTypeId, Integer extCode1,
			Integer extCode2, Integer extCode3, Integer extCode4,
			Integer extCode5, String addTime) {
		super();
		RecordId = recordId;
		Oid = oid;
		Uid = uid;
		Sid = sid;
		Pid = pid;
		PSN = pSN;
		CateId = cateId;
		BrandId = brandId;
		Name = name;
		ShowImg = showImg;
		DiscountPrice = discountPrice;
		ShopPrice = shopPrice;
		CostPrice = costPrice;
		MarketPrice = marketPrice;
		Weight = weight;
		IsReview = isReview;
		RealCount = realCount;
		BuyCount = buyCount;
		SendCount = sendCount;
		Type = type;
		PayCredits = payCredits;
		CouponTypeId = couponTypeId;
		ExtCode1 = extCode1;
		ExtCode2 = extCode2;
		ExtCode3 = extCode3;
		ExtCode4 = extCode4;
		ExtCode5 = extCode5;
		AddTime = addTime;
	}
	@Override
	public String toString() {
		return "OrderProductInfo [RecordId=" + RecordId + ", Oid=" + Oid
				+ ", Uid=" + Uid + ", Sid=" + Sid + ", Pid=" + Pid + ", PSN="
				+ PSN + ", CateId=" + CateId + ", BrandId=" + BrandId
				+ ", Name=" + Name + ", ShowImg=" + ShowImg
				+ ", DiscountPrice=" + DiscountPrice + ", ShopPrice="
				+ ShopPrice + ", CostPrice=" + CostPrice + ", MarketPrice="
				+ MarketPrice + ", Weight=" + Weight + ", IsReview=" + IsReview
				+ ", RealCount=" + RealCount + ", BuyCount=" + BuyCount
				+ ", SendCount=" + SendCount + ", Type=" + Type
				+ ", PayCredits=" + PayCredits + ", CouponTypeId="
				+ CouponTypeId + ", ExtCode1=" + ExtCode1 + ", ExtCode2="
				+ ExtCode2 + ", ExtCode3=" + ExtCode3 + ", ExtCode4="
				+ ExtCode4 + ", ExtCode5=" + ExtCode5 + ", AddTime=" + AddTime
				+ "]";
	}
	
    
    
    

}
