package com.example.shuliao_bombrate.utils;

import android.text.TextUtils;

import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;


public class RSAUtil {
    public static final String ALGORITHM = "RSA";

    private static final int SECRET_KEY_LENGTH = 2048;

    private static final int MAX_ENCRYPT_BLOCK = 245;

    private static final int MAX_DECRYPT_BLOCK = 256;

    public KeyPair getKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    public String getSignContent(Map<String, ?> params) {
        if (params == null)
            return null;
        params.remove("sign");
        StringBuilder content = new StringBuilder();
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);
        for (int i = 0; i < keys.size(); i++) {
            String strValue, key = keys.get(i);
            Object value = params.get(key);
            if (value == null) {
                strValue = null;
            } else if (value instanceof String) {
                strValue = (String)value;
            } else if (value instanceof Integer) {
                strValue = ((Integer)value).toString();
            } else if (value instanceof Long) {
                strValue = ((Long)value).toString();
            } else if (value instanceof Float) {
                strValue = ((Float)value).toString();
            } else if (value instanceof Double) {
                strValue = ((Double)value).toString();
            } else if (value instanceof Boolean) {
                strValue = ((Boolean)value).toString();
            } else if (value instanceof Date) {
                strValue = DateUtil.toStr(DateUtil.DATE_TIME_FORMAT, (Date)value);
            } else {
                strValue = value.toString();
            }
            content.append(((i == 0) ? "" : "&") + key + "=" + strValue);
        }
        return content.toString();
    }

    public String encrypt(String data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(1, publicKey);
        int inputLen = (data.getBytes()).length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        int i = 0;
        while (inputLen - offset > 0) {
            byte[] cache;
            if (inputLen - offset > 245) {
                cache = cipher.doFinal(data.getBytes(), offset, 245);
            } else {
                cache = cipher.doFinal(data.getBytes(), offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * 245;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return Base64Util.byte2Base64(encryptedData);
    }

    public String decrypt(String data, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(2, privateKey);
        byte[] dataBytes = Base64Util.base642Byte(data);
        int inputLen = dataBytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        int i = 0;
        while (inputLen - offset > 0) {
            byte[] cache;
            if (inputLen - offset > 256) {
                cache = cipher.doFinal(dataBytes, offset, 256);
            } else {
                cache = cipher.doFinal(dataBytes, offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * 256;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return new String(decryptedData, "UTF-8");
    }

    public String sign(String data, PrivateKey privateKey) {
        try {
            Signature signature = Signature.getInstance("SHA256WithRSA");
            signature.initSign(privateKey);
            signature.update(data.getBytes());
            return Base64Util.byte2Base64(signature.sign());
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean verify(String signContent, PublicKey publicKey, String sign, String charset) throws Exception {
        try {
            Signature signature = Signature.getInstance("SHA256WithRSA");
            signature.initVerify(publicKey);
            if (TextUtils.isEmpty(charset)) {
                signature.update(signContent.getBytes());
            } else {
                signature.update(signContent.getBytes(charset));
            }
            return signature.verify(Base64.decodeBase64(sign.getBytes()));
        } catch (Exception e) {
            throw new Exception("RSA_content = " + signContent + ",sign=" + sign + ",charset = " + charset, e);
        }
    }

    public PublicKey getPublicKeyFromX509(String algorithm, InputStream ins) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        StringWriter writer = new StringWriter();
        StreamUtil.io(new InputStreamReader(ins), writer);
        byte[] encodedKey = writer.toString().getBytes();
        encodedKey = Base64.decodeBase64(encodedKey);
        return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
    }

    public PublicKey getPublicKeyFromX509(InputStream ins) throws Exception {
        return getPublicKeyFromX509("RSA", ins);
    }

    public PrivateKey getPrivateKeyFromPKCS8(InputStream ins) throws Exception {
        return getPrivateKeyFromPKCS8("RSA", ins);
    }

    public PrivateKey getPrivateKeyFromPKCS8(String algorithm, InputStream ins) throws Exception {
        if (ins == null || TextUtils.isEmpty(algorithm))
            return null;
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        byte[] encodedKey = StreamUtil.readText(ins).getBytes();
        encodedKey = Base64.decodeBase64(encodedKey);
        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
    }

    public PublicKey string2PublicKey(String pubStr) throws Exception {
        byte[] keyBytes = Base64Util.base642Byte(pubStr);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    public PrivateKey string2PrivateKey(String priStr) throws Exception {
        byte[] keyBytes = Base64Util.base642Byte(priStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    public String key2String(byte[] key) {
        return Base64Util.byte2Base64(key);
    }
}
