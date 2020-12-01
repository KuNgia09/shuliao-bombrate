package com.example.shuliao_bombrate.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
  public static String getRandomCode(int length) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < length; i++)
      sb.append((int)(Math.random() * 100.0D) % 10);
    return sb.toString();
  }
  
  public static boolean isMobile(String mobile) {
    boolean flag = false;
    try {
      Pattern p = Pattern.compile("^(1[3-9][0-9])\\d{8}$");
      Matcher m = p.matcher(mobile);
      flag = m.matches();
    } catch (Exception e) {
      flag = false;
    } 
    return flag;
  }
  public static synchronized String getAppName(Context context) {
    try {
      PackageManager packageManager = context.getPackageManager();
      PackageInfo packageInfo = packageManager.getPackageInfo(
          context.getPackageName(), 0);
      int labelRes = packageInfo.applicationInfo.labelRes;
      return context.getResources().getString(labelRes);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static Map<String, Object> json2Map(String json) {
    JsonElement jsonElement = JsonParser.parseString(json);
    Map<String, Object> map = new ConcurrentHashMap<>();
    if (jsonElement.isJsonNull())
      return map; 
    if (jsonElement.isJsonObject()) {
      JsonObject jsonObject = jsonElement.getAsJsonObject();
      Set<String> keys = jsonObject.keySet();
      for (String key : keys) {
        JsonElement e = jsonObject.get(key);
        String value = e.toString();
        map.put(key, value);
      } 
    } 
    if (jsonElement.isJsonArray()) {
      JsonArray jsonArray = jsonElement.getAsJsonArray();
      int size = jsonArray.size();
      for (int i = 0; i < size; i++) {
        JsonElement e = jsonArray.get(i);
        map.put("key" + i, e.getAsString());
      } 
    } 
    return map;
  }
  
  public static void main(String[] args) {
    JsonElement jsonElement = JsonParser.parseString("[\"true\",\"false\",\"333\"]");
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("sss", Integer.valueOf(123));
    jsonObject.addProperty("fgfdg", "3424");
    jsonObject.addProperty("qqqq", Boolean.valueOf(true));
    JsonArray jsonArray = new JsonArray();
    jsonArray.add(Boolean.valueOf(false));
    jsonArray.add(Boolean.valueOf(true));
    jsonArray.add("1212");
    jsonObject.add("ddd", (JsonElement)jsonArray);
    String s = jsonObject.toString();
    json2Map(s);
  }
}
