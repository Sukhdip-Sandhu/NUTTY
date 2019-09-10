package com.example.omocha.Fragments.CreateNewSpeech;


import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omocha.Adapters.HVoiceProfilesRecyclerViewAdapter;
import com.example.omocha.Models.SavedSpeechDAO;
import com.example.omocha.Models.VoiceProfile;
import com.example.omocha.Models.VoiceProfileDAO;
import com.example.omocha.R;
import com.example.omocha.Util.SpeechUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CreateNewSpeechFragment extends Fragment implements CreateNewSpeechContract.View{

    private static final String TAG = "CreateNewSpeechFragment";

    private CreateNewSpeechPresenter presenter;
    private SavedSpeechDAO savedSpeechDAO;
    private VoiceProfileDAO voiceProfileDAO;
    private SpeechUtil speechUtil;
    private ArrayList<VoiceProfile> voiceProfileArrayList;
    private HVoiceProfilesRecyclerViewAdapter adapter;

    @BindView(R.id.tts_textbox)
    EditText ttsEditText;

    @BindView(R.id.test_tts)
    Button testTTSButton;

    @BindView(R.id.save_tts)
    Button saveTTSButton;

    @BindView(R.id.horizontal_recycler_view)
    RecyclerView voiceProfilesRecyclerView;

    @BindView(R.id.indeterminateBar)
    ProgressBar indeterminateProgressBar;

    public CreateNewSpeechFragment() {

    }

    public CreateNewSpeechFragment(com.example.omocha.Util.SpeechUtil speechUtil) {
        this.speechUtil = speechUtil;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_new_speech, container, false);
        ButterKnife.bind(this, view);
        presenter = new CreateNewSpeechPresenter(getContext(), this, speechUtil, new SavedSpeechDAO(getContext()));
        savedSpeechDAO = new SavedSpeechDAO(getContext());
        voiceProfileDAO = new VoiceProfileDAO(getContext());
        voiceProfileArrayList = voiceProfileDAO.getAllVoiceProfiles();
        initRecyclerView();

        testTTSButton.setOnClickListener(v -> {
            int currentHighlightedVoiceProfile = adapter.getCurrentHighlightedVoiceProfile();
            presenter.onGetSpeech(ttsEditText.getText().toString(),
                    voiceProfileArrayList.get(currentHighlightedVoiceProfile), false, null);
        });

        saveTTSButton.setOnClickListener(v -> confirmSaveDialog());

        return view;
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        voiceProfilesRecyclerView.setLayoutManager(layoutManager);
        adapter = new HVoiceProfilesRecyclerViewAdapter(getContext(), voiceProfileArrayList);
        voiceProfilesRecyclerView.setAdapter(adapter);
    }


    @Override
    public void onShowProgressIndicator() {
        indeterminateProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onHideProgressIndicator() {
        indeterminateProgressBar.setVisibility(View.INVISIBLE);
    }

    private void confirmSaveDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        final EditText speechTitleEditText = new EditText(getActivity());
        alert.setTitle(getResources().getString(R.string.save_speech_title));
        alert.setMessage(getResources().getString(R.string.save_speech_message));
        alert.setView(speechTitleEditText);
        alert.setPositiveButton(getResources().getString(R.string.yes), (dialog, whichButton) -> {
            String inputSpeechTitleName = speechTitleEditText.getText().toString();
            if (isSpeechTitleOkay(inputSpeechTitleName)) {
                int currentHighlightedVoiceProfile = adapter.getCurrentHighlightedVoiceProfile();
                presenter.onGetSpeech(ttsEditText.getText().toString(),
                        voiceProfileArrayList.get(currentHighlightedVoiceProfile), true, inputSpeechTitleName);
            } else {
                Toast.makeText(getContext(), getResources().getString(R.string.save_speech_error), Toast.LENGTH_LONG).show();
            }
        });
        alert.setNegativeButton(getResources().getString(R.string.no), (dialog, whichButton) -> {});
        alert.show();
    }

    private boolean isSpeechTitleOkay(String requestedName) {
        ArrayList<String> databaseSavedSpeechNames = savedSpeechDAO.getAllSavedSpeechNames();
        // check if name exists in the database
        for (String existingName : databaseSavedSpeechNames) {
            if (existingName.equals(requestedName)) {
                return false;
            }
        }
        return true;
    }
}
