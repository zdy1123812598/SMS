package com.chinadgis.db.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import cn.sendsms.InboundMessage;
import cn.sendsms.Message.MessageEncodings;
import cn.sendsms.OutboundMessage;

import com.chinadgis.db.dao.ISmsDao;
import com.chinadgis.db.dao.impl.SmsDao;
import com.chinadgis.db.dto.SmsReadObject;
import com.chinadgis.db.dto.SmsSendObject;
import com.chinadgis.db.service.ISmsService;
import com.chinadgis.sms.ReadMessages;
import com.chinadgis.sms.SendMessage;

public class SmsService implements ISmsService {
	private static Logger logger = Logger.getLogger(SmsService.class);
	@Override
	public String getSmsStatusInfo() {
		// TODO Auto-generated method stub
		ISmsDao smsDao = new SmsDao();
		return smsDao.getSmsStatusInfo();
	}

	@Override
	public void smsDraft2Send() {
		// TODO Auto-generated method stub
		ISmsDao smsDao = new SmsDao();
		smsDao.smsDraft2Send();
	}

	@Override
	public void smsRead2Log() {
		// TODO Auto-generated method stub
		ISmsDao smsDao = new SmsDao();
		smsDao.smsRead2Log();
	}

	@Override
	public void smsSend2Log() {
		// TODO Auto-generated method stub
		ISmsDao smsDao = new SmsDao();
		smsDao.smsSend2Log();
	}

	@Override
	public void smsSend() {
		// TODO Auto-generated method stub
		ISmsDao smsDao = new SmsDao();
		try {
			SmsSendObject smsSendObject = smsDao.getOneSmsSendMsg();
			if (smsSendObject != null) {
				OutboundMessage msg = new OutboundMessage(smsSendObject.getTargetPhoneNumber(),smsSendObject.getSmsContent());
				msg.setEncoding(MessageEncodings.ENCUCS2);
				SendMessage.getInstance().sendMsg(msg);
				if (msg.getStatusReport()) {
					smsDao.updateSendMessage(smsSendObject.getSendID());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();			
			logger.error("发送短信信息时出现异常："+e.getMessage());
		}

	}

	@Override
	public void smsRead() {
		// TODO Auto-generated method stub
		ISmsDao smsDao = new SmsDao();
		try {
			List<InboundMessage> list = ReadMessages.getInstance().readMsg();
			if (list != null && list.size()>0) {
				for (InboundMessage inboundMessage : list) {
					SmsReadObject smsReadObject = new SmsReadObject();
					smsReadObject.setSmsPhoneNumber(inboundMessage.getOriginator().toString());
					smsReadObject.setSmsContent(inboundMessage.getText().toString());
					smsReadObject.setSendTime(inboundMessage.getDate());
					int flag = smsDao.insertOneSmsReadMsg(smsReadObject);
					if (flag == 1) {
						ReadMessages.getInstance().deleteMessage(inboundMessage);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("接收短信信息时出现异常："+e.getMessage());
		}

	}

}
