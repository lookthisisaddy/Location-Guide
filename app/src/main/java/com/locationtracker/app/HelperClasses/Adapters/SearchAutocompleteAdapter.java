package com.locationtracker.app.HelperClasses.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.text.style.CharacterStyle;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.locationtracker.app.HelperClasses.Interfaces.AutoCompleteAdapterClickListener;
import com.locationtracker.app.R;

import java.util.ArrayList;
import java.util.List;

public class SearchAutocompleteAdapter extends RecyclerView.Adapter<SearchAutocompleteAdapter.PlaceHolder> {

    static final String TAG = "AutoCompleteAdapter";

    Context context;

    public static List<String> placeIDs = new ArrayList<>();
    List<String> predictions = new ArrayList<>();

    PlacesClient placesClient;

    private final CharacterStyle STYLE_BOLD = new StyleSpan(Typeface.BOLD);

    static AutoCompleteAdapterClickListener adapterClickListener;

    public SearchAutocompleteAdapter(Context context, AutoCompleteAdapterClickListener autoCompleteAdapterClickListener) {
        this.context = context;
        adapterClickListener = autoCompleteAdapterClickListener;
        placesClient = Places.createClient(context);

    }

    @NonNull
    @Override
    public PlaceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //individual rows or item in rec view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.search_suggestions_card, parent, false);

        return new PlaceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceHolder holder, int position) {
        if (predictions != null) {
             holder.placeText.setText(predictions.get(position));
        }

    }

    @Override
    public int getItemCount() {
        if (predictions != null){
            return predictions.size();
        }else {
            return 0;
        }

    }


    static class PlaceHolder extends RecyclerView.ViewHolder {

        TextView placeText;

        public PlaceHolder(@NonNull View itemView) {
            super(itemView);

            placeText = itemView.findViewById(R.id.suggestion_text_view);

            placeText.setOnClickListener(v -> {
                adapterClickListener.onSearchedItemClick(getAdapterPosition());
            });
        }

    }

    public void getPredictions(CharSequence charSequence) {

        if (charSequence.length() > 0){

            AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();

            FindAutocompletePredictionsRequest predictionsRequest = FindAutocompletePredictionsRequest
                    .builder()
                    .setCountry("IN")
                    .setTypeFilter(TypeFilter.ESTABLISHMENT)
                    .setSessionToken(token)
                    .setQuery(charSequence.toString())
                    .build();

            placesClient.findAutocompletePredictions(predictionsRequest)
                    .addOnSuccessListener(response -> {
                        Log.d(TAG, "getPredictions: Hello");
                        predictions.clear();
                        placeIDs.clear();
                        for (int i = 0; i < response.getAutocompletePredictions().size(); i++) {
                            AutocompletePrediction prediction = response.getAutocompletePredictions().get(i);
                            placeIDs.add(prediction.getPlaceId());
                            predictions.add(prediction.getFullText(STYLE_BOLD).toString());
                        }
                        notifyDataSetChanged();
                    }).addOnFailureListener(e -> Log.d(TAG, "onFailure: " + e.getMessage()));
        }

    }
}