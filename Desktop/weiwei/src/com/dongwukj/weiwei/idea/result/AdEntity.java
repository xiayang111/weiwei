package com.dongwukj.weiwei.idea.result;

/**
 * Created by sunjaly on 15/3/23.
 */
public class AdEntity {
    private Integer adPosId;
    private String title;
    private String url;
    private String body;
    private Integer type;
    private int extField1;
    private String extField2;
    private String extField3;
    
    public int getExtField1() {
		return extField1;
	}

	public void setExtField1(int extField1) {
		this.extField1 = extField1;
	}

	public String getExtField2() {
		return extField2;
	}

	public void setExtField2(String extField2) {
		this.extField2 = extField2;
	}

	public String getExtField3() {
		return extField3;
	}

	public void setExtField3(String extField3) {
		this.extField3 = extField3;
	}

	public Integer getAdPosId() {
        return adPosId;
    }

    public void setAdPosId(Integer adPosId) {
        this.adPosId = adPosId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

	@Override
	public String toString() {
		return "AdEntity [adPosId=" + adPosId + ", title=" + title + ", url="
				+ url + ", body=" + body + ", type=" + type + "]";
	}
    
}
