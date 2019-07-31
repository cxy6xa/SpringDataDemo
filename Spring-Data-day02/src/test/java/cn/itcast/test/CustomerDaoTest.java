package cn.itcast.test;

import cn.itcast.dao.CustomerDao;
import cn.itcast.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@RunWith(SpringJUnit4ClassRunner.class)//声明spring提供的单元测试环境
@ContextConfiguration(locations = "classpath:applicationContext.xml")//指定spring容器的配置信息
public class CustomerDaoTest {

    @Autowired
    private CustomerDao customerDao;

    /**
     * 根据id查询
     */
    @Test
    public void testFindOne(){
        Customer customer = customerDao.findOne(2L);
        System.out.println(customer);
    }

    /**
     * save:保存或者更新
     *      根据传递的对象是否存在主键ID,如果没有id属性:保存
     *      如果存在ID主键属性,根据id查询数据然后更新
     */
    @Test
    public void testSave(){
        Customer customer = new Customer();
        customer.setCustName("黑马程序员");
        customer.setCustLevel("vip");
        customer.setCustIndustry("itttt");
        customerDao.save(customer);
    }

    @Test
    public void testUpdate(){
        Customer customer = new Customer();
        customer.setCustId(4L);
        customer.setCustName("黑马程序员很厉害");
        customer.setCustLevel("vip");
        customer.setCustIndustry("itttt");
        customerDao.save(customer);
    }

    /**
     * 更新规范做法,先查再更新
     */
    @Test
    public void testOnlyUpdate(){
        Long id = 100L;
        Customer oldCustomer = customerDao.findOne(id);
        if (Objects.isNull(oldCustomer)){
            //严格处理,抛出异常,给出明显提示
            throw new RuntimeException("试图更新一条不存在的数据");
            //如果查询不严格,可以直接return
        }
        oldCustomer.setCustName("传智播客testOnlyUpdate");
        Customer updateCustomer = customerDao.save(oldCustomer);
        System.out.println(updateCustomer);
    }

    @Test
    public void testDelete(){
        customerDao.delete(4l);

        //批量删除(先循环查询,再删除)
        //customerDao.deleteInBatch(deleteCustomerList);
    }

    /**
     * 删除的规范做法
     */
    @Test
    public void testDeleteNotExitsExceptionMessage(){
        Long id = 100L;
        Customer deleteCustomer = customerDao.findOne(id);
        if (Objects.isNull(deleteCustomer)){
            //严格处理,抛出异常,给出明显提示
            throw new RuntimeException("试图删除一条不存在的数据");
            //如果查询不严格,可以直接return
        }
        customerDao.delete(deleteCustomer);
    }

    /**
     * 查询全部:
     * 使用Lambda表达式,循环打印
     * 但是要将id为偶数的过滤掉
     */
    @Test
    public void testFindAllAndFilterId(){
        List<Customer> list = customerDao.findAll();
        list.stream()
                .filter(customer -> customer.getCustId() % 2 != 0)
                .forEach(System.out::println);
    }

    @Test
    public void testFindAll(){
        List<Customer> customers = customerDao.findAll();
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    @Test
    public void testCount(){
        long count = customerDao.count();//查询全部的客户数量
        System.out.println(count);
    }

    @Test
    public void testExists(){
        boolean b = customerDao.exists(4l);//判断id为4的客户是否存在
        System.out.println(b);
    }

    /**
     * 根据id从数据库查询
     * findone:
     *      em.find():立即记载
     * getone:
     *      em.getReference:延迟加载
     */
    @Test
    @Transactional//保证getOne正常运行
    public void testGetOne(){
        Customer customer = customerDao.getOne(7l);
        System.out.println(customer);
    }

}
