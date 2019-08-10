package com.example.hotelsyaria;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

public class MainActivity extends AppCompatActivity {
    private SeekBar seekBar_pelayanan,seekBar_produk,seekBar_pengelolaan;
    private TextView sb_progress_pelayanan,sb_progress_produk,sb_progress_pengelolaan;
    RecyclerView recyclerView;
    public ArrayList<ModelHotel> clonedListHotel,listHotel;
    Button btnOpenFilter,btnFilter,btnCloseFilter;
    ArrayList<CheckBox> listCheckBox;
    ArrayList<ArrayList<Integer>> listFilter;
    ArrayList<Integer> filterProduk,filterPelayanan;
    ArrayList<String> index_produk,index_pelayanan,index_pengelolaan;
    ArrayList<ArrayList<Integer>> produk_value,pelayanan_value,pengelolaan_value;
    ArrayList<Float> bobot_produk,bobot_pelayanan,bobot_pengelolaan;
    public ArrayList<Float> listRating;
    ConstraintLayout filterLayout;
    RecyclerAdapter adapter;


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
        index_produk=new ArrayList<>();
        index_pelayanan=new ArrayList<>();
        index_pengelolaan=new ArrayList<>();
        produk_value=new ArrayList<>();
        pelayanan_value=new ArrayList<>();
        pengelolaan_value=new ArrayList<>();
        bobot_pelayanan=new ArrayList<>();
        bobot_produk=new ArrayList<>();
        bobot_pengelolaan=new ArrayList<>();

