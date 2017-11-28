package com.dongwukj.weiwei.idea.result;

/**
 * Created by sunjaly on 15/4/21.
 */
public class PhoneVerifyQuestionResult extends BaseResult {

    private String questionOne;
    private String questionTwo;
    private String questionThree;

    public String getQuestionOne() {
        return questionOne;
    }

    public void setQuestionOne(String questionOne) {
        this.questionOne = questionOne;
    }

    public String getQuestionTwo() {
        return questionTwo;
    }

    public void setQuestionTwo(String questionTwo) {
        this.questionTwo = questionTwo;
    }

    public String getQuestionThree() {
        return questionThree;
    }

    public void setQuestionThree(String questionThree) {
        this.questionThree = questionThree;
    }
}
