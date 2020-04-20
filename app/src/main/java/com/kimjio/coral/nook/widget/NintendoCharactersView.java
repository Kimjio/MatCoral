package com.kimjio.coral.nook.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.kimjio.coral.R;
import com.kimjio.coral.databinding.NookCharsItemBinding;
import com.kimjio.coral.recycler.OnItemClickListener;
import com.kimjio.coral.util.ViewUtils;


public class NintendoCharactersView extends MaterialCardView {

    public static final char[] chars = {'Ǻ', '★', '☆', '♠', '♡', '♢', '♣', '♤', '♥', '♦', '♧', '♪',
            '\uE0A0', '\uE0A1', '\uE0A2', '\uE0A3', '\uE0A4',
            '\uE0A5', '\uE0A6', '\uE0A7', '\uE0A8', '\uE0A9',
            '\uE0AA', '\uE0AB', '\uE0AC', '\uE0AD', '\uE0AE',
            '\uE0AF', '\uE0B0', '\uE0B1', '\uE0B2', '\uE0B3',
            '\uE0B4', '\uE0B5', '\uE0B6', '\uE0B7', '\uE0B8',
            '\uE0B9', '\uE0BA', '\uE0C0', '\uE0C1', '\uE0C2',
            '\uE0C3', '\uE0C4', '\uE0C5', '\uE0C6', '\uE0C7',
            '\uE0C8', '\uE0C9', '\uE0CA', '\uE0CB', '\uE0CC',
            '\uE0CD', '\uE0CE', '\uE0CF', '\uE0D0', '\uE0D1',
            '\uE0D2', '\uE0D3', '\uE0D4', '\uE0D5', '\uE0D6',
            '\uE0E0', '\uE0E1', '\uE0E2', '\uE0E3', '\uE0E4',
            '\uE0E5', '\uE0E6', '\uE0E7', '\uE0E8', '\uE0E9',
            '\uE0EA', '\uE0EB', '\uE0EC', '\uE0ED', '\uE0EE',
            '\uE0EF', '\uE0F0', '\uE0F1', '\uE0F2', '\uE0F3',
            '\uE0F4', '\uE0F5', '\uE100', '\uE101', '\uE102',
            '\uE103', '\uE104', '\uE105', '\uE110', '\uE111',
            '\uE112', '\uE113', '\uE114', '\uE115', '\uE116',
            '\uE121', '\uE122', '\uE123', '\uE124', '\uE125',
            '\uE126', '\uE127', '\uE128', '\uE129', '\uE12A',
            '\uE12B', '\uE12C', '\uE130', '\uE131', '\uE132',
            '\uE133', '\uE134', '\uE135', '\uE136', '\uE137',
            '\uE138', '\uE139', '\uE13A', '\uE13B', '\uE13C',
            '\uE140', '\uE141', '\uE142', '\uE143', '\uE144',
            '\uE145', '\uE146', '\uE147', '\uE148', '\uE149',
            '\uE14A', '\uE14B', '\uE14C', '\uE14D', '\uE150',
            '\uE151', '\uE152', '\uE153', '\uE200', '\uE201',
            '\uE202', '\uE203', '\uE204', '\uE205', '\uE206',
            '\uE207', '\uE208', '\uE209', '\uE20A', '\uE20B',
            '\uE20C', '\uE20D', '\uE20E', '\uE20F', '\uE210',
            '\uE211', '\uE212', '\uE213', '\uE214', '\uE215',
            '\uE216', '\uE217', '\uE218', '\uE219', '\uE21A',
            '\uE21B', '\uE21C', '\uE21D', '\uE21E', '\uE21F',
            '\uE220', '\uE221', '\uE222', '\uE223', '\uE224',
            '\uE225', '\uE226', '\uE227', '\uE228', '\uE229',
            '\uE22A', '\uE22B', '\uE230', '\uE231', '\uE232',
            '\uE233', '\uE234', '\uE235', '\uE236', '\uE237',
            '\uE238', '\uE239', '\uE23A', '\uE23B', '\uE23C',
            '\uE23D', '\uE23E', '\uE23F', '\uE240', '\uE241',
            '\uE242', '\uE243', '\uE244', '\uE245', '\uE246',
            '\uE247', '\uE248', '\uE249', '\uE24A', '\uE24B'
    };

    private OnItemClickListener<CharSequence> onItemClickListener;

    private OnClickListener onCloseClickListener;

    public NintendoCharactersView(Context context) {
        this(context, null);
    }

    public NintendoCharactersView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NintendoCharactersView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        LinearLayout linearLayout = new LinearLayout(context, attrs, defStyle);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        AppCompatImageButton imageButton = new AppCompatImageButton(context, attrs, defStyle);
        imageButton.setImageResource(R.drawable.ic_close);
        int closeSize = ViewUtils.dpToPx(context, 32);
        LinearLayout.LayoutParams paramsImage = new LinearLayout.LayoutParams(closeSize, closeSize);
        int paddingImage = ViewUtils.dpToPx(context, 4);
        imageButton.setPaddingRelative(paddingImage, paddingImage, paddingImage, paddingImage);
        paramsImage.gravity = GravityCompat.END;
        imageButton.setLayoutParams(paramsImage);
        imageButton.setOnClickListener(v -> {
            if (onCloseClickListener != null)
                onCloseClickListener.onClick(v);
        });
        linearLayout.addView(imageButton);

        RecyclerView recyclerView = new RecyclerView(context, attrs, defStyle);
        recyclerView.setAdapter(new Adapter());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 5));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        recyclerView.setLayoutParams(params);
        linearLayout.addView(recyclerView);

        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(linearLayout);
    }

    public void setOnItemClickListener(OnItemClickListener<CharSequence> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnCloseClickListener(OnClickListener onCloseClickListener) {
        this.onCloseClickListener = onCloseClickListener;
    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.nook_chars_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.binding.setCharacter(chars[position]);
            holder.itemView.setOnClickListener(view -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(holder.binding.character.getText(), position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return chars.length;
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        NookCharsItemBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}