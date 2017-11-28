package com.dongwukj.weiwei.idea.result;

import java.io.Serializable;
import java.util.Date;

/**
 * apk更新信息
 * @author： aokunsang
 * @date： 2012-12-18
 */
public class ApkInfo extends BaseResult implements Serializable {

	
    private int id;

    /**
	 * 
	 */

	private String downloadUrl;  //下载地址
	private String apkVersion;  //apk版本
	private String apkSize;    //apk文件大小
	private int apkCode;   //apk版本号(更新必备)
	private String apkName;  //apk名字
	private String apkLog;   //apk更新日志
    private String apkPath; //apk保存路径
   // private boolean Update;
    private boolean isUpdate;
    private long date;

    private boolean isForce;



    public ApkInfo(){

    }

  

	 



	public boolean getIsUpdate() {
		return isUpdate;
	}







	public void setIsUpdate(boolean isUpdate) {
		this.isUpdate = isUpdate;
	}







	public ApkInfo(String downloadUrl, String apkVersion, String apkSize, int apkCode, String apkName, String apkLog) {
        this.downloadUrl = downloadUrl;
        this.apkVersion = apkVersion;
        this.apkSize = apkSize;
        this.apkCode = apkCode;
        this.apkName = apkName;
        this.apkLog = apkLog;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getIsForce() {
        return isForce;
    }

    public void setIsForce(boolean isForce) {
        this.isForce = isForce;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getApkPath() {
        return apkPath;
    }

    public void setApkPath(String apkPath) {
        this.apkPath = apkPath;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getApkVersion() {
        return apkVersion;
    }

    public void setApkVersion(String apkVersion) {
        this.apkVersion = apkVersion;
    }

    public String getApkSize() {
        return apkSize;
    }

    public void setApkSize(String apkSize) {
        this.apkSize = apkSize;
    }

    public int getApkCode() {
        return apkCode;
    }

    public void setApkCode(int apkCode) {
        this.apkCode = apkCode;
    }

    public String getApkName() {
        return apkName;
    }

    public void setApkName(String apkName) {
        this.apkName = apkName;
    }

    public String getApkLog() {
        return apkLog;
    }

    public void setApkLog(String apkLog) {
        this.apkLog = apkLog;
    }
}