package com.example.omocha.Fragments.CreateVoiceProfile.AddVoiceProfile;


import android.app.AlertDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.ToggleButton;

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
public class AddVoiceProfileFragment extends Fragment implements AddVoiceProfileContract.View {

    private static final String TAG = "AddVoiceProfileFragment";

    private AddVoiceProfilePresenter presenter;
    private VoiceProfileDAO voiceProfileDAO;
    private SpeechUtil speechUtil;
    private VoiceProfile voiceProfileConfigurations;
    private Unbinder unbinder;

    @BindView(R.id.voice_girl)
    ToggleButton voiceGirlToggle;

    @BindView(R.id.voice_boy)
    ToggleButton voiceBoyToggle;

    @BindView(R.id.emotion_none)
    ToggleButton emotionNoneToggle;

    @BindView(R.id.emotion_happy)
    ToggleButton emotionHappyToggle;

    @BindView(R.id.emotion_sad)
    ToggleButton emotionSadToggle;

    @BindView(R.id.emotion_angry)
    ToggleButton emotionAngryToggle;

    @BindView(R.id.emotion_seekbar)
    SeekBar emotionSeekbar;

    @BindView(R.id.pitch_seekbar)
    SeekBar pitchSeekbar;

    @BindView(R.id.speed_seekbar)
    SeekBar speedSeekbar;

    @BindView(R.id.volume_seekbar)
    SeekBar volumeSeekbar;

    @BindView(R.id.test_voice_sample1)
    Button voiceSample1Button;

    @BindView(R.id.test_voice_sample2)
    Button voiceSample2Button;

    @BindView(R.id.test_voice_sample3)
    Button voiceSample3Button;

    @BindView(R.id.save_voice_profile)
    Button saveVoiceProfileButton;

    @BindView(R.id.indeterminateBar)
    ProgressBar indeterminateProgressBar;


    public AddVoiceProfileFragment() {
        // Required empty public constructor
    }

