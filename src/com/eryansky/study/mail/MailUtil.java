/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.eryansky.study.mail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.eryansky.study.exception.SystemException;

/**
 * 邮件发送工具类
 * @author 尔演&Eryan eryanwcp@163.com
 * @date 2012-3-23 上午10:29:34
 */
public class MailUtil {

	/**
	 * 发送邮件（HTML格式）
	 * @param mailProperties
	 * @param username 用户名
	 * @param password 密码
	 * @param from 源地址
	 * @param to 目标地址（多个地址以","分割）
	 * @param subject 邮件主题
	 * @param body 邮件内容（HTML格式）
	 */
	public static void sendHtmlMail(Properties mailProperties, final String username,final String password,String from, String to,
			String subject, String body) {
		try {
			// create a new Session object
			Session session = Session.getInstance(mailProperties,
					new Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(username,password);
						}
					});

			// create a new MimeMessage object (using the Session created above)
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(RecipientType.TO, 
					InternetAddress.parse(to));
			message.setSubject(subject);
			message.setContent(body, "text/html;charset=UTF-8");

			Transport.send(message);

		} catch (Exception e) {
			throw new SystemException("sending mail failed", e);
		}
	}

}
