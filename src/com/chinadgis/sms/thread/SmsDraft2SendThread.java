package com.chinadgis.sms.thread;

import net.sf.json.JSONObject;

import com.chinadgis.db.service.ISmsService;
import com.chinadgis.db.service.impl.SmsService;
import com.chinadgis.sms.MainWindow;

public class SmsDraft2SendThread extends Thread implements IServiceThread{
	private MainWindow mMainWindow;
	private boolean runOrNot = true;
	public SmsDraft2SendThread(MainWindow mainWindow) {
		mMainWindow = mainWindow;
		
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (runOrNot) {
			try {
				ISmsService smsService = new SmsService();
				smsService.smsDraft2Send();			
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
