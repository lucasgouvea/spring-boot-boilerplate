package br.org.fitec.util.comparator;

import java.util.Comparator;

import br.org.fitec.model.Formacao;

public class FormacaoComparator implements Comparator<Formacao>{

    @Override
    public int compare(Formacao formacao1, Formacao formacao2) {
        return formacao1.getNome().compareTo(formacao2.getNome());
    }
    

}