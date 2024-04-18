package com.projetoBD.demo.consultas.service;

import com.projetoBD.demo.consultas.ConsultasEntity;
import com.projetoBD.demo.consultas.ConsultasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ConsultasServiceImpl implements ConsultasService {

    @Autowired
    private ConsultasRepository consultasRepository;

    @Override
    public void marcarConsulta(ConsultasEntity consulta) {
        if (verificarDataConsulta(consulta) && verificarDisponibilidadeMedico(consulta)) {
            consultasRepository.marcarConsulta(consulta);
        } else {
            throw new RuntimeException("Não foi possível marcar a consulta. Verifique a data ou a disponibilidade do médico.");
        }
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
        if (verificarDataConsulta(consulta) && verificarDisponibilidadeMedico(consulta)) {
            consultasRepository.atualizarConsulta(consulta);
        } else {
            throw new RuntimeException("Não foi possível atualizar a consulta. Verifique a data ou a disponibilidade do médico.");
        }
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

    private boolean verificarDataConsulta(ConsultasEntity consulta) {
        LocalDateTime dataAtual = LocalDateTime.now();
        LocalDateTime dataConsulta = consulta.getDataConsulta();

        return dataConsulta.isAfter(dataAtual);
    }

    private boolean verificarDisponibilidadeMedico(ConsultasEntity novaConsulta) {
        List<ConsultasEntity> consultasDoMedico = consultasRepository.buscarConsultasPorNomeMedico(novaConsulta.getNomeMedico());

        for (ConsultasEntity consultaExistente : consultasDoMedico) {
            LocalDateTime dataConsultaExistente = consultaExistente.getDataConsulta();
            LocalDateTime dataNovaConsulta = novaConsulta.getDataConsulta();

            // Se as consultas sao iguais (mesmo paciente, medico e data), permite que mantenha a hora de antes
            if (consultaExistente.equals(novaConsulta)) {
                continue;
            }


            if (dataConsultaExistente.toLocalDate().isEqual(dataNovaConsulta.toLocalDate())
                    && dataConsultaExistente.toLocalTime().equals(dataNovaConsulta.toLocalTime())) {
                return false;
            }


            if (Math.abs(dataConsultaExistente.getHour() - dataNovaConsulta.getHour()) < 1) {
                return false;
            }
        }

        return true;
    }

}
