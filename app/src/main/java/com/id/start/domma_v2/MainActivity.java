package com.id.start.domma_v2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.id.start.domma_v2.adapter.TransaksiAdapter;
import com.id.start.domma_v2.fitur.DetailTransaksiActivity;
import com.id.start.domma_v2.fitur.LoginActivity;
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
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "fakuy";

    //navdrawer
    private DrawerLayout drawerLayout;

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

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
        drawerLayout = findViewById(R.id.drawer_layout);

        //setup data

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null){
                     startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            }
        };
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        
                        }

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Domma");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_24dp);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryLight));

        adapter = new TransaksiAdapter(listTransaksi, new ClickListener() {
            @Override
            public void onPositionClicked(int position) {
                
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
                                transaksi1.setKey(document.getId());

                                Log.d(TAG, "isi key "+document.getId());
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void hitungTransaksi(){
        Log.d(TAG, "tracing list transaksi: " + listTransaksi.size());
        for (int i = 0; i < listTransaksi.size(); i++) {
            if (listTransaksi.get(i).getTipe() == 0){
                if (listTransaksiExpense.size() > 0) {
                    jmlExpense = jmlExpense + listTransaksi.get(i).getMount();
                    jmlSisaUang = jmlSisaUang - listTransaksi.get(i).getMount();
                } else {
                    jmlExpense = 0;
                }
            } else {
                if (listTransaksiIncome.size() > 0){
                    jmlIncome = jmlIncome + listTransaksi.get(i).getMount();
                    jmlSisaUang = jmlSisaUang + listTransaksi.get(i).getMount();
                } else{
                    jmlIncome = 0;
                }
            }
//            Log.d(TAG, "jumlah expense: "+jmlIncome);
            tvExpense.setText(jmlExpense.toString());
            tvIncome.setText(jmlIncome.toString());
            tvSisaUang.setText(jmlSisaUang.toString());
        }
    }
}
