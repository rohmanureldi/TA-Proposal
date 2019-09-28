package com.example.hotelsyaria;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    ArrayList<ModelHotel> listHotel;
    Activity act;
    public ArrayList<ArrayList> sorted_hasil;
    ArrayList<Float> bobot_produk,bobot_pelayanan,bobot_pengelolaan;

    public RecyclerAdapter(Activity act, ArrayList<ModelHotel> listHotel) {
        this.listHotel = listHotel;
        this.act = act;
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
        TextView nama_hotel,alamat_hotel,rating_syaria;
        ConstraintLayout container_item_layout;
        ImageView hotelCover;
        myViewHolder(@NonNull View itemView) {
            super(itemView);
            hotelCover=itemView.findViewById(R.id.gambar_hotel_item);
            nama_hotel = itemView.findViewById(R.id.nama_hotel);
            alamat_hotel= itemView.findViewById(R.id.alamat_hotel);
            rating_syaria = itemView.findViewById(R.id.syaria_rating);
            container_item_layout = itemView.findViewById(R.id.item_layout);
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_hotel,viewGroup,false);
        return new myViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        TextView nama_hotel = ((myViewHolder)viewHolder).nama_hotel;
        TextView alamat_hotel= ((myViewHolder)viewHolder).alamat_hotel;
        TextView syaria_rating = ((myViewHolder)viewHolder).rating_syaria;
        ConstraintLayout layout = ((myViewHolder)viewHolder).container_item_layout;
        nama_hotel.setText(listHotel.get(i).getNama_hotel());
        alamat_hotel.setText(listHotel.get(i).getAlamat_hotel());
        syaria_rating.setText(listHotel.get(i).getRating_syariah());
        ((myViewHolder)viewHolder).hotelCover.setImageResource(listHotel.get(i).getImages()[0]);

        layout.setOnClickListener(v->{
            Dialog builder = new Dialog(act);
            LayoutInflater layoutInflater = act.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.dialog_hotel_click,null);
            ImageListener imageListener = (position, imageView) -> imageView.setImageResource(listHotel.get(i).getImages()[position]);

            ((TextView)view.findViewById(R.id.nama_hotel_dialog)).setText(listHotel.get(i).getNama_hotel());
            ((TextView)view.findViewById(R.id.alamat_hotel)).setText(listHotel.get(i).getAlamat_hotel());
            ((TextView)view.findViewById(R.id.syaria_rating)).setText(listHotel.get(i).getRating_umum().toString());
            ((TextView)view.findViewById(R.id.produk_rating)).setText(listHotel.get(i).getProduk().toString());
            ((TextView)view.findViewById(R.id.pelayanan_rating)).setText(listHotel.get(i).getPelayanan().toString());
            ((TextView)view.findViewById(R.id.pengelolaan_rating)).setText(listHotel.get(i).getPengelolaan().toString());
            ((CarouselView)view.findViewById(R.id.carousel_hotel)).setPageCount(listHotel.get(i).getImages().length);
            ((CarouselView)view.findViewById(R.id.carousel_hotel)).setImageListener(imageListener);
            view.findViewById(R.id.btn_cancel).setOnClickListener(v1 -> builder.dismiss());
            view.findViewById(R.id.btn_book).setOnClickListener(v1 -> {
                Toast.makeText(act, "Hotel succesfully booked !", Toast.LENGTH_SHORT).show();
                builder.dismiss();
//                try{
//                    PreferenceHandler preferenceHandler= new PreferenceHandler(act);
//                    ArrayList<ArrayList<Float>> preferences = preferenceHandler.getPref();
//                    ArrayList<ArrayList<Float>> tmp = new ArrayList<>();
//                    if(preferences==null){
//                        preferences=new ArrayList<>();
//                        preferences.add(new ArrayList<>());
//                        preferences.add(new ArrayList<>());
//                        preferences.add(new ArrayList<>());
//
//                    }
//                    ArrayList<ModelHotel> listBooked = preferenceHandler.getListBooked();
//                    if(listBooked==null){
//                        listBooked=new ArrayList<>();
//                    }
//                    listBooked.add(listHotel.get(i));
//                    preferenceHandler.setlistBooked(listBooked);
//
//                    float[] tmpPref=convertPref(new float[]{listHotel.get(i).getProduk(),listHotel.get(i).getPelayanan(),listHotel.get(i).getPengelolaan()});
//                    preferences.get(0).add(tmpPref[0]);
//                    preferences.get(1).add(tmpPref[1]);
//                    preferences.get(2).add(tmpPref[2]);
//
//                    preferenceHandler.setPref(preferences);
//                    ArrayList<ModelHotel> clonedData = cloneData();
//                    listHotel.clear();
//                    ArrayList<ArrayList> hasil = normalisasi(preferences);
//                    for(int j =0;j<10;j++){
//                        listHotel.add(clonedData.get(Integer.parseInt(String.valueOf(hasil.get(0).get(j)))-1));
//                    }
//                    notifyDataSetChanged();
//                    Toast.makeText(act, "Hotel succesfully booked !", Toast.LENGTH_SHORT).show();
//                }catch(Exception e ){
//                    Log.d(TAG, "onBindViewHolder: "+e.getLocalizedMessage());
//                }
//                finally {
//                    builder.dismiss();
//                }
            });
            builder.setContentView(view);
            builder.setCancelable(true);
            builder.show();
        });

    }

    @Override
    public int getItemCount() {
        return listHotel.size();
    }

    float[] convertPref(float[] pref){
        float x,y,z,sum=0;
        sum=pref[0]+pref[1]+pref[2];
        x=pref[0]/sum;
        y=pref[1]/sum;
        z=pref[2]/sum;
        return new float[]{x,y,z};
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    ArrayList<ArrayList> normalisasi(ArrayList<ArrayList<Float>> prefList){
        ArrayList<ArrayList<Float>> matrix = new PreferenceHandler(act).getMatrix();
        float[] preferensi=prefToArray(prefList);

        Map<String,Float> hasil=new HashMap<>();
        for(int i=0;i<10;i++){
            float pre_hasil=0;
            for(int j =0;j<3;j++){
                ArrayList<Float> sorted=new ArrayList<>(matrix.get(j));
                Collections.sort(sorted);
                float bobot_alternatif = matrix.get(j).get(i);
                float x_min= sorted.get(0);
                float x_max= sorted.get(sorted.size()-1);
                pre_hasil+=preferensi[j]*(bobot_alternatif-x_min)/(x_min+x_max);
            }
            hasil.put(Integer.toString(i+1),pre_hasil);
        }
        Map<String, Float> sorted = hasil
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2,
                        LinkedHashMap::new));


        sorted_hasil = new ArrayList<>();
        ArrayList<String> list_key = new ArrayList<>();
        ArrayList<Float> list_value= new ArrayList<>();
        for(int i =0;i<hasil.size();i++){
            String key =(String) sorted.keySet().toArray()[i];
            Float value =(float) sorted.get(key);
            list_key.add(key);
            list_value.add(value);
        }
        sorted_hasil.add(list_key);
        sorted_hasil.add(list_value);
        return sorted_hasil;
    }

    float[] prefToArray(ArrayList<ArrayList<Float>> preference){
        float pr,pl,pg,x,y,z,sum=0;

        for(float i : preference.get(0)){
            sum+=i;
        }
        x=sum/preference.get(0).size();
        sum=0;

        for(float i : preference.get(1)){
            sum+=i;
        }
        y=sum/preference.get(1).size();
        sum=0;

        for(float i : preference.get(2)){
            sum+=i;
        }
        z=sum/preference.get(2).size();

        sum=x+y+z;
        pr=x/sum;
        pl=y/sum;
        pg=z/sum;

        return new float[] {pr,pl,pg};

    }

    ArrayList<ModelHotel> cloneData() {
        ArrayList<ModelHotel> clone = new ArrayList<>();
        clone.addAll(new PreferenceHandler(act).getListHotel());
        return clone;
    }
}
