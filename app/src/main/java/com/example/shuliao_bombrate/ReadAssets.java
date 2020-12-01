package com.example.shuliao_bombrate;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ReadAssets {

    /**
     * 读取群组信息
     *
     * @param context
     * @param fileName
     */
    public static void readGroupNameFromAssets(Context context, String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(
                    context.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";


            while ((line = bufReader.readLine()) != null) {
                Settings.groupNames.add(line);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String readLoginInfoFromAssets(Context context, String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(
                    context.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            StringBuilder sb = new StringBuilder();
            while ((line = bufReader.readLine()) != null) {
                sb.append(line);

            }
            return sb.toString();
        } catch (Exception e) {
            Log.e(MainActivity.TAG,"读取登录配置文件异常:"+e.toString());
            e.printStackTrace();

        }
        return "";
    }

}
