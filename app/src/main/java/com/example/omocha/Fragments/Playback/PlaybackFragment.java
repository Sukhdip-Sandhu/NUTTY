package com.example.omocha.Fragments.Playback;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.omocha.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlaybackFragment extends Fragment implements PlaybackContract.View {


    public PlaybackFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_playback, container, false);
    }

}
