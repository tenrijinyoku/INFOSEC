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
    String globalPrivateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCMoh/wtWe5VdZFrcCrw6ARBW7bLqMhbyqg37bDjP9Lht5ZxmsuLKZLN+un/IB2Z7IHnXadFXoscCdmVPKKRFNto4Lb36vVzC3y+92gbzZsK8ZBD6+omj+veQO+vJi1WsrlzFooQco5Uo/OXRlZT9TaT4LHHN8MwTcHKP9k/JPTFkSHvO0LOJDfSvUtOPLAj7DMLD5crXjSwFgdw47nQXOTZ7PK6bGdylt/sYbjvhEo30urLNONOshhbnDS3QSl8zOsNVSdhlkDPF0ZVbSsZSboqILC7XVo9LUvmBawBBW9eFXMvF+QxmxflK6m3nnEv2dnwlYk/dH7vWkGmiM1wSCLAgMBAAECggEABJw6OLc5s7VBHIz9pNzAc3kQWGRxq7fPdCo2qA0klPDSewC43XDem538WtZWBuEBJfltSukDHMHUCNQav2Exl2kz8eal5Tt7bUA51KJe7JXGV2ECfLx8cRiiDswBwD53Daibj5SDOC3aCsyAvnTLEuCfhHeCRqDEPCi+kxANjBl6VLWc0N8QGsJoNw/JsRosNmDSktGs/VcB1KteG0/tXCi1hkXruuUurNGqRlT6hkzDXtWDUgaTGtfKFD0UvpVR7odgxl7xH+5F1K/Hnk1X1TTgLyAkLppEhc7FxwsYgU5viDyBJeDkshADgHalJSXICaAsXhw1TyX+jNDoN23ZQQKBgQDEf7oQfi2SxNzlVzMQwy4yxQLvY+W76i4GP0fObd3eo8NexljlGOi4JrWaYa3qJ3TzqdPFBDgEr7xhNZzhciMDEqoFH4zyk3FZnnSyeXwBUWC4AAa36g77s9BCIsiJZviqq/hh1wryQxZ/v3bFUeIQ1+oLKBYX0rFwVGALFYhjvQKBgQC3N8lF6BexM95L7W4e5D3oFzC3E8/qkeEdUfLJ876yGxX93S2HQia4ZfikfDiCqAA6cxpPMv4g1mvjLdIUQVYc3UfRnSW7ulL1ke+JJzLE5zdDIpxqm1ydMeHGuZ0LjG4ywNqZI4LtvNGeKkzgDtk5nIecpGV3Wcxgy3/t0z015wKBgGumjgj7F3yQ3XPHkfJc+7JUvIbp87I/WXeSs4QkLKkMjrwdbhYUz2oTyuoJGX5un2PeZmZJKWdn34xiEtBv9z53uy2TW2SJ4k92v/gHFiiirJohaYzYbdz3ZeDVrYeZIiJelHUFmrmeh3T1L9xqP696mn4COBhVFWC02Ok5or8BAoGBAKfDfBf3ueH3NYBeqqu8oHbo7SFmEcaexvta8y46cw2QwaVPpgWo692C+ZZbbD8u5+6dQKTuc0XMMMO3TBph7SDG+aEU/N0VcisL8sKqUtF7Sd2+Tm1LTxVc/HhDs2pErw7oCgGZQ6S+dU4XO4cZ8YvQKVikK4iKgBrNm5lACFdXAoGAfENKQ0lstcmeEaXDuBiAif1kQM4kig3WdoY82zvGEE38W8/12iM7ezbv/v89eaGO/FdIMqblTykYGRVLegsak+D2Vgal7dNr9weCoN3ZMx++8OKLFT+CBggn4UT34d3w/r8jSCHuvooHS8PsT5Zz6nuq6m/clsVmhC4cBlin9SI=";
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

    @Override
    public String GlobalDecode(DigitalEnvelope de, String globalPrivateKey) {
        try {
            return DigitalEnvelopeUtil.Decrypt(de.getCipherTemporaryKey(), de.getCipherText(), globalPrivateKey);
        } catch (Exception e) {
            System.out.println("解密失败!!!");
        }
        return null;
    }
}
