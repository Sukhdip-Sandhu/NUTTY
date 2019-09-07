package com.example.omocha.Fragments.CreateVoiceProfile;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.omocha.Fragments.CreateNewSpeech.CreateNewSpeechContract;
import com.example.omocha.R;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateVoiceProfileFragment extends Fragment implements CreateVoiceProfileContract.View{

    private CreateVoiceProfilePresenter presenter;

    public CreateVoiceProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_voice_profile, container, false);
        ButterKnife.bind(this, view);

        presenter = new CreateVoiceProfilePresenter();

        return view;
    }

}
