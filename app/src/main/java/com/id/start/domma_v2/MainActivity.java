package com.id.start.domma_v2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.id.start.domma_v2.adapter.TransaksiAdapter;
import com.id.start.domma_v2.fitur.DetailTransaksiActivity;
import com.id.start.domma_v2.fitur.TransaksiActivity;
import com.id.start.domma_v2.model.Kategori;
import com.id.start.domma_v2.model.Transaksi;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fab;
    private RecyclerView rvTransaksi;
    private TextView tvIncome,tvExpense,tvSisaUang;
    Toolbar toolbar;
    ProgressBar progressBar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<Transaksi> listTransaksi = new ArrayList<>();
    List<Transaksi> listTransaksiIncome = new ArrayList<>();
    List<Transaksi> listTransaksiExpense = new ArrayList<>();
    TransaksiAdapter adapter;
    Integer jmlSisaUang = 0;
    Integer jmlExpense = 0;
    Integer jmlIncome = 0;
    private static final String TAG = "fakuy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setup view
        fab = findViewById(R.id.fab_transaksi);
        rvTransaksi = findViewById(R.id.rv_transaksi);
        toolbar = findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progress_bar_get_transaksi);
        progressBar.setVisibility(View.VISIBLE);
        tvSisaUang = findViewById(R.id.tv_total_sisa_uang);
        tvIncome = findViewById(R.id.tv_total_income);
        tvExpense = findViewById(R.id.tv_total_expense);

        //setup data
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TransaksiActivity.class);
                startActivity(intent);
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Domma");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryLight));

        adapter = new TransaksiAdapter(listTransaksi, new ClickListener() {
            @Override
            public void onPositionClicked(int position) {
                Intent intent = new Intent(getApplicationContext(),DetailTransaksiActivity.class);
                if (listTransaksi.get(position).getTipe() == 0){
                    intent.putExtra("jenis", "expense");
                    intent.putExtra("mount", listTransaksi.get(position).getMount());
                    intent.putExtra("date",listTransaksi.get(position).getTgl_transaki());
                    intent.putExtra("nama",listTransaksi.get(position).getNama());
                }
                startActivity(intent);
            }

            @Override
            public void onLongClicked(int position) {

            }
        });

        rvTransaksi.setHasFixedSize(true);
        rvTransaksi.setLayoutManager(new LinearLayoutManager(this));
        rvTransaksi.setItemAnimator(new DefaultItemAnimator());
        rvTransaksi.setAdapter(adapter);
        rvTransaksi.setNestedScrollingEnabled(false);

        db.collection("transaksi")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            listTransaksiIncome.clear();
                            listTransaksiExpense.clear();
                            listTransaksi.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Transaksi transaksi1 = document.toObject(Transaksi.class);
                                /*Log.d(TAG, "get kategori di transaksi " + kategori1.getNama());
                                Log.d(TAG, document.getId() + " => " + document.getData());*/
                                if (transaksi1.getTipe() == 0){
                                    listTransaksiExpense.add(transaksi1);
                                    Log.d(TAG, "isi transaksi Expense di main"+listTransaksiExpense.size());
                                } else{
                                    listTransaksiIncome.add(transaksi1);
                                    Log.d(TAG, "isi transaksi Income di main"+listTransaksiIncome.size());
                                }
                                listTransaksi.add(transaksi1);

                                Log.d(TAG, "isi transaksi di main"+listTransaksi.size());
                            }
                            adapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                            hitungTransaksi();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    public void hitungTransaksi(){
        Log.d(TAG, "tracing list transaksi: " + listTransaksi.size());
        for (int i = 0; i < listTransaksi.size(); i++) {
            if (listTransaksi.get(i).getTipe() == 0){
                if (listTransaksiExpense.size() > 0){
                    for (int j = 0; j < listTransaksiExpense.size(); j++) {
                        jmlExpense = jmlExpense + listTransaksiExpense.get(j).getMount();
                        jmlSisaUang = jmlSisaUang - listTransaksiExpense.get(j).getMount();
                    }
                }
            } else {
                if (listTransaksiIncome.size() > 0){
                    for (int j = 0; j < listTransaksiIncome.size(); j++) {
                        jmlIncome = jmlIncome + listTransaksiIncome.get(j).getMount();
                        jmlSisaUang = jmlSisaUang + listTransaksiIncome.get(j).getMount();
                    }
                }
            }
//            Log.d(TAG, "jumlah expense: "+jmlIncome);
            tvExpense.setText(jmlExpense.toString());
            tvIncome.setText(jmlIncome.toString());
            tvSisaUang.setText(jmlSisaUang.toString());
        }
    }
}
