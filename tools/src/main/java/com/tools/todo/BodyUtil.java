package com.tools.todo;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;

/**
 * 描述：
 *
 * @author zhaowen at 2019/1/31 15:26
 * @version 1.0.0
 */
public class BodyUtil {

    //private static BodyUtil bodyUtil;

    private static BodyUtil instance=new BodyUtil();

    public BodyUtil(){

    }
    public synchronized  static BodyUtil getInstance(){
        if (instance == null) {
            instance = new BodyUtil();
        }
        return instance;
    }
    public String getBody(HttpServletRequest request) throws Exception{

        BufferedReader br = request.getReader();

        String str, wholeStr = "";
        while((str = br.readLine()) != null){
            wholeStr += str;
        }

        return wholeStr;
    }
}
