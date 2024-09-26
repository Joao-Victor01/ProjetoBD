package com.projetoBD.demo.medicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MedicosRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public void cadastrarMedico (MedicosEntity medico){

        String CADASTRARMEDICO = "INSERT INTO Medicos (nomeMedico, crm, dataNascMedico, " +
                "sexoMedico, emailMedico, celularMedico, tipoUsuario ) VALUES (?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(CADASTRARMEDICO, medico.getNomeMedico(), medico.getCrm(), medico.getDataNascMedico(),
                medico.getSexoMedico(), medico.getEmailMedico(), medico.getCelularMedico(), medico.getTipoUsuario());

    }

    public void atualizarMedico(MedicosEntity medico) {
        String ATUALIZARMEDICO = "UPDATE Medicos SET nomeMedico = ?, dataNascMedico = ?, " +
                "emailMedico = ?, celularMedico = ? WHERE crm = ?";

        try {
            jdbcTemplate.update(ATUALIZARMEDICO, medico.getNomeMedico(),
                    medico.getDataNascMedico(), medico.getEmailMedico(), medico.getCelularMedico(), medico.getCrm());
        } catch (Exception exception) {
            System.out.println("Erro: " + exception);
        }
    }

    public void deletarMedico (String crm) {
        String DELETARMEDICO = "DELETE FROM Medicos WHERE crm = ?";
        jdbcTemplate.update(DELETARMEDICO, crm);
    }

    public List<MedicosEntity> buscarMedicosPorNome (String nomeMedico){
        String BUSCANOME = "SELECT * FROM Medicos WHERE LOWER(nomeMedico) LIKE LOWER(?)";

        try {
            return jdbcTemplate.query(
                    BUSCANOME,
                    new Object[]{"%" + nomeMedico.toLowerCase() + "%"},
                    new BeanPropertyRowMapper<>(MedicosEntity.class)
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

    public Optional<MedicosEntity> buscarMedicoPorCrm(String crm){
        String BUSCACRM = "SELECT * FROM Medicos WHERE crm = ?";

        try {
            List<MedicosEntity> medicos = jdbcTemplate.query(BUSCACRM, new Object[]{crm}, new BeanPropertyRowMapper<>(MedicosEntity.class));
            return medicos.stream().findFirst();
        } catch (EmptyResultDataAccessException e) {
            // se nao achar, retorna vazio
            return Optional.empty();
        }
    }

    public List<MedicosEntity> listarMedicos (){
        String LISTARMEDICOS = "SELECT * FROM Medicos";

        try {
            return jdbcTemplate.query(LISTARMEDICOS, new BeanPropertyRowMapper<>(MedicosEntity.class));
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
