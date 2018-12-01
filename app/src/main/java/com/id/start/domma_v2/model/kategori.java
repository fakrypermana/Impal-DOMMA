package com.id.start.domma_v2.model;

public class kategori {
    private int id;
    private String nama;
    private int tipe;

    public kategori(int id, String nama, int tipe) {
        this.id = id;
        this.nama = nama;
        this.tipe = tipe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
