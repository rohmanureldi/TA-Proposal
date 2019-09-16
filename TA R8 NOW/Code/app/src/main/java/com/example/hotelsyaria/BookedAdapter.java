package com.example.hotelsyaria;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<ModelHotel> listHotel;


    public BookedAdapter(ArrayList<ModelHotel> listHotel) {
        this.listHotel = listHotel;
        if(this.listHotel==null){
            this.listHotel=new ArrayList<>();
        }
    }

    public static class bookedHolder extends RecyclerView.ViewHolder {
        TextView nama_hotel,rating_syaria;
        ConstraintLayout item_layout;

        bookedHolder(@NonNull View itemView) {
            super(itemView);
            nama_hotel = itemView.findViewById(R.id.nama_hotel);
            rating_syaria = itemView.findViewById(R.id.syaria_rating);
            item_layout=itemView.findViewById(R.id.item_layout);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booked_item,parent,false);
        return new bookedHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TextView namaHotel = ((bookedHolder)holder).nama_hotel;
        TextView rating= ((bookedHolder)holder).rating_syaria;
        ConstraintLayout item_layout = ((bookedHolder)holder).item_layout;

        namaHotel.setText(listHotel.get(position).getNama_hotel());
        rating.setText(listHotel.get(position).getRating_syariah());


    }

    @Override
    public int getItemCount() {
        return listHotel.size();
    }
}
