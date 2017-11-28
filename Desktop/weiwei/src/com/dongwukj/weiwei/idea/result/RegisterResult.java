package com.dongwukj.weiwei.idea.result;

public class RegisterResult extends BaseResult {

	private String phone_number;
	private String validate_code;
	private String Useraccount; //新返回的用户id
	private String Tokenid;
	private String NickName;
	private String Avatar;
	private String level;
	private int plotid;
	private Market market;
	
	
	public Market getMarket() {
		return market;
	}
	public void setMarket(Market market) {
		this.market = market;
	}
	public String getUseraccount() {
		return Useraccount;
	}
	public void setUseraccount(String useraccount) {
		Useraccount = useraccount;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
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
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public String getValidate_code() {
		return validate_code;
	}
	public void setValidate_code(String validate_code) {
		this.validate_code = validate_code;
	}
	
	public String getTokenid() {
		return Tokenid;
	}
	public void setTokenid(String tokenid) {
		Tokenid = tokenid;
	}
	

	
	public int getPlotid() {
		return plotid;
	}
	public void setPlotid(int plotid) {
		this.plotid = plotid;
	}
	public RegisterResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
