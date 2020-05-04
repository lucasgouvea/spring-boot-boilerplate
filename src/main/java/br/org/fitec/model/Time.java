package br.org.fitec.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="time")
public class Time implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 3132159725739434498L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long timeId;
    
    @Column(name="nome")
    private String nome;
    
    @ManyToOne
    @JoinColumn(name = "projetoId")
    private Projeto projeto;
    
    @OneToMany(mappedBy = "time")
    private List<Funcionario> funcionarios = new ArrayList<>();

    public Time() {
        super();
        this.setFuncionarios(null);
    }
    public Time(String nome) {
        super();
        this.nome = nome;
        this.setFuncionarios(null);
    }
  
    public Time(long timeId, String nome) {
        this.setTimeId(timeId);
        this.setNome(nome);
        this.setFuncionarios(null);
    }

    public Time(long timeId, String nome, Projeto projeto) {
        this.setTimeId(timeId);
        this.setNome(nome);
        this.setProjeto(projeto);
        this.setFuncionarios(null);
    }
    
    public Time(long timeId, String nome, long projetoId) {
        this.setTimeId(timeId);
        this.setNome(nome);
        this.setProjeto(new Projeto(projetoId));
        this.setFuncionarios(null);
    }
    
    /*
    public Gestor getGestor() {
        return gestor;
    }

    public void setGestor(Gestor gestor) {
        this.gestor = gestor;
    }
    */
    
    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public long getTimeId() {
        return timeId;
    }

    public void setTimeId(long timeId) {
        this.timeId = timeId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

}
