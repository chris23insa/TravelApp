package com.example.chris.travelorga_kth;

/**
 * Created by Chris on 13/11/2019.
 */
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TripRecyclerViewItemHolder extends RecyclerView.ViewHolder {

    private TextView tripTitleText = null;

    private TextView tripDateText = null;

    private TextView tripDescriptionText = null;

    private ImageView tripImageView = null;

    public TripRecyclerViewItemHolder(View itemView) {
        super(itemView);

        if(itemView != null)
        {
            tripTitleText = (TextView)itemView.findViewById(R.id.card_view_image_title);

            tripDateText = (TextView)itemView.findViewById(R.id.card_view_date);

            tripDescriptionText = (TextView)itemView.findViewById(R.id.card_view_description);

            tripImageView = (ImageView)itemView.findViewById(R.id.card_view_image);
        }
    }

    public TextView getTripTitleText() {
        return tripTitleText;
    }

    public TextView getTripDateText() { return tripDateText; }

    public TextView getTripDescriptionText() {
        return tripDescriptionText;
    }

    public ImageView getTripImageView() {
        return tripImageView;
    }
}