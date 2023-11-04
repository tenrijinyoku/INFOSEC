package com.ghostflower.CA_System.mapper;

import com.ghostflower.CA_System.pojo.Ca;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;



@Mapper
public interface CaMapper {
    //从用户UID(逐渐)寻找对应证书,证书serial与UID分离开，独立存在(有浪费空间的嫌疑？？？)

    @Select("select * from Ca where uid = #{uid}")
    Ca FindCa(String uid);

    @Insert("insert into Ca(uid,serial,alg,publickey,privatekey,startdate,enddate,issuer,version)" +
            " values(#{uid},#{serial},#{alg},#{publicKey},#{privateKey},#{startDate},#{endDate},#{issuer},#{version})")
    void Add(String uid,String serial, String alg,String publicKey,String privateKey,long startDate,long endDate,String issuer,String version );


}
