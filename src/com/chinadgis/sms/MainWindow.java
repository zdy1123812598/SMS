package com.chinadgis.sms;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.chinadgis.common.LicenseTool;

public class MainWindow {

	private JFrame frame;
	TrayIcon trayIcon; // 托盘图标
	SystemTray tray; // 本操作系统托盘的实例
	public JLabel lblSmsStatus;//服务状态
	
	public JLabel lblDraftCount;//草稿箱条数
	public JLabel lblSendCount;//发送箱条数
	public JLabel lblReadCount;//接收箱条数
	public JLabel lblSendLogCount;//发送箱日志条数
	public JLabel lblReadLogCount;//接收箱日志条数
	private ThreadManager threadManager;
	private static Logger logger;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//初始化log4j配置
					PropertyConfigurator.configure("assets/config/log4j.properties" );
					logger = Logger.getLogger(MainWindow.class);
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		LicenseTool.validate("iSmsServer.key");
		threadManager = new ThreadManager(this);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("\u77ED\u4FE1\u670D\u52A1\u5668");
		frame.setBounds(100, 100, 613, 404);
		frame.setUndecorated(true);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.setLocationRelativeTo(null);
		
		JPanel appPanel = new JPanel();
		appPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		frame.getContentPane().add(appPanel, BorderLayout.CENTER);
		appPanel.setLayout(new BorderLayout(0, 0));
		JPanel titlePanel = new JPanel();
		appPanel.add(titlePanel, BorderLayout.NORTH);
		titlePanel.setLayout(new BorderLayout(0, 0));
		
		JPanel titleLPanel = new JPanel();
		titlePanel.add(titleLPanel, BorderLayout.WEST);
		titleLPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel appIcon = new JLabel("");
		ImageIcon appicon = new ImageIcon("assets/images/sms.png");
		appIcon.setIcon(appicon);
		titleLPanel.add(appIcon);
		
		JLabel lblNewLabel = new JLabel("\u77ED\u4FE1\u670D\u52A1\u5668");
		titleLPanel.add(lblNewLabel);
		
		JPanel titleCPanel = new JPanel();
		titlePanel.add(titleCPanel, BorderLayout.CENTER);
		
		JPanel titleRPanel = new JPanel();
		titlePanel.add(titleRPanel, BorderLayout.EAST);
	
		titleRPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnNewButton_1 = new JButton("-");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);// 使窗口不可视
				frame.dispose();// 释放当前窗体资源
			}
		});
		titleRPanel.add(btnNewButton_1);
		
		JButton btnNewButton = new JButton("X");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int n = JOptionPane.showConfirmDialog(null, "退出会导致所有短信不能发送，不能接受，确认退出短信服务器吗?", "退出短信服务器确认框", JOptionPane.YES_NO_OPTION); 
		        if (n == JOptionPane.YES_OPTION) {
		        	logger.info("退出了短信服务器");
		        	System.exit(0);
		        } else if (n == JOptionPane.NO_OPTION) { 
		            // ...... 
		        } 
			}
		});
		titleRPanel.add(btnNewButton);
		
		
		JPanel contentPanel = new JPanel();
		appPanel.add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("\u63A5\u6536\u7BB1\uFF1A");
		lblNewLabel_1.setBounds(43, 168, 54, 15);
		contentPanel.add(lblNewLabel_1);
		
		JLabel label = new JLabel("\u8349\u7A3F\u7BB1\uFF1A");
		label.setBounds(43, 193, 54, 15);
		contentPanel.add(label);
		
		JLabel label_1 = new JLabel("\u53D1\u9001\u7BB1\uFF1A");
		label_1.setBounds(43, 218, 54, 15);
		contentPanel.add(label_1);
		
		JLabel label_2 = new JLabel("\u63A5\u6536\u65E5\u5FD7\u7BB1\uFF1A");
		label_2.setBounds(43, 245, 86, 15);
		contentPanel.add(label_2);
		
		JLabel label_3 = new JLabel("\u53D1\u9001\u65E5\u5FD7\u7BB1\uFF1A");
		label_3.setBounds(43, 270, 86, 15);
		contentPanel.add(label_3);
		
		JButton btnStartService = new JButton("\u542F\u52A8\u670D\u52A1");
		btnStartService.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logger.info("点击了开始服务按钮");
				threadManager.startService();
			}
		});
		btnStartService.setBounds(428, 118, 93, 36);
		contentPanel.add(btnStartService);
		
		JButton btnStopService = new JButton("\u505C\u6B62\u670D\u52A1");
		btnStopService.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int n = JOptionPane.showConfirmDialog(null, "停止服务会导致所有短信不能发送，不能接受，确认停止短信服务吗?", "停止短信服务确认框", JOptionPane.YES_NO_OPTION); 
		        if (n == JOptionPane.YES_OPTION) {
		        	logger.info("点击了停止服务按钮");
		        	threadManager.stopService();
		        }
				
			}
		});
		btnStopService.setBounds(428, 164, 93, 36);
		contentPanel.add(btnStopService);
		
		lblSmsStatus = new JLabel("服务器信息");
		lblSmsStatus.setBounds(428, 270, 128, 15);
		contentPanel.add(lblSmsStatus);
		
		lblDraftCount = new JLabel("0");
		lblDraftCount.setBounds(131, 193, 54, 15);
		contentPanel.add(lblDraftCount);
		
		lblSendCount = new JLabel("0");
		lblSendCount.setBounds(131, 218, 54, 15);
		contentPanel.add(lblSendCount);
		
		lblReadCount = new JLabel("0");
		lblReadCount.setBounds(131, 168, 54, 15);
		contentPanel.add(lblReadCount);
		
		lblSendLogCount = new JLabel("0");
		lblSendLogCount.setBounds(131, 270, 54, 15);
		contentPanel.add(lblSendLogCount);
		
		lblReadLogCount = new JLabel("0");
		lblReadLogCount.setBounds(131, 245, 54, 15);
		contentPanel.add(lblReadLogCount);
		//鼠标事件处理类     
		MouseEventListener mouseListener = new MouseEventListener(frame);   
		titlePanel.addMouseListener(mouseListener);     
		titlePanel.addMouseMotionListener(mouseListener); 
		setWindowIcon();
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				int n = JOptionPane.showConfirmDialog(null, "退出会导致所有短信不能发送，不能接受，确认退出短信服务器吗?", "退出短信服务器确认框", JOptionPane.YES_NO_OPTION); 
		        if (n == JOptionPane.YES_OPTION) {
		        	logger.info("退出了短信服务器");
		        	System.exit(0);
		        } else if (n == JOptionPane.NO_OPTION) { 
		            // ...... 
		        } 
				
			}
		});

		frame.setVisible(true);
		tray = SystemTray.getSystemTray(); // 获得本操作系统托盘的实例
		ImageIcon icon = new ImageIcon("assets/images/sms.png"); // 将要显示到托盘中的图标

		PopupMenu pop = new PopupMenu(); // 构造一个右键弹出式菜单
		final MenuItem show = new MenuItem("打开程序");
		final MenuItem exit = new MenuItem("退出程序");
		pop.add(show);
		pop.add(exit);
		trayIcon = new TrayIcon(icon.getImage(), "系统托盘", pop);// 实例化托盘图标
		trayIcon.setImageAutoSize(true);
		// 为托盘图标监听点击事件
		trayIcon.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2)// 鼠标双击图标
				{
					// trayIcon.displayMessage("警告", "这是一个警告提示!",
					// TrayIcon.MessageType.WARNING);
					// trayIcon.displayMessage("错误", "这是一个错误提示!",
					// TrayIcon.MessageType.ERROR);
					// trayIcon.displayMessage("信息", "这是一个信息提示!",
					// TrayIcon.MessageType.INFO);
					// tray.remove(trayIcon); // 从系统的托盘实例中移除托盘图标
					frame.setExtendedState(JFrame.NORMAL);// 设置状态为正常
					frame.setVisible(true);// 显示主窗体
				}
			}
		});

		// 选项注册事件
		ActionListener al2 = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 退出程序
				if (e.getSource() == exit) {
					int n = JOptionPane.showConfirmDialog(null, "退出会导致所有短信不能发送，不能接受，确认退出短信服务器吗?", "退出短信服务器确认框", JOptionPane.YES_NO_OPTION); 
			        if (n == JOptionPane.YES_OPTION) { 
			        	System.exit(0);
			        } else if (n == JOptionPane.NO_OPTION) { 
			            // ...... 
			        } 
				}
				// 打开程序
				if (e.getSource() == show) {
					frame.setExtendedState(JFrame.NORMAL);// 设置状态为正常
					frame.setVisible(true);
				}
			}
		};
		exit.addActionListener(al2);
		show.addActionListener(al2);

		try {
			tray.add(trayIcon); // 将托盘图标添加到系统的托盘实例中
		} catch (AWTException ex) {
			ex.printStackTrace();
		}

		// 为主窗体注册窗体事件
		frame.addWindowListener(new WindowAdapter() {
			// 窗体最小化事件
			public void windowIconified(WindowEvent e) {
				frame.setVisible(false);// 使窗口不可视
				frame.dispose();// 释放当前窗体资源
			}
		});

		// 加个定时监听,每到什么时候就提醒
		// try {
		// java.util.Timer timer = new java.util.Timer();
		// timer.schedule(new TimerTask() {
		// public void run() {
		// String eet = "2012-08-28 17:39:30";
		// String str = thisTime().replace("-", "").replace(":", "")
		// .replace(" ", "");
		// String str1 = eet.replace("-", "").replace(":", "")
		// .replace(" ", "");
		// int et = Integer.parseInt(str.substring(4));
		// int et1 = Integer.parseInt(str1.substring(4));
		// int t = 0;
		// if (et1 - et <= 0) {
		// t++;
		// if (t < 2) {
		// trayIcon.displayMessage("警告", "这是一个警告提示!",
		// TrayIcon.MessageType.WARNING);
		// }
		// }
		// }
		// }, 0, 1000);
		// 监听结束
		// } catch (Exception ex) {
		// System.out.println("Timer exception");
		// }
	}
	/**  
     * 设置窗口图标  
     */  
    protected void setWindowIcon()   
    {   
        ImageIcon imageIcon = new ImageIcon("assets/images/sms.png");
        frame.setIconImage(imageIcon.getImage());   
  
    } 
}