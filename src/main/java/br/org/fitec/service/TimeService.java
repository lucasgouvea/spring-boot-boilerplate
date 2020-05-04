package br.org.fitec.service;

import java.util.List;

import br.org.fitec.model.Projeto;
import br.org.fitec.model.Time;

public interface TimeService {
	
	Time findById(Long id);
	
	Time findByName(String name);
	
	void saveTime(Time Time);
	
	void updateTime(Time Time);
	
	void deleteAllTimes();
	
	List<Time> findAllTimes();
	
	boolean isTimeExist(Time Time);

	void deleteTimeById(long id);

	void deleteTime(Time Time);

	List<Time> findByProjeto(String projetoNome);

    List<Time> findByProjetoIsNull();
    
    void updateTimeProjetoId(Time time);
	
}
