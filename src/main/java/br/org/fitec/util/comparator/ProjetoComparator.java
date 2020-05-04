package br.org.fitec.util.comparator;

import java.util.Comparator;

import br.org.fitec.model.Projeto;

public class ProjetoComparator implements Comparator<Projeto>{

    @Override
    public int compare(Projeto projeto1, Projeto projeto2) {
        return projeto1.getNome().compareTo(projeto2.getNome());
    }
    

}