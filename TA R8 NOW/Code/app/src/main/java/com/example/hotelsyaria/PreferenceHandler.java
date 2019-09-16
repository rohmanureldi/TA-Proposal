package com.example.hotelsyaria;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PreferenceHandler {
    SharedPreferences pref;
    Context c;
    public PreferenceHandler(Context c) {
        pref=c.getSharedPreferences("pref_file",Context.MODE_PRIVATE);
        this.c = c;
    }

    public void setPref(ArrayList<ArrayList<Float>> list){
        pref.edit().putString("pref",new Gson().toJson(list)).apply();
    }

    public ArrayList<ArrayList<Float>> getPref(){
        Type listType = new  TypeToken<ArrayList<ArrayList<Float>>>() {}.getType();
        return new Gson().fromJson(pref.getString("pref",null),listType);
    }

    public void setMatrix(ArrayList<ArrayList<Float>>matrix){
        c.getSharedPreferences("matrix",Context.MODE_PRIVATE).edit().putString("matrix",new Gson().toJson(matrix)).apply();
    }

    public ArrayList<ArrayList<Float>> getMatrix(){
        Type listType = new  TypeToken<ArrayList<ArrayList<Float>>>() {}.getType();
        return new Gson().fromJson(c.getSharedPreferences("matrix",Context.MODE_PRIVATE).getString("matrix",null),listType);
    }

    void setListHotel(ArrayList<ModelHotel> listHotel){
        pref.edit().putString("listHotel",new Gson().toJson(listHotel)).apply();
    }

    public ArrayList<ModelHotel> getListHotel(){
        Type listType = new  TypeToken<ArrayList<ModelHotel>>() {}.getType();
        return new Gson().fromJson(pref.getString("listHotel",null),listType);
    }

    public void setlistBooked(ArrayList<ModelHotel> listBooked){
        pref.edit().putString("listBooked",new Gson().toJson(listBooked)).apply();
    }

    public ArrayList<ModelHotel> getListBooked(){
        Type listType = new  TypeToken<ArrayList<ModelHotel>>() {}.getType();
        return new Gson().fromJson(pref.getString("listBooked",null),listType);
    }




}
