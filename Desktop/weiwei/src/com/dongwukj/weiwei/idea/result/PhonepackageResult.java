package com.dongwukj.weiwei.idea.result;

import java.util.ArrayList;

/**
 * Created by sunjaly on 15/3/30.
 */
public class PhonepackageResult extends BaseResult {
    private Integer listNumber;
    private ArrayList<PhonepackageEntity> packages;

    public Integer getListNumber() {
        return listNumber;
    }

    public void setListNumber(Integer listNumber) {
        this.listNumber = listNumber;
    }

    public ArrayList<PhonepackageEntity> getPackages() {
        return packages;
    }

    public void setPackages(ArrayList<PhonepackageEntity> packages) {
        this.packages = packages;
    }
}
