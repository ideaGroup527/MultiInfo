package org.jmu.multiinfo.utils;

import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.util.DigestUtils;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/***
 * 
* @Title: SecretUtil.java 
* @Package org.jmu.multiinfo.utils 
* @Description: ���ܽ���util
* @author  <a href="mailto:www_1350@163.com">Absurd</a>
* @date 2015��9��23�� ����5:08:57 
* @version V1.0
 */
public class SecretUtil {
	public static final String KEY_SHA = "SHA";
    public static final String KEY_MD5 = "MD5";
 
    /**
     * MAC�㷨��ѡ���¶����㷨
     * 
     * <pre>
     * HmacMD5 
     * HmacSHA1 
     * HmacSHA256 
     * HmacSHA384 
     * HmacSHA512
     * </pre>
     */
    public static final String KEY_MAC = "HmacMD5";
    
    
    public static final String KEY_DES = "DES";
    
    public static final String KEY_RSA = "RSA";
    
    public static final String KEY_RSA_SIGN="MD5withRSA";
    
    public static final String KEY_RSA_PUBLIC = "RSAPublicKey";//��Կ
    public static final String KEY_RSA_PRIVATE = "RSAPrivateKey";//˽Կ
    
    public static final String SEC_PRO_LOC = "secret/secret.properties";
    
    public static final String PRO_SEC_PASS = "secret.password";
    		
    /***
     * ��ȡ�����ļ�
     * @return
     * @throws IOException
     */
    public static Properties getProperties() throws IOException{
    	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();  
        InputStream in = classLoader.getResourceAsStream(SEC_PRO_LOC);  
        Properties prop = new Properties();  
        prop.load(in);
        return prop;
    }
    
    
    /***
     * ��ȡ��Կ
     * @return
     * @throws IOException
     */
    public static String getProSecPass() throws IOException{
    	Properties prop = getProperties();
    	 String password = prop.getProperty(PRO_SEC_PASS);
        return password;
    }
    		
