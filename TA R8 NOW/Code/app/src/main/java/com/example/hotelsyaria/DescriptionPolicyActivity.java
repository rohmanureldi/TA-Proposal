package com.example.hotelsyaria;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class DescriptionPolicyActivity extends AppCompatActivity {
    ViewPager vp;
    TabLayout tl;
    MyPagerAdapter pagerAdapter;
    PreferenceHandler pref;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.desciption_policy_activity);
        vp=findViewById(R.id.vpDeskripsi);
        tl=findViewById(R.id.tlDeskripsi);
        pref = new PreferenceHandler(this);
        ModelHotel hotel = pref.getClickedHotel();
        pagerAdapter=new MyPagerAdapter(getSupportFragmentManager(),hotel.getDeskripsi(),hotel.getPolicy());
        vp.setAdapter(pagerAdapter);
        tl.setupWithViewPager(vp);

    }
}
