package safisoft.fyta;

import static com.facebook.FacebookSdk.getApplicationContext;


import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ConnectedThread extends Thread{
    private final BluetoothSocket mmSocket;
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;
    public static final int RESPONSE_MESSAGE = 10;
    Handler uih;
    InputStream tmpIn = null;
    OutputStream tmpOut = null;

    LiveModeActivity liveModeActivity = new LiveModeActivity();

    public ConnectedThread(BluetoothSocket socket, Handler uih){
        mmSocket = socket;

        this.uih = uih;
        Log.i("[THREAD-CT]","Creating thread");
        try{
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();

        } catch(IOException e) {
            Log.e("[THREAD-CT]","Error:"+ e.getMessage());
        }

        mmInStream = tmpIn;
        mmOutStream = tmpOut;

        try {
            mmOutStream.flush();
        } catch (IOException e) {
            return;
        }
        Log.i("[THREAD-CT]","IO's obtained");

    }

    public void run(){

           byte[] buffer = new byte[256];
           int bytes;


          while(true) {
            try {

                DataInputStream mmInStream = new DataInputStream(tmpIn);

                bytes = mmInStream.read(buffer);
                String readMessage = new String(buffer, 0, bytes);

                Message msg = new Message();
                msg.what = RESPONSE_MESSAGE;
                msg.obj = readMessage;
                uih.sendMessage(msg);
            } catch (Exception e) {
                Log.i("[EX]", e.getMessage());



                liveModeActivity.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Connection Lost", Toast.LENGTH_SHORT).show();
                    }
                });




                break;
            }
        }

    }

    public void write(byte[] bytes){
        try{
            Log.i("[THREAD-CT]", "Writting bytes");
            mmOutStream.write(bytes);

        }catch(IOException e){
        }


    }

    public void cancel(){
        try{
            mmSocket.close();
        }catch(IOException e){}
    }
}

