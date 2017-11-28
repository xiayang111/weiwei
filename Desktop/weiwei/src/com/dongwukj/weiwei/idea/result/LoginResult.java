package com.dongwukj.weiwei.idea.result;

/**
 * Created by pc on 2015/3/12.
 */
public class LoginResult extends BaseResult{

    private String key;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
