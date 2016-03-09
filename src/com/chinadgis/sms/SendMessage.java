package com.chinadgis.sms;

// SendMessage.java - 金笛短信服务器 v3.4.6 发送示例.
// www.sendsms.cn

import java.io.IOException;

import org.apache.log4j.Logger;

import com.chinadgis.db.util.UtilAttrs;

import cn.sendsms.AGateway;
import cn.sendsms.AGateway.Protocols;
import cn.sendsms.GatewayException;
import cn.sendsms.IOutboundMessageNotification;
import cn.sendsms.Library;
import cn.sendsms.OutboundMessage;
import cn.sendsms.SendSMSException;
import cn.sendsms.Service;
import cn.sendsms.TimeoutException;
import cn.sendsms.modem.SerialModemGateway;

public class SendMessage {
	private static Logger logger = Logger.getLogger(SendMessage.class);
	private static SendMessage sendMessage = null;
	Service srv;
	private String devicetype = null;

	public static SendMessage getInstance() {
		if (sendMessage == null) {
			sendMessage = new SendMessage();
		}
		return sendMessage;
	}

	public void startService() {
		if (devicetype == null) {
			devicetype = UtilAttrs.getProperties().getProperty("devicetype")
					.toString();
		}
		try {

			OutboundNotification outboundNotification = new OutboundNotification();
			System.out.println("示例: 通过串口短信设备发送短信.");
			System.out.println(Library.getLibraryDescription());
			System.out.println("版本: " + Library.getLibraryVersion());

			// 有时由于信号问题,可能会引起超时,运行时若出现No Response 请把这句注释打开
			// System.setProperty("sendsms.nocops",new String());
			// 在linux系统上如果出现No Response，在信号问题已经排除的情况下，请把这句注释打开
			// System.setProperty("sendsms.serial.polling",new String());
			srv = Service.getInstance();

			// 使用时请修改端口号和波特率,如果不清楚,可以去www.sendsms.com.cn下载金笛设备检测工具检测一下
			// 设置短信编码格式，默认为 PDU (如果只发送英文，请设置为TEXT)。CDMA设备只支持TEXT协议
			SerialModemGateway gateway = null;
			if (Integer.parseInt(devicetype) == 2) {
				gateway = new SerialModemGateway("短信猫", "COM4",115200, "Wavecom", "WISMOQCDMA");
				gateway.setProtocol(Protocols.TEXT);
			} else {
				gateway = new SerialModemGateway("jindi",
						"COM5", 9600, "Wavecom", null);
				gateway.setProtocol(Protocols.PDU);
			}			

			// 设置通道gateway是否处理接受到的短信
			// gateway.setInbound(true);

			// 设置是否可发送短信
			gateway.setOutbound(true);

			gateway.setSimPin("0000");			

			// 添加Gateway到Service对象，如果有多个Gateway，都要一一添加。
			srv.addGateway(gateway);

			// 启动服务
			srv.startService();
			System.out.println();
			System.out.println("设备信息:");
			System.out.println("  厂  商: " + gateway.getManufacturer());
			System.out.println("  型  号: " + gateway.getModel());
			System.out.println("  序列号: " + gateway.getSerialNo());
			System.out.println("  IMSI号: " + gateway.getImsi());
			System.out.println("  信  号: " + gateway.getSignalLevel() + "%");
			System.out.println("  电  池: " + gateway.getBatteryLevel() + "%");
			System.out.println();
		} catch (Exception e) {
			// TODO: handle exception			
			logger.error("启动发送信息服务时出现异常："+e.getMessage());
		}

	}

	public void stopService() {
		try {
			this.srv.stopService();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
			logger.error("停止发送信息服务时出现异常："+e.getMessage());
		} catch (GatewayException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
			logger.error("停止发送信息服务时出现异常："+e.getMessage());
		} catch (SendSMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("停止发送信息服务时出现异常："+e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
			logger.error("停止发送信息服务时出现异常："+e.getMessage());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
			logger.error("停止发送信息服务时出现异常："+e.getMessage());
		}
	}

	public void sendMsg(OutboundMessage msg) throws Exception {
		srv.sendMessage(msg);
		msg.setStatusReport(true);
	}

	public class OutboundNotification implements IOutboundMessageNotification {
		public void process(AGateway gateway, OutboundMessage msg) {
			System.out.println("Outbound handler called from Gateway: "
					+ gateway.getGatewayId());
			System.out.println(msg);
		}
	}

	public static void main(String args[]) {
		SendMessage app = new SendMessage();
		try {
			OutboundMessage msg = new OutboundMessage("15665885670",
					"2014.3.19测试。");
			msg.setEncoding(OutboundMessage.MessageEncodings.ENCUCS2);
			msg.setStatusReport(true);
			app.sendMsg(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}