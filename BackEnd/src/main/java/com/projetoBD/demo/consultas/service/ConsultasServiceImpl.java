package com.projetoBD.demo.consultas.service;

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
    public void marcarConsulta(ConsultasEntity consulta) {
        consultasRepository.marcarConsulta(consulta);
    }

    @Override
    public Optional<ConsultasEntity> buscarConsultaPorId(Integer id) {
        return consultasRepository.buscarConsultaPorId(id);
    }

    @Override
    public List<ConsultasEntity> listarConsultas() {
        return consultasRepository.listarConsultas();
    }

    @Override
    public void atualizarConsulta(ConsultasEntity consulta) {
        consultasRepository.atualizarConsulta(consulta);
    }

    @Override
    public void deletarConsulta(Integer id) {
        consultasRepository.deletarConsulta(id);
    }

    @Override
    public List<ConsultasEntity> buscarConsultasPorNomePaciente(String nomePaciente) {
        return consultasRepository.buscarConsultasPorNomePaciente(nomePaciente);
    }

    @Override
    public List<ConsultasEntity> buscarConsultasPorNomeMedico(String nomeMedico) {
        return consultasRepository.buscarConsultasPorNomeMedico(nomeMedico);
    }
}
