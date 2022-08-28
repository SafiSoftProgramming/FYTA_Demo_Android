package safisoft.fyta;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;
import static com.facebook.FacebookSdk.getApplicationContext;
import static com.facebook.FacebookSdk.getCacheDir;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.telephony.ims.ImsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static safisoft.fyta.SettingsActivity.deleteFile;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.imageview.ShapeableImageView;
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
import java.util.UUID;


public class GardensAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    String ServerURL ;
    String Notification_Icon_Type ;
    String URL_SEARCH_MEMBER ;
    private Context mCtx;
    private List<Gardens> GardensList;



    class HeaderViewMessagesHolder extends RecyclerView.ViewHolder {

        CircularImageView imgv_profile_pic_garden_head ;
        TextView txtv_member_name_garden_head , txtv_plant_count_garden_head , txtv_beams_count_garden_head ;
        LottieAnimationView lott_plant_state_progress_bar ;

        public HeaderViewMessagesHolder(View itemView) {
            super(itemView);

            imgv_profile_pic_garden_head = itemView.findViewById(R.id.imgv_profile_pic_garden_head);
            txtv_member_name_garden_head = itemView.findViewById(R.id.txtv_member_name_garden_head);
            txtv_plant_count_garden_head = itemView.findViewById(R.id.txtv_plant_count_garden_head);
            txtv_beams_count_garden_head = itemView.findViewById(R.id.txtv_beams_count_garden_head);
            lott_plant_state_progress_bar = itemView.findViewById(R.id.lott_plant_state_progress_bar);

        }
    }



    class GardensViewHolder extends RecyclerView.ViewHolder {

        ShapeableImageView imgv_plant_pic ;
        ImageView imgv_connect_state ;
        TextView txtv_plant_name , txtv_plant_place ;
        ImageView imgv_water_state , imgv_nutrient_state , imgv_light_state , imgv_temp_state ;
        ImageButton btn_the_list ;


        public GardensViewHolder(View itemView) {
            super(itemView);


            imgv_plant_pic = itemView.findViewById(R.id.imgv_plant_pic);
            imgv_connect_state = itemView.findViewById(R.id.imgv_connect_state);
            txtv_plant_name = itemView.findViewById(R.id.txtv_plant_name);
            txtv_plant_place = itemView.findViewById(R.id.txtv_plant_place);
            imgv_water_state = itemView.findViewById(R.id.imgv_water_state);
            imgv_nutrient_state = itemView.findViewById(R.id.imgv_nutrient_state);
            imgv_light_state = itemView.findViewById(R.id.imgv_light_state);
            imgv_temp_state = itemView.findViewById(R.id.imgv_temp_state);
            btn_the_list = itemView.findViewById(R.id.btn_the_list);

        }
    }






    public GardensAdapter(Context mCtx, List<Gardens>GardensList ) {
        this.mCtx = mCtx;
        this.GardensList = GardensList ;
    }



    @Override
    public int getItemViewType(int position) {

        switch (position) {
            case 0:
                return 0;
            case 1:
                return 1;
            default:
                return -1;
        }


    }





    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        if(viewType == 0){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gardens_header, parent, false);
            return new HeaderViewMessagesHolder(view);
        }
        else {
         view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gardens_list, parent, false);
         return new GardensViewHolder(view);
        }


    }

    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, int position) {
            ServerURL = mCtx.getResources().getString(R.string.Server_URL);
          //  Notification_Icon_Folder_Name = mCtx.getResources().getString(R.string.);
            Notification_Icon_Type = mCtx.getResources().getString(R.string.Notification_Icon_Type);
            URL_SEARCH_MEMBER = mCtx.getResources().getString(R.string.Member_search_php);

        if(position == 0){

            class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
                String line ;
                @Override
                protected String doInBackground(String... params) {
                    String Server_URL_link = ServerURL+URL_SEARCH_MEMBER;
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                    nameValuePairs.add(new BasicNameValuePair("rfid", "2"));
                    nameValuePairs.add(new BasicNameValuePair("fytaname", "FYTA"));
                    try {
                        HttpClient httpClient = new DefaultHttpClient();
                        HttpPost httpPost = new HttpPost(Server_URL_link);
                        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                        HttpResponse httpResponse = httpClient.execute(httpPost);
                        HttpEntity httpEntity = httpResponse.getEntity();
                        BufferedReader rd = new BufferedReader(new InputStreamReader
                                (httpResponse.getEntity().getContent()));
                        line = "";
                        line = rd.readLine();
                    } catch (ClientProtocolException e) {
                    } catch (IOException e) {
                    }
                    return line;
                }
                @Override
                protected void onPostExecute(String result) {
                    super.onPostExecute(result);
///////////////////
                    if(result.equals("[]")){
                        try {
                            clearApplicationData();
                            Intent intent = new Intent(mCtx, GardensAdapter.class);
                            mCtx.startActivity(intent);
                            mCtx.fileList(); }
                        catch (Exception e){ } }
                    if (result != null) {
                        JSONArray jsonarray = null;
                        try {
                            jsonarray = new JSONArray(result);
                            JSONObject obj = jsonarray.getJSONObject(0);
                            ((HeaderViewMessagesHolder) holder).txtv_member_name_garden_head.setText(obj.getString("user_name"));
                            ((HeaderViewMessagesHolder) holder).txtv_plant_count_garden_head.setText(obj.getString("plant_count"));
                            ((HeaderViewMessagesHolder) holder).txtv_beams_count_garden_head.setText(obj.getString("beams_count"));
                            ((HeaderViewMessagesHolder) holder).lott_plant_state_progress_bar.setFrame(85);

                            Glide.with(getApplicationContext())
                                    .load(ServerURL+mCtx.getResources().getString(R.string.URL_members_pic)+obj.get("profile_pic"))
                                    .apply(centerCropTransform()
                                  //          .placeholder(R.drawable.ic_defult_user_circ)
                                            .error(R.drawable.ic_defult_user_circ)
                                            .priority(Priority.HIGH))
                                    .apply(RequestOptions.skipMemoryCacheOf(true))
                                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                                    .into(((HeaderViewMessagesHolder) holder).imgv_profile_pic_garden_head);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
            sendPostReqAsyncTask.execute("2");

        }
        else {

            position = position - 1;
            Gardens gardens = GardensList.get(position);

            Glide.with(mCtx)
                    .load(ServerURL + gardens.getPlant_pic() )
                    .apply(centerCropTransform()
               //             .placeholder(R.drawable.ic_logo)
                            .error(R.drawable.ic_logo)
                            .priority(Priority.HIGH))
                    .apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .into(((GardensViewHolder) holder).imgv_plant_pic);


              ((GardensViewHolder) holder).txtv_plant_name.setText(gardens.getPlant_name());
              ((GardensViewHolder) holder).txtv_plant_place.setText(gardens.getPlant_place());

              String Connection_State = gardens.getConnect_state();
              if(Connection_State.equals("true")){
                  ((GardensViewHolder) holder).imgv_connect_state.setBackgroundResource(R.drawable.ic_fyta_sensor_online_icon);
              }
              else if (Connection_State.equals("false")){
                  ((GardensViewHolder) holder).imgv_connect_state.setBackgroundResource(R.drawable.ic_fyta_sensor_offline_icon);
              }

              int Water_State = Integer.parseInt(gardens.getWater_state());
              if (Water_State == 0 ){
                  ((GardensViewHolder) holder).imgv_water_state.setBackgroundResource(R.drawable.ic_garden_list_state_bg_gray);
              }
              else if(Water_State > 0 && Water_State < 25 ){
                  ((GardensViewHolder) holder).imgv_water_state.setBackgroundResource(R.drawable.ic_garden_list_state_bg_red);
              }
              else if(Water_State > 25 ){
                  ((GardensViewHolder) holder).imgv_water_state.setBackgroundResource(R.drawable.ic_garden_list_state_bg_green);
              }

            int Nutrient_State = Integer.parseInt(gardens.getNutrient_state());
            if (Nutrient_State == 0 ){
                ((GardensViewHolder) holder).imgv_nutrient_state.setBackgroundResource(R.drawable.ic_garden_list_state_bg_gray);
            }
            else if(Nutrient_State > 0 && Nutrient_State < 25 ){
                ((GardensViewHolder) holder).imgv_nutrient_state.setBackgroundResource(R.drawable.ic_garden_list_state_bg_red);
            }
            else if(Nutrient_State > 25 ){
                ((GardensViewHolder) holder).imgv_nutrient_state.setBackgroundResource(R.drawable.ic_garden_list_state_bg_green);
            }

            int Light_State = Integer.parseInt(gardens.getLight_state());
            if (Light_State == 0 ){
                ((GardensViewHolder) holder).imgv_light_state.setBackgroundResource(R.drawable.ic_garden_list_state_bg_gray);
            }
            else if(Light_State > 0 && Light_State < 25 ){
                ((GardensViewHolder) holder).imgv_light_state.setBackgroundResource(R.drawable.ic_garden_list_state_bg_red);
            }
            else if(Light_State > 25 ){
                ((GardensViewHolder) holder).imgv_light_state.setBackgroundResource(R.drawable.ic_garden_list_state_bg_green);
            }

            int Temp_State = Integer.parseInt(gardens.getTemp_state());
            if (Temp_State == 0 ){
                ((GardensViewHolder) holder).imgv_temp_state.setBackgroundResource(R.drawable.ic_garden_list_state_bg_gray);
            }
            else if(Temp_State > 0 && Temp_State < 25 ){
                ((GardensViewHolder) holder).imgv_temp_state.setBackgroundResource(R.drawable.ic_garden_list_state_bg_red);
            }
            else if(Temp_State > 25 ){
                ((GardensViewHolder) holder).imgv_temp_state.setBackgroundResource(R.drawable.ic_garden_list_state_bg_green);
            }

            ((GardensViewHolder) holder).btn_the_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), LiveModeActivity.class);
                    intent.putExtra("PLANT_NAME",gardens.getPlant_name());
                    intent.putExtra("PLANT_PLACE",gardens.getPlant_place());
                    intent.putExtra("PLANT_PIC",gardens.getPlant_pic());
                    intent.putExtra("PLANT_CONNECT_STATE",gardens.getConnect_state());
                    intent.putExtra("PLANT_WATER_STATE",gardens.getWater_state());
                    intent.putExtra("PLANT_NUTRIENT_STATE",gardens.getNutrient_state());
                    intent.putExtra("PLANT_LIGHT_STATE",gardens.getLight_state());
                    intent.putExtra("PLANT_TEMP_STATE",gardens.getTemp_state());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(intent);
                }
            });

        }

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

    @Override
    public int getItemCount() {
        return GardensList.size()+1;
    }


}
