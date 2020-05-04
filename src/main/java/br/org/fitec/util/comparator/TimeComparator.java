package br.org.fitec.util.comparator;

import java.util.Comparator;

import br.org.fitec.model.Time;

public class TimeComparator implements Comparator<Time>{

    @Override
    public int compare(Time time1, Time time2) {
        return time1.getNome().compareTo(time2.getNome());
    }
    

}