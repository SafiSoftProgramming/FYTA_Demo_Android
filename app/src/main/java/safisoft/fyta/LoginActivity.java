package safisoft.fyta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class LoginActivity extends AppCompatActivity {

    ImageButton btn_login_demo_login , btn_login_demo_pass ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        btn_login_demo_login =findViewById(R.id.btn_login_demo_login);
        btn_login_demo_pass =findViewById(R.id.btn_login_demo_pass);





        btn_login_demo_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DEMO();
            }
        });
        btn_login_demo_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DEMO();
            }
        });



    }

    private void DEMO(){
        SnackBarInfoControl snackBarInfoControl = new SnackBarInfoControl();
        snackBarInfoControl.SnackBarInfoControlView( findViewById(android.R.id.content).getRootView(), LoginActivity.this,"Sorry not include in this demo version.",R.raw.ic_sensor_face_sad);
    }


}