package cn.itcast.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 配置映射关系
 *      1.配置实体类和表的映射关系
 *          @Entity:声明实体类
 *          @Table:配置实体类和表的映射关系
 *               name属性:配置数据库表的名称
 *
 *      2.配置实体类中属性和表中字段的映射关系
 *
 */
@Entity
@Table(name = "cst_customer")
public class Customer {

    /**
     * @Id:声明主键的配置
     * @GeneratedValue:配置主键的生成策略
     *      GenerationType.IDENTITY:自增
     * @Column:配置属性和字段的映射关系
     *      name:代表数据库中表的字段名
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cust_id")
    private Long custId;

    @Column(name = "cust_name")
    private String custName;

    @Column(name = "cust_source")
    private String custSource;

    @Column(name = "cust_industry")
    private String custIndustry;

    @Column(name = "cust_level")
    private String custLevel;

    @Column(name = "cust_address")
    private String custAddress;

    @Column(name = "cust_phone")
    private String custPhone;

    //配置客户和联系人之间的关系(一对多关系)
    /**
     * 使用注解的形式配置多表关系
     *      1.声明关系
     *          @OneToMany:配置一对多关系
     *              targetEntity:对方对象的字节码对象
     *      2.配置外键(中间表)
     *          @JoinColumn:配置外键
     *              name:外键字段名称
     *              referencedColumnName:参照的主表的主键字段名
     */
    //@OneToMany(targetEntity = LinkMan.class)
    //@JoinColumn(name = "lkm_cust_id",referencedColumnName = "cust_id")

    /**
     * 放弃外键维护权
     *      mappedBy:对方配置关系的属性名称
     * cascade:配置级联(可以配置到设置多表的映射关系的注解上)
     *      CascadeType.ALL:所有
     *                  MERGE:更新
     *                  PERSIST:保存
     *                  REMOVE:删除
     *
     * fetch:配置关联对象的加载方式:
     *          EAGER:立即加载
     *          LAZY:延迟加载
      */
    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<LinkMan> linkMans = new HashSet<>();


    public Set<LinkMan> getLinkMans() {
        return linkMans;
    }

    public void setLinkMans(Set<LinkMan> linkMans) {
        this.linkMans = linkMans;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustSource() {
        return custSource;
    }

    public void setCustSource(String custSource) {
        this.custSource = custSource;
    }

    public String getCustIndustry() {
        return custIndustry;
    }

    public void setCustIndustry(String custIndustry) {
        this.custIndustry = custIndustry;
    }

    public String getCustLevel() {
        return custLevel;
    }

    public void setCustLevel(String custLevel) {
        this.custLevel = custLevel;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

}
