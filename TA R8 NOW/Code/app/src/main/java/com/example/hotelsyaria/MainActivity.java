package com.example.hotelsyaria;


import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;


public class MainActivity extends AppCompatActivity {
    private SeekBar seekBar_pelayanan, seekBar_produk, seekBar_pengelolaan;
    private TextView sb_progress_pelayanan, sb_progress_produk, sb_progress_pengelolaan;
    RecyclerView recyclerView;
    public ArrayList<ModelHotel> clonedListHotel, listHotel,sortedListHotel;
    Button btnOpenFilter, btnFilter, btnCloseFilter,btnOpenPref;
    ArrayList<CheckBox> listCheckBox;
    ArrayList<ArrayList<Integer>> listFilter;
    ArrayList<Integer> filterProduk, filterPelayanan;
    ArrayList<String> index_produk, index_pelayanan, index_pengelolaan;
    ArrayList<ArrayList<String>> produk_value, pelayanan_value, pengelolaan_value;
    ArrayList<Float> bobot_produk, bobot_pelayanan, bobot_pengelolaan,bobot_umum,listRating;
    public ArrayList<ArrayList<Float>> preferences;
    ArrayList<ArrayList<Float>> matrix;
    ConstraintLayout filterLayout;
    RecyclerAdapter adapter;
    filterFragment filter_fragment;
    FragmentTransaction transaction;
    PreferenceHandler preferenceHandler;
    Map<String,Integer> gambarFasilitasUmum,gambarFasilitasSyariah;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_2);
