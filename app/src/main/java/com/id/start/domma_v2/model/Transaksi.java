package com.id.start.domma_v2.model;

import java.util.Date;

public class Transaksi {
    private String nama;
    private int mount;
    private String tgl_transaki;
    private int tipe;
    private String key;

    public Transaksi(){

    }

    public Transaksi(String nama, int mount, String tgl_transaki, int tipe) {
        this.nama = nama;
        this.mount = mount;
        this.tgl_transaki = tgl_transaki;
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

    public int getMount() {
        return mount;
    }

    public void setMount(int mount) {
        this.mount = mount;
    }

    public String getTgl_transaki() {
        return tgl_transaki;
    }

    public void setTgl_transaki(String tgl_transaki) {
        this.tgl_transaki = tgl_transaki;
    }

    public int getTipe() {
        return tipe;
    }

    public void setTipe(int tipe) {
        this.tipe = tipe;
    }
}
