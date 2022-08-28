package safisoft.fyta;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeActivity extends AppCompatActivity {


    ImageView btn_go_notification ;

    LottieAnimationView gifTextView_conn_status ;
    CircularImageView img_loading_pic  ;
    TextView txtv_loading_info ;

    ImageButton btn_demo1 , btn_demo2 ;
    ImageView btn_demo3 ;
    List<HomeAdsPost> Adlist;
    RecyclerView recyclerView;
    LinearLayout linearLayout_activity_control ;
    RelativeLayout main_notification_layout ;
    SwipeRefreshLayout pullToRefresh ;
    boolean visb = true ;
    String Server_URL ;
    String URL_ADPOST ;
    ImageView btn_go_home,btn_go_library,btn_go_profile,btn_go_gardens,btn_go_settings,btn_go_fyta;

    CircularImageView imgv_profile_pic_home ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        Server_URL = getResources().getString(R.string.Server_URL);
        URL_ADPOST = getResources().getString(R.string.Home_adpost_php);



        btn_go_notification = findViewById(R.id.btn_go_notification);

        gifTextView_conn_status =findViewById(R.id.gifTextView_conn_status);
        img_loading_pic = findViewById(R.id.img_loading_pic);
        txtv_loading_info = findViewById(R.id.txtv_loading_info);

        btn_demo1 = findViewById(R.id.btn_demo1);
        btn_demo2 = findViewById(R.id.btn_demo2);
        btn_demo3 = findViewById(R.id.btn_demo3);
        linearLayout_activity_control = findViewById(R.id.linearLayout_activity_control);
        main_notification_layout = findViewById(R.id.main_notification_layout);
        imgv_profile_pic_home = findViewById(R.id.imgv_profile_pic_home);

///////////////////
        btn_go_home = findViewById(R.id.btn_go_home);
        btn_go_library = findViewById(R.id.btn_go_library);
        btn_go_profile = findViewById(R.id.btn_go_profile);
        btn_go_gardens = findViewById(R.id.btn_go_gardens);
        btn_go_settings = findViewById(R.id.btn_go_settings);
        btn_go_fyta = findViewById(R.id.btn_go_fyta);
////////////////////

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setColorSchemeColors(Color.parseColor("#33FF00"));
        pullToRefresh.setProgressBackgroundColorSchemeColor(Color.parseColor("#0E563E"));

        Adlist = new ArrayList<>();

        loadadpost();

        btn_demo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnackBarInfoControl snackBarInfoControl = new SnackBarInfoControl();
                snackBarInfoControl.SnackBarInfoControlView( findViewById(android.R.id.content).getRootView(), HomeActivity.this,"Sort Post not include in this demo version.",R.raw.ic_sensor_face_sad);
            }
        });

        btn_demo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnackBarInfoControl snackBarInfoControl = new SnackBarInfoControl();
                snackBarInfoControl.SnackBarInfoControlView( findViewById(android.R.id.content).getRootView(), HomeActivity.this,"Sort Posts not include in this demo version.",R.raw.ic_sensor_face_sad);
            }
        });

        btn_demo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnackBarInfoControl snackBarInfoControl = new SnackBarInfoControl();
                snackBarInfoControl.SnackBarInfoControlView( findViewById(android.R.id.content).getRootView(), HomeActivity.this,"New Posts not include in this demo version.",R.raw.ic_sensor_face_sad);
            }
        });

        btn_go_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, NotificationActivity.class);
                startActivity(intent);
                finish();
            }
        });




        ////////////////////////////////////////////////////////////////
        btn_go_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        btn_go_library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, LibraryActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_go_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_go_gardens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, GardensActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_go_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_go_fyta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, FytaActivity.class);
                startActivity(intent);
                finish();
            }
        });
        ///////////////////////////////////////////////////////////////




        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 && visb == true) {
                    main_notification_layout.removeView(linearLayout_activity_control);
                    visb = false ;
                } else if (dy < 0 && visb == false) {
                    if(linearLayout_activity_control.getParent() != null) {
                        ((ViewGroup)linearLayout_activity_control.getParent()).removeView(linearLayout_activity_control); // <- fix
                    }
                    main_notification_layout.addView(linearLayout_activity_control);
                    visb = true ;
                }
            }
        });


        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Adlist.clear();
                loadadpost();
                pullToRefresh.setRefreshing(false);
            }
        });



    }


    private void loadadpost() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,Server_URL+ URL_ADPOST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //   recyclerView.setVisibility(View.VISIBLE);
                          // gifTextView_conn_status.setVisibility(View.GONE);


                        try {
                            JSONArray array = new JSONArray(response);

                            if(array.length() == 0) {

                                recyclerView.setVisibility(View.GONE);
                                gifTextView_conn_status.setVisibility(View.VISIBLE);
                                img_loading_pic.setVisibility(View.VISIBLE);
                                txtv_loading_info.setVisibility(View.VISIBLE);
                                txtv_loading_info.setText("Some Thing Went Wrong");


                            }
                            else {
                                //if there any data
                                recyclerView.setVisibility(View.VISIBLE);
                                gifTextView_conn_status.setVisibility(View.GONE);
                                img_loading_pic.setVisibility(View.GONE);
                                txtv_loading_info.setVisibility(View.GONE);

                                for (int i = 0; i < array.length(); i++) {

                                    JSONObject AdPostData = array.getJSONObject(array.length()-1-i);
                                    Adlist.add(new HomeAdsPost(
                                            AdPostData.getInt("_id"),
                                            AdPostData.getString("ad_desc"),
                                            AdPostData.getString("ad_gif"),
                                            AdPostData.getString("ad_name"),
                                            AdPostData.getString("ad_icon"),
                                            AdPostData.getString("ad_time_date"),
                                            AdPostData.getString("promo_code"),
                                            AdPostData.getString("promo_code_expiry_date"),
                                            AdPostData.getString("contact_details")
                                    ));
                                }
                                HomePostAdapter homePostAdapter = new HomePostAdapter(HomeActivity.this,Adlist);
                                recyclerView.setAdapter(homePostAdapter);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        if (error instanceof TimeoutError) {
                            recyclerView.setVisibility(View.GONE);
                            gifTextView_conn_status.setVisibility(View.VISIBLE);
                            img_loading_pic.setVisibility(View.VISIBLE);
                            txtv_loading_info.setVisibility(View.VISIBLE);
                            txtv_loading_info.setText("Some Thing Went Wrong");

                        }

                        else if (error instanceof ServerError) {
                            recyclerView.setVisibility(View.GONE);
                            gifTextView_conn_status.setVisibility(View.VISIBLE);
                            img_loading_pic.setVisibility(View.VISIBLE);
                            txtv_loading_info.setVisibility(View.VISIBLE);
                            txtv_loading_info.setText("Some Thing Went Wrong");

                        }

                        else if (error instanceof NetworkError) {
                            recyclerView.setVisibility(View.GONE);
                            gifTextView_conn_status.setVisibility(View.VISIBLE);
                            img_loading_pic.setVisibility(View.VISIBLE);
                            txtv_loading_info.setVisibility(View.VISIBLE);
                            txtv_loading_info.setText("Some Thing Went Wrong");

                        }

                        else if (error instanceof ParseError) {
                            recyclerView.setVisibility(View.GONE);
                            gifTextView_conn_status.setVisibility(View.VISIBLE);
                            img_loading_pic.setVisibility(View.VISIBLE);
                            txtv_loading_info.setVisibility(View.VISIBLE);
                            txtv_loading_info.setText("Some Thing Went Wrong");

                        }

                        else {
                            recyclerView.setVisibility(View.GONE);
                            gifTextView_conn_status.setVisibility(View.VISIBLE);
                            img_loading_pic.setVisibility(View.VISIBLE);
                            txtv_loading_info.setVisibility(View.VISIBLE);
                            txtv_loading_info.setText("Some Thing Went Wrong");


                        }

                    }
                });
        Volley.newRequestQueue(this).add(stringRequest);





        Glide.with(getApplicationContext())
                .load(Server_URL + getResources().getString(R.string.URL_members_pic)+"prof_9.png")
                .apply(centerCropTransform()
                        //          .placeholder(R.drawable.ic_defult_user_circ)
                        .error(R.drawable.ic_defult_user_circ)
                        .priority(Priority.HIGH))
                     .apply(RequestOptions.skipMemoryCacheOf(true))
                  .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(imgv_profile_pic_home);


    }




    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }





    @Override
    public void onResume(){
        super.onResume();

    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;


        SnackBarInfoControl snackBarInfoControl = new SnackBarInfoControl();
        snackBarInfoControl.SnackBarInfoControlView(findViewById(android.R.id.content).getRootView(), HomeActivity.this,"Please click BACK again to exit",R.raw.ic_sensor_face_sleep);


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }






}
