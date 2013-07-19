package com.eryansky.study.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
/**
 * 
 * @ClassName: StreamTool 
 * @Description: 流读取工具 
 * @author 尔演&Eryan eryanwcp@163.com
 * @date 2011-12-13 上午11:23:27 
 *
 */
public class StreamTool {

	public static String readLine(PushbackInputStream in) throws IOException {
		char buf[] = new char[128];
		int room = buf.length;
		int offset = 0;
		int c;
		loop: while (true) {
			switch (c = in.read()) {
			case -1:
			case '\n':
				break loop;
			case '\r':
				int c2 = in.read();
				if ((c2 != '\n') && (c2 != -1))
					in.unread(c2);
				break loop;
			default:
				if (--room < 0) {
					char[] lineBuffer = buf;
					buf = new char[offset + 128];
					room = buf.length - offset - 1;
					System.arraycopy(lineBuffer, 0, buf, 0, offset);

				}
				buf[offset++] = (char) c;
				break;
			}
		}
		if ((c == -1) && (offset == 0))
			return null;
		return String.copyValueOf(buf, 0, offset);
	}
	
	/**
	 * @Description: 从输入流读取数据
	 * @param inStream 输入流
	 * @return byte[]    byte字节数组 
	 * @throws
	 */
	public static byte[] readInputStream(InputStream inStream) throws Exception{
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while((len = inStream.read(buffer)) != -1){
			outStream.write(buffer,0,len);
		}
		outStream.close();
		inStream.close();
		return outStream.toByteArray();
		
	}
	
	/**
	 * @Description: 从输入流复制到输出流  
	 * @param inStream输入流
	 * @param outStream输出流
	 * @throws java.io.IOException    异常
	 * @return void    返回类型 
	 */
	public static void copyStream(InputStream inStream,OutputStream outStream) throws IOException{
		byte[] buf = new byte[1024];
		int len = 0;
		while((len=inStream.read(buf)) !=-1){
			outStream.write(buf, 0, len);
		}
	}
}
