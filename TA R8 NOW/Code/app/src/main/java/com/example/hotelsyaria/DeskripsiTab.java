package com.example.hotelsyaria;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class DeskripsiTab extends Fragment {
    private String desc;
    private TextView text;

    public DeskripsiTab(String deskripsi) {
        desc=deskripsi;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layour_deskripsi,container,false);
        text=view.findViewById(R.id.tvDetailDeskripsi);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            text.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }
        text.setText(desc);
        return view;
    }
}
