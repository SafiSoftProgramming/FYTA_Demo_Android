package safisoft.fyta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.airbnb.lottie.LottieAnimationView;

public class ShopActivity extends AppCompatActivity {

    LinearLayout linearLayout_activity_control ;
    RelativeLayout main_notification_layout ;
    ScrollView recyclerView;
    LottieAnimationView lottie_cart ;
    LottieAnimationView lottie_fun ;
    TextView txtv_cart_count ;
    ImageButton btn_cart1,btn_cart2,btn_cart3,btn_cart4,btn_cart5,btn_cart6,btn_cart7 ;
    ImageButton btn_info1,btn_info2,btn_info3,btn_info4,btn_info5,btn_info6,btn_info7 ;
    boolean visb = true ;

    ImageView btn_go_home,btn_go_library,btn_go_profile,btn_go_gardens,btn_go_settings,btn_go_fyta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);



        recyclerView = findViewById(R.id.recyclerView);
        linearLayout_activity_control = (LinearLayout)findViewById(R.id.linearLayout_activity_control);
        main_notification_layout = (RelativeLayout)findViewById(R.id.main_notification_layout);
        lottie_cart = findViewById(R.id.lott_cart);
        lottie_fun = findViewById(R.id.lott_fun);
        txtv_cart_count = findViewById(R.id.txtv_cart_count);
        btn_cart1 = findViewById(R.id.btn_cart1);
        btn_cart2 = findViewById(R.id.btn_cart2);
        btn_cart3 = findViewById(R.id.btn_cart3);
        btn_cart4 = findViewById(R.id.btn_cart4);
        btn_cart5 = findViewById(R.id.btn_cart5);
        btn_cart6 = findViewById(R.id.btn_cart6);
        btn_cart7 = findViewById(R.id.btn_cart7);

        btn_info1 = findViewById(R.id.btn_info1);
        btn_info2 = findViewById(R.id.btn_info2);
        btn_info3 = findViewById(R.id.btn_info3);
        btn_info4 = findViewById(R.id.btn_info4);
        btn_info5 = findViewById(R.id.btn_info5);
        btn_info6 = findViewById(R.id.btn_info6);
        btn_info7 = findViewById(R.id.btn_info7);

///////////////////
        btn_go_home = findViewById(R.id.btn_go_home);
        btn_go_library = findViewById(R.id.btn_go_library);
        btn_go_profile = findViewById(R.id.btn_go_profile);
        btn_go_gardens = findViewById(R.id.btn_go_gardens);
        btn_go_settings = findViewById(R.id.btn_go_settings);
        btn_go_fyta = findViewById(R.id.btn_go_fyta);
////////////////////




        ViewFlipper flipper = (ViewFlipper) findViewById(R.id.flipper1);
        ViewFlipper flipper2 = (ViewFlipper) findViewById(R.id.flipper2);
        ViewFlipper flipper3 = (ViewFlipper) findViewById(R.id.flipper3);
        ViewFlipper flipper4 = (ViewFlipper) findViewById(R.id.flipper4);
        ViewFlipper flipper5 = (ViewFlipper) findViewById(R.id.flipper5);
        ViewFlipper flipper6 = (ViewFlipper) findViewById(R.id.flipper6);
        ViewFlipper flipper7 = (ViewFlipper) findViewById(R.id.flipper7);
        flipper.startFlipping();
        flipper2.startFlipping();
        flipper3.startFlipping();
        flipper4.startFlipping();
        flipper5.startFlipping();
        flipper6.startFlipping();
        flipper7.startFlipping();


        recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY > 0 && visb == true) {
                    main_notification_layout.removeView(linearLayout_activity_control);
                    visb = false ;
                } else if (scrollY < 0 && visb == false) {
                    if(linearLayout_activity_control.getParent() != null) {
                        ((ViewGroup)linearLayout_activity_control.getParent()).removeView(linearLayout_activity_control); // <- fix
                    }
                    main_notification_layout.addView(linearLayout_activity_control);
                    visb = true ;
                }

            }
        });

        btn_cart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart_act() ;
            }
        });

        btn_cart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart_act() ;
            }
        });

        btn_cart3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart_act() ;
            }
        });

        btn_cart4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart_act() ;
            }
        });

        btn_cart5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart_act() ;
            }
        });

        btn_cart6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart_act() ;
            }
        });

        btn_cart7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart_act() ;
            }
        });

        btn_info1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_more_info_link("https://fyta.de/collections/pflanzensensoren/products/single-beam");
            }
        });

        btn_info2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_more_info_link("https://fyta.de/collections/pflanzensensoren/products/3-beams-bundle-s");
            }
        });

        btn_info3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_more_info_link("https://fyta.de/collections/pflanzensensoren/products/beam-hub-bundle-m");
            }
        });

        btn_info4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_more_info_link("https://fyta.de/collections/pflanzensensoren/products/10-beams-1-hub");
            }
        });

        btn_info5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_more_info_link("https://fyta.de/collections/pflanzensensoren/products/single-hub");
            }
        });

        btn_info6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_more_info_link("https://fyta.de/collections/pflanzensensoren/products/probes");
            }
        });

        btn_info7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_more_info_link("https://fyta.de/collections/pflanzensensoren/products/ph-test-kit");
            }
        });




        ////////////////////////////////////////////////////////////////
        btn_go_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_go_library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopActivity.this, LibraryActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_go_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_go_gardens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopActivity.this, GardensActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_go_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopActivity.this, SettingsActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_go_fyta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        ///////////////////////////////////////////////////////////////







    }

    private void cart_act(){
        lottie_cart.playAnimation();
        lottie_fun.playAnimation();
        int like_count = Integer.parseInt(txtv_cart_count.getText().toString());
        like_count = like_count + 1 ;
        txtv_cart_count.setText(Integer.toString(like_count));
    }

    private void open_more_info_link(String Link){
        Intent intent = new Intent(ShopActivity.this, WebViewActivity.class);
        intent.putExtra("LINK", Link);
        startActivity(intent);
    }

}