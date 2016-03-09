package com.chinadgis.db.dto;

import java.util.Date;

public class SmsReadObject {
	private String receiptid; // 接收短信ID
	private String smsPhoneNumber; // 短信手机号
	private String smsContent; // 短信内容
	private Date sendTime;// 短信发送时间
	private Date receiptTime;// 短信接收时间
	private int state;// 状态值
	public String getReceiptid() {
		return receiptid;
	}
	public void setReceiptid(String receiptid) {
		this.receiptid = receiptid;
	}
	public String getSmsPhoneNumber() {
		return smsPhoneNumber;
	}
	public void setSmsPhoneNumber(String smsPhoneNumber) {
		this.smsPhoneNumber = smsPhoneNumber;
	}
	public String getSmsContent() {
		return smsContent;
	}
	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public Date getReceiptTime() {
		return receiptTime;
	}
	public void setReceiptTime(Date receiptTime) {
		this.receiptTime = receiptTime;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}


}
