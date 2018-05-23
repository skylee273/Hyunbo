package btcore.co.kr.hyunbo;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import btcore.co.kr.hyunbo.BackPressClass.BackPressCloseHandler;
import btcore.co.kr.hyunbo.LoginAndRegister.HyunBo_Login_Main;
import btcore.co.kr.hyunbo.Notice.HyunBo_Notice_Main;
import btcore.co.kr.hyunbo.Setting.HyunBo_Setting_Main;
import btcore.co.kr.hyunbo.Work.HyunBo_Work_Main;

import static btcore.co.kr.hyunbo.DataBase.MySQL_Config.URL_SURVEY_SELECT;
import static btcore.co.kr.hyunbo.DataBase.MySQL_Config.URL_VERSION_CHECK;
import static btcore.co.kr.hyunbo.LoginAndRegister.HyunBo_Login_Main.hyunBo_user_info;
import static btcore.co.kr.hyunbo.DataBase.MySQL_Config.URL_TODAY_NOTICE_SELECT;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private BackPressCloseHandler backPressCloseHandler;
    private TextView textView_date, textView_hour, textview_name, textView_id, textView_notice;
    private Button button_work, button_confirm, btn_setting, btn_notice;
    private Timer mTimer;
    private ProgressDialog loading;
    private String strDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backPressCloseHandler = new BackPressCloseHandler(this);

        strDate = String.format("%4d-%02d-%02d",year(), month(), date());;

        hyunbo_notice_selector task1 = new hyunbo_notice_selector();
        task1.execute(strDate);

        textView_date = (TextView) findViewById(R.id.textView_date);
        textView_hour = (TextView) findViewById(R.id.textView_hour);
        textview_name = (TextView) findViewById(R.id.textview_name);
        textView_id = (TextView) findViewById(R.id.textView_id);
        textView_notice = (TextView)findViewById(R.id.textView_notice);
        textView_notice.setSelected(true);

        button_work = (Button) findViewById(R.id.button_work);
        button_confirm = (Button) findViewById(R.id.button_confirm);
        btn_setting = (Button)findViewById(R.id.btn_setting);
        btn_notice = (Button)findViewById(R.id.btn_notice);


        button_work.setOnClickListener(this);
        button_confirm.setOnClickListener(this);
        btn_setting.setOnClickListener(this);
        btn_notice.setOnClickListener(this);

        MainTimerTask timerTask = new MainTimerTask();
        mTimer = new Timer();
        mTimer.schedule(timerTask, 500, 1000);

        textview_name.setText(hyunBo_user_info.getName());
        textView_id.setText(hyunBo_user_info.getDepartment());

    }


    private Handler mHandler = new Handler();

    private Runnable mUpdateTimeTask = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        public void run() {

            String strCurDate = String.format("%d.%02d.%02d",year(),month(), date());
            String strCurTime = String.format("%02d :  %02d",hour(), min());

            textView_date.setText(strCurDate);
            textView_hour.setText(strCurTime);

        }
    };

    class MainTimerTask extends TimerTask {
        public void run() {
            mHandler.post(mUpdateTimeTask);
        }
    }

    @Override
    protected void onDestroy() {
        mTimer.cancel();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        mTimer.cancel();
        super.onPause();
    }

    @Override
    protected void onResume() {
        try {
            MainTimerTask timerTask = new MainTimerTask();
            mTimer.schedule(timerTask, 500, 3000);
        } catch (IllegalStateException e) {

        }

        super.onResume();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_work:

                try {
                    Intent intent = new Intent(getApplicationContext(), HyunBo_Work_Main.class);
                    startActivity(intent);
                    finish();

                } catch (Exception e) {

                }
                break;

            case R.id.button_confirm:

                hyunbo_survey_selector task = new hyunbo_survey_selector();
                task.execute();

                break;
            case R.id.btn_setting:

                Intent intent = new Intent(getApplicationContext(), HyunBo_Setting_Main.class);
                startActivity(intent);
                finish();

                break;
            case R.id.btn_notice:

                Intent intent1 = new Intent(getApplicationContext(), HyunBo_Notice_Main.class);
                startActivity(intent1);
                finish();

                break;
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }

    private int year(){
        Calendar cal = Calendar.getInstance();

        int year = cal.get(cal.YEAR);
        return year;
    }
    private int month(){
        Calendar cal = Calendar.getInstance();

        int month = cal.get(cal.MONTH) + 1;

        return month;
    }
    private int date(){
        Calendar cal = Calendar.getInstance();


        int date = cal.get(cal.DATE);

        return date;
    }
    private int hour(){
        Calendar cal = Calendar.getInstance();

        int year = cal.get(cal.HOUR);
        return year;
    }
    private int min(){
        Calendar cal = Calendar.getInstance();

        int year = cal.get(cal.MINUTE);
        return year;
    }
    class hyunbo_survey_selector extends AsyncTask<String, Void, String> {
        URL user_url;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(MainActivity.this, "Please Wait", null, true, true);

        }

        public hyunbo_survey_selector() {
            super();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.dismiss();

            String temp = s;

            if (!temp.equals("false")) {
                Intent intent3 = new Intent(Intent.ACTION_VIEW, Uri.parse(temp));
                startActivity(intent3);
            }else{
                Snackbar.make(getWindow().getDecorView().getRootView(), "설문조사가 없습니다.", Snackbar.LENGTH_LONG).show();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            try {


                String url_address = URL_SURVEY_SELECT;

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

    class hyunbo_notice_selector extends AsyncTask<String, Void, String> {
        URL user_url;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(MainActivity.this, "Please Wait", null, true, true);

        }

        public hyunbo_notice_selector() {
            super();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.dismiss();

            String temp = s;

            if (!temp.equals("")) {

                textView_notice.setText(strDate +"  " +temp  + " 공지사항 확인 부탁드립니다." );

            } else {
                // Toast.makeText(getApplicationContext(), "연결 실패", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            try {

                String _date = params[0];

                String url_address = URL_TODAY_NOTICE_SELECT  + "?date=" + _date;

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
