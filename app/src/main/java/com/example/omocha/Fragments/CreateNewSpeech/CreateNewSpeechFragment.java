package com.example.omocha.Fragments.CreateNewSpeech;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.omocha.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateNewSpeechFragment extends Fragment implements CreateNewSpeechContract.View{


    public CreateNewSpeechFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_new_speech, container, false);
    }

}
