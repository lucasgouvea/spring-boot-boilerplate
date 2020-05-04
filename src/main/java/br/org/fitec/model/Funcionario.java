package br.org.fitec.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="funcionario")
public class Funcionario implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 8585866485108460259L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long matricula;

    @Column(name="nome")
    private String nome;

    @Column(name="fileId")
    private String fileId;

    @Column(name="fileName")
    private String fileName;

    @ManyToOne
    @JoinColumn(name = "timeId")
    private Time time;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "formacaoId")
    private Formacao formacao;
    
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "funcaoId")
    private Funcao funcao;

    public Funcionario() {

    }
    public Funcionario(long matricula, String nome, Time time, Formacao formacao, 
            Funcao funcao, String fileId, String fileName) {
        time.getProjeto().setTimes(null);
        if(time.getProjeto().getGestor() != null) {
            time.getProjeto().getGestor().setProjetos(null);
        }
        this.matricula = matricula;
        this.nome = nome;
        this.time = new Time(time.getTimeId(), time.getNome(), time.getProjeto());
        this.formacao = new Formacao(formacao.getFormacaoId(), formacao.getNome());
        this.funcao = new Funcao(funcao.getFuncaoId(), funcao.getNome());
        this.fileId = fileId;
        this.fileName = fileName;
    }

   public Funcionario(long matricula, String nome, Formacao formacao, 
            Funcao funcao, String fileId, String fileName) {
        this.matricula = matricula;
        this.nome = nome;
        this.formacao = new Formacao(formacao.getFormacaoId(), formacao.getNome());
        this.funcao = new Funcao(funcao.getFuncaoId(), funcao.getNome());
        this.fileId = fileId;
        this.fileName = fileName;
    }

    public Funcionario(long matricula, String nome) {
        this.matricula = matricula;
        this.nome = nome;
        this.formacao = null;
        this.funcao = null;
        this.time = null;
    }    

    public long getMatricula() {
        return matricula;
    }

    public void setMatricula(long matricula) {
        this.matricula = matricula;
    }

    public Formacao getFormacao() {
        return formacao;
    }

    public void setFormacao(Formacao formacao) {
        this.formacao = formacao;
    }

    public Funcao getFuncao() {
        return funcao;
    }

    public void setFuncao(Funcao funcao) {
        this.funcao = funcao;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFileId() {
        return fileId;
    }
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
