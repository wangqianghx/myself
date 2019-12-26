package com.tools.todo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;

/**
 * @Auther: lvp
 * @Date: 2019-02-18
 * @Description: 文件操作工具类
 */
public class FileUtils {

    private static final Logger log = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 如果目录不存在，就创建文件
     *
     * @param dirPath 文件路径
     * @return
     */
    public static String mkdirs(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return dirPath;
    }

    /*写入Text文件操作*/
    public static void writeText(File filePath, String content, boolean isAppend) {
        FileOutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            outputStream = new FileOutputStream(filePath, isAppend);
            outputStreamWriter = new OutputStreamWriter(outputStream);
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(content);
        } catch (IOException e) {
            log.info(e.toString());
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
                if (outputStreamWriter != null) {
                    outputStreamWriter.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception e) {
                log.info(e.toString());
            }
        }
    }


    /**
     * 普通文件下载，文件路径固定
     *
     * @param targetFile 下载的文件路径
     * @param response
     */
    public static void download(String targetFile, HttpServletResponse response) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            //设置文件下载时，文件流的格式
            bis = new BufferedInputStream(new FileInputStream(targetFile));
            bos = new BufferedOutputStream(response.getOutputStream());
            //下面这个变量保存的是要下载的文件拼接之后的完整路径
            String downName = targetFile.substring(targetFile.lastIndexOf("/") + 1);
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(downName, "utf-8"));
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            log.info("下载成功!");
        } catch (Exception e) {
            log.info(e.toString());
            log.info("下载出错");
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
            } catch (Exception e) {
                log.info(e.toString());
            }
            try {
                if (bis != null) {
                    bis.close();
                }
            } catch (Exception e) {
                log.info(e.toString());
            }
        }
    }

    /*文件拷贝操作*/
    public static void copy(String sourceFile, String targetFile) throws IOException {
        File source = new File(sourceFile);
        File target = new File(targetFile);
        target.getParentFile().mkdirs();
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            fis = new FileInputStream(source);
            fos = new FileOutputStream(target);
            in = fis.getChannel();//得到对应的文件通道
            out = fos.getChannel();//得到对应的文件通道
            in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道
        } catch (IOException e) {
            log.info(e.toString());
        } finally {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
    }


    /**
     * 删除文件
     *
     * @param fileName
     * @return
     */
    public static boolean delete(String fileName) {
        try {
            File sourceFile = new File(fileName);
            if (sourceFile.isDirectory()) {
                for (File listFile : sourceFile.listFiles()) {
                    delete(listFile.getAbsolutePath());
                }
            }
            return sourceFile.delete();
        } catch (Exception e) {
            log.info(e.toString());
        }
        return false;
    }

    //上传文件
    public static void upload(String filePath, String base64Code) {
        FileOutputStream fileOutputStream = null;
        try {
            byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
            fileOutputStream = new FileOutputStream(filePath);
            fileOutputStream.write(buffer);
        } catch (Exception e) {
            log.info(e.toString());
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (Exception e) {
                log.info(e.toString());
            }
        }
    }
}
