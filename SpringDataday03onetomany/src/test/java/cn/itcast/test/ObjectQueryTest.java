package cn.itcast.test;

import cn.itcast.dao.CustomerDao;
import cn.itcast.dao.LinkManDao;
import cn.itcast.domain.Customer;
import cn.itcast.domain.LinkMan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)//声明spring提供的单元测试环境
@ContextConfiguration(locations = "classpath:applicationContext.xml")//指定spring容器的配置信息
public class ObjectQueryTest {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private LinkManDao linkManDao;

    /**
     * 测试对象导航查询(查询一个对象的时候,通过此对象查询所有关联对象)
     */
    @Test
    @Transactional
    public void testQuery1(){
        Customer customer = customerDao.getOne(1l);
        //对象导航查询
        Set<LinkMan> linkMans = customer.getLinkMans();
        for (LinkMan linkMan : linkMans) {
            System.out.println(linkMan);
        }
    }

    /**
     * 对象导航查询:
     * 默认使用的是延迟加载的形式查询
     *      调用get方法并不会立即发送查询,而是在使用关联对象的时候才会查询
     * 修改配置,将延迟改为立即加载
     *      fetch:需要配置到多表映射关系的注解上
     */
    @Test
    @Transactional
    public void testQuery2(){
        Customer customer = customerDao.findOne(1l);
        //对象导航查询
        Set<LinkMan> linkMans = customer.getLinkMans();

        System.out.println(linkMans.size());
    }

    /**
     * 从联系人对象导航查询他的所属客户
     * 默认:立即加载
     * 修改配置,将延迟改为延迟加载
     *      fetch:需要配置到多表映射关系的注解上
     */
    @Test
    @Transactional
    public void testQuery3(){
        LinkMan linkMan = linkManDao.findOne(2l);
        //对象导航查询
        Customer customer = linkMan.getCustomer();
        System.out.println(customer);
    }
}
