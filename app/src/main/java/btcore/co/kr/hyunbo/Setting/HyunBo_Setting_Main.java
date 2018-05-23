package btcore.co.kr.hyunbo.Setting;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import btcore.co.kr.hyunbo.Message.HyunBo_Message_Main;
import btcore.co.kr.hyunbo.MainActivity;
import btcore.co.kr.hyunbo.Notice.HyunBo_Notice_Main;
import btcore.co.kr.hyunbo.R;

import static btcore.co.kr.hyunbo.LoginAndRegister.HyunBo_Login_Main.editor;
import static btcore.co.kr.hyunbo.LoginAndRegister.HyunBo_Login_Main.pref;

/**
 * Created by leehaneul on 2017-10-13.
 */

public class HyunBo_Setting_Main extends AppCompatActivity implements View.OnClickListener {

    private Button btn_back, btn_save, btn_phone_change, btn_password, btn_notice, btn_hyunbo, btn_question, btn_version, btn_message;
    private CheckBox checkBox_auto;
    String _version = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hyunbo_setting_main);

        btn_back = (Button) findViewById(R.id.btn_back);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_password = (Button) findViewById(R.id.btn_password);
        btn_phone_change = (Button) findViewById(R.id.btn_phone_change);
        btn_notice = (Button) findViewById(R.id.btn_notice);
        btn_hyunbo = (Button) findViewById(R.id.btn_hyunbo);
        btn_question = (Button) findViewById(R.id.btn_question);
        btn_version = (Button) findViewById(R.id.btn_version);
        btn_message = (Button) findViewById(R.id.btn_message);


        btn_back.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        btn_password.setOnClickListener(this);
        btn_phone_change.setOnClickListener(this);
        btn_notice.setOnClickListener(this);
        btn_hyunbo.setOnClickListener(this);
        btn_question.setOnClickListener(this);
        btn_version.setOnClickListener(this);
        btn_message.setOnClickListener(this);


        checkBox_auto = (CheckBox) findViewById(R.id.checkBox_auto);
        checkBox_auto.setChecked(false);
        if (pref.getBoolean("autoLogin", false)) {
            checkBox_auto.setChecked(true);
        }

        checkBox_auto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                } else {
                    // if unChecked, removeAll
                    pref = getSharedPreferences("pref", getApplicationContext().MODE_PRIVATE);
                    editor = pref.edit();
                    editor.clear();
                    editor.commit();
                }
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

        switch (v.getId()) {
            case R.id.btn_back:

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();

                break;
            case R.id.btn_save:

                Toast.makeText(getApplicationContext(), "저장 되었습니다.", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent1);
                finish();

                break;
            case R.id.btn_password:

                    Intent intent4 = new Intent(getApplicationContext(), Hyunbo_Password_Main.class);
                    startActivity(intent4);
                    finish();

                break;
            case R.id.btn_phone_change:
                Intent intent6 = new Intent(getApplicationContext(), Hyunbo_Phone_Main.class);
                startActivity(intent6);
                finish();
                break;
            case R.id.btn_notice:
                    Intent intent2 = new Intent(getApplicationContext(), HyunBo_Notice_Main.class);
                    startActivity(intent2);
                    finish();
                break;
            case R.id.btn_hyunbo:
                    Intent intent3 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.hyunbo.com/?home"));
                    startActivity(intent3);
                break;
            case R.id.btn_question:
                Snackbar.make(getWindow().getDecorView().getRootView(), "궁금하신 사항은 041-552-5515 로 문의 부탁드립니다.", Snackbar.LENGTH_LONG).show();
                break;
            case R.id.btn_version:
                PackageInfo i = null;
                try {
                    i = getApplicationContext().getPackageManager().getPackageInfo(getApplication().getPackageName(),0);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                _version = i.versionName;
                Snackbar.make(getWindow().getDecorView().getRootView(), "Ver. " + _version  + " 최신 버전입니다.", Snackbar.LENGTH_LONG).show();

                break;

            case R.id.btn_message:

                Intent intent5 = new Intent(getApplicationContext(), HyunBo_Message_Main.class);
                startActivity(intent5);
                finish();
                break;


        }
    }
}
