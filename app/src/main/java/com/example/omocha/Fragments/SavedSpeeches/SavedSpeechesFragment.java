package com.example.omocha.Fragments.SavedSpeeches;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.omocha.Adapters.SavedSpeechRecyclerViewAdapter;
import com.example.omocha.Adapters.VoiceProfilesRecyclerViewAdapter;
import com.example.omocha.Fragments.CreateVoiceProfile.CreateVoiceProfilePresenter;
import com.example.omocha.MainActivity;
import com.example.omocha.Models.SavedSpeech;
import com.example.omocha.Models.SavedSpeechDAO;
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
public class SavedSpeechesFragment extends Fragment implements SavedSpeechesContract.View {

    private static final String TAG = "SavedSpeechesFragment";

    private SavedSpeechDAO savedSpeechDAO;
    private SavedSpeechesPresenter presenter;
    private SpeechUtil speechUtil;
    private ArrayList<SavedSpeech> savedSpeechArrayList = new ArrayList<>();
    private Unbinder unbinder;

    @BindView(R.id.saved_speech_recycler_view)
    RecyclerView savedSpeechesRecyclerView;

    @BindView(R.id.create_new_speech)
    Button createNewSpeechButton;

    public SavedSpeechesFragment() {
        // Required empty public constructor
    }

    public SavedSpeechesFragment(com.example.omocha.Util.SpeechUtil speechUtil) {
        this.speechUtil = speechUtil;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_saved_speeches, container, false);
        unbinder = ButterKnife.bind(this, view);

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).
                getSupportActionBar()).setTitle("SAVED SPEECHES");
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).
                getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

        presenter = new SavedSpeechesPresenter();
        savedSpeechDAO = new SavedSpeechDAO(getContext());
        savedSpeechArrayList = savedSpeechDAO.getAllSpeeches();
        initRecyclerView();

        createNewSpeechButton.setOnClickListener(v -> ((MainActivity) Objects.requireNonNull(
                getContext())).showCreateNewSpeechFragment(speechUtil));

        return view;
    }

    private void initRecyclerView() {
        SavedSpeechRecyclerViewAdapter adapter = new SavedSpeechRecyclerViewAdapter(
                getContext(), savedSpeechArrayList, speechUtil);
        savedSpeechesRecyclerView.setAdapter(adapter);
        savedSpeechesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        speechUtil.stopSpeaking();
        unbinder.unbind();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            Objects.requireNonNull(getActivity()).onBackPressed();
        return super.onOptionsItemSelected(item);
    }

}
