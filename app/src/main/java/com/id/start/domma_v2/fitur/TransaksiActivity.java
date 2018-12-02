package com.id.start.domma_v2.fitur;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.id.start.domma_v2.R;
import com.id.start.domma_v2.model.Kategori;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransaksiActivity extends AppCompatActivity {

    private GridLayoutManager gridKategori;
    private final static String TAG = "fakuy";
    private Spinner spinner;
    private Toolbar toolbar;

    //calculator
    Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn0;
    Button btnTambah,btnKurang,btnSamaDengan,btnTitik,btnHapus, btnSelesai;
    private TextView tvResult;
    private String currentDisplayedInput = "";
    private String inputToBeParsed = "";
    private String result;

    //database
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);

        //setup view
        Toolbar toolbar = findViewById(R.id.toolbar_transaksi);
        setSupportActionBar(toolbar);
        btn0 = findViewById(R.id.btn_0);
        btn1 = findViewById(R.id.btn_1);
        btn2 = findViewById(R.id.btn_2);
        btn3 = findViewById(R.id.btn_3);
        btn4 = findViewById(R.id.btn_4);
        btn5 = findViewById(R.id.btn_5);
        btn6 = findViewById(R.id.btn_6);
        btn7 = findViewById(R.id.btn_7);
        btn8 = findViewById(R.id.btn_8);
        btn9 = findViewById(R.id.btn_9);

        btnTambah = findViewById(R.id.btn_tambah);
        btnKurang = findViewById(R.id.btn_kurang);
        btnSamaDengan = findViewById(R.id.btn_sama_dengan);
        btnSelesai = findViewById(R.id.btn_selesai);
        btnTitik = findViewById(R.id.btn_titik);
        btnHapus = findViewById(R.id.btn_hapus);

        tvResult = findViewById(R.id.tv_hasil_operasi);
        ImageView image = findViewById(R.id.iv_kategori);

        spinner = findViewById(R.id.spn_transaksi);
        toolbar = findViewById(R.id.toolbar_transaksi);

        //setup data
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String[] tipe = new String[]{
                "Expense",
                "Income"
        };

        List<String> listTipe = new ArrayList<>(Arrays.asList(tipe));
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(
                this,R.layout.item_spinner,listTipe
        );

        spinnerArrayAdapter.setDropDownViewResource(R.layout.item_spinner);
        spinner.setAdapter(spinnerArrayAdapter);

        //get data for kategori

        //condition for selected spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //btn add transaksi
        btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        initControlListener();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_kategori, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add_kategori:
                //get data
                db.collection("kategori")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Kategori kategori1 = document.toObject(Kategori.class);
                                        Log.d(TAG, "get kategori di transaksi " + kategori1.getNama());
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

                //intent
                Intent intent = new Intent(getApplicationContext(), KategoriActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initControlListener() {
        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumberButtonClicked("0");
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumberButtonClicked("1");
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumberButtonClicked("2");
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumberButtonClicked("3");
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumberButtonClicked("4");
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumberButtonClicked("5");
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumberButtonClicked("6");
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumberButtonClicked("7");
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumberButtonClicked("8");
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumberButtonClicked("9");
            }
        });

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClearButtonClicked();
            }
        });
        btnKurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                onOperatorButtonClicked("-");
            }
        });
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOperatorButtonClicked("+");
            }
        });
        btnSamaDengan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEqualButtonClicked();
            }
        });

    }

    private void onEqualButtonClicked() {
        int res = 0;
        try {
            int number = Integer.valueOf(currentDisplayedInput);
            int number2 = Integer.valueOf(tvResult.getText().toString());
            switch (inputToBeParsed) {
                case "+":
                    res = number + number2;
                    break;
                case "/":
                    res = number / number2;
                    break;
                case "-":
                    res = number - number2;
                    break;
                case "X":
                    res = number * number2;
                    break;
            }
            result = String.valueOf(res);
            tvResult.setText(result);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onOperatorButtonClicked(String inputToBeParsed) {
        currentDisplayedInput = tvResult.getText().toString();
        onClearButtonClicked();
        this.inputToBeParsed = inputToBeParsed;
    }

    private void onClearButtonClicked() {
        result = "";
        tvResult.setText("");
    }

    private void onNumberButtonClicked(String pos) {
        result = tvResult.getText().toString();
        result = result + pos;
        tvResult.setText(result);
    }

}
