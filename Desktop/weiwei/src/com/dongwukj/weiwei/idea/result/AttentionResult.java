package com.dongwukj.weiwei.idea.result;

import java.util.ArrayList;

/**
 * Created by sunjaly on 15/3/24.
 */
public class AttentionResult extends  BaseResult{

    private Integer listNumber;
    private ArrayList<AttentionEntity> attentions;

    public Integer getListNumber() {
        return listNumber;
    }

    public void setListNumber(Integer listNumber) {
        this.listNumber = listNumber;
    }

    public ArrayList<AttentionEntity> getAttentions() {
        return attentions;
    }

    public void setAttentions(ArrayList<AttentionEntity> attentions) {
        this.attentions = attentions;
    }
}
