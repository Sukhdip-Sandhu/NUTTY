package com.example.omocha.Fragments.LiveChat;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.omocha.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LiveChatFragment extends Fragment implements LiveChatContract.View {


    public LiveChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_live_chat, container, false);
    }

}
