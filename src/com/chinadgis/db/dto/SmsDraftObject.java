package com.chinadgis.db.dto;

public class SmsDraftObject {
	private String  draftID; //草稿短信ID
	private String  humanID; //系统人员ID
	private String  targetPhoneNumber; //发送目标手机号
	private String  smsContent; //短信内容
	private String  createTime;   //短信创建时间 
	
	public String getDraftID() {
		return draftID;
	}
	public void setDraftID(String draftID) {
		this.draftID = draftID;
	}
	public String getHumanID() {
		return humanID;
	}
	public void setHumanID(String humanID) {
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
	
}
