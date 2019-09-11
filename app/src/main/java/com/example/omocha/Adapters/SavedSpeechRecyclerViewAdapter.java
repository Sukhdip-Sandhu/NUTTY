package com.example.omocha.Adapters;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omocha.Models.SavedSpeech;
import com.example.omocha.Models.SavedSpeechDAO;
import com.example.omocha.Models.VoiceProfile;
import com.example.omocha.R;
import com.example.omocha.Util.SpeechUtil;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedSpeechRecyclerViewAdapter extends RecyclerView.Adapter<SavedSpeechRecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "SavedSpeechRecyclerView";
    private Context context;
    private ArrayList<SavedSpeech> savedSpeechArrayList;
    SpeechUtil speechUtil;
    SavedSpeechDAO savedSpeechDAO;

    public SavedSpeechRecyclerViewAdapter(Context context, ArrayList<SavedSpeech> savedSpeechArrayList, SpeechUtil speechUtil) {
        this.context = context;
        this.savedSpeechArrayList = savedSpeechArrayList;
        this.speechUtil = speechUtil;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.layout_saved_speeches, parent, false);
        savedSpeechDAO = new SavedSpeechDAO(context);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.savedSpeechTitle.setText(savedSpeechArrayList.get(position).getSpeechTitle());
        holder.deleteSavedSpeechButton.setOnClickListener(v -> {
            Log.d(TAG, "onBindViewHolder: " + position);
            savedSpeechDAO.deleteSavedSpeech(savedSpeechArrayList.get(position).getId());
            removeFileFromSDCard(savedSpeechArrayList.get(position).getSpeechPath());
            savedSpeechArrayList = savedSpeechDAO.getAllSpeeches();
            refresh(savedSpeechArrayList);
        });
        holder.parentLayout.setOnClickListener(v -> {
            Log.d(TAG, "onBindViewHolder: ");
            String speechPath = savedSpeechArrayList.get(position).getSpeechPath();
            speechUtil.saySpeech(speechPath);
        });

    }

    private void removeFileFromSDCard(String speechPath) {
        File fileToDelete = new File(speechPath);
        if (fileToDelete.exists()) {
            fileToDelete.delete();
        }
    }

    @Override
    public int getItemCount() {
        return savedSpeechArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.saved_speech_title)
        TextView savedSpeechTitle;

        @BindView(R.id.delete_saved_speech_button)
        Button deleteSavedSpeechButton;

        @BindView(R.id.save_speech_profiles_parent)
        RelativeLayout parentLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void refresh(final ArrayList<SavedSpeech> savedSpeeches) {
        Handler refresh = new Handler(Looper.getMainLooper());
        refresh.post(() -> {
            savedSpeechArrayList = savedSpeeches;
            notifyDataSetChanged();
        });
    }

}
