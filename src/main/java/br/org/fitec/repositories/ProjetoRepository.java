package br.org.fitec.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.org.fitec.model.Gestor;
import br.org.fitec.model.Projeto;

public interface ProjetoRepository extends JpaRepository<Projeto, Long>{

	Projeto findByNome(String nome);
	int findIdByNome(String nomeProjeto);
	@Query("SELECT NEW Projeto(p.id, p.nome) FROM Projeto p")
	List<Projeto> findAll();
	List<Projeto> findByGestor(Gestor gestor);
	
    @Query("SELECT NEW Projeto(p.projetoId, p.nome)"
            + "FROM Projeto p WHERE p.gestor.gestorMatricula = null")
    List<Projeto> findByGestorMatriculaIsNull();
    
    
    @Transactional
    @Modifying
    @Query("UPDATE Projeto p SET p.gestor.gestorMatricula = NULL WHERE p.projetoId = ?#{[0]}")
    void updateProjetoGestorMatricula(long projetoId);
}


