package safisoft.fyta;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    String Server_URL ;


    ImageView btn_pick_img ;
    ImageButton btn_login_demo ;
    TextView txtv_member_name ;

    String URL ;
    String URL_update_profile_img ;
    String encodedImage ;





    CircularImageView imgv_profile_pic_settings ;
    ImageView btn_go_home,btn_go_library,btn_go_profile,btn_go_gardens,btn_go_settings,btn_go_fyta;

    ImageButton btn_demo_s1 , btn_demo_s2 , btn_demo_s3 , btn_demo_s4 , btn_demo_s5 , btn_demo_s6 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Server_URL = getResources().getString(R.string.Server_URL);

        URL = Server_URL+"members_profile_imgs/fyta_upload_member_profile_img_api.php";
        URL_update_profile_img = Server_URL+"fyta_update_member_profile_img_url.php";






        btn_login_demo = findViewById(R.id.btn_login_demo);
        btn_pick_img = findViewById(R.id.btn_pick_img);
        imgv_profile_pic_settings = findViewById(R.id.imgv_profile_pic_settings);
        txtv_member_name = findViewById(R.id.txtv_member_name);


        btn_demo_s1 = findViewById(R.id.btn_demo_s1);
        btn_demo_s2 = findViewById(R.id.btn_demo_s2);
        btn_demo_s3 = findViewById(R.id.btn_demo_s3);
        btn_demo_s4 = findViewById(R.id.btn_demo_s4);
        btn_demo_s5 = findViewById(R.id.btn_demo_s5);
        btn_demo_s6 = findViewById(R.id.btn_demo_s6);




///////////////////
        btn_go_home = findViewById(R.id.btn_go_home);
        btn_go_library = findViewById(R.id.btn_go_library);
        btn_go_profile = findViewById(R.id.btn_go_profile);
        btn_go_gardens = findViewById(R.id.btn_go_gardens);
        btn_go_settings = findViewById(R.id.btn_go_settings);
        btn_go_fyta = findViewById(R.id.btn_go_fyta);
////////////////////




        show_profile_img_and_name_localdatabase();


        btn_pick_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(SettingsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(SettingsActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    } else {
                        ActivityCompat.requestPermissions(SettingsActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    }
                }



    CropImage.activity()
            .setGuidelines(CropImageView.Guidelines.ON)
            .setActivityTitle("Crop Image")
            .setCropShape(CropImageView.CropShape.OVAL)
            .setAspectRatio(1,1)
            .setCropMenuCropButtonTitle("Done")
            .setRequestedSize(1000, 1000)
            .start(SettingsActivity.this);
            }
        });





        ////////////////////////////////////////////////////////////////
        btn_go_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_go_library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, LibraryActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_go_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_go_gardens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, GardensActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_go_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        btn_go_fyta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, FytaActivity.class);
                startActivity(intent);
                finish();
            }
        });
        ///////////////////////////////////////////////////////////////



        btn_demo_s1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DEMO();
            }
        });
        btn_demo_s2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DEMO();
            }
        });
        btn_demo_s3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DEMO();
            }
        });
        btn_demo_s4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DEMO();
            }
        });
        btn_demo_s5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DEMO();
            }
        });
        btn_demo_s6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DEMO();
            }
        });






         btn_login_demo.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {            
                 Intent intent = new Intent(SettingsActivity.this, SignUpActivity.class);
                 startActivity(intent);
                 fileList();

             }
         });






    }


    private void DEMO(){
        SnackBarInfoControl snackBarInfoControl = new SnackBarInfoControl();
        snackBarInfoControl.SnackBarInfoControlView( findViewById(android.R.id.content).getRootView(), SettingsActivity.this,"Sorry not include in this demo version.",R.raw.ic_sensor_face_sad);
    }



   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {

       super.onActivityResult(requestCode, resultCode, data);
       if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
           CropImage.ActivityResult result = CropImage.getActivityResult(data);
           if (resultCode == RESULT_OK) {
               ((ImageView) findViewById(R.id.imgv_profile_pic_settings)).setImageURI(result.getUri());
               String img_path = result.getUri().getPath();
               upload(img_path);
           } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
               Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
           }
       }
   }

    private void upload(String img_path) {
        // Image location URL
        Log.e("path", "----------------" + img_path);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 0;
        options.inPurgeable = true;
        Bitmap bm = BitmapFactory.decodeFile(img_path,options);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,30,baos);



        byte[] byteImage_photo = baos.toByteArray();

        encodedImage = Base64.encodeToString(byteImage_photo,Base64.DEFAULT);

        new uploadToServer().execute();
    }

    public class uploadToServer extends AsyncTask<Void, Void, String> {
        private ProgressDialog pd = new ProgressDialog(SettingsActivity.this);
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("Wait image uploading!");
            pd.show();
        }

        @Override
        protected String doInBackground(Void... params) {

            String rfid = user_rfid_local_database();
            String fytaname = fyta_name().replaceAll("\\s+","");
            String img_name =fytaname+rfid.replaceAll("\\s+","");
            img_name.replaceAll("\\s+","");


            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("base64", encodedImage));
            nameValuePairs.add(new BasicNameValuePair("ImageName", img_name + ".png"));
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(URL);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                String st = EntityUtils.toString(response.getEntity());
                Log.v("log_tag", "In the try Loop" + st);

            } catch (Exception e) {
                Log.v("log_tag", "Error in http connection " + e.toString());
            }


            String new_img_url = "members_profile_imgs/"+img_name + ".png";

            ArrayList<NameValuePair> update_memper_profile_img = new ArrayList<NameValuePair>();
            update_memper_profile_img.add(new BasicNameValuePair("fytaname", fyta_database_name()));
            update_memper_profile_img.add(new BasicNameValuePair("rfid",rfid ));
            update_memper_profile_img.add(new BasicNameValuePair("profile_img_new_url",new_img_url ));
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(URL_update_profile_img);
                httppost.setEntity(new UrlEncodedFormEntity(update_memper_profile_img));
                HttpResponse response = httpclient.execute(httppost);
                String st = EntityUtils.toString(response.getEntity());
                Log.v("log_tag", "In the try Loop" + st);



            } catch (Exception e) {
                Log.v("log_tag", "Error in http connection " + e.toString());
            }
            return "Success";
        }


        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pd.hide();
            pd.dismiss();
        }
    }

    @Override
    public void onResume(){
        super.onResume();


    }

    public String fyta_name(){
        return "prof" ;
    }

    public String fyta_database_name(){
        return "FYTA" ;
    }

    public String user_rfid_local_database(){
        return "_9" ;
    }


    public void show_profile_img_and_name_localdatabase(){


        Glide.with(getApplicationContext())
                .load(Server_URL + getResources().getString(R.string.URL_members_pic)+"prof_9.png")
                .apply(centerCropTransform()
                        //          .placeholder(R.drawable.ic_defult_user_circ)
                        .error(R.drawable.ic_defult_user_circ)
                        .priority(Priority.HIGH))
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(imgv_profile_pic_settings);
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


    @Override
    public void onBackPressed()
    {

        Intent intent = new Intent(SettingsActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();

    }




















}
