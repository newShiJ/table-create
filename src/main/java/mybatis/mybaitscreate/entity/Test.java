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
@Entity
@Table(name = "test")
@Data
public class Test {

    @Id
    @Column(name = "id",length = 200,nullable = false)
    private String id;

    @Column(name = "name",length = 200,nullable = false)
    private String name;

    @Column(name = "json_key", columnDefinition = "varchar(40) not null comment '推送的json 的key'")
    private String jsonKey;

    @Column(name = "column_index", columnDefinition = "varchar(40) not null comment 'Excel 中展示的列索引'")
    private Integer columnIndex;

    @Column(name = "current_amount", columnDefinition="bigint not null comment '银单当前金额:(金额精确到分)'")
    private Long currentAmount;

    @Column(name = "root_amount", columnDefinition="bigint not null comment '银单当前金额:(金额精确到分)'")
    private Long rootAmount;
}
