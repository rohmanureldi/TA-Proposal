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
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
    ArrayList<Float> bobot_produk, bobot_pelayanan, bobot_pengelolaan,listRating;
    public ArrayList<ArrayList<Float>> preferences;

    ConstraintLayout filterLayout;
    RecyclerAdapter adapter;
    filterFragment filter_fragment;
    FragmentTransaction transaction;

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
        preferences = new ArrayList<>();
        PreferenceHandler preferenceHandler = new PreferenceHandler(this);



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

        cleanData(data);
        prepareVariable();
        onClickController();

        listHotel = new ArrayList<>();
        listHotel.add(new ModelHotel("Hotel Grand Asrilia", "Jl. Pelajar Pejuang 45 No. 123"));
        listHotel.add(new ModelHotel("Hotel Horison", "Jl. Pelajar Pejuang 45 No. 121"));
        listHotel.add(new ModelHotel("Hotel Yello", "Jl. Pasir Kaliki No. 25"));
        listHotel.add(new ModelHotel("Hotel Aston Pasteur", "Jl. Dr. Djunjunan No. 162"));
        listHotel.add(new ModelHotel("Hotel Aston Braga ", "Jl. Braga No. 99"));
        listHotel.add(new ModelHotel("Hotel Four Points", "Jl. Ir. H. Djuanda No. 46"));
        listHotel.add(new ModelHotel("Hotel Hilton", " Jl. HOS Tjokroaminoto No.41"));
        listHotel.add(new ModelHotel("Shakti Hotel Bandung", "Jl. Soekarno Hatta No.753"));
        listHotel.add(new ModelHotel("Hotel I", "Jalan Pasadena"));
        listHotel.add(new ModelHotel("Hotel J", "Jalan Pasadena"));

        addFacilityException(0, new String[]{"spa", "olg"});
        addFacilityException(5, new String[]{"spa"});
        prepareData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        if (preferenceHandler.getPref()!=null) {
            Type listType = new  TypeToken<ArrayList<ArrayList<Float>>>() {}.getType();
            preferences = preferenceHandler.getPref();
            ArrayList<ArrayList> hasil = normalisasi(preferences);
            sortedListHotel = new ArrayList<>();
            for(int i =0;i<10;i++){
                sortedListHotel.add(listHotel.get(Integer.parseInt(String.valueOf(hasil.get(0).get(i)))-1));
            }
            adapter = new RecyclerAdapter(this, sortedListHotel);
        }else{
            clonedListHotel = cloneData();
            adapter = new RecyclerAdapter(this, clonedListHotel);
        }
        hitungRating();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    ArrayList<ModelHotel> cloneData() {
        ArrayList<ModelHotel> clone = new ArrayList<>(listHotel.size());
        clone.addAll(listHotel);
        return clone;
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
        ArrayList<ArrayList<Float>> matrix = new ArrayList<>();
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
        for (int i = 0; i < 11; i++) {
            ArrayList<String> tmp_produk = new ArrayList<>();
            ArrayList<String> tmp_pelayanan = new ArrayList<>();
            ArrayList<String> tmp_pengelolaan = new ArrayList<>();
            for (int j = 0; j < data.size(); j++) {
                if (j < 27) {
                    if (i == 0) index_produk.add(data.get(j)[i]);
                    else tmp_produk.add(data.get(j)[i]);

                } else if (j < 47) {
                    if (i == 0) index_pelayanan.add(data.get(j)[i]);
                    else tmp_pelayanan.add(data.get(j)[i]);
                } else {
                    if (i == 0) index_pengelolaan.add(data.get(j)[i]);
                    else tmp_pengelolaan.add(data.get(j)[i]);
                }
            }
            if (i > 0) {
                produk_value.add(tmp_produk);
                pelayanan_value.add(tmp_pelayanan);
                pengelolaan_value.add(tmp_pengelolaan);
            }
        }
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
        List<Integer> filteredHotel = new ArrayList<>();

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
            for(int j =0;j<3;j++){
                ArrayList<Float> sorted=new ArrayList<>(matrix.get(j));
                Collections.sort(sorted);
                float bobot_alternatif = matrix.get(j).get(i);
                float x_min= sorted.get(0);
                float x_max= sorted.get(sorted.size()-1);
                pre_hasil+=preferensi[j]*(bobot_alternatif-x_min)/(x_min+x_max);
            }
            hasil.put(Integer.toString(i+1),pre_hasil);
        }
        Map<String, Float> sorted = hasil
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2,
                        LinkedHashMap::new));


        ArrayList<ArrayList> sorted_hasil = new ArrayList<>();
        ArrayList<String> list_key = new ArrayList<>();
        ArrayList<Float> list_value= new ArrayList<>();
        for(int i =0;i<hasil.size();i++){
            String key =(String) sorted.keySet().toArray()[i];
            Float value =(float) sorted.get(key);
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

        sum=x+y+z;
        pr=x/sum;
        pl=y/sum;
        pg=z/sum;

        return new float[] {pr,pl,pg};
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
}
