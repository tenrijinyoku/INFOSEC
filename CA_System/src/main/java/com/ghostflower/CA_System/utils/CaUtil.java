package com.ghostflower.CA_System.utils;


import com.ghostflower.CA_System.pojo.Ca;
import org.javatuples.Pair;

/*
基于rsa的证书设计与生成
@return
 */
public class CaUtil {


    //生成密钥对
    public static Pair<String,String> GetRsaKeyTuple_final(){

        Pair<String,String> RsaKeyTuple = RsaUtils.GetRsaKeyTuple();
        return RsaKeyTuple;

    }
    /*
    生成证书(先实例化再赋值)
     */
    public static Ca generateCA(){
        Ca ca = new Ca();
        ca.serial = AesUtil.getGUID();//16位
        ca.startDate=System.currentTimeMillis();
        ca.endDate = ca.startDate+60*60*24*1000;
        ca.keyPairs = GetRsaKeyTuple_final();
        return ca;
    }

}

