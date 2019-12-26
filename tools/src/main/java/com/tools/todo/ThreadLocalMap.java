//package com.tools.todo;
//
//import com.google.gson.Gson;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * 描述：
// *
// * @author zhaowen at 2018/11/2 09:07
// * @version 1.0.0
// */
//public class ThreadLocalMap {
//
//    private static ThreadLocalMap threadLocalMap;
//
//    public static ThreadLocal threadLocal=new ThreadLocal();
//
//    public ThreadLocalMap(){
//
//    }
//
//    public  void add(String key,Object value){
//        if(threadLocal.get()==null){
//            Map<String,Object> m=new HashMap<String,Object>();
//            m.put(key,value);
//            threadLocal.set(m);
//        }else{
//            Map<String,Object> map=(Map<String,Object>)threadLocal.get();
//            map.put(key,value);
//            threadLocal.set(map);
//        }
//
//    }
//
//    public Object get(String key){
//        Map<String,Object> map=(Map<String,Object>)threadLocal.get();
//        if(map==null){
//            return null;
//        }
//        return map.get(key);
//    }
//
//    public Map<String,Object> getMap(){
//      if(threadLocal.get()==null){
//          return null;
//      }
//        Map<String,Object> map=(Map<String,Object>)threadLocal.get();
//        return map;
//    }
//
//    public void remove(){
//        threadLocal.remove();
//    }
//
//    public synchronized  static ThreadLocalMap getInstance(){
//        if (threadLocalMap == null) {
//            threadLocalMap = new ThreadLocalMap();
//        }
//        return threadLocalMap;
//    }
//
//    public Object getParam(Class T){
//        Map<String,Object> map=(Map<String,Object>)threadLocal.get();
//        Gson gson=new Gson();
//        Object obj=gson.fromJson(map.get("json")==null?"":map.get("json").toString(),T);
//        return obj;
//
//
//    }
//}
