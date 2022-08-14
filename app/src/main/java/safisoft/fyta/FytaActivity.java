package safisoft.fyta;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mikhaellopez.circularimageview.CircularImageView;

public class FytaActivity extends AppCompatActivity {
    String Server_URL ;
    CircularImageView imgv_profile_pic_fyta ;
    ImageView btn_go_home,btn_go_library,btn_go_profile,btn_go_gardens,btn_go_settings,btn_go_fyta;
    ImageButton btn_demo1 , btn_demo2 , btn_demo3  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fyta);

        Server_URL = getResources().getString(R.string.Server_URL);

        imgv_profile_pic_fyta = findViewById(R.id.imgv_profile_pic_fyta);

        btn_demo1 = findViewById(R.id.btn_demo1);
        btn_demo2 = findViewById(R.id.btn_demo2);
        btn_demo3 = findViewById(R.id.btn_demo3);

///////////////////
        btn_go_home = findViewById(R.id.btn_go_home);
        btn_go_library = findViewById(R.id.btn_go_library);
        btn_go_profile = findViewById(R.id.btn_go_profile);
        btn_go_gardens = findViewById(R.id.btn_go_gardens);
        btn_go_settings = findViewById(R.id.btn_go_settings);
        btn_go_fyta = findViewById(R.id.btn_go_fyta);
////////////////////



        btn_demo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnackBarInfoControl snackBarInfoControl = new SnackBarInfoControl();
                snackBarInfoControl.SnackBarInfoControlView( findViewById(android.R.id.content).getRootView(), FytaActivity.this,"Identify not include in this demo version.",R.raw.ic_sensor_face_sad);
            }
        });

        btn_demo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnackBarInfoControl snackBarInfoControl = new SnackBarInfoControl();
                snackBarInfoControl.SnackBarInfoControlView( findViewById(android.R.id.content).getRootView(), FytaActivity.this,"Plant Health not include in this demo version.",R.raw.ic_sensor_face_sad);
            }
        });

        btn_demo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FytaActivity.this, LibraryActivity.class);
                startActivity(intent);
                finish();
            }
        });




        ////////////////////////////////////////////////////////////////
        btn_go_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FytaActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_go_library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FytaActivity.this, LibraryActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_go_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FytaActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_go_gardens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FytaActivity.this, GardensActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_go_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FytaActivity.this, SettingsActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_go_fyta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FytaActivity.this, ShopActivity.class);
                startActivity(intent);
                finish();
            }
        });
        ///////////////////////////////////////////////////////////////




        Glide.with(getApplicationContext())
                .load(Server_URL + getResources().getString(R.string.URL_members_pic)+"prof_9.png")
                .apply(centerCropTransform()
                        //          .placeholder(R.drawable.ic_defult_user_circ)
                        .error(R.drawable.ic_defult_user_circ)
                        .priority(Priority.HIGH))
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(imgv_profile_pic_fyta);

    }
}