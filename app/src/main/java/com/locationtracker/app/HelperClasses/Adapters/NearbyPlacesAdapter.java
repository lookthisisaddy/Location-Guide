package com.locationtracker.app.HelperClasses.Adapters;

import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.locationtracker.app.HelperClasses.APIData.DataParser;
import com.locationtracker.app.HelperClasses.APIData.GetNearbyPlace;
import com.locationtracker.app.HelperClasses.Interfaces.PlacesAdapterClickListener;
import com.locationtracker.app.R;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class NearbyPlacesAdapter extends RecyclerView.Adapter<NearbyPlacesAdapter.ViewHolder> {

    public static final String TAG = "NearbyPlacesAdapter";

    static PlacesAdapterClickListener cardClickListener;

    public static List<HashMap<String, String>> placeList;

    GoogleMap map;

    public NearbyPlacesAdapter(PlacesAdapterClickListener placesAdapterClickListener, GoogleMap googleMap) {
        cardClickListener = placesAdapterClickListener;
        this.map = googleMap;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.nearby_place_card, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (placeList != null) {
            if (placeList.get(position).get("name") != null) {

                holder.name.setText(placeList.get(position).get("name"));

                if (placeList.get(position).get("rating") != null &&
                        placeList.get(position).get("user_ratings_total") != null) {

                    String rat = placeList.get(position).get("rating");
                    holder.ratingBar.setRating(Float.parseFloat(Objects.requireNonNull(rat)));
                    holder.rating.setText(rat);
                    String s = placeList.get(position).get("user_ratings_total") + " reviews";
                    holder.totalRatings.setText(s);

                } else {
                    String message = "Not rated";
                    holder.rating.setText(message);
                    holder.ratingBar.setVisibility(View.INVISIBLE);
                    holder.totalRatings.setVisibility(View.INVISIBLE);
                }

            } else {
                //some names were null but rating was shown on some Mobile device
                placeList.remove(position);
            }
        }

    }

    @Override
    public int getItemCount() {
        if (placeList != null){
            return placeList.size();
        }else {
            return 0;
        }

    }


    public void showPlaces(String url) {

        GetNearbyPlace task = new GetNearbyPlace();
        String data = null;

        try {
            data = task.execute(url).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        DataParser dataParser = new DataParser();
        placeList = dataParser.parsePlaces(data);
        notifyDataSetChanged();
    }

    public void setMarker(int position) {

        if (placeList != null) {

            if (placeList.get(position).get("lat") != null &&
                    placeList.get(position).get("lng") != null &&
                    placeList.get(position).get("name") != null) {

                String lat = placeList.get(position).get("lat");
                String lng = placeList.get(position).get("lng");

                double latitude = Double.parseDouble(lat);
                double longitude = Double.parseDouble(lng);

                LatLng latLng = new LatLng(latitude, longitude);

                map.addMarker(new MarkerOptions()
                        .title(placeList.get(position).get("name"))
                        .position(latLng));

                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
            }

        }
    }

    private void circle(Location location) {

        Circle circle = map.addCircle(new CircleOptions()
                .center(new LatLng(location.getLatitude(), location.getLongitude()))
                .radius(1500));

        circle.setVisible(false);

        double radius = circle.getRadius();
        double scale = radius / 500;
        int zoomLevel = (int) (16 - Math.log(scale) / Math.log(2));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout businessCard;

        TextView name, rating, totalRatings;
        RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.business_name);
            rating = itemView.findViewById(R.id.rating_text);
            totalRatings = itemView.findViewById(R.id.total_ratings);
            ratingBar = itemView.findViewById(R.id.rating_bar);

            businessCard = itemView.findViewById(R.id.business_card_layout);
            businessCard.setOnClickListener(v -> cardClickListener.onCardClick(getAdapterPosition()));

        }
    }
}
