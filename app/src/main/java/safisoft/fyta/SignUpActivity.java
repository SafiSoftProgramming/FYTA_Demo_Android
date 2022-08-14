package safisoft.fyta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;

public class SignUpActivity extends AppCompatActivity {

    ImageButton btn_login_demo_to , btn_login_demo_facebook , btn_login_demo_google , btn_login_demo_fyta , btn_terms ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        btn_login_demo_to = findViewById(R.id.btn_login_demo_to);
        btn_login_demo_facebook = findViewById(R.id.btn_login_demo_facebook);
        btn_login_demo_google = findViewById(R.id.btn_login_demo_google);
        btn_login_demo_fyta = findViewById(R.id.btn_login_demo_fyta);
        btn_terms = findViewById(R.id.btn_terms);


        btn_login_demo_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                fileList();
            }
        });
        btn_login_demo_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DEMO();
            }
        });
        btn_login_demo_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DEMO();
            }
        });
        btn_login_demo_fyta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, RegisterActivity.class);
                startActivity(intent);
                fileList();
            }
        });
        btn_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DEMO();
            }
        });



    }








    private void DEMO(){
        SnackBarInfoControl snackBarInfoControl = new SnackBarInfoControl();
        snackBarInfoControl.SnackBarInfoControlView( findViewById(android.R.id.content).getRootView(), SignUpActivity.this,"Sorry not include in this demo version.",R.raw.ic_sensor_face_sad);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SignUpActivity.this, SettingsActivity.class);
        startActivity(intent);
        fileList();
    }







}