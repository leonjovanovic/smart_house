/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uredjaji;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 *
 * @author leon
 */
public class Zahtev implements Serializable {
    private String metoda;
    private String pesma;
    private ZonedDateTime vreme;
    private int perioda;
    private Alarm.ponudjenoVreme en;
    private String naziv;
    private String obaveza;
    private String destinacija;
    private Obaveza stara;
    private Obaveza nova;
    private Obaveza obavezaObj;
    private Point2D.Double start;
    private Point2D.Double end;
    
    public Zahtev(String metoda, String pesma){
        this.metoda=metoda;
        this.pesma = pesma;
        this.naziv = pesma;//ako je promeniPesmu metoda a ne reprodukuj jer imaju isti potpis
    }
    
    public Zahtev(String metoda){
        this.metoda=metoda;
    }
    
    public Zahtev(String metoda, ZonedDateTime vreme){
        this.metoda=metoda;
        this.vreme = vreme;
    }
    
    public Zahtev(String metoda, ZonedDateTime vreme, int perioda){
        this.metoda=metoda;
        this.vreme = vreme;
        this.perioda = perioda;
    }    
    
    public Zahtev(String metoda, Alarm.ponudjenoVreme en){
        this.metoda=metoda;
        this.en = en;
    } 
    
    public Zahtev(String metoda, String ob, String dest){
        this.metoda=metoda;
        this.obaveza = ob;
        this.destinacija = dest;
    } 
    
    public Zahtev(String metoda, Obaveza stara, Obaveza nova){
        this.metoda=metoda;
        this.stara=stara;
        this.nova=nova;
    }     
  
    public Zahtev(String metoda, Obaveza obaveza){
        this.metoda=metoda;
        this.obavezaObj = obaveza;
    }   
    
    public Zahtev(String metoda, Point2D.Double a, Point2D.Double b){
        this.metoda=metoda;
        this.start = a;
        this.end = b;
    }    
    
    public Zahtev(String metoda, Point2D.Double b){
        this.metoda=metoda;
        this.end = b;
    } 
    
    public Zahtev(String metoda, Obaveza o, ZonedDateTime vreme, Point2D.Double mesto){
        this.metoda=metoda;
        this.vreme = vreme;
        this.end = mesto;
    } 







    
    
    //Geteri i Seteri
    public String getPesma() {
        return pesma;
    }

    public void setPesma(String pesma) {
        this.pesma = pesma;
    }

    public String getMetoda() {
        return metoda;
    }

    public void setMetoda(String metoda) {
        this.metoda = metoda;
    }

    public ZonedDateTime getVreme() {
        return vreme;
    }

    public void setVreme(ZonedDateTime vreme) {
        this.vreme = vreme;
    }

    public int getPerioda() {
        return perioda;
    }

    public void setPerioda(int perioda) {
        this.perioda = perioda;
    }

    public Alarm.ponudjenoVreme getEn() {
        return en;
    }

    public void setEn(Alarm.ponudjenoVreme en) {
        this.en = en;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }   

    public String getObaveza() {
        return obaveza;
    }

    public void setObaveza(String obaveza) {
        this.obaveza = obaveza;
    }

    public String getDestinacija() {
        return destinacija;
    }

    public void setDestinacija(String destinacija) {
        this.destinacija = destinacija;
    }

    public Obaveza getStara() {
        return stara;
    }

    public void setStara(Obaveza stara) {
        this.stara = stara;
    }

    public Obaveza getNova() {
        return nova;
    }

    public void setNova(Obaveza nova) {
        this.nova = nova;
    }

    public Obaveza getObavezaObj() {
        return obavezaObj;
    }

    public void setObavezaObj(Obaveza obavezaObj) {
        this.obavezaObj = obavezaObj;
    }

    public Point2D.Double getStart() {
        return start;
    }

    public void setStart(Point2D.Double start) {
        this.start = start;
    }

    public Point2D.Double getEnd() {
        return end;
    }

    public void setEnd(Point2D.Double end) {
        this.end = end;
    }
    
    
}
