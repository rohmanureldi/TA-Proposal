package com.example.hotelsyaria;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ModelHotel {
    private String nama_hotel,alamat_hotel,rating_syariah,deskripsi,policy;
    private Float rating_umum,produk,pelayanan,pengelolaan,bobot_produk,bobot_pelayanan,bobot_pengelolaan;
    private ArrayList<String> facilityException;
    private int[] images;
    private Map<String,Integer> fasilitasUmum,fasilitasSyariah;


    public void setFasilitasUmum(String[] key, int[] value){
        fasilitasUmum = new HashMap<>();
        for(int i = 0 ;i<key.length;i++){
            fasilitasUmum.put(key[i],value[i]);
        }
    }

    public Map<String,Integer> getFasilitasUmum(){
        return this.fasilitasUmum;
    }

    public void setFasilitasSyariah(String[] key, int[] value){
        fasilitasSyariah= new HashMap<>();
        for(int i = 0 ;i<key.length;i++){
            fasilitasSyariah.put(key[i],value[i]);
        }
    }

    public Map<String,Integer> getFasilitasSyariah(){
        return this.fasilitasSyariah;
    }

    public ModelHotel(String nama_hotel, String alamat_hotel,int[]images) {
        this.nama_hotel = nama_hotel;
        this.alamat_hotel = alamat_hotel;
        facilityException = new ArrayList<>();
        this.images=images;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsiPolicy(String deskripsi, String policy) {
        this.deskripsi = deskripsi;
        this.policy=policy;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public ArrayList<String> getFacilityException() {
        return facilityException;
    }

    public int[] getImages() {
        return images;
    }

    public void setFacilityException(ArrayList<String> facilityException) {
        this.facilityException = facilityException;
    }

    public Float getBobot_produk() {
        return bobot_produk;
    }

    public void setBobot_produk(Float bobot_produk) {
        this.bobot_produk = bobot_produk;
    }

    public Float getBobot_pelayanan() {
        return bobot_pelayanan;
    }

    public void setBobot_pelayanan(Float bobot_pelayanan) {
        this.bobot_pelayanan = bobot_pelayanan;
    }

    public Float getBobot_pengelolaan() {
        return bobot_pengelolaan;
    }

    public void setBobot_pengelolaan(Float bobot_pengelolaan) {
        this.bobot_pengelolaan = bobot_pengelolaan;
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
