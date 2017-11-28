package com.dongwukj.weiwei.idea.result;

/**
 * Created by pc on 2015/3/12.
 */
public class HomeProduceEntity {

    private Integer id;
    private String title;
    private String description;
    private String url;
    private Float currentPrice;
    private Float prePrice;
    private String sales;
    private Boolean checked=false;
    
    

    public void setChecked(Boolean checked) {
		this.checked = checked;
	}

    public Boolean getChecked() {
		return checked;
	}
    
    public String getSales() {
		return sales;
	}

	public void setSales(String sales) {
		this.sales = sales;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Float getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Float currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Float getPrePrice() {
        return prePrice;
    }

    public void setPrePrice(Float prePrice) {
        this.prePrice = prePrice;
    }
}
