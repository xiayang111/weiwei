package com.dongwukj.weiwei.idea.request;

public class PayPasswordRequest extends BaseRequest {

	private String questionOne ;
	private String answerOne  ;
	private String questionTwo  ;
	private String answerTwo  ;
	private String questionThree  ;
	private String answerThree  ;
	private String payPassword  ;
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
	public PayPasswordRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PayPasswordRequest(String questionOne, String answerOne,
			String questionTwo, String answerTwo, String questionThree,
			String answerThree, String payPassword) {
		super();
		this.questionOne = questionOne;
		this.answerOne = answerOne;
		this.questionTwo = questionTwo;
		this.answerTwo = answerTwo;
		this.questionThree = questionThree;
		this.answerThree = answerThree;
		this.payPassword = payPassword;
	}
	@Override
	public String toString() {
		return "PayPasswordRequest [questionOne=" + questionOne
				+ ", answerOne=" + answerOne + ", questionTwo=" + questionTwo
				+ ", answerTwo=" + answerTwo + ", questionThree="
				+ questionThree + ", answerThree=" + answerThree
				+ ", payPassword=" + payPassword + "]";
	}
	
	
	
	
}
