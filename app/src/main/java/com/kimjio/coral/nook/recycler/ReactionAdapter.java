package com.kimjio.coral.nook.recycler;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kimjio.coral.R;
import com.kimjio.coral.data.nook.Reaction;
import com.kimjio.coral.recycler.OnItemClickListener;

import java.util.List;

public class ReactionAdapter extends RecyclerView.Adapter<ReactionViewHolder> {
    private List<Reaction> reactions;
    private OnItemClickListener<Reaction> onItemClickListener;

    public ReactionAdapter(OnItemClickListener<Reaction> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ReactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReactionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.reaction_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReactionViewHolder holder, int position) {
        Reaction reaction = reactions.get(position);
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(reaction, position);
        });
        holder.binding.setReaction(reaction);
    }

    public void setReactions(List<Reaction> reactions) {
        this.reactions = reactions;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (reactions == null) return 0;
        return reactions.size();
    }
}
