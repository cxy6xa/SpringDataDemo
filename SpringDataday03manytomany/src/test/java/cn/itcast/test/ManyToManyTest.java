package cn.itcast.test;

import cn.itcast.dao.RoleDao;
import cn.itcast.dao.UserDao;
import cn.itcast.domain.Role;
import cn.itcast.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)//声明spring提供的单元测试环境
@ContextConfiguration(locations = "classpath:applicationContext.xml")//指定spring容器的配置信息
public class ManyToManyTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    /**
     * 多对多放弃维护权:被动的一方放弃
     */
    @Test
    @Transactional
    @Rollback(false)
    public void testAdd(){
        User user = new User();
        user.setUserName("小王");

        Role role = new Role();
        role.setRoleName("java程序员");

        user.getRoles().add(role);//配置用户到角色的关系
        role.getUsers().add(user);//配置角色到用户的关系

        userDao.save(user);
        roleDao.save(role);
    }

    /**
     * 测试级联添加(保存一个用户的同时保存用户的关联角色)
     */
    @Test
    @Transactional
    @Rollback(false)
    public void testCascadeAdd(){
        User user = new User();
        user.setUserName("小王");

        Role role = new Role();
        role.setRoleName("java程序员");

        user.getRoles().add(role);//配置用户到角色的关系
        role.getUsers().add(user);//配置角色到用户的关系

        userDao.save(user);

    }

    @Test
    @Transactional
    @Rollback(false)
    public void testCascadeRemove(){
        User one = userDao.findOne(1l);
        userDao.delete(one);

    }
}
