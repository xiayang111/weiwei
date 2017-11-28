package com.dongwukj.weiwei.idea.request;

/**
 * Created by pc on 2015/3/12.
 */
public class LoginEntity extends BaseRequest{
    //private User login;


    private String userPassword;

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

//    public User getLogin() {
//        return login;
//    }
//
//    public void setLogin(User login) {
//        this.login = login;
//    }
//
//    public LoginEntity(User login) {
//        this.login = login;
//    }
}
