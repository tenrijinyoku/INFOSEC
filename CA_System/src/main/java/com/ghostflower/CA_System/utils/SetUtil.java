package com.ghostflower.CA_System.utils;

import com.ghostflower.CA_System.pojo.Ca;
import org.javatuples.Pair;
import org.javatuples.Triplet;


/*SET协议实现：
步骤：
包装过程使用数字信封
1.购买初始化请求：用户c向商家M请求交易号，消息一
2.回答：M指派交易号，签名后，消息二发送给c
3.购买请求：c验证消息二后发送购买请求消息三给M，其中包含（OI，PI），详细过程见后面
4.授权与获取请求：M验证后，产生授权请求到支付网关P，消息4
5.P回答：验证后，看PI，OI，持卡人是否一致，通过则发消息给M，消息5
6.购买回答：M收到5后，转发给c，消息六。
@return
 */
public class SetUtil {


}

/*双重签名
@return
 */
class DoubleSignature {

    public String OI;//订单信息->商家
    public String PI;//支付信息->银行
    public String OP;//HOI+HPI
    public String HOI;
    public String HPI;
    public String HOP;
    public Triplet messToSeller = new Triplet(OI, HPI, HOP);
    public Triplet messToBank = new Triplet(HOI, PI, HOP);

    //判断签名是否正确
    public static boolean CheckTheSignature(String HPart, String OPart, String HOP) {
        String HOPart = SignatureUtil.sign(OPart, "test key");
        String OP = HOPart + HPart;
        String HOPGeted = SignatureUtil.sign(OP, "test key");
        return HOPGeted != null && HOPGeted.equals(HOP);


    }

    //生成签名
    public void GenerateSignature(String OI, String PI) {
        String HOI = SignatureUtil.sign(OI, "test key");
        String HPI = SignatureUtil.sign(PI, "test key");
        String OP = HPI + HPI;
        String HOP = SignatureUtil.sign(OP, "test key");
        this.OI = OI;
        this.PI = PI;
        this.HPI = HPI;
        this.HOI = HOI;
        this.OP = OP;
        this.HOP = HOP;

    }


}

/*数字信封1.0基本功能实现
组成：明文用AES(对称加密)加密,使用基于RSA的数字证书（使用接收方的证书公钥）来封装对称秘钥。
@return
 */
class DigitalEnvelope {
    public String plainText;
    public String ciphertext;
    public String temproryKey;
    public String ciphertemproryKey;

    //信封加密
    public static Pair<String, String> encrypt(String plaintext, String publickey) throws Exception {

        String temproryKey = AesUtil.getGUID();
        System.out.println("生成秘钥：" + temproryKey);
        // 需要加密的字串
        System.out.println("明文:" + plaintext);
        // 对称加密
        String ciphertext = AesUtil.Encrypt(plaintext, temproryKey);
        System.out.println("加密后的明文：" + ciphertext);
        //非对称加密
        String ciphertemproryKey = RsaUtils.Encrypy(temproryKey, publickey);//待实现
        System.out.println("加密后的密钥：" + ciphertemproryKey + "\n");
        //返回加密后的密钥和加密后的明文信息
        return new Pair<>(ciphertemproryKey, ciphertext);
    }

    //信封解密
    public static void decrypt(String ciphertemproryKey, String ciphertext, String privatekey) throws Exception {
        // 解密
        String temproryKey = RsaUtils.Decrypy(ciphertemproryKey, privatekey);//待实现
        System.out.println("解密后的密钥：" + temproryKey);
        String DeString = AesUtil.Decrypt(ciphertext, temproryKey);
        System.out.println("解密后的字串是：" + DeString);
    }


      public static void main(String[]args)throws Exception{
          Ca ca=CaUtil.generateCA();
          mysqlmanipulate.AddItem("customer","newcatable",ca.serial,ca.alg,ca.startDate,ca.endDate,ca.keyPairs.getValue0(),ca.keyPairs.getValue1());
          Pair<String,String> temp=mysqlmanipulate.QueryTable("3300","customer","newcatable","root","25832233l",ca.serial);;
          Pair<String,String> recv=DigitalEnvelope.encrypt("这是一个测试文本！！！",temp.getValue0());
          DigitalEnvelope.decrypt(recv.getValue0(), recv.getValue1(),temp.getValue1());
      }
}



