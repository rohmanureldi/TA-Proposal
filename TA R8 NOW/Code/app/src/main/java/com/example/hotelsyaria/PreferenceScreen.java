package com.example.hotelsyaria;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PreferenceScreen extends AppCompatActivity {
    TextView prefProd,prefPel,prefPeng,prefUmum;
    Button btn_clear,btn_back;
    RecyclerView recyclerView;
    BookedAdapter adapter;
    ArrayList<ModelHotel> listBooked;
    PreferenceHandler prefHandler;
    ArrayList<ArrayList<Float>> preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference_screen);
        prefHandler = new PreferenceHandler(this);
        prefProd = findViewById(R.id.pref_produk);
        prefPel = findViewById(R.id.pref_pelayanan);
        prefPeng= findViewById(R.id.pref_pengelolaan);
        prefUmum= findViewById(R.id.pref_umum);
        btn_back=findViewById(R.id.btn_back);
        btn_clear=findViewById(R.id.btn_clear_pref);
        recyclerView = findViewById(R.id.booked_history_recycler);
        preferences = prefHandler.getPref();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        Type listType = new  TypeToken<ArrayList<ModelHotel>>() {}.getType();
        listBooked = new Gson().fromJson(getSharedPreferences("pref_file",MODE_PRIVATE).getString("listBooked",null),listType);


        adapter = new BookedAdapter(listBooked);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        setPreferenceText();
        controllerHandler();
    }

    @SuppressLint("SetTextI18n")
    private void setPreferenceText(){
        StringBuilder prod = new StringBuilder();
        StringBuilder pel = new StringBuilder();
        StringBuilder peng=new StringBuilder();
        StringBuilder umum=new StringBuilder();
        int index =0;

        for(float i : preferences.get(0)){
            umum.append(i * 5);
            if(index != preferences.get(0).size()-1){
                umum.append(" , ");
                index++;
            }else{
                index=0;
                break;
            }
        }

        for(float i : preferences.get(1)){
            prod.append(i * 5).append(" ");
            if(index!=preferences.get(1).size()-1){
                prod.append(" , ");
                index++;
            }else{
                index=0;
                break;
            }
        }

        for(float i : preferences.get(2)){
            pel.append(i * 5).append(" ");
            if(index!=preferences.get(2).size()-1){
                pel.append(" , ");
                index++;
            }else{
                index=0;
                break;
            }
        }

        for(float i : preferences.get(3)){
            peng.append(i * 5).append(" ");
            if(index!=preferences.get(3).size()-1){
                peng.append(" , ");
                index++;
            }else{
                break;
            }
        }

        prefProd.setText(prod);
        prefPel.setText(pel);
        prefPeng.setText(peng);
        prefUmum.setText(umum);
    }

    void controllerHandler(){
        btn_back.setOnClickListener(v -> finish());

        btn_clear.setOnClickListener(v -> {
            getSharedPreferences("pref_file",MODE_PRIVATE).edit().clear().apply();
            listBooked.clear();
            adapter.notifyDataSetChanged();
            prefProd.setText("");
            prefPel.setText("");
            prefPeng.setText("");
            prefUmum.setText("");
            Toast.makeText(this,"Preference cleared",Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
