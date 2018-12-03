package com.id.start.domma_v2.fitur;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.id.start.domma_v2.ClickListener;
import com.id.start.domma_v2.MainActivity;
import com.id.start.domma_v2.R;
import com.id.start.domma_v2.adapter.KategoriAdapter;
import com.id.start.domma_v2.model.Kategori;
import com.id.start.domma_v2.model.Transaksi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TransaksiActivity extends AppCompatActivity {

    private GridLayoutManager gridKategori;
    private final static String TAG = "fakuy";
    private Spinner spinner;
    private Toolbar toolbar;
    RecyclerView rvKategori;
    private KategoriAdapter adapter;
    Calendar myCalendar = Calendar.getInstance();
    ProgressBar progressBar;
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };


    //calculator
    Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn0;
    Button btnTambah,btnKurang,btnSamaDengan,btnHapus, btnSelesai;
    TextView tvDate;
    private TextView tvNamaTransaksi;
    private EditText tvResult;
    private String currentDisplayedInput = "";
    private String inputToBeParsed = "";
    private String result;


    //database
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<Kategori> listKategori = new ArrayList<>();
    List<Kategori> listKatExpense = new ArrayList<>();
    List<Kategori> listKatIncome = new ArrayList<>();
    TextDrawable textDrawable;
    String nama_transaksi = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);

        //setup view
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

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

        tvDate = findViewById(R.id.tv_date);
        btnTambah = findViewById(R.id.btn_tambah);
        btnKurang = findViewById(R.id.btn_kurang);
        btnSamaDengan = findViewById(R.id.btn_sama_dengan);
        btnSelesai = findViewById(R.id.btn_selesai);
        btnHapus = findViewById(R.id.btn_hapus);
        rvKategori = findViewById(R.id.rv_kategori);

        tvResult = findViewById(R.id.tv_hasil_operasi);
        tvNamaTransaksi = findViewById(R.id.tv_nama_transaksi);
        progressBar = findViewById(R.id.progress_bar_get_kategori);
        progressBar.setVisibility(View.VISIBLE);

        spinner = findViewById(R.id.spn_transaksi);
        toolbar = findViewById(R.id.toolbar_transaksi);
        gridKategori = new GridLayoutManager(TransaksiActivity.this, 4);

        //setup data
        tvDate.setText(sdf.format(myCalendar.getTime()));
        String[] tipe = new String[]{
                "Expense",
                "Income"
        };

        final List<String> listTipe = new ArrayList<>(Arrays.asList(tipe));

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(
                this,R.layout.item_spinner,listTipe
        );

        spinnerArrayAdapter.setDropDownViewResource(R.layout.item_spinner);
        spinner.setAdapter(spinnerArrayAdapter);

        Log.d(TAG, "posisi spinner"+spinner.getSelectedItemPosition());

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Transaksi");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryLight));

        rvKategori.setHasFixedSize(true);
        rvKategori.setLayoutManager(gridKategori);
        rvKategori.setItemAnimator(new DefaultItemAnimator());
        rvKategori.setNestedScrollingEnabled(false);


        //get data for kategori

        //condition for selected spinner

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        adapter = new KategoriAdapter(listKatExpense, new ClickListener() {
                            @Override
                            public void onPositionClicked(int position) {
                                nama_transaksi = "";
                                tvNamaTransaksi.setText("-");
                                nama_transaksi = listKatExpense.get(position).getNama();
                                tvNamaTransaksi.setText(nama_transaksi);
                            }

                            @Override
                            public void onLongClicked(int position) {

                            }
                        });
                        rvKategori.setAdapter(adapter);
                        break;
                    case 1:
                        adapter = new KategoriAdapter(listKatIncome, new ClickListener() {
                            @Override
                            public void onPositionClicked(int position) {
                                nama_transaksi = "";
                                tvNamaTransaksi.setText("-");
                                nama_transaksi = listKatIncome.get(position).getNama();
                                tvNamaTransaksi.setText(nama_transaksi);
                            }

                            @Override
                            public void onLongClicked(int position) {

                            }
                        });
                        rvKategori.setAdapter(adapter);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        db.collection("kategori")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            listKategori.clear();
                            listKatExpense.clear();
                            listKatIncome.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Kategori kategori1 = document.toObject(Kategori.class);
                                /*Log.d(TAG, "get kategori di transaksi " + kategori1.getNama());
                                Log.d(TAG, document.getId() + " => " + document.getData());*/

                                if (kategori1.getTipe()==0){
                                    listKatExpense.add(kategori1);
                                } else {
                                    listKatIncome.add(kategori1);
                                }

                                listKategori.add(document.toObject(Kategori.class));
//                                Log.d(TAG, "kategori untuk gambar: "+ listKategori);
                            }
                            adapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        //btn add transaksi
        btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int type = 0;
                if (spinner.getSelectedItemPosition() == 0){
                    type = 0;
                } else{
                    type = 1;
                }
                if (!nama_transaksi.equals("") && (!result.equals("") || Integer.valueOf(result) != 0)){
                    Transaksi transaksi = new Transaksi(nama_transaksi, Integer.valueOf(result), tvDate.getText().toString(),type);
                    transaksi.setTipe(type);

                    db.collection("transaksi")
                            .add(transaksi)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d(TAG, "onSuccess transaksi: "+documentReference);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "onFailure transaksi: ", e);
                                }
                            });
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext().getApplicationContext(),
                            "Harap lengkapi data jumlah transaksi atau kategori transaksi",
                            Toast.LENGTH_LONG).show();
                }
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
                //intent
                Intent intent = new Intent(getApplicationContext(), KategoriActivity.class);
                startActivity(intent);
                return true;
            case android.R.id.home:
                Intent i = new Intent(TransaksiActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initControlListener() {
        tvResult.setText("");

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(TransaksiActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

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
                case "-":
                    res = number - number2;
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

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        tvDate.setText(sdf.format(myCalendar.getTime()));
    }

}
