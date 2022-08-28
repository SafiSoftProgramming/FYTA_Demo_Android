package safisoft.fyta;

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
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LibraryActivity extends AppCompatActivity {

    private static LibraryActivity instance;


    LottieAnimationView gifTextView_conn_status ;
    CircularImageView img_loading_pic  ;
    TextView txtv_loading_info ;


    EditText edittext_search_library ;
    List<Library> libraryList;
    RecyclerView recyclerView;


    LinearLayout linearLayout_activity_control ;
    RelativeLayout main_notification_layout ;
    SwipeRefreshLayout pullToRefresh ;
    ImageView btn_go_home,btn_go_library,btn_go_profile,btn_go_gardens,btn_go_settings,btn_go_fyta;

    boolean visb = true ;
    String Server_URL ;
    String URL_LIBRARY ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        instance = this ;


        Server_URL = getResources().getString(R.string.Server_URL);
        URL_LIBRARY = getResources().getString(R.string.URL_library);


        gifTextView_conn_status =findViewById(R.id.gifTextView_conn_status);
        img_loading_pic = findViewById(R.id.img_loading_pic);
        txtv_loading_info = findViewById(R.id.txtv_loading_info);



        linearLayout_activity_control = findViewById(R.id.linearLayout_activity_control);
        main_notification_layout = findViewById(R.id.main_notification_layout);
        edittext_search_library = findViewById(R.id.edittext_search_library);


///////////////////
        btn_go_home = findViewById(R.id.btn_go_home);
        btn_go_library = findViewById(R.id.btn_go_library);
        btn_go_profile = findViewById(R.id.btn_go_profile);
        btn_go_gardens = findViewById(R.id.btn_go_gardens);
        btn_go_settings = findViewById(R.id.btn_go_settings);
        btn_go_fyta = findViewById(R.id.btn_go_fyta);
////////////////////


        recyclerView = findViewById(R.id.library_recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setColorSchemeColors(Color.parseColor("#33FF00"));
        pullToRefresh.setProgressBackgroundColorSchemeColor(Color.parseColor("#0E563E"));




        libraryList = new ArrayList<>();
        loadadpost();




        edittext_search_library.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SnackBarInfoControl snackBarInfoControl = new SnackBarInfoControl();
                snackBarInfoControl.SnackBarInfoControlView( findViewById(android.R.id.content).getRootView(), LibraryActivity.this,"Search not include in this demo version.",R.raw.ic_sensor_face_sleep);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });




        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 && visb == true) {
                    main_notification_layout.removeView(linearLayout_activity_control);
                    visb = false ;
                } else if (dy < 0 && visb == false) {
                    if(linearLayout_activity_control.getParent() != null) {
                        ((ViewGroup)linearLayout_activity_control.getParent()).removeView(linearLayout_activity_control);
                    }
                    main_notification_layout.addView(linearLayout_activity_control);
                    visb = true ;
                }
            }
        });


        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                libraryList.clear();
                loadadpost();
                pullToRefresh.setRefreshing(false);
            }
        });





        ////////////////////////////////////////////////////////////////
        btn_go_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LibraryActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_go_library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_go_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LibraryActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_go_gardens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LibraryActivity.this, GardensActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_go_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LibraryActivity.this, SettingsActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_go_fyta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LibraryActivity.this, FytaActivity.class);
                startActivity(intent);
                finish();
            }
        });
        ///////////////////////////////////////////////////////////////



    }


    private void loadadpost() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,Server_URL+ URL_LIBRARY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

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
                                recyclerView.setVisibility(View.VISIBLE);
                                gifTextView_conn_status.setVisibility(View.GONE);
                                img_loading_pic.setVisibility(View.GONE);
                                txtv_loading_info.setVisibility(View.GONE);

                                for (int i = 0; i < array.length(); i++) {

                                    JSONObject AdPostData = array.getJSONObject(array.length()-1-i);
                                    libraryList.add(new Library(
                                            AdPostData.getInt("_id"),
                                            AdPostData.getString("plant_name"),
                                            AdPostData.getString("plant_des"),
                                            AdPostData.getString("plant_img")
                                    ));
                                }
                                LibraryAdapter libraryAdapter = new LibraryAdapter(LibraryActivity.this, libraryList);
                                recyclerView.setAdapter(libraryAdapter);
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
        snackBarInfoControl.SnackBarInfoControlView(findViewById(android.R.id.content).getRootView(), LibraryActivity.this,"Please click BACK again to exit",R.raw.ic_sensor_face_sleep);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    public void SnackBar(String info){

        SnackBarInfoControl snackBarInfoControl = new SnackBarInfoControl();
        snackBarInfoControl.SnackBarInfoControlView( findViewById(android.R.id.content).getRootView(), LibraryActivity.this,info,R.raw.ic_sensor_face_sleep);

    }

    public static LibraryActivity getInstance() { //run from adapter 3 of 3
        return instance;
    }




}
