package com.example.shuliao_bombrate;

import android.util.Log;

import org.junit.Test;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    public static String baseUrl="http://admin.zmtjx.com:9527";
    public static String listRobot=baseUrl+"/manage/group/listRobot";
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test2(){
//        RequestBody body = RequestBody.create(
//                MediaType.parse("application/json"), oldParamsJson);

        Request request = new Request.Builder()
                .url("http://localhost:8080/listRobot")
                .build();
        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(request);
        Response response = null;
        try {
            response = call.execute();
            String responseText = response.body().string();
            System.out.println(responseText);
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void test3(){
        //        RequestBody body = RequestBody.create(
//                MediaType.parse("application/json"), oldParamsJson);

        Request request = new Request.Builder()
                .url("http://localhost:8080/updateBombRate")
                .build();
        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(request);
        Response response = null;
        try {
            response = call.execute();
            String responseText = response.body().string();
            System.out.println(responseText);
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}