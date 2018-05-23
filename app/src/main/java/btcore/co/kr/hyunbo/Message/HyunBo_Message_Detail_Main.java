package btcore.co.kr.hyunbo.Message;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import btcore.co.kr.hyunbo.R;

/**
 * Created by leehaneul on 2018-01-11.
 */

public class HyunBo_Message_Detail_Main extends AppCompatActivity implements View.OnClickListener {

    private Button btn_back;
    private TextView textView_title, textView_context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hyunbo_message_detail_main);

        textView_title = (TextView)findViewById(R.id.textView_title);
        textView_context = (TextView)findViewById(R.id.textView_context);

        btn_back = (Button)findViewById(R.id.btn_back);

        btn_back.setOnClickListener(this);
        if (!HyunBo_Message_Main.message_data.equals("")) {
            String[] _Detail_data = HyunBo_Message_Main.message_data.split("#####");
            textView_title.setText(_Detail_data[1].toString());
            textView_context.setText(_Detail_data[2].toString());
        }


    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), HyunBo_Message_Main.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_back:

                Intent intent = new Intent(getApplicationContext(), HyunBo_Message_Main.class);
                startActivity(intent);
                finish();

                break;

        }

    }
}