package com.cw.auction.util.db.util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import com.cw.auction.util.db.listener.SpringContextLoaderListener;


/**
 * @Comments ：spring工具类
 * @Author ：陈伟
 * @Group : A组
 * @Worker: 1001
 * @Date ：2017年7月24日 下午5:18:27
 * @Project ：ktshop-service 
 * @Company ：枣庄康婷控股有限公司
 */
public final class SpringUtils {

    private static Logger logger = LoggerFactory.getLogger("SpringUtils");

    /**
     * 获取全局WebApplicationContext
     * 
     * @return 全局WebApplicationContext
     */
    public static WebApplicationContext getContext() {
        WebApplicationContext context = SpringContextLoaderListener.getWebRootAppContext();
        return context;
    }

    /**
     * 根据id获取bean
     * 
     * @return spring bean对象
     */
    public static Object getBean(String id) {
        Object bean = null;
        try {
            bean = getContext().getBean(id);
        } catch (RuntimeException e) {
            logger.error("SpringUtils.getBean(String id) get bean failure, bean id = " + id);
            throw e;
        }
        return bean;
    }
}
