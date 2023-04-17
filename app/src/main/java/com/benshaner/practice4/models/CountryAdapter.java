package com.benshaner.practice4.models;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.benshaner.practice4.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> {

    private List<Country> mCountries;
    private FirebaseDatabase firebaseDatabase;

    public CountryAdapter(List<Country> mCountries) {
        this.mCountries = mCountries;
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    @NonNull
    @Override
    public CountryAdapter.CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        Country country = mCountries.get(position);
        holder.bind(country);
        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Delete: ", country.getCode());
                firebaseDatabase.getReference("countries").child(country.getCode()).removeValue();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCountries.size();
    }

    public void setCountries(List<Country> countries) {
        mCountries = countries;
        notifyDataSetChanged();
    }

    static class CountryViewHolder extends RecyclerView.ViewHolder {
        private TextView mCodeTextView;
        private TextView mNameTextView;
        private TextView mContinentTextView;
        public ImageButton mDeleteButton;

        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            mCodeTextView = itemView.findViewById(R.id.code);
            mNameTextView = itemView.findViewById(R.id.name);
            mContinentTextView = itemView.findViewById(R.id.continent);
            mDeleteButton = itemView.findViewById(R.id.delete_button);
        }

        public void bind(Country country) {
            mCodeTextView.setText("Code: " + country.getCode());
            mNameTextView.setText("Name: " + country.getName());
            mContinentTextView.setText("Continent: " + country.getContinent());
        }
    }
}
