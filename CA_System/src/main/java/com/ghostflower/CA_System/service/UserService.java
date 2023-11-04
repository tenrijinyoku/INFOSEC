package com.ghostflower.CA_System.service;

import com.ghostflower.CA_System.pojo.User;


public interface UserService {
    //查找用户
    User FindUser(String User);
    //注册
    void register(String User,String password);
}
