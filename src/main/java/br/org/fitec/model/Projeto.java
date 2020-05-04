package br.org.fitec.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="projeto")
public class Projeto implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = -3443008860303179724L;

    public Projeto() {
    }

    public Projeto(long projetoId, String nome) {
        super();
        this.projetoId = projetoId;
        this.nome = nome;
        this.times = null;
        this.gestor = null;
    }
    
    public Projeto(long projetoId) {
        super();
        this.projetoId = projetoId;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long projetoId;
    
    @Column(name="Nome")
    private String nome;
    
    @OneToMany(mappedBy = "projeto", fetch=FetchType.EAGER)
    private List<Time> times = new ArrayList<>();
    
    @ManyToOne
    @JoinColumn(name="gestorMatricula")
    private Gestor gestor;
    
    public String getNome() {
        return nome;
    }

    public long getProjetoId() {
        return projetoId;
    }

    public void setProjetoId(long projetoId) {
        this.projetoId = projetoId;
    }

    public Gestor getGestor() {
        return gestor;
    }

    public void setGestor(Gestor gestor) {
        this.gestor = gestor;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public List<Time> getTimes() {
        return times;
    }
    public void setTimes(List<Time> times) {
        this.times = times;
    }    

}
