package com.ghostflower.CA_System.service;

import com.ghostflower.CA_System.pojo.Ca;

public interface CaService {
    //查找用户
    Ca FindCa(String token);
    //注册
    void Apply(String token);
}
