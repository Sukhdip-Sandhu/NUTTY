package com.example.omocha.Adapters;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omocha.Models.VoiceProfile;
import com.example.omocha.Models.VoiceProfileDAO;
import com.example.omocha.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VoiceProfilesRecyclerViewAdapter  extends RecyclerView.Adapter<VoiceProfilesRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "VoiceProfilesTAG";
    private Context context;
    private VoiceProfileDAO voiceProfileDAO;
    private ArrayList<VoiceProfile> voiceProfilesList;

    public VoiceProfilesRecyclerViewAdapter(Context context, ArrayList<VoiceProfile> voiceProfilesList) {
        this.context = context;
        this.voiceProfilesList = voiceProfilesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.layout_voice_profiles, parent, false);
        voiceProfileDAO = new VoiceProfileDAO(context);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.voiceProfileText.setText(voiceProfilesList.get(position).getVoiceProfileName());
        holder.deleteVoiceProfileButton.setOnClickListener(v -> {
            Log.d(TAG, "onBindViewHolder: " + position);
            voiceProfileDAO.deleteVoiceProfile(voiceProfilesList.get(position).getVoiceProfileName());
            voiceProfilesList = voiceProfileDAO.getAllVoiceProfiles();
            refresh(voiceProfilesList);
        });
        holder.parentLayout.setOnClickListener(v -> {
            Log.d(TAG, "clicked on " + position);
        });
    }

    @Override
    public int getItemCount() {
        return voiceProfilesList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.voice_profile_image)
        ImageView voiceProfileImage;

        @BindView(R.id.voice_profile_text)
        TextView voiceProfileText;

        @BindView(R.id.delete_voice_profile_button)
        Button deleteVoiceProfileButton;

        @BindView(R.id.layout_voice_profiles_parent)
        RelativeLayout parentLayout;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void refresh(final ArrayList<VoiceProfile> voiceProfiles) {
        Handler refresh = new Handler(Looper.getMainLooper());
        refresh.post(() -> {
            voiceProfilesList = voiceProfiles;
            notifyDataSetChanged();
        });
    }
}
