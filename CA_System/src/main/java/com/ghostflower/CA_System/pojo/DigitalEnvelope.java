package com.ghostflower.CA_System.pojo;


import lombok.Data;

@Data
public class DigitalEnvelope {
    private String receiverUid;
    private String cipherText;
    private String cipherTemporaryKey;

    public DigitalEnvelope(String receiverUid, String cipherText, String cipherTemporaryKey) {
        this.receiverUid = receiverUid;
        this.cipherText = cipherText;
        this.cipherTemporaryKey = cipherTemporaryKey;
    }
}
