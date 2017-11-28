package com.dongwukj.weiwei.idea.request;

import com.dongwukj.weiwei.idea.result.BaseResult;

/**
 * Created by sunjaly on 15/4/18.
 */
public class PhonepayResult extends BaseResult{

    private Float moratorium;
    private Float probalances;
    private Float backbalances;

    public Float getMoratorium() {
        return moratorium;
    }

    public void setMoratorium(Float moratorium) {
        this.moratorium = moratorium;
    }

    public Float getProbalances() {
        return probalances;
    }

    public void setProbalances(Float probalances) {
        this.probalances = probalances;
    }

    public Float getBackbalances() {
        return backbalances;
    }

    public void setBackbalances(Float backbalances) {
        this.backbalances = backbalances;
    }
}
