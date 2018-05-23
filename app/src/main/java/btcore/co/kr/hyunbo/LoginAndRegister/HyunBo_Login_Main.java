package btcore.co.kr.hyunbo.LoginAndRegister;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.URL;

import btcore.co.kr.hyunbo.Item.HyunBo_User_Info;
import btcore.co.kr.hyunbo.MarketVersionChecker;
import btcore.co.kr.hyunbo.MainActivity;
import btcore.co.kr.hyunbo.R;

import static btcore.co.kr.hyunbo.DataBase.MySQL_Config.URL_LOGIN;
import static btcore.co.kr.hyunbo.DataBase.MySQL_Config.URL_USER_INFO;
import static btcore.co.kr.hyunbo.DataBase.MySQL_Config.URL_VERSION_CHECK;

/**
 * Created by leehaneul on 2017-09-25.
 */

public class HyunBo_Login_Main extends AppCompatActivity implements View.OnClickListener {

    private EditText editText_id, editText_pw;
    private Button button_login;
    private ProgressDialog loading;
    private CheckBox checkBox_Auto;
    private String device_version;
    private String stroe_version;
    private String save_id, _pw;
    private BackgroundThread mBackgroundThread;

    public static HyunBo_User_Info hyunBo_user_info;
    public static SharedPreferences.Editor editor;
    public static SharedPreferences pref = null;
    public static Boolean loginChecked = false;
    public static String User_ID = null;

