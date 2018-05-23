package btcore.co.kr.hyunbo.Work;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
 * Created by leehaneul on 2017-09-25.
 */

public class HyunBo_Work_Main extends AppCompatActivity implements View.OnClickListener {


    private TextView textView_date, textView_day,textView_eyear, textView_year, textView_gotime, textView_outtime, textView_default_time, textView_overtime, textView_state;
    private Button button_work_confirm, button_work_modify, button_month , button_choice;
    private String Day, strDate;
    private String month_array [] = { "January", "February" , "March" , "April" , "May", "June", "July", "August", "September" , "October", "November", "December"};
    private Calendar calendar;
    private ProgressDialog loading;
    int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hyunbo_work_main);

        long now = System.currentTimeMillis();

        calendar = Calendar.getInstance();
        day = calendar.get(calendar.DAY_OF_WEEK);

        strDate = String.format("%4d%02d%02d",year(), month(), date());;
        strDate = String.format("%4d%02d%02d",year(), month(), date());;
        String strCurDay = String.format("%02d",date());
        String strCurMonth = String.format("%02d",month());
        String strCurYear = String.format("%d",year());

        int Month = Integer.parseInt(strCurMonth);
        String strMonth = month_array[Month-1];


        hyunbo_work task = new hyunbo_work();
        task.execute(hyunBo_user_info.getCard() , strDate);



        textView_date = (TextView)findViewById(R.id.textView_date);
        textView_day = (TextView)findViewById(R.id.textView_day);
        textView_eyear = (TextView)findViewById(R.id.textView_eyear);
        textView_year = (TextView)findViewById(R.id.textView_year);
        textView_gotime = (TextView)findViewById(R.id.textView_gotime);
        textView_outtime = (TextView)findViewById(R.id.textView_outtime);
        textView_default_time = (TextView)findViewById(R.id.textView_default_time);
        textView_overtime = (TextView)findViewById(R.id.textView_overtime);
        textView_state = (TextView)findViewById(R.id.textView_state);

        button_work_confirm = (Button)findViewById(R.id.button_work_confirm);
        button_work_modify = (Button)findViewById(R.id.button_work_modify);
        button_month = (Button)findViewById(R.id.button_month);
        button_choice = (Button)findViewById(R.id.button_choice);

        button_work_modify.setOnClickListener(this);
        button_work_confirm.setOnClickListener(this);
        button_month.setOnClickListener(this);
        button_choice.setOnClickListener(this);

        textView_day.setText(strCurDay);
        textView_date.setText(date_set(day));
        textView_eyear.setText(strMonth);
        textView_year.setText(strCurYear);
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
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_work_confirm:

                if(textView_state.getText().toString().equals("확인")){
                    Snackbar.make(getWindow().getDecorView().getRootView(), "현재 근태 상태가 확인 상태입니다.", Snackbar.LENGTH_LONG).show();
                }else if(textView_state.getText().toString().equals("미확인")){

                    hyunbo_work_update task = new hyunbo_work_update();
                    task.execute(hyunBo_user_info.getName(), strDate);


                }else{
                    Snackbar.make(getWindow().getDecorView().getRootView(), "출근 기록이 없습니다.", Snackbar.LENGTH_LONG).show();

                }
                break;

            case R.id.button_work_modify:

                Intent intent = new Intent(getApplicationContext(), HyunBo_Modify_Main.class);
                startActivity(intent);
                finish();

                break;

            case R.id.button_month:

                Intent intent1 = new Intent(getApplicationContext(), HyunBo_Work_Month_Main.class);
                startActivity(intent1);
                finish();

                break;

            case R.id.button_choice:

                Intent intent2 = new Intent(getApplicationContext(), HyunBo_Work_Choice_Main.class);
                startActivity(intent2);
                finish();

                break;
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }


    class hyunbo_work extends AsyncTask<String, Void, String> {
        URL user_url;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(HyunBo_Work_Main.this, "Please Wait", null, true, true);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.dismiss();

            String temp = s;

            if (!temp.equals("")) {
                String[] _temp = temp.split(",");

                try{
                    if(date_set(day).equals("Saturday") || date_set(day).equals("Sunday")){
                        textView_gotime.setText(_temp[0].toString());
                        textView_outtime.setText(_temp[1].toString());
                        textView_default_time.setText(_temp[5].toString() + "(특근)");
                        textView_overtime.setText(_temp[6].toString() + "(특근)");
                        textView_default_time.setTextColor(Color.parseColor("#FF0000"));
                        textView_overtime.setTextColor(Color.parseColor("#FF0000"));
                        textView_state.setText(_temp[7].toString());
                    }else{
                        textView_gotime.setText(_temp[0].toString());
                        textView_outtime.setText(_temp[1].toString());
                        textView_default_time.setText(_temp[2].toString());
                        textView_overtime.setText(_temp[3].toString());
                        textView_default_time.setTextColor(Color.parseColor("#58595b"));
                        textView_overtime.setTextColor(Color.parseColor("#58595b"));
                        textView_state.setText(_temp[7].toString());
                    }
                }catch (ArrayIndexOutOfBoundsException e){
                    Log.d("Work", "ArrayIndexOutOfBoundsException");
                }





            } else {
                // Toast.makeText(getApplicationContext(), "연결 실패", Toast.LENGTH_SHORT).show();
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


    class hyunbo_work_update extends AsyncTask<String, Void, String> {
        URL user_url;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(HyunBo_Work_Main.this, "Please Wait", null, true, true);

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