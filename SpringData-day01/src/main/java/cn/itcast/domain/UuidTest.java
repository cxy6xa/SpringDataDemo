package cn.itcast.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "uuid_test")
@GenericGenerator(name = "jpa_uuid",strategy = "uuid")
public class UuidTest {
    @Id
    @GeneratedValue(generator = "jpa_uuid")
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Override
    public String toString() {
        return "GeneratorTest{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
