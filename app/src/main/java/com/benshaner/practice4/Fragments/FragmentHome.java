package com.benshaner.practice4.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.benshaner.practice4.R;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHome extends Fragment {

    private TextView mUserNameTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_home, container, false);
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        mUserNameTextView = rootView.findViewById(R.id.user_name_text);
        mUserNameTextView.setText(email);
        return rootView;
    }
}