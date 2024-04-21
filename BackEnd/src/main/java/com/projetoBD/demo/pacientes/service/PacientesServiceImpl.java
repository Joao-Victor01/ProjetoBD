package com.projetoBD.demo.pacientes.service;

import com.projetoBD.demo.pacientes.PacienteException;
import com.projetoBD.demo.pacientes.PacientesEntity;
import com.projetoBD.demo.pacientes.PacientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PacientesServiceImpl implements PacientesService {

    @Autowired
    private PacientesRepository pacientesRepository;


    @Override
    public void cadastrarNovoPaciente(PacientesEntity novoPaciente) {

        if (novoPaciente.getDataNascPaciente().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Data Inválida!");
        }

        if (pacientesRepository.buscarPacientePorCpf(novoPaciente.getCpfPaciente()).isPresent()){
            throw new PacienteException("CPF já cadastrado!");
        }
        pacientesRepository.cadastrarPaciente(novoPaciente);
    }

    @Override
    public void atualizarPaciente(PacientesEntity paciente){
        if (paciente.getDataNascPaciente().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Data de nascimento inválida!");
        } else {
            pacientesRepository.atualizarPaciente(paciente);
        }
    }

    @Override
    public void deletarPaciente(String cpfPaciente){
        //TODO antes de deletar paciente, verificar se tem consulta marcada. Se tiver, nao deleta
        pacientesRepository.deletarPaciente(cpfPaciente);
    }

    @Override
    public List<PacientesEntity> buscarPacientesPorNome(String nomePaciente){
        return pacientesRepository.buscarPacientesPorNome(nomePaciente);
    }

    @Override
    public Optional<PacientesEntity> buscaPacientePorCpf(String cpfPaciente){
        return pacientesRepository.buscarPacientePorCpf(cpfPaciente);
    }

    @Override
    public List<PacientesEntity> listarTodosPacientes(){
        return pacientesRepository.listarPacientes();
    }
}
