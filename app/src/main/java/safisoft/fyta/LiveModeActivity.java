package safisoft.fyta;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.UUID;

public class LiveModeActivity extends AppCompatActivity {
    String ServerURL;

    String PLANT_NAME;
    String PLANT_PLACE;
    String PLANT_PIC;
    String PLANT_CONNECT_STATE;
    String PLANT_WATER_STATE;
    String PLANT_NUTRIENT_STATE;
    String PLANT_LIGHT_STATE;
    String PLANT_TEMP_STATE;
    String PLANT_WATER_TANK;




    ////////
    String BLUETOOTH_NAME;
    String BLUETOOTH_MAC;
    String MODULE_MAC;
    public final static int REQUEST_ENABLE_BT = 1;
    UUID MY_UUID;
    BluetoothAdapter bta;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    ConnectedThread btt = null;
    //  TextView response;
    public Handler mHandler;
//////////


    ScrollView recyclerView;
    LinearLayout linearLayout_activity_control;
    RelativeLayout main_notification_layout;
    boolean visb = true;

    int tic;

    CountDownTimer CountDownTimer;

    ShapeableImageView imgv_plant_pic_live;
    TextView txtv_plan_name_live, txtv_plant_place_live;
    ImageButton btn_connect_to_beam;
    ImageButton btn_edit_plant, btn_more_info_plant, btn_ph, btn_heart;

    ImageButton btn_tank ;

    ImageView btn_go_home, btn_go_library, btn_go_profile, btn_go_gardens, btn_go_settings, btn_go_fyta;

    TextView txtv_water_data_text, txtv_light_data_text, txtv_temp_data_text, txtv_nutrient_data_text, txtv_tank_data_text;
    LottieAnimationView lott_water_data_arr, lott_light_data_arr, lott_temp_data_arr, lott_nutrient_data_arr, lott_tank_data_arr;
    ImageView imgv_water_data_bg, imgv_light_data_bg, imgv_temp_data_bg, imgv_nutrient_data_bg, imgv_tank_data_bg;

    LottieAnimationView lott_plant_state_progress_bar ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_mode);


        ServerURL = getApplicationContext().getResources().getString(R.string.Server_URL);
        PLANT_NAME = getIntent().getStringExtra("PLANT_NAME");
        PLANT_PLACE = getIntent().getStringExtra("PLANT_PLACE");
        PLANT_PIC = getIntent().getStringExtra("PLANT_PIC");
        PLANT_CONNECT_STATE = getIntent().getStringExtra("PLANT_CONNECT_STATE");
        PLANT_WATER_STATE = getIntent().getStringExtra("PLANT_WATER_STATE");
        PLANT_NUTRIENT_STATE = getIntent().getStringExtra("PLANT_NUTRIENT_STATE");
        PLANT_LIGHT_STATE = getIntent().getStringExtra("PLANT_LIGHT_STATE");
        PLANT_TEMP_STATE = getIntent().getStringExtra("PLANT_TEMP_STATE");
        PLANT_WATER_TANK = "0";

        BLUETOOTH_NAME = getIntent().getStringExtra("BLUETOOTH_NAME");
        BLUETOOTH_MAC = getIntent().getStringExtra("BLUETOOTH_MAC");

        if (BLUETOOTH_NAME == null) {
            BLUETOOTH_NAME = "";
        }


///////////////////
        btn_go_home = findViewById(R.id.btn_go_home);
        btn_go_library = findViewById(R.id.btn_go_library);
        btn_go_profile = findViewById(R.id.btn_go_profile);
        btn_go_gardens = findViewById(R.id.btn_go_gardens);
        btn_go_settings = findViewById(R.id.btn_go_settings);
        btn_go_fyta = findViewById(R.id.btn_go_fyta);
