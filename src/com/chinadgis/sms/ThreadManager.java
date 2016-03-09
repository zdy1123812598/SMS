package com.chinadgis.sms;

import java.util.ArrayList;
import java.util.List;

import com.chinadgis.sms.thread.IServiceThread;
import com.chinadgis.sms.thread.SmsDraft2SendThread;
import com.chinadgis.sms.thread.SmsRead2LogThread;
import com.chinadgis.sms.thread.SmsReadThread;
import com.chinadgis.sms.thread.SmsSend2LogThread;
import com.chinadgis.sms.thread.SmsSendThread;
import com.chinadgis.sms.thread.SmsStatusThread;

public class ThreadManager {
	private MainWindow mMainWindow;
	private List<IServiceThread> stList = new ArrayList<IServiceThread>();
	// 0 停止状态 -1 正在停止 1 正在启动 2 启动完成
	private int status = 0;

	public ThreadManager(MainWindow mainWindow) {
		mMainWindow = mainWindow;
		stList.add(new SmsStatusThread(mMainWindow));
		stList.add(new SmsDraft2SendThread(mMainWindow));
		stList.add(new SmsSendThread(mMainWindow));
		stList.add(new SmsReadThread(mMainWindow));
		stList.add(new SmsSend2LogThread(mMainWindow));
		stList.add(new SmsRead2LogThread(mMainWindow));
	}

	public void showStatus() {
		switch (status) {
		case -1: {
			mMainWindow.lblSmsStatus.setText("正在停止服务...");
			break;
		}
		case 0: {
			mMainWindow.lblSmsStatus.setText("服务已停止...");
			break;
		}
		case 1: {
			mMainWindow.lblSmsStatus.setText("正在启动服务...");
			break;
		}
		case 2: {
			mMainWindow.lblSmsStatus.setText("服务已启动...");
			break;
		}
		}
	}

	/**
	 * 批量开启线程
	 */
	public void startService() {
		if (status == 0) {			
			setStatus(1);
			ReadMessages.getInstance().startService();
			SendMessage.getInstance().startService();
			for (IServiceThread serviceThread : stList) {
				serviceThread.startService();
			}			
			setStatus(2);			
		}

	}

	/**
	 * 批量停止线程
	 */
	public void stopService() {
		if (status == 2) {
			setStatus(-1);
			for (IServiceThread serviceThread : stList) {
				serviceThread.stopService();
			}
			ReadMessages.getInstance().stopService();
			SendMessage.getInstance().stopService();
			setStatus(0);
		}

	}

	public int getStatus() {
		return status;
	}

	private void setStatus(int status) {
		this.status = status;
		showStatus();
	}

}
