package com.tools.todo;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 描述：获取上下文工具类
 *
 * @author zhangyu at 2018/11/9 11:20
 * @version 1.0.0
 */
@Component
public class WebSpringContextUtil implements ApplicationContextAware {
    /** Spring应用上下文环境 */
    private static ApplicationContext applicationContext;

    /**
     * 获取对象
     *
     * @return Object 一个以所给名字注册的bean的实例
     */
    public static Object getBean(String name) throws BeansException {
        return applicationContext.getBean(name);
    }

    /**
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        WebSpringContextUtil.applicationContext = applicationContext;
    }
}
