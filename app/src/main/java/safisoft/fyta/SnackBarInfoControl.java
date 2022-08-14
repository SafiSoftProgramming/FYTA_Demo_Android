package safisoft.fyta;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.snackbar.Snackbar;

public class SnackBarInfoControl {

    public void SnackBarInfoControlView( View v, Activity activity,String Text_info,int animation){


        final Snackbar snackbar = Snackbar.make(v, "", Snackbar.LENGTH_LONG);
        View customSnackView = activity.getLayoutInflater().inflate(R.layout.snack_bar_info, null);


        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);

        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();

        snackbarLayout.setPadding(0, 0, 0, 0);

        TextView txtv_snack_bar_info = customSnackView.findViewById(R.id.txtv_snack_bar_info);
        LottieAnimationView lottieAnimationView = customSnackView.findViewById(R.id.lottie_info);
        lottieAnimationView.setAnimation(animation);
        txtv_snack_bar_info.setText(Text_info);
        snackbarLayout.addView(customSnackView, 0);

        snackbar.show();

    }
// show snackbar at the start of Activity
//    SnackBarInfoControl snackBarInfoControl = new SnackBarInfoControl();
//    snackBarInfoControl.SnackBarInfoControlView(getApplicationContext(),findViewById(android.R.id.content).getRootView(),StartNewProjectActivity.this,"Start New Project");



}
