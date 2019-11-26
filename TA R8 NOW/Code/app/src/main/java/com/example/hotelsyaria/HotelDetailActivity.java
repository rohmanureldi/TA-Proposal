package com.example.hotelsyaria;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;

public class HotelDetailActivity extends AppCompatActivity {
    private PreferenceHandler prefHandler;
    private TextView tvRatingSyariah, tvRatingUmum, tvAlamat, tvNamaHotel,tvDeskripsi,tvLihatDetail;
    private Button btnBook;
    private RecyclerView rvFasilitasUmum, rvFasilitasSyariah;
    private CarouselView cvHotelCarousel;
    private ModelHotel hotel;
    FasilitasUmumAdapter fasilitasUmumAdapter, fasilitasSyariahAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hotel_detail);
        prefHandler = new PreferenceHandler(this);
        hotel = prefHandler.getClickedHotel();
        initView();
        initAdapter();
        onClickController();
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        tvNamaHotel = findViewById(R.id.tvHotelName);
        tvRatingSyariah = findViewById(R.id.tvRatingSyariah);
        tvRatingUmum = findViewById(R.id.tvRatingTripAdvisor);
        tvAlamat = findViewById(R.id.tvAlamat);
        tvDeskripsi = findViewById(R.id.tvHotelDescription);
        btnBook = findViewById(R.id.btnBook);
        rvFasilitasSyariah = findViewById(R.id.rvFasilitasSyariah);
        rvFasilitasUmum = findViewById(R.id.rvFasilitasUmum);
        cvHotelCarousel = findViewById(R.id.cvHotelCarousel);
        tvLihatDetail = findViewById(R.id.tvLihatDetail);
        tvNamaHotel.setText(hotel.getNama_hotel());
        tvAlamat.setText(hotel.getAlamat_hotel());
        tvRatingUmum.setText(hotel.getRating_umum().toString());
        tvRatingSyariah.setText(hotel.getRating_syariah());
        tvDeskripsi.setText(hotel.getDeskripsi());
        ImageListener imageListener = (position, imageView) -> imageView.setImageResource(hotel.getImages()[position]);
        cvHotelCarousel.setPageCount(hotel.getImages().length);
        cvHotelCarousel.setImageListener(imageListener);
    }

    private void initAdapter() {
        fasilitasUmumAdapter = new FasilitasUmumAdapter(hotel.getFasilitasUmum());
        rvFasilitasUmum.setAdapter(fasilitasUmumAdapter);
        fasilitasSyariahAdapter = new FasilitasUmumAdapter(hotel.getFasilitasSyariah());
        rvFasilitasSyariah.setAdapter(fasilitasSyariahAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        layoutManager1.setOrientation(RecyclerView.HORIZONTAL);
        rvFasilitasUmum.setLayoutManager(layoutManager);
        rvFasilitasSyariah.setLayoutManager(layoutManager1);
    }

    @SuppressLint("SetTextI18n")
    private void onClickController() {
        btnBook.setOnClickListener(v -> {
            ArrayList<ArrayList<Float>> preferences = prefHandler.getPref();
            ArrayList<ArrayList<Float>> tmp = new ArrayList<>();
            if (preferences == null) {
                preferences = new ArrayList<>();
                preferences.add(new ArrayList<>());
                preferences.add(new ArrayList<>());
                preferences.add(new ArrayList<>());
                preferences.add(new ArrayList<>());
            }
            ArrayList<ModelHotel> listBooked = prefHandler.getListBooked();
            if (listBooked == null) {
                listBooked = new ArrayList<>();
            }
            listBooked.add(hotel);
            prefHandler.setlistBooked(listBooked);

            float[] tmpPref = convertPref(new float[]{hotel.getRating_umum(), hotel.getProduk(), hotel.getPelayanan(), hotel.getPengelolaan()});

            preferences.get(0).add(tmpPref[0]);
            preferences.get(1).add(tmpPref[1]);
            preferences.get(2).add(tmpPref[2]);
            preferences.get(3).add(tmpPref[3]);

            prefHandler.setPref(preferences);
            Toast.makeText(this, "Hotel succesfully booked !", Toast.LENGTH_SHORT).show();
        });

        tvDeskripsi.setOnClickListener(v -> {
            startActivity(new Intent(this,DescriptionPolicyActivity.class));
        });

        tvLihatDetail.setOnClickListener(v->{
            View view = getLayoutInflater().inflate(R.layout.dialog_info,null);
            BottomSheetDialog dialog = new BottomSheetDialog(this);
            TextView tvProd,tvPel,tvPeng,tvUmum,tvRatProd,tvRatPel,tvRatPeng,tvRatUm;
            tvProd = view.findViewById(R.id.tvProduk);
            tvPel = view.findViewById(R.id.tvPelayanan);
            tvPeng = view.findViewById(R.id.tvPengelolaan);
            tvRatProd= view.findViewById(R.id.tvRatingProduk);
            tvRatPel= view.findViewById(R.id.tvRatingPelayanan);
            tvRatPeng= view.findViewById(R.id.tvRatingPengelolaan);

            tvProd.setText(getResources().getString(R.string.produk_deskripsi));
            tvRatProd.setText(hotel.getProduk().toString());

            tvPel.setText(getResources().getString(R.string.pelayanan_deskripsi));
            tvRatPel.setText(hotel.getPelayanan().toString());

            tvPeng.setText(getResources().getString(R.string.pengelolaan_deskripsi));
            tvRatPeng.setText(hotel.getPengelolaan().toString());


            dialog.setContentView(view);
            dialog.show();
        });
    }

    private float[] convertPref(float[] pref){
        float x,y,z;
        x=pref[0]/5;
        y=pref[1]/5;
        z=pref[2]/5;
        return new float[]{x,y,z,pref[3]/5};
    }
}
