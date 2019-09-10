package com.example.omocha.Fragments.LiveChat;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.omocha.Adapters.HVoiceProfilesRecyclerViewAdapter;
import com.example.omocha.Fragments.CreateNewSpeech.CreateNewSpeechPresenter;
import com.example.omocha.Models.SavedSpeechDAO;
import com.example.omocha.Models.VoiceProfile;
import com.example.omocha.Models.VoiceProfileDAO;
import com.example.omocha.R;
import com.example.omocha.Util.SpeechUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    @BindView(R.id.tts_textbox)
    EditText ttsEditText;

    @BindView(R.id.play_tts)
    Button saveTTSButton;

    @BindView(R.id.horizontal_recycler_view)
    RecyclerView voiceProfilesRecyclerView;

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
        ButterKnife.bind(this, view);

        presenter = new LiveChatPresenter(getContext(), this, speechUtil);
        voiceProfileDAO = new VoiceProfileDAO(getContext());
        voiceProfileArrayList = voiceProfileDAO.getAllVoiceProfiles();
        initRecyclerView();


        saveTTSButton.setOnClickListener(v -> {
            int currentHighlightedVoiceProfile = adapter.getCurrentHighlightedVoiceProfile();
            presenter.onGetSpeech(ttsEditText.getText().toString(),
                    voiceProfileArrayList.get(currentHighlightedVoiceProfile));
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
    }

    @Override
    public void onHideProgressIndicator() {
        indeterminateProgressBar.setVisibility(View.INVISIBLE);
    }
}
