package com.ghostflower.CA_System.pojo;

import lombok.Data;
import org.javatuples.Pair;
@Data
public class Ca {
    public String version="0.0.1";//版本号
    public String serial;//序列号
    public String alg="RSA";//加密算法

    public String issuer="GhostFlower";//签发者
    public long startDate;//生效时间
    public long endDate;//终止时间
    public Pair<String,String> keyPairs;//RSA密钥对
}
