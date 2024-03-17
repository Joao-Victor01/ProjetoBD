package com.projetoBD.demo.consultas.Service;
import com.projetoBD.demo.consultas.ConsultasEntity;

import java.util.List;
import java.util.Optional;

public interface ConsultasService {
    List<ConsultasEntity> listarTodasConsultas();
    void salvarConsulta(ConsultasEntity consulta);
    Optional<ConsultasEntity> buscarConsultaPorId(Integer id);

    Optional<ConsultasEntity> consultasPorPaciente(String nome);

    void atualizarConsulta(ConsultasEntity consulta);
    void deletarConsulta(Integer id);
}