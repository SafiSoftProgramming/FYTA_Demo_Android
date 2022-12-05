package safisoft.fyta;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RequiresApi(api = Build.VERSION_CODES.S)
public class SplashActivity extends AppCompatActivity {
    private static final int REQUEST= 112;

    String[] PERMISSIONS = {
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
            //  android.Manifest.permission.ACCESS_COARSE_LOCATION,
            //  android.Manifest.permission.CHANGE_WIFI_STATE
    };



    String Server_URL ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);



        Server_URL = getResources().getString(R.string.Server_URL);

        try {
            SimpleDateFormat dates = new SimpleDateFormat("yyyy.MM.dd");
            SimpleDateFormat times = new SimpleDateFormat("HH:mm:ss a");
            String currentDate = dates.format(new Date());
            String currentTime = times.format(new Date());
            SendReceivedData_Login_Count(currentTime,currentDate,getDeviceName());
        }catch (Exception e){
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= 23) {

            if (!hasPermissions(this, PERMISSIONS[0]) &&
                    !hasPermissions(this, PERMISSIONS[1]) &&
                    !hasPermissions(this, PERMISSIONS[2]) &&
                    !hasPermissions(this, PERMISSIONS[3]) &&
                    !hasPermissions(this, PERMISSIONS[4]) &&
                    !hasPermissions(this, PERMISSIONS[5])
            )

            {

                LoginInfoDialog appPermissionsDialog = new LoginInfoDialog(SplashActivity.this);
                appPermissionsDialog.show();
                appPermissionsDialog.setCanceledOnTouchOutside(false);
                appPermissionsDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                appPermissionsDialog.btn_ok_in_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityCompat.requestPermissions(SplashActivity.this, PERMISSIONS, REQUEST);
                        appPermissionsDialog.dismiss();
                    }
                });
            }
            else {
                callActivity();
            }

        }
        else {
            callActivity();
        }



    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST: {


                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ||
                        grantResults[1] == PackageManager.PERMISSION_GRANTED ||
                        grantResults[2] == PackageManager.PERMISSION_GRANTED)
                {

                    callActivity();
                } else {
                    SnackBarInfoControl snackBarInfoControl = new SnackBarInfoControl();
                    snackBarInfoControl.SnackBarInfoControlView(findViewById(android.R.id.content).getRootView(), SplashActivity.this, "Please allow All permissions.",R.raw.ic_sensor_face_sad);
                }

            }
        }
    }


    public void callActivity() {
        new CountDownTimer(4000, 1000) {
            public void onTick(long millisUntilFinished) { }
            public void onFinish() {
                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }.start();
    }



    public void SendReceivedData_Login_Count(String time,String date,String type) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            String line ;
            @Override
            protected String doInBackground(String... params) {
                String Target_Server_URL = Server_URL+"chick_opening_count.php";
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("time", time));
                nameValuePairs.add(new BasicNameValuePair("date", date));
                nameValuePairs.add(new BasicNameValuePair("type", type));
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(Target_Server_URL);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    HttpEntity httpEntity = httpResponse.getEntity();
                    BufferedReader rd = new BufferedReader(new InputStreamReader
                            (httpResponse.getEntity().getContent()));
                } catch (ClientProtocolException e) {
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return line;
            }
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(time);
    }


    public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }


    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }





}
