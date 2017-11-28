//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dongwukj.weiwei.idea.data;

public class PingppObject {
    private String wxAppId;
    private String currentChannel;

    private PingppObject() {
        this.wxAppId = null;
        this.currentChannel = null;
    }

    public static PingppObject getInstance() {
        return PingppObject.PingppObjectHolder.INSTANCE;
    }

    public void setWxAppId(String appId) {
        this.wxAppId = appId;
    }

    public String getWxAppId() {
        return this.wxAppId;
    }

    public String getCurrentChannel() {
        return this.currentChannel;
    }

    public void setCurrentChannel(String currentChannel) {
        this.currentChannel = currentChannel;
    }

    private static class PingppObjectHolder {
        private static final PingppObject INSTANCE = new PingppObject();

        private PingppObjectHolder() {
        }
    }
}
