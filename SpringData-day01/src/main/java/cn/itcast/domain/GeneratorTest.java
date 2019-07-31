package cn.itcast.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "generator_test")
@GenericGenerator(name = "my-generator",strategy = "cn.itcast.utils.MyGenerator")
public class GeneratorTest {

    @Id
    @GeneratedValue(generator = "my-generator")
    @Column(name = "id")
    public Long id;

    @Column(name = "name")
    public String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "GeneratorTest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
