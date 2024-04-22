package com.tencent.demo.controller;

import com.tencent.demo.entity.User;
import com.tencent.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * DBController
 *
 * @author torrisli
 * @date 2024/4/19
 * @Description: DBController
 */
@RestController
public class DBController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/user/get")
    public User getUser(@RequestParam String userid) {
        return userMapper.findByUserid(userid);
    }

    @GetMapping("/user/update")
    public int updateUser(@RequestParam String username, @RequestParam String password, @RequestParam String userid) {
        return userMapper.update(username, password, userid);
    }
}
