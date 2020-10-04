package com.kimjio.coral.recycler;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kimjio.coral.databinding.GameWebServiceItemBinding;

public class GameWebServiceViewHolder extends RecyclerView.ViewHolder {
    GameWebServiceItemBinding binding;

    public GameWebServiceViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }
}
