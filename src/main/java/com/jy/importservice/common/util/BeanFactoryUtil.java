package com.jy.importservice.common.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * @类名 BeanFactoryUtil
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/21 16:36
 * @版本 1.0
 */
@Component
public class BeanFactoryUtil implements BeanFactoryPostProcessor {

    public static ConfigurableListableBeanFactory beanFactory;

    //得到Spring应用上下文对象
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanFactoryUtil.beanFactory = beanFactory;
    }

    public static <T> T getBean(Class<T> clazz) {
        return beanFactory.getBean(clazz);
    }
}
