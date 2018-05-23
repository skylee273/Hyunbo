package btcore.co.kr.hyunbo.Notice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import btcore.co.kr.hyunbo.Adpter.HyunBo_Notice_Listview_Adapter;
import btcore.co.kr.hyunbo.MainActivity;
import btcore.co.kr.hyunbo.R;

import static btcore.co.kr.hyunbo.DataBase.MySQL_Config.URL_NOTICE_SELECT;
import static btcore.co.kr.hyunbo.DataBase.MySQL_Config.URL_NOTICE_UPDATE;

/**
 * Created by leehaneul on 2017-10-16.
 */

public class HyunBo_Notice_Main extends AppCompatActivity implements View.OnClickListener {


    private Button btn_back;
    private ListView listview_notice;
    private HyunBo_Notice_Listview_Adapter adapter;
    private ProgressDialog loading;
    public static ArrayList<String> _Context = new ArrayList<String>();
    public static String notice_data;
    String date = null;
    int click;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hyunbo_notice_main);

        _Context.clear();

        btn_back = (Button)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);

        listview_notice = (ListView) findViewById(R.id.listview_notice);
        listview_notice.setFocusable(true);
        listview_notice.setClickable(true);


        adapter = new HyunBo_Notice_Listview_Adapter();
        listview_notice.setAdapter(adapter);

        hyunbo_notice_selector task =  new hyunbo_notice_selector();
        task.execute();

        listview_notice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int i = position;
                notice_data = _Context.get(i);
                String update[] = _Context.get(i).split("#####");
                click = Integer.valueOf(update[3]) + 1;
                date = update[0];

                hyunbo_notice_update task = new hyunbo_notice_update();
                task.execute(String.valueOf(click), date);

            }
        });

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
        switch (v.getId()){
            case R.id.btn_back:

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();


                break;
        }
    }

    class hyunbo_notice_selector extends AsyncTask<String, Void, String> {
        URL user_url;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(HyunBo_Notice_Main.this, "Please Wait", null, true, true);

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
                String[] _temp = temp.split("&&&&&");
                for (String ss : _temp) {
                    String[] _ss = ss.split("&&&&");
                    click = Integer.parseInt(_ss[3]);
                    _Context.add(_ss[0] + "#####" + _ss[1] + "#####" + _ss[2] + "#####" + _ss[3]);
                    adapter.addItem(_ss[0].substring(0,10), _ss[1],  "조회수 : " + _ss[3]);
                    adapter.notifyDataSetChanged();

                }
            } else {
                Toast.makeText(getApplicationContext(), "연결 실패", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            try {


                String url_address = URL_NOTICE_SELECT;

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

    class hyunbo_notice_update extends AsyncTask<String, Void, String> {
        URL user_url;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(HyunBo_Notice_Main.this, "Please Wait", null, true, true);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.dismiss();

            String temp = s;

            try{
                if (temp.contains("success")) {
                    Intent intent = new Intent(getApplicationContext(), HyunBo_Notice_Detail_Main.class );
                    startActivity(intent);
                    finish();
                } else {

                }
            }catch (IllegalArgumentException e){
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                String _click = params[0];
                String _date = params[1];

                String url_address = URL_NOTICE_UPDATE + "?click=" + _click
                        +"&date=" + _date;

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
