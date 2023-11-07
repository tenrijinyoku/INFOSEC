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
}
