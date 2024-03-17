package com.projetoBD.demo.consultas.Service;
import com.projetoBD.demo.consultas.ConsultasEntity;
import com.projetoBD.demo.consultas.ConsultasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsultasServiceImpl implements ConsultasService {

    @Autowired
    private ConsultasRepository consultasRepository;

    @Override
    public List<ConsultasEntity> listarTodasConsultas() {
        return consultasRepository.listarConsultas();
    }

    @Override
    public void salvarConsulta(ConsultasEntity consulta) {
        consultasRepository.marcarConsulta(consulta);
    }

    @Override
    public Optional<ConsultasEntity> buscarConsultaPorId(Integer id) {
        return consultasRepository.buscarConsultaPorId(id);
    }

    @Override
    public Optional<ConsultasEntity> consultasPorPaciente(String nome){
        return consultasRepository.buscarConsultaPorPaciente(nome);
    }

    @Override
    public void atualizarConsulta(ConsultasEntity consulta) {
        consultasRepository.atualizarConsulta(consulta);
    }

    @Override
    public void deletarConsulta(Integer id) {
        consultasRepository.deletarConsulta(id);
    }
}
