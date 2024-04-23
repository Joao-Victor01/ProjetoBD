package com.projetoBD.demo.consultas;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Repository
public class ConsultasRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ConsultaRowMapper consultaRowMapper;

    public ConsultasRepository(JdbcTemplate jdbcTemplate, ConsultaRowMapper consultaRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.consultaRowMapper = consultaRowMapper;
    }

    public void marcarConsulta(ConsultasEntity consulta) {
        String MARCARCONSULTA = "INSERT INTO consultas (cpfPaciente, crm, dataConsulta, motivoConsulta) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(MARCARCONSULTA, consulta.getPaciente().getCpfPaciente(),
                consulta.getMedico().getCrm(), consulta.getDataConsulta(), consulta.getMotivoConsulta());
    }

    public List<ConsultasEntity> listarTodasConsultas() {
        String LISTARCONSULTAS = "SELECT * FROM consultas";
        return jdbcTemplate.query(LISTARCONSULTAS, consultaRowMapper);
    }

    public Optional<ConsultasEntity> buscarConsultaPorId(Integer idConsulta){
        String BUSCARCONSULTAID = "SELECT * FROM consultas WHERE idConsulta = ?";

        try {
            List<ConsultasEntity> consulta = jdbcTemplate.query(BUSCARCONSULTAID, new Object[]{idConsulta}, consultaRowMapper);
            return consulta.stream().findFirst();
        } catch (EmptyResultDataAccessException e) {
            // se nao achar, retorna vazio
            return Optional.empty();
        }
    }

    public List<ConsultasEntity> listarConsultasPorNomePaciente(String nomePaciente) {
        String CONSULTASNOMEPACIENTE = "SELECT c.* FROM consultas c JOIN pacientes p ON c.cpfPaciente = p.cpfPaciente WHERE LOWER(p.nomePaciente) LIKE ?";
        return jdbcTemplate.query(CONSULTASNOMEPACIENTE, consultaRowMapper, "%" + nomePaciente.toLowerCase(Locale.ROOT) + "%");
    }

    public List<ConsultasEntity> listarConsultasPorNomeMedico(String nomeMedico) {
        String CONSULTASNOMEMEDICO = "SELECT c.* FROM consultas c JOIN medicos m ON c.crm = m.crm WHERE LOWER(m.nomeMedico) LIKE ?";
        return jdbcTemplate.query(CONSULTASNOMEMEDICO, consultaRowMapper, "%" + nomeMedico.toLowerCase(Locale.ROOT) + "%");
    }

    public void cancelarConsulta(Integer idConsulta) {
        String DELETARCONSULTA = "DELETE FROM consultas WHERE idConsulta = ?";
        jdbcTemplate.update(DELETARCONSULTA, idConsulta);
    }

    public List<ConsultasEntity> listarConsultasPorCrmMedico(String crmMedico) {
        String CONSULTASCRM = "SELECT * FROM consultas WHERE crm = ?";
        return jdbcTemplate.query(CONSULTASCRM, consultaRowMapper, crmMedico);
    }

    public List<ConsultasEntity> listarConsultasPorCpfPaciente(String cpfPaciente) {
        String CONSULTASCPF = "SELECT * FROM consultas WHERE cpfPaciente = ?";
        return jdbcTemplate.query(CONSULTASCPF, consultaRowMapper, cpfPaciente);
    }

    public List<ConsultasEntity> listarConsultasPorData(LocalDateTime inicioDoDia, LocalDateTime finalDoDia) {
        String CONSULTASDATA = "SELECT * FROM consultas WHERE dataConsulta BETWEEN TIMESTAMP(?) AND TIMESTAMP(?)";
        return jdbcTemplate.query(CONSULTASDATA, consultaRowMapper, inicioDoDia, finalDoDia);
    }

    public List<ConsultasEntity> listarHorariosConsultasMedicos(LocalDateTime inicioDoDia, LocalDateTime finalDoDia, String crm) {
        String CONSULTAS_DATA_E_MEDICO = "SELECT * FROM consultas WHERE dataConsulta BETWEEN TIMESTAMP(?) AND TIMESTAMP(?) AND crm = ?";
        return jdbcTemplate.query(CONSULTAS_DATA_E_MEDICO, consultaRowMapper, inicioDoDia, finalDoDia, crm);
    }
}