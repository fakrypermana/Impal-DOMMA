package com.id.start.domma_v2.model;

import java.util.Date;

public class transaksi {
    private int id_transaksi;
    private String nama;
    private String nama_kategori;
    private double mount;
    private Date tgl_transaki;

    public transaksi(int id_transaksi, String nama, String nama_kategori, double mount, Date tgl_transaki) {
        this.id_transaksi = id_transaksi;
        this.nama = nama;
        this.nama_kategori = nama_kategori;
        this.mount = mount;
        this.tgl_transaki = tgl_transaki;
    }

    public int getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(int id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNama_kategori() {
        return nama_kategori;
    }

    public void setNama_kategori(String nama_kategori) {
        this.nama_kategori = nama_kategori;
    }

    public double getMount() {
        return mount;
    }

    public void setMount(double mount) {
        this.mount = mount;
    }

    public Date getTgl_transaki() {
        return tgl_transaki;
    }

    public void setTgl_transaki(Date tgl_transaki) {
        this.tgl_transaki = tgl_transaki;
    }
}
