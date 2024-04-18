package com.projetoBD.demo.pacientes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PacientesRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    public void cadastrarPaciente (PacientesEntity paciente){

        String CADASTRAPACIENTE = "INSERT INTO pacientes (nomePaciente, cpfPaciente, dataNascPaciente, sexoPaciente, tipoUsuario) VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(CADASTRAPACIENTE, paciente.getNomePaciente(), paciente.getCpfPaciente(),
                paciente.getDataNascPaciente(), paciente.getSexoPaciente(), paciente.getTipoUsuario());

    }

    public void atualizarPaciente (PacientesEntity paciente) {

        String ATUALIZARPACIENTE = "UPDATE pacientes SET nomePaciente = ?, dataNascPaciente = ? WHERE cpfPaciente = ?";

        try{
            jdbcTemplate.update(ATUALIZARPACIENTE, paciente.getNomePaciente(), paciente.getDataNascPaciente());

        }catch(Exception exception){
            System.out.println("Erro: "+exception);
        }
    }

    public void deletarPaciente (String cpfPaciente) {
        String DELETARPACIENTE = "DELETE FROM pacientes WHERE cpfPaciente = ?";
        jdbcTemplate.update(DELETARPACIENTE, cpfPaciente);
    }

    public List<PacientesEntity> buscarPacientesPorNome (String nomePaciente){
        String BUSCANOME = "SELECT * FROM pacientes WHERE LOWER(nomePaciente) LIKE LOWER(?)";

        try {
            return jdbcTemplate.query(
                    BUSCANOME,
                    new Object[]{"%" + nomePaciente.toLowerCase() + "%"},
                    new BeanPropertyRowMapper<>(PacientesEntity.class)
            );
        } catch (EmptyResultDataAccessException e) {
            // Se não encontrar nada, retorna uma lista vazia
            return new ArrayList<>();
        } catch (Exception exception) {
            // Erros genéricos
            System.out.println("Erro: " + exception);
            return new ArrayList<>();
        }
    }

    public PacientesEntity buscarPacientePorCpf (String cpfPaciente){
        String BUSCACPF = "SELECT * FROM pacientes WHERE cpfPaciente = ?";

        try {
            return jdbcTemplate.queryForObject(BUSCACPF, new Object[]{cpfPaciente}, new BeanPropertyRowMapper<>(PacientesEntity.class));
        } catch (EmptyResultDataAccessException e) {
            // se nao achar, retorna nulo
            return null;
        }
    }

    public List<PacientesEntity> listarPacientes (){
        String LISTARPACIENTES = "SELECT * FROM pacientes";

        try {
            return jdbcTemplate.query(LISTARPACIENTES, new BeanPropertyRowMapper<>(PacientesEntity.class));
        } catch (EmptyResultDataAccessException e) {
            // Se não encontrar nada, retorna uma lista vazia
            return new ArrayList<>();
        } catch (Exception exception) {
            // Erros genéricos
            System.out.println("Erro: " + exception);
            return new ArrayList<>();
        }

    }
}
