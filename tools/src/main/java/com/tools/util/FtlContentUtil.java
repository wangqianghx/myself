package com.tools.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.io.output.StringBuilderWriter;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

/**
 * 通过Freemarker 模板引擎生成内容
 * - 目前主要应用于生成邮件正文内容
 *
 * @author jyusun at 2019-08-01
 */
public class FtlContentUtil {

    /**
     * 获取内容
     *
     * @param ftlName 模板名称
     * @param dataMap Map<String, Object>类型的数据
     * @param ftlPath 模板存放位置
     * @return String字符串
     */
    public static String getContent(String ftlName, String ftlPath, Map<String, Object> dataMap) {
        //通过Template可以将模板文件输出到相应的流
        Template temp = getFtlTemplate(ftlName, ftlPath);
        try (StringBuilderWriter out = new StringBuilderWriter()) {
            temp.process(dataMap, out);
            Assert.notNull(out, "Nothing in the template");
            return out.toString();
        } catch (TemplateException | IOException e) {
            throw new RuntimeException("生成内容参数配置错误", e);
        }
    }

    /**
     * 获取指定的模板
     *
     * @param ftlName 模板名称
     * @param ftlPath 模板存放路径
     * @return Template
     */
    private static Template getFtlTemplate(String ftlName, String ftlPath) {

        // 通过FreeMaker的Configuration读取相应的ftl
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
        cfg.setEncoding(Locale.CHINA, "utf-8");

        Template template = null;
        // 在模板文件目录中找到名称为name的文件
        try {
            // 设定去哪里读取相应的ftl模板文件
            cfg.setDirectoryForTemplateLoading(new File(ftlPath));
            // 设置ftl模板文件加载方式
            template = cfg.getTemplate(ftlName);
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return template;
    }
}
