package com.example.omocha.Fragments.LiveChat;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omocha.Adapters.HVoiceProfilesRecyclerViewAdapter;
import com.example.omocha.Fragments.CreateNewSpeech.CreateNewSpeechPresenter;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class LiveChatFragment extends Fragment implements LiveChatContract.View {

    private static final String TAG = "LiveChatFragment";
    private LiveChatPresenter presenter;
    private VoiceProfileDAO voiceProfileDAO;
    private SpeechUtil speechUtil;
    private ArrayList<VoiceProfile> voiceProfileArrayList;
    private HVoiceProfilesRecyclerViewAdapter adapter;
    private Unbinder unbinder;

    @BindView(R.id.tts_textbox)
    EditText ttsEditText;

    @BindView(R.id.play_tts)
    Button saveTTSButton;

    @BindView(R.id.horizontal_recycler_view)
    RecyclerView voiceProfilesRecyclerView;

    @BindView(R.id.character_limit_count)
    TextView characterLimitTextView;

    @BindView(R.id.indeterminateBar)
    ProgressBar indeterminateProgressBar;

    public LiveChatFragment() {
        // Required empty public constructor
    }

    public LiveChatFragment(com.example.omocha.Util.SpeechUtil speechUtil) {
        this.speechUtil = speechUtil;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_live_chat, container, false);
        unbinder = ButterKnife.bind(this, view);

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).
                getSupportActionBar()).setTitle("LIVE CHAT");
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).
                getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

        presenter = new LiveChatPresenter(getContext(), this, speechUtil);
        voiceProfileDAO = new VoiceProfileDAO(getContext());
        voiceProfileArrayList = voiceProfileDAO.getAllVoiceProfiles();
        initRecyclerView();

        saveTTSButton.setOnClickListener(v -> {
            if (ttsEditText.getText().length() == 0) {
                Toast.makeText(getContext(), getResources().getString(R.string.no_text_entered), Toast.LENGTH_SHORT).show();
                return;
            }
            int currentHighlightedVoiceProfile = adapter.getCurrentHighlightedVoiceProfile();
            presenter.onGetSpeech(ttsEditText.getText().toString(),
                    voiceProfileArrayList.get(currentHighlightedVoiceProfile));
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
