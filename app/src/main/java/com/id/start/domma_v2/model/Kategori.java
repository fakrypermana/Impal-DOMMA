package com.id.start.domma_v2.model;

public class Kategori {
    private String nama;
    private int tipe;

    public Kategori(){

    }

    public Kategori(String nama, int tipe) {
        this.nama = nama;
        this.tipe = tipe;
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
