package com.ghostflower.CA_System.service.impl;

import com.ghostflower.CA_System.mapper.CaMapper;
import com.ghostflower.CA_System.pojo.Ca;
import com.ghostflower.CA_System.pojo.DigitalEnvelope;
import com.ghostflower.CA_System.pojo.Result;
import com.ghostflower.CA_System.service.CipherService;
import com.ghostflower.CA_System.utils.DigitalEnvelopeUtil;
import com.ghostflower.CA_System.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CipherServiceImpl implements CipherService {
    @Autowired
    CaMapper cm;
    @Override
    public DigitalEnvelope GenerateDigitalEnvelope(String plainText,String receiverUid){
        Ca ca = cm.FindCa(receiverUid);
        if(ca!=null){
            String publicKey = ca.publicKey;
            try {
               return DigitalEnvelopeUtil.Encrypt(plainText,publicKey,receiverUid);
            } catch (Exception e) {
                System.out.println("加密失败!!!");;
            }

        }else{
            return null;
        }
        return null;
    }


    @Override
    public String DecryptDigitalEnvelope(DigitalEnvelope de) {
        String uid = de.getReceiverUid();
        Ca ca = cm.FindCa(uid);
        if(ca!=null){
            String privateKey = ca.privateKey;
            try {
                return DigitalEnvelopeUtil.Decrypt(de.getCipherTemporaryKey(),de.getCipherText(),privateKey);
            } catch (Exception e) {
                System.out.println("解密失败!!!");;
            }

        }else{
            return null;
        }
        return null;
    }
}
