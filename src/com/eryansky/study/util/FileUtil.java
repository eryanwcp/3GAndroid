package com.eryansky.study.util;

import java.io.*;

import android.os.Environment;
/**
 * 文件操作工具类
 * @author 尔演&Eryan eryanwcp@163.com
 * @date 2012-5-31 下午10:34:43
 */
public class FileUtil {
    private static final int DEFAULT_BUFFER_SIZE = 1024;

    /**
     * 将字符串写入指定文件(当指定的父路径中文件夹不存在时，会最大限度去创建，以保证保存成功！)
     *
     * @param res      原字符串
     * @param filePath 文件路径
     * @return 成功标记
     */
    public static boolean string2File(String res, String filePath) {
        boolean flag = true;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
            File distFile = new File(filePath);
            if (!distFile.getParentFile().exists())
                distFile.getParentFile().mkdirs();
            bufferedReader = new BufferedReader(new StringReader(res));
            bufferedWriter = new BufferedWriter(new FileWriter(distFile));
            char buf[] = new char[1024]; // 字符缓冲区
            int len;
            while ((len = bufferedReader.read(buf)) != -1) {
                bufferedWriter.write(buf, 0, len);
            }
            bufferedWriter.flush();
            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            flag = false;
            return flag;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    /**
     * 文本文件转换为指定编码的字符串
     *
     * @param file     文本文件
     * @param encoding 编码类型
     * @return 转换后的字符串
     * @throws java.io.IOException
     */
    public static String file2String(File file, String encoding) {
        InputStreamReader reader = null;
        StringWriter writer = new StringWriter();
        try {
            if (encoding == null || "".equals(encoding.trim())) {
                reader = new InputStreamReader(new FileInputStream(file), encoding);
            } else {
                reader = new InputStreamReader(new FileInputStream(file));
            }
            //将输入流写入输出流
            char[] buffer = new char[DEFAULT_BUFFER_SIZE];
            int n = 0;
            while (-1 != (n = reader.read(buffer))) {
                writer.write(buffer, 0, n);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        //返回转换结果
        if (writer != null)
            return writer.toString();
        else return null;
    }

    public static void instream2file(InputStream ins, File file) throws Exception {
        OutputStream os = new FileOutputStream(file);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        ins.close();
    }

    /**
     * 创建任意深度的文件所在文件夹,可以用来替代直接new File(path)。
     *
     * @param path
     * @return File对象
     */
    @SuppressWarnings({})
    public static File createFile(String path) {
        File file = new File(path);
        //寻找父目录是否存在
        File parent = new File(file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf(File.separator)));
        //如果父目录不存在，则递归寻找更上一层目录
        if (!parent.exists()) {
            createFile(parent.getPath());
            //创建父目录
            parent.mkdirs();
        }
        return file;
    }

    /**
     * 生成文件夹
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static boolean createFolder(String path) throws Exception {
        File dirFile = null;
        try {
            dirFile = new File(path);
            if (!(dirFile.exists()) && !(dirFile.isDirectory())) {
                boolean creadok = dirFile.mkdirs();
                if (creadok) {
                    System.out.println(" ok:创建文件夹成功！ ");
                } else {
                    System.out.println(" err:创建文件夹失败！ " + path);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            return false;
        }
        return true;
    }


    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     * @author jiangxc<br>
     * <b>create Date:</b>Jul 13, 2009 5:36:18 PM<br>
     * <b>last modify Date:</b>Jul 13, 2009 5:36:18 PM<br>
     */

    public static boolean copyFile(String oldPath, String newPath) throws IOException {
        boolean flag = false;
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { // 文件存在时
                InputStream inStream = new FileInputStream(oldPath); // 读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; // 字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
            flag = true;
        } catch (IOException e) {
            throw e;
        }
        return flag;
    }
    
    
    /*------ Android ------*/
    /**
     * 1、判断SD卡是否存在
     */
    public static boolean hasSdcard() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }
    
    

}
