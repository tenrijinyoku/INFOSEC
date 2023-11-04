package as;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
/*基于HMACSHA256算法的消息认证码
@return
 */
public abstract class Signature{
    private static Logger logger = LogManager.getLogger(Signature.class);
    private static final String ALGORITHM = "HmacSHA256";

    public static boolean valid(String message, String secret, String signature) {
        return signature != null && signature.equals(sign(message, secret));
    }

    public static String sign(String message, String secret) {
        try {

            Mac hmac = Mac.getInstance(ALGORITHM);
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            hmac.init(secret_key);
            byte[] bytes = hmac.doFinal(message.getBytes());
            //logger.info("service sign is "+byteArrayToHexString(bytes));
            return byteArrayToHexString(bytes);
        } catch (Exception ex) {
            logger.error("签名错误：", ex);
        }
        return null;
    }

    private static String byteArrayToHexString(byte[] bytes) {
        StringBuilder hs = new StringBuilder();
        String tempStr;
        for (int index = 0; bytes != null && index < bytes.length; index++) {
            tempStr = Integer.toHexString(bytes[index] & 0XFF);
            if (tempStr.length() == 1)
                hs.append('0');
            hs.append(tempStr);
        }
        return hs.toString().toLowerCase();
    }
    public static void main(String[]args){
        String testss="this is a test text!!!";
        String testString=Signature.sign(testss,"this is a test key!!!!");
        System.out.println("测试加密结果："+testString);
        if(Signature.valid(testss,"this is a test key!!!!",testString)){

            System.out.println("结果正确！！！");


        }


    }

}
