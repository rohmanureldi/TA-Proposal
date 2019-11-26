package com.example.hotelsyaria;

import android.annotation.SuppressLint;
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
        TextView nama_hotel, rating_overall,prod,pel,peng,umum;
        ConstraintLayout item_layout;

        bookedHolder(@NonNull View itemView) {
            super(itemView);
            nama_hotel = itemView.findViewById(R.id.nama_hotel);
            rating_overall = itemView.findViewById(R.id.syaria_rating);
            prod = itemView.findViewById(R.id.tvRatingProduk);
            pel= itemView.findViewById(R.id.tvRatingPelayanan);
            peng= itemView.findViewById(R.id.tvRatingPengelolaan);
            umum= itemView.findViewById(R.id.tvRatingUmum);
            item_layout=itemView.findViewById(R.id.item_layout);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booked_item,parent,false);
        return new bookedHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TextView namaHotel = ((bookedHolder)holder).nama_hotel;
        TextView rating= ((bookedHolder)holder).rating_overall;
        ConstraintLayout item_layout = ((bookedHolder)holder).item_layout;

        namaHotel.setText(listHotel.get(position).getNama_hotel());
        rating.setText(listHotel.get(position).getRating_syariah());
        ((bookedHolder)holder).prod.setText(listHotel.get(position).getProduk().toString());
        ((bookedHolder)holder).pel.setText(listHotel.get(position).getPelayanan().toString());
        ((bookedHolder)holder).peng.setText(listHotel.get(position).getPengelolaan().toString());
        ((bookedHolder)holder).umum.setText(listHotel.get(position).getRating_umum().toString());

    }

    @Override
    public int getItemCount() {
        return listHotel.size();
    }
}
