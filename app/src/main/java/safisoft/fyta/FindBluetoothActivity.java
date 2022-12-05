package safisoft.fyta;

import static safisoft.fyta.LiveModeActivity.REQUEST_ENABLE_BT;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;

public class FindBluetoothActivity extends AppCompatActivity {

    private BluetoothAdapter mBluetoothAdapter;
    String PLANT_NAME;
    String PLANT_PLACE;
    String PLANT_PIC;
    String PLANT_CONNECT_STATE;
    String PLANT_WATER_STATE;
    String PLANT_NUTRIENT_STATE;
    String PLANT_LIGHT_STATE;
    String PLANT_TEMP_STATE;
    String BLUETOOTH_NAME;
    String BLUETOOTH_MAC;
    TextView txtv_info, txtv_try;
    LottieAnimationView lottie_connect_face;
    ImageButton btn_connect_try;

    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_bluetooth);



        PLANT_NAME = getIntent().getStringExtra("PLANT_NAME");
        PLANT_PLACE = getIntent().getStringExtra("PLANT_PLACE");
        PLANT_PIC = getIntent().getStringExtra("PLANT_PIC");
        PLANT_CONNECT_STATE = getIntent().getStringExtra("PLANT_CONNECT_STATE");
        PLANT_WATER_STATE = getIntent().getStringExtra("PLANT_WATER_STATE");
        PLANT_NUTRIENT_STATE = getIntent().getStringExtra("PLANT_NUTRIENT_STATE");
        PLANT_LIGHT_STATE = getIntent().getStringExtra("PLANT_LIGHT_STATE");
        PLANT_TEMP_STATE = getIntent().getStringExtra("PLANT_TEMP_STATE");
        BLUETOOTH_NAME = "";
        BLUETOOTH_MAC = "";

        txtv_info = findViewById(R.id.txtv_info);
        txtv_try = findViewById(R.id.txtv_try);
        lottie_connect_face = findViewById(R.id.lottie_connect_face);
        btn_connect_try = findViewById(R.id.btn_connect_try);


        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


        if(mBluetoothAdapter != null &&!mBluetoothAdapter.isEnabled()){
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBTIntent, REQUEST_ENABLE_BT);

        }else{
            try {
                mBluetoothAdapter.startDiscovery();
                IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                registerReceiver(mReceiver, filter);
            }catch (Exception e){

                txtv_info.setText("Not Supported");
                txtv_try.setText("Try again");
                lottie_connect_face.setAnimation(R.raw.ic_sensor_face_sad);
                lottie_connect_face.playAnimation();

            }
        }


        countDownTimer = new CountDownTimer(8000, 1000
        ) {
            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {

                txtv_info.setText("Something Went Wrong Make Sure Your FLOS Sensor Is In Pairing Mode To Connect");
                txtv_try.setText("Try again");
                lottie_connect_face.setAnimation(R.raw.ic_sensor_face_sad);
                lottie_connect_face.playAnimation();

            }
        }.start();


        btn_connect_try.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    countDownTimer.cancel();
                    mBluetoothAdapter.startDiscovery();
                    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                    registerReceiver(mReceiver, filter);

                    txtv_info.setText("Connecting ...");
                    txtv_try.setText("");
                    lottie_connect_face.setAnimation(R.raw.ic_sensor_face_love_eyes_wow);
                    lottie_connect_face.playAnimation();
                    countDownTimer.start();
                }catch (Exception e){

                    txtv_info.setText("Something Went Wrong Make Sure Your FLOS Sensor Is In Pairing Mode To Connect");
                    txtv_try.setText("Try again");
                    lottie_connect_face.setAnimation(R.raw.ic_sensor_face_sad);
                    lottie_connect_face.playAnimation();

                }
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ENABLE_BT) {
            if(resultCode == Activity.RESULT_OK){
                mBluetoothAdapter.startDiscovery();
                IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                registerReceiver(mReceiver, filter);
            }
            if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }

    @Override
    protected void onDestroy() {
        //   unregisterReceiver(mReceiver);
        super.onDestroy();
    }


    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);



                if (device.getName().equals("HC05") && device.getAddress().equals("98:D3:31:F9:78:0B")) {
                    countDownTimer.cancel();
                    BLUETOOTH_NAME = device.getName();
                    BLUETOOTH_MAC = device.getAddress();
                    Intent intent_move = new Intent(FindBluetoothActivity.this, LiveModeActivity.class);
                    intent_move.putExtra("PLANT_NAME", PLANT_NAME);
                    intent_move.putExtra("PLANT_PLACE", PLANT_PLACE);
                    intent_move.putExtra("PLANT_PIC", PLANT_PIC);
                    intent_move.putExtra("PLANT_CONNECT_STATE", "true");
                    intent_move.putExtra("PLANT_WATER_STATE", PLANT_WATER_STATE);
                    intent_move.putExtra("PLANT_NUTRIENT_STATE", PLANT_NUTRIENT_STATE);
                    intent_move.putExtra("PLANT_LIGHT_STATE", PLANT_LIGHT_STATE);
                    intent_move.putExtra("PLANT_TEMP_STATE", PLANT_TEMP_STATE);
                    intent_move.putExtra("BLUETOOTH_NAME", BLUETOOTH_NAME);
                    intent_move.putExtra("BLUETOOTH_MAC", BLUETOOTH_MAC);
                    startActivity(intent_move);
                    finish();
                }



            }
        }
    };

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(FindBluetoothActivity.this, LiveModeActivity.class);
        intent.putExtra("PLANT_NAME",PLANT_NAME);
        intent.putExtra("PLANT_PLACE",PLANT_PLACE);
        intent.putExtra("PLANT_PIC",PLANT_PIC);
        intent.putExtra("PLANT_CONNECT_STATE",PLANT_CONNECT_STATE);
        intent.putExtra("PLANT_WATER_STATE",PLANT_WATER_STATE);
        intent.putExtra("PLANT_NUTRIENT_STATE",PLANT_NUTRIENT_STATE);
        intent.putExtra("PLANT_LIGHT_STATE",PLANT_LIGHT_STATE);
        intent.putExtra("PLANT_TEMP_STATE",PLANT_TEMP_STATE);
        intent.putExtra("BLUETOOTH_NAME", BLUETOOTH_NAME);
        intent.putExtra("BLUETOOTH_MAC", BLUETOOTH_MAC);
        startActivity(intent);
        finish();
    }



}