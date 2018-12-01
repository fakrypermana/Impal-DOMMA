package com.id.start.domma_v2.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.id.start.domma_v2.R;

class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView nama;

    public RecyclerViewHolders(View itemView) {
        super(itemView);

        itemView.setOnClickListener(this);
        nama = itemView.findViewById(R.id.tv_kategori);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(), "Position = " + getPosition(), Toast.LENGTH_SHORT).show();
    }
}
