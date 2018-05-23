package btcore.co.kr.hyunbo.Work;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Calendar;

import btcore.co.kr.hyunbo.Adpter.HyunBo_Month_Date_Listview_Adapter;
import btcore.co.kr.hyunbo.Adpter.HyunBo_Month_Listview_Adapter;
import btcore.co.kr.hyunbo.MainActivity;
import btcore.co.kr.hyunbo.R;

import static btcore.co.kr.hyunbo.LoginAndRegister.HyunBo_Login_Main.hyunBo_user_info;
import static btcore.co.kr.hyunbo.DataBase.MySQL_Config.URL_MONTH;

/**
 * Created by leehaneul on 2017-09-27.
 */

public class HyunBo_Work_Month_Main extends AppCompatActivity implements View.OnClickListener {

    private Button button_today, button_month, button_choice;
    private TextView textView_date;
    private String month_array[] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private ListView listview_month, listview_date;
    private HyunBo_Month_Listview_Adapter adapter;
    private HyunBo_Month_Date_Listview_Adapter date_adapter;
    private ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hyunbo_month_main);

        long now = System.currentTimeMillis();


        String strCurMonth = String.format("%d%02d",year(),month());
        String _strMonth = String.format("%02d",month());
        String strCurYear = String.format("%d",year());

        hyunbo_month_select task = new hyunbo_month_select();
        task.execute(hyunBo_user_info.getName(), hyunBo_user_info.getCard(), strCurMonth);

        int Month = Integer.parseInt(_strMonth);
        String strMonth = month_array[Month - 1];


        listview_month = (ListView) findViewById(R.id.listview_month);
        listview_date = (ListView) findViewById(R.id.listview_date);

        listview_date.setFocusable(true);
        listview_date.setClickable(true);
        listview_month.setFocusable(true);
        listview_month.setClickable(true);

        listview_date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if(action == MotionEvent.ACTION_DOWN){
                }
                else if(action == MotionEvent.ACTION_UP){
                }
                return true;
            }
        });
        listview_month.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if(action == MotionEvent.ACTION_DOWN){
                }
                else if(action == MotionEvent.ACTION_UP){
                }
                return true;
            }
        });
        textView_date = (TextView) findViewById(R.id.textView_date);

        button_today = (Button) findViewById(R.id.button_today);
        button_month = (Button) findViewById(R.id.button_month);
        button_choice = (Button) findViewById(R.id.button_choice);

        button_today.setOnClickListener(this);
        button_month.setOnClickListener(this);
        button_choice.setOnClickListener(this);

        adapter = new HyunBo_Month_Listview_Adapter();
        listview_month.setAdapter(adapter);

        date_adapter = new HyunBo_Month_Date_Listview_Adapter();
        listview_date.setAdapter(date_adapter);




        textView_date.setText(strMonth + "  " + strCurYear);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.button_today:

                Intent intent = new Intent(getApplicationContext(), HyunBo_Work_Main.class);
                startActivity(intent);
                finish();

                break;

            case R.id.button_month:


                break;
            case R.id.button_choice:

                Intent intent1 = new Intent(getApplicationContext(), HyunBo_Work_Choice_Main.class);
                startActivity(intent1);
                finish();

                break;

        }
    }

    class hyunbo_month_select extends AsyncTask<String, Void, String> {
        URL user_url;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(HyunBo_Work_Month_Main.this, "Please Wait", null, true, true);

        }

        public hyunbo_month_select() {
            super();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.dismiss();

            String temp = s;

            if (!temp.equals("")) {
                String[] _temp = temp.split("&&&&&");
                for (String ss : _temp) {
                    String[] _ss = ss.split(",");
                    date_adapter.addItem(_ss[0]);
                    adapter.addItem(_ss[1],_ss[2],_ss[3],_ss[4],_ss[5], _ss[6], _ss[7], _ss[8]);


                    adapter.notifyDataSetChanged();
                    date_adapter.notifyDataSetChanged();


                }
            } else {
                Snackbar.make(getWindow().getDecorView().getRootView(), "이번달 출근 기록이 없습니다.", Snackbar.LENGTH_LONG).show();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                String _name = params[0];
                String _card = params[1];
                String _date = params[2];

                String url_address = URL_MONTH + "?name=" + _name
                        + "&card=" + _card.replace(" ", "")
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