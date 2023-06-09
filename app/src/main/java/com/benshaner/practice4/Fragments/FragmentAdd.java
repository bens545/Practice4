package com.benshaner.practice4.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.benshaner.practice4.MainActivity;
import com.benshaner.practice4.R;
import com.benshaner.practice4.models.Country;
import com.google.firebase.database.FirebaseDatabase;

public class FragmentAdd extends Fragment implements View.OnClickListener {

    private EditText mEditTextCode;
    private EditText mEditTextName;
    private EditText mEditTextContinent;
    private Button mSaveButton;
    private FirebaseDatabase firebaseDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add, container, false);

        // Find Edit Texts and buttons
        mEditTextCode = rootView.findViewById(R.id.code);
        mEditTextName = rootView.findViewById(R.id.name);
        mEditTextContinent = rootView.findViewById(R.id.continent);
        mSaveButton = rootView.findViewById(R.id.save_button);

        // Setup button listeners
        mSaveButton.setOnClickListener(this);

        firebaseDatabase = FirebaseDatabase.getInstance();

        return rootView;
    }

    @Override
    public void onClick(View view) {
        // Gather text from inputs
        String code = mEditTextCode.getText().toString();
        String name = mEditTextName.getText().toString();
        String continent = mEditTextContinent.getText().toString();

        // Verify Country code is provided
        if (TextUtils.isEmpty(code)) {
            mEditTextCode.setError("Country code is required");
            return;
        }

        // Verify Country name is provided
        if (TextUtils.isEmpty(name)) {
            mEditTextName.setError("Country name is required");
            return;
        }

        // Create a new country and save to the database
        // Once saved it will automatically display on the recycler view
        Country country = new Country(code, name, continent);
        firebaseDatabase.getReference("countries").child(code).setValue(country);
        Toast.makeText(getActivity(), "Save Successful!", Toast.LENGTH_SHORT).show();

        // Clear inputs
        mEditTextCode.setText("");
        mEditTextName.setText("");
        mEditTextContinent.setText("");
    }
}