        InputStream inputStream = getResources().openRawResource(R.raw.data);
        ArrayList<String[]> data = new ArrayList<>();
        try {
            InputStreamReader is = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(is);
            reader.readLine();
            String line;

            while ((line = reader.readLine()) != null) {
                String[] txt=line.split(";");
                data.add(txt);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        cleanData(data);
        prepareData();
        prepareVariable();
        onClickController();

        listHotel = new ArrayList<>();
        listHotel.add(new ModelHotel("Hotel A","Jalan Buah Batu"));
        listHotel.add(new ModelHotel("Hotel B","Jalan Karapitan"));
        listHotel.add(new ModelHotel("Hotel C","Jalan Pasadena"));
        listHotel.add(new ModelHotel("Hotel D","Jalan Buah Batu"));
        listHotel.add(new ModelHotel("Hotel E","Jalan Karapitan"));
        listHotel.add(new ModelHotel("Hotel F","Jalan Pasadena"));
        listHotel.add(new ModelHotel("Hotel G","Jalan Buah Batu"));
        listHotel.add(new ModelHotel("Hotel H","Jalan Karapitan"));
        listHotel.add(new ModelHotel("Hotel I","Jalan Pasadena"));
        listHotel.add(new ModelHotel("Hotel J","Jalan Pasadena"));
        clonedListHotel=cloneData();
        hitungRating();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new RecyclerAdapter(this,clonedListHotel);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }
    ArrayList<ModelHotel> cloneData(){
        ArrayList<ModelHotel> clone = new ArrayList<>(listHotel.size());
        for(ModelHotel item : listHotel) clone.add(item);
        return clone;
    }

    void preparePreferences(){
        listFilter.clear();
        filterProduk = new ArrayList<>();
        filterPelayanan = new ArrayList<>();
        if(listCheckBox.get(0).isChecked()){
            filterProduk.add(2);
            filterPelayanan.add(32-27);
        }
        if(listCheckBox.get(1).isChecked()){
            filterProduk.add(3);
            filterPelayanan.add(33-27);
        }
        if(listCheckBox.get(2).isChecked()){
            filterProduk.add(4);
            filterPelayanan.add(45-27);
        }
        if(listCheckBox.get(3).isChecked()){
            filterProduk.add(5);
            filterPelayanan.add(35-27);
        }
        if(listCheckBox.get(4).isChecked()){
            filterProduk.add(8);
        }
        if(listCheckBox.get(5).isChecked()){
            filterProduk.add(24);
        }
        if(listCheckBox.get(6).isChecked()){
            filterProduk.add(25);
            filterPelayanan.add(41-27);
        }
        if(listCheckBox.get(7).isChecked()){
            filterProduk.add(26);
        }
        Log.d("CHECKBOX ", "preparePreferences: ");

    }
    void prepareData(){
        for(int k=0;k<10;k++){
            bobot_produk.add((float) (hitungBobot(index_produk,produk_value,k)/20.5));
            bobot_pelayanan.add((float) (hitungBobot(index_pelayanan,pelayanan_value,k)/14));
            bobot_pengelolaan.add((float) (hitungBobot(index_pengelolaan,pengelolaan_value,k)/2));
        }
    }

    void cleanData(@NonNull ArrayList<String[]> data){
        for(int i =0;i<11;i++){
            ArrayList<Integer> tmp_produk = new ArrayList<>();
            ArrayList<Integer> tmp_pelayanan= new ArrayList<>();
            ArrayList<Integer> tmp_pengelolaan= new ArrayList<>();
            for(int j=0;j<data.size();j++){
                if(j<27){
                    if(i==0)index_produk.add(data.get(j)[i]);
                    else tmp_produk.add(Integer.parseInt(data.get(j)[i]));

                }
                else if(j < 47) {
                    if(i==0)index_pelayanan.add(data.get(j)[i]);
                    else tmp_pelayanan.add(Integer.parseInt(data.get(j)[i]));
                }
                else {
                    if(i==0)index_pengelolaan.add(data.get(j)[i]);
                    else tmp_pengelolaan.add(Integer.parseInt(data.get(j)[i]));
                }
            }
            if(i>0){
                produk_value.add(tmp_produk);
                pelayanan_value.add(tmp_pelayanan);
                pengelolaan_value.add(tmp_pengelolaan);
            }
        }
    }

    float hitungBobot(ArrayList<String> index,ArrayList<ArrayList<Integer>>value,int j){
        float sum=0;
        for(int i =0;i<index.size();i++){
            if(index.get(i).equals("M") && value.get(j).get(i)==1) sum+=1;
            if(index.get(i).equals("TM") && value.get(j).get(i)==1) sum+=0.5;
        }
        return sum;
    }

    void prepareVariable(){
        listCheckBox = new ArrayList<>();
        listCheckBox.add(findViewById(R.id.cb1));
        listCheckBox.add(findViewById(R.id.cb2));
        listCheckBox.add(findViewById(R.id.cb3));
        listCheckBox.add(findViewById(R.id.cb4));
        listCheckBox.add(findViewById(R.id.cb5));
        listCheckBox.add(findViewById(R.id.cb6));
        listCheckBox.add(findViewById(R.id.cb7));
        listCheckBox.add(findViewById(R.id.cb8));

        btnOpenFilter = findViewById(R.id.btn_open_filter);
        btnFilter=findViewById(R.id.btn_filter);
        btnCloseFilter=findViewById(R.id.btn_close_filter);
        listFilter = new ArrayList<>();

        filterLayout=findViewById(R.id.layout_filter);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void filteringHotel(){
        preparePreferences();
        ArrayList<Integer> filtered = new ArrayList<>();
        List<Integer> filteredHotel = new ArrayList<>();

        for(int i=0;i<produk_value.size();i++){
            boolean acc = true;
            for(int j : filterProduk){
                if(produk_value.get(i).get(j)!=1){
                    acc=false;
                    break;
                }
            }
            if(acc){
                filtered.add(i);
            }
        }
        for(int i=0;i<pelayanan_value.size();i++){
            boolean acc = true;
            for(int j : filterPelayanan){
                if(pelayanan_value.get(i).get(j)!=1){
                    acc=false;
                    break;
                }
            }
            if(acc){
                filtered.add(i);
            }
        }
//        filteredHotel=filtered.stream().distinct().collect(Collectors.toList());
        filteredHotel=filtered.stream().filter(d-> Collections.frequency(filtered,d)>1).distinct().collect(Collectors.toList());

        Log.d("FILTER", "filteringHotel: ");
        clonedListHotel.clear();
        for(int j:filteredHotel){
            for(int i =0;i<listHotel.size();i++){
                if(i==j) clonedListHotel.add(listHotel.get(i));
            }
        }
        RecyclerAdapter newAdapter = new RecyclerAdapter(this,clonedListHotel);
        recyclerView.setAdapter(newAdapter);
        filterLayout.setVisibility(View.INVISIBLE);
        btnOpenFilter.setVisibility(View.VISIBLE);
//        return filteredHotel;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void onClickController(){
        btnOpenFilter.setOnClickListener(v -> {
            btnOpenFilter.setVisibility(View.INVISIBLE);
            filterLayout.setVisibility(View.VISIBLE);
        });

        btnCloseFilter.setOnClickListener(v -> {
            btnOpenFilter.setVisibility(View.VISIBLE);
            filterLayout.setVisibility(View.INVISIBLE);
        });

        btnFilter.setOnClickListener(v -> filteringHotel());
    }

    void hitungRating(){
        listRating = new ArrayList<>();
        for(int i=0;i<10;i++){
            float x =  ((bobot_produk.get(i)+bobot_pelayanan.get(i)+bobot_pengelolaan.get(i))/3);
            float bobot = Float.parseFloat(new DecimalFormat("#.00").format(x*5));
            listRating.add(bobot);
        }
        for(int i =0;i<listHotel.size();i++){
            listHotel.get(i).setRating_syariah(listRating.get(i).toString());
            listHotel.get(i).setProduk(Float.parseFloat(new DecimalFormat("#.00").format(bobot_produk.get(i)*5)));
            listHotel.get(i).setPelayanan(Float.parseFloat(new DecimalFormat("#.00").format(bobot_pelayanan.get(i)*5)));
            listHotel.get(i).setPengelolaan(Float.parseFloat(new DecimalFormat("#.00").format(bobot_pengelolaan.get(i)*5)));

        }

        Log.d("BOBOT", "hitungRating: ");
    }
}
