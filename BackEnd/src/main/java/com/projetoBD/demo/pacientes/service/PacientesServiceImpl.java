package com.projetoBD.demo.pacientes.service;

import com.projetoBD.demo.pacientes.PacientesEntity;
import com.projetoBD.demo.pacientes.PacientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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
}
