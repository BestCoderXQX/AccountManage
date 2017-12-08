package acffo.xqx.accountmanage.base.utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;


/**
* @author xqx
* @email djlxqx@163.com
* blog:http://www.cnblogs.com/xqxacm/
* createAt 2017/11/27
* description: des 加密
*/

public class CryptUtil {
    // 对称加密 DES 部分
    public static String KEY = "jiamikey";

    public static byte[] getPasswordByte(){
        return KEY.getBytes();
    }
    public static byte[] desEncrypt(byte[] data, byte[] password) {
        byte[] ret = null;

        if (data != null && password != null) {
            // DES 密码必须是8个字节；标准的描述是按位描述；必须是 64bit
            if (data.length > 0 && password.length == 8) {

                // 1. 创建 Cipher ，用于加密和解密，就是一个内部的算法引擎
                // getInstance("加密的算法名称")
                try {

                    Cipher cipher = Cipher.getInstance("DES");

                    // 3. 生成 Key 对象，根据不同的算法

                    DESKeySpec keySpec = new DESKeySpec(password);

                    // 3.2 使用密钥生成工具，来生成实际的 Key 对象
                    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");// 参数为算法名称

                    // 3.3 生成 Key

                    SecretKey secretKey = keyFactory.generateSecret(keySpec);

                    // 2. 初始化 Cipher，设置是加密模式还是解密模式，同时设置密码
                    // 通常第二个参数可以使用  Key 对象，每一种算法，Key对象的生成是不同的
                    cipher.init(Cipher.ENCRYPT_MODE, secretKey);

                    // 4. 进行加密或者解密的实际操作；
                    // 返回值就是最终的结果了
                    ret = cipher.doFinal(data);

                } catch (NoSuchAlgorithmException e) {  // 找不到算法的异常
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) { //
                    e.printStackTrace();
                } catch (InvalidKeyException e) {  // 非法的密钥异常
                    e.printStackTrace();
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                }


            }
        }

        return ret;
    }


    public static byte[] desDecrypt(byte[] data, byte[] password) {
        byte[] ret = null;

        if (data != null && password != null) {
            // DES 密码必须是8个字节；标准的描述是按位描述；必须是 64bit
            if (data.length > 0 && password.length == 8) {

                // 1. 创建 Cipher ，用于加密和解密，就是一个内部的算法引擎
                // getInstance("加密的算法名称")
                try {

                    Cipher cipher = Cipher.getInstance("DES");

                    // 3. 生成 Key 对象，根据不同的算法

                    DESKeySpec keySpec = new DESKeySpec(password);

                    // 3.2 使用密钥生成工具，来生成实际的 Key 对象
                    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");// 参数为算法名称

                    // 3.3 生成 Key

                    SecretKey secretKey = keyFactory.generateSecret(keySpec);

                    // 2. 初始化 Cipher，设置是加密模式还是解密模式，同时设置密码
                    // 通常第二个参数可以使用  Key 对象，每一种算法，Key对象的生成是不同的
                    cipher.init(Cipher.DECRYPT_MODE, secretKey);

                    // 4. 进行加密或者解密的实际操作；
                    // 返回值就是最终的结果了
                    ret = cipher.doFinal(data);

                } catch (NoSuchAlgorithmException e) {  // 找不到算法的异常
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) { //
                    e.printStackTrace();
                } catch (InvalidKeyException e) {  // 非法的密钥异常
                    e.printStackTrace();
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                }


            }
        }

        return ret;
    }


}
