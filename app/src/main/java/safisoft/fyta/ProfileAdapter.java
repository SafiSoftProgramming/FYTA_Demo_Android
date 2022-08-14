package safisoft.fyta;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;


public class ProfileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    String ServerURL ;
    String Notification_Icon_Folder_Name ;
    String Notification_Icon_Type ;
    private Context mCtx;
    private List<Profile> profileList;



    class HeaderViewMessagesHolder extends RecyclerView.ViewHolder {

        CircularImageView imgv_profile_pic_head ;
        ImageView imgv_rank_icon_head ;
        TextView txtv_plant_count_head , txtv_beams_count_head , txtv_member_name_head , txtv_rank_head , txtv_date_head , txtv_location_head ;
        LottieAnimationView lottie_star_rate_head ;

        public HeaderViewMessagesHolder(View itemView) {
            super(itemView);

            imgv_profile_pic_head = itemView.findViewById(R.id.imgv_profile_pic_head);
            imgv_rank_icon_head = itemView.findViewById(R.id.imgv_rank_icon_head);
            txtv_plant_count_head = itemView.findViewById(R.id.txtv_plant_count_head);
            txtv_beams_count_head = itemView.findViewById(R.id.txtv_beams_count_head);
            txtv_member_name_head = itemView.findViewById(R.id.txtv_member_name_head);
            txtv_rank_head = itemView.findViewById(R.id.txtv_rank_head);
            txtv_date_head = itemView.findViewById(R.id.txtv_date_head);
            txtv_location_head = itemView.findViewById(R.id.txtv_location_head);
            lottie_star_rate_head = itemView.findViewById(R.id.lottie_star_rate_head);

        }
    }



    class MembersProfileViewHolder extends RecyclerView.ViewHolder {
          CircularImageView imgv_profile_pic_list ;
          ImageView imgv_rank_icon_list ;
          TextView txtv_plant_count_list , txtv_beams_count_list , txtv_member_name_list , txtv_rank_list , txtv_date_list , txtv_location_list ;
          LottieAnimationView lottie_star_rate_list ;

        public MembersProfileViewHolder(View itemView) {
            super(itemView);

            imgv_profile_pic_list = itemView.findViewById(R.id.imgv_profile_pic_list);
            imgv_rank_icon_list = itemView.findViewById(R.id.imgv_rank_icon_list);
            txtv_plant_count_list = itemView.findViewById(R.id.txtv_plant_count_list);
            txtv_beams_count_list = itemView.findViewById(R.id.txtv_beams_count_list);
            txtv_member_name_list = itemView.findViewById(R.id.txtv_member_name_list);
            txtv_rank_list = itemView.findViewById(R.id.txtv_rank_list);
            txtv_date_list = itemView.findViewById(R.id.txtv_date_list);
            txtv_location_list = itemView.findViewById(R.id.txtv_location_list);
            lottie_star_rate_list = itemView.findViewById(R.id.lottie_star_rate_list);


        }
    }




    public ProfileAdapter(Context mCtx, List<Profile> profileList) {
        this.mCtx = mCtx;
        this.profileList = profileList;
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
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_header, parent, false);
            return new HeaderViewMessagesHolder(view);
        }
        else {
         view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_list, parent, false);
         return new MembersProfileViewHolder(view);
        }


    }

    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, int position) {
            ServerURL = mCtx.getResources().getString(R.string.Server_URL);
            Notification_Icon_Folder_Name = mCtx.getResources().getString(R.string.URL_members_pic);


        if(position == 0){
            Profile profile = profileList.get(8);

            Glide.with(mCtx)
                    .load(ServerURL + Notification_Icon_Folder_Name + profile.getProfile_pic())
                    .apply(centerCropTransform()
                            .placeholder(R.drawable.ic_defult_user_circ)
                            .error(R.drawable.ic_defult_user_circ)
                            .priority(Priority.HIGH))
                    .apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .into(((HeaderViewMessagesHolder) holder).imgv_profile_pic_head);


            Glide.with(mCtx)
                    .load(ServerURL + "rank_icon/" + profile.getRank_icon())
                    .apply(centerCropTransform()
                            .placeholder(R.drawable.ic_defult_user_circ)
                            .error(R.drawable.ic_defult_user_circ)
                            .priority(Priority.HIGH))
                 //   .apply(RequestOptions.skipMemoryCacheOf(true))
                 //   .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .into(((HeaderViewMessagesHolder) holder).imgv_rank_icon_head);



            ((HeaderViewMessagesHolder) holder).txtv_plant_count_head.setText(profile.getPlant_count());
            ((HeaderViewMessagesHolder) holder).txtv_beams_count_head.setText(String.valueOf(profile.getBeams_count()));
            ((HeaderViewMessagesHolder) holder).txtv_member_name_head.setText(profile.getUser_name());
            ((HeaderViewMessagesHolder) holder).txtv_rank_head.setText("Your Rank is "+profile.getUser_rank());
            ((HeaderViewMessagesHolder) holder).txtv_date_head.setText(String.valueOf("FYTA Member Since "+profile.getMember_date()));
            ((HeaderViewMessagesHolder) holder).txtv_location_head.setText(String.valueOf(profile.getLocation()));


            int State_Int = Integer.parseInt(profile.getUser_rank());
            int frame = State_Int / 20 + 25;
            ((HeaderViewMessagesHolder) holder).lottie_star_rate_head.setFrame(State_Int + frame);



        }
        else {

            position = position - 1;
            Profile profile = profileList.get(position);

            Glide.with(mCtx)
                    .load(ServerURL + Notification_Icon_Folder_Name + profile.getProfile_pic())
                    .apply(centerCropTransform()
                            .placeholder(R.drawable.ic_defult_user_circ)
                            .error(R.drawable.ic_defult_user_circ)
                            .priority(Priority.HIGH))
               //     .apply(RequestOptions.skipMemoryCacheOf(true))
                 //   .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .into(((MembersProfileViewHolder) holder).imgv_profile_pic_list);


            Glide.with(mCtx)
                    .load(ServerURL + "rank_icon/" + profile.getRank_icon())
                    .apply(centerCropTransform()
                            .placeholder(R.drawable.ic_defult_user_circ)
                            .error(R.drawable.ic_defult_user_circ)
                            .priority(Priority.HIGH))
                 //   .apply(RequestOptions.skipMemoryCacheOf(true))
                 //   .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .into(((MembersProfileViewHolder) holder).imgv_rank_icon_list);



            ((MembersProfileViewHolder) holder).txtv_plant_count_list.setText(profile.getPlant_count());
            ((MembersProfileViewHolder) holder).txtv_beams_count_list.setText(String.valueOf(profile.getBeams_count()));
            ((MembersProfileViewHolder) holder).txtv_member_name_list.setText(profile.getUser_name());
            ((MembersProfileViewHolder) holder).txtv_rank_list.setText("The user Rank is "+profile.getUser_rank());
            ((MembersProfileViewHolder) holder).txtv_date_list.setText(String.valueOf("FYTA Member Since "+profile.getMember_date()));
            ((MembersProfileViewHolder) holder).txtv_location_list.setText(String.valueOf(profile.getLocation()));


            int State_Int = Integer.parseInt(profile.getUser_rank());
            int frame = State_Int / 20 + 25;
            ((MembersProfileViewHolder) holder).lottie_star_rate_list.setFrame(State_Int + frame);


        }

    }

    @Override
    public int getItemCount() {
        return profileList.size()+1;
    }


}
