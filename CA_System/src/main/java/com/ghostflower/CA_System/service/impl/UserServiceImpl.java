package com.ghostflower.CA_System.service.impl;

import com.ghostflower.CA_System.mapper.UserMapper;
import com.ghostflower.CA_System.pojo.User;
import com.ghostflower.CA_System.service.UserService;
import com.ghostflower.CA_System.utils.AesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper um;

    @Override
    public User FindUser(String username){
        User u = um.FinderUser(username);
        return u;
    }

    @Override
    public void register(String username,String password){

        //加密（用写过的模块）
        //16为UID作为用户唯一标识
        um.add(AesUtil.getGUID(),username,password,System.currentTimeMillis());
    }
}
