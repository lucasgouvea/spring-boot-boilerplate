package br.org.fitec.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.org.fitec.model.Formacao;

public interface FormacaoRepository extends JpaRepository<Formacao, Long>{

	Formacao findByNome(String nome);

	@Query("SELECT NEW br.org.fitec.model.Formacao(f.id, f.nome)"
			+ " FROM Formacao f")	
	List<Formacao> findAll();

}
