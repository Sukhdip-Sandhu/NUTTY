package com.example.omocha;

import android.os.Bundle;
import android.os.Environment;

import android.content.pm.PackageManager;
import android.Manifest;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.omocha.DashboardUI.DashboardFragment;
import com.example.omocha.Fragments.CreateNewSpeech.CreateNewSpeechFragment;
import com.example.omocha.Fragments.CreateVoiceProfile.CreateVoiceProfileFragment;
import com.example.omocha.Fragments.LiveChat.LiveChatFragment;
import com.example.omocha.Fragments.SavedSpeeches.SavedSpeechesFragment;
import com.example.omocha.Fragments.Settings.SettingsFragment;
import com.example.omocha.Fragments.SplashScreen.SplashScreenFragment;
import com.example.omocha.Fragments.UseYourVoice.UseYourVoiceFragment;
import com.example.omocha.Util.SpeechUtil;


public class MainActivity extends AppCompatActivity implements MainContract.View{

    private static final String TAG = "MainActivityTAG";
    public static final String SPEECH_DIRECTORY = Environment.getExternalStorageDirectory()
            + "/SpeechFiles/";
    public static final String TEMP_SPEECH_PATH = Environment.getExternalStorageDirectory()
            + "/SpeechFiles/tmp.wav";
    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;
    private static final String[] REQUIRED_SDK_PERMISSIONS = new String[] {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermissions();
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
    public void showCreateVoiceProfileFragment(SpeechUtil speechUtil) {
        showFragment(new CreateVoiceProfileFragment(speechUtil), true);
    }

    @Override
    public void showCreateNewSpeechFragment(SpeechUtil speechUtil) {
        showFragment(new CreateNewSpeechFragment(speechUtil), true);
    }

    @Override
    public void showSavedSpeechesFragment(SpeechUtil speechUtil) {
        showFragment(new SavedSpeechesFragment(speechUtil), true);
    }

    @Override
    public void showLiveChatFragment(SpeechUtil speechUtil) {
        showFragment(new LiveChatFragment(speechUtil), true);
    }

    @Override
    public void showUseYourVoiceFragment(SpeechUtil speechUtil) {
        showFragment(new UseYourVoiceFragment(speechUtil), true);
    }

    @Override
    public void showSettingsFragment() {
        showFragment(new SettingsFragment(), true);
    }

    /**
     * Checks the dynamically-controlled permissions and requests missing permissions from end user.
     */
    protected void checkPermissions() {
        final List<String> missingPermissions = new ArrayList<String>();
        // check all required dynamic permissions
        for (final String permission : REQUIRED_SDK_PERMISSIONS) {
            final int result = ContextCompat.checkSelfPermission(this, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission);
            }
        }
        if (!missingPermissions.isEmpty()) {
            // request all missing permissions
            final String[] permissions = missingPermissions
                    .toArray(new String[missingPermissions.size()]);
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            final int[] grantResults = new int[REQUIRED_SDK_PERMISSIONS.length];
            Arrays.fill(grantResults, PackageManager.PERMISSION_GRANTED);
            onRequestPermissionsResult(REQUEST_CODE_ASK_PERMISSIONS, REQUIRED_SDK_PERMISSIONS,
                    grantResults);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                for (int index = permissions.length - 1; index >= 0; --index) {
                    if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                        // exit the app if one permission is not granted
                        Toast.makeText(this, "Required permission '" + permissions[index]
                                + "' not granted, exiting", Toast.LENGTH_LONG).show();
                        finish();
                        return;
                    }
                }
                createDirIfNotExist(SPEECH_DIRECTORY);
                // all permissions were granted
                break;
        }
    }


    private void createDirIfNotExist(String dir) {
        File directory = new File(dir);
        if (!directory.exists()) {
            boolean mkdir = directory.mkdir();
            if (!mkdir) {
                Log.d(TAG, "error creating directory");
            }
        }
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
