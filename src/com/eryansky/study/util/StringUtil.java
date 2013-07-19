package com.eryansky.study.util;

/**
 * StringUtil
 * @author 尔演&Eryan eryanwcp@163.com
 * @date 2011-11-23 下午02:00:48 
 *
 */
public class StringUtil {     
    /** 
     * 自定义Int转String方法 
     * @param num int型数值 
     * @param len 序列号长度 
     * @return String型固定长度序列号，位数不足时自动补0 
     */  
    public static String toIntString(int num, int len) {  
        String str = "";  
        int s = len - sizeInt(num);  
        for (int i = 0; i < s; i++) {  
            str += "0";  
        }  
        return str + num;  
    }  
    
    
    
    /** 
     * 自定义查询Int型数据位数 
     * @param x Int型数据 
     * @return Int型数据位数 
     */  
    public static int sizeInt(int x){  
        final int[] sizeTable = { 9, 99, 999, 9999, 99999, 999999, 9999999,     
            99999999, 999999999, Integer.MAX_VALUE };      
        for (int i = 0;; i++)     
            if (x <= sizeTable[i])     
                return i + 1;      
    }     
    /*public static void main(String[] args) {     
        String s = toIntString(112,4);  
        System.out.print(s);  
    }  */   
}  