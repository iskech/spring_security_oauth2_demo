package com.iskech.gateway.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;


@Component
public class ApplicationSupport implements DisposableBean, ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /**
     * 
     * @param paramKey
     * @return
     */
    public static String getParamVal(String paramKey) {
        return applicationContext.getEnvironment().getProperty(paramKey);
    }

    /**
     * 
     * @param name
     * @return
     */
    public static Object getBean(String name) {
        Assert.hasText(name);
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }
    
    /**
     * 
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
    
    /**
     * 
     */
    @Override
    public void destroy() throws Exception {
        applicationContext = null;
    }

}
