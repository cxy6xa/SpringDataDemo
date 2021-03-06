package cn.itcast.test;

import cn.itcast.dao.CustomerDao;
import cn.itcast.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)//声明spring提供的单元测试环境
@ContextConfiguration(locations = "classpath:applicationContext.xml")//指定spring容器的配置信息
public class JpqlTest {

    @Autowired
    private CustomerDao customerDao;

    @Test
    public void testFindJpql(){
        Customer customer = customerDao.findJpql("黑马");
        System.out.println(customer);
    }

    @Test
    public void testFindCustNameAndId(){
        Customer customer = customerDao.findCustNameAndId(1l,"百度");
        System.out.println(customer);
    }

    /**
     * springDataJpa中使用jpql完成  更新/删除操作
     *      需要手动添加事务的支持
     *      默认会在执行结束之后,回滚事务
     *      @Rollback:设置是否回滚
     */
    @Test
    @Transactional//添加事务支持
    @Rollback(false)
    public void testUpdateCustomer(){
        customerDao.updateCustomer(5l,"黑马喵喵喵");
    }

    @Test
    public void testFindSql(){
        List<Object[]> list = customerDao.findSql();
        for (Object[] obj : list) {
            System.out.println(Arrays.toString(obj));
        }
    }

    @Test
    public void testFindSql2(){
        List<Object[]> list = customerDao.findSql2("传智播客%");
        for (Object[] obj : list) {
            System.out.println(Arrays.toString(obj));
        }
    }

    @Test
    public void testFindByCustName(){
        Customer customer = customerDao.findByCustName("黑马");
        System.out.println(customer);
    }

    @Test
    public void testFindByCustNameLike(){
        List<Customer> customers = customerDao.findByCustNameLike("传智播客%");
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    @Test
    public void testFindByCustNameLikeAndCustIndustry(){
        Customer customer = customerDao.findByCustNameLikeAndCustIndustry("传智播客%", "it教育");
        System.out.println(customer);
    }
}
