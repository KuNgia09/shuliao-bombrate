package com.example.shuliao_bombrate;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.shuliao_bombrate.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.shuliao_bombrate.Settings.targetGroupId;
import static com.example.shuliao_bombrate.Settings.uidList;

public class MainActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {

    public static final String TAG = "TK";

    @BindView(R.id.spinner1)
    Spinner groupListSpinner;

    @BindView(R.id.spinner_uid)
    Spinner uidSpinner;

    @BindView(R.id.et_uid)
    EditText et_uid;

    @BindView(R.id.et_bombrate)
    EditText et_bombrate;

    @BindView(R.id.bt_restoreBombRate)
    Button bt_restoreBombRate;

    @BindView(R.id.bt_mustNotBombRate)
    Button bt_mustNotBombRate;

    @BindView(R.id.bt_mustBombRate)
    Button bt_mustBombRate;


    @OnClick(R.id.bt_changeBombRate)
    public void chanageBombRateClick() {
        String temp = et_bombrate.getText().toString();
        Integer bombRate = Integer.parseInt(temp);
        Log.d(TAG, "当前bombRate:" + bombRate);
        if (bombRate < 0 || bombRate >= 100) {
            Toast.makeText(MainActivity.this, "中雷概率只能在0-99之间", Toast.LENGTH_SHORT).show();
            return;
        }
        String uid = (String) uidSpinner.getSelectedItem();
        Log.d(TAG, "要设置中雷概率的uid为:" + uid);
        if (TextUtils.isEmpty(uid)) {
            Toast.makeText(MainActivity.this, "请添加uid", Toast.LENGTH_SHORT).show();
            return;
        }
        String groupId = Settings.targetGroupId;
        testAndChangeBombRate(uid, groupId, bombRate);


    }

    private void testAndChangeBombRate(String uid, String groupId, int bombRate) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // /*  75 */   GROUP_NOT_EXIST(135, "群不存在"),
                //         /*  76 */   GROUP_MEMBER_NOT_EXIST(136, "群成员不存在"),
                //         /*  77 */   GROUP_MEMBER_FREEZE(137, "你已经被冻结,请联系管理员"),

                String testResult = prepCheck(groupId, uid);
                if (TextUtils.isEmpty(testResult)) {
                    mySendMessage(1, "测试群成员是否存在失败");
                    return;
                }
                Log.d(TAG, "设置群成员结果是否存在结果:" + testResult);

                JSONObject jsonObject = JSON.parseObject(testResult);
                int code = jsonObject.getInteger("code");
                if (code == 27) {
                    mySendMessage(1, "群成员不存在");
                    return;
                }

