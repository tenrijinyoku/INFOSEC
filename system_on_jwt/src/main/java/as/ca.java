package as;


import org.javatuples.Pair;

import java.util.Date;

/*
基于rsa的证书设计与生成
@return
 */
public class ca {
    public String version;//版本号
    public String serial;//序列号
    public String alg;//加密算法

    public String issuer;//签发者
    public long startdate;//生效时间
    public long enddate;//终止时间
    public Pair<String,String> keypairs;//RSA密钥对

    //生成密钥对
    public Pair<String,String> GetRsaKeyTuple_final(){

        Pair<String,String> RsaKeyTuple = RSA.GetRsaKeyTuple();
        return RsaKeyTuple;

    }
    /*
    生成证书(先实例化再赋值)
     */
    public void generateCA(){
        serial=AES.getGUID();//16位
        alg="RSA";
        issuer="LZY";
        startdate=System.currentTimeMillis();
        enddate=startdate+60*60*24*1000;
        GetRsaKeyTuple_final();
        keypairs=GetRsaKeyTuple_final();
        System.out.println(keypairs.getValue0()+"\n"+keypairs.getValue1());
    }

}