    public AddVoiceProfileFragment(com.example.omocha.Util.SpeechUtil speechUtil) {
        this.speechUtil = speechUtil;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_voice_profile, container, false);
        unbinder = ButterKnife.bind(this, view);

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).
                getSupportActionBar()).setTitle("ADD VOICE PROFILE");
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).
                getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

        voiceProfileDAO = new VoiceProfileDAO(getContext());
        presenter = new AddVoiceProfilePresenter(getContext(), this, speechUtil, voiceProfileDAO);

        voiceProfileConfigurations = new VoiceProfile("tmp",
                "hikari", null, 0, 100, 100, 100);

        voiceGirlToggle.setOnClickListener(v -> presenter.handleSpeakerToggles(
                String.valueOf(voiceGirlToggle.getText())));

        voiceBoyToggle.setOnClickListener(v -> presenter.handleSpeakerToggles(
                String.valueOf(voiceBoyToggle.getText())));

        emotionNoneToggle.setOnClickListener(v -> presenter.handleEmotionToggles(
                String.valueOf(emotionNoneToggle.getText())));

        emotionHappyToggle.setOnClickListener(v -> presenter.handleEmotionToggles(
                String.valueOf(emotionHappyToggle.getText())));

        emotionSadToggle.setOnClickListener(v -> presenter.handleEmotionToggles(
                String.valueOf(emotionSadToggle.getText())));

        emotionAngryToggle.setOnClickListener(v -> presenter.handleEmotionToggles(
                String.valueOf(emotionAngryToggle.getText())));

        voiceSample1Button.setOnClickListener(v -> {
            voiceProfileConfigurations = getVoiceDataFromUI();
            presenter.onTestVoiceProfile(
                    getSampleString(String.valueOf(voiceSample1Button.getText())), voiceProfileConfigurations);
        });

        voiceSample2Button.setOnClickListener(v -> {
            voiceProfileConfigurations = getVoiceDataFromUI();
            presenter.onTestVoiceProfile(getSampleString(
                    String.valueOf(voiceSample2Button.getText())), voiceProfileConfigurations);
        });

        voiceSample3Button.setOnClickListener(v -> {
            voiceProfileConfigurations = getVoiceDataFromUI();
            presenter.onTestVoiceProfile(
                    getSampleString(String.valueOf(voiceSample3Button.getText())), voiceProfileConfigurations);
        });

        saveVoiceProfileButton.setOnClickListener(v -> confirmSaveDialog());

        return view;
    }

    private void confirmSaveDialog() {
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(50, 0, 50, 0);
        EditText textBox = new EditText(getContext());
        textBox.setHint("BUZZ LIGHTYEAR");
        layout.addView(textBox, params);
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setIcon(R.drawable.temp_acorn);
        alert.setTitle(getResources().getString(R.string.save_voice_profile_title));
        alert.setMessage(getResources().getString(R.string.save_voice_profile_message));
        alert.setView(layout);
        alert.setPositiveButton(getResources().getString(R.string.yes), (dialog, whichButton) -> {
            String inputVoiceProfileName = textBox.getText().toString().toUpperCase();
            if (isVoiceProfileNameOkay(inputVoiceProfileName)) {
                voiceProfileConfigurations = getVoiceDataFromUI();
                voiceProfileConfigurations.setVoiceProfileName(inputVoiceProfileName);
                presenter.onSaveVoiceProfile(voiceProfileConfigurations);
                Objects.requireNonNull(getActivity()).onBackPressed();
            } else {
                Toast.makeText(getContext(), getResources().getString(R.string.save_voice_profile_error), Toast.LENGTH_LONG).show();
            }
        });
        alert.setNegativeButton(getResources().getString(R.string.no), (dialog, whichButton) -> {});
        alert.show();
    }

    private boolean isVoiceProfileNameOkay(String requestedName) {
        ArrayList<String> databaseVoiceProfileNames = voiceProfileDAO.getAllVoiceProfileNames();
        // check if name exists in the database
        for (String existingName : databaseVoiceProfileNames) {
            if (existingName.equals(requestedName)) {
                return false;
            }
        }
        return true;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void initToggles() {

    }

    @Override
    public void handleSpeakerToggles(boolean toggleGirl) {
        if (toggleGirl) {
            voiceGirlToggle.setChecked(true);
            voiceBoyToggle.setChecked(false);
        } else {
            voiceGirlToggle.setChecked(false);
            voiceBoyToggle.setChecked(true);
        }
    }

    @Override
    public void handleEmotionToggles(String emotionToToggle) {
        disableAllEmotionToggles();
        disableEmotionIntensity(false);
        if (emotionToToggle.equalsIgnoreCase("HAPPY")) {
            emotionHappyToggle.setChecked(true);
        } else if (emotionToToggle.equalsIgnoreCase("SAD")) {
            emotionSadToggle.setChecked(true);
        } else if (emotionToToggle.equalsIgnoreCase("ANGRY")) {
            emotionAngryToggle.setChecked(true);
        } else {
            emotionNoneToggle.setChecked(true);
            disableEmotionIntensity(true);
        }
    }

    private void disableEmotionIntensity(boolean shouldDisable) {
        emotionSeekbar.setEnabled(!shouldDisable);
    }

    private void disableAllEmotionToggles() {
        emotionNoneToggle.setChecked(false);
        emotionHappyToggle.setChecked(false);
        emotionSadToggle.setChecked(false);
        emotionAngryToggle.setChecked(false);
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

    private String getSampleString(String sampleStringFromButton) {
        if (sampleStringFromButton.equalsIgnoreCase("GREETING")) {
            return getResources().getString(R.string.test_voice_1_sample);
        } else if (sampleStringFromButton.equalsIgnoreCase("GOOD MORNING")) {
            return getResources().getString(R.string.test_voice_2_sample);
        } else {
            return getResources().getString(R.string.test_voice_3_sample);
        }
    }

    private VoiceProfile getVoiceDataFromUI() {
        voiceProfileConfigurations.setSpeaker(getToggledSpeaker());
        voiceProfileConfigurations.setEmotion(getToggledEmotion());
        voiceProfileConfigurations.setEmotion_level(presenter.seekbarToEmotionLevel(emotionSeekbar.getProgress()));
        voiceProfileConfigurations.setPitch(presenter.seekbarToPitchValue(pitchSeekbar.getProgress()));
        voiceProfileConfigurations.setSpeed(presenter.seekbarToSpeedValue(speedSeekbar.getProgress()));
        voiceProfileConfigurations.setVolume(presenter.seekbarToVolumeValue(volumeSeekbar.getProgress()));
        return voiceProfileConfigurations;
    }

    private String getToggledSpeaker() {
        if (voiceGirlToggle.isChecked()) {
            return "haruka";
        } else {
            return "takeru";
        }
    }

    private String getToggledEmotion() {
        if (emotionNoneToggle.isChecked()) {
            return null;
        } else if (emotionHappyToggle.isChecked()) {
            return "happiness";
        } else if (emotionSadToggle.isChecked()) {
            return "sadness";
        } else {
            return "anger";
        }
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
