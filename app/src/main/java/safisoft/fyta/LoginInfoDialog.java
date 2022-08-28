package safisoft.fyta;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class LoginInfoDialog extends Dialog implements
        View.OnClickListener {

    public ImageButton btn_ok_in_dialog, btn_cancel_in_dialog;

    public Activity c;

    public LoginInfoDialog(@NonNull Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_info_dialog);
       btn_ok_in_dialog = findViewById(R.id.btn_ok_in_dialog);






        btn_ok_in_dialog.setOnClickListener(this);
    //    btn_cancel_in_dialog.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
           case R.id.btn_ok_in_dialog:
               c.finish();
               break;
    //       case R.id.btn_cancel_in_dialog:
    //           dismiss();
    //           break;
    //       default:
    //           break;
        }
        dismiss();

    }
}
