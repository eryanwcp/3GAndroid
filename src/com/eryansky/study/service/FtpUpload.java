package com.eryansky.study.service;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
/**
 * 通过ftp上传文件
 * @author 尔演&Eryan eryanwcp@163.com
 * @date 2012-3-6 上午09:38:36
 */
public class FtpUpload {

	/** 
	 * 通过ftp上传文件 
	 * @param url ftp服务器地址 如： 192.168.1.110 
	 * @param port 端口如 ： 21 
	 * @param username  登录名 
	 * @param password   密码 
	 * @param remotePath  上到ftp服务器的磁盘路径 
	 * @param fileNamePath  要上传的文件路径 
	 * @param fileName      要上传的文件名 
	 * @return 
	 */  
	public String ftpUpload(String url, String port, String username,String password, String remotePath, String fileNamePath,String fileName) {  
	 FTPClient ftpClient = new FTPClient();  
	 FileInputStream fis = null;  
	 String returnMessage = "0";  
	 try {  
	     ftpClient.connect(url, Integer.parseInt(port));  
	     boolean loginResult = ftpClient.login(username, password);  
	     int returnCode = ftpClient.getReplyCode();  
	     if (loginResult && FTPReply.isPositiveCompletion(returnCode)) {// 如果登录成功   
	         ftpClient.makeDirectory(remotePath);  
	         // 设置上传目录   
	         ftpClient.changeWorkingDirectory(remotePath);  
	         ftpClient.setBufferSize(2*1024);  
	         ftpClient.setControlEncoding("UTF-8");  
	         ftpClient.enterLocalPassiveMode();  
	                 fis = new FileInputStream(fileNamePath + fileName);  
	         ftpClient.storeFile(fileName, fis);  
	           
	         returnMessage = "1";   //上传成功         
	     } else {// 如果登录失败   
	         returnMessage = "0";  
	         }  
	               
	  
	 } catch (IOException e) {  
	     e.printStackTrace();  
	     throw new RuntimeException("FTP客户端出错！", e);  
	 } finally {  
	     //IOUtils.closeQuietly(fis);   
	 try {  
	     ftpClient.disconnect();  
	 } catch (IOException e) {  
	        e.printStackTrace();  
	        throw new RuntimeException("关闭FTP连接发生异常！", e);  
	    }  
	 }  
	 return returnMessage;  
	}  
}
