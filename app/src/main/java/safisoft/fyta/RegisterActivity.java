package safisoft.fyta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class RegisterActivity extends AppCompatActivity {

    ImageButton btn_demo_r1 , btn_demo_r2 , btn_demo_r3 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_demo_r1 = findViewById(R.id.btn_demo_r1);
        btn_demo_r2 = findViewById(R.id.btn_demo_r2);
        btn_demo_r3 = findViewById(R.id.btn_demo_r3);

        btn_demo_r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DEMO();
            }
        });

        btn_demo_r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               DEMO();
            }
        });

        btn_demo_r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              DEMO();
            }
        });
    }

    private void DEMO(){
        SnackBarInfoControl snackBarInfoControl = new SnackBarInfoControl();
        snackBarInfoControl.SnackBarInfoControlView( findViewById(android.R.id.content).getRootView(), RegisterActivity.this,"Sorry not include in this demo version.",R.raw.ic_sensor_face_sad);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(RegisterActivity.this, SignUpActivity.class);
        startActivity(intent);
        fileList();
    }



}