//        seekBar_produk=findViewById(R.id.seekBar_produk);
//        seekBar_pelayanan=findViewById(R.id.seekBar_pelayanan);
//        seekBar_pengelolaan=findViewById(R.id.seekBar_pengelolaan);
//        sb_progress_produk=findViewById(R.id.sb_progress_produk);
//        sb_progress_pengelolaan=findViewById(R.id.sb_progress_pengelolaan);
//        sb_progress_pelayanan=findViewById(R.id.sb_progress_pelayanan);
//        btn_filter=findViewById(R.id.btn_filter);
//
//
//        sb_Progress(seekBar_produk,sb_progress_produk);
//        sb_Progress(seekBar_pelayanan,sb_progress_pelayanan);
//        sb_Progress(seekBar_pengelolaan,sb_progress_pengelolaan);
//
//        btn_filter.setOnClickListener(v -> {
//            float preference[] = {(float)Integer.parseInt(sb_progress_produk.getText().toString())/5,(float)Integer.parseInt(sb_progress_pelayanan.getText().toString())/5,(float)Integer.parseInt(sb_progress_pengelolaan.getText().toString())/5};
//            startActivity(new Intent(MainActivity.this, HasilActivity.class).putExtra("pref",preference));
//        });
//
//    }
//
//    void sb_Progress(SeekBar sb,final TextView progress_status){
//        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                progress_status.setText(Integer.toString(progress));
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });

        recyclerView = findViewById(R.id.recycler);
        index_produk = new ArrayList<>();
        index_pelayanan = new ArrayList<>();
        index_pengelolaan = new ArrayList<>();
        produk_value = new ArrayList<>();
        pelayanan_value = new ArrayList<>();
        pengelolaan_value = new ArrayList<>();
        bobot_pelayanan = new ArrayList<>();
        bobot_produk = new ArrayList<>();
        bobot_pengelolaan = new ArrayList<>();
        bobot_umum = new ArrayList<>();
        preferences = new ArrayList<>();
        preferenceHandler = new PreferenceHandler(this);
        matrix = new ArrayList<>();



        InputStream inputStream = getResources().openRawResource(R.raw.data);
        ArrayList<String[]> data = new ArrayList<>();
        try {
            InputStreamReader is = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(is);
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] txt = line.split(";");
                data.add(txt);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



        initHotel();
        cleanData(data);
        prepareVariable();

        addFacilityException(0, new String[]{"spa", "olg"});
        addFacilityException(5, new String[]{"spa"});
        prepareData();
        hitungRating();
        preferenceHandler.setListHotel(listHotel);
        onClickController();
    }

    @SuppressWarnings("ConstantConditions")
    void initHotel(){
        listHotel = new ArrayList<>();
        gambarFasilitasSyariah= new HashMap<>();
        gambarFasilitasUmum= new HashMap<>();
        initGambarFasilitas();
        listHotel.add(new ModelHotel("Hotel Grand Asrilia", "Jl. Pelajar Pejuang 45 No. 123",new int[]{R.drawable.as_cover,R.drawable.as_dining,R.drawable.as_room}));
        listHotel.add(new ModelHotel("Hotel Horison", "Jl. Pelajar Pejuang 45 No. 121",new int[]{R.drawable.ho_cover,R.drawable.ho_receptionis,R.drawable.ho_room}));
        listHotel.add(new ModelHotel("Hotel Yello", "Jl. Pasir Kaliki No. 25",new int[]{R.drawable.ye_cover,R.drawable.ye_dining,R.drawable.ye_room}));
        listHotel.add(new ModelHotel("Hotel Aston Pasteur", "Jl. Dr. Djunjunan No. 162",new int[]{R.drawable.astp_cover,R.drawable.astp_dining,R.drawable.astp_room}));
        listHotel.add(new ModelHotel("Hotel Aston Braga ", "Jl. Braga No. 99",new int[]{R.drawable.astb_cover,R.drawable.astb_dining,R.drawable.astb_room}));
        listHotel.add(new ModelHotel("Hotel Four Points", "Jl. Ir. H. Djuanda No. 46",new int[]{R.drawable.fp_cover,R.drawable.fp_dining,R.drawable.fp_room}));
        listHotel.add(new ModelHotel("Hotel Hilton", " Jl. HOS Tjokroaminoto No.41",new int[]{R.drawable.hi_cover,R.drawable.hi_dining,R.drawable.hi_room}));
        listHotel.add(new ModelHotel("Shakti Hotel Bandung", "Jl. Soekarno Hatta No.753",new int[]{R.drawable.sh_cover,R.drawable.sh_dining,R.drawable.sh_room}));
        listHotel.add(new ModelHotel("Hotel I", "Jalan Pasadena",new int[]{R.drawable.ic_hotel,R.drawable.ic_hotel,R.drawable.ic_hotel}));
        listHotel.add(new ModelHotel("Hotel J", "Jalan Pasadena",new int[]{R.drawable.ic_hotel,R.drawable.ic_hotel,R.drawable.ic_hotel}));
        String dmyStr="Hotel ini hotel dummy.";
        listHotel.get(0).setDeskripsiPolicy(getResources().getString(R.string.d1),"Kebijakan hotel belum tersedia.");
        listHotel.get(1).setDeskripsiPolicy(getResources().getString(R.string.d2),getResources().getString(R.string.p2));
        listHotel.get(2).setDeskripsiPolicy(getResources().getString(R.string.d3),getResources().getString(R.string.p3));
        listHotel.get(3).setDeskripsiPolicy(getResources().getString(R.string.d4),getResources().getString(R.string.p4));
        listHotel.get(4).setDeskripsiPolicy(getResources().getString(R.string.d5),getResources().getString(R.string.p5));
        listHotel.get(5).setDeskripsiPolicy(getResources().getString(R.string.d6),getResources().getString(R.string.p6));
        listHotel.get(6).setDeskripsiPolicy(getResources().getString(R.string.d7),getResources().getString(R.string.p7));
        listHotel.get(7).setDeskripsiPolicy(getResources().getString(R.string.d8),getResources().getString(R.string.p8));
        listHotel.get(8).setDeskripsiPolicy(dmyStr,dmyStr);
        listHotel.get(9).setDeskripsiPolicy(dmyStr,dmyStr);

        listHotel.get(0).setFasilitasUmum(new String[]{"AC","Restaurant","Kolam Renang","Resepsionis 24 Jam","Parkir","Lift","Wifi"},
                new int[]{gambarFasilitasUmum.get("AC"),
                        gambarFasilitasUmum.get("Restaurant"),
                        gambarFasilitasUmum.get("Kolam Renang"),
                        gambarFasilitasUmum.get("Resepsionis 24 Jam"),
                        gambarFasilitasUmum.get("Parkir"),
                        gambarFasilitasUmum.get("Lift"),
                        gambarFasilitasUmum.get("Wifi")});
        listHotel.get(1).setFasilitasUmum(new String[]{"AC","Restaurant","Kolam Renang","Resepsionis 24 Jam","Parkir","Lift","Wifi"},
                new int[]{gambarFasilitasUmum.get("AC"),
                        gambarFasilitasUmum.get("Restaurant"),
                        gambarFasilitasUmum.get("Kolam Renang"),
                        gambarFasilitasUmum.get("Resepsionis 24 Jam"),
                        gambarFasilitasUmum.get("Parkir"),
                        gambarFasilitasUmum.get("Lift"),
                        gambarFasilitasUmum.get("Wifi")});
        listHotel.get(2).setFasilitasUmum(new String[]{"AC","Restaurant","Resepsionis 24 Jam","Parkir","Lift","Wifi"},
                new int[]{gambarFasilitasUmum.get("AC"),
                        gambarFasilitasUmum.get("Restaurant"),
                        gambarFasilitasUmum.get("Resepsionis 24 Jam"),
                        gambarFasilitasUmum.get("Parkir"),
                        gambarFasilitasUmum.get("Lift"),
                        gambarFasilitasUmum.get("Wifi")});
        listHotel.get(3).setFasilitasUmum(new String[]{"AC","Restaurant","Kolam Renang","Resepsionis 24 Jam","Parkir","Lift","Wifi"},
                new int[]{gambarFasilitasUmum.get("AC"),
                        gambarFasilitasUmum.get("Restaurant"),
                        gambarFasilitasUmum.get("Kolam Renang"),
                        gambarFasilitasUmum.get("Resepsionis 24 Jam"),
                        gambarFasilitasUmum.get("Parkir"),
                        gambarFasilitasUmum.get("Lift"),
                        gambarFasilitasUmum.get("Wifi")});
        listHotel.get(4).setFasilitasUmum(new String[]{"AC","Restaurant","Kolam Renang","Resepsionis 24 Jam","Parkir","Lift","Wifi"},
                new int[]{gambarFasilitasUmum.get("AC"),
                        gambarFasilitasUmum.get("Restaurant"),
                        gambarFasilitasUmum.get("Kolam Renang"),
                        gambarFasilitasUmum.get("Resepsionis 24 Jam"),
                        gambarFasilitasUmum.get("Parkir"),
                        gambarFasilitasUmum.get("Lift"),
                        gambarFasilitasUmum.get("Wifi")});
        listHotel.get(5).setFasilitasUmum(new String[]{"AC","Restaurant","Kolam Renang","Resepsionis 24 Jam","Parkir","Lift","Wifi"},
                new int[]{gambarFasilitasUmum.get("AC"),
                        gambarFasilitasUmum.get("Restaurant"),
                        gambarFasilitasUmum.get("Kolam Renang"),
                        gambarFasilitasUmum.get("Resepsionis 24 Jam"),
                        gambarFasilitasUmum.get("Parkir"),
                        gambarFasilitasUmum.get("Lift"),
                        gambarFasilitasUmum.get("Wifi")});
        listHotel.get(6).setFasilitasUmum(new String[]{"Restaurant","Kolam Renang","Resepsionis 24 Jam","Lift","Wifi"},
                new int[]{
                        gambarFasilitasUmum.get("Restaurant"),
                        gambarFasilitasUmum.get("Kolam Renang"),
                        gambarFasilitasUmum.get("Resepsionis 24 Jam"),
                        gambarFasilitasUmum.get("Lift"),
                        gambarFasilitasUmum.get("Wifi")});
        listHotel.get(7).setFasilitasUmum(new String[]{"AC","Restaurant","Kolam Renang","Resepsionis 24 Jam","Parkir","Lift","Wifi"},
                new int[]{gambarFasilitasUmum.get("AC"),
                        gambarFasilitasUmum.get("Restaurant"),
                        gambarFasilitasUmum.get("Kolam Renang"),
                        gambarFasilitasUmum.get("Resepsionis 24 Jam"),
                        gambarFasilitasUmum.get("Parkir"),
                        gambarFasilitasUmum.get("Lift"),
                        gambarFasilitasUmum.get("Wifi")});
        listHotel.get(8).setFasilitasUmum(new String[]{"AC","Restaurant","Wifi"},
                new int[]{gambarFasilitasUmum.get("AC"),
                        gambarFasilitasUmum.get("Restaurant"),
                        gambarFasilitasUmum.get("Wifi")});
        listHotel.get(9).setFasilitasUmum(new String[]{"AC","Restaurant","Kolam Renang","Resepsionis 24 Jam","Parkir","Lift","Wifi"},
                new int[]{gambarFasilitasUmum.get("AC"),
                        gambarFasilitasUmum.get("Restaurant"),
                        gambarFasilitasUmum.get("Kolam Renang"),
                        gambarFasilitasUmum.get("Resepsionis 24 Jam"),
                        gambarFasilitasUmum.get("Parkir"),
                        gambarFasilitasUmum.get("Lift"),
                        gambarFasilitasUmum.get("Wifi")});
        listHotel.get(0).setFasilitasSyariah(new String[]{
                        "Sajadah",
                        "Al Quran",
                        "Makanan & Minuman Halal",
                        "Tidak Tersedia Akses Porno",
                        "Kolam Renang Terpisah dan Tertutup"
                },
                new int[]{
                        gambarFasilitasSyariah.get("Sajadah"),
                        gambarFasilitasSyariah.get("Al Quran"),
                        gambarFasilitasSyariah.get("Makanan & Minuman Halal"),
                        gambarFasilitasSyariah.get("Tidak Tersedia Akses Porno"),
                        gambarFasilitasSyariah.get("Kolam Renang Terpisah dan Tertutup")
                });

        listHotel.get(1).setFasilitasSyariah(new String[]{
                        "Sajadah",
                        "Al Quran",
                        "Makanan & Minuman Halal",
                        "Tidak Tersedia Akses Porno",
                        "Spa Sesuai Muhrim & Berbahan Halal",
                        "Kolam Renang Terpisah dan Tertutup"
                },
                new int[]{
                        gambarFasilitasSyariah.get("Sajadah"),
                        gambarFasilitasSyariah.get("Al Quran"),
                        gambarFasilitasSyariah.get("Makanan & Minuman Halal"),
                        gambarFasilitasSyariah.get("Tidak Tersedia Akses Porno"),
                        gambarFasilitasSyariah.get("Spa Sesuai Muhrim & Berbahan Halal"),
                        gambarFasilitasSyariah.get("Kolam Renang Terpisah dan Tertutup")
                });
        listHotel.get(2).setFasilitasSyariah(new String[]{
                        "Sajadah",
                        "Makanan & Minuman Halal",
                        "Tidak Tersedia Akses Porno"
                },
                new int[]{
                        gambarFasilitasSyariah.get("Sajadah"),
                        gambarFasilitasSyariah.get("Makanan & Minuman Halal"),
                        gambarFasilitasSyariah.get("Tidak Tersedia Akses Porno")
                });
        listHotel.get(3).setFasilitasSyariah(new String[]{
                        "Sajadah",
                        "Makanan & Minuman Halal",
                        "Tidak Tersedia Akses Porno"
                },
                new int[]{
                        gambarFasilitasSyariah.get("Sajadah"),
                        gambarFasilitasSyariah.get("Makanan & Minuman Halal"),
                        gambarFasilitasSyariah.get("Tidak Tersedia Akses Porno")
                });
        listHotel.get(4).setFasilitasSyariah(new String[]{
                        "Sajadah",
                        "Al Quran",
                        "Makanan & Minuman Halal",
                        "Tidak Tersedia Akses Porno",
                        "Kolam Renang Terpisah dan Tertutup"
                },
                new int[]{
                        gambarFasilitasSyariah.get("Sajadah"),
                        gambarFasilitasSyariah.get("Al Quran"),
                        gambarFasilitasSyariah.get("Makanan & Minuman Halal"),
                        gambarFasilitasSyariah.get("Tidak Tersedia Akses Porno"),
                        gambarFasilitasSyariah.get("Kolam Renang Terpisah dan Tertutup")
                });
        listHotel.get(5).setFasilitasSyariah(new String[]{
                        "Sajadah",
                        "Al Quran",
                        "Tidak Tersedia Akses Porno"
                },
                new int[]{
                        gambarFasilitasSyariah.get("Sajadah"),
                        gambarFasilitasSyariah.get("Al Quran"),
                        gambarFasilitasSyariah.get("Tidak Tersedia Akses Porno")
                });
        listHotel.get(6).setFasilitasSyariah(new String[]{
                        "Sajadah",
                        "Tidak Tersedia Akses Porno",
                        "Spa Sesuai Muhrim & Berbahan Halal",
                        "Kolam Renang Terpisah dan Tertutup"
                },
                new int[]{
                        gambarFasilitasSyariah.get("Sajadah"),
                        gambarFasilitasSyariah.get("Tidak Tersedia Akses Porno"),
                        gambarFasilitasSyariah.get("Spa Sesuai Muhrim & Berbahan Halal"),
                        gambarFasilitasSyariah.get("Kolam Renang Terpisah dan Tertutup")
                });
        listHotel.get(7).setFasilitasSyariah(new String[]{
                        "Makanan & Minuman Halal",
                        "Tidak Tersedia Akses Porno"
                },
                new int[]{
                        gambarFasilitasSyariah.get("Makanan & Minuman Halal"),
                        gambarFasilitasSyariah.get("Tidak Tersedia Akses Porno")
                });
        listHotel.get(8).setFasilitasSyariah(new String[]{
                        "Sajadah",
                        "Al Quran",
                        "Makanan & Minuman Halal",
                        "Tidak Tersedia Akses Porno"
                },
                new int[]{
                        gambarFasilitasSyariah.get("Sajadah"),
                        gambarFasilitasSyariah.get("Al Quran"),
                        gambarFasilitasSyariah.get("Makanan & Minuman Halal"),
                        gambarFasilitasSyariah.get("Tidak Tersedia Akses Porno")

                });
        listHotel.get(9).setFasilitasSyariah(new String[]{
                        "Sajadah",
                        "Al Quran",
                        "Makanan & Minuman Halal",
                        "Tidak Tersedia Akses Porno",
                        "Spa Sesuai Muhrim & Berbahan Halal",
                        "Kolam Renang Terpisah dan Tertutup"
                },
                new int[]{
                        gambarFasilitasSyariah.get("Sajadah"),
                        gambarFasilitasSyariah.get("Al Quran"),
                        gambarFasilitasSyariah.get("Makanan & Minuman Halal"),
                        gambarFasilitasSyariah.get("Tidak Tersedia Akses Porno"),
                        gambarFasilitasSyariah.get("Spa Sesuai Muhrim & Berbahan Halal"),
                        gambarFasilitasSyariah.get("Kolam Renang Terpisah dan Tertutup")
                });

    }

    private void initGambarFasilitas(){
        gambarFasilitasUmum.put("AC",R.drawable.air_conditioner);
        gambarFasilitasUmum.put("Restaurant",R.drawable.cutlery);
        gambarFasilitasUmum.put("Kolam Renang",R.drawable.swimming_pool);
        gambarFasilitasUmum.put("Resepsionis 24 Jam", R.drawable.reception);
        gambarFasilitasUmum.put("Parkir",R.drawable.parking);
        gambarFasilitasUmum.put("Lift",R.drawable.elevator);
        gambarFasilitasUmum.put("Wifi",R.drawable.wifi);

        gambarFasilitasSyariah.put("Sajadah",R.drawable.sajadah);
        gambarFasilitasSyariah.put("Al Quran",R.drawable.ic_quran);
        gambarFasilitasSyariah.put("Makanan & Minuman Halal",R.drawable.food);
        gambarFasilitasSyariah.put("Tidak Tersedia Akses Porno",R.drawable.no_porn);
        gambarFasilitasSyariah.put("Spa Sesuai Muhrim & Berbahan Halal",R.drawable.spa);
        gambarFasilitasSyariah.put("Kolam Renang Terpisah dan Tertutup",R.drawable.swimming_pool);
    }

    ArrayList<ModelHotel> cloneData() {
        ArrayList<ModelHotel> clone = new ArrayList<>(listHotel.size());
        clone.addAll(listHotel);
        return clone;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onResume() {
        super.onResume();
        if (preferenceHandler.getPref()!=null) {
            preferences = preferenceHandler.getPref();
            ArrayList<ArrayList> hasil = normalisasi(preferences);
            sortedListHotel = new ArrayList<>();
            for(int i =9;i>=0;i--){
                sortedListHotel.add(preferenceHandler.getListHotel().get(Integer.parseInt(String.valueOf(hasil.get(0).get(i)))-1));
            }
            adapter = new RecyclerAdapter(this, sortedListHotel);
        }else{
            adapter = new RecyclerAdapter(this, preferenceHandler.getListHotel());
        }
        clonedListHotel = cloneData();
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    void prepareFilter() {
        listFilter.clear();
        filterProduk = new ArrayList<>();
        filterPelayanan = new ArrayList<>();
        if (listCheckBox.get(0).isChecked()) {
            filterProduk.add(2);
            filterPelayanan.add(32 - 27);
        }
        if (listCheckBox.get(1).isChecked()) {
            filterProduk.add(3);
            filterPelayanan.add(33 - 27);
        }
        if (listCheckBox.get(2).isChecked()) {
            filterProduk.add(4);
            filterPelayanan.add(45 - 27);
        }
        if (listCheckBox.get(3).isChecked()) {
            filterProduk.add(5);
            filterPelayanan.add(35 - 27);
        }
        if (listCheckBox.get(4).isChecked()) {
            filterProduk.add(8);
        }
        if (listCheckBox.get(5).isChecked()) {
            filterProduk.add(24);
        }
        if (listCheckBox.get(6).isChecked()) {
            filterProduk.add(25);
            filterPelayanan.add(41 - 27);
        }
        if (listCheckBox.get(7).isChecked()) {
            filterProduk.add(26);
        }
        Log.d("CHECKBOX ", "prepareFilter: ");

    }

    void prepareData() {
        for (int k = 0; k < 10; k++) {
            ArrayList<String> facilityException = listHotel.get(k).getFacilityException();
            double bobotProdukAkhir = 20.5;
            double bobotPelayananAkhir = 14;
            if (!facilityException.isEmpty()) {
                for (String i : facilityException) {
                    switch (i) {
                        case "spa":
                            bobotProdukAkhir -= 1;
                            bobotPelayananAkhir -= 3;
                            break;
                        case "kolam":
                            bobotProdukAkhir -= 0.5;
                            break;
                        case "olg":
                            bobotPelayananAkhir -= 1;
                            break;
                    }
                }
            }
            bobot_produk.add((float) (hitungBobot(index_produk, produk_value, k) / bobotProdukAkhir));
            bobot_pelayanan.add((float) (hitungBobot(index_pelayanan, pelayanan_value, k) / bobotPelayananAkhir));
            bobot_pengelolaan.add((float) (hitungBobot(index_pengelolaan, pengelolaan_value, k) / 2));

            listHotel.get(k).setBobot_produk(bobot_produk.get(k));
            listHotel.get(k).setBobot_pelayanan(bobot_pelayanan.get(k));
            listHotel.get(k).setBobot_pengelolaan(bobot_pengelolaan.get(k));
        }
//        ArrayList<ArrayList<Float>> matrix = new ArrayList<>();
        matrix.add(bobot_produk);
        matrix.add(bobot_pelayanan);
        matrix.add(bobot_pengelolaan);
        new PreferenceHandler(this).setMatrix(matrix);

    }

    void addFacilityException(int i, String[] facility) {
        for (String f : facility) {
            listHotel.get(i).getFacilityException().add(f);
        }
    }

    void cleanData(@NonNull ArrayList<String[]> data) {
        for(int i =0;i<11;i++){
            ArrayList<String> tmp_produk = new ArrayList<>();
            ArrayList<String> tmp_pelayanan = new ArrayList<>();
            ArrayList<String> tmp_pengelolaan = new ArrayList<>();
            String tmp_umum="";

            for (int j = 0; j < data.size(); j++) {
                if (j < 27) {
                    if (i == 0) index_produk.add(data.get(j)[i]);
                    else tmp_produk.add(data.get(j)[i]);

                } else if (j < 47) {
                    if (i == 0) index_pelayanan.add(data.get(j)[i]);
                    else tmp_pelayanan.add(data.get(j)[i]);
                } else if(j<data.size()-1){
                    if (i == 0) index_pengelolaan.add(data.get(j)[i]);
                    else tmp_pengelolaan.add(data.get(j)[i]);
                }else{
                    if(i!=0) tmp_umum= data.get(j)[i];
                }
            }
            if (i > 0) {
                produk_value.add(tmp_produk);
                pelayanan_value.add(tmp_pelayanan);
                pengelolaan_value.add(tmp_pengelolaan);
                bobot_umum.add(Float.parseFloat(tmp_umum));
                listHotel.get(i-1).setRating_umum(Float.parseFloat(tmp_umum)*5);
            }
        }

        matrix.add(bobot_umum);
    }

    float hitungBobot(ArrayList<String> index, ArrayList<ArrayList<String>> value, int j) {
        float sum = 0;
        for (int i = 0; i < index.size(); i++) {
            if (index.get(i).equals("M") && value.get(j).get(i).equals("1")) sum += 1;
            if (index.get(i).equals("TM") && value.get(j).get(i).equals("1")) sum += 0.5;
        }
        return sum;
    }

    void prepareVariable() {
        btnOpenFilter = findViewById(R.id.btn_open_filter);
        listFilter = new ArrayList<>();
        btnOpenPref = findViewById(R.id.btn_open_preference);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void filteringHotel() {
        prepareFilter();
        ArrayList<Integer> filtered = new ArrayList<>();
        List<Integer> filteredHotel;

        for (int i = 0; i < produk_value.size(); i++) {
            boolean acc = true;
            for (int j : filterProduk) {
                if (!produk_value.get(i).get(j).equals("1")) {
                    acc = false;
                    break;
                }
            }
            if (acc) {
                filtered.add(i);
            }
        }
        for (int i = 0; i < pelayanan_value.size(); i++) {
            boolean acc = true;
            for (int j : filterPelayanan) {
                if (!pelayanan_value.get(i).get(j).equals("1")) {
                    acc = false;
                    break;
                }
            }
            if (acc) {
                filtered.add(i);
            }
        }
        filteredHotel = filtered.stream().filter(d -> Collections.frequency(filtered, d) > 1).distinct().collect(Collectors.toList());

        Log.d("FILTER", "filteringHotel: ");
        clonedListHotel.clear();
        for (int j : filteredHotel) {
            for (int i = 0; i < listHotel.size(); i++) {
                if (i == j) clonedListHotel.add(listHotel.get(i));
            }
        }
        RecyclerAdapter newAdapter = new RecyclerAdapter(this, clonedListHotel);
        recyclerView.setAdapter(newAdapter);
    }

    void hitungRating() {
        listRating = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            float x = ((bobot_produk.get(i) + bobot_pelayanan.get(i) + bobot_pengelolaan.get(i)) / 3);
            float bobot = Float.parseFloat(new DecimalFormat("#.00").format(x * 5));
            listRating.add(bobot);
        }
        for (int i = 0; i < listHotel.size(); i++) {
            listHotel.get(i).setRating_syariah(listRating.get(i).toString());
            listHotel.get(i).setProduk(Float.parseFloat(new DecimalFormat("#.00").format(bobot_produk.get(i) * 5)));
            listHotel.get(i).setPelayanan(Float.parseFloat(new DecimalFormat("#.00").format(bobot_pelayanan.get(i) * 5)));
            listHotel.get(i).setPengelolaan(Float.parseFloat(new DecimalFormat("#.00").format(bobot_pengelolaan.get(i) * 5)));

        }

        Log.d("BOBOT", "hitungRating: ");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    ArrayList<ArrayList> normalisasi(ArrayList<ArrayList<Float>> prefList){
        ArrayList<ArrayList<Float>> matrix = new PreferenceHandler(this).getMatrix();
        float[] preferensi=prefToArray(prefList);

        Map<String,Float> hasil=new HashMap<>();
        for(int i=0;i<10;i++){
            float pre_hasil=0;
            for(int j =0;j<4;j++){
//                ArrayList<Float> sorted=new ArrayList<>(matrix.get(j));
//                Collections.sort(sorted);
                float bobot_alternatif = matrix.get(j).get(i);
//                float x_min= sorted.get(0);
//                float x_max= sorted.get(sorted.size()-1);
                pre_hasil+=preferensi[j]*(bobot_alternatif);
            }
            hasil.put(Integer.toString(i+1),pre_hasil);
        }
        Map<String, Float> sorted = sortByValue(hasil);

        ArrayList<ArrayList> sorted_hasil = new ArrayList<>();
        ArrayList<String> list_key = new ArrayList<>();
        ArrayList<Float> list_value= new ArrayList<>();
        for(int i =0;i<hasil.size();i++){
            String key =(String) Objects.requireNonNull(sorted.keySet().toArray())[i];
            Float value = sorted.get(key);
            list_key.add(key);
            list_value.add(value);
        }
        sorted_hasil.add(list_key);
        sorted_hasil.add(list_value);
        return sorted_hasil;
    }

    float[] prefToArray(ArrayList<ArrayList<Float>> preference){
        float pr,pl,pg,x,y,z,sum=0;

        for(float i : preference.get(0)){
            sum+=i;
        }
        x=sum/preference.get(0).size();
        sum=0;

        for(float i : preference.get(1)){
            sum+=i;
        }
        y=sum/preference.get(1).size();
        sum=0;

        for(float i : preference.get(2)){
            sum+=i;
        }
        z=sum/preference.get(2).size();
        sum=0;

        for(float i : preference.get(3)){
            sum+=i;
        }
        float zz=sum/preference.get(3).size();

        sum=x+y+z;
        pr=x/sum;
        pl=y/sum;
        pg=z/sum;

        return new float[] {x,y,z,zz};
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void openFilterFragment() {
        View view = getLayoutInflater().inflate(R.layout.fragment_filter,null);
        Dialog dialog = new BottomSheetDialog(this);
        Button btn_closeDialog = view.findViewById(R.id.btn_close_filter);
        Button btn_filter = view.findViewById(R.id.btn_filter);

        btn_closeDialog.setOnClickListener(v->dialog.dismiss());
        btn_filter.setOnClickListener(v->{
            listCheckBox = new ArrayList<>();
            listCheckBox.add(view.findViewById(R.id.cb1));
            listCheckBox.add(view.findViewById(R.id.cb2));
            listCheckBox.add(view.findViewById(R.id.cb3));
            listCheckBox.add(view.findViewById(R.id.cb4));
            listCheckBox.add(view.findViewById(R.id.cb5));
            listCheckBox.add(view.findViewById(R.id.cb6));
            listCheckBox.add(view.findViewById(R.id.cb7));
            listCheckBox.add(view.findViewById(R.id.cb8));
            filteringHotel();
            dialog.dismiss();
        });

        dialog.setContentView(view);
        dialog.show();

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    void onClickController() {
        btnOpenFilter.setOnClickListener(v -> {
            openFilterFragment();
        });

        btnOpenPref.setOnClickListener(v->{
            startActivity(new Intent(this,PreferenceScreen.class));
        });
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> unsortMap) {

        List<Map.Entry<K, V>> list =
                new LinkedList<>(unsortMap.entrySet());

        Collections.sort(list, (o1, o2) -> (o1.getValue()).compareTo(o2.getValue()));

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;

    }
}
