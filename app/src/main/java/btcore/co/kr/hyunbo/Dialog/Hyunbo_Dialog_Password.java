package btcore.co.kr.hyunbo.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import btcore.co.kr.hyunbo.R;

/**
 * Created by leehaneul on 2017-10-13.
 */

public class Hyunbo_Dialog_Password extends Dialog {



    public Hyunbo_Dialog_Password(Context context) {
        super(context);
        requestWindowFeature( Window.FEATURE_NO_TITLE ) ;
        getWindow().setBackgroundDrawable( new ColorDrawable( Color.WHITE ) ) ;
        setContentView(R.layout.hyunbo_dialog_password);

        WindowManager.LayoutParams lp = getWindow().getAttributes( ) ;
        WindowManager wm = ((WindowManager)context.getApplicationContext().getSystemService(context.getApplicationContext().WINDOW_SERVICE)) ;
        lp.width =  (int)( wm.getDefaultDisplay().getWidth( ) * 0.5 );
        lp.height = (int)(wm.getDefaultDisplay().getHeight() * 0.7);


        getWindow().setAttributes( lp ) ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }




}