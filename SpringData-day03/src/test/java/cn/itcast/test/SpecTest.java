package cn.itcast.test;

import cn.itcast.dao.CustomerDao;
import cn.itcast.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.criteria.*;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)//声明spring提供的单元测试环境
@ContextConfiguration(locations = "classpath:applicationContext.xml")//指定spring容器的配置信息
public class SpecTest {

    @Autowired
    private CustomerDao customerDao;

    /**
     * 根据条件:查询单个对象
     */
    @Test
    public void testSpec(){
        //匿名内部类
        /**
         * 自定义查询条件
         *      1.实现Specification接口(提供泛型:查询的对象类型)
         *      2.实现toPredicate方法(构造查询条件)
         *      3.需要借助方法参数中的两个参数(root:获取需要查询的对象属性   CriteriaBuilder:构造查询条件的,内部封装了很多查询条件)
         *
         * 案例:根据用户名称查询,查询用户为传智播客的客户
         *      查询条件:
         *         1.查询方式
         *              cb对象
         *         2.比较的属性
         *              root对象
         */
        Specification<Customer> spec = new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //1.获取比较的属性
                Path<Object> custName = root.get("custName");
                //2.构造查询条件
                Predicate predicate = cb.equal(custName, "传智播客");//(比较的属性,比较的属性的取值)
                return predicate;
            }
        };
        Customer customer = customerDao.findOne(spec);
        System.out.println(customer);
    }

    /**
     * 多条件查询:
     *      案例:根据客户名和客户所属行业查询
     */
    @Test
    public void testSpec1(){
        Specification<Customer> spec = new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<Object> custName = root.get("custName");
                Path<Object> custIndustry = root.get("custIndustry");
                Predicate p1 = cb.equal(custName, "传智播客");
                Predicate p2 = cb.equal(custIndustry, "it");
                //将多个查询条件组合到一起
                Predicate and = cb.and(p1, p2);
                return and;
            }
        };
        Customer customer = customerDao.findOne(spec);
        System.out.println(customer);
    }

    /**
     * 案例:根据客户名称的模糊匹配,返回客户列表
     *
     * like:得到path对象,根据path对象指定比较的参数类型,再去进行比较
     *      指定参数类型:path.as(类型的字节码对象)
     */
    @Test
    public void testSpec2(){
        Specification<Customer> spec = new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<Object> custName = root.get("custName");
                //模糊查询
                Predicate predicate = cb.like(custName.as(String.class), "传智播客%");
                return predicate;
            }
        };
        /*List<Customer> customers = customerDao.findAll(spec);
        for (Customer customer : customers) {
            System.out.println(customer);
        }*/
        //添加排序
        //创建排序对象
        /*
            第一个参数:排序的顺序
            Sort.Direction.DESC:倒序
            Sort.Direction.ASC:升序
            第二个参数:排序的属性名称
         */
        Sort sort = new Sort(Sort.Direction.DESC,"custId");
        List<Customer> customers = customerDao.findAll(spec, sort);
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    /**
     * 分页查询
     *      Specification:查询条件
     *      Pageable:分页参数
     *      findAll(Specification,Pageable)
     * 返回:Page(springDataJpa为我们封装好的pageBean对象,里面有数据列表,总条数)
     */
    @Test
    public void testSpec3(){
        Specification spec = null;
        //PageRequest是pageable的实现类
        /**
         * 创建PageRequest的过程中:需要调用它的构造方法传入两个参数
         *      第一个参数:当前查询的页数(从0开始)
         *      第二个参数:每页查询的数量
         */
        Pageable pageable = new PageRequest(0,2);
        //分页查询
        Page page = customerDao.findAll(spec, pageable);
        System.out.println(page.getContent());//得到数据集合列表
        System.out.println(page.getTotalElements());//得到总条数
        System.out.println(page.getTotalPages());//得到总页数
    }
}
