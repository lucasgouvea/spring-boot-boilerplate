package br.org.fitec.util.comparator;

import java.util.Comparator;

import br.org.fitec.model.Funcionario;

public class FuncionarioComparator implements Comparator<Funcionario>{

    @Override
    public int compare(Funcionario funcionario1, Funcionario funcionario2) {
        return funcionario1.getNome().compareTo(funcionario2.getNome());
    }
    

}