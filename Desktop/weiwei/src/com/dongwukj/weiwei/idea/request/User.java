package com.dongwukj.weiwei.idea.request;

/**
 * Created by pc on 2015/3/11.
 */
public class User {

    private String useraccount;
    private String userpassword;

    public User(String accountName, String password) {
        this.useraccount = accountName;
        this.userpassword = password;
    }

    public void setAccountName(String accountName) {
        this.useraccount = accountName;
    }

    public void setPassword(String password) {
        this.userpassword = password;
    }

    public String getAccountName() {
        return useraccount;
    }

    public String getPassword() {
        return userpassword;
    }
}
