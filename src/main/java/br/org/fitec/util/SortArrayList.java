package br.org.fitec.util;

import java.util.ArrayList;
import java.util.Collections;

import br.org.fitec.model.Formacao;
import br.org.fitec.model.Funcao;
import br.org.fitec.model.Funcionario;
import br.org.fitec.model.Projeto;
import br.org.fitec.model.Time;
import br.org.fitec.model.Usuario;
import br.org.fitec.util.comparator.FormacaoComparator;
import br.org.fitec.util.comparator.FuncaoComparator;
import br.org.fitec.util.comparator.FuncionarioComparator;
import br.org.fitec.util.comparator.ProjetoComparator;
import br.org.fitec.util.comparator.TimeComparator;
import br.org.fitec.util.comparator.UsuarioComparator;

public final class SortArrayList{
    private static final SortArrayList INSTANCE = new SortArrayList();

    public static SortArrayList getInstance() {
        return INSTANCE;
    }
    
    @SuppressWarnings("unchecked")
    public static <T> ArrayList<T> sort(ArrayList<T> list, String objectType){
        switch(objectType) {
            case "Formacao":
                Collections.sort((ArrayList<Formacao>) list, new FormacaoComparator());
                break;
            case "Funcao":
                Collections.sort((ArrayList<Funcao>) list, new FuncaoComparator());
                break;
            case "Funcionario":
                Collections.sort((ArrayList<Funcionario>) list, new FuncionarioComparator());
                break;
            case "Time":
                Collections.sort((ArrayList<Time>) list, new TimeComparator());
                break;
            case "Projeto":
                Collections.sort((ArrayList<Projeto>) list, new ProjetoComparator());
                break;
            case "Usuario":
                Collections.sort((ArrayList<Usuario>) list, new UsuarioComparator());
                break;
        }
        return list;
    }
    
    
}
