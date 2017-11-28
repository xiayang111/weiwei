package com.dongwukj.weiwei.idea.result;

import android.R.integer;
import net.tsz.afinal.annotation.sqlite.Id;

/**
 * Created by sunjaly on 15/3/23.
 */
public class LoginUserResult extends BaseResult{
	@Id(column="User_Id")
	private int userId;
    
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	private String Avatar;
	private String NickName;
	private int isByMoney;
	private String userAccount;
    private String tokenId;
    private boolean isLogin=false;
    private String level;
    private String tel;
    private int plotid;
    private int defaultSaId;
    private Market market;
    
	

	public Market getMarket() {
		return market;
	}

	public void setMarket(Market market) {
		this.market = market;
	}

	public int getDefaultSaId() {
		return defaultSaId;
	}

	public void setDefaultSaId(int defaultSaId) {
		this.defaultSaId = defaultSaId;
	}

	public int getPlotid() {
	return plotid;
}

	public void setPlotid(int plotid) {
	this.plotid = plotid;
}

	public String getAvatar() {
		return Avatar;
	}

	public void setAvatar(String avatar) {
		Avatar = avatar;
	}

	public String getNickName() {
		return NickName;
	}

	public void setNickName(String nickName) {
		NickName = nickName;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }


	public int getIsByMoney() {
		return isByMoney;
	}

	public void setIsByMoney(int isByMoney) {
		this.isByMoney = isByMoney;
	}

	
	
	
    
}