////////////////////


        btn_tank = findViewById(R.id.btn_tank);

        recyclerView = findViewById(R.id.recyclerView);
        linearLayout_activity_control = (LinearLayout) findViewById(R.id.linearLayout_activity_control);
        main_notification_layout = (RelativeLayout) findViewById(R.id.main_notification_layout);

        imgv_plant_pic_live = findViewById(R.id.imgv_plant_pic_live);
        txtv_plan_name_live = findViewById(R.id.txtv_plan_name_live);
        txtv_plant_place_live = findViewById(R.id.txtv_plant_place_live);
        btn_connect_to_beam = findViewById(R.id.btn_connect_to_beam);

        btn_edit_plant = findViewById(R.id.btn_edit_plant);
        btn_more_info_plant = findViewById(R.id.btn_more_info_plant);
        btn_ph = findViewById(R.id.btn_ph);
        btn_heart = findViewById(R.id.btn_heart);

        txtv_water_data_text = findViewById(R.id.txtv_water_data_text);
        txtv_light_data_text = findViewById(R.id.txtv_light_data_text);
        txtv_temp_data_text = findViewById(R.id.txtv_temp_data_text);
        txtv_nutrient_data_text = findViewById(R.id.txtv_nutrient_data_text);
        txtv_tank_data_text = findViewById(R.id.txtv_tank_data_text);

        lott_water_data_arr = findViewById(R.id.lott_water_data_arr);
        lott_light_data_arr = findViewById(R.id.lott_light_data_arr);
        lott_temp_data_arr = findViewById(R.id.lott_temp_data_arr);
        lott_nutrient_data_arr = findViewById(R.id.lott_nutrient_data_arr);
        lott_tank_data_arr = findViewById(R.id.lott_tank_data_arr);

        imgv_water_data_bg = findViewById(R.id.imgv_water_data_bg);
        imgv_light_data_bg = findViewById(R.id.imgv_light_data_bg);
        imgv_temp_data_bg = findViewById(R.id.imgv_temp_data_bg);
        imgv_nutrient_data_bg = findViewById(R.id.imgv_nutrient_data_bg);
        imgv_tank_data_bg = findViewById(R.id.imgv_tank_data_bg);

        lott_plant_state_progress_bar = findViewById(R.id.lott_plant_state_progress_bar);


        ////////////////////////////////////////////////////////////////
        btn_go_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetConnection();
                Intent intent = new Intent(LiveModeActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_go_library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetConnection();
                Intent intent = new Intent(LiveModeActivity.this, LibraryActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_go_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetConnection();
                Intent intent = new Intent(LiveModeActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_go_gardens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetConnection();
                Intent intent = new Intent(LiveModeActivity.this, GardensActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_go_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetConnection();
                Intent intent = new Intent(LiveModeActivity.this, SettingsActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_go_fyta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetConnection();
                Intent intent = new Intent(LiveModeActivity.this, FytaActivity.class);
                startActivity(intent);
                finish();
            }
        });
        ///////////////////////////////////////////////////////////////


        Glide.with(getApplicationContext())
                .load(ServerURL + PLANT_PIC)
                .apply(centerCropTransform()
                        //          .placeholder(R.drawable.ic_defult_user_circ)
                        .error(R.drawable.ic_defult_user_circ)
                        .priority(Priority.HIGH))
                //     .apply(RequestOptions.skipMemoryCacheOf(true))
                //  .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(imgv_plant_pic_live);

        txtv_plan_name_live.setText(PLANT_NAME);
        txtv_plant_place_live.setText(PLANT_PLACE);


        if (PLANT_CONNECT_STATE.equals("true")) {
            btn_connect_to_beam.setBackgroundResource(R.drawable.ic_fyta_sensor_online_icon_live);
        } else if (PLANT_CONNECT_STATE.equals("false")) {
            btn_connect_to_beam.setBackgroundResource(R.drawable.ic_fyta_sensor_offline_icon_live);
        }


        SHOW_DATA(PLANT_WATER_STATE, lott_water_data_arr, imgv_water_data_bg, txtv_water_data_text);
        SHOW_DATA(PLANT_LIGHT_STATE, lott_light_data_arr, imgv_light_data_bg, txtv_light_data_text);
        SHOW_DATA(PLANT_TEMP_STATE, lott_temp_data_arr, imgv_temp_data_bg, txtv_temp_data_text);
        SHOW_DATA(PLANT_NUTRIENT_STATE, lott_nutrient_data_arr, imgv_nutrient_data_bg, txtv_nutrient_data_text);
        SHOW_DATA(PLANT_WATER_TANK, lott_tank_data_arr, imgv_tank_data_bg, txtv_tank_data_text);


        btn_edit_plant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnackBarInfoControl snackBarInfoControl = new SnackBarInfoControl();
                snackBarInfoControl.SnackBarInfoControlView(findViewById(android.R.id.content).getRootView(), LiveModeActivity.this, "Edit plant not include in this demo version.", R.raw.ic_sensor_face_sad);
            }
        });
        btn_more_info_plant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnackBarInfoControl snackBarInfoControl = new SnackBarInfoControl();
                snackBarInfoControl.SnackBarInfoControlView(findViewById(android.R.id.content).getRootView(), LiveModeActivity.this, "plant info not include in this demo version.", R.raw.ic_sensor_face_sad);
            }
        });
        btn_ph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnackBarInfoControl snackBarInfoControl = new SnackBarInfoControl();
                snackBarInfoControl.SnackBarInfoControlView(findViewById(android.R.id.content).getRootView(), LiveModeActivity.this, "PH not include in this demo version.", R.raw.ic_sensor_face_sad);
            }
        });
        btn_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnackBarInfoControl snackBarInfoControl = new SnackBarInfoControl();
                snackBarInfoControl.SnackBarInfoControlView(findViewById(android.R.id.content).getRootView(), LiveModeActivity.this, "plant health not include in this demo version.", R.raw.ic_sensor_face_sad);
            }
        });

        btn_connect_to_beam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetConnection();
                Intent intent = new Intent(LiveModeActivity.this, FindBluetoothActivity.class);
                intent.putExtra("PLANT_NAME", PLANT_NAME);
                intent.putExtra("PLANT_PLACE", PLANT_PLACE);
                intent.putExtra("PLANT_PIC", PLANT_PIC);
                intent.putExtra("PLANT_CONNECT_STATE", PLANT_CONNECT_STATE);
                intent.putExtra("PLANT_WATER_STATE", PLANT_WATER_STATE);
                intent.putExtra("PLANT_NUTRIENT_STATE", PLANT_NUTRIENT_STATE);
                intent.putExtra("PLANT_LIGHT_STATE", PLANT_LIGHT_STATE);
                intent.putExtra("PLANT_TEMP_STATE", PLANT_TEMP_STATE);
                startActivity(intent);
                finish();
            }
        });


        btn_tank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SEND_COMMAND("W");
            }
        });


        /////////////////////////////////

        MODULE_MAC = BLUETOOTH_MAC;
        MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
        bta = BluetoothAdapter.getDefaultAdapter();


        //if bluetooth is not enabled then create Intent for user to turn it on
        if (bta != null && !bta.isEnabled()) {
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            startActivityForResult(enableBTIntent, REQUEST_ENABLE_BT);
        } else {
            if (!BLUETOOTH_NAME.equals("")) {
                initiateBluetoothProcess();
            }
        }
        //////////////////////////////////


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (!BLUETOOTH_NAME.equals("") && resultCode == RESULT_OK && requestCode == REQUEST_ENABLE_BT) {
            initiateBluetoothProcess();
        }
    }


    public void initiateBluetoothProcess() {

        if (bta.isEnabled()) {
            //attempt to connect to bluetooth module
            BluetoothSocket tmp = null;
            mmDevice = bta.getRemoteDevice(MODULE_MAC);

            //create socket
            try {
                tmp = mmDevice.createRfcommSocketToServiceRecord(MY_UUID);
                mmSocket = tmp;
                mmSocket.connect();
                Log.i("[BLUETOOTH]", "Connected to: " + mmDevice.getName());
            } catch (IOException e) {
                try {
                    mmSocket.close();
                } catch (IOException c) {
                    return;
                }
            }

            Log.i("[BLUETOOTH]", "Creating handler");
            mHandler = new Handler(Looper.getMainLooper()) {


                @Override
                public void handleMessage(Message msg) {

                    super.handleMessage(msg);
                    if (msg.what == ConnectedThread.RESPONSE_MESSAGE) {
                        String txt = (String) msg.obj;

                        ArrayList<String> strings = null;
                        StringBuilder sb = new StringBuilder();
                        sb.append(txt);                                      // append string
                        String sbprint = sb.substring(0, sb.length());            // extract string
                        sb.delete(0, sb.length());
                        final String finalSbprint = sb.append(sbprint).toString();

                        GET_VAL_FROM_DTRING(finalSbprint);

                    }

                }
            };

            Log.i("[BLUETOOTH]", "Creating and running Thread");
            btt = new ConnectedThread(mmSocket, mHandler);
            btt.start();


        }
    }

    private void resetConnection() {
        if (mmSocket != null) {
            try {
                mmSocket.close();
            } catch (Exception e) {

                Toast.makeText(this, "dfdsf", Toast.LENGTH_SHORT).show();
            }
            mmSocket = null;

        }
    }

    public static boolean isConnected(BluetoothDevice device) {
        try {
            Method m = device.getClass().getMethod("isConnected", (Class[]) null);
            boolean connected = (boolean) m.invoke(device, (Object[]) null);
            return connected;
        } catch (Exception e) {
            throw new IllegalStateException(e);

        }
    }


    @Override
    public void onBackPressed() {
        resetConnection();
        Intent intent = new Intent(LiveModeActivity.this, GardensActivity.class);
        startActivity(intent);
        finish();
    }

    int Soil_moisture_prog = 0 , Light_prog = 0 ,Temp_prog = 0 , Nutrient_prog = 0 ;
    // we have value from 0 to 100
    private void SHOW_DATA(String State, LottieAnimationView LottieAnimationView, ImageView imageView, TextView textView) {




        textView.setText("");
        int State_Int = Integer.parseInt(State.trim());


        if (State.equals("0") && PLANT_CONNECT_STATE.equals("false")) {
            if (textView.getId() == txtv_tank_data_text.getId()) {
                imageView.setBackgroundResource(R.drawable.ic_garden_list_state_bg_gray_big_tank);
            } else {
                imageView.setBackgroundResource(R.drawable.ic_garden_list_state_bg_gray_big);
            }
            textView.setText("No Data");
            lott_plant_state_progress_bar.setFrame(8);

        } else {

            ////////////////////////////////////soil_moisture
            if (textView.getId() == txtv_water_data_text.getId()) {
                if(State_Int > 1000){
                    imageView.setBackgroundResource(R.drawable.ic_garden_list_state_bg_red_big);
                    textView.setText("0 %");
                    LottieAnimationView.setFrame(0);
                    Soil_moisture_prog = 0 ;
                }
                else if(State_Int < 900){
                    State_Int = State_Int / 8 ;
                    State_Int = State_Int - 100 ;

                    textView.setText(Math.abs(State_Int) + " %");
                    LottieAnimationView.setFrame(Math.abs(State_Int)+25);

                    if(Math.abs(State_Int) < 25){
                        imageView.setBackgroundResource(R.drawable.ic_garden_list_state_bg_red_big);
                        Soil_moisture_prog = 0 ;
                    }
                    else if (Math.abs(State_Int) > 25){
                        imageView.setBackgroundResource(R.drawable.ic_garden_list_state_bg_green_big);
                        Soil_moisture_prog = 25 ;
                    }

                }
            }///////////////////////////////////////////


            ////////////////////////////////////Light
            if (textView.getId() == txtv_light_data_text.getId()) {
                    State_Int = State_Int / 15 ;
                    textView.setText(State_Int + " %");
                    LottieAnimationView.setFrame(State_Int+25);
                    if(State_Int < 25){
                        imageView.setBackgroundResource(R.drawable.ic_garden_list_state_bg_red_big);
                       Light_prog = 0 ;
                    }
                    else if (State_Int > 25){
                        imageView.setBackgroundResource(R.drawable.ic_garden_list_state_bg_green_big);
                        Light_prog = 25 ;
                    }
            }///////////////////////////////////////////


            ////////////////////////////////////Temp
            if (textView.getId() == txtv_temp_data_text.getId()) {
                State_Int = State_Int - 10 ;
                textView.setText(State_Int + " ℃");
                LottieAnimationView.setFrame(State_Int+25);
                if(State_Int <= 25){
                    imageView.setBackgroundResource(R.drawable.ic_garden_list_state_bg_red_big);
                   Temp_prog = 0 ;
                }
                else if (State_Int >= 26 && State_Int <= 31){
                    imageView.setBackgroundResource(R.drawable.ic_garden_list_state_bg_green_big);
                    Temp_prog = 25 ;
                }
                else if (State_Int > 31){
                    imageView.setBackgroundResource(R.drawable.ic_garden_list_state_bg_red_big);
                    Temp_prog = 0 ;
                }
            }///////////////////////////////////////////


            ////////////////////////////////////Nutrient
            if (textView.getId() == txtv_nutrient_data_text.getId()) {
                if(State_Int > 1000){
                    imageView.setBackgroundResource(R.drawable.ic_garden_list_state_bg_red_big);
                    textView.setText("0 %");
                    LottieAnimationView.setFrame(0);
                    Nutrient_prog = 0 ;
                }
                else if(State_Int < 900){
                    State_Int = State_Int / 8 ;
                    State_Int = State_Int - 90 ;

                    textView.setText(Math.abs(State_Int) + " %");
                    LottieAnimationView.setFrame(Math.abs(State_Int)+25);

                    if(Math.abs(State_Int) < 25){
                        imageView.setBackgroundResource(R.drawable.ic_garden_list_state_bg_red_big);
                        Nutrient_prog = 0 ;
                    }
                    else if (Math.abs(State_Int) > 25){
                        imageView.setBackgroundResource(R.drawable.ic_garden_list_state_bg_green_big);
                        Nutrient_prog = 25 ;
                    }
                }
            }///////////////////////////////////////////

            ////////////////////////////////////Tank
            if (textView.getId() == txtv_tank_data_text.getId()) {
              //  State_Int = State_Int / 15 ;

                if(State_Int >= 0 && State_Int <= 9 ) {
                    textView.setText("2 %");
                    LottieAnimationView.setFrame(0);
                    imageView.setBackgroundResource(R.drawable.ic_garden_list_state_bg_red_big_tank);
                }
                else if(State_Int >= 10 && State_Int <= 220 ) {
                    textView.setText("25 %");
                    LottieAnimationView.setFrame(25+25);
                    imageView.setBackgroundResource(R.drawable.ic_garden_list_state_bg_red_big_tank);
                }
                else if(State_Int >= 221 && State_Int <= 400 ) {
                    textView.setText("50 %");
                    LottieAnimationView.setFrame(50+25);
                    imageView.setBackgroundResource(R.drawable.ic_garden_list_state_bg_green_big_tank);
                }
                else if(State_Int >= 401 && State_Int < 420 ) {
                    textView.setText("75 %");
                    LottieAnimationView.setFrame(75+25);
                    imageView.setBackgroundResource(R.drawable.ic_garden_list_state_bg_green_big_tank);
                }
                else if(State_Int >= 421  ) {
                    textView.setText("100 %");
                    LottieAnimationView.setFrame(100+25);
                    imageView.setBackgroundResource(R.drawable.ic_garden_list_state_bg_green_big_tank);
                }

            }///////////////////////////////////////////

            lott_plant_state_progress_bar.setFrame(Soil_moisture_prog + Light_prog + Temp_prog + Nutrient_prog+10);



        }

    }





    String prev = null;
    private void GET_VAL_FROM_DTRING(String String) {
              String[] Final_Val;
        try {
            String[] msgs = String.split("\n");
            int msgsCount = msgs.length;
            if (msgsCount > 0) {
                if (prev != null)
                    msgs[0] = prev + msgs[0]; //add it to first message
                if (String.endsWith("\n"))
                    prev = null;
                else {
                    prev = msgs[msgsCount - 1];
                    msgsCount--; }
                for (int i = 0; i < msgsCount; i++) {


                    Final_Val = msgs[i].split(",");

                      SHOW_DATA( Final_Val[0], lott_water_data_arr, imgv_water_data_bg, txtv_water_data_text);
                       SHOW_DATA(Final_Val[1], lott_light_data_arr, imgv_light_data_bg, txtv_light_data_text);
                       SHOW_DATA(Final_Val[2], lott_temp_data_arr, imgv_temp_data_bg, txtv_temp_data_text);
                      SHOW_DATA(Final_Val[0], lott_nutrient_data_arr, imgv_nutrient_data_bg, txtv_nutrient_data_text);
                     SHOW_DATA(Final_Val[3], lott_tank_data_arr, imgv_tank_data_bg, txtv_tank_data_text);

                     System.out.println(Final_Val[3]);


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void SEND_COMMAND(String Commend){

        try {
            if(!isConnected(mmDevice)){
                SnackBarInfoControl snackBarInfoControl = new SnackBarInfoControl();
                snackBarInfoControl.SnackBarInfoControlView(findViewById(android.R.id.content).getRootView(), LiveModeActivity.this,"Bluetooth Connection Lost!", R.raw.ic_sensor_face_sad);
            }
            if (mmSocket.isConnected() && btt != null) {
                btt.write(Commend.getBytes());
            } else {
                SnackBarInfoControl snackBarInfoControl = new SnackBarInfoControl();
                snackBarInfoControl.SnackBarInfoControlView( findViewById(android.R.id.content).getRootView(), LiveModeActivity.this,"Something Went Wrong", R.raw.ic_sensor_face_sad);
            }
        }
        catch (Exception E){
            SnackBarInfoControl snackBarInfoControl = new SnackBarInfoControl();
            snackBarInfoControl.SnackBarInfoControlView( findViewById(android.R.id.content).getRootView(), LiveModeActivity.this,"Something Went Wrong", R.raw.ic_sensor_face_sad);
        }


    }






    }