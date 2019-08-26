package com.example.hotelsyaria;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PreferenceHandler {
    SharedPreferences pref;
    public PreferenceHandler(Context c) {
        pref=c.getSharedPreferences("pref_file",Context.MODE_PRIVATE);
    }

    public void setPref(ArrayList<ArrayList<Float>> list){
        pref.edit().putString("pref",new Gson().toJson(list)).apply();
    }

    public ArrayList<ArrayList<Float>> getPref(){
        Type listType = new  TypeToken<ArrayList<ArrayList<Float>>>() {}.getType();
        return new Gson().fromJson(pref.getString("pref",null),listType);
    }

    public void setMatrix(ArrayList<ArrayList<Float>>matrix){
        pref.edit().putString("matrix",new Gson().toJson(matrix)).apply();
    }

    public ArrayList<ArrayList<Float>> getMatrix(){
        Type listType = new  TypeToken<ArrayList<ArrayList<Float>>>() {}.getType();
        return new Gson().fromJson(pref.getString("matrix",null),listType);
    }


}
