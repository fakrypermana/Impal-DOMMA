package com.id.start.domma_v2.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.id.start.domma_v2.R;
import com.id.start.domma_v2.model.kategori;

import java.util.List;

public class KategoriAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {

    private List<kategori> itemList;
    private Context context;

    public KategoriAdapter(List<kategori> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolders onCreateViewHolder( ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kategori, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder( RecyclerViewHolders holder, int position) {
        h
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
