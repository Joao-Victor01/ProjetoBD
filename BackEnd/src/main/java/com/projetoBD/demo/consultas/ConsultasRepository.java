package com.projetoBD.demo.consultas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository //o spring entende que essa classe faz acesso ao BD e traduz as excecoes do BD
public class ConsultasRepository {

    private static String NOVACONSULTA = "INSERT INTO consultasBD (nome_paciente, nome_medico, data_consulta, motivo_consulta) VALUES (?, ?, ?, ?)";
    private static String BUSCARCONSULTA = "SELECT * FROM consultasBD WHERE id_consulta = ?";
    private static String DELETARCONSULTA = "DELETE FROM consultasBD WHERE id_consulta = ?";
    private static String ATUALIZARCONSULTA = "UPDATE consultasBD SET nome_paciente = ?, nome_medico = ?, data_consulta = ?, motivo_consulta = ? WHERE id_consulta = ?";
    private static String LISTARCONSULTAS = "SELECT * FROM consultasBD";


    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void marcarConsulta(ConsultasEntity consulta) {
        //o metodo update do JDBC serve para scripts SQL de atuailizacao, insercao e delecao

        Timestamp horarioConsulta = Timestamp.valueOf(consulta.getDataConsulta());
        jdbcTemplate.update(NOVACONSULTA, consulta.getNomePaciente(), consulta.getNomeMedico(), horarioConsulta, consulta.getMotivoConsulta());
    }

    public Optional<ConsultasEntity> buscarConsultaPorId(Integer id) {
        List<ConsultasEntity> consultas = jdbcTemplate.query(BUSCARCONSULTA, new Object[]{id}, new BeanPropertyRowMapper<>(ConsultasEntity.class));
        return consultas.stream().findFirst();
    }

    public List<ConsultasEntity> listarConsultas (){
        return jdbcTemplate.query(LISTARCONSULTAS, new BeanPropertyRowMapper<>(ConsultasEntity.class));
    }

    public void atualizarConsulta(ConsultasEntity consulta) {
       try{
           jdbcTemplate.update(ATUALIZARCONSULTA, consulta.getNomePaciente(), consulta.getNomeMedico(), consulta.getDataConsulta(), consulta.getMotivoConsulta(), consulta.getIdConsulta());

       }catch(Exception exception){
           System.out.println("Erro: "+exception);
        }
    }


    public void deletarConsulta(Integer id) {
        jdbcTemplate.update(DELETARCONSULTA, id);
    }
}
