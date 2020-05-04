package br.org.fitec.util.comparator;

import java.util.Comparator;

import br.org.fitec.model.Funcao;

public class FuncaoComparator implements Comparator<Funcao>{

    @Override
    public int compare(Funcao funcao1, Funcao funcao2) {
        return funcao1.getNome().compareTo(funcao2.getNome());
    }
    

}