package com.chinadgis.db.dao;

import com.chinadgis.db.dto.SmsReadObject;
import com.chinadgis.db.dto.SmsSendObject;

public interface ISmsDao {
	/**
	 * 获取服务器状态信息
	 * 
	 * @return
	 */
	public String getSmsStatusInfo();

	/**
	 * 将信息从草稿箱转移到发送箱
	 */
	public void smsDraft2Send();

	/**
	 * 将信息从接收箱转移到接受日志箱
	 */
	public void smsRead2Log();

	/**
	 * 将信息从发送箱转移到发送日志箱
	 */
	public void smsSend2Log();

	/**
	 * 获取一条要发送的短信信息
	 */
	public SmsSendObject getOneSmsSendMsg();

	/**
	 * 插入一条接收的短信信息
	 */
	public int insertOneSmsReadMsg(SmsReadObject smsReadObject);

	/**
	 * 更新一条已经发送信息的状态
	 */
	public void updateSendMessage(String sendID);

}
