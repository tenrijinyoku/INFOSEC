package com.ghostflower.CA_System.service;

import com.ghostflower.CA_System.pojo.DigitalEnvelope;

public interface CipherService {
    /**
     * 生成数字信封
     * @param plainText
     * @param receiverUid
     * @return
     */
    DigitalEnvelope GenerateDigitalEnvelope(String plainText,String receiverUid);


    /**
     * 解密数字信封
     * @param de
     * @return
     */
    String DecryptDigitalEnvelope(DigitalEnvelope de);

    /**
     * 全局解密，服务系统独占一个密钥对，用来处理外界对服务器的请求
     * @param de
     * @param globalPrivateKey
     * @return
     */
    String GlobalDecode(DigitalEnvelope de,String globalPrivateKey);



}
