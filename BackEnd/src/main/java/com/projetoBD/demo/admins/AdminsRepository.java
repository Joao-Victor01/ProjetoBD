package com.projetoBD.demo.admins;

import com.projetoBD.demo.pacientes.PacientesEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AdminsRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    public void cadastrarAdmin(AdminsEntity admin){

        String CADASTRARADMIN = "INSERT INTO Admins (nomeAdmin, cpfAdmin, dataNascAdmin, " +
                "sexoAdmin, emailAdmin, celularAdmin, tipoUsuario) VALUES (?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(CADASTRARADMIN, admin.getNomeAdmin(), admin.getCpfAdmin(), admin.getDataNascAdmin(),
                admin.getSexoAdmin(), admin.getEmailAdmin(), admin.getCelularAdmin(), admin.getTipoUsuario());

    }

    public void atualizarAdmin(AdminsEntity admin) {
        String ATUALIZARADMIN= "UPDATE Admins SET nomeAdmin = ?, dataNascAdmin = ?, celularAdmin = ?, emailAdmin = ? WHERE cpfAdmin = ?";

        try {
            jdbcTemplate.update(ATUALIZARADMIN, admin.getNomeAdmin(), admin.getDataNascAdmin(),
                    admin.getCelularAdmin(), admin.getEmailAdmin(), admin.getCpfAdmin());
        } catch (Exception exception) {
            System.out.println("Erro: " + exception);
        }
    }

    public void deletarAdmin (String cpfAdmin) {

        String DELETARADMIN = "DELETE FROM Admins WHERE cpfAdmin = ?";
        jdbcTemplate.update(DELETARADMIN, cpfAdmin);
    }

    public Optional<AdminsEntity> buscarAdminPorCpf (String cpfAdmin){
        String BUSCACPF = "SELECT * FROM Admins WHERE cpfAdmin = ?";

        try {
            List<AdminsEntity> admin = jdbcTemplate.query(BUSCACPF, new Object[]{cpfAdmin}, new BeanPropertyRowMapper<>(AdminsEntity.class));
            return admin.stream().findFirst();
        } catch (EmptyResultDataAccessException e) {
            // se nao achar, retorna vazio
            return Optional.empty();
        }
    }

}
