package com.id.start.domma_v2.model;

public class Kategori {
    private String nama;
    private int tipe;
    private String key;

    public Kategori(){

    }

    public Kategori(String nama, int tipe) {
        this.nama = nama;
        this.tipe = tipe;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getTipe() {
        return tipe;
    }

    public void setTipe(int tipe) {
        this.tipe = tipe;
    }
}
