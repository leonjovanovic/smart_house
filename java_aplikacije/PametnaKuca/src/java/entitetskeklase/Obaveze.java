/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitetskeklase;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author leon
 */
@Entity
@Table(name = "Obaveze")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Obaveze.findAll", query = "SELECT o FROM Obaveze o")
    , @NamedQuery(name = "Obaveze.findByIdObaveza", query = "SELECT o FROM Obaveze o WHERE o.idObaveza = :idObaveza")
    , @NamedQuery(name = "Obaveze.findByOpisObaveza", query = "SELECT o FROM Obaveze o WHERE o.opisObaveza = :opisObaveza")
    , @NamedQuery(name = "Obaveze.findByDestinacija", query = "SELECT o FROM Obaveze o WHERE o.destinacija = :destinacija")})
public class Obaveze implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idObaveza")
    private Integer idObaveza;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "opisObaveza")
    private String opisObaveza;
    @Size(max = 100)
    @Column(name = "destinacija")
    private String destinacija;

    public Obaveze() {
    }

    public Obaveze(Integer idObaveza) {
        this.idObaveza = idObaveza;
    }

    public Obaveze(Integer idObaveza, String opisObaveza) {
        this.idObaveza = idObaveza;
        this.opisObaveza = opisObaveza;
    }

    public Integer getIdObaveza() {
        return idObaveza;
    }

    public void setIdObaveza(Integer idObaveza) {
        this.idObaveza = idObaveza;
    }

    public String getOpisObaveza() {
        return opisObaveza;
    }

    public void setOpisObaveza(String opisObaveza) {
        this.opisObaveza = opisObaveza;
    }

    public String getDestinacija() {
        return destinacija;
    }

    public void setDestinacija(String destinacija) {
        this.destinacija = destinacija;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idObaveza != null ? idObaveza.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Obaveze)) {
            return false;
        }
        Obaveze other = (Obaveze) object;
        if ((this.idObaveza == null && other.idObaveza != null) || (this.idObaveza != null && !this.idObaveza.equals(other.idObaveza))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entitetskeklase.Obaveze[ idObaveza=" + idObaveza + " ]";
    }
    
}
