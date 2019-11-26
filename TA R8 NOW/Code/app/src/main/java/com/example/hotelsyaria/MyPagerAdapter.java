package com.example.hotelsyaria;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class MyPagerAdapter extends FragmentStatePagerAdapter {
    String deskripsi,policy;
    public MyPagerAdapter(FragmentManager fm,String deskripsi,String policy) {
        super(fm);
        this.deskripsi=deskripsi;
        this.policy=policy;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:return new DeskripsiTab(deskripsi);
            case 1:return new DeskripsiTab(policy);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:return "Deskripsi";
            case 1:return "Kebijakan";
            default: return null;
        }
    }
}
