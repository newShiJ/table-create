package mybatis.mybaitscreate.controller;

import mybatis.mybaitscreate.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenmingming
 * @date 2018/9/4
 */
@RestController
@RequestMapping("/user")
public class TestController {
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/all")
    public Object getAll(){
        return userMapper.findAll();
    }

}
