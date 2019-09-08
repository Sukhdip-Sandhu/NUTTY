package com.example.omocha.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omocha.Models.VoiceProfile;
import com.example.omocha.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HVoiceProfilesRecyclerViewAdapter extends RecyclerView.Adapter<HVoiceProfilesRecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "newspeechtag";
    private ArrayList<VoiceProfile> voiceProfileArrayList = new ArrayList<>();
    private Context context;
    private int currentHighlightedVoiceProfile = 0;

    public HVoiceProfilesRecyclerViewAdapter(Context context, ArrayList<VoiceProfile> voiceProfileArrayList) {
        this.context = context;
        this.voiceProfileArrayList = voiceProfileArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_horizontal_voice_profiles, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.voiceProfileText.setText(voiceProfileArrayList.get(position).getVoiceProfileName());
        if (currentHighlightedVoiceProfile == position) {
            holder.parentLayout.setBackgroundColor(context.getResources().getColor(R.color.colorAccentTransparent));
        } else {
            holder.parentLayout.setBackgroundColor(context.getResources().getColor(R.color.transparent));
        }

        holder.parentLayout.setOnClickListener(v -> {
            Log.d(TAG, "clicked on : " + voiceProfileArrayList.get(position).getVoiceProfileName());
            currentHighlightedVoiceProfile = position;
            notifyDataSetChanged();
        });
    }

    public int getCurrentHighlightedVoiceProfile() {
        return currentHighlightedVoiceProfile;
    }

    @Override
    public int getItemCount() {
        return voiceProfileArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.horizontal_voice_profile_image)
        ImageView voiceProfileImage;

        @BindView(R.id.horizontal_voice_profile_name)
        TextView voiceProfileText;

        @BindView(R.id.horizontal_recycler_view_parent)
        RelativeLayout parentLayout;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
