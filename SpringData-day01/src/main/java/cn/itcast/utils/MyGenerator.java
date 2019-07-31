package cn.itcast.utils;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentityGenerator;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 自定义主键生成策略
 *
 * [注意]：要继承Hibernate的IdentityGenerator类
 *
 * 重写generate方法
 *
 */
public class MyGenerator extends IdentityGenerator {
    private static final long SEQUENCE_MASK = 100000L; // 5位序列号，最大99999
    private volatile long time = 0L;
    private volatile int sequence = 0;

    @Override
    public Serializable generate(SessionImplementor session, Object object) throws HibernateException {
        Object id = newUniqueId();
        if (id != null) {
            return (Serializable) id;
        }
        return super.generate(session, object);
    }

    public synchronized Long newUniqueId() {
        while (true) {
            long now = formatDateTimeAsLong(LocalDateTime.now());
            if (time != now) {
                // 时间有变化，重置序列号
                time = now;
                sequence = 0;
            } else {
                // 时间没有变化，序列号递增
                sequence++;
                if (sequence >= SEQUENCE_MASK) {
                    // 序列号溢出，自旋
                    continue;
                }
            }
            return (time * SEQUENCE_MASK) + sequence;
        }
    }

    public static long formatDateTimeAsLong(LocalDateTime dateTime) {
        return dateTime.getYear() * 10000000000L
                + dateTime.getMonthValue() * 100000000L
                + dateTime.getDayOfMonth() * 1000000L
                + dateTime.getHour() * 10000L
                + dateTime.getMinute() * 100L
                + dateTime.getSecond();
    }
}
