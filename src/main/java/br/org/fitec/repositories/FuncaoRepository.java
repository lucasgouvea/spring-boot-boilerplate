package br.org.fitec.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.org.fitec.model.Funcao;

public interface FuncaoRepository extends JpaRepository<Funcao, Long>{

    Funcao findByNome(String nome);

    @Query("SELECT NEW br.org.fitec.model.Funcao(f.id, f.nome) FROM Funcao f")
    ArrayList<Funcao> findAll();

}
