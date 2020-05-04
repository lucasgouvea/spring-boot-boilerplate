package br.org.fitec.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.org.fitec.model.Time;

public interface TimeRepository extends JpaRepository<Time, Long>{

	Time findByNome(String nome);

	@Query("SELECT NEW Time(t.timeId, t.nome, t.projeto.projetoId)"
	        + "FROM Time t WHERE t.projeto.projetoId = "
	        + "(SELECT p.projetoId FROM Projeto p WHERE p.nome = ?#{[0]}) "
	        + "ORDER BY t.nome")
    List<Time> findByProjetoNome(String nome);

    @Query("SELECT NEW br.org.fitec.model.Time(t.timeId, t.nome)"
            + "FROM Time t WHERE t.projeto.projetoId = null")
	List<Time> findByProjetoIsNull();
    
    @Query("SELECT NEW br.org.fitec.model.Time(t.timeId, t.nome, t.projeto.projetoId)"
            + "FROM Time t WHERE t.projeto is not null")
    List<Time> findAll();
    
    @Transactional
    @Modifying
    @Query("UPDATE Time t SET t.projeto.projetoId = NULL WHERE t.timeId = ?#{[0]}")
    void updateTimeProjetoId(long timeId);

    String findByTimeId(long timeId);

}
