package br.org.fitec.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.org.fitec.model.Funcao;
import br.org.fitec.model.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>{

    List<Funcionario> findByFuncao(Funcao funcao);

    Funcionario findByNome(String nome);

    @Query("SELECT NEW Funcionario(f.matricula, f.nome, "
            + "f.time, f.formacao, f.funcao, f.fileId, f.fileName) FROM Funcionario f WHERE f.time.timeId = "
            + "(SELECT t.timeId FROM Time t WHERE t.nome = ?#{[0]}) "
            + "ORDER BY f.nome")
    List<Funcionario> findByTimeNome(String nomeTime);
    
    @Query("SELECT NEW Funcionario(f.matricula, f.nome, "
            + "f.time, f.formacao, f.funcao, f.fileId, f.fileName) FROM Funcionario f WHERE f.time.projeto.projetoId = "
            + "(SELECT p.projetoId FROM Projeto p WHERE p.nome = ?#{[0]}) "
            + "ORDER BY f.nome")
    List<Funcionario> findByProjetoNome(String nomeTime);    
    
    Funcionario findByMatricula(long matricula);
    
    @Query("SELECT NEW Funcionario(f.matricula, f.nome, "
            + "f.time, f.formacao, f.funcao, f.fileId, f.fileName) FROM Funcionario f WHERE f.time.timeId = "
            + "?#{[0]} "
            + "ORDER BY f.nome")
    List<Funcionario> findByTimeId(long id);
    
    @Query("SELECT NEW Funcionario(f.matricula, f.nome, "
            + "f.formacao, f.funcao, f.fileId, f.fileName) FROM Funcionario f WHERE f.time.timeId = null ")
    List<Funcionario> findByTimeIsNull();

    @Query("SELECT NEW Funcionario(f.matricula, f.nome, "
            + "f.time, f.formacao, f.funcao, f.fileId, f.fileName) FROM Funcionario f WHERE f.funcao.funcaoId = "
            + "?#{[0]} "
            + "ORDER BY f.nome")
    List<Funcionario> findByFuncaoId(long id);

    @Query("SELECT NEW Funcionario(f.matricula, f.nome, "
            + "f.time, f.formacao, f.funcao, f.fileId, f.fileName) FROM Funcionario f WHERE f.formacao.formacaoId = "
            + "?#{[0]} "
            + "ORDER BY f.nome")
    List<Funcionario> findByFormacaoId(long id);

}
