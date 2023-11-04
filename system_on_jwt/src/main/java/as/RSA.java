package as;



import org.javatuples.Pair;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class RSA{
    public  static Pair<String,String> GetRsaKeyTuple(){
        String publickey=null;
        String privatekey=null;
        try {
            // 生成RSA密钥对

            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048, new SecureRandom());
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PublicKey publicKey = keyPair.getPublic(); //公钥
            PrivateKey privateKey = keyPair.getPrivate(); //私钥


            byte[] byte_publicKey = publicKey.getEncoded();
            String strpub = Base64.getEncoder().encodeToString(byte_publicKey);
            byte[] byte_privateKey = privateKey.getEncoded();
            String strpri = Base64.getEncoder().encodeToString(byte_privateKey);

            publickey=strpub;
            privatekey=strpri;


        }catch (Exception e){
            System.out.println("报错！！！");
        }
        Pair<String, String> keytuple=new Pair<>(publickey,privatekey);
        return keytuple;
    }
  //将字符串转回密钥形式
    public static PublicKey getPublicKey(String key) {
        try {
            byte[] byteKey = Base64.getDecoder().decode(key);
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(byteKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(x509EncodedKeySpec);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static PrivateKey  getPrivateKey(String key) {
        try {
            byte[] byteKey = Base64.getDecoder().decode(key);
            PKCS8EncodedKeySpec x509EncodedKeySpec = new PKCS8EncodedKeySpec(byteKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(x509EncodedKeySpec);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    public static String Encrypy(String plaintext,String publickey)throws Exception{
        Cipher cipher = Cipher.getInstance("RSA");
//        byte[] pubKey = Base64.getDecoder().decode(publickey);
//        SecretKey originalpubKey = new SecretKeySpec(pubKey, 0, pubKey.length, "AES");//无法使用，待分析原因
        PublicKey originalpubKey=getPublicKey(publickey);
        cipher.init(Cipher.ENCRYPT_MODE, originalpubKey);
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
        String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);
        return encryptedText;


    }
    public static String Decrypy(String ciphertext,String privatekey)throws Exception{

        // 使用私钥进行解密
        Cipher cipher = Cipher.getInstance("RSA");
//        byte[] priKey = Base64.getDecoder().decode(privatekey);
//        SecretKey originalpriKey = new SecretKeySpec(priKey, 0, priKey.length, "AES");
        PrivateKey originalpriKey=getPrivateKey(privatekey);
        cipher.init(Cipher.DECRYPT_MODE, originalpriKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
        String decryptedText = new String(decryptedBytes);

        return decryptedText;

    }

    public static void main(String[]args)throws Exception{
        RSA aaa=new RSA();
        Pair<String,String> pp=aaa.GetRsaKeyTuple();
        String mingwen="这是一个测试明文！！！！";
        String jiamidemingwen=aaa.Encrypy(mingwen,pp.getValue0());
        String mingwenfrommiwen=aaa.Decrypy(jiamidemingwen,pp.getValue1());

    }
}


