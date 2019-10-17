package com.example.hotelsyaria;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import static java.util.stream.Collectors.toMap;

public class HasilActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    public ArrayList<ModelHotel> clonedListHotel, listHotel,sortedListHotel;
//    Button btnOpenFilter,btnOpenPref;
//    ArrayList<CheckBox> listCheckBox;
//    ArrayList<ArrayList<Integer>> listFilter;
//    ArrayList<Integer> filterProduk, filterPelayanan;
    ArrayList<String> index_produk, index_pelayanan, index_pengelolaan;
    ArrayList<ArrayList<String>> produk_value, pelayanan_value, pengelolaan_value;
    ArrayList<Float> bobot_produk, bobot_pelayanan, bobot_pengelolaan,bobot_umum,listRating;
    ArrayList<ArrayList<Float>> matrix;
    public ArrayList<ArrayList<Float>> preferences;
    PreferenceHandler preferenceHandler;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil);


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
                String[] txt=line.split(";");
                data.add(txt);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }




        listHotel = new ArrayList<>();
        listHotel = new ArrayList<>();
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
        addFacilityException(0, new String[]{"spa", "olg"});
        addFacilityException(5, new String[]{"spa"});
        cleanData(data);
        prepareData();
        hitungRatingSyariah();
        ArrayList<ArrayList> hasil = normalisasi(getIntent().getFloatArrayExtra("preference"));



        sortedListHotel = new ArrayList<>();
        for(int i =9;i>=0;i--){
            sortedListHotel.add(listHotel.get(Integer.parseInt(String.valueOf(hasil.get(0).get(i)))-1));
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerAdapter adapter = new RecyclerAdapter(this,sortedListHotel);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

    }

    void cleanData(@NonNull ArrayList<String[]> data){
//        ArrayList<String> tmp_umum = new ArrayList<>();
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
//                        tmp_umum.add(data.get(j)[i]);
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

    void prepareData(){
        for (int k = 0; k < 10; k++) {
            ArrayList<String> facilityException =listHotel.get(k).getFacilityException();
            double maxBobotProduk = 20.5;
            double maxBobotPelayanan = 14;
            if (!facilityException.isEmpty()) {
                for (String i : facilityException) {
                    switch (i) {
                        case "spa":
                            maxBobotProduk -= 1;
                            maxBobotPelayanan -= 3;
                            break;
                        case "kolam":
                            maxBobotProduk -= 0.5;
                            break;
                        case "olg":
                            maxBobotPelayanan -= 1;
                            break;
                    }
                }
            }
            bobot_produk.add((float) (hitungBobotHotel(index_produk, produk_value, k) / maxBobotProduk));
            bobot_pelayanan.add((float) (hitungBobotHotel(index_pelayanan, pelayanan_value, k) / maxBobotPelayanan));
            bobot_pengelolaan.add(hitungBobotHotel(index_pengelolaan, pengelolaan_value, k) / 2);

            listHotel.get(k).setBobot_produk(bobot_produk.get(k));
            listHotel.get(k).setBobot_pelayanan(bobot_pelayanan.get(k));
            listHotel.get(k).setBobot_pengelolaan(bobot_pengelolaan.get(k));
        }

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

    float hitungBobotHotel(ArrayList<String> index, ArrayList<ArrayList<String>> value, int j) {
        float sum = 0;
        for (int i = 0; i < index.size(); i++) {
            if (index.get(i).equals("M") && value.get(j).get(i).equals("1")) sum += 1;
            if (index.get(i).equals("TM") && value.get(j).get(i).equals("1")) sum += 0.5;
        }
        return sum;
    }

    void hitungRatingSyariah(){
        listRating = new ArrayList<>();
        for(int i=0;i<10;i++){
            float x = (float) ((bobot_produk.get(i)+bobot_pelayanan.get(i)+bobot_pengelolaan.get(i))/3);
            float bobot = Float.parseFloat(new DecimalFormat("#.00").format(x*5));
            listRating.add(bobot);
        }
        for(int i =0;i<listHotel.size();i++){
            listHotel.get(i).setRating_syariah(listRating.get(i).toString());
            listHotel.get(i).setProduk(Float.parseFloat(new DecimalFormat("#.00").format(bobot_produk.get(i)*5)));
            listHotel.get(i).setPelayanan(Float.parseFloat(new DecimalFormat("#.00").format(bobot_pelayanan.get(i)*5)));
            listHotel.get(i).setPengelolaan(Float.parseFloat(new DecimalFormat("#.00").format(bobot_pengelolaan.get(i)*5)));
        }

        Log.d("BOBOT", "hitungRatingSyariah: ");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    ArrayList<ArrayList> normalisasi(float preferensi[]){
        ArrayList<ArrayList<Float>> matrix = new PreferenceHandler(this).getMatrix();

        Map<String,Float> hasil=new HashMap<>();
        for(int i=0;i<10;i++){
            float pre_hasil=0;
            for(int j =0;j<4;j++){
                ArrayList<Float> sorted=new ArrayList<>(matrix.get(j));
                Collections.sort(sorted);
                float bobot_alternatif = matrix.get(j).get(i);
//                float x_min= sorted.get(0);
//                float x_max= sorted.get(sorted.size()-1);
                pre_hasil+=preferensi[j]*(bobot_alternatif);
            }
            hasil.put(Integer.toString(i+1),pre_hasil);
        }


//        Map<String, Float> sorted = hasil
//                .entrySet()
//                .stream()
//                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
//                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
//                        LinkedHashMap::new));

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

//    public static <K, V extends Comparable<? super V>> Map<K, V> sortByKey(Map<K, V> map) {
//        Map<K, V> result = new LinkedHashMap<>();
//        for (Map.Entry<K, V> entry : map.entrySet()) {
//            result.put(entry.getKey(), entry.getValue());
//        }
//        return result;
//    }

}
