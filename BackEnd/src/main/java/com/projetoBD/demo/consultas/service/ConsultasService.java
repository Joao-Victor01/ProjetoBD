package com.projetoBD.demo.consultas.service;

import com.projetoBD.demo.consultas.ConsultasDTO;
import com.projetoBD.demo.consultas.ConsultasEntity;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ConsultasService {


    void marcarConsulta(ConsultasEntity consulta);

    Optional<ConsultasEntity> buscarConsultaPorId(Integer id);

    List<ConsultasEntity> listarConsultas();

    void deletarConsulta(Integer id);

    List<ConsultasEntity> buscarConsultasPorNomePaciente(String nomePaciente);

    List<ConsultasEntity> buscarConsultasPorNomeMedico(String nomeMedico);

    List<LocalTime> buscarHorariosIndisponiveisPorDataEMedico(LocalDateTime inicioDoDia, LocalDateTime finalDoDia, String crm);
}
