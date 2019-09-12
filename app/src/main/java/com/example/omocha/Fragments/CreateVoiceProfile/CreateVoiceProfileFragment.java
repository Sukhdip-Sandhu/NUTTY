package com.example.omocha.Fragments.CreateVoiceProfile;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omocha.Adapters.VoiceProfilesRecyclerViewAdapter;
import com.example.omocha.Fragments.CreateVoiceProfile.AddVoiceProfile.AddVoiceProfileFragment;
import com.example.omocha.Models.VoiceProfile;
import com.example.omocha.Models.VoiceProfileDAO;
import com.example.omocha.R;
import com.example.omocha.Util.SpeechUtil;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateVoiceProfileFragment extends Fragment implements CreateVoiceProfileContract.View{

    private VoiceProfileDAO voiceProfileDAO;
    private CreateVoiceProfilePresenter presenter;
    private SpeechUtil speechUtil;
    private ArrayList<VoiceProfile> voiceProfilesList = new ArrayList<>();
    private Unbinder unbinder;

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
        unbinder = ButterKnife.bind(this, view);

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).
                getSupportActionBar()).setTitle("CREATE VOICE PROFILE");
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).
                getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

        presenter = new CreateVoiceProfilePresenter();
        voiceProfileDAO = new VoiceProfileDAO(getContext());
        voiceProfilesList = voiceProfileDAO.getAllVoiceProfiles();
        initRecyclerView();

        createNewVoiceProfileButton.setOnClickListener(v ->
                showFragment(new AddVoiceProfileFragment(speechUtil)));

        return view;
    }

    private void initRecyclerView() {
        VoiceProfilesRecyclerViewAdapter adapter = new VoiceProfilesRecyclerViewAdapter(
                getContext(), voiceProfilesList, speechUtil);
        voiceProfilesRecyclerView.setAdapter(adapter);
        voiceProfilesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void showFragment(Fragment fragment) {
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out,
                        R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.container, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            Objects.requireNonNull(getActivity()).onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
