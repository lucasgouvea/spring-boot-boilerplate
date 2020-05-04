package br.org.fitec.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="formacao")
public class Formacao implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 656410450374720076L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long formacaoId;
    
    @Column(name="nome")
    private String nome;
    
    @OneToMany(mappedBy = "formacao")
    private List<Funcionario> funcionarios = new ArrayList<>();

    public Formacao() {

    }
    
    public Formacao(long formacaoId, String nome) {
        super();
        this.formacaoId = formacaoId;
        this.nome = nome;
        this.funcionarios = null;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public long getFormacaoId() {
        return formacaoId;
    }

    public void setFormacaoId(long formacaoId) {
        this.formacaoId = formacaoId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
