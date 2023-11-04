package com.ghostflower.CA_System.mapper;

import com.ghostflower.CA_System.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface UserMapper {

    @Select("select * from user where username = #{username}")
    User FinderUser(String username);


    @Insert("insert into user(uid,username,password,createtime)" +
            " values(#{uid},#{username},#{password},#{createTime})")

    void add(String uid,String username, String password,long createTime);
}
