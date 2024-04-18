package com.projetoBD.demo.pacientes.service;

import com.projetoBD.demo.pacientes.PacientesEntity;
import com.projetoBD.demo.pacientes.PacientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PacientesServiceImpl implements PacientesService {

    @Autowired
    private PacientesRepository pacientesRepository;


    @Override
    public void cadastrarNovoPaciente(PacientesEntity novoPaciente) {

        if (novoPaciente.getDataNascPaciente().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Data Inválida!");
        }
        pacientesRepository.cadastrarPaciente(novoPaciente);
    }

    @Override
    public void atualizarPaciente(PacientesEntity pacienteAtualizado){
        if (pacienteAtualizado.getDataNascPaciente().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Data de nascimento inválida!");
        }

        PacientesEntity pacienteAntigo = pacientesRepository.buscarPacientePorCpf(pacienteAtualizado.getCpfPaciente());

        if (pacienteAntigo == null) {
            throw new IllegalArgumentException("Paciente não encontrado!");
        }

        pacienteAntigo.setNomePaciente(pacienteAtualizado.getNomePaciente());
        pacienteAntigo.setDataNascPaciente(pacienteAtualizado.getDataNascPaciente());

        pacientesRepository.atualizarPaciente(pacienteAntigo);
    }

    @Override
    public void deletarPaciente(String cpfPaciente){
        pacientesRepository.deletarPaciente(cpfPaciente);
    }

    @Override
    public List<PacientesEntity> buscarPacientesPorNome(String nomePaciente){
        return pacientesRepository.buscarPacientesPorNome(nomePaciente);
    }

    @Override
    public PacientesEntity buscaPacientePorCpf(String cpfPaciente){
        return pacientesRepository.buscarPacientePorCpf(cpfPaciente);
    }

    @Override
    public List<PacientesEntity> listarTodosPacientes(){
        return pacientesRepository.listarPacientes();
    }
}
