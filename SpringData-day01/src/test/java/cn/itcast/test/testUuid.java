package cn.itcast.test;

import cn.itcast.domain.UuidTest;
import cn.itcast.utils.JpaUtils;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class testUuid {

    @Test
    public void uuid(){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("myJpa");
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        for (int i = 0; i < 20; i++) {
            UuidTest uuidTest = new UuidTest();
            uuidTest.setName("测试uuid-"+i);
             em.persist(uuidTest);
        }
        tx.commit();
        em.close();
    }
}
