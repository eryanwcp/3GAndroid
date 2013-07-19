package com.eryansky.study.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
/**
 * mina通信加密解密方法 
 * @author 尔演&Eryan eryanwcp@163.com
 * @date 2011-12-13 上午11:22:30 
 *
 */
public class JsDes {
//  public static String s52s = "abcdefghijklmnopqrstuvwxyz";
  public static String s52s = "8ABC7DLO5MN6Z9EFGdeJfghijkHIVrstuvwWSTUXYabclmnopqKPQRxyz01234";
  static boolean s52t = true;
  static int N, N2;
  static int[] s52r = new int[128];

  static void s52f() {
      N = s52s.length();
      N2 = N * N;
      byte[] bytes = s52s.getBytes();
      for (int x = 0; x < bytes.length; x++) {
          s52r[bytes[x]] = x;
      }
      s52t = false;
  }

  public static String s52e(String n) {
      if (s52t) s52f();
      byte[] bytes = s52s.getBytes();
      byte[] ns = new byte[0];
      try {
          ns = n.getBytes("UTF-8");
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      }
      int l = ns.length, a, x = 0;
      List<Byte> t = new ArrayList<Byte>(l * 3);
      for (; x < l; x++) {
          a = (int) (char) ns[x];
          if (a < N2) {
              t.add(bytes[a / N]);
              t.add(bytes[a % N]);
          } else {
              t.add(bytes[a / N2 + 5]);
              t.add(bytes[(a / N) % N]);
              t.add(bytes[a % N]);
          }
      }
      ns = new byte[t.size()];
      for (int i = 0; i < t.size(); i++) {
          ns[i] = t.get(i);
      }
      return String.valueOf(ns.length).length() + String.valueOf(ns.length) + new String(ns);
  }

  public static String s52d(String n) {
      if (s52t) s52f();
      int c = 0;
      try {
          c = Integer.parseInt(n.substring(0, 1));
          c = Integer.parseInt(n.substring(1, c + 1));
      } catch (Exception e) {
          return "";
      }
      if (c == 0) {
          return "";
      }
      byte[] ns = n.getBytes();
      int x = String.valueOf(c).length() + 1;
      if (ns.length != c + x) return "";
      int nl = ns.length, a;
      List<Byte> t = new ArrayList<Byte>(nl * 3);
      for (; x < nl; x++) {
          a = s52r[(int) ns[x]];
          x++;
          if (a < 5) {
              c = a * N + s52r[(int) ns[x]];
          } else {
              c = (a - 5) * N2 + s52r[(int) ns[x]] * N;
              x++;
              c += s52r[(int) ns[x]];
          }
          t.add((byte) c);
      }
      ns = new byte[t.size()];
      for (int i = 0; i < t.size(); i++) {
          ns[i] = t.get(i);
      }
      try {
          return new String(ns, "UTF-8");
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
          return null;
      }
  }
}
