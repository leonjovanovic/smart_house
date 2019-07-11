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
@Table(name = "Pesme")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pesme.findAll", query = "SELECT p FROM Pesme p")
    , @NamedQuery(name = "Pesme.findByIdPesme", query = "SELECT p FROM Pesme p WHERE p.idPesme = :idPesme")
    , @NamedQuery(name = "Pesme.findByNaziv", query = "SELECT p FROM Pesme p WHERE p.naziv = :naziv")})
public class Pesme implements Serializable {

    //private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPesme")
    private Integer idPesme;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "Naziv")
    private String naziv;

    public Pesme() {
    }

    public Pesme(Integer idPesme) {
        this.idPesme = idPesme;
    }

    public Pesme(Integer idPesme, String naziv) {
        this.idPesme = idPesme;
        this.naziv = naziv;
    }

    public Integer getIdPesme() {
        return idPesme;
    }

    public void setIdPesme(Integer idPesme) {
        this.idPesme = idPesme;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPesme != null ? idPesme.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pesme)) {
            return false;
        }
        Pesme other = (Pesme) object;
        if ((this.idPesme == null && other.idPesme != null) || (this.idPesme != null && !this.idPesme.equals(other.idPesme))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entitetskeklase.Pesme[ idPesme=" + idPesme + " ]";
    }
    
}
