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
    private ArrayList<VoiceProfile> voiceProfileArrayList;

    public VoiceProfilesRecyclerViewAdapter(Context context, ArrayList<VoiceProfile> voiceProfilesList) {
        this.context = context;
        this.voiceProfileArrayList = voiceProfilesList;
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
        holder.voiceProfileText.setText(voiceProfileArrayList.get(position).getVoiceProfileName());
        holder.deleteVoiceProfileButton.setOnClickListener(v -> {
            Log.d(TAG, "onBindViewHolder: " + position);
            voiceProfileDAO.deleteVoiceProfile(voiceProfileArrayList.get(position).getVoiceProfileName());
            voiceProfileArrayList = voiceProfileDAO.getAllVoiceProfiles();
            refresh(voiceProfileArrayList);
        });
        holder.parentLayout.setOnClickListener(v -> {
//            ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
//            .replace(R.id.container, new AddVoiceProfileFragment(new SpeechUtil(context)))
//            .addToBackStack(null)
//            .commit();
        });

        VoiceProfile currentVoiceProfile = voiceProfileArrayList.get(position);
        if (currentVoiceProfile.getSpeaker().equalsIgnoreCase("Haruka")) {
            if (currentVoiceProfile.getEmotion() != null) {
                if (currentVoiceProfile.getEmotion().equalsIgnoreCase("happiness")) {
                    holder.voiceProfileImage.setImageResource(R.drawable.girl_happy);
                } else if (currentVoiceProfile.getEmotion().equalsIgnoreCase("sadness")) {
                    holder.voiceProfileImage.setImageResource(R.drawable.girl_sad);
                } else if (currentVoiceProfile.getEmotion().equalsIgnoreCase("anger")) {
                    holder.voiceProfileImage.setImageResource(R.drawable.girl_angry);
                }
            } else {
                holder.voiceProfileImage.setImageResource(R.drawable.girl_no_emotion);
            }
        } else {
            if (currentVoiceProfile.getEmotion() != null) {
                if (currentVoiceProfile.getEmotion().equalsIgnoreCase("happiness")) {
                    holder.voiceProfileImage.setImageResource(R.drawable.boy_happy);
                } else if (currentVoiceProfile.getEmotion().equalsIgnoreCase("sadness")) {
                    holder.voiceProfileImage.setImageResource(R.drawable.boy_sad);
                } else if (currentVoiceProfile.getEmotion().equalsIgnoreCase("anger")) {
                    holder.voiceProfileImage.setImageResource(R.drawable.boy_angry);
                }
            } else {
                holder.voiceProfileImage.setImageResource(R.drawable.boy_no_emotion);
            }
        }
    }

    @Override
    public int getItemCount() {
        return voiceProfileArrayList.size();
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
            voiceProfileArrayList = voiceProfiles;
            notifyDataSetChanged();
        });
    }
}