    /**
     * BASE64����
     * 
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }
 
    /**
     * BASE64����
     * 
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }
 
    /**
     * MD5����(��ϢժҪ�㷨)
     * 
     * @param data
     * @return byte[]
     * @throws Exception
     */
    public static byte[] encryptMD5(byte[] data) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
        md5.update(data);
        return md5.digest();
    }
 
    /***
     * MD5 ����(��ϢժҪ�㷨)
     * @param data
     * @return ʮ������
     * @throws Exception
     */
    public static String encryptMD5ToHex(byte[] data) throws Exception{
    	return byteArrayToHexString(encryptMD5(data));
    }
    
    /**
     * SHA����(��ȫɢ���㷨)
     * 
     * @param data
     * @return byte[]
     * @throws Exception
     */
    public static byte[] encryptSHA(byte[] data) throws Exception {
        MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
        sha.update(data);
        return sha.digest();
    }
 
    /***
     * SHA ����(��ȫɢ���㷨)
     * @param data
     * @return ʮ������
     * @throws Exception
     */
    public static String encryptSHAToHex(byte[] data) throws Exception{
    	return byteArrayToHexString(encryptSHA(data));
    }
    
    /***
     * DES����(���ݼ����㷨/�Գ�)
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encryptDES(byte[] data) throws Exception {
    	 SecureRandom random = new SecureRandom();  
    	 DESKeySpec desKey = new DESKeySpec(getProSecPass().getBytes());  
    	//����һ���ܳ׹�����Ȼ��������DESKeySpecת����  
         SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_DES);  
         SecretKey securekey = keyFactory.generateSecret(desKey);  
         //Cipher����ʵ����ɼ��ܲ���  
         Cipher cipher = Cipher.getInstance(KEY_DES);  
         //���ܳ׳�ʼ��Cipher����  
         cipher.init(Cipher.ENCRYPT_MODE, securekey, random);  
         //���ڣ���ȡ���ݲ�����  
         //��ʽִ�м��ܲ���  
         return cipher.doFinal(data);  
    }
    
    /***
     * DES����(���ݼ����㷨/�Գ�)
     * @param data
     * @param password
     * @return
     * @throws Exception
     */
    public static byte[] decryptDES(byte[] data) throws Exception {
    	 // DES�㷨Ҫ����һ�������ε������Դ  
        SecureRandom random = new SecureRandom();  
        // ����һ��DESKeySpec����  
        DESKeySpec desKey = new DESKeySpec(getProSecPass().getBytes());  
        // ����һ���ܳ׹���  
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_DES);  
        // ��DESKeySpec����ת����SecretKey����  
        SecretKey securekey = keyFactory.generateSecret(desKey);  
        // Cipher����ʵ����ɽ��ܲ���  
        Cipher cipher = Cipher.getInstance(KEY_DES);  
        // ���ܳ׳�ʼ��Cipher����  
        cipher.init(Cipher.DECRYPT_MODE, securekey, random);  
        // ������ʼ���ܲ���  
        return cipher.doFinal(data);  
   }
    
    /***
     * RSA��ʼ����Կ(�ǶԳ�)
     * @return
     * @throws Exception
     */
    public static Map<String,Object> initKey()throws Exception{
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_RSA);
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
         
        //��Կ
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        //˽Կ
        RSAPrivateKey privateKey =  (RSAPrivateKey) keyPair.getPrivate();
         
        Map<String,Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(KEY_RSA_PUBLIC, publicKey);
        keyMap.put(KEY_RSA_PRIVATE, privateKey);
        return keyMap;
    }
    
    /***
     * ȡ��RSA��Կ����ת��ΪString����
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getRSAPublicKey(Map<String, Object> keyMap)throws Exception{
        Key key = (Key) keyMap.get(KEY_RSA_PUBLIC);  
        return encryptBASE64(key.getEncoded());     
    }
    
    
    /***
     * ȡ��RSA˽Կ����ת��ΪString����
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception{
        Key key = (Key) keyMap.get(KEY_RSA_PRIVATE);  
        return encryptBASE64(key.getEncoded());     
    }
    
    /***
     * RSA��˽Կ����(�ǶԳ�)
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptRSAByPrivateKey(byte[] data,String key)throws Exception{
        //������Կ
        byte[] keyBytes = decryptBASE64(key);
        //ȡ˽Կ
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_RSA);
        Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
         
        //�����ݼ���
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
         
        return cipher.doFinal(data);
    }
 
    /***
     * RSA��˽Կ����(�ǶԳ�)
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptRSAByPrivateKey(byte[] data,String key)throws Exception{
        //��˽Կ����
        byte[] keyBytes = decryptBASE64(key);
         
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_RSA);
        Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        //�����ݽ���
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
         
        return cipher.doFinal(data);
    }
    
    
    /***
     * RSA�ù�Կ����(�ǶԳ�)
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptRSAByPublicKey(byte[] data,String key)throws Exception{
        //�Թ�Կ����
        byte[] keyBytes = decryptBASE64(key);
        //ȡ��Կ
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_RSA);
        Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
         
        //�����ݽ���
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
         
        return cipher.doFinal(data);
    }
    
    /***
     * RSA�ù�Կ����(�ǶԳ�)
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptRSAByPublicKey(byte[] data,String key)throws Exception{
        //��˽Կ����
        byte[] keyBytes = decryptBASE64(key);
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_RSA);
        Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
         
        //�����ݽ���
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
         
        return cipher.doFinal(data);
    }
    
    
    /***
     * RSA��˽Կ����Ϣ��������ǩ��
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String signRSA(byte[] data,String privateKey)throws Exception{
        //����˽Կ
        byte[] keyBytes = decryptBASE64(privateKey);
        //����PKCS8EncodedKeySpec����
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        //ָ�������㷨
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_RSA);
        //ȡ˽Կ�׶���
        PrivateKey privateKey2 = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        //��˽Կ����Ϣ��������ǩ��
        Signature signature = Signature.getInstance(KEY_RSA_SIGN);
        signature.initSign(privateKey2);
        signature.update(data);
         
        return encryptBASE64(signature.sign());
    }
    
    
    /***
     * RSAУ������ǩ��
     * @param data
     * @param publicKey
     * @param sign
     * @return
     * @throws Exception
     */
    public static boolean verifyRSA(byte[] data,String publicKey,String sign)throws Exception{
        //���ܹ�Կ
        byte[] keyBytes = decryptBASE64(publicKey);
        //����X509EncodedKeySpec����
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        //ָ�������㷨
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_RSA);
        //ȡ��Կ�׶���
        PublicKey publicKey2 = keyFactory.generatePublic(x509EncodedKeySpec);
         
        Signature signature = Signature.getInstance(KEY_RSA_SIGN);
        signature.initVerify(publicKey2);
        signature.update(data);
        //��֤ǩ���Ƿ�����
        return signature.verify(decryptBASE64(sign));
         
    }
    
    /**
     * ��ʼ��HMAC��Կ
     * 
     * @return
     * @throws Exception
     */
    public static String initMacKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);
        SecretKey secretKey = keyGenerator.generateKey();
        return encryptBASE64(secretKey.getEncoded());
    }
 
    /**
     * HMAC����(ɢ����Ϣ������)
     * 
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encryptHMAC(byte[] data) throws Exception {
        SecretKey secretKey = new SecretKeySpec(decryptBASE64(getProSecPass()), KEY_MAC);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        return mac.doFinal(data);
    }
    
    
   
    /***
     * ת16����
     * @param bytearray
     * @return
     */
    private static String byteArrayToHexString(byte[] bytearray) {
    	
    	   StringBuffer hexString = new StringBuffer();
           // �ֽ�����ת��Ϊ ʮ������ ��
           for (int i = 0; i < bytearray.length; i++) {
               String shaHex = Integer.toHexString(bytearray[i] & 0xFF);
               if (shaHex.length() < 2) {
                   hexString.append(0);
               }
               hexString.append(shaHex);
           }
           return hexString.toString();
          
       }
    
    public static void main(String[] args) throws Exception {
    
    	String str ="6000";
    	StringBuilder sb = new StringBuilder();
    	String rt3 = new String(DigestUtils.appendMd5DigestAsHex(str.getBytes(), sb));
    	System.out.println(rt3);
String rt5 = new String(SecretUtil.encryptBASE64(str.getBytes()));
System.out.println(rt5+"------------------5");
String rt6 = SecretUtil.byteArrayToHexString(SecretUtil.encryptSHA(str.getBytes()));
System.out.println(rt6+"----------------66");
String rt7 = org.apache.commons.codec.digest.DigestUtils.shaHex(str);
System.out.println(rt7+"-----------------7");
String rt8 = Base64.encode(str.getBytes());
System.out.println(rt8);
    	String rt =	SecretUtil.byteArrayToHexString(SecretUtil.encryptMD5(str.getBytes()));
		System.out.println(rt);
		String rt2 = new String(SecretUtil.byteArrayToHexString(SecretUtil.encryptSHA(str.getBytes())));
		System.out.println(rt2);
	}
}
