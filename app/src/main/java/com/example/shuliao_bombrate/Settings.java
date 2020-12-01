package com.example.shuliao_bombrate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Settings {

    public static MainActivity myMainActivity=null;

    public static List<String> groupNames = new ArrayList<>();

    public static List uidList = new ArrayList<String>();

    public static String targetGroupId="";

    public static final String baseUrl = "http://im.hao308.top:8080/";
    public static final String baseUrl2 = "http://api.zmtjx.com:8888/";
    public static final String baseUrl8080 = "http://api.zmtjx.com:8080/";
    public static final String bileiUrl = baseUrl2 + "v1/red/group/fetch";

    public static final String fetchRedUrl = baseUrl8080 + "/v1/redPackage/fetch";
    // 查看账单
    public static final String getAccountUrl =
            baseUrl8080 + "v1/group/account/listGroupMemberAccountFlow";

    public static final String changeBombRate=baseUrl8080+"v1/admin/group/member/bomb/rate";

    public static JWTUtils jwtUtils = new JWTUtils();
    public static Random ra = new Random();

    public static Gson gson;
    static {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
    }

}
