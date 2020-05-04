
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
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="funcao")
public class Funcao implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -65516603613424936L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long funcaoId;
    
    @Column(name="nome")
    private String nome;
    
    @OneToMany(mappedBy = "funcao", fetch = FetchType.EAGER)
    private List<Funcionario> funcionarios = new ArrayList<>();
	public Funcao() {
		super();
	}
	
	public Funcao(long funcaoId, String nome) {
		super();
		this.funcaoId = funcaoId;
		this.nome = nome;
		this.funcionarios = null;
	}	


	
	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public long getFuncaoId() {
		return funcaoId;
	}

	public void setFuncaoId(long funcaoId) {
		this.funcaoId = funcaoId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
