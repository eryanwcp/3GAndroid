package com.eryansky.study.util;

import com.eryansky.study.entity.User;
/**
 * 缓存一些手机端需要访问的数据，这样的好处是可以达达节省手机和服务器的交互，用单例实现
 * @author 尔演&Eryan eryanwcp@163.com
 * @date 2012-3-16 上午11:52:38
 */
public class Cache {  
      
    private User User;  
      
    private Cache(){  
          
    }  
    /** 构造单例 */  
    private static class CacheHolder{  
        private static final Cache INSTANCE = new Cache();  
    }  
    public Cache getInstance(){  
        return CacheHolder.INSTANCE;  
    }  
    public User getUser() {  
        return User;  
    }  
    public void setUser(User User) {  
        this.User = User;  
    }  
  
}  