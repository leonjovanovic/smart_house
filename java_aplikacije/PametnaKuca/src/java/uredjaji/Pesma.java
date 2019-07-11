/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uredjaji;

/**
 *
 * @author leon
 */
public class Pesma {
    private int id;
    private String pesma;
    
    public Pesma(int id, String pesma){
        this.id = id;
        this.pesma = pesma;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPesma() {
        return pesma;
    }

    public void setPesma(String pesma) {
        this.pesma = pesma;
    }
    
    
}
