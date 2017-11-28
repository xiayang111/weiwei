package com.dongwukj.weiwei.idea.result;

import java.util.ArrayList;
import java.util.List;

import com.dongwukj.weiwei.idea.request.PurchaseEntity;

/**
 * Created by sunjaly on 15/3/23.
 * 首页请求结果
 */
public class HomeResult extends BaseResult {

    private ArrayList<AdEntity> bannersList;
    private int withGiftId;
    private String withGiftText;
    private String withGiftImg;
    private int desenoId;
    private String desenoText;
    private String desenoImg;
    private int referralsId;
    private String referralsText;
    private String referralsImg;
 
    
    private PurchaseEntity phoneReferrals;
//    private FullGiftEntity fullGiftEntity;
//    private FullGiftEntity limitBuy;
//    private FullGiftEntity recommended;
    private ArrayList<FullGiftEntity> hotList;
    private List<FullGiftEntity> referralsList;
    private int catcount;
    
 
	public int getCatcount() {
		return catcount;
	}

	public void setCatcount(int catcount) {
		this.catcount = catcount;
	}

	public List<FullGiftEntity> getReferralsList() {
		return referralsList;
	}

	public void setReferralsList(List<FullGiftEntity> referralsList) {
		this.referralsList = referralsList;
	}

	public PurchaseEntity getPhoneReferrals() {
		return phoneReferrals;
	}

	public void setPhoneReferrals(PurchaseEntity phoneReferrals) {
		this.phoneReferrals = phoneReferrals;
	}

	private Integer listCount;
    private Integer addNumber;
    
    
    

    public int getWithGiftId() {
		return withGiftId;
	}

	public void setWithGiftId(int withGiftId) {
		this.withGiftId = withGiftId;
	}

	public String getWithGiftText() {
		return withGiftText;
	}

	public void setWithGiftText(String withGiftText) {
		this.withGiftText = withGiftText;
	}

	public String getWithGiftImg() {
		return withGiftImg;
	}

	public void setWithGiftImg(String withGiftImg) {
		this.withGiftImg = withGiftImg;
	}

	public int getDesenoId() {
		return desenoId;
	}

	public void setDesenoId(int desenoId) {
		this.desenoId = desenoId;
	}

	public String getDesenoText() {
		return desenoText;
	}

	public void setDesenoText(String desenoText) {
		this.desenoText = desenoText;
	}

	public String getDesenoImg() {
		return desenoImg;
	}

	public void setDesenoImg(String desenoImg) {
		this.desenoImg = desenoImg;
	}

	public int getReferralsId() {
		return referralsId;
	}

	public void setReferralsId(int referralsId) {
		this.referralsId = referralsId;
	}

	public String getReferralsText() {
		return referralsText;
	}

	public void setReferralsText(String referralsText) {
		this.referralsText = referralsText;
	}

	public String getReferralsImg() {
		return referralsImg;
	}

	public void setReferralsImg(String referralsImg) {
		this.referralsImg = referralsImg;
	}

	public ArrayList<AdEntity> getBannersList() {
        return bannersList;
    }

    public void setBannersList(ArrayList<AdEntity> bannersList) {
        this.bannersList = bannersList;
    }

//    public FullGiftEntity getFullGiftEntity() {
//        return fullGiftEntity;
//    }
//
//    public void setFullGiftEntity(FullGiftEntity fullGiftEntity) {
//        this.fullGiftEntity = fullGiftEntity;
//    }
//
//    public FullGiftEntity getLimitBuy() {
//        return limitBuy;
//    }
//
//    public void setLimitBuy(FullGiftEntity limitBuy) {
//        this.limitBuy = limitBuy;
//    }
//
//    public FullGiftEntity getRecommended() {
//        return recommended;
//    }
//
//    public void setRecommended(FullGiftEntity recommended) {
//        this.recommended = recommended;
//    }

    public ArrayList<FullGiftEntity> getHotList() {
        return hotList;
    }

    public void setHotList(ArrayList<FullGiftEntity> hotList) {
        this.hotList = hotList;
    }

    public Integer getListCount() {
        return listCount;
    }

    public void setListCount(Integer listCount) {
        this.listCount = listCount;
    }

    public Integer getAddNumber() {
        return addNumber;
    }

    public void setAddNumber(Integer addNumber) {
        this.addNumber = addNumber;
    }

	@Override
	public String toString() {
		return "HomeResult [bannersList=" + bannersList + ", withGiftId="
				+ withGiftId + ", withGiftText=" + withGiftText
				+ ", withGiftImg=" + withGiftImg + ", desenoId=" + desenoId
				+ ", desenoText=" + desenoText + ", desenoImg=" + desenoImg
				+ ", referralsId=" + referralsId + ", referralsText="
				+ referralsText + ", referralsImg=" + referralsImg
				+ ", phoneReferrals=" + phoneReferrals + ", hotList=" + hotList
				+ ", listCount=" + listCount + ", addNumber=" + addNumber + "]";
	}

	
	
    
}
