package com.tools.todo;

/**
 *         <dependency>
 *             <groupId>com.github.junrar</groupId>
 *             <artifactId>junrar</artifactId>
 *             <version>0.7</version>
 *         </dependency>
 */
import com.github.junrar.Archive;
import com.github.junrar.VolumeManager;
import com.github.junrar.rarfile.FileHeader;
/**
 *         <dependency>
 *             <groupId>org.apache.ant</groupId>
 *             <artifactId>ant</artifactId>
 *             <version>RELEASE</version>
 *         </dependency>
 */
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipOutputStream;

/**
 * 描述：解压文件处理类
 *
 * @author wangqiang at 2019/8/20 14:29
 */
public class ZipUtil {
    private static final Logger log = LoggerFactory.getLogger(ZipUtil.class);

    public static synchronized void unzip(File zipFile, String targetPath) throws Exception {
        String zipFileName = zipFile.getName();
        if(zipFileName.endsWith(".rar") || zipFileName.endsWith(".RAR")){
            unRarFile(zipFile, targetPath);
        }else if(zipFileName.endsWith(".zip") || zipFileName.endsWith(".zip")){
            unZipFiles(zipFile, targetPath);
        }else{
            unZipFiles(zipFile,targetPath);
        }
    }


    /**
     * 解压zip格式的压缩文件到指定位置
     * @param srcZipFile  压缩文件
     * @param extPlace 解压目录
     * @throws Exception
     */
     static boolean unZipFiles(File srcZipFile, String extPlace) throws Exception {
        System.setProperty("sun.zip.encoding",
                System.getProperty("sun.jnu.encoding"));
        try {
            (new File(extPlace)).mkdirs();
            if ((!srcZipFile.exists()) && (srcZipFile.length() <= 0)) {
                throw new Exception("要解压的文件不存在!");
            }
            ZipFile zipFile = new ZipFile(srcZipFile, "GBK"); // 处理中文文件名乱码的问题
            String strPath, gbkPath, strtemp;
            File tempFile = new File(extPlace);
            strPath = tempFile.getAbsolutePath();
            Enumeration<?> e = zipFile.getEntries();
            while (e.hasMoreElements()) {
                ZipEntry zipEnt = (ZipEntry) e.nextElement();
                gbkPath = zipEnt.getName();
                if (zipEnt.isDirectory()) {
                    strtemp = strPath + File.separator + gbkPath;
                    File dir = new File(strtemp);
                    dir.mkdirs();
                    continue;
                } else { // 读写文件
                    InputStream is = zipFile.getInputStream(zipEnt);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    gbkPath = zipEnt.getName();
                    strtemp = strPath + File.separator + gbkPath;// 建目录
                    String strsubdir = gbkPath;
                    for (int i = 0; i < strsubdir.length(); i++) {
                        if (strsubdir.substring(i, i + 1).equalsIgnoreCase("/")) {
                            String temp = strPath + File.separator
                                    + strsubdir.substring(0, i);
                            File subdir = new File(temp);
                            if (!subdir.exists()) {
                                subdir.mkdir();
                            }
                        }
                    }
                    FileOutputStream fos = new FileOutputStream(strtemp);
                    BufferedOutputStream bos = new BufferedOutputStream(fos);
                    int c;
                    while ((c = bis.read()) != -1) {
                        bos.write((byte) c);
                    }
                    bos.close();
                    fos.close();
                    bis.close();
                    is.close();
                }
            }
            return true;
        } catch (Exception e) {
            log.info(e.toString());
            return false;
        }
    }
    /**
     * 根据原始rar路径，解压到指定文件夹下.
     *
     * @param srcFile 原文件
     * @param dstDirectoryPath 解压到的文件夹
     */
     static void unRarFile(File srcFile, String dstDirectoryPath) {
        String srcFileName = srcFile.getName();
        if (!srcFileName.toLowerCase().endsWith(".rar")) {
            log.info("非rar文件！");
            return;
        }
        File dstDiretory = new File(dstDirectoryPath);
        if (!dstDiretory.exists()) {// 目标目录不存在时，创建该文件夹
            dstDiretory.mkdirs();
        }
        Archive a = null;
        try {
            a = new Archive((VolumeManager) new FileInputStream(srcFile));
            if (a != null) {
                FileHeader fh = a.nextFileHeader();
                while (fh != null) {
                    // 防止文件名中文乱码问题的处理
                    String fileName = fh.getFileNameW().isEmpty() ? fh
                            .getFileNameString() : fh.getFileNameW();
                    if(fileName.contains("/")  || fileName.contains("\\")){
                        fileName = fileName.replace("\\","/");
                    }else if(fileName.contains(".")){
                        fileName = srcFileName.substring(0,srcFileName.lastIndexOf(".")) + "/" + fileName;
                    }
                    if (fh.isDirectory()) { // 文件夹
                        File fol = new File(dstDirectoryPath + File.separator
                                + fileName);
                        fol.mkdirs();
                    } else { // 文件
                        File out = new File(dstDirectoryPath + File.separator
                                + fileName.trim());
                        try {
                            if (!out.exists()) {
                                if (!out.getParentFile().exists()) {// 相对路径可能多级，可能需要创建父目录.
                                    out.getParentFile().mkdirs();
                                }
                                out.createNewFile();
                            }
                            FileOutputStream os = new FileOutputStream(out);
                            a.extractFile(fh, os);
                            os.close();
                        } catch (Exception ex) {
                            log.info(ex.toString());
                        }
                    }
                    fh = a.nextFileHeader();
                }
                a.close();
            }
        } catch (Exception e) {
            log.info(e.toString());
        }
    }



    /**
     * 压缩方法
     * @param sourceFileName 原文件名(绝对路径)
     * @param zipFileName 压缩后的文件名(绝对路径)
     * @return
     */
    public static boolean zip(String sourceFileName, String zipFileName){
        boolean tag = false;
        try {
            ZipOutputStream out = new ZipOutputStream( new FileOutputStream(zipFileName));
            //创建缓冲输出流
            File sourceFile = new File(sourceFileName);
            //调用函数
            compress(out,sourceFile,sourceFile.getName());
            out.close();
            tag = true;
        } catch (Exception e) {
            log.info(e.toString());
        }
        return tag;
    }

    /**
     * 执行压缩
     * @param out 压缩输出流
     * @param sourceFile 源文件夹
     * @param base 压缩文件名
     * @throws Exception
     */
     static void compress(ZipOutputStream out,File sourceFile,String base) throws Exception {
        if(sourceFile.isDirectory()) {
            //取出文件夹中的文件（或子文件夹）
            File[] listFiles = sourceFile.listFiles();
            //如果文件夹为空，则只需在目的地zip文件中写入一个目录进入点
            if(listFiles.length==0){
                out.putNextEntry(  new java.util.zip.ZipEntry(base+"/") );
            }else{
                //如果文件夹不为空，则递归调用compress，文件夹中的每一个文件（或文件夹）进行压缩
                for(int i=0;i<listFiles.length;i++){
                    compress(out,listFiles[i],base+"/"+listFiles[i].getName());
                }
            }
        }else{
            //如果不是目录（文件夹），即为文件，则先写入目录进入点，之后将文件写入zip文件中
            out.putNextEntry( new java.util.zip.ZipEntry(base) );
            FileInputStream fis = new FileInputStream(sourceFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            byte[] buff = new byte[2048];
            int bytesRead = 0;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                out.write(buff, 0, bytesRead);
            }
            try {
                bis.close();
                fis.close();
            }catch (Exception e){
                log.info(e.toString());
            }

        }
    }
}