                changeBombRateInternal(bombRate, groupId, uid);
            }
        }).start();
    }

    private void mySendMessage(int what, String title) {
        Message message = new Message();
        message.what = what;
        message.obj = title;
        mHandler.sendMessage(message);
    }

    private String prepCheck(String groupId, String uid) {
        HbSimpleInfo hbSimpleInfo = new HbSimpleInfo();
        Integer a = 1000 + Settings.ra.nextInt(9000);
        String snowflakeId = "78236581514314" + a.toString();
        hbSimpleInfo.setSnowflakeId(snowflakeId);
        hbSimpleInfo.setTargetId(groupId);
        hbSimpleInfo.setToken("eyJ0eXBlIjoiSldUIiwic2FsdCI6IjU5OWI5NTBlLWQxN2MtNDlkNC04YTFkLTY0NTI3ZjM2Y2Q0MSIsInJlZElkIjoiNzgyMzY1ODE1MTQzMTQ1NDcyIiwiYWxnIjoiSFM1MTIifQ.eyJzdWIiOiI3Nzk0MCIsImlhdCI6MTYwNjU3MjE0NSwiZXhwIjoxNjA2NjU4NTQ1fQ.ixUIJQH9Jyrf02JuKDqL1cuZHzPGxKr5I0FinyGOn-Pj75Oz_HYTeuKNIryKZGgmobBrrX7gMa9NSmkNYfTsog");
        String testResult = qiangHb(uid, hbSimpleInfo);
        return testResult;


    }


    public String qiangHb(String tuoUid, HbSimpleInfo hbSimpleInfo) {


        String groupId = hbSimpleInfo.getTargetId();

        String hbToken = hbSimpleInfo.getToken();

        String sid = UUID.randomUUID().toString();
        String token = Settings.jwtUtils.generateToken(tuoUid, sid);

        Map postData = new HashMap<String, Object>();
        postData.put("groupId", groupId);
        postData.put("redId", hbSimpleInfo.getSnowflakeId());
        postData.put("token", hbToken);
        postData.put("userId", tuoUid);


        String oldParamsJson = Settings.gson.toJson(postData);
        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"), oldParamsJson);

        Request request = new Request.Builder()
                .url(Settings.fetchRedUrl)
                .post(body)
                .addHeader("token", token)
                .addHeader("u_id", tuoUid)
                .addHeader("s_id", sid)
                .addHeader("once", XfriendUtil.createOnce())
                .build();
        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(request);
        Response response = null;
        try {
            response = call.execute();
            String responseText = response.body().string();


            Log.d(TAG, "respons text" + responseText);
            response.close();
            return responseText;

        } catch (IOException e) {
            Log.d(TAG, "请求bilei 异常:" + e.toString());
            e.printStackTrace();
        }
        return "";
    }

    public void changeBombRateInternal(int bombRate, String groupId, String uid) {
        String result = changeBombRate(bombRate, groupId, uid);

        Message message = Message.obtain();
        if (result.isEmpty()) {
            message.what = 1;
            String info = "设置中雷概率失败:groupId:" + Settings.targetGroupId + ";bombRate:" + bombRate + ";uid:" + uid;
            message.obj = info;
            mHandler.sendMessage(message);
            Log.e(TAG, info);
            return;
        }
        JSONObject jsonObject = JSON.parseObject(result);
        Integer code = jsonObject.getInteger("code");
        if (code == 0) {
            message.what = 1;
            message.obj = "设置成功";
            mHandler.sendMessage(message);
            return;
        } else {
            message.what = 1;
            message.obj = jsonObject.getString("message");
            mHandler.sendMessage(message);
            return;
        }
    }

    /**
     * 恢复中雷概率
     */
    @OnClick(R.id.bt_restoreBombRate)
    public void restoreBombRateClick() {
        String uid = (String) uidSpinner.getSelectedItem();
        if (TextUtils.isEmpty(uid)) {
            Toast.makeText(MainActivity.this, "请添加uid", Toast.LENGTH_SHORT).show();
            return;
        }
        String groupId = Settings.targetGroupId;
        testAndChangeBombRate(uid, groupId, 0);


    }

    /**
     * 必定中雷
     */
    @OnClick(R.id.bt_mustBombRate)
    public void mustBombRateClick() {
        String uid = (String) uidSpinner.getSelectedItem();
        if (TextUtils.isEmpty(uid)) {
            Toast.makeText(MainActivity.this, "请添加uid", Toast.LENGTH_SHORT).show();
            return;
        }

        String groupId = Settings.targetGroupId;
        testAndChangeBombRate(uid, groupId, 99);
    }

    @OnClick(R.id.bt_mustNotBombRate)
    public void mustNotBombRateClick() {
        String uid = (String) uidSpinner.getSelectedItem();
        if (TextUtils.isEmpty(uid)) {
            Toast.makeText(MainActivity.this, "请添加uid", Toast.LENGTH_SHORT).show();
            return;
        }
        String groupId = Settings.targetGroupId;
        testAndChangeBombRate(uid, groupId, 1);
    }

    private String changeBombRate(int bombRate, String groupId, String uid) {
        long currentTimeMillis = System.currentTimeMillis();
        BombRateRequest bombRateRequest = new BombRateRequest();
        bombRateRequest.setGroupId(groupId);
        bombRateRequest.setRate(bombRate);
        List<String> ids = new ArrayList<>();
        ids.add(uid);
        bombRateRequest.setUserIds(ids);
        // String oldParamsJson=gson.toJson(postData);
        String oldParamsJson = JSON.toJSONString(bombRateRequest);
        System.out.println("oldParamJson:" + oldParamsJson);

        Map<String, Object> rootMap = Utils.json2Map(oldParamsJson);

        rootMap.put("timestamp", currentTimeMillis + "");
        String sign = "";
        String once = "";
        try {
            sign = Encrypt.signParams(rootMap);
            System.out.println("sign:" + sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"), oldParamsJson);

        Request request = new Request.Builder()
                .url(Settings.changeBombRate)
                .post(body)
                .addHeader("timestamp", String.valueOf(currentTimeMillis))
                .addHeader("sign", sign)
                // .addHeader("once",XfriendUtil.createOnce())
                .addHeader("token", "MIIEwAIBADANBgkqhkiG9w0BAQEFAASCBKowgg")
                .build();
        Response response = null;
        OkHttpClient client = new OkHttpClient();
        try {
            //将请求添加到请求队列等待执行，并返回执行后的Response对象
            response = client.newCall(request).execute();
            System.out.println(response.code());
            //获取Http Status Code.其中200表示成功
            if (response.code() == 200) {
                //这里需要注意，response.body().string()是获取返回的结果，此句话只能调用一次，再次调用获得不到结果。
                //所以先将结果使用result变量接收
                String result = response.body().string();
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.body().close();
            }
        }
        return null;
    }

    /**
     * 添加uid
     *
     * @param v
     */
    @OnClick(R.id.bt_addUid)
    public void addUid(View v) {
        String uid = et_uid.getText().toString().trim();
        if (TextUtils.isEmpty(uid)) {
            return;
        }
        for (int i = 0; i < uidList.size(); i++) {
            if (uidList.get(i).equals(uid)) {
                Toast.makeText(MainActivity.this, "uid存在", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        uidList.add(uid);
        ArrayAdapter<String> uidAdapter = new ArrayAdapter<>(this, R.layout.custom_spiner_text_item,
                uidList);
        uidSpinner.setAdapter(uidAdapter);
        Toast.makeText(MainActivity.this, "添加成功", Toast.LENGTH_SHORT).show();

    }

    @OnClick(R.id.bt_clearUid)
    public void clearUidClick(View v) {
        uidList.clear();
        ArrayAdapter<String> uidAdapter = new ArrayAdapter<>(this, R.layout.custom_spiner_text_item,
                uidList);
        uidSpinner.setAdapter(uidAdapter);
        Toast.makeText(MainActivity.this, "清空成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initConfig();
        initView();

    }

    public void initConfig() {
        ReadAssets.readGroupNameFromAssets(this, "group-info.txt");
        if (Settings.groupNames.size() == 0) {
            Toast.makeText(MainActivity.this, "读取群组信息失败", Toast.LENGTH_SHORT).show();
            return;
        }


    }

    private void initView() {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.custom_spiner_text_item,
                Settings.groupNames);
        ArrayAdapter<String> uidAdapter = new ArrayAdapter<>(this, R.layout.custom_spiner_text_item,
                uidList);
        // ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
        //     R.array.groupList, R.layout.custom_spiner_text_item);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        uidAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        groupListSpinner.setAdapter(adapter);
        uidSpinner.setAdapter(uidAdapter);
        groupListSpinner.setOnItemSelectedListener(this);
        uidSpinner.setOnItemSelectedListener(this);

        bt_restoreBombRate.setTextColor(Color.BLUE);
        bt_mustBombRate.setTextColor(Color.RED);
        bt_mustNotBombRate.setTextColor(Color.GREEN);

        bt_restoreBombRate.setBackgroundColor(Color.WHITE);
        bt_mustBombRate.setBackgroundColor(Color.WHITE);
        bt_mustNotBombRate.setBackgroundColor(Color.WHITE);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinner1:
                String groupId = Settings.groupNames.get(position).substring(0, 5);
                Settings.targetGroupId = groupId;
                Log.d(TAG, "选择的群组为:" + groupId);

                break;
            case R.id.spinner_uid:
                Log.d(TAG, "选择的uid为:" + uidSpinner.getSelectedItem());
                // uidSpinner.setSelection(position);
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(MainActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(MainActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };
}