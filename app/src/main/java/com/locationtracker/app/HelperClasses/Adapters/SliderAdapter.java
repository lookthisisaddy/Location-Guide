package com.locationtracker.app.HelperClasses.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.locationtracker.app.R;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater inflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    int[] images = {
        R.drawable.search,
        R.drawable.call,
        R.drawable.missing,
        R.drawable.relax
    };

    int[] headings = {
        R.string.first_slide_title,
        R.string.second_slide_title,
        R.string.third_slide_title,
    };

    int[] descriptions = {
        R.string.first_slide_desc,
        R.string.second_slide_desc,
        R.string.third_slide_desc,
    };

    @Override
    public int getCount() {
        return headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.onboarding_slide, container, false);

        ImageView sliderImage = view.findViewById(R.id.slider_image);
        TextView heading = view.findViewById(R.id.slider_heading);
        TextView desc = view.findViewById(R.id.slider_desc);

        sliderImage.setImageResource(images[position]);
        heading.setText(headings[position]);
        desc.setText(descriptions[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);
    }
}
