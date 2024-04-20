package com.projetoBD.demo.pacientes.service;

import com.projetoBD.demo.pacientes.PacientesEntity;

import java.util.List;
import java.util.Optional;

public interface PacientesService {
    void cadastrarNovoPaciente (PacientesEntity novoPaciente);

    void atualizarPaciente(PacientesEntity paciente);

    void deletarPaciente(String cpfPaciente);

    List<PacientesEntity> buscarPacientesPorNome(String nomePaciente);

    Optional<PacientesEntity> buscaPacientePorCpf(String cpfPaciente);

    List<PacientesEntity> listarTodosPacientes();
}
