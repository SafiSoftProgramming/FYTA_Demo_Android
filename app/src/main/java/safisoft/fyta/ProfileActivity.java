package safisoft.fyta;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
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
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    String URL_MEMBER_PROFILE;


    LottieAnimationView gifTextView_conn_status ;
    CircularImageView img_loading_pic  ;
    TextView txtv_loading_info ;

    List<Profile> ProfileList;
    RecyclerView recyclerView;

    String Server_URL ;


    LinearLayout linearLayout_activity_control ;
    RelativeLayout main_notification_layout ;

    SwipeRefreshLayout pullToRefresh ;

    boolean visb = true ;
    boolean response = false ;

    ImageView btn_go_home,btn_go_library,btn_go_profile,btn_go_gardens,btn_go_settings,btn_go_fyta;


    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);





        Server_URL = getResources().getString(R.string.Server_URL);
        URL_MEMBER_PROFILE = getResources().getString(R.string.URL_member_profile);
        ProfileList = new ArrayList<>();



///////////////////
        btn_go_home = findViewById(R.id.btn_go_home);
        btn_go_library = findViewById(R.id.btn_go_library);
        btn_go_profile = findViewById(R.id.btn_go_profile);
        btn_go_gardens = findViewById(R.id.btn_go_gardens);
        btn_go_settings = findViewById(R.id.btn_go_settings);
        btn_go_fyta = findViewById(R.id.btn_go_fyta);
////////////////////

        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setColorSchemeColors(Color.parseColor("#33FF00"));
        pullToRefresh.setProgressBackgroundColorSchemeColor(Color.parseColor("#0E563E"));

        SendReceivedData_notification(Server_URL, URL_MEMBER_PROFILE, fyta_database_url());

        gifTextView_conn_status =findViewById(R.id.gifTextView_conn_status);
        img_loading_pic = findViewById(R.id.img_loading_pic);
        txtv_loading_info = findViewById(R.id.txtv_loading_info);

        linearLayout_activity_control = findViewById(R.id.linearLayout_activity_control);
        main_notification_layout = findViewById(R.id.main_notification_layout);

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

        no_internet();

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ProfileList.clear();
                SendReceivedData_notification(Server_URL, URL_MEMBER_PROFILE, fyta_database_url());
                pullToRefresh.setRefreshing(false);
            }
        });

        ////////////////////////////////////////////////////////////////
        btn_go_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_go_library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, LibraryActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_go_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        btn_go_gardens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, GardensActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_go_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, SettingsActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_go_fyta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, FytaActivity.class);
                startActivity(intent);
                finish();
            }
        });
        ///////////////////////////////////////////////////////////////




    }


    public void SendReceivedData_notification(final String Server_url,final String php_Search_member_url,final String fyta_name) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            String line ;
            @Override
            protected String doInBackground(String... params) {
                String Server_URL = Server_url+php_Search_member_url;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("fytaname", fyta_name));
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(Server_URL);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    HttpEntity httpEntity = httpResponse.getEntity();
                    BufferedReader rd = new BufferedReader(new InputStreamReader
                            (httpResponse.getEntity().getContent()));
                    line = "";
                    line = rd.readLine();

                    if(!line.equals("")) {
                        response = true;
                    }

                } catch (ClientProtocolException e) {
                } catch (IOException e) {
                }
                return line;
            }
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
        try {

            if(result.equals("[]")){
                    try {
                        clearApplicationData();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        getApplicationContext().startActivity(intent);
                        getApplicationContext().fileList();
                    }
                    catch (Exception e){
                    }
                }
}catch (Exception e){
    gifTextView_conn_status.setVisibility(View.VISIBLE);
    img_loading_pic.setVisibility(View.VISIBLE);
    txtv_loading_info.setVisibility(View.VISIBLE);
    txtv_loading_info.setText("Some Thing Went Wrong");

}

                if (result != null) {

                    try {
                        JSONArray array = new JSONArray(result);

                        if (array.length() == 0) {
                            recyclerView.setVisibility(View.GONE);
                            gifTextView_conn_status.setVisibility(View.VISIBLE);
                            img_loading_pic.setVisibility(View.VISIBLE);
                            txtv_loading_info.setVisibility(View.VISIBLE);
                            txtv_loading_info.setText("Some Thing Went Wrong");
                        } else {

                            recyclerView.setVisibility(View.VISIBLE);
                            gifTextView_conn_status.setVisibility(View.GONE);
                            img_loading_pic.setVisibility(View.GONE);
                            txtv_loading_info.setVisibility(View.GONE);

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject MemberseData = array.getJSONObject(array.length() - 1 - i);
                                ProfileList.add(new Profile(
                                        MemberseData.getInt("_id"),
                                        MemberseData.getString("user_name"),
                                        MemberseData.getString("user_rank"),
                                        MemberseData.getString("member_date"),
                                        MemberseData.getString("location"),
                                        MemberseData.getString("plant_count"),
                                        MemberseData.getString("beams_count"),
                                        MemberseData.getString("profile_pic"),
                                        MemberseData.getString("rank_icon")
                                ));
                            }
                            ProfileAdapter adapter = new ProfileAdapter(ProfileActivity.this, ProfileList);
                            recyclerView.setAdapter(adapter);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(fyta_name);
    }



    public String fyta_database_url(){
        return "FYTA"    ;
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


    public void no_internet(){
        int min = 2*6000;
        new CountDownTimer(min, 1000
        ) {
            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
                if(!response){
                    gifTextView_conn_status.setVisibility(View.VISIBLE);
                    img_loading_pic.setVisibility(View.VISIBLE);
                    txtv_loading_info.setVisibility(View.VISIBLE);
                    txtv_loading_info.setText("Some Thing Went Wrong");
                }
            }
        }.start();
    }


    @Override
    public void onBackPressed()
    {

        Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();

    }

    public void clearApplicationData() {
        File cacheDirectory = getCacheDir();
        File applicationDirectory = new File(cacheDirectory.getParent());
        if (applicationDirectory.exists()) {
            String[] fileNames = applicationDirectory.list();
            for (String fileName : fileNames) {
                if (!fileName.equals("lib")) {
                    deleteFile(new File(applicationDirectory, fileName));
                }
            }
        }
    }

    public static boolean deleteFile(File file) {
        boolean deletedAll = true;
        if (file != null) {
            if (file.isDirectory()) {
                String[] children = file.list();
                for (int i = 0; i < children.length; i++) {
                    deletedAll = deleteFile(new File(file, children[i])) && deletedAll;
                }
            } else {
                deletedAll = file.delete();
            }
        }

        return deletedAll;
    }



}