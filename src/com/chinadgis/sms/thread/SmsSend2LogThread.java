package com.chinadgis.sms.thread;

import com.chinadgis.db.service.ISmsService;
import com.chinadgis.db.service.impl.SmsService;
import com.chinadgis.sms.MainWindow;

public class SmsSend2LogThread extends Thread implements IServiceThread{
	private MainWindow mMainWindow;
	private boolean runOrNot = true;
	public SmsSend2LogThread(MainWindow mainWindow) {
		super();
		mMainWindow = mainWindow;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (runOrNot) {
			try {
				ISmsService smsService = new SmsService();
				smsService.smsSend2Log();			
				sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	@Override
	public void startService() {
		runOrNot = true;
		this.start();
	}

	@Override
	public void stopService() {
		// TODO Auto-generated method stub
		runOrNot = false;
	}
}
