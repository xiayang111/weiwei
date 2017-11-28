package com.dongwukj.weiwei.idea.request;

public class OrderCancleRequest extends BaseRequest {
	private Integer oid;
	private String  ip;
	public Integer getOid() {
		return oid;
	}
	public void setOid(Integer oid) {
		this.oid = oid;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	@Override
	public String toString() {
		return "OrderCancleRequest [oid=" + oid + ", ip=" + ip + "]";
	}
	
}
