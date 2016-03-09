package com.chinadgis.db.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class SmsSendObject {
	private String sendID; // 发送短信ID
	private BigDecimal humanID; // 系统人员ID
	private String targetPhoneNumber; // 发送目标手机号
	private String smsContent; // 短信内容
	private String createTime; // 短信创建时间
	private String sendTime; // 短信发送时间
	private BigDecimal state; // 0未发送，1已发送 已发送会移动到tbSmsSendLogBox

	public String getSendID() {
		return sendID;
	}

	public void setSendID(String sendID) {
		this.sendID = sendID;
	}

	public BigDecimal getHumanID() {
		return humanID;
	}

	public void setHumanID(BigDecimal humanID) {
		this.humanID = humanID;
	}

	public String getTargetPhoneNumber() {
		return targetPhoneNumber;
	}

	public void setTargetPhoneNumber(String targetPhoneNumber) {
		this.targetPhoneNumber = targetPhoneNumber;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public BigDecimal getState() {
		return state;
	}

	public void setState(BigDecimal state) {
		this.state = state;
	}

}
