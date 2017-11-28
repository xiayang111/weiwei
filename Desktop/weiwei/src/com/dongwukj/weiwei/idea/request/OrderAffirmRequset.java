package com.dongwukj.weiwei.idea.request;

public class OrderAffirmRequset extends BaseRequest {
	private String ip;
	private Integer oid;
	@Override
	public String toString() {
		return "OrderAffirmRequset [ip=" + ip + ", oid=" + oid + "]";
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Integer getOid() {
		return oid;
	}
	public void setOid(Integer oid) {
		this.oid = oid;
	}
	
}
