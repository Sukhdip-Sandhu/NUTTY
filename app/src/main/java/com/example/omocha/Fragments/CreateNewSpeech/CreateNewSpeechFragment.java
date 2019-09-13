package com.example.omocha.Fragments.CreateNewSpeech;


import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omocha.Adapters.HVoiceProfilesRecyclerViewAdapter;
import com.example.omocha.MainActivity;
import com.example.omocha.Models.SavedSpeechDAO;
import com.example.omocha.Models.VoiceProfile;
import com.example.omocha.Models.VoiceProfileDAO;
import com.example.omocha.R;
import com.example.omocha.Util.SpeechUtil;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class CreateNewSpeechFragment extends Fragment implements CreateNewSpeechContract.View{

    private static final String TAG = "CreateNewSpeechFragment";

    private CreateNewSpeechPresenter presenter;
    private SavedSpeechDAO savedSpeechDAO;
    private VoiceProfileDAO voiceProfileDAO;
    private SpeechUtil speechUtil;
    private ArrayList<VoiceProfile> voiceProfileArrayList;
    private HVoiceProfilesRecyclerViewAdapter adapter;
    private Unbinder unbinder;

    @BindView(R.id.tts_textbox)
    EditText ttsEditText;

    @BindView(R.id.test_tts)
    Button testTTSButton;

    @BindView(R.id.save_tts)
    Button saveTTSButton;

    @BindView(R.id.horizontal_recycler_view)
    RecyclerView voiceProfilesRecyclerView;

    @BindView(R.id.character_limit_count)
    TextView characterLimitTextView;

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
        unbinder = ButterKnife.bind(this, view);

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).
                getSupportActionBar()).setTitle("CREATE SPEECH");
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).
                getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

        presenter = new CreateNewSpeechPresenter(getContext(), this, speechUtil, new SavedSpeechDAO(getContext()));
        savedSpeechDAO = new SavedSpeechDAO(getContext());
        voiceProfileDAO = new VoiceProfileDAO(getContext());
        voiceProfileArrayList = voiceProfileDAO.getAllVoiceProfiles();
        initRecyclerView();

        testTTSButton.setOnClickListener(v -> {
            if (ttsEditText.getText().length() == 0) {
                Toast.makeText(getContext(), getResources().getString(R.string.no_text_entered), Toast.LENGTH_SHORT).show();
                return;
            }
            int currentHighlightedVoiceProfile = adapter.getCurrentHighlightedVoiceProfile();
            presenter.onGetSpeech(ttsEditText.getText().toString(),
                    voiceProfileArrayList.get(currentHighlightedVoiceProfile), false, null);
        });

        ttsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int remaining = 200 - s.length();
                characterLimitTextView.setText(String.valueOf(remaining));
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void afterTextChanged(Editable s) { }
        });

        saveTTSButton.setOnClickListener(v -> {
            if (ttsEditText.getText().length() == 0) {
                Toast.makeText(getContext(), getResources().getString(R.string.no_text_entered), Toast.LENGTH_SHORT).show();
                return;
            }
            confirmSaveDialog();
        });

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
        indeterminateProgressBar.bringToFront();
    }

    @Override
    public void onHideProgressIndicator() {
        indeterminateProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void clearEditText() {
        ttsEditText.setText("");
    }

    private void confirmSaveDialog() {
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(50, 0, 50, 0);
        EditText textBox = new EditText(getContext());
        textBox.setHint("MY AWESOME SPEECH");
        layout.addView(textBox, params);
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setIcon(R.drawable.temp_acorn);
        alert.setTitle(getResources().getString(R.string.save_speech_title));
        alert.setMessage(getResources().getString(R.string.save_speech_message));
        alert.setView(layout);
        alert.setPositiveButton(getResources().getString(R.string.yes), (dialog, whichButton) -> {
            String inputSpeechTitleName = textBox.getText().toString().toUpperCase();
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
