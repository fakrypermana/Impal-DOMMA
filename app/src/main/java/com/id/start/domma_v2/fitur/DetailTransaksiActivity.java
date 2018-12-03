package com.id.start.domma_v2.fitur;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.id.start.domma_v2.MainActivity;
import com.id.start.domma_v2.R;
import com.id.start.domma_v2.model.Transaksi;

import java.util.ArrayList;
import java.util.List;

public class DetailTransaksiActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView tvJenis,tvJumlah, tvTanggal, tvNama;
    private static final String TAG ="fakuy";
    List<Transaksi> listTransaksi = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaksi);

        //setup view
        

        //setup data
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if(bd != null)
        {

            
        }

        db.collection("transaksi")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            listTransaksi.clear();
                            listTransaksi.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Transaksi transaksi1 = document.toObject(Transaksi.class);
                                /*Log.d(TAG, "get kategori di transaksi " + kategori1.getNama());
                                Log.d(TAG, document.getId() + " => " + document.getData());*/
                                listTransaksi.add(transaksi1);

                                Log.d(TAG, "isi transaksi di main"+listTransaksi.size());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_transaksi, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent i = new Intent(DetailTransaksiActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
                return true;
            case R.id.action_delete_transaksi:
                new AlertDialog.Builder(this)
                        .setTitle("Hapus")
                        .setMessage("Apa kamu yakin ingin menghapus ini?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                Intent in = getIntent();
                                Bundle bd = in.getExtras();
                                String key = (String) bd.get("key");

                                db.collection("transaksi").document(key)
                                        .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(TAG, "Error deleting document", e);
                                            }
                                        });
                                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(i);
                                finish();
                                Toast.makeText(DetailTransaksiActivity.this, "deleted", Toast.LENGTH_SHORT).show();
                            }})
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
                return true;
            case R.id.action_edit_transaksi:
                //TODO edit function
                Intent inte = getIntent();
                Bundle bd = inte.getExtras();
                if(bd != null)
                {

                    int jenis = (int) bd.get("jenis");
                    String nama = (String) bd.get("nama");
                    String tanggal = (String) bd.get("date");
                    int jumlah = (int) bd.get("mount");
                    String key = (String) bd.get("key");

                    Intent inten = new Intent(getApplicationContext(),TransaksiActivity.class);
                    inten.putExtra("key",key);
                    inten.putExtra("jenis", jenis);
                    inten.putExtra("mount", jumlah);
                    inten.putExtra("date",tanggal);
                    inten.putExtra("nama",nama);
                    startActivity(inten);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
