package com.ghostflower.CA_System.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;


public class AesUtil {
    /**
     * 此处使用AES-128-ECB加密模式，key需要为16位。
     * @param SSrc
     * @param SKey
     * @return
     * @throws Exception
     */
    public static String Encrypt(String SSrc, String SKey) throws Exception {

        // 判断Key是否为16位
        if (SKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
        byte[] raw = SKey.getBytes("utf-8");
        SecretKeySpec SKeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, SKeySpec);
        byte[] encrypted = cipher.doFinal(SSrc.getBytes("utf-8"));

        String en=Base64.getEncoder().encodeToString(encrypted);//此处使用BASE64做转码功能
        return en;
    }

    /**
     * SKey 此处使用AES-128-ECB加密模式，key需要为16位。
     * @param SSrc
     * @param SKey
     * @return
     * @throws Exception
     */
    public static String Decrypt(String SSrc, String SKey) throws Exception {
        try {
            // 判断Key是否正确
            if (SKey == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (SKey.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }
            byte[] raw = SKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = Base64.getDecoder().decode(SSrc);//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original,"utf-8");
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
/*
生成16位不重复的随机数，含数字+大小写
@return
 */
    public static String getGUID() {
        StringBuilder uid = new StringBuilder();
        //产生16位的强随机数
        Random rd = new SecureRandom();
        for (int i = 0; i < 16; i++) {
            //产生0-2的3位随机数
            int type = rd.nextInt(3);
            switch (type){
                case 0:
                    //0-9的随机数
                    uid.append(rd.nextInt(10));
                    break;
                case 1:
                    //ASCII在65-90之间为大写,获取大写随机
                    uid.append((char)(rd.nextInt(25)+65));
                    break;
                case 2:
                    //ASCII在97-122之间为小写，获取小写随机
                    uid.append((char)(rd.nextInt(25)+97));
                    break;
                default:
                    break;
            }
        }
        return uid.toString();
    }


    /*
    模拟
     */
    public static void main(String[] args) throws Exception {
        /*
         * 此处使用AES-128-ECB加密模式，key需要为16位。
         */
        String cKey = getGUID();
        System.out.println("生成秘钥："+cKey);
        // 需要加密的字串
        String cSrc = "test.888888";
        System.out.println(cSrc);
        // 加密
        String enString = AesUtil.Encrypt(cSrc, cKey);
        System.out.println("加密后的字串是：" + enString);

        // 解密
        String DeString = AesUtil.Decrypt(enString, cKey);
        System.out.println("解密后的字串是：" + DeString);
    }
}


