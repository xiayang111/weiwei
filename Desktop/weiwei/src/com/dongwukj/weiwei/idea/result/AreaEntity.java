package com.dongwukj.weiwei.idea.result;

public class AreaEntity {

	private int id;
	private String province;
	private String city;
	private String area;
	
	private String street;
	private String streetnumber;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	

	
	
	
	
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getStreetnumber() {
		return streetnumber;
	}
	public void setStreetnumber(String streetnumber) {
		this.streetnumber = streetnumber;
	}
	private AreaEntity() {
		
	}
	private static AreaEntity areaEntity = new AreaEntity();
	
	public static AreaEntity getInstance(){
		return areaEntity;
	}
	
	public AreaEntity(int id, String province, String city, String area,String street,String streetnumber) {
		super();
		this.id = id;
		this.province = province;
		this.city = city;
		this.area = area;
		this.street = street;
		this.streetnumber = streetnumber;
	}
	@Override
	public String toString() {
		return "AreaEntity [id=" + id + ", province=" + province + ", city="
				+ city + ", area=" + area + ", street=" + street
				+ ", streetnumber=" + streetnumber + "]";
	}
	
/*	public AreaEntity(int id, String province, String city, String area) {
		super();
		this.id = id;
		this.province = province;
		this.city = city;
		this.area = area;
	}*/
/*	@Override
	public String toString() {
		return "AreaEntity [id=" + id + ", province=" + province + ", city="
				+ city + ", area=" + area + "]";
	}*/
	
	
	
	
}
