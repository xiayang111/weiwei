package com.dongwukj.weiwei.idea.result;

public class PresentEntity {

	private int url ;  			//商品id
	private String body ; 		//商品图片
	private String extfield1 ;  //商品名称
	private String extfield2;   //原价
	private String extfield3;   //现价
	private String extfield4;   //商品规格
	
	private String listCount;   //总行数
	private int addNumber;      //每页行数
	private int adPosId;        //广告位置id
	private String title;       //标题
	private int type;			//类型
	public int getUrl() {
		return url;
	}
	public void setUrl(int url) {
		this.url = url;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getExtfield1() {
		return extfield1;
	}
	public void setExtfield1(String extfield1) {
		this.extfield1 = extfield1;
	}
	public String getExtfield2() {
		return extfield2;
	}
	public void setExtfield2(String extfield2) {
		this.extfield2 = extfield2;
	}
	public String getExtfield3() {
		return extfield3;
	}
	public void setExtfield3(String extfield3) {
		this.extfield3 = extfield3;
	}
	public String getExtfield4() {
		return extfield4;
	}
	public void setExtfield4(String extfield4) {
		this.extfield4 = extfield4;
	}
	public String getListCount() {
		return listCount;
	}
	public void setListCount(String listCount) {
		this.listCount = listCount;
	}
	public int getAddNumber() {
		return addNumber;
	}
	public void setAddNumber(int addNumber) {
		this.addNumber = addNumber;
	}
	public int getAdPosId() {
		return adPosId;
	}
	public void setAdPosId(int adPosId) {
		this.adPosId = adPosId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public PresentEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PresentEntity(int url, String body, String extfield1,
			String extfield2, String extfield3, String extfield4,
			String listCount, int addNumber, int adPosId, String title, int type) {
		super();
		this.url = url;
		this.body = body;
		this.extfield1 = extfield1;
		this.extfield2 = extfield2;
		this.extfield3 = extfield3;
		this.extfield4 = extfield4;
		this.listCount = listCount;
		this.addNumber = addNumber;
		this.adPosId = adPosId;
		this.title = title;
		this.type = type;
	}
	@Override
	public String toString() {
		return "PresentEntity [url=" + url + ", body=" + body + ", extfield1="
				+ extfield1 + ", extfield2=" + extfield2 + ", extfield3="
				+ extfield3 + ", extfield4=" + extfield4 + ", listCount="
				+ listCount + ", addNumber=" + addNumber + ", adPosId="
				+ adPosId + ", title=" + title + ", type=" + type + "]";
	}
	
	
	
	
}
