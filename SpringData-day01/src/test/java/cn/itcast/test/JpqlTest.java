package cn.itcast.test;

import cn.itcast.utils.JpaUtils;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class JpqlTest {

    /**
     * 查询全部
     *      jpql:from Customer
     *      sql:select * from cst_customer
     */
    @Test
    public void testFindAll(){
        //获取entityManager对象
        EntityManager entityManager = JpaUtils.getEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        //开启事务
        tx.begin();
        //查询全部
        String jpql = "from Customer";
        Query query = entityManager.createQuery(jpql);//创建Query查询对象,query对象才是执行jpql的对象

        //发送查询,并封装结果集
        List list = query.getResultList();
        for (Object o : list) {
            System.out.println(o);
        }
        //提交事务
        tx.commit();
        //释放资源
        entityManager.close();
    }


    @Test
    public void testOrderBy(){
        //获取entityManager对象
        EntityManager entityManager = JpaUtils.getEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        //开启事务
        tx.begin();
        //查询全部
        String jpql = "from cn.itcast.domain.Customer order by custId desc";
        Query query = entityManager.createQuery(jpql);//创建Query查询对象,query对象才是执行jpql的对象

        //发送查询,并封装结果集
        List list = query.getResultList();
        for (Object o : list) {
            System.out.println(o);
        }
        //提交事务
        tx.commit();
        //释放资源
        entityManager.close();
    }

    /**
     * 统计查询:
     *      sql: select count(cust_id) from cst_customer
     *      jpql: select count(custId) from Customer
     */
    @Test
    public void testCount(){
        //获取entityManager对象
        EntityManager entityManager = JpaUtils.getEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        //开启事务
        tx.begin();
        //查询全部
        String jpql = "select count(custId) from Customer";
        Query query = entityManager.createQuery(jpql);//创建Query查询对象,query对象才是执行jpql的对象

        Object result = query.getSingleResult();
        System.out.println(result);

        //提交事务
        tx.commit();
        //释放资源
        entityManager.close();
    }

    /**
     * 分页查询
     *      sql:select * from cst_customer limit?,?
     *      jqpl:from Customer
     */
    @Test
    public void testPaged(){
        //获取entityManager对象
        EntityManager entityManager = JpaUtils.getEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        //开启事务
        tx.begin();

        String jpql = "from Customer";
        Query query = entityManager.createQuery(jpql);//创建Query查询对象,query对象才是执行jpql的对象
        //对参数赋值 --分页参数
        //起始索引
        query.setFirstResult(0);
        //每页查询的条数
        query.setMaxResults(2);

        List list = query.getResultList();
        for (Object o : list) {
            System.out.println(o);
        }

        //提交事务
        tx.commit();
        //释放资源
        entityManager.close();
    }

    /**
     * 条件查询
     *      查询客户名称以"传智播客"开头的客户
     *      sql:select * from cst_customer where cust_name like ?
     *      jqpl:from Customer where custName like ?
     */
    @Test
    public void testCondition(){
        //获取entityManager对象
        EntityManager entityManager = JpaUtils.getEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        //开启事务
        tx.begin();

        String jpql = "from Customer where custName like ?";
        Query query = entityManager.createQuery(jpql);//创建Query查询对象,query对象才是执行jpql的对象
        //对参数赋值 --占位符参数
        //第一个参数:占位符的索引位置(从1开始),第二个参数:取值
        query.setParameter(1,"传智播客%");

        List list = query.getResultList();
        for (Object o : list) {
            System.out.println(o);
        }

        //提交事务
        tx.commit();
        //释放资源
        entityManager.close();
    }

    /**
     * 模糊查询+排序+分页
     */
    @Test
    public void test(){
        //获取entityManager对象
        EntityManager entityManager = JpaUtils.getEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        //开启事务
        tx.begin();

        String jpql = "from Customer where custName like ? order by custId desc";
        Query query = entityManager.createQuery(jpql);//创建Query查询对象,query对象才是执行jpql的对象
        //对参数赋值 --占位符参数
        //第一个参数:占位符的索引位置(从1开始),第二个参数:取值
        query.setParameter(1,"传智播客%");
        //对参数赋值 --分页参数
        //起始索引
        query.setFirstResult(0);
        //每页查询的条数
        query.setMaxResults(2);

        List list = query.getResultList();
        for (Object o : list) {
            System.out.println(o);
        }

        //提交事务
        tx.commit();
        //释放资源
        entityManager.close();
    }
}
