package com.example.hotelsyaria;

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
    TextView prefText;
    Button btn_clear,btn_back;
    RecyclerView recyclerView;
    BookedAdapter adapter;
    ArrayList<ModelHotel> listBooked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference_screen);
        prefText = findViewById(R.id.pref_text);
        btn_back=findViewById(R.id.btn_back);
        btn_clear=findViewById(R.id.btn_clear_pref);
        recyclerView = findViewById(R.id.booked_history_recycler);
        prefText.setText(getSharedPreferences("pref_file",MODE_PRIVATE).getString("pref",null));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        Type listType = new  TypeToken<ArrayList<ModelHotel>>() {}.getType();
        listBooked = new Gson().fromJson(getSharedPreferences("pref_file",MODE_PRIVATE).getString("listBooked",null),listType);


        adapter = new BookedAdapter(listBooked);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        controllerHandler();
    }

    void controllerHandler(){
        btn_back.setOnClickListener(v -> finish());

        btn_clear.setOnClickListener(v -> {
            getSharedPreferences("pref_file",MODE_PRIVATE).edit().clear().apply();
            listBooked.clear();
            adapter.notifyDataSetChanged();
            prefText.setText("");
            Toast.makeText(this,"Preference cleared",Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
