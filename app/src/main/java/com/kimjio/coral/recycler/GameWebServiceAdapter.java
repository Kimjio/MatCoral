package com.kimjio.coral.recycler;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kimjio.coral.R;
import com.kimjio.coral.data.GameWebService;

import java.util.List;

public class GameWebServiceAdapter extends RecyclerView.Adapter<GameWebServiceViewHolder> {

    private List<GameWebService> gameWebServices;
    private OnItemClickListener<GameWebService> onItemClickListener;

    public GameWebServiceAdapter(OnItemClickListener<GameWebService> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public GameWebServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GameWebServiceViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.game_web_service_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GameWebServiceViewHolder holder, int position) {
        GameWebService gameWebService = gameWebServices.get(position);
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(gameWebService, position);
        });
        holder.binding.setItem(gameWebService);
    }

    public void setGameWebServices(List<GameWebService> gameWebServices) {
        this.gameWebServices = gameWebServices;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (gameWebServices == null) return 0;
        return gameWebServices.size();
    }
}
