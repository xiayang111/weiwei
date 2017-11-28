package com.dongwukj.weiwei.idea.result;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sunjaly on 15/3/20.
 */
public class BaseResult {
    private String message;
    private Integer code;
    private String timestamp;
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(timestamp);
        if(matcher.find()){
            this.timestamp=matcher.group();
            return;
        }
        this.timestamp = timestamp;
    }

    public static enum CodeState{
        Success(0),
        Fail(1),
        Logout(3);

        private Integer code;
        private CodeState(Integer code){
            this.code=code;
        }
        public Integer getCode(){
            return this.code;
        }
    }
}
