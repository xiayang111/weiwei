package com.dongwukj.weiwei.idea.result;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JpushMessageEntity {

	private int jpushid;
	private int businesstype;
	private String businessurl;
	private int busunessid;
	private String content;
	private int msgtype;
	private String platform;
	private String sendtime;
	private String title;
	private int type;
	public int getJpushid() {
		return jpushid;
	}
	public void setJpushid(int jpushid) {
		this.jpushid = jpushid;
	}
	public int getBusinesstype() {
		return businesstype;
	}
	public void setBusinesstype(int businesstype) {
		this.businesstype = businesstype;
	}
	public String getBusinessurl() {
		return businessurl;
	}
	public void setBusinessurl(String businessurl) {
		this.businessurl = businessurl;
	}
	public int getBusunessid() {
		return busunessid;
	}
	public void setBusunessid(int busunessid) {
		this.busunessid = busunessid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(int msgtype) {
		this.msgtype = msgtype;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getSendtime() {
		return sendtime;
	}
	public void setSendtime(String sendtime) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(sendtime);
        if(matcher.find()){
            this.sendtime=matcher.group();
            return;
        }
        this.sendtime = sendtime;
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
	

}
