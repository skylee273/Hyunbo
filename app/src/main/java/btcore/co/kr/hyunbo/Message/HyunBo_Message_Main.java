package btcore.co.kr.hyunbo.Message;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import btcore.co.kr.hyunbo.Adpter.HyunBo_Message_Listview_Adapter;
import btcore.co.kr.hyunbo.R;
import btcore.co.kr.hyunbo.Setting.HyunBo_Setting_Main;

import static btcore.co.kr.hyunbo.LoginAndRegister.HyunBo_Login_Main.hyunBo_user_info;
import static btcore.co.kr.hyunbo.DataBase.MySQL_Config.URL_MESSAGE_SELECT;

/**
 * Created by leehaneul on 2018-01-11.
 */

public class HyunBo_Message_Main extends AppCompatActivity implements View.OnClickListener {


    private Button btn_back;
    private ListView listview_message;
    private TextView textview_message;
    private HyunBo_Message_Listview_Adapter adapter;
    private ProgressDialog loading;
    private ArrayList<String> _MessageContext = new ArrayList<String>();
    public static String message_data;
    private String cardNumber = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hyunbo_message_main);

        try {
            cardNumber = hyunBo_user_info.getCard();
        }catch (Exception e){
            cardNumber = hyunBo_user_info.getCard();
        }
        _MessageContext.clear();
        textview_message = (TextView)findViewById(R.id.textview_message);
        textview_message.setVisibility(View.INVISIBLE);
        btn_back = (Button)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);

        listview_message = (ListView) findViewById(R.id.listview_message);
        listview_message.setFocusable(true);
        listview_message.setClickable(true);


        adapter = new HyunBo_Message_Listview_Adapter();
        listview_message.setAdapter(adapter);

        HyunBo_Message_Main.hyunbo_message_selector task =  new HyunBo_Message_Main.hyunbo_message_selector();
        task.execute(cardNumber);

        listview_message.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int i = position;
                message_data = _MessageContext.get(i);

                Intent intent = new Intent(getApplicationContext(), HyunBo_Message_Detail_Main.class );
                startActivity(intent);
                finish();



            }
        });

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), HyunBo_Setting_Main.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:

                Intent intent = new Intent(getApplicationContext(), HyunBo_Setting_Main.class);
                startActivity(intent);
                finish();


                break;
        }
    }

    class hyunbo_message_selector extends AsyncTask<String, Void, String> {
        URL user_url;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(HyunBo_Message_Main.this, "Please Wait", null, true, true);

        }

        public hyunbo_message_selector() {
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
                    _MessageContext.add(_ss[0] + "#####" + _ss[1] + "#####" + _ss[2]);
                    adapter.addItem(_ss[0], _ss[1]);
                    adapter.notifyDataSetChanged();

                }
            } else {
                textview_message.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected String doInBackground(String... params) {

            try {

                String _card = params[0];

                String url_address = URL_MESSAGE_SELECT + "?card=" + _card ;

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
