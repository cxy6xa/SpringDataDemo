package cn.itcast.test;

import cn.itcast.dao.CustomerDao;
import cn.itcast.dao.LinkManDao;
import cn.itcast.domain.Customer;
import cn.itcast.domain.LinkMan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)//声明spring提供的单元测试环境
@ContextConfiguration(locations = "classpath:applicationContext.xml")//指定spring容器的配置信息
public class OneToManyTest {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private LinkManDao linkManDao;

    /**
     * 保存一个客户,保存一个联系人
     */
    @Test
    @Transactional
    @Rollback(false)
    public void testAdd(){
        //创建一个客户,创建一个联系人
        Customer customer = new Customer();
        customer.setCustName("百度");
        LinkMan linkMan = new LinkMan();
        linkMan.setLkmName("小李");

        customer.getLinkMans().add(linkMan);
        customerDao.save(customer);
        linkManDao.save(linkMan);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void testAdd1(){
        //创建一个客户,创建一个联系人
        Customer customer = new Customer();
        customer.setCustName("百度");
        LinkMan linkMan = new LinkMan();
        linkMan.setLkmName("小李");

        linkMan.setCustomer(customer);

        customerDao.save(customer);
        linkManDao.save(linkMan);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void testAdd2(){
        //创建一个客户,创建一个联系人
        Customer customer = new Customer();
        customer.setCustName("百度");
        LinkMan linkMan = new LinkMan();
        linkMan.setLkmName("小李");

        linkMan.setCustomer(customer);
        customer.getLinkMans().add(linkMan);
        customerDao.save(customer);
        linkManDao.save(linkMan);
    }

    /**
     * 级联添加:保存一个客户的同时,保存客户的所有联系人
     *
     *      需要在操作主体的实体类上,配置Cascade属性
     */
    @Test
    @Transactional
    @Rollback(false)
    public void testCascadeAdd(){
        Customer customer = new Customer();
        customer.setCustName("百度1");
        LinkMan linkMan = new LinkMan();
        linkMan.setLkmName("小李1");

        linkMan.setCustomer(customer);
        customer.getLinkMans().add(linkMan);

        customerDao.save(customer);
    }

    /**
     * 级联删除:
     *      删除1号客户的同时,删除1号客户的所有联系人
     */
    @Test
    @Transactional
    @Rollback(false)
    public void testCascadeRemove(){
        //1.查询1号客户
        Customer customer = customerDao.findOne(1l);
        //2.删除1号客户
        customerDao.delete(customer);

    }
}
