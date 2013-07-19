package com.eryansky.test;

import java.io.File;

import android.os.Environment;
import android.test.AndroidTestCase;
import android.util.Log;

import com.eryansky.study.util.FileUtil;

public class FileTest extends AndroidTestCase{

	private static final String TAG = "FileTest";
	public void createFie(){
		String dir = Environment.getExternalStorageDirectory()+ File.separator + "ok";
		Log.i(TAG, dir);
		try {
			boolean b = FileUtil.createFolder(dir);
			Log.i(TAG,"" + b);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
