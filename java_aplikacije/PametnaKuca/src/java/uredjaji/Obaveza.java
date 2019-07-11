/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uredjaji;

import java.io.Serializable;

/**
 *
 * @author leon
 */
public class Obaveza implements Serializable{
    private String obaveza;
    private String destinacija;
    
    public Obaveza(String obaveza, String destinacija){
        this.obaveza = obaveza;
        this.destinacija = destinacija;
    }
    
    public Obaveza(String obaveza){
        this.obaveza = obaveza;
        this.destinacija = "";
    }
    
    public String getObaveza(){
        return obaveza;
    }
    
    public void setObaveza(String ob){
        obaveza = ob;
    }
    
    public String getDestinacija(){
        return destinacija;
    }
    
    public void setDestinacija(String des){
        destinacija = des;
    }
    
    @Override
    public String toString(){
        return "Obaveza: "+obaveza+" Destinacija: "+destinacija+"\n";
    }
}
