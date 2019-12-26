package com.tools.todo;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * 描述：下载文件工具类
 *
 * @author gongzhijing at 2018/12/18 16:07
 * @version 1.0.0
 */
public class UploadUtil {


    /**
     * 描述： 下载单个文件
     *
     * @param fileName  文件名称
     * @param agent   用户代理数据
     * @param response   响应
     * @author gongzhijing at 2018/12/18 16:12:52
     */
    public static void downloadOne(String filePath,String fileName,String agent, HttpServletResponse response) {

        try {
            //
            fileName = new String(fileName.getBytes("iso8859-1"), "utf-8");
            File file = new File(filePath+File.separator+fileName);
            //
            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            OutputStream os = setDownloadHeader(fileName,agent,response);
            InputStream inputStream = new FileInputStream(file);
            byte[] b = new byte[2048];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
            }
            os.flush();
            os.close();

            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 描述： 根据内核不同输出流
     *
     * @param filename 文件名称
     * @param agent 用户代理数据
     * @param response 响应
     * @return   输出流
     * @author gongzhijing at 2018/12/18 16:12:52
     */
    private static OutputStream setDownloadHeader(String filename,String agent,HttpServletResponse response) throws IOException {

        response.reset();
        if (StringUtils.isNotEmpty(agent) && (StringUtils.containsIgnoreCase(agent, "edge"))) {
            filename = URLEncoder.encode(filename, "utf-8").replaceAll("\\+", "%20");
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            response.setContentType("application/octet-stream; charset=UTF-8");
        } else if (StringUtils.isNotEmpty(agent)
                && (StringUtils.containsIgnoreCase(agent, "firefox") || StringUtils.containsIgnoreCase(agent, "safari"))) {
            response.setHeader("Content-Disposition", "attachment; filename="
                    + new String(("\"" + filename + "\"").getBytes("UTF-8"), "ISO-8859-1"));
            response.setContentType("application/octet-stream; charset=ISO-8859-1");
        } else if (StringUtils.isNotEmpty(agent)
                && (StringUtils.containsIgnoreCase(agent, "msie") || StringUtils.containsIgnoreCase(agent, "trident"))) {
            filename = URLEncoder.encode(filename, "utf-8").replaceAll("\\+", "%20");
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            response.setContentType("application/octet-stream; charset=UTF-8");
        } else {
            filename = URLEncoder.encode(filename, "utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            response.setContentType("application/octet-stream; charset=UTF-8");
        }
        return response.getOutputStream();
    }

}
