package btcore.co.kr.hyunbo.Work;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Calendar;

import btcore.co.kr.hyunbo.HyunBo_Modify_Main;
import btcore.co.kr.hyunbo.MainActivity;
import btcore.co.kr.hyunbo.R;

import static btcore.co.kr.hyunbo.LoginAndRegister.HyunBo_Login_Main.hyunBo_user_info;
import static btcore.co.kr.hyunbo.DataBase.MySQL_Config.URL_WORK;
import static btcore.co.kr.hyunbo.DataBase.MySQL_Config.URL_WORK_UPDATE;

/**
 * Created by leehaneul on 2017-09-27.
 */

public class HyunBo_Work_Choice_Main extends AppCompatActivity implements View.OnClickListener {


    private CalendarView calender_view;
    private Button button_choice, button_MONTH, button_today, button_confirm, button_modify;
    private TextView textView_date, textView_gotime, textView_outtime, textView_default_time, textView_overtime, textView_state;
    private String strDate, today, Day;
    private ProgressDialog loading;
    private Calendar calendar;
    int day = 0;
    String nDate;
    private String[] weekDay = {"일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hyunbo_choice_main);


        calender_view = (CalendarView)findViewById(R.id.calender_view);
        strDate = String.format("%4d%02d%02d",year() ,month(), date());

        calendar = Calendar.getInstance();
        day = calendar.get(calendar.DAY_OF_WEEK);
        nDate = date_set(day);

        hyunbo_work task = new hyunbo_work();
        task.execute(hyunBo_user_info.getCard() , strDate);

        button_choice = (Button)findViewById(R.id.button_choice);
        button_MONTH = (Button)findViewById(R.id.button_MONTH);
        button_today = (Button)findViewById(R.id.button_today);
        button_confirm = (Button)findViewById(R.id.button_confirm);
        button_modify = (Button)findViewById(R.id.button_modify);

        textView_date = (TextView)findViewById(R.id.textView_date);
        textView_gotime = (TextView)findViewById(R.id.textView_gotime);
        textView_outtime = (TextView)findViewById(R.id.textView_outtime);
        textView_default_time = (TextView)findViewById(R.id.textView_default_time);
        textView_overtime = (TextView)findViewById(R.id.textView_overtime);
        textView_state = (TextView)findViewById(R.id.textView_state);

        button_choice.setOnClickListener(this);
        button_MONTH.setOnClickListener(this);
        button_today.setOnClickListener(this);
        button_confirm.setOnClickListener(this);
        button_modify.setOnClickListener(this);

        textView_date.setText(String.format("%d.%02d.%02d",year(), month(), date()));

        calender_view.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String calender_date = String.format("%d.%02d.%02d",year, month+1, dayOfMonth);
                String set_date = String.format("%4d%02d%02d", year, month+1, dayOfMonth);
                strDate = set_date;
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                nDate = calendar_date_set(dayOfWeek);
                textView_date.setText(calender_date);

                hyunbo_work task = new hyunbo_work();
                task.execute(hyunBo_user_info.getCard() , strDate);


            }
        });
    }


    private String  date_set(int day){

        switch (day-1){

            case 1:
                Day = "Monday";
                break;

            case 2:
                Day = "Tuesday";
                break;

            case 3:
                Day = "Wednesday";
                break;

            case 4:
                Day = "Thursday";
                break;

            case 5:
                Day = "Friday";
                break;

            case 6:
                Day = "Saturday";
                break;

            case 7:
                Day = "Sunday";
                break;

            default:
                Day = "Monday";
                break;
        }
        return Day;
    }
    private String  calendar_date_set(int day){

        switch (day-1){
            case 0:
                Day = "Sunday";
                break;

            case 1:
                Day = "Monday";
                break;

            case 2:
                Day = "Tuesday";
                break;

            case 3:
                Day = "Wednesday";
                break;

            case 4:
                Day = "Thursday";
                break;

            case 5:
                Day = "Friday";
                break;

            case 6:
                Day = "Saturday";
                break;

            default:
                Day = "Monday";
                break;
        }
        return Day;
    }





    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.button_today:

                Intent intent = new Intent(getApplicationContext(), HyunBo_Work_Main.class);
                startActivity(intent);
                finish();

                break;

            case R.id.button_MONTH:

                Intent intent1 = new Intent(getApplicationContext(), HyunBo_Work_Month_Main.class);
                startActivity(intent1);
                finish();

                break;

            case R.id.button_confirm:

                if(textView_state.getText().toString().equals("확인")){
                    Snackbar.make(getWindow().getDecorView().getRootView(), "현재 근태 상태가 확인 상태입니다.", Snackbar.LENGTH_LONG).show();

                }else if(textView_state.getText().toString().equals("미확인")){

                    hyunbo_work_update task = new hyunbo_work_update();
                    task.execute(hyunBo_user_info.getName(), strDate);
                }else{
                    Snackbar.make(getWindow().getDecorView().getRootView(), "출근 기록이 없습니다.", Snackbar.LENGTH_LONG).show();
                }



                break;

            case R.id.button_modify:

                Intent intent2 = new Intent(getApplicationContext(), HyunBo_Modify_Main.class);
                startActivity(intent2);
                finish();

                break;
        }
    }

    class hyunbo_work_update extends AsyncTask<String, Void, String> {
        URL user_url;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(HyunBo_Work_Choice_Main.this, "Please Wait", null, true, true);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.dismiss();

            String temp = s;

            if (temp.contains("success")) {
                Snackbar.make(getWindow().getDecorView().getRootView(), "근태 상태를 확인으로 수정하였습니다.", Snackbar.LENGTH_LONG).show();
                hyunbo_work task = new hyunbo_work();
                task.execute(hyunBo_user_info.getCard() , strDate);
            } else {
                Snackbar.make(getWindow().getDecorView().getRootView(), "근태시간이 정확하지 않습니다.", Snackbar.LENGTH_LONG).show();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                String _name = params[0];
                String _date = params[1];
                String _confirm = "확인";

                String url_address = URL_WORK_UPDATE + "?name=" + _name
                        + "&date=" + _date.replace(" ", "")
                        + "&confirm=" + _confirm;

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

    class hyunbo_work extends AsyncTask<String, Void, String> {
        URL user_url;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(HyunBo_Work_Choice_Main.this, "Please Wait", null, true, true);

        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.dismiss();

            String temp = s;

            if (!temp.equals("")) {
                String[] _temp = temp.split(",");


                    if(nDate.equals("Saturday") || nDate.equals("Sunday") ){
                    textView_default_time.setTextColor(Color.parseColor("#FF0000"));
                    textView_overtime.setTextColor(Color.parseColor("#FF0000"));

                    textView_gotime.setText(_temp[0].toString());
                    textView_outtime.setText(_temp[1].toString());
                    textView_default_time.setText(_temp[5].toString() + "(특근)");
                    textView_overtime.setText(_temp[6].toString() + "(특근)");
                    textView_state.setText(_temp[7].toString());

                }else{
                    textView_gotime.setText(_temp[0].toString());
                    textView_default_time.setTextColor(Color.parseColor("#58595b"));
                    textView_overtime.setTextColor(Color.parseColor("#58595b"));
                    textView_outtime.setText(_temp[1].toString());
                    textView_default_time.setText(_temp[2].toString());
                    textView_overtime.setText(_temp[3].toString());
                    textView_state.setText(_temp[7].toString());
                }





            } else {
                textView_gotime.setText("");
                textView_outtime.setText("");
                textView_default_time.setText("");
                textView_overtime.setText("");
                textView_state.setText("");
                Snackbar.make(getWindow().getDecorView().getRootView(), "해당 날짜에 출근 기록이 없습니다.", Snackbar.LENGTH_LONG).show();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                String _card = params[0];
                String _date = params[1];

                String url_address = URL_WORK + "?card=" + _card.replace(" ", "")
                        + "&date=" + _date.replace(" ", "");

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
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
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
}
