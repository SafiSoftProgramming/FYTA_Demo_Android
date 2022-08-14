package safisoft.fyta;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;
import static com.facebook.FacebookSdk.getApplicationContext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

import pl.droidsonroids.gif.GifTextView;


/**
 * Created by Belal on 10/18/2017.
 */

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.AdPostViewHolder> {


    private Context mCtx;
    private List<Library> adList;
    String Server_URL ;


    public LibraryAdapter(Context mCtx, List<Library> adList) {
        this.mCtx = mCtx;
        this.adList = adList;
    }

    @Override
    public AdPostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.library_list, null);
        Server_URL = mCtx.getResources().getString(R.string.Server_URL);
        return new AdPostViewHolder(view);

    }

    @Override
    public void onBindViewHolder(AdPostViewHolder holder, int position) {
        Library library = adList.get(position);


     
        Glide.with(mCtx)
                .load(Server_URL+ library.get_plant_img())
               // .apply(RequestOptions.skipMemoryCacheOf(true))
             //   .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(holder.gif_postview);


        holder.txtv_plant_name.setText(library.get_plant_name());
        holder.txtv_plant_des.setText(library.get_plant_des());


        holder.btn_plant_more_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LibraryActivity.getInstance().SnackBar("Plant info not include in this demo version.");
            }
        });

        holder.btn_add_to_garden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LibraryActivity.getInstance().SnackBar("Add plant not include in this demo version.");
            }
        });


    }



    @Override
    public int getItemCount() {
        return adList.size();
    }

    class AdPostViewHolder extends RecyclerView.ViewHolder {

        ShapeableImageView gif_postview ;
        TextView txtv_plant_name , txtv_plant_des ;
        ImageButton btn_add_to_garden ,btn_plant_more_info ;



        public AdPostViewHolder(View itemView) {
            super(itemView);
            txtv_plant_name = itemView.findViewById(R.id.txtv_plant_name);
            txtv_plant_des = itemView.findViewById(R.id.txtv_plant_des);
            gif_postview = itemView.findViewById(R.id.gif_postview);
            btn_add_to_garden = itemView.findViewById(R.id.btn_add_to_garden);
            btn_plant_more_info  = itemView.findViewById(R.id.btn_plant_more_info);




        }
    }
}
