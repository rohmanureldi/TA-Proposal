package com.example.hotelsyaria;

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    ArrayList<ModelHotel> listHotel;
    Activity act;

    public RecyclerAdapter(Activity act, ArrayList<ModelHotel> listHotel) {
        this.listHotel = listHotel;
        this.act = act;
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
        TextView nama_hotel,alamat_hotel,rating_syaria;
        ConstraintLayout container_item_layout;
        myViewHolder(@NonNull View itemView) {
            super(itemView);
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

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        TextView nama_hotel = ((myViewHolder)viewHolder).nama_hotel;
        TextView alamat_hotel= ((myViewHolder)viewHolder).alamat_hotel;
        TextView syaria_rating = ((myViewHolder)viewHolder).rating_syaria;
        ConstraintLayout layout = ((myViewHolder)viewHolder).container_item_layout;
        nama_hotel.setText(listHotel.get(i).getNama_hotel());
        alamat_hotel.setText(listHotel.get(i).getAlamat_hotel());
        syaria_rating.setText(listHotel.get(i).getRating_syariah());

        layout.setOnClickListener(v->{
            Dialog builder = new Dialog(act);
            LayoutInflater layoutInflater = act.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.dialog_hotel_click,null);
            ((TextView)view.findViewById(R.id.nama_hotel_dialog)).setText(listHotel.get(i).getNama_hotel());
            ((TextView)view.findViewById(R.id.alamat_hotel)).setText(listHotel.get(i).getAlamat_hotel());
            ((TextView)view.findViewById(R.id.syaria_rating)).setText(listHotel.get(i).getRating_syariah());
            ((TextView)view.findViewById(R.id.produk_rating)).setText(listHotel.get(i).getProduk().toString());
            ((TextView)view.findViewById(R.id.pelayanan_rating)).setText(listHotel.get(i).getPelayanan().toString());
            ((TextView)view.findViewById(R.id.pengelolaan_rating)).setText(listHotel.get(i).getPengelolaan().toString());
            view.findViewById(R.id.btn_cancel).setOnClickListener(v1 -> builder.dismiss());
            view.findViewById(R.id.btn_book).setOnClickListener(v1 -> {
                Toast.makeText(act, "Hotel succesfully booked !", Toast.LENGTH_SHORT).show();
                builder.dismiss();
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
}
