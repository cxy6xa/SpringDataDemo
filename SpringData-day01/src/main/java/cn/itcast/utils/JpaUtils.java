package cn.itcast.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * 解决实体管理器工厂的浪费资源和耗时问题
 *      通过静态代码块的形式,当程序第一次访问此工具类,创建一个公共的实体管理器对象
 */
public class JpaUtils {

    private static EntityManagerFactory factory;

    static {
        //1.加载配置文件,创建entityManagerFactory
        factory = Persistence.createEntityManagerFactory("myJpa");
    }

    /**
     * 获取EntityManager对象
     */
    public static EntityManager getEntityManager(){
        return factory.createEntityManager();
    }
}
