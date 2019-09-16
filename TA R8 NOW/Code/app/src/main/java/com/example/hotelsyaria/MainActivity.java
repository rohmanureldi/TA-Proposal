package com.example.hotelsyaria;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    SeekBar sbPelayanan,sbProduk,sbPengelolaan,sbRatingUmum;
    Button btnFilter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVariable();
        onClickListener();
    }


    private void initVariable(){
        sbPelayanan = findViewById(R.id.seekBar_pelayanan);
        sbProduk= findViewById(R.id.seekBar_produk);
        sbPengelolaan= findViewById(R.id.seekBar_pengelolaan);
        sbRatingUmum= findViewById(R.id.seekBar_ratingUmum);
        btnFilter= findViewById(R.id.btn_filter);
    }

    private void onClickListener(){
        btnFilter.setOnClickListener(v->{
            float[] preference = getPreferences();
            startActivity(new Intent(this,HasilActivity.class).putExtra("preference",preference));
        });
    }

    private float[] getPreferences(){
        int pelayanan=sbPelayanan.getProgress();
        int pengelolaan = sbPengelolaan.getProgress();
        int produk = sbProduk.getProgress();
        int umum = sbRatingUmum.getProgress();
        float sum = (pelayanan/5f)+pengelolaan/5f+produk/5f+umum/5f;
        return new float[] {umum/(5*sum),produk/(5*sum),pelayanan/(5*sum),pengelolaan/(5*sum)};

    }

}
