package com.dooool.OrderServer.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.PropertySources;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * Created by ck on 2016/6/22.
 *
 * @author ck
 * @version 1.0
 */
public class PropertyUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    private static volatile Properties properties;

    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        PropertyUtils.applicationContext = applicationContext;
    }


    /**
     * 取得存储在静态变量中的ApplicationContext.
     * @author 程康，时间：2013-9-24 下午5:27:44
     * @return
     */
    private static ApplicationContext getApplicationContext(){
        checkApplicationContext();
        return applicationContext;
    }

    @SuppressWarnings("unchecked")
    /**
     * 从application中获取bean
     * @author 程康，时间：2013-9-24 下午5:45:42
     * @param name
     * @return
     */
    private static <T> T getBean(String name){
        checkApplicationContext();
        return (T)applicationContext.getBean(name);
    }
    /**
     * 从application中获取bean
     * @author 程康，时间：2013-9-24 下午5:52:57
     * @param clazz
     * @return
     */
    private static <T> T getBean(Class<T> clazz){
        checkApplicationContext();
        return (T)applicationContext.getBean(clazz);
    }


    public static void checkApplicationContext(){
        if(applicationContext == null){
            StringBuilder builder = new StringBuilder("The SpringUtils has not been configuring Spring configuration file。Maybe you can do it\r\n");
            builder.append("<bean id=\"springUtils\" class=\"com.lifang.utils.PropertyUtils\" lazy-init=\"false\" />\r\n");
            throw new IllegalStateException(builder.toString());

        }
    }


    public static String getProperty(String key){
        initProperties();
        return properties.getProperty(key);
    }

    public static String getProperty(String key,String defaultValue){
        initProperties();
        return properties.getProperty(key,defaultValue);
    }

    public static Properties getProperties(){
        initProperties();
        return properties;
    }

    private static void initProperties(){
        if(properties == null){
            synchronized (PropertyUtils.class){
                if(properties == null){
                    PropertySourcesPlaceholderConfigurer configurer = PropertyUtils.getBean(PropertySourcesPlaceholderConfigurer.class);
                    PropertySources propertySources = configurer.getAppliedPropertySources();
                    Iterator<PropertySource<?>> iterator = propertySources.iterator();
                    while (iterator.hasNext()){
                        PropertySource propertySource = iterator.next();
                        if("localProperties".equals(propertySource.getName())){
                            Map<?,?> map = (Map) propertySource.getSource();
                            if(map != null){
                                //这里不实例化properties，是因为new这个操作本身就不是原子类型的
                                //另外还有一个就是，需要将从propertySource获取的可以全部添加到properties中，properties才能对外正常服务
                                Properties prop = new Properties();
                                for(Map.Entry<?,?> entry:map.entrySet()){
                                    prop.put(entry.getKey().toString(),entry.getValue().toString());
                                }
                                properties = prop;
                            }
                        }
                    }
                }
            }
        }
    }
}
