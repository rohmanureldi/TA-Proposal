package com.example.hotelsyaria;

import android.support.annotation.Nullable;

public class ModelHotel {
    private String nama_hotel,alamat_hotel,rating_syariah;
    private Float rating_umum,produk,pelayanan,pengelolaan;

    public ModelHotel(String nama_hotel, String alamat_hotel) {
        this.nama_hotel = nama_hotel;
        this.alamat_hotel = alamat_hotel;
    }

    public Float getRating_umum() {
        return rating_umum;
    }

    public void setRating_umum(Float rating_umum) {
        this.rating_umum = rating_umum;
    }

    public Float getProduk() {
        return produk;
    }

    public void setProduk(Float produk) {
        this.produk = produk;
    }

    public Float getPelayanan() {
        return pelayanan;
    }

    public void setPelayanan(Float pelayanan) {
        this.pelayanan = pelayanan;
    }

    public Float getPengelolaan() {
        return pengelolaan;
    }

    public void setPengelolaan(Float pengelolaan) {
        this.pengelolaan = pengelolaan;
    }

    public String getNama_hotel() {
        return nama_hotel;
    }

    public void setNama_hotel(String nama_hotel) {
        this.nama_hotel = nama_hotel;
    }

    public String getAlamat_hotel() {
        return alamat_hotel;
    }

    public void setAlamat_hotel(String alamat_hotel) {
        this.alamat_hotel = alamat_hotel;
    }

    public String getRating_syariah() {
        return rating_syariah;
    }

    public void setRating_syariah(String rating_syariah) {
        this.rating_syariah = rating_syariah;
    }
}
