package com.example.omocha;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.omocha.DashboardUI.DashboardFragment;
import com.example.omocha.Fragments.CreateNewSpeech.CreateNewSpeechFragment;
import com.example.omocha.Fragments.CreateVoiceProfile.CreateVoiceProfileFragment;
import com.example.omocha.Fragments.LiveChat.LiveChatFragment;
import com.example.omocha.Fragments.Playback.PlaybackFragment;
import com.example.omocha.Fragments.Settings.SettingsFragment;
import com.example.omocha.Fragments.SplashScreen.SplashScreenFragment;
import com.example.omocha.Fragments.UseYourVoice.UseYourVoiceFragment;


public class MainActivity extends AppCompatActivity implements MainContract.View{

    private static final String TAG = "MainActivityTAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        showSplashFragment();
        showDashboardFragment();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showSplashFragment() {
        showFragment(new SplashScreenFragment(), true);
    }

    @Override
    public void showDashboardFragment() {
        showFragment(new DashboardFragment(this), false);
    }

    @Override
    public void showCreateVoiceProfileFragment() {
        showFragment(new CreateVoiceProfileFragment(), true);
    }

    @Override
    public void showCreateNewSpeechFragment() {
        showFragment(new CreateNewSpeechFragment(), true);
    }

    @Override
    public void showPlaybackFragment() {
        showFragment(new PlaybackFragment(), true);
    }

    @Override
    public void showLiveChatFragment() {
        showFragment(new LiveChatFragment(), true);
    }

    @Override
    public void showUseYourVoiceFragment() {
        showFragment(new UseYourVoiceFragment(), true);
    }

    @Override
    public void showSettingsFragment() {
        showFragment(new SettingsFragment(), true);
    }


    private void showFragment(Fragment fragment, boolean canGoBack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        if (canGoBack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

}
