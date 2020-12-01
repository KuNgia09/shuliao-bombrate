package com.example.shuliao_bombrate;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class XfriendUtil {
    public static String onceKey = "auij3r98y4hfwejafoijiobh97498y7439e432ur78343hfgvh9834y9382i2ru398tg";

    public static String getMD5Str(String str) {
        try {
            byte[] digest = MessageDigest.getInstance("MD5").digest(str.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(Integer.toString((b & 255) + 256, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String createOnce() {
        long currentTimeMillis = System.currentTimeMillis();
        String replace = UUID.randomUUID().toString().replace("-", "");
        String l = Long.toString(currentTimeMillis, 16);
        String mD5Str = getMD5Str(l + replace + onceKey);
        return l + mD5Str + replace;
    }

}
