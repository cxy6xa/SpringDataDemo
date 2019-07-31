package cn.itcast.test;

import cn.itcast.domain.Customer;
import cn.itcast.utils.JpaUtils;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaTest {

    /**
     * 测试Jpa的保存
     *      案例:保存一个客户到数据库中
     * Jpa的操作步骤:
     *      1.加载配置文件,创建工厂(实体管理类工厂)对象
     *      2.通过工厂获取实体管理器
     *      3.获取事务对象,开启事务
     *      4.完成增删改查操作
     *      5.提交事务(回滚)
     *      6.释放资源
     */
    @Test
    public void testSave(){
        //1.加载配置文件,创建工厂(实体管理类工厂)对象
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("myJpa");

        //2.通过工厂获取实体管理器
        EntityManager em = factory.createEntityManager();

        //3.获取事务对象,开启事务
        EntityTransaction tx = em.getTransaction();
        tx.begin();//开启事务

        //4.完成增删改查操作
        Customer customer = new Customer();
        customer.setCustName("传智播客");
        customer.setCustIndustry("教育");
        //保存
        em.persist(customer);

        //5.提交事务(回滚)
        tx.commit();

        //6.释放资源
        em.close();
        factory.close();
    }

    /**
     * 使用find方法查询:
     *      1.查询的对象就是当前客户对象本身
     *      2.在调用find方法的时候,就会发送sql语句查询数据库
     * 立即加载
     */
    @Test
    public void testFind(){

        EntityManager em = JpaUtils.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        //查询
        Customer customer = em.find(Customer.class, 1L);
        System.out.println(customer);

        tx.commit();
        //6.释放资源
        em.close();
    }

    /**
     * 使用getReference方法
     *      1.获取的对象是一个动态代理对象
     *      2.调用getReference方法不会立即发送sql语句查询数据库
     *              当调用查询结果对象的时候,才会发送查询的sql语句,什么时候使用,什么时候发送查询语句
     * 延迟加载(懒加载)
     */
    @Test
    public void testReference(){

        EntityManager em = JpaUtils.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        //查询
        Customer customer = em.getReference(Customer.class, 1L);
        System.out.println(customer);

        tx.commit();
        //6.释放资源
        em.close();
    }

    @Test
    public void testRemove(){

        EntityManager em = JpaUtils.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        //删除

        //1.根据id查询客户
        Customer customer = em.find(Customer.class, 1L);
        //2.调用remove完成删除
        em.remove(customer);

        tx.commit();
        //6.释放资源
        em.close();
    }

    @Test
    public void testUpdate(){

        EntityManager em = JpaUtils.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        //更新

        //1.根据id查询客户
        Customer customer = em.find(Customer.class, 2L);
        customer.setCustName("黑马");
        //2.调用remove完成更新
        em.merge(customer);

        tx.commit();
        //6.释放资源
        em.close();
    }

}
