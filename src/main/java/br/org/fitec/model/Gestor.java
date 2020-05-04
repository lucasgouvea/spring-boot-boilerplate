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
@Table(name="gestor")
public class Gestor implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = -7152467008956780960L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="gestorMatricula")
    private long gestorMatricula;
    
    @Column(name="nome")
    private String nome;
    
    @OneToMany(mappedBy = "gestor")
    private List<Projeto> projetos = new ArrayList<>();
    
    @Column(name="fileId")
    private String fileId;

    @Column(name="fileName")
    private String fileName;
    public Gestor() {
        super();
    }

   public Gestor(long gestorMatricula, String nome) {
        super();
        this.gestorMatricula = gestorMatricula;
        this.nome = nome;
    }
   

   public Gestor(long gestorMatricula, String nome, String fileId, String fileName) {
       super();
       this.gestorMatricula = gestorMatricula;
       this.nome = nome;
       this.fileId = fileId;
       this.fileName = fileName;
   }

    public long getGestorMatricula() {
        return gestorMatricula;
    }

    public void setGestorMatricula(long gestorMatricula) {
        this.gestorMatricula = gestorMatricula;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Projeto> getProjetos() {
        return projetos;
    }

    public void setProjetos(List<Projeto> projetos) {
        this.projetos = projetos;
    }

}
