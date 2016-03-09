package com.chinadgis.sms.thread;

import net.sf.json.JSONObject;

import com.chinadgis.db.service.ISmsService;
import com.chinadgis.db.service.impl.SmsService;
import com.chinadgis.sms.MainWindow;
/**
 * 显示服务器的 运行状态信息2015-01-29
 * @author Dcx
 *
 */
public class SmsStatusThread extends Thread implements IServiceThread {
	private MainWindow mMainWindow;
	private boolean runOrNot = true;

	public SmsStatusThread(MainWindow mainWindow) {
		super();
		mMainWindow = mainWindow;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (runOrNot) {
			try {
				ISmsService smsService = new SmsService();
				String smsStatus = smsService.getSmsStatusInfo();
				JSONObject jsonObj = JSONObject.fromObject(smsStatus);
				int readcount = jsonObj.getInt("readcount");
			    int draftcount = jsonObj.getInt("draftcount");
			    int sendcount = jsonObj.getInt("sendcount");
			    int readlogcount = jsonObj.getInt("readlogcount");
			    int sendlogcount = jsonObj.getInt("sendlogcount");
			    mMainWindow.lblReadCount.setText(readcount+"条");
			    mMainWindow.lblDraftCount.setText(draftcount+"条");
			    mMainWindow.lblSendCount.setText(sendcount+"条");
			    mMainWindow.lblReadLogCount.setText(readlogcount+"条");
			    mMainWindow.lblSendLogCount.setText(sendlogcount+"条");
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
