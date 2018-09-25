package com.id.start.domma_v2.model;

public class kategori {
    String namaKategori;
    int tipeKategori;

    public kategori(String namaKategori, int tipeKategori) {
        this.namaKategori = namaKategori;
        this.tipeKategori = tipeKategori;
    }

    public String getNamaKategori() {
        return namaKategori;
    }

    public void setNamaKategori(String namaKategori) {
        this.namaKategori = namaKategori;
    }

    public int getTipeKategori() {
        return tipeKategori;
    }

    public void setTipeKategori(int tipeKategori) {
        this.tipeKategori = tipeKategori;
    }
}
