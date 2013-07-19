package com.eryansky.study.service;


import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.future.ReadFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.eryansky.study.util.MyCodecFactory;
import com.eryansky.study.util.StringUtil;

/**
 * Mina客户端发送并且接收从服务器端返回的数据 String型xml数据
 * @author 尔演&Eryan eryanwcp@163.com
 * @date 2011-11-23 下午01:40:44 
 */
public class MinaCilentFactory {

	private static final String SERVER_IP = "10.0.2.2";//服务器ip地址
	private static final int SERVER_PORT = 3210;//服务器Socket端口
	private static final int TIME_OUT = 5*1000;//5秒
	/**
	 * 发送请求（xml长度[4位]+xml实体）并返回xml字符串
	 * @param xml
	 * @return
	 */
	public static String getSoctetRequest(String xml) {
		String result = null;
        NioSocketConnector connector = new NioSocketConnector();
        connector.setConnectTimeoutMillis(TIME_OUT);
        connector.getFilterChain().addLast("codec",
                new ProtocolCodecFilter(new MyCodecFactory()));
        //connector.setHandler(new MinaClientHandler());
        connector.getFilterChain().addLast("logger", new LoggingFilter());
        SocketSessionConfig cfg = connector.getSessionConfig();
        cfg.setUseReadOperation(true);
        IoSession session = null;
        try {
        	String head = StringUtil.toIntString(xml.getBytes("UTF-8").length, 4);//固定报文长度 不足前置补0 
            session = connector.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT)).awaitUninterruptibly().getSession();
            // 发送
            session.write(head + xml).awaitUninterruptibly();
            // 接收
            ReadFuture readFuture = session.read();
            if (readFuture.awaitUninterruptibly(TIME_OUT, TimeUnit.MILLISECONDS)) {
                result = (String) readFuture.getMessage();
            } else {
            	result = "接收数据连接超时！";
            	System.out.println(result);
            }

        } catch (Exception e) {
        	result = "连接服务器异常！";
        	e.printStackTrace();
        } finally {
            // 断开
            try {
            	if(session != null){
            		session.close(true);
                    session.getService().dispose();
            	}
            } catch (Exception e) {
            	e.printStackTrace();
            }
        }
        return result;
	
		/*Socket socket = null;
		String result = null;
		try {	
			//创建Socket
			socket = new Socket(SERVER_IP,SERVER_PORT); 
			//向服务器发送消息
			PrintWriter out = new PrintWriter( new BufferedWriter( new OutputStreamWriter(socket.getOutputStream())),true);      
			out.println(xml); 
			//接收来自服务器的消息
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
			result = br.readLine(); 
			byte[] data = StreamTool.readInputStream(socket.getInputStream());
			result = new String(data, "UTF-8");
			//关闭流
			out.close();
			//关闭Socket
			socket.close();
		}
		catch (Exception e){
			e.toString();
		}
	    return result;*/
	}
}