package com.delaroystudios.weatherapp.binding;

import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.delaroystudios.weatherapp.R;
import com.squareup.picasso.Picasso;

public class BindingAdapters {
    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter({"bind:imageUrl"})
    public static void setImageUrl(ImageView view, String imageUrl) {
        if (imageUrl != null && imageUrl.length() > 0) {
            Picasso.with(view.getContext())
                    .load(imageUrl)
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.broken_clouds)
                    .into(view);

        }
    }
}
