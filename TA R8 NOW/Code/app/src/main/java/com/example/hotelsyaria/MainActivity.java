package com.example.hotelsyaria;


import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class MainActivity extends AppCompatActivity {
    SeekBar sbPelayanan, sbProduk, sbPengelolaan, sbRatingUmum;
    Button btnFilter;
    ImageView ivInfoProduk, ivInfoPelayanan, ivInfoPengelolaan, ivInfoTripAdv;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVariable();
        onClickListener();
    }

    SeekBar.OnSeekBarChangeListener listener(TextView tv) {
        return new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv.setText(Integer.toString(progress+1));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
    }


    private void initVariable() {
        sbPelayanan = findViewById(R.id.seekBar_pelayanan);
        sbProduk = findViewById(R.id.seekBar_produk);
        sbPengelolaan = findViewById(R.id.seekBar_pengelolaan);
        sbRatingUmum = findViewById(R.id.seekBar_ratingUmum);
        btnFilter = findViewById(R.id.btn_filter);
        ivInfoProduk = findViewById(R.id.iv_info_produk);
        ivInfoPelayanan = findViewById(R.id.iv_info_pelayanan);
        ivInfoPengelolaan = findViewById(R.id.iv_info_pengelolaan);
        ivInfoTripAdv = findViewById(R.id.iv_info_rating_umum);

        sbPelayanan.setOnSeekBarChangeListener(listener(findViewById(R.id.sb_progress_pelayanan)));
        sbProduk.setOnSeekBarChangeListener(listener(findViewById(R.id.sb_progress_produk)));
        sbPengelolaan.setOnSeekBarChangeListener(listener(findViewById(R.id.sb_progress_pengelolaan)));
        sbRatingUmum.setOnSeekBarChangeListener(listener(findViewById(R.id.sb_progress_ratingUmum)));

    }

    private void onClickListener() {
        btnFilter.setOnClickListener(v -> {
            float[] preference = getPreferences();
            startActivity(new Intent(this, HasilActivity.class).putExtra("preference", preference));
        });
        ivInfoProduk.setOnClickListener(v -> showInfo(1));
        ivInfoPelayanan.setOnClickListener(v -> showInfo(2));
        ivInfoPengelolaan.setOnClickListener(v -> showInfo(3));
        ivInfoTripAdv.setOnClickListener(v -> showInfo(4));
    }

    private float[] getPreferences() {
        int pelayanan = sbPelayanan.getProgress()+1;
        int pengelolaan = sbPengelolaan.getProgress()+1;
        int produk = sbProduk.getProgress()+1;
        int umum = sbRatingUmum.getProgress()+1;
        float sum = (pelayanan / 5f) + pengelolaan / 5f + produk / 5f + umum / 5f;
        float[] preferensi = new float[]{umum / (5 * sum), produk / (5 * sum), pelayanan / (5 * sum), pengelolaan / (5 * sum)};
        return preferensi;

    }

    private void showInfo(int i) {

        String deskripsi;
        String title="";
        switch (i) {
            case 1:
                deskripsi = getResources().getString(R.string.produk_deskripsi);
                title="Deskripsi 'Produk'";
                break;
            case 2:
                deskripsi = getResources().getString(R.string.pelayanan_deskripsi);
                title="Deskripsi 'Pelayanan'";
                break;

            case 3:
                deskripsi = getResources().getString(R.string.pengelolaan_deskripsi);
                title="Deskripsi 'Pengelolaan'";
                break;

            case 4:
                deskripsi = getResources().getString(R.string.tripAdvisor_deskripsi);
                title="Deskripsi 'Rating Umum'";
                break;
            default: deskripsi="";
        }

        View view = getLayoutInflater().inflate(R.layout.dialog_info, null);
        Dialog dialog = new BottomSheetDialog(this);
        TextView tvTitleDeskripsi= view.findViewById(R.id.tv_title_desc);
        TextView tvDeskripsi = view.findViewById(R.id.tvDeskripsi);
        tvTitleDeskripsi.setText(title);
        tvDeskripsi.setText(deskripsi);
        dialog.setContentView(view);
        dialog.show();
    }

}
