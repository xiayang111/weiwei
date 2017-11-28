package com.dongwukj.weiwei.idea.request;

import java.io.Serializable;

public class PhoneUpdateRequest extends BaseRequest implements Serializable {
	private String questionOne;
	private String answerOne;
	private String questionTwo;
	private String answerTwo;
	private String questionThree;
	private String answerThree;
	private String payPassword;
	public String getQuestionOne() {
		return questionOne;
	}
	public void setQuestionOne(String questionOne) {
		this.questionOne = questionOne;
	}
	public String getAnswerOne() {
		return answerOne;
	}
	public void setAnswerOne(String answerOne) {
		this.answerOne = answerOne;
	}
	public String getQuestionTwo() {
		return questionTwo;
	}
	public void setQuestionTwo(String questionTwo) {
		this.questionTwo = questionTwo;
	}
	public String getAnswerTwo() {
		return answerTwo;
	}
	public void setAnswerTwo(String answerTwo) {
		this.answerTwo = answerTwo;
	}
	public String getQuestionThree() {
		return questionThree;
	}
	public void setQuestionThree(String questionThree) {
		this.questionThree = questionThree;
	}
	public String getAnswerThree() {
		return answerThree;
	}
	public void setAnswerThree(String answerThree) {
		this.answerThree = answerThree;
	}
	public String getPayPassword() {
		return payPassword;
	}
	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}
	@Override
	public String toString() {
		return "PhoneUpdateRequest [questionOne=" + questionOne
				+ ", answerOne=" + answerOne + ", questionTwo=" + questionTwo
				+ ", answerTwo=" + answerTwo + ", questionThree="
				+ questionThree + ", answerThree=" + answerThree
				+ ", payPassword=" + payPassword + "]";
	}
	
}
