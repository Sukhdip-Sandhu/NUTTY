package com.example.omocha.Fragments.UseYourVoice;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.omocha.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UseYourVoiceFragment extends Fragment implements UseYourVoiceContract.View {


    public UseYourVoiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_use_your_voice, container, false);
    }

}
