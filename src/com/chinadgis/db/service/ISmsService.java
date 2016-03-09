package com.chinadgis.db.service;

public interface ISmsService {
	public String getSmsStatusInfo();
	
	public void smsDraft2Send();

	public void smsRead2Log();

	public void smsSend2Log();

	public void smsSend();

	public void smsRead();
}