    AlertDialog.Builder alert_confirm;
    AlertDialog alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hyunbo_login_main);

        try{
            mBackgroundThread = new BackgroundThread();
            mBackgroundThread.start();

        }catch (NullPointerException e){
            Log.d("HyunBo_Login_Main", "null");
        }


        pref = getSharedPreferences("pref", AppCompatActivity.MODE_PRIVATE);
        editor = pref.edit();

        hyunBo_user_info = new HyunBo_User_Info();

        editText_id = (EditText)findViewById(R.id.editText_id);
        editText_pw = (EditText)findViewById(R.id.editText_pw);

        button_login = (Button)findViewById(R.id.button_login);

        button_login.setOnClickListener(this);


        checkBox_Auto = (CheckBox)findViewById(R.id.checkBox_Auto);
        checkBox_Auto.setChecked(false);


        checkBox_Auto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {
                    loginChecked = true;
                } else {
                    // if unChecked, removeAll
                    loginChecked = false;
                    editor.clear();
                    editor.commit();
                }
            }
        });



    }
    public class BackgroundThread extends Thread{
        @Override
        public void  run(){
            stroe_version = MarketVersionChecker.getMarketVersion(getPackageName());

            try{
                device_version = getPackageManager().getPackageInfo(getPackageName(),0).versionName;
            }catch (PackageManager.NameNotFoundException e){
                e.printStackTrace();
            }

            deviceVersionCheckHandler.sendMessage(deviceVersionCheckHandler.obtainMessage());
        }
    }
    private final DeviceVersionCheckHandler deviceVersionCheckHandler = new DeviceVersionCheckHandler(this);

    private static class DeviceVersionCheckHandler extends Handler {
        private final WeakReference<HyunBo_Login_Main> mainActivityWeakReference;
        public DeviceVersionCheckHandler(HyunBo_Login_Main mainActivity){
            mainActivityWeakReference = new WeakReference<HyunBo_Login_Main>(mainActivity);
        }
        @Override
        public void handleMessage(Message msg){
            HyunBo_Login_Main activity = mainActivityWeakReference.get();
            if(activity != null){
                activity.handleMessage(msg);
            }
        }
    }

    private void handleMessage(Message msg){
        try{
            if(stroe_version.compareTo(device_version) > 0){
                alert_confirm = new AlertDialog.Builder(HyunBo_Login_Main.this);
                alert_confirm.setTitle("업데이트");
                alert_confirm.setMessage("새로운 버전이 있습니다. \n보다 나은 사용을 위해 업데이트 해 주세요.").setCancelable(false).setPositiveButton("업데이트바로가기",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=btcore.co.kr.hyunbo"));
                                startActivity(intent);
                            }
                        }).setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 'No'
                                return;
                            }
                        });
                alert = alert_confirm.create();
                alert.show();
            }else {
                if (pref.getBoolean("autoLogin", false)) {
                    save_id = pref.getString("id", "");
                    _pw = pref.getString("pw", "");
                    checkBox_Auto.setChecked(true);
                    User_ID = save_id;
                    Rankers_Login(save_id, _pw);
                    // goto mainActivity

                }
            }
        }catch (NullPointerException e){
            if (pref.getBoolean("autoLogin", false)) {
                save_id = pref.getString("id", "");
                _pw = pref.getString("pw", "");
                checkBox_Auto.setChecked(true);
                User_ID = save_id;
                Rankers_Login(save_id, _pw);
                // goto mainActivity

            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_login:

                if(!editText_pw.getText().toString().equals("") && !editText_pw.getText().toString().equals(null) && !editText_id.getText().toString().equals("") && !editText_id.getText().toString().equals(null) ){

                    if(loginChecked) {
                        // if autoLogin Checked, save values
                        editor.putString("id", editText_id.getText().toString());
                        editor.putString("pw", editText_pw.getText().toString());
                        editor.putBoolean("autoLogin", true);
                        editor.commit();
                    }
                    User_ID = editText_id.getText().toString();
                    Rankers_Login(editText_id.getText().toString(), editText_pw.getText().toString());


                }else {
                    Toast.makeText(getApplicationContext(), "아이디와 패스워드를 입력해주세요.", Toast.LENGTH_SHORT).show();

                }
                break;

        }
    }

    private void Rankers_Login(String id, String pw) {
        class rankers_login extends AsyncTask<String, Void, String> {
            URL login_url;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                if (s.contains("success")) {

                    hyunbo_user_set task = new hyunbo_user_set();
                    task.execute(User_ID);



                } else {
                    Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected String doInBackground(String... params) {

                try {
                    String _id = params[0];
                    String _pw = params[1];


                    String url_address = URL_LOGIN + "?ID=" + _id
                            + "&PW=" + _pw;

                    login_url = new URL(url_address);
                    BufferedReader in = new BufferedReader(new InputStreamReader(login_url.openStream()));

                    String result = "";
                    String temp = "";
                    while ((temp = in.readLine()) != null) {
                        result += temp;
                    }
                    in.close();

                    return result;
                } catch (Exception e) {
                    return new String("Exception: " + e.getMessage());
                }
            }
        }
        rankers_login login_task = new rankers_login();
        login_task.execute(id, pw);
    }

    class hyunbo_user_set extends AsyncTask<String, Void, String> {
        URL user_url;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(HyunBo_Login_Main.this, "Please Wait", null, true, true);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.dismiss();

            String temp = s;

            if (!temp.equals("")) {
                String[] _temp = temp.split(",");

                hyunBo_user_info.setName(_temp[0].toString());
                hyunBo_user_info.setDepartment(_temp[1].toString());
                hyunBo_user_info.setRank(_temp[2].toString());
                hyunBo_user_info.setCard(_temp[3].toString());
                hyunBo_user_info.setPhone(_temp[4].toString());
                hyunBo_user_info.setEtc(_temp[5].toString());

                if(alert != null  && alert.isShowing() ){
                    alert.dismiss();
                    alert = null;
                }


                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();



            } else {
                Toast.makeText(getApplicationContext(), "연결 실패", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                String _id = params[0];


                String url_address = URL_USER_INFO + "?id=" + _id;

                user_url = new URL(url_address);
                BufferedReader in = new BufferedReader(new InputStreamReader(user_url.openStream()));

                String result = "";
                String temp = "";
                while ((temp = in.readLine()) != null) {
                    result += temp;
                }
                in.close();

                return result;
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }
    }
}
