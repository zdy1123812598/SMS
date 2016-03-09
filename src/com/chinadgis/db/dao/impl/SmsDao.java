package com.chinadgis.db.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.chinadgis.db.dao.ISmsDao;
import com.chinadgis.db.dto.SmsReadObject;
import com.chinadgis.db.dto.SmsSendObject;
import com.chinadgis.db.util.DBOperaRule;
import com.chinadgis.db.util.ResultSetToList;

public class SmsDao implements ISmsDao {

	@Override
	public String getSmsStatusInfo() {
		// TODO Auto-generated method stub
		String sql_read = "select count(*) from tbSmsReadBox";
		String sql_draft = "select count(*) from tbSmsDraftBox";
		String sql_send = "select count(*) from tbSmsSendBox";
		String sql_readlog = "select count(*) from tbSmsReadLogBox";
		String sql_sendlog = "select count(*) from tbSmsSendLogBox";

		int readCount = 0, draftCount = 0, sendCount = 0, readLogCount = 0, sendLogCount = 0;

		DBOperaRule dbor = new DBOperaRule();
		try {
			dbor.createConnection();
			if (dbor.isConnOK()) {
				dbor.beginTransaction();
				readCount = dbor.readCount(sql_read);
				draftCount = dbor.readCount(sql_draft);
				sendCount = dbor.readCount(sql_send);
				readLogCount = dbor.readCount(sql_readlog);
				sendLogCount = dbor.readCount(sql_sendlog);
				dbor.commitTransaction();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbor.closeDBConnection();
		}

		return "{\"readcount\":\"" + readCount + "\",\"draftcount\":\""
				+ draftCount + "\",\"sendcount\":\"" + sendCount
				+ "\",\"readlogcount\":\"" + readLogCount
				+ "\",\"sendlogcount\":\"" + sendLogCount + "\"}";
	}

	@Override
	public void smsDraft2Send() {
		// TODO Auto-generated method stub
		String sql_update = "update tbSmsDraftBox set state = 2 where state = 1";
		String sql_insert = "insert into tbSmsSendBox (sendid, humanid, targetphonenumber, smscontent, createtime,state)"
				+ " select sys_guid(), humanid, targetphonenumber, smscontent, createtime , 0  from tbSmsDraftBox where state = 2";
		String sql_delete = "delete from tbSmsDraftBox where state = 2";

		DBOperaRule dbor = new DBOperaRule();
		try {
			dbor.createConnection();
			if (dbor.isConnOK()) {
				dbor.beginTransaction();
				dbor.execute(sql_update);
				dbor.execute(sql_insert);
				dbor.execute(sql_delete);
				dbor.commitTransaction();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbor.closeDBConnection();
		}
	}

	@Override
	public void smsRead2Log() {
		// TODO Auto-generated method stub
		String sql_update = "update tbSmsReadBox set state = 2 where state = 1";
		String sql_insert = "insert into tbSmsReadLogBox (receiptid,smsphonenumber, smscontent, sendtime,receipttime)"
				+ " select sys_guid(), smsphonenumber, smscontent, sendtime,receipttime from tbSmsReadBox where state = 2";
		String sql_delete = "delete from tbSmsReadBox where state = 2";
		DBOperaRule dbor = new DBOperaRule();
		try {
			dbor.createConnection();
			if (dbor.isConnOK()) {
				dbor.beginTransaction();
				dbor.execute(sql_update);
				dbor.execute(sql_insert);
				dbor.execute(sql_delete);
				dbor.commitTransaction();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbor.closeDBConnection();
		}
	}

	@Override
	public void smsSend2Log() {
		// TODO Auto-generated method stub
		String sql_update = "update tbSmsSendBox set state = 2 where state = 1";
		String sql_insert = "insert into tbSmsSendLogBox (sendid, humanid, targetphonenumber, smscontent, createtime,sendtime)"
				+ " select sys_guid(), humanid, targetphonenumber, smscontent, createtime,sendtime from tbSmsSendBox where state = 2";
		String sql_delete = "delete from tbSmsSendBox where state = 2";
		DBOperaRule dbor = new DBOperaRule();
		try {
			dbor.createConnection();
			if (dbor.isConnOK()) {
				dbor.beginTransaction();
				if (dbor.isConnOK()) {
					dbor.execute(sql_update);
					dbor.execute(sql_insert);
					dbor.execute(sql_delete);
					dbor.commitTransaction();
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbor.closeDBConnection();
		}
	}

	@Override
	public SmsSendObject getOneSmsSendMsg() {
		// TODO Auto-generated method stub
		SmsSendObject smsSendObject = null;
		String sql = "select sendID,humanID,targetPhoneNumber,smsContent,to_char(createTime,'YYYY-MM-DD HH24:MI:SS') createTime,to_char(sendTime,'YYYY-MM-DD HH24:MI:SS') sendTime,state from tbSmsSendBox where state=0 and createtime = (select min(createtime) from tbSmsSendBox)";
		DBOperaRule dbor = new DBOperaRule();
		try {
			List<SmsSendObject> list = null;
			dbor.createConnection();
			if (dbor.isConnOK()) {
				dbor.beginTransaction();
				ResultSet rs = dbor.read(sql);
				list = ResultSetToList.MakeRsToList(rs, SmsSendObject.class);
				dbor.commitTransaction();
				if (list != null && list.size() > 0) {
					smsSendObject = list.get(0);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbor.closeDBConnection();
		}
		return smsSendObject;
	}

	@Override
	public int insertOneSmsReadMsg(SmsReadObject smsReadObject) {
		// TODO Auto-generated method stub
		int flag = -1;
		String smsPhoneNumber = smsReadObject.getSmsPhoneNumber().toString();
		String smsContent = smsReadObject.getSmsContent().toString();
		String sendTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(
				smsReadObject.getSendTime()).toString();
		String receiptTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date()).toString();
		String sql_insert = String
				.format("insert into tbSmsReadBox (receiptid, smsPhoneNumber, smsContent, sendTime,receiptTime,state) values "
						+ "(sys_guid(),'"
						+ smsPhoneNumber
						+ "','"
						+ smsContent
						+ "',to_date('"
						+ sendTime
						+ "',"
						+ "'YYYY-MM-DD HH24:MI:SS'"
						+ "),to_date('"
						+ receiptTime
						+ "',"
						+ "'YYYY-MM-DD HH24:MI:SS'"
						+ "),0)");
		DBOperaRule dbor = new DBOperaRule();
		try {
			dbor.createConnection();
			if (dbor.isConnOK()) {
				dbor.beginTransaction();
				dbor.execute(sql_insert);
				dbor.commitTransaction();
				flag = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbor.closeDBConnection();
		}
		return flag;
	}

	public void updateSendMessage(String sendID) {
		String sendTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(
				new Date()).toString();
		String sql_update = "update tbSmsSendBox set  sendTime=to_date('"
				+ sendTime + "'," + "'YYYY-MM-DD HH24:MI:SS'"
				+ "),state=1  where sendID='" + sendID + "'";
		DBOperaRule dbor = new DBOperaRule();
		try {
			dbor.createConnection();
			if (dbor.isConnOK()) {
				dbor.beginTransaction();
				dbor.execute(sql_update);
				dbor.commitTransaction();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbor.closeDBConnection();
		}
	}

}
