package cn.itcast.test;

import cn.itcast.domain.GeneratorTest;
import cn.itcast.utils.JpaUtils;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class testGenerator {

    @Test
    public void generator(){
        EntityManager em = JpaUtils.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        for (int i = 0; i < 20; i++) {
            GeneratorTest generatorTest = new GeneratorTest();
            generatorTest.setName("测试generator"+i);
            em.persist(generatorTest);
        }
        tx.commit();
        em.close();
    }
}
