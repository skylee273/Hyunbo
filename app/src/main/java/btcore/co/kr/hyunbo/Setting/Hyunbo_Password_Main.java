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
import static btcore.co.kr.hyunbo.DataBase.MySQL_Config.URL_PASSWORD_UPDATE;

/**
 * Created by leehaneul on 2018-01-10.
 */

public class Hyunbo_Password_Main extends AppCompatActivity implements View.OnClickListener {

    private TextView textView_state;
    private EditText editText_pw, editText_pw1, editText_pw2;
    private Button btn_change, btn_back;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hyunbo_password);

        textView_state = (TextView)findViewById(R.id.textView_state);

        editText_pw = (EditText)findViewById(R.id.editText_pw);
        editText_pw1 = (EditText)findViewById(R.id.editText_pw1);
        editText_pw2 = (EditText)findViewById(R.id.editText_pw2);

        btn_change = (Button)findViewById(R.id.btn_change);
        btn_back = (Button)findViewById(R.id.btn_back);

        btn_change.setOnClickListener(this);
        btn_back.setOnClickListener(this);

        editText_pw1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(editText_pw1.getText().length() < 6){
                        textView_state.setText("변경하실 비밀번호는 (6~20) 자 이내여야 합니다.");
                        textView_state.setTextColor(Color.RED);
                    }else{
                        textView_state.setText("사용가능한 비밀번호 입니다.");
                        textView_state.setTextColor(Color.GREEN);
                    }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editText_pw2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String pw = editText_pw1.getText().toString();
                String pwconfirm = editText_pw2.getText().toString();
                if( pw.equals(pwconfirm)) {
                    textView_state.setText("비밀번호가 일치합니다.");
                    textView_state.setTextColor(Color.GREEN);

                } else {
                    textView_state.setText("비밀번호가 일치하지 않습니다..");
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
                if(editText_pw.getText().length() == 0 ){
                    Snackbar.make(getWindow().getDecorView().getRootView(), "비밀번호를 입력하세요.", Snackbar.LENGTH_LONG).show();
                    editText_pw.requestFocus();
                }
                else if(editText_pw1.getText().length() == 0 ){
                    Snackbar.make(getWindow().getDecorView().getRootView(), "비밀번호 확인을 입력하세요.", Snackbar.LENGTH_LONG).show();
                    editText_pw1.requestFocus();
                }
                else if(editText_pw2.getText().length() == 0 ){
                    Snackbar.make(getWindow().getDecorView().getRootView(), "비밀번호 재입력을 입력하세요.", Snackbar.LENGTH_LONG).show();
                    editText_pw2.requestFocus();
                }else if(editText_pw.getText().toString().equals(editText_pw1.getText().toString())){
                    Snackbar.make(getWindow().getDecorView().getRootView(), "변경할 비밀번호가 현재 비밀번호와 같습니다.", Snackbar.LENGTH_LONG).show();
                }
                else if (!editText_pw1.getText().toString().equals(editText_pw2.getText().toString())){
                    Snackbar.make(getWindow().getDecorView().getRootView(), "비밀번호가 일치 하지 않습니다.", Snackbar.LENGTH_LONG).show();
                }else if(editText_pw1.getText().length() > 20){
                    Snackbar.make(getWindow().getDecorView().getRootView(), "비밀번호는 20자 이내여야 합니다.", Snackbar.LENGTH_LONG).show();
                }else if(editText_pw1.getText().length() < 6){
                    Snackbar.make(getWindow().getDecorView().getRootView(), "비밀번호는 6자 이상이여야 합니다.", Snackbar.LENGTH_LONG).show();
                }else{
                    hyunbo_password_update task = new hyunbo_password_update();
                    task.execute(hyunBo_user_info.getCard(),editText_pw1.getText().toString() );
                }


                break;
            case R.id.btn_back:
                Intent intent = new Intent(getApplicationContext(), HyunBo_Setting_Main.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    class hyunbo_password_update extends AsyncTask<String, Void, String> {
        URL user_url;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(Hyunbo_Password_Main.this, "Please Wait", null, true, true);

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
                String _pw = params[1];

                String url_address = URL_PASSWORD_UPDATE + "?card=" + _card
                        + "&pw=" + _pw;

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
