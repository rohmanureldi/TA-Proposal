//package com.example.hotelsyaria;
//
//import android.os.Build;
//import android.support.annotation.NonNull;
//import android.support.annotation.RequiresApi;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.text.DecimalFormat;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//import static java.util.stream.Collectors.toMap;
//
//public class HasilActivity extends AppCompatActivity {
//    RecyclerView recyclerView;
//    ArrayList<String> index_produk,index_pelayanan,index_pengelolaan;
//    ArrayList<ArrayList<Integer>> produk_value,pelayanan_value,pengelolaan_value;
//    ArrayList<Float> bobot_produk,bobot_pelayanan,bobot_pengelolaan;
//    float preference[];
//    public ArrayList<ModelHotel> sortedListHotel,listHotel;
//    public ArrayList<ArrayList> sorted_hasil;
//    public ArrayList<Float> listRating;
//
//    @RequiresApi(api = Build.VERSION_CODES.N)
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_hasil);
//        preference = getIntent().getFloatArrayExtra("pref");
//        recyclerView = findViewById(R.id.recycler);
//        index_produk=new ArrayList<>();
//        index_pelayanan=new ArrayList<>();
//        index_pengelolaan=new ArrayList<>();
//        produk_value=new ArrayList<>();
//        pelayanan_value=new ArrayList<>();
//        pengelolaan_value=new ArrayList<>();
//        bobot_pelayanan=new ArrayList<>();
//        bobot_produk=new ArrayList<>();
//        bobot_pengelolaan=new ArrayList<>();
//
//
//
//
//        InputStream inputStream = getResources().openRawResource(R.raw.data);
//        ArrayList<String[]> data = new ArrayList<>();
//        try {
//            InputStreamReader is = new InputStreamReader(inputStream);
//            BufferedReader reader = new BufferedReader(is);
//            reader.readLine();
//            String line;
//
//            while ((line = reader.readLine()) != null) {
//                String[] txt=line.split(";");
//                data.add(txt);
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        cleanData(data);
//        prepareData();
//        ArrayList<ArrayList> hasil = normalisasi(preference);
//
//        listHotel = new ArrayList<>();
////        listHotel.add(new ModelHotel("Hotel A","Jalan Buah Batu", Float.toString(Float.parseFloat(new DecimalFormat("#.00").format(bobot_produk.get(0)*5)))));
////        listHotel.add(new ModelHotel("Hotel B","Jalan Karapitan", Float.toString(Float.parseFloat(new DecimalFormat("#.00").format(bobot_produk.get(1)*5)))));
////        listHotel.add(new ModelHotel("Hotel C","Jalan Pasadena", Float.toString(Float.parseFloat(new DecimalFormat("#.00").format(bobot_produk.get(2)*5)))));
////        listHotel.add(new ModelHotel("Hotel D","Jalan Buah Batu", Float.toString(Float.parseFloat(new DecimalFormat("#.00").format(bobot_produk.get(3)*5)))));
////        listHotel.add(new ModelHotel("Hotel E","Jalan Karapitan", Float.toString(Float.parseFloat(new DecimalFormat("#.00").format(bobot_produk.get(4)*5)))));
////        listHotel.add(new ModelHotel("Hotel F","Jalan Pasadena", Float.toString(Float.parseFloat(new DecimalFormat("#.00").format(bobot_produk.get(5)*5)))));
////        listHotel.add(new ModelHotel("Hotel G","Jalan Buah Batu", Float.toString(Float.parseFloat(new DecimalFormat("#.00").format(bobot_produk.get(6)*5)))));
////        listHotel.add(new ModelHotel("Hotel H","Jalan Karapitan", Float.toString(Float.parseFloat(new DecimalFormat("#.00").format(bobot_produk.get(7)*5)))));
////        listHotel.add(new ModelHotel("Hotel I","Jalan Pasadena", Float.toString(Float.parseFloat(new DecimalFormat("#.00").format(bobot_produk.get(8)*5)))));
////        listHotel.add(new ModelHotel("Hotel J","Jalan Pasadena", Float.toString(Float.parseFloat(new DecimalFormat("#.00").format(bobot_produk.get(9)*5)))));
//        hitungRating();
//
//
//
//        sortedListHotel = new ArrayList<>();
//        for(int i =0;i<10;i++){
//            sortedListHotel.add(listHotel.get(Integer.parseInt(String.valueOf(hasil.get(0).get(i)))-1));
//        }
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        RecyclerAdapter adapter = new RecyclerAdapter(this,sortedListHotel);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(layoutManager);
//
//    }
//    void cleanData(@NonNull ArrayList<String[]> data){
//        for(int i =0;i<11;i++){
//            ArrayList<Integer> tmp_produk = new ArrayList<>();
//            ArrayList<Integer> tmp_pelayanan= new ArrayList<>();
//            ArrayList<Integer> tmp_pengelolaan= new ArrayList<>();
//            for(int j=0;j<data.size();j++){
//                if(j<27){
//                    if(i==0)index_produk.add(data.get(j)[i]);
//                    else tmp_produk.add(Integer.parseInt(data.get(j)[i]));
//
//                }
//                else if(j < 47) {
//                    if(i==0)index_pelayanan.add(data.get(j)[i]);
//                    else tmp_pelayanan.add(Integer.parseInt(data.get(j)[i]));
//                }
//                else {
//                    if(i==0)index_pengelolaan.add(data.get(j)[i]);
//                    else tmp_pengelolaan.add(Integer.parseInt(data.get(j)[i]));
//                }
//            }
//            if(i>0){
//                produk_value.add(tmp_produk);
//                pelayanan_value.add(tmp_pelayanan);
//                pengelolaan_value.add(tmp_pengelolaan);
//            }
//        }
//    }
//    void prepareData(){
//        for(int k=0;k<10;k++){
//            bobot_produk.add((float) (hitungBobot(index_produk,produk_value,k)/20.5));
//            bobot_pelayanan.add((float) (hitungBobot(index_pelayanan,pelayanan_value,k)/14));
//            bobot_pengelolaan.add((float) (hitungBobot(index_pengelolaan,pengelolaan_value,k)/2));
//        }
//    }
//
//    float hitungBobot(ArrayList<String> index,ArrayList<ArrayList<Integer>>value,int j){
//        float sum=0;
//        for(int i =0;i<index.size();i++){
//            if(index.get(i).equals("M") && value.get(j).get(i)==1) sum+=1;
//            if(index.get(i).equals("TM") && value.get(j).get(i)==1) sum+=0.5;
//        }
//        return sum;
//    }
//
//    void hitungRating(){
//        listRating = new ArrayList<>();
//        for(int i=0;i<10;i++){
//            float x = (float) ((bobot_produk.get(i)+bobot_pelayanan.get(i)+bobot_pengelolaan.get(i))/3);
//            float bobot = Float.parseFloat(new DecimalFormat("#.00").format(x*5));
//            listRating.add(bobot);
//        }
//        for(int i =0;i<listHotel.size();i++){
//            listHotel.get(i).setRating_umum(listRating.get(i));
//            listHotel.get(i).setProduk(Float.parseFloat(new DecimalFormat("#.00").format(bobot_produk.get(i)*5)));
//            listHotel.get(i).setPelayanan(Float.parseFloat(new DecimalFormat("#.00").format(bobot_pelayanan.get(i)*5)));
//            listHotel.get(i).setPengelolaan(Float.parseFloat(new DecimalFormat("#.00").format(bobot_pengelolaan.get(i)*5)));
//        }
//
//        Log.d("BOBOT", "hitungRating: ");
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.N)
//    ArrayList<ArrayList> normalisasi(float preferensi[]){
//        ArrayList<ArrayList<Float>> matrix = new ArrayList<>();
//        matrix.add(bobot_produk);
//        matrix.add(bobot_pelayanan);
//        matrix.add(bobot_pengelolaan);
//
//        Map<String,Float> hasil=new HashMap<>();
//        for(int i=0;i<10;i++){
//            float pre_hasil=0;
//            for(int j =0;j<3;j++){
//                ArrayList<Float> sorted=new ArrayList<>(matrix.get(j));
//                Collections.sort(sorted);
//                float bobot_alternatif = matrix.get(j).get(i);
//                float x_min= sorted.get(0);
//                float x_max= sorted.get(sorted.size()-1);
//                pre_hasil+=preferensi[j]*(bobot_alternatif-x_min)/(x_min+x_max);
//            }
//            hasil.put(Integer.toString(i+1),pre_hasil);
//        }
//        Map<String, Float> sorted = hasil
//                .entrySet()
//                .stream()
//                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
//                .collect(toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2,
//                        LinkedHashMap::new));
//
//
//        sorted_hasil = new ArrayList<>();
//        ArrayList<String> list_key = new ArrayList<>();
//        ArrayList<Float> list_value= new ArrayList<>();
//        for(int i =0;i<hasil.size();i++){
//            String key =(String) sorted.keySet().toArray()[i];
//            Float value =(float) sorted.get(key);
//            list_key.add(key);
//            list_value.add(value);
//        }
//        sorted_hasil.add(list_key);
//        sorted_hasil.add(list_value);
//        return sorted_hasil;
//    }
//
//}
