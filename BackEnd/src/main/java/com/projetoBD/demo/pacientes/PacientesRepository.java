package com.projetoBD.demo.pacientes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PacientesRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    public void cadastrarPaciente (PacientesEntity paciente){

        String CADASTRAPACIENTE = "INSERT INTO pacientes (nomePaciente, cpfPaciente, dataNascPaciente, sexoPaciente, tipoUsuario) VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(CADASTRAPACIENTE, paciente.getNomePaciente(), paciente.getCpfPaciente(),
                paciente.getDataNascPaciente(), paciente.getSexoPaciente(), paciente.getTipoUsuario());

    }
}
