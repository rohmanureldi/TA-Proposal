package com.example.hotelsyaria;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

public class FasilitasUmumAdapter extends RecyclerView.Adapter<FasilitasUmumAdapter.FasilitasUmumVH>{
    private Map<String,Integer> listFasilitas;
    private ArrayList<String> facilitiesKey;
    public FasilitasUmumAdapter(Map<String,Integer> listFasilitas) {
        this.listFasilitas=listFasilitas;
        if(!listFasilitas.isEmpty() && listFasilitas.size()!=0)facilitiesKey=new ArrayList<>(listFasilitas.keySet());
    }

    @NonNull
    @Override
    public FasilitasUmumVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fasilitas_item,parent,false);
        return new FasilitasUmumVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FasilitasUmumVH holder, int position) {
        if(!facilitiesKey.isEmpty()){
            holder.tvFasilitas.setText(facilitiesKey.get(position));
            holder.ivFasilitas.setImageResource(listFasilitas.get(facilitiesKey.get(position)));
        }
    }

    @Override
    public int getItemCount() {
        return listFasilitas.size();
    }

    public static class FasilitasUmumVH extends RecyclerView.ViewHolder{
        ImageView ivFasilitas;
        TextView tvFasilitas;
        public FasilitasUmumVH(@NonNull View itemView) {
            super(itemView);
            ivFasilitas = itemView.findViewById(R.id.ivFasilitas);
            tvFasilitas = itemView.findViewById(R.id.tvFasilitasCaption);
        }
    }
}
