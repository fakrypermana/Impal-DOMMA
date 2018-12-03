package com.id.start.domma_v2.adapter;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
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
import com.id.start.domma_v2.model.Transaksi;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class TransaksiAdapter extends RecyclerView.Adapter<TransaksiAdapter.ViewHolder> {
    private List<Transaksi> listTransaksi = new ArrayList<>();
    private ClickListener listener;

    public TransaksiAdapter(List<Transaksi> listTransaksi, ClickListener listener) {
        this.listTransaksi = listTransaksi;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TransaksiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_transaksi, parent, false);
        return new ViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvNama.setText(listTransaksi.get(position).getNama());
        holder.tvDate.setText(listTransaksi.get(position).getTgl_transaki());
        holder.tvMount.setText(String.valueOf(listTransaksi.get(position).getMount()));
        if (listTransaksi.get(position).getTipe() == 0){
            holder.tvJenis.setText("Expense");
        } else{
            holder.tvJenis.setText("Income");
        }

        String firstLet = listTransaksi.get(position).getNama();
        if (firstLet.length()>0){
            String first = firstLet.substring(0,1);
            ColorGenerator colorGenerator = ColorGenerator.MATERIAL;
            int color = colorGenerator.getRandomColor();
            TextDrawable textDrawable = TextDrawable.builder().beginConfig()
                    .toUpperCase()
                    .bold()
                    .endConfig().buildRound(first, color);
            holder.ivTransaksi.setImageDrawable(textDrawable);
        }

    }

    @Override
    public int getItemCount() {
        return listTransaksi.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvDate, tvNama, tvMount, tvJenis;
        private ImageView ivTransaksi;
        private CardView cvTransaksi;
        private WeakReference<ClickListener> listenerRef;

        public ViewHolder(View itemView, ClickListener listener) {
            super(itemView);

            listenerRef = new WeakReference<>(listener);
            tvDate = itemView.findViewById(R.id.tv_date_transaksi_menu);
            tvMount = itemView.findViewById(R.id.tv_mount_transaksi_menu);
            tvNama = itemView.findViewById(R.id.tv_nama_transaksi_menu);
            cvTransaksi = itemView.findViewById(R.id.cv_transaksi_menu);
            ivTransaksi = itemView.findViewById(R.id.iv_transaksi_menu);
            tvJenis = itemView.findViewById(R.id.tv_jenis_transaksi);

            cvTransaksi.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            listenerRef.get().onPositionClicked(getAdapterPosition());
        }
    }
}
