package com.id.start.domma_v2.fitur;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.id.start.domma_v2.R;
import com.id.start.domma_v2.model.Kategori;
import com.id.start.domma_v2.model.Transaksi;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KategoriActivity extends AppCompatActivity {
    List<Kategori> itemList = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docRef;
    private final static String TAG = "fakuy";
    Spinner spinner;
    Toolbar toolbar;
    EditText edt_nama_kategori;
    List<Kategori> listKategori = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori);

        //setup view
        spinner = findViewById(R.id.spn_tipe_kategori);
        toolbar = findViewById(R.id.toolbar_kategori);
        edt_nama_kategori = findViewById(R.id.edt_nama_kategori);

        //setup data
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Kategori");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryLight));
        String[] tipe = new String[]{
                "Expense",
                "Income"
        };

        List<String> listTipe = new ArrayList<>(Arrays.asList(tipe));
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(
                this, R.layout.item_spinner, listTipe
        );

        spinnerArrayAdapter.setDropDownViewResource(R.layout.item_spinner);
        spinner.setAdapter(spinnerArrayAdapter);


        //condition for selected spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, KategoriActivity.class);
        //u can set put extra here
        activity.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_complete_kategori, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_complete_kategori) {
            int type = 0;
            final Kategori kategori = new Kategori(edt_nama_kategori.getText().toString(), type);
            Log.d(TAG, "posisi spinner di kategori: " + spinner.getSelectedItemPosition());
            if (spinner.getSelectedItemPosition() == 0) {
                type = 0;
            } else {
                type = 1;
            }
            final String namaKategori = edt_nama_kategori.getText().toString();
            if (namaKategori.trim().length() > 0) {
                kategori.setTipe(type);

                db.collection("kategori")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Kategori kategori1 = document.toObject(Kategori.class);
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        Log.d(TAG, "isinya " + kategori1.getNama());
                                        listKategori.add(kategori1);
                                        Log.d(TAG, "isi list kategori" + listKategori);
                                    }
                                    int x = 0;
                                    for (int i = 0; i < listKategori.size(); i++) {
                                        if (listKategori.get(i).getNama().equals(namaKategori.toLowerCase())) {
                                            x++;
                                            Log.d(TAG, "ini x" + x);
                                        }
                                    }
                                    if (x > 0) {
                                        Toast.makeText(getApplicationContext().getApplicationContext(),
                                                "Kategori sudah anda buat sebelumnya",
                                                Toast.LENGTH_LONG).show();
                                    } else {

                                        db.collection("kategori")
                                                .add(kategori)
                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        Log.d(TAG, "onSuccess: " + documentReference);
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.w(TAG, "onFailure: ", e);
                                                    }
                                                });

                                        Intent intent = new Intent(getApplicationContext(), TransaksiActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
            } else {
                // user didn't entered name
                Toast.makeText(getApplicationContext().getApplicationContext(),
                        "Please enter category name",
                        Toast.LENGTH_LONG).show();
            }
        }
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(KategoriActivity.this, TransaksiActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
