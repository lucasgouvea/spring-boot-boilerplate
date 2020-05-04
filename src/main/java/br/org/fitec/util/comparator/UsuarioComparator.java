package br.org.fitec.util.comparator;

import java.util.Comparator;

import br.org.fitec.model.Usuario;

public class UsuarioComparator implements Comparator<Usuario>{

    @Override
    public int compare(Usuario usuario1, Usuario usuario2) {
        return usuario1.getNome().compareTo(usuario2.getNome());
    }
    

}