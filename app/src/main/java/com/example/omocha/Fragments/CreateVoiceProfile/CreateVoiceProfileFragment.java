package com.example.omocha.Fragments.CreateVoiceProfile;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.omocha.Adapters.VoiceProfilesRecyclerViewAdapter;
import com.example.omocha.Fragments.CreateNewSpeech.CreateNewSpeechContract;
import com.example.omocha.Fragments.CreateVoiceProfile.AddVoiceProfile.AddVoiceProfileFragment;
import com.example.omocha.Models.VoiceProfile;
import com.example.omocha.Models.VoiceProfileDAO;
import com.example.omocha.R;
import com.example.omocha.Util.SpeechUtil;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateVoiceProfileFragment extends Fragment implements CreateVoiceProfileContract.View{

    private static final String TAG = "VoiceProfilesTAG";

    VoiceProfileDAO voiceProfileDAO;
    private CreateVoiceProfilePresenter presenter;
    SpeechUtil speechUtil;
    private ArrayList<VoiceProfile> voiceProfilesList = new ArrayList<>();

    @BindView(R.id.voice_profiles_recycler_view)
    RecyclerView voiceProfilesRecyclerView;

    @BindView(R.id.create_new_voice_profile)
    Button createNewVoiceProfileButton;

    public CreateVoiceProfileFragment() {
        // Required empty public constructor
    }

    public CreateVoiceProfileFragment(SpeechUtil speechUtil) {
        this.speechUtil = speechUtil;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_voice_profile, container, false);
        ButterKnife.bind(this, view);
        presenter = new CreateVoiceProfilePresenter();
        voiceProfileDAO = new VoiceProfileDAO(getContext());
        voiceProfilesList = voiceProfileDAO.getAllVoiceProfiles();
        initRecyclerView();

        createNewVoiceProfileButton.setOnClickListener(v -> {
            showFragment(new AddVoiceProfileFragment(speechUtil));
        });

        return view;
    }

    private void initRecyclerView() {
        VoiceProfilesRecyclerViewAdapter adapter = new VoiceProfilesRecyclerViewAdapter(
                getContext(), voiceProfilesList);
        voiceProfilesRecyclerView.setAdapter(adapter);
        voiceProfilesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void showFragment(Fragment fragment) {
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(null)
                .commit();
    }

}
