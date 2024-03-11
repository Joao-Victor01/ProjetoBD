package com.projetoBD.demo.consultas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository //o spring entende que essa classe faz acesso ao BD e traduz as excecoes do BD
public class ConsultasRepository {

    private static String NOVACONSULTA = "INSERT INTO consultas (nome_paciente, nome_medico, data_consulta, motivo_consulta) VALUES (?, ?, ?, ?)";
    private static String BUSCARCONSULTA = "SELECT * FROM consultas WHERE id_consulta = ?";
    private static String CONSULTAPORPACIENTE = "SELECT * FROM consultas WHERE nome_paciente = ?";
    private static String DELETARCONSULTA = "DELETE FROM consultas WHERE id_consulta = ?";
    private static String ATUALIZARCONSULTA = "UPDATE consultas SET nome_paciente = ?, nome_medico = ?, data_consulta = ?, motivo_consulta = ? WHERE id_consulta = ?";
    private static String LISTARCONSULTAS = "SELECT * FROM consultas";


    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void marcarConsulta (ConsultasEntity consulta){
        //o metodo update do JDBC serve para scripts SQL de atuailizacao, insercao e delecao
        jdbcTemplate.update(NOVACONSULTA, consulta.getNomePaciente(), consulta.getNomeMedico(), consulta.getDataConsulta(), consulta.getMotivoConsulta());
    }

    public Optional<ConsultasEntity> buscarConsultaPorId(Integer id) {
        List<ConsultasEntity> consultas = jdbcTemplate.query(BUSCARCONSULTA, new Object[]{id}, new BeanPropertyRowMapper<>(ConsultasEntity.class));
        return consultas.stream().findFirst();
    }

    public Optional<ConsultasEntity> buscarConsultaPorPaciente (String nomePaciente){
        List<ConsultasEntity> consultas = jdbcTemplate.query(BUSCARCONSULTA, new Object[]{nomePaciente}, new BeanPropertyRowMapper<>(ConsultasEntity.class));
        return consultas.stream().findFirst();
    }

    public List<ConsultasEntity> listarConsultas (){
        return jdbcTemplate.query(LISTARCONSULTAS, new BeanPropertyRowMapper<>(ConsultasEntity.class));
    }

    public void atualizarConsulta(ConsultasEntity consulta) {
        jdbcTemplate.update(ATUALIZARCONSULTA, consulta.getNomePaciente(), consulta.getNomeMedico(), consulta.getDataConsulta(), consulta.getMotivoConsulta(), consulta.getIdConsulta());
    }


    public void deletarConsulta(Integer id) {
        jdbcTemplate.update(DELETARCONSULTA, id);
    }
}
