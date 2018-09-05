package mybatis.mybaitscreate.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author chenmingming
 * @date 2018/9/4
 */
@Table(name = "user")
@Data
@Entity
public class User{

    private static final long serialVersionUID = 5199200306752426433L;

    @Id
    @Column(name = "id",length = 5)
    private Integer id;

    @Column(name = "name")
    private String  name;

    @Column(name = "description")
    private String  description;

    @Column(name = "create_time")
    private String create_time;

    @Column(name = "update_time")
    private String update_time;

    @Column(name = "number",length = 10)
    private Long number;

    @Column(name = "lifecycle")
    private String lifecycle;

    @Column(name = "dekes",length = 10)
    private Double  dekes;

}
