package com.ghostflower.CA_System.utils;

import com.ghostflower.CA_System.pojo.Ca;
import com.ghostflower.CA_System.pojo.DigitalEnvelope;
import org.javatuples.Pair;

public class DigitalEnvelopeUtil {
    /**
     *
     * 数字信封加密
     * @param plainText
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static DigitalEnvelope Encrypt(String plainText, String publicKey,String receiverUid) throws Exception {
        //生成临时密钥
        String temporaryKey = AesUtil.getGUID();
        // 对称加密
        String ciphertext = AesUtil.Encrypt(plainText, temporaryKey);
        //非对称加密
        String cipherTemporaryKey = RsaUtils.Encrypy(temporaryKey, publicKey);//待实现
        System.out.println("明文:" + plainText+"加密后的明文：" + ciphertext+"生成秘钥：" + temporaryKey+"加密后的密钥：" + cipherTemporaryKey + "\n");
        //返回加密后的密钥和加密后的明文信息
        return new DigitalEnvelope(receiverUid,ciphertext,cipherTemporaryKey);
    }

    //信封解密
    public static String Decrypt(String cipherTemporaryKey, String cipherText, String privateKey) throws Exception {
        // 解密
        String temporaryKey = RsaUtils.Decrypy(cipherTemporaryKey, privateKey);//待实现
        String DeString = AesUtil.Decrypt(cipherText, temporaryKey);
        System.out.println("解密后的密钥：" + temporaryKey+"解密后的字串是：" + DeString);
        return DeString;
    }


}
