package btcore.co.kr.hyunbo.Setting;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import btcore.co.kr.hyunbo.R;

import static btcore.co.kr.hyunbo.LoginAndRegister.HyunBo_Login_Main.hyunBo_user_info;
import static btcore.co.kr.hyunbo.DataBase.MySQL_Config.URL_PHONE_UPDATE;

/**
 * Created by leehaneul on 2018-01-11.
 */

public class Hyunbo_Phone_Main extends AppCompatActivity implements View.OnClickListener {

    private TextView textView_state;
    private EditText editText_phone, editText_newphone, editText_newphone2;
    private Button btn_change, btn_back;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hyunbo_phone);

        textView_state = (TextView)findViewById(R.id.textView_state);

        editText_phone = (EditText)findViewById(R.id.editText_phone);
        editText_newphone = (EditText)findViewById(R.id.editText_newphone);
        editText_newphone2 = (EditText)findViewById(R.id.editText_newphone2);

        btn_change = (Button)findViewById(R.id.btn_change);
        btn_back = (Button)findViewById(R.id.btn_back);

        btn_change.setOnClickListener(this);
        btn_back.setOnClickListener(this);

        editText_newphone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(editText_newphone.getText().length() != 11){
                    textView_state.setText("ex) 01011111111");
                    textView_state.setTextColor(Color.RED);
                }else{
                    textView_state.setText("사용가능한 휴대폰 번호 입니다.");
                    textView_state.setTextColor(Color.GREEN);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editText_newphone2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String phone = editText_newphone.getText().toString();
                String phoneconfirm = editText_newphone2.getText().toString();
                if( phone.equals(phoneconfirm)) {
                    textView_state.setText("휴대폰 번호가 일치합니다.");
                    textView_state.setTextColor(Color.GREEN);

                } else {
                    textView_state.setText("휴대폰 번호가 일치하지 않습니다..");
                    textView_state.setTextColor(Color.RED);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_change:
                if(editText_phone.getText().length() == 0 ){
                    Snackbar.make(getWindow().getDecorView().getRootView(), "휴대폰 번호를 입력하세요.", Snackbar.LENGTH_LONG).show();
                    editText_phone.requestFocus();
                }else if(!editText_phone.getText().toString().equals(hyunBo_user_info.getPhone())){
                    Snackbar.make(getWindow().getDecorView().getRootView(), "휴대폰 번호가 일치하지 않습니다.", Snackbar.LENGTH_LONG).show();
                    editText_phone.requestFocus();
                }
                else if(editText_newphone.getText().length() == 0 ){
                    Snackbar.make(getWindow().getDecorView().getRootView(), "휴대폰 번호 확인을 입력하세요.", Snackbar.LENGTH_LONG).show();
                    editText_newphone.requestFocus();
                }
                else if(editText_newphone2.getText().length() == 0 ){
                    Snackbar.make(getWindow().getDecorView().getRootView(), "휴대폰 번호 재입력을 입력하세요.", Snackbar.LENGTH_LONG).show();
                    editText_newphone2.requestFocus();
                }else if(editText_phone.getText().toString().equals(editText_newphone.getText().toString())){
                    Snackbar.make(getWindow().getDecorView().getRootView(), "변경할 휴대폰 번호가 현재 휴대폰번호와 같습니다.", Snackbar.LENGTH_LONG).show();
                }
                else if (!editText_newphone.getText().toString().equals(editText_newphone2.getText().toString())){
                    Snackbar.make(getWindow().getDecorView().getRootView(), "휴대폰 번호가 일치 하지 않습니다.", Snackbar.LENGTH_LONG).show();
                }else if(editText_newphone.getText().length() != 11){
                    Snackbar.make(getWindow().getDecorView().getRootView(), "ex) 01011111111", Snackbar.LENGTH_LONG).show();
                }else{
                    Hyunbo_Phone_Main.hyunbo_phone_update task = new Hyunbo_Phone_Main.hyunbo_phone_update();
                    task.execute(hyunBo_user_info.getCard(),editText_newphone.getText().toString() );
                }


                break;
            case R.id.btn_back:
                Intent intent = new Intent(getApplicationContext(), HyunBo_Setting_Main.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    class hyunbo_phone_update extends AsyncTask<String, Void, String> {
        URL user_url;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(Hyunbo_Phone_Main.this, "Please Wait", null, true, true);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.dismiss();

            String temp = s;

            if (temp.contains("success")) {
                Intent intent = new Intent(getApplicationContext(), HyunBo_Setting_Main.class);
                startActivity(intent);
                finish();

            } else {
                Snackbar.make(getWindow().getDecorView().getRootView(), "비밀번호가 변경되지 않았습니다. 확인해 주세요..", Snackbar.LENGTH_LONG).show();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                String _card = params[0];
                String _phone = params[1];

                String url_address = URL_PHONE_UPDATE + "?card=" + _card
                        + "&phone=" + _phone;

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
