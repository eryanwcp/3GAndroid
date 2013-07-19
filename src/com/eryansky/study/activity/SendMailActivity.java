package com.eryansky.study.activity;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.eryansky.study.mail.MailUtil;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * 发送邮件 选用系统自带的邮件客服端程序
 *  或者使用Javamail组件自行发送/接收邮件
 * @author 尔演&Eryan eryanwcp@163.com
 * @date 2011-12-8 上午10:26:34
 */
public class SendMailActivity extends Activity {
	
	private static final String TAG = "SendMailActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// 打开发邮件界面
		/*
		 * Uri uri = Uri.parse("mailto:1036416764@qq.com"); 
		 * Intent intent = new Intent(Intent.ACTION_SENDTO, uri); 
		 * startActivity(intent);
		 */
		//发送邮件 传递
		/*Intent intent = new Intent(Intent.ACTION_SEND);
		String[] tos = { "1036416764@qq.com" };//邮件接收者
		String[] ccs = { "eryanwcp@gmail.com" };//邮件发者送或者抄送者
		intent.putExtra(Intent.EXTRA_EMAIL, tos);
		intent.putExtra(Intent.EXTRA_CC, ccs);
		intent.putExtra(Intent.EXTRA_TEXT, "I come from http://www.eryansky.com");//邮件文本内容
		intent.putExtra(Intent.EXTRA_SUBJECT, "http://www.eryansky.com");//邮件主题
		intent.setType("message/rfc882");
		this.startActivity(Intent.createChooser(intent, "Choose Email Client"));*/
		
		// 发送带附件的邮件
		/*Intent intent = new Intent(Intent.ACTION_SEND);
		intent.putExtra(Intent.EXTRA_SUBJECT, "The email subject text");
		intent.putExtra(Intent.EXTRA_STREAM, "file:///sdcard/mysong.mp3");
		intent.setType("audio/mp3");
		startActivity(Intent.createChooser(intent, "Choose Email Client"));*/

		
		//借助javamail组件自行发送/接收邮件email 注：需要引入mail.jar,activation.jar,additionnal.jar
		
		
		/*try {
			Properties props = new Properties();
			props.setProperty("mail.smtp.auth", "true");
			props.setProperty("mail.transport.protocol", "smtp");
			props.setProperty("mail.host", "smtp.163.com");
			Session session = Session.getInstance(props,
					new Authenticator()
					{
						protected PasswordAuthentication getPasswordAuthentication()
						{
							return new PasswordAuthentication("eryanwcp","123456");
						}
					}
			);
			session.setDebug(true);
			
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("eryanwcp@163.com"));
			msg.setSubject("测试主题");
			msg.setRecipients(RecipientType.TO, 
					InternetAddress.parse("wcperyan@qq.com,eryanwcp@gmail.com,eryanwcp@163.com"));
			msg.setContent("<span style='color:red'>这只不过是一个测试用的邮件 不是垃圾邮件啦 烦死你了...</span>", "text/html;charset=gbk");
			
			Transport.send(msg);
		} catch (Exception e){
			Log.e(TAG, e.toString());
		}*/
		
		Properties props = new Properties();
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.host", "smtp.163.com");		
		MailUtil.sendHtmlMail(props, "eryanwcp", "5736165wcp", "eryanwcp@163.com", "eryanwcp@gmail.com,1036416764@qq.com", "测试主题", "<span style='color:red'>这只不过是一个测试用的邮件 不是垃圾邮件啦 烦死你了...</span>");
		
	}
}
