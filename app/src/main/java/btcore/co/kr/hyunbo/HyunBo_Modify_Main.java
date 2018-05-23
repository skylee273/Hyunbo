package btcore.co.kr.hyunbo;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

import btcore.co.kr.hyunbo.Work.HyunBo_Work_Main;

import static btcore.co.kr.hyunbo.LoginAndRegister.HyunBo_Login_Main.hyunBo_user_info;
import static btcore.co.kr.hyunbo.DataBase.MySQL_Config.URL_MODIFY_REGISTER;

/**
 * Created by leehaneul on 2017-09-27.
 */

public class HyunBo_Modify_Main extends AppCompatActivity implements View.OnClickListener {


    private Button hyunbo_modify_back_button, hyunbo_modify_calender_button, hyunbo_modify_register_button;
    private EditText hyunbo_modify_edittext;
    private TextView textView_date, textView_day, textView_eyear, textView_year;
    private String Day;
    int year, month, day;
    private String month_array[] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private Calendar calendar;
    private ProgressDialog loading;
    private String Modify_date = null;

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hyunbo_modify_main);


        long now = System.currentTimeMillis();

        Date date = new Date(now);

        calendar = Calendar.getInstance();
        int _day = calendar.get(calendar.DAY_OF_WEEK);


        Modify_date = String.format("%d.%02d.%02d",year(),month(), date());
        String strCurDay = String.format("%02d",date());
        String strCurMonth = String.format("%02d",month());
        String strCurYear = String.format("%d",year());

        int Month = Integer.parseInt(strCurMonth);
        String strMonth = month_array[Month - 1];

        hyunbo_modify_edittext = (EditText) findViewById(R.id.hyunbo_modify_edittext);

        textView_date = (TextView) findViewById(R.id.textView_date);
        textView_day = (TextView) findViewById(R.id.textView_day);
        textView_eyear = (TextView) findViewById(R.id.textView_eyear);
        textView_year = (TextView) findViewById(R.id.textView_year);

        hyunbo_modify_back_button = (Button) findViewById(R.id.hyunbo_modify_back_button);
        hyunbo_modify_calender_button = (Button) findViewById(R.id.hyunbo_modify_calender_button);
        hyunbo_modify_register_button = (Button) findViewById(R.id.hyunbo_modify_register_button);


        hyunbo_modify_back_button.setOnClickListener(this);
        hyunbo_modify_calender_button.setOnClickListener(this);
        hyunbo_modify_register_button.setOnClickListener(this);


        textView_day.setText(strCurDay);
        textView_date.setText(date_set(_day));
        textView_eyear.setText(strMonth);
        textView_year.setText(strCurYear);


    }


    private String date_set(int day) {

        switch (day - 1) {
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
    private String date(int day) {

        switch (day - 1) {
            case 0:

                Day = "Monday";
                break;
            case 1:

                Day = "Tuesday";
                break;
            case 2:

                Day = "Wednesday";
                break;
            case 3:

                Day = "Thursday";
                break;

            case 4:

                Day = "Friday";
                break;
            case 5:

                Day = "Saturday";
                break;
            case 6:

                Day = "Sunday";
                break;

            default:
                Day = "Monday";
                break;
        }
        return Day;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.hyunbo_modify_back_button:

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();

                break;

            case R.id.hyunbo_modify_calender_button:

                long now = System.currentTimeMillis();

                String strCurYear = String.format("%d",year());

                String strCurMonth = String.format("%02d",month());

                String strCurDay = String.format("%02d",date());;

                year = Integer.parseInt(strCurYear);
                month = Integer.parseInt(strCurMonth);
                day = Integer.parseInt(strCurDay);

                new DatePickerDialog(HyunBo_Modify_Main.this, dateSetListener, year, month-1, day).show();

                break;

            case R.id.hyunbo_modify_register_button:
                try{
                    if(!Modify_date.toString().equals("") && !Modify_date.toString().equals(null) && !hyunBo_user_info.getCard().equals(null) && !hyunBo_user_info.getName().equals(null)
                            && !hyunbo_modify_edittext.getText().toString().equals("") && !hyunbo_modify_edittext.getText().toString().equals(null)){
                        register_modify(hyunBo_user_info.getCard(), hyunBo_user_info.getName(), Modify_date, hyunbo_modify_edittext.getText().toString());
                    }else{
                        Toast.makeText(getApplicationContext(), "날짜와 메시지를 입력해주세요.",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "날짜와 메시지를 입력해주세요.",Toast.LENGTH_SHORT).show();
                }


                break;
        }
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {


        @Override

        public void onDateSet(DatePicker view, int year, int monthOfYear,

                              int dayOfMonth) {

            // TODO Auto-generated method stub


            String msg = String.format("%d-%02d-%d ", year, monthOfYear+1, dayOfMonth);
            Modify_date = String.format("%d.%02d.%d ", year, monthOfYear+1, dayOfMonth);
            String[] data = msg.split("-");
            int month = monthOfYear+1;
            if(monthOfYear+1  < 3){
                year--;
                month += 12;
            }
            int cal = (year + year / 4 - year / 100 + year / 400 + ( 13 * (month) + 8) / 5 + dayOfMonth) % 7;

            textView_day.setText(data[2]);
            textView_date.setText(date(cal));
            textView_eyear.setText(month_array[Integer.parseInt(data[1]) - 1]);
            textView_year.setText(data[0]);


        }

    };

    private void register_modify(
            String card, String name, String date, String message
    )
    {
        class modify extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            URL register_url;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                loading = ProgressDialog.show(Share_bicycle_Register.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //     loading.dismiss();


                if(s.equals("success"))
                {
                    Toast.makeText(getApplicationContext(), "수정 완료",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), HyunBo_Work_Main.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "수정 실패",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), HyunBo_Modify_Main.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            protected String doInBackground(String... params) {

                try{
                    String _card          = params[0];
                    String _name          = params[1];
                    String _date        = params[2];
                    String _message       = params[3].replace(" ", "$");


                    String url_address = URL_MODIFY_REGISTER + "?card=" + _card.replace(" ", "")
                            + "&name=" + _name.replace(" ", "")
                            + "&date=" + _date.replace(" ", "")
                            + "&message=" + _message.replace("\n", "#");

                    register_url = new URL(url_address);
                    BufferedReader in = new BufferedReader(new InputStreamReader(register_url.openStream()));

                    String result = "";
                    String temp = "";
                    while((temp = in.readLine()) != null)
                    {
                        result += temp;
                    }
                    in.close();

                    return result;
                }
                catch(Exception e){
                    return new String("Exception: " + e.getMessage());
                }
            }
        }
        modify task = new modify();
        task.execute(card, name, date, message);

        finish();
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
