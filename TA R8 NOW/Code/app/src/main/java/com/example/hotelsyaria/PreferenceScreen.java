package com.example.hotelsyaria;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PreferenceScreen extends AppCompatActivity {
    TextView prefText;
    Button btn_clear,btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference_screen);
        prefText = findViewById(R.id.pref_text);
        btn_back=findViewById(R.id.btn_back);
        btn_clear=findViewById(R.id.btn_clear_pref);
        prefText.setText(getSharedPreferences("pref_file",MODE_PRIVATE).getString("pref",null));

        controllerHandler();
    }

    void controllerHandler(){
        btn_back.setOnClickListener(v -> finish());

        btn_clear.setOnClickListener(v -> {
            getSharedPreferences("pref_file",MODE_PRIVATE).edit().clear().apply();
            Toast.makeText(this,"Preference cleared",Toast.LENGTH_SHORT).show();
        });
    }
}
