package com.kimjio.coral.nook.recycler;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kimjio.coral.databinding.ReactionItemBinding;

public class ReactionViewHolder extends RecyclerView.ViewHolder {
    ReactionItemBinding binding;

    public ReactionViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }
}
