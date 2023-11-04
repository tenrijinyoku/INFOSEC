package com.ghostflower.CA_System.pojo;



import lombok.Data;

//lombok  在编译阶段,为实体类自动生成setter  getter toString
// pom文件中引入依赖   在实体类上添加注解
@Data
public class User {


    private String uid;//主键ID
    private String userName;//用户名
    private String passWord;//密码
    private long createTime;//创建时间




}
