package mybatis.mybaitscreate.dao;

import mybatis.mybaitscreate.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author chenmingming
 * @date 2018/9/4
 */
@Mapper
public interface UserMapper {
    List<User> findAll();
}
