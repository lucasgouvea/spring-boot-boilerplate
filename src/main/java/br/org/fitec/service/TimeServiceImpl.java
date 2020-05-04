package br.org.fitec.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.fitec.model.Funcao;
import br.org.fitec.model.Projeto;
import br.org.fitec.model.Time;
import br.org.fitec.repositories.TimeRepository;
import br.org.fitec.util.SortArrayList;

import javax.transaction.Transactional;


@Service("TimeService")
@Transactional
public class TimeServiceImpl implements TimeService{

    @Autowired
    private TimeRepository timeRepository;

    @Override
    public Time findById(Long id) {
        return timeRepository.findOne(id);
    }
    

    @Override
    public void saveTime(Time Time) {
        timeRepository.save(Time);
    }

    @Override
    public void updateTime(Time Time) {
        saveTime(Time);
    }

    @Override
    public void deleteTime(Time Time) {
        timeRepository.delete(Time);
    }

    @Override
    public List<Time> findAllTimes() {
        ArrayList<Time> times = (ArrayList<Time>) timeRepository.findAll();
        times = SortArrayList.sort(times, "Time");
        return times;
    
    }

    @Override
    public boolean isTimeExist(Time Time) {
        return findByName(Time.getNome()) != null;
    }

    @Override
    public void deleteTimeById(long id) {
          timeRepository.delete(id);
        
    }

    @Override
    public void deleteAllTimes() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Time findByName(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Time> findByProjeto(String projetoNome) {
        return timeRepository.findByProjetoNome(projetoNome);
    }
    

    @Override
    public List<Time> findByProjetoIsNull() {
        return timeRepository.findByProjetoIsNull();
    }


    @Override
    public void updateTimeProjetoId(Time time) {
        timeRepository.updateTimeProjetoId(time.getTimeId());
        
    }
    
}
