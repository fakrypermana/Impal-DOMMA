package com.id.start.domma_v2.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.id.start.domma_v2.ClickListener;
import com.id.start.domma_v2.R;
import com.id.start.domma_v2.model.Kategori;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class KategoriAdapter extends RecyclerView.Adapter<KategoriAdapter.ViewHolder> {
    private List<Kategori> listKategori = new ArrayList<>();
    private ClickListener listener;

    public KategoriAdapter(List<Kategori> listKategori, ClickListener listener) {
        this.listKategori = listKategori;
        this.listener = listener;
    }

    @NonNull
    @Override
    public KategoriAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_kategori, parent, false);
        return new ViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String firstLet = listKategori.get(position).getNama();
        String first = firstLet.substring(0,1);
        ColorGenerator colorGenerator = ColorGenerator.MATERIAL;
        int color = colorGenerator.getRandomColor();
        TextDrawable textDrawable = TextDrawable.builder().beginConfig()
                .toUpperCase()
                .bold()
                .endConfig().buildRound(first, color);
        TextDrawable textDrawable2 = TextDrawable.builder()
                .beginConfig()
                .textColor(Color.RED)
                .endConfig()
                .buildRound(listKategori.get(position).getNama().substring(0,1),Color.WHITE);

        holder.tvKategori.setText(listKategori.get(position).getNama());
        holder.ivKategori.setImageDrawable(textDrawable);
    }

    @Override
    public int getItemCount() {
        return listKategori.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener, View.OnLongClickListener {
        private TextView tvKategori;
        private ImageView ivKategori;
        private WeakReference<ClickListener> listenerRef;
        private List<Kategori> listKategori = new ArrayList<>();
        TextDrawable textDrawable2;

        public ViewHolder(View itemView, ClickListener listener) {
            super(itemView);
            listenerRef = new WeakReference<>(listener);
            for (int i = 0; i < listKategori.size(); i++) {
                        textDrawable2 = TextDrawable.builder()
                        .beginConfig()
                        .textColor(Color.RED)
                        .endConfig()
                        .buildRound(listKategori.get(i).getNama().substring(0,1),Color.WHITE);
            }

            tvKategori = itemView.findViewById(R.id.tv_kategori);
            ivKategori = itemView.findViewById(R.id.iv_kategori);

            ivKategori.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listenerRef.get().onPositionClicked(getAdapterPosition());
//            ivKategori.setImageDrawable(textDrawable2);
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}
