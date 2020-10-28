package com.springboot.demo.util;

/**
 * 这段程序代码主要是为读者展示如何创建按钮组件和标签组件，并且将处理按钮组件的动作事件import java.awt.GridLayout;
 */
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class LinkSftp extends JPanel{


	private Session sshSession = null;
	static final int WIDTH=600;
	static final int HEIGHT=400;
	JTextField text1;//HOST
	JTextField text2;//端口号
	JTextField text3;//用户名
	/*JTextField text4;*/   //密码
	String str;
	LinkSftp(){
		JFrame frame=new JFrame();
		frame.setTitle("sftp测试连接窗口");
		frame.setSize(WIDTH,HEIGHT);
		frame.setVisible(true);

		text1=new JTextField();//HOST
		text2=new JTextField();//用户名
		text3=new JTextField();//密码
		// 	JLabel label1=new JLabel("IP");
		//  	JLabel label2=new JLabel("用户名");
		// 	JLabel label3=new JLabel("密码");
		JButton button1=new JButton("连接结果");
    	/*JButton button2=new JButton("立方结果");
    	JButton button3=new JButton("四次方结果");
    	*/
		frame.setContentPane(this);
		setLayout(new GridLayout(4,2));//让面板具有GridLayout布局管理器，其知识在后面会有所介绍
		//	add(label1);
		add(text1);
		// 	add(label2);
		add(text2);
		// 	add(label3);
		add(text3);

		//	add(label1);
		add(button1);
    	/*add(label2);
    	add(button2);
    	add(label3);
    	add(button3);*/
		button1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent Event){

				String hostIp=text1.getText();
				String username=text2.getText();
				String password=text3.getText();
				System.out.println("IP:"+hostIp+"==用户名:"+username+"==密码:"+password);
				//打印日志类
				com.jcraft.jsch.Logger logger = new SettleLogger();
				JSch.setLogger(logger);

				JSch jsch = new JSch();
				try {
					jsch.getSession(username, hostIp, 22);
					Session sshSession = jsch.getSession(username, hostIp, 22);
					sshSession.setPassword(password);
					Properties sshConfig = new Properties();
					sshConfig.put("StrictHostKeyChecking", "no");
					//同时不要忘了 session.setConfig("StrictHostKeyChecking", "no");
					sshConfig.put("userauth.gssapi-with-mic", "no");
					sshSession.setConfig(sshConfig);
					sshSession.connect(30000);
					System.out.println("连接结果:"+sshSession.isConnected());
					text3.setText("连接结果:"+sshSession.isConnected());
				} catch (JSchException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			}
		});
    	/*button2.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent Event){
    			str=text1.getText();
    			text2.setText(""+Math.pow(Double.parseDouble(str),3));
    		}
    	});
    	button3.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent Event){
    			str=text1.getText();
    			text2.setText(""+Math.pow(Double.parseDouble(str),4));
    		}
    	});
    	*/
	}

	public static void main(String[] args){
		new LinkSftp();
	}


}

