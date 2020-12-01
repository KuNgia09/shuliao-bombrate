package com.example.shuliao_bombrate.utils;


import org.apache.commons.codec.binary.Base64;

public class Base64Util {
  public static String byte2Base64(byte[] bytes) {

    return Base64.encodeBase64String(bytes);
//    return Base64.getEncoder().encodeToString(bytes);
  }

  
  public static byte[] base642Byte(String base64Key) {

    return Base64.decodeBase64(base64Key);
//    return Base64.getDecoder().decode(base64Key);
  }
}